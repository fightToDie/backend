package com.vivace.recommendservice.vo;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseTrackIds {
    private Long recommendId;
    private List<String> trackIds = new ArrayList<>();
    private int offset;

    public ResponseTrackIds(List<String> trackIds, int offset) {
        this.trackIds = trackIds;
        this.offset = offset;
    }

    public ResponseTrackIds(Long recommendId, List<String> trackIds, int offset) {
        this.recommendId = recommendId;
        this.trackIds = trackIds;
        this.offset = offset;
    }

    public ResponseTrackIds() {

    }
}
