import cv2
from skimage.filters import threshold_local


def escanear(imagen):

    imagen_gris = cv2.cvtColor(imagen, cv2.COLOR_BGR2GRAY)

    valor_umbral, imagen_escaneada = cv2.threshold(
        imagen_gris, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU
    )

    return {"imagen": imagen_escaneada, "umbral": float(valor_umbral)}
