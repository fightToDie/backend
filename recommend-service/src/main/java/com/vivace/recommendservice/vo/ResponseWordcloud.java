package com.vivace.recommendservice.vo;

import lombok.Getter;

import java.util.Map;

@Getter
public class ResponseWordcloud {
    private Map<String, Integer> wordcloud;
}
