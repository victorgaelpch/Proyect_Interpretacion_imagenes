package com.interpretacion.imagenes.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import com.interpretacion.imagenes.Dto.RespuestaImagenEditada;
import com.interpretacion.imagenes.exceptions.*;

@Service
public class ImageSaveService {
    private final RestClient restClient;
        public ImageSaveService(){
            restClient=RestClient.builder()
            .baseUrl("http://localhost:8000/")
            .build();
        }
        private final String RUTA_BASE="C:\\imagenesAnalizadas\\";

        public RespuestaImagenEditada saveImage(MultipartFile imagen) throws IOException{

            tipoImagen(imagen);
            String extencion=imagen.getOriginalFilename().substring(imagen.getOriginalFilename().lastIndexOf("."));
            String nombreSeguro= UUID.randomUUID().toString()+"_imagenOriginal"+extencion;
            if(rutaNoExiste()) {
                crearRuta();
            }
            File file=new File(RUTA_BASE+nombreSeguro);
            try {
                imagen.transferTo(file);
            } catch (Exception e) {
            throw new ImagenException("Error al guardar la imagen en disco: " + e.getMessage());
            }
            RespuestaImagenEditada respuesta = restClient.post()
            .uri("/editarImagen")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Map.of("nombre", nombreSeguro))
            .retrieve()
            .body(RespuestaImagenEditada.class);
            RespuestaImagenEditada res = new RespuestaImagenEditada();
            res.setNombreImagenOriginal(respuesta.getNombreImagenOriginal());
            res.setNombreImagenEditada(respuesta.getNombreImagenEditada());

            return res;
            
            
    }
        private void tipoImagen(MultipartFile imagen) throws IOException{
            List<String> tipoPermitidos= List.of("image/png","image/jpeg","image/jpg");
            Tika tika = new Tika();
            String contentType = tika.detect(imagen.getInputStream());
            if(!tipoPermitidos.contains(contentType)){
            throw new ImagenContentTypeException("El tipo de imagen no es permitido");
            }  
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
