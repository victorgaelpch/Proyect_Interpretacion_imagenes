import cv2
import numpy as np
import imutils
from transformacion import transformar_perspectiva
from escaner import escanear
from ordenamiento_puntos import ordenar_puntos


def procesar_imagen(imagen):

    factor_escala = imagen.shape[0] / 500.0

    imagen_original = imagen.copy()

    imagen_redimensionada = imutils.resize(imagen, height=500)

    imagen_gris = cv2.cvtColor(imagen_redimensionada, cv2.COLOR_BGR2GRAY)

    imagen_gris = cv2.GaussianBlur(imagen_gris, (5, 5), 0)

    bordes = cv2.Canny(imagen_gris, 50, 150)

    kernel = np.ones((5, 5), np.uint8)

    bordes = cv2.dilate(bordes, kernel, iterations=2)

    bordes = cv2.erode(bordes, kernel, iterations=1)

    contornos = cv2.findContours(bordes.copy(), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)

    contornos = imutils.grab_contours(contornos)

    if len(contornos) == 0:
        raise Exception("No se encontraron contornos")

    contornos = sorted(contornos, key=cv2.contourArea, reverse=True)[:10]

    documento_detectado = None

    area_total_imagen = imagen_redimensionada.shape[0] * imagen_redimensionada.shape[1]

    for contorno in contornos:

        area_contorno = cv2.contourArea(contorno)

        if area_contorno < area_total_imagen * 0.05:
            continue

        perimetro = cv2.arcLength(contorno, True)

        aproximacion = cv2.approxPolyDP(contorno, 0.02 * perimetro, True)

        if len(aproximacion) == 4:

            documento_detectado = aproximacion.reshape(4, 2)

            break

    if documento_detectado is None:

        contorno_principal = max(contornos, key=cv2.contourArea)

        rectangulo_minimo = cv2.minAreaRect(contorno_principal)

        documento_detectado = cv2.boxPoints(rectangulo_minimo)

    documento_detectado = documento_detectado.astype("float32") * factor_escala

    imagen_debug = imagen_original.copy()

    cv2.drawContours(
        imagen_debug, [documento_detectado.astype(int)], -1, (0, 255, 0), 5
    )

    cv2.imwrite("C:/imagenesAnalizadas/debug_contorno.png", imagen_debug)

    imagen_corregida = transformar_perspectiva(imagen_original, documento_detectado)

    documento_ordenado = ordenar_puntos(documento_detectado)
    coordenadas = [
        {"x": int(punto[0]), "y": int(punto[1])} for punto in documento_ordenado
    ]
    resultado_escaneo = escanear(imagen_corregida)

    return {
        "imagen_editada": imagen_corregida,
        "imagen_escaneada": resultado_escaneo["imagen"],
        "coordenadas": coordenadas,
        "anchoResultado": imagen_corregida.shape[1],
        "altoResultado": imagen_corregida.shape[0],
        "umbral": resultado_escaneo["umbral"],
    }
