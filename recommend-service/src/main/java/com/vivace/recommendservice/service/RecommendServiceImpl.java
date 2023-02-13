package com.vivace.recommendservice.service;

import com.vivace.recommendservice.entity.Recommend;
import com.vivace.recommendservice.exception.RecommendNotFoundException;
import com.vivace.recommendservice.repository.RecommendRepository;
import com.vivace.recommendservice.vo.ResponsePlaylist;
import com.vivace.recommendservice.vo.ResponseTrack;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RecommendServiceImpl implements RecommendService{

    private RecommendRepository recommendRepository;

    @Override
    public ResponsePlaylist recommendByTrack(String title, String artist) {
        return null;
    }

    @Override
    public ResponsePlaylist recommendByPlaylist(String playlistId) {
        return null;
    }

    @Override
    public ResponseTrack recommendByGenre(String email) {
        return null;
    }

    @Override
    public ResponsePlaylist searchTrackByTitle(String title) {
        return null;
    }

    @Override
    @Transactional
    public void feedbackRecommend(Long recommendId, Long rating) {
        Recommend recommend = recommendRepository.findById(recommendId).orElseThrow(RecommendNotFoundException::new);
        recommend.updateRating(rating);
    }
}
