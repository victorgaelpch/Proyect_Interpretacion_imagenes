import numpy as np

def ordenar_puntos(puntos):

    rectangulo = np.zeros((4, 2), dtype="float32")

    suma_coordenadas = puntos.sum(axis=1)

    rectangulo[0] = puntos[np.argmin(suma_coordenadas)]  
    rectangulo[2] = puntos[np.argmax(suma_coordenadas)] 

    diferencia_coordenadas = np.diff(puntos, axis=1)

    rectangulo[1] = puntos[np.argmin(diferencia_coordenadas)]
    rectangulo[3] = puntos[np.argmax(diferencia_coordenadas)]

    return rectangulo