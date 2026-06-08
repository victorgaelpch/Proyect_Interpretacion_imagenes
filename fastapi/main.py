from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import cv2
from pydantic import BaseModel
from procesamiento import procesar_imagen

app = FastAPI()
"""origins = [
    "http://127.0.0.1:8000",
]"""

app.add_middleware(
    CORSMiddleware, allow_origins=["*"], allow_methods=["*"], allow_headers=["*"]
)


app = FastAPI()


@app.get("/")
def read_root():
    return {"Hello": "World"}


class Punto(BaseModel):
    x: int
    y: int


class RespuestaImagenEditada(BaseModel):
    nombreImagenOriginal: str
    nombreImagenEditada: str
    nombreImagenEscaneada: str
    coordenadas: list[Punto]
    anchoResultado: int
    altoResultado: int
    umbral: float


class NombreImagen(BaseModel):
    nombre: str


@app.post("/editarImagen")
def editar_imagen(nombre: NombreImagen):

    ruta = f"C:/imagenesAnalizadas/{nombre.nombre}"

    imagen = cv2.imread(ruta)

    if imagen is None:
        raise HTTPException(status_code=404, detail="Imagen no encontrada")

    extension = nombre.nombre.split(".")[-1].lower()

    resultado = procesar_imagen(imagen)

    nombre_python = f"{nombre.nombre.split('.')[0]}_corregida.{extension}"
    nombre_python_esc = f"{nombre.nombre.split('.')[0]}_escaneada.{extension}"

    nueva_ruta = f"C:/imagenesAnalizadas/{nombre_python}"
    nueva_ruta_esc = f"C:/imagenesAnalizadas/{nombre_python_esc}"

    cv2.imwrite(nueva_ruta, resultado["imagen_editada"])
    cv2.imwrite(nueva_ruta_esc, resultado["imagen_escaneada"])

    return RespuestaImagenEditada(
        nombreImagenOriginal=nombre.nombre,
        nombreImagenEditada=nombre_python,
        nombreImagenEscaneada=nombre_python_esc,
        coordenadas=resultado["coordenadas"],
        anchoResultado=resultado["anchoResultado"],
        altoResultado=resultado["altoResultado"],
        umbral=resultado["umbral"],
    )
