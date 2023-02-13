package com.vivace.recommendservice.exception;

public class RecommendNotFoundException extends RuntimeException{
    public RecommendNotFoundException() {
        super("해당 추천기록이 없습니다.");
    }
}
