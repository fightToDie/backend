package com.vivace.recommendservice.vo;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseTrackUriList {
    private Long recommendId;
    private List<String> trackUriList = new ArrayList<>();
    private int offset;

    public ResponseTrackUriList(List<String> trackUriList, int offset) {
        this.trackUriList = trackUriList;
        this.offset = offset;
    }

    public ResponseTrackUriList(Long recommendId, List<String> trackUriList, int offset) {
        this.recommendId = recommendId;
        this.trackUriList = trackUriList;
        this.offset = offset;
    }
}
