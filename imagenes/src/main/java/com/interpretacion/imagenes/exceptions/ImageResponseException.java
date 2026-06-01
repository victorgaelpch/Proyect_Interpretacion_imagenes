package com.interpretacion.imagenes.exceptions;

import com.interpretacion.imagenes.Dto.ErroresDTO;

public record ImageResponseException(String message, int status, String code, String path, String timestamp) {
    public ErroresDTO toErroresDTO() {
        ErroresDTO erroresDTO = new ErroresDTO();
        erroresDTO.setMessage(message);
        erroresDTO.setStatus(status);
        erroresDTO.setCode(code);
        erroresDTO.setPath(path);
        erroresDTO.setTimestamp(timestamp);
        return erroresDTO;
    }

}