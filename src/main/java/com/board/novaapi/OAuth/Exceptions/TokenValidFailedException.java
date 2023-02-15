package com.board.novaapi.OAuth.Exceptions;

public class TokenValidFailedException extends RuntimeException {

    public TokenValidFailedException() {
        super("토큰발급실패");
    }

    private TokenValidFailedException(String message) {
        super(message);
    }
}
