package com.interpretacion.imagenes.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@CrossOrigin(origins="*")
public class ImageSaveController {

   /*
    private final ImageSaveService imageSaveService;
    public ImageSaveController(ImageSaveService imageSaveService){ this.imageSaveService = imageSaveService; }
    */

    @PostMapping("/save")
    public String saveImage(@RequestParam("image") MultipartFile image){
        String fileName = image.getOriginalFilename();
        return "la imagen se ha recibido con el nombre: " + fileName;
    }
    
}
