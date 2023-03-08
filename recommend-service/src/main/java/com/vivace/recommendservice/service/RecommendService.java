package com.vivace.recommendservice.service;

import com.vivace.recommendservice.vo.ResponseTrackUriList;
import com.vivace.recommendservice.vo.ResponseTrack;
import com.vivace.recommendservice.vo.ResponseWordcloud;

public interface RecommendService {
    ResponseTrackUriList recommendByTrack(String title, String artist);
    ResponseTrackUriList recommendByPlaylist(String playlistId);
    ResponseTrack recommendByGenre(String email);
    ResponseTrackUriList searchTrackByTitle(String title, int offset);
    void feedbackRecommend(Long recommendId, Double rating);
    ResponseWordcloud getGenreWordcloud(Long recommendId);
    ResponseWordcloud getTitleWordcloud(Long recommendId);
}
