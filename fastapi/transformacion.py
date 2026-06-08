import cv2
import numpy as np
from ordenamiento_puntos import ordenar_puntos

def transformar_perspectiva(imagen, puntos):

    rectangulo = ordenar_puntos(puntos)

    superior_izquierda, superior_derecha, inferior_derecha, inferior_izquierda = (
        rectangulo
    )

    ancho_1 = np.sqrt(
        ((inferior_derecha[0] - inferior_izquierda[0]) ** 2)
        + ((inferior_derecha[1] - inferior_izquierda[1]) ** 2)
    )

    ancho_2 = np.sqrt(
        ((superior_derecha[0] - superior_izquierda[0]) ** 2)
        + ((superior_derecha[1] - superior_izquierda[1]) ** 2)
    )

    ancho_maximo = max(int(ancho_1), int(ancho_2))

    alto_1 = np.sqrt(
        ((superior_derecha[0] - inferior_derecha[0]) ** 2)
        + ((superior_derecha[1] - inferior_derecha[1]) ** 2)
    )

    alto_2 = np.sqrt(
        ((superior_izquierda[0] - inferior_izquierda[0]) ** 2)
        + ((superior_izquierda[1] - inferior_izquierda[1]) ** 2)
    )

    alto_maximo = max(int(alto_1), int(alto_2))

    puntos_destino = np.array(
        [
            [0, 0],
            [ancho_maximo - 1, 0],
            [ancho_maximo - 1, alto_maximo - 1],
            [0, alto_maximo - 1],
        ],
        dtype="float32",
    )

    matriz_transformacion = cv2.getPerspectiveTransform(rectangulo, puntos_destino)

    imagen_transformada = cv2.warpPerspective(
        imagen, matriz_transformacion, (ancho_maximo, alto_maximo)
    )

    return imagen_transformada