package com.quickorderservice.controller;

import com.quickorderservice.exception.BasketException;
import com.quickorderservice.exception.DuplicatedIdException;
import com.quickorderservice.exception.EditException;
import com.quickorderservice.exception.NotFoundIdException;
import com.quickorderservice.exception.auth.NeedLoginException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> duplicatedIdExceptionHandler(DuplicatedIdException e) {
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> editMemberExceptionHandler(EditException e) {
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> needLoginExceptionHandler(NeedLoginException e) {
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> notFoundIdExceptionHandler(NotFoundIdException e) {
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> runtimeExceptionHandler(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> basketExceptionHandler(BasketException e) {
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    private class ErrorMessage {

        private String message;

        public ErrorMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
