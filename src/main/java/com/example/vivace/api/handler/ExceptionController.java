package com.example.vivace.api.handler;

import com.example.vivace.common.exception.BadRequestException;
import com.example.vivace.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    //400
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<?> badRequestExceptionHandler(final BadRequestException ex){
        log.warn("error", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    //401
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<?> handleRequestException(final AccessDeniedException ex){
        log.warn("error", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    //404
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(final NotFoundException ex){
        log.warn("error", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    //500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(final Exception ex){
        log.warn("error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
