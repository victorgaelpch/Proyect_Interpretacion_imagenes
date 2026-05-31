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
}
