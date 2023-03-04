package com.vivace.recommendservice.service;

import com.vivace.recommendservice.entity.Recommend;
import com.vivace.recommendservice.exception.BadRequestException;
import com.vivace.recommendservice.exception.RecommendNotFoundException;
import com.vivace.recommendservice.repository.RecommendRepository;
import com.vivace.recommendservice.utils.HttpRequestHandler;
import com.vivace.recommendservice.utils.SpotifyUriBuilder;
import com.vivace.recommendservice.vo.ResponseTrackUriList;
import com.vivace.recommendservice.vo.ResponseTrack;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RecommendServiceImpl implements RecommendService{

    private RecommendRepository recommendRepository;
    private SpotifyUriBuilder uriBuilder;
    private HttpRequestHandler httpRequestHandler;

    public RecommendServiceImpl(RecommendRepository recommendRepository, SpotifyUriBuilder uriBuilder, HttpRequestHandler httpRequestHandler) {
        this.recommendRepository = recommendRepository;
        this.uriBuilder = uriBuilder;
        this.httpRequestHandler = httpRequestHandler;
    }

    @Override
    public ResponseTrackUriList recommendByTrack(String title, String artist) {
        // AI 서버에 추천 리스트 요청

        return null;
    }

    @Override
    public ResponseTrackUriList recommendByPlaylist(String playlistId) {
        return null;
    }

    @Override
    public ResponseTrack recommendByGenre(String email) {
        return null;
    }

    @Override
    public ResponseTrackUriList searchTrackByTitle(String title, int offset) {
        String uri = uriBuilder.getSearchTrackByTitleURI(title, offset);
        String spotifyToken = "BQBZCFUJZAu0Vy1cPYeWc3ue0p3SJMs7Ch1Dm1n31Z9Nbsgc2nzZeNYZ98nkORZljI3o9aWSDnkX3O0NLp5Kjml-qsrOvamgLm_O2a-YoXN3m88I78iDmup6p3rnYIHMWXtBHhVTGKhwBTuY_DuYQ1uxwRAop4Irz78BOohXbzw1iaYpk8uC9agmpOWBKByRMTQLXpmG";
        ResponseEntity<String> response = httpRequestHandler.getSearchTrackByTitleURI(spotifyToken, uri);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new BadRequestException();
        }

        log.info("response body {}", response.getBody());

        List<String> uriList = getTrackUriList(response);

        return new ResponseTrackUriList(uriList, offset);
    }

    private List<String> getTrackUriList(ResponseEntity<String> response) {
        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONArray items = jsonObject.getJSONObject("tracks").getJSONArray("items");
        List<String> uriList = new ArrayList<>();
        for (Object item : items) {
            uriList.add(((JSONObject) item).get("uri").toString());
        }
        return uriList;
    }

    @Override
    @Transactional
    public void feedbackRecommend(Long recommendId, Double rating) {
        Recommend recommend = recommendRepository.findById(recommendId).orElseThrow(RecommendNotFoundException::new);
        recommend.updateRating(rating);
    }
}
