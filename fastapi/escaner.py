import cv2
from skimage.filters import threshold_local

def escanear(imagen):

    imagen_gris = cv2.cvtColor(imagen, cv2.COLOR_BGR2GRAY)

    umbral_local = threshold_local(imagen_gris, 11, offset=10, method="gaussian")

    imagen_escaneada = (imagen_gris > umbral_local).astype("uint8") * 255

    return imagen_escaneada