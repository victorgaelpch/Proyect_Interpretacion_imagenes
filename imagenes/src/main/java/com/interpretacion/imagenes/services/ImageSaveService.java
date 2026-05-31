package com.interpretacion.imagenes.services;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageSaveService {
    private final RestClient restClient;
        public ImageSaveService(){
            restClient=RestClient.builder()
            .baseUrl("http://localhost:8000/")
            .build();
        }
                private final String RUTA_BASE="C:\\imagenesAnalizadas\\";

        public String saveImage(MultipartFile imagen) throws IOException{

            //tipoImagen(imagen);
            String extencion=imagen.getOriginalFilename().substring(imagen.getOriginalFilename().lastIndexOf("."));
            String nombreSeguro= UUID.randomUUID().toString()+"_imagenOriginal"+extencion;
            if(rutaNoExiste()) {
                crearRuta();
            }
            File file=new File(RUTA_BASE+nombreSeguro);
            try {
                imagen.transferTo(file);
            } catch (Exception e) {
            /*
            throw new ImagenException("Error al guardar la imagen: " + e.getMessage());}
            RespuestaAnalisisDTO analisis = restClient.post()
            .uri("/analisis")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Map.of("nombre", nombreSeguro))
            .retrieve()
            .body(RespuestaAnalisisDTO.class);
            RespuestaImagen respuesta=new RespuestaImagen();
            respuesta.setAnalisis(analisis);
            respuesta.setImagenUrlGris(analisis.getNombreGris());
            respuesta.setImagenUrlOriginal(nombreSeguro);
            respuesta.setImagenUrlHsv(analisis.getNombreHsv());*/
            
            }
            return "la imagen se ha guardado con el nombre: " + nombreSeguro;
    }

        private boolean rutaNoExiste() {
            File ruta=new File(RUTA_BASE);
            return !ruta.exists();
        }
        private void crearRuta() {
            File ruta=new File(RUTA_BASE);
            ruta.mkdirs();
            System.out.println("Ruta creada: " + RUTA_BASE);
        }
    
}
