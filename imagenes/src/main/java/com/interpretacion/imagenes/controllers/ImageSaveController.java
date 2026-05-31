package com.interpretacion.imagenes.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.interpretacion.imagenes.exceptions.ImagenEmptyException;

import com.interpretacion.imagenes.services.ImageSaveService;

@RestController
@CrossOrigin(origins="*")
public class ImageSaveController {

    private final ImageSaveService imageSaveService;
    public ImageSaveController(ImageSaveService imageSaveService){ this.imageSaveService = imageSaveService; }

    @PostMapping("/save")
    public String saveImage(@RequestParam("image") MultipartFile image){
        validaciones(image);
        try {
            return imageSaveService.saveImage(image);
        } catch (Exception e) {
            return "Error al guardar la imagen: " + e.getMessage();
        }
    }
    private void validaciones(MultipartFile imagen){
        if(imagen.isEmpty()){  throw new ImagenEmptyException("La imagen esta vacia");}
    }

}
