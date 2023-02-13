package com.vivace.recommendservice.service;

import com.vivace.recommendservice.vo.ResponsePlaylist;
import com.vivace.recommendservice.vo.ResponseTrack;

public interface RecommendService {
    ResponsePlaylist recommendByTrack(String title, String artist);

    ResponsePlaylist recommendByPlaylist(String playlistId);
    ResponseTrack recommendByGenre(String email);

    ResponsePlaylist searchTrackByTitle(String title);

    void feedbackRecommend(Long recommendId, Long rating);
}
