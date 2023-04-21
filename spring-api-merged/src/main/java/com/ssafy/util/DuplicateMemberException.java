package com.ssafy.util;

public class DuplicateMemberException extends RuntimeException{
    public DuplicateMemberException() {
        this("중복된 계정입니다.");
    }

    public DuplicateMemberException(String message) {
        super(message);
    }
}
