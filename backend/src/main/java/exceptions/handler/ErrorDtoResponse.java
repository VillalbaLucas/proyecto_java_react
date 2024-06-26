package exceptions.handler;

import java.time.LocalDateTime;

import jakarta.ws.rs.core.Response.Status;
//genera un objeto respuesta en el cuerpo del Response
public record ErrorDtoResponse(
    Status httpStatus, 
    int codeError, 
    String message,
    LocalDateTime timestamp
) {}
