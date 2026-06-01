package com.interpretacion.imagenes.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.interpretacion.imagenes.Dto.RespuestaImagenEditada;

import com.interpretacion.imagenes.services.ImageSaveService;
import com.interpretacion.imagenes.Dto.*;
import com.interpretacion.imagenes.exceptions.*;
@RestController
@CrossOrigin(origins="*")
public class ImageSaveController {
    private final String RUTA_ORIGINALES="C:\\imagenesAnalizadas";
    private final ImageSaveService imageSaveService;
    public ImageSaveController(ImageSaveService imageSaveService){ this.imageSaveService = imageSaveService; }

    @PostMapping("/imagen/save")
    public RespuestaImagenEditada saveImage(@RequestParam("image") MultipartFile image){
        validaciones(image);
        try {
            return imageSaveService.saveImage(image);
        } catch (Exception e) {
            throw new ImagenException("Error al guardar la imagen en disco: " + e.getMessage());
        }
    }
    
    private void validaciones(MultipartFile imagen){
        if(imagen.isEmpty()){  throw new ImagenEmptyException("La imagen esta vacia");}
    }
    @GetMapping("/imagen/DesdeElfron_ten/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        Path filePath = Path.of(RUTA_ORIGINALES, filename);
        if (!Files.exists(filePath)) {return ResponseEntity.notFound().build();}
        Resource resource = new UrlResource(filePath.toUri());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath))).body(resource);
    }

}
