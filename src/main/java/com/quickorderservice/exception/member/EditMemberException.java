package com.quickorderservice.exception.member;

public class EditMemberException extends RuntimeException {

    public EditMemberException() {
        super("Edit Member Exception");
    }

    public EditMemberException(String message) {
        super(message);
    }
}
