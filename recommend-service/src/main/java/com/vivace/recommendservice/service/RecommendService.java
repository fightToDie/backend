package com.vivace.recommendservice.service;

import com.vivace.recommendservice.vo.ResponseTrackIds;
import com.vivace.recommendservice.vo.ResponseTrack;
import com.vivace.recommendservice.vo.ResponseWordcloud;

public interface RecommendService {
    ResponseTrackIds recommendByTrack(Long userId, String title, String artist);
    ResponseTrackIds recommendByPlaylist(Long userId, String playlistId);
    ResponseTrack recommendByGenre(String email);
    ResponseTrackIds searchTrackByTitle(String title, int offset);
    void feedbackRecommend(Long recommendId, Double rating);
    ResponseWordcloud getGenreWordcloud(Long recommendId);
    ResponseWordcloud getTitleWordcloud(Long recommendId);
}
