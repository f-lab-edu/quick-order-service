package com.quickorderservice.controller;

import com.quickorderservice.exception.DuplicatedIdException;
import com.quickorderservice.exception.auth.NeedLoginException;
import com.quickorderservice.exception.member.EditMemberException;
import com.quickorderservice.exception.member.NotFoundMemberException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity notFoundMemberExceptionHandler(NotFoundMemberException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity duplicatedIdExceptionHandler(DuplicatedIdException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity editMemberExceptionHandler(EditMemberException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity needLoginExceptionHandler(NeedLoginException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
