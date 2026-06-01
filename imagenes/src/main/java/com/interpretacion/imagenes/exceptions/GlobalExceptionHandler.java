package com.interpretacion.imagenes.exceptions;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ImagenEmptyException.class)
    public ResponseEntity<ImageResponseException> imagenEmptyException(ImagenEmptyException ex){
        ImageResponseException response = new ImageResponseException(LocalDate.now().toString());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ImagenException.class)
    public ResponseEntity<ImageResponseException> imagenException(ImagenException ex){
        ImageResponseException response = new ImageResponseException(LocalDate.now().toString());
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ImagenMaxUploadSizeException.class)
    public ResponseEntity<ImageResponseException> imagenMaxUploadSizeException(ImagenMaxUploadSizeException ex){
        ImageResponseException response = new ImageResponseException(LocalDate.now().toString());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
