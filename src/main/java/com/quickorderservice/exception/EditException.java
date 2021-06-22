package com.quickorderservice.exception;

public class EditException extends RuntimeException {

    public EditException() {
        super("수정에 실패하였습니다.");
    }

    public EditException(String message) {
        super(message);
    }
}
