package com.board.novaapi.Exception;

public class MemberStudentIdAlreadyExistsException extends RuntimeException {
    public MemberStudentIdAlreadyExistsException(String message) {
        super(message);
    }
}
