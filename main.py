from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import cv2
from pydantic import BaseModel

app = FastAPI()
"""origins = [
    "http://127.0.0.1:8000",
]"""

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"]
)



app = FastAPI()
@app.get("/")
def read_root():
    return {"Hello": "World"}

class RespuestaImagenEditada(BaseModel):
    nombreImagenOriginal:str
    nombreImagenEditada:str

class NombreImagen(BaseModel):
    nombre:str

@app.post("/editarImagen")
def editar_imagen(nombre: NombreImagen):
    ruta = f"C:/imagenesAnalizadas/{nombre.nombre}"
    imagen = cv2.imread(ruta)

    if imagen is None:
        raise HTTPException(status_code=404, detail="Imagen no encontrada")

    extension = nombre.nombre.split(".")[-1].lower()

    
    gray_imagen=cv2.cvtColor(imagen, cv2.COLOR_BGR2GRAY)
    nombre_python = f"{nombre.nombre.split('.')[0]}_gris.{extension}"
    nueva_ruta = f"C:/imagenesAnalizadas/{nombre_python}"
    cv2.imwrite(nueva_ruta, gray_imagen)
    
    return RespuestaImagenEditada(nombreImagenOriginal=nombre.nombre, nombreImagenEditada=nombre_python)