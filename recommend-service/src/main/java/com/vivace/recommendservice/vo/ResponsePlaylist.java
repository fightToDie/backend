package com.vivace.recommendservice.vo;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponsePlaylist {
    private Long recommendId;
    private List<String> trackIdList = new ArrayList<>();
}
