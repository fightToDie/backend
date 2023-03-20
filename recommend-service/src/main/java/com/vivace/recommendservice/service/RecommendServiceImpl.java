package com.vivace.recommendservice.service;

import com.vivace.recommendservice.entity.Recommend;
import com.vivace.recommendservice.exception.BadRequestException;
import com.vivace.recommendservice.exception.RecommendNotFoundException;
import com.vivace.recommendservice.repository.GenreRepository;
import com.vivace.recommendservice.repository.RecommendRepository;
import com.vivace.recommendservice.repository.TitleRepository;
import com.vivace.recommendservice.utils.HttpRequestHandler;
import com.vivace.recommendservice.utils.SpotifyUriBuilder;
import com.vivace.recommendservice.vo.ResponseTrackIds;
import com.vivace.recommendservice.vo.ResponseTrack;
import com.vivace.recommendservice.vo.ResponseWordcloud;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RecommendServiceImpl implements RecommendService{

    private static final String SPOTIFY_TOKEN = "BQBZCFUJZAu0Vy1cPYeWc3ue0p3SJMs7Ch1Dm1n31Z9Nbsgc2nzZeNYZ98nkORZljI3o9aWSDnkX3O0NLp5Kjml-qsrOvamgLm_O2a-YoXN3m88I78iDmup6p3rnYIHMWXtBHhVTGKhwBTuY_DuYQ1uxwRAop4Irz78BOohXbzw1iaYpk8uC9agmpOWBKByRMTQLXpmG";

    private RecommendRepository recommendRepository;
    private TitleRepository titleRepository;
    private GenreRepository genreRepository;
    private SpotifyUriBuilder uriBuilder;
    private HttpRequestHandler httpRequestHandler;

    public RecommendServiceImpl(RecommendRepository recommendRepository, TitleRepository titleRepository,
                                GenreRepository genreRepository, SpotifyUriBuilder uriBuilder, HttpRequestHandler httpRequestHandler) {
        this.recommendRepository = recommendRepository;
        this.titleRepository = titleRepository;
        this.genreRepository = genreRepository;
        this.uriBuilder = uriBuilder;
        this.httpRequestHandler = httpRequestHandler;
    }

    @Override
    public ResponseTrackIds recommendByTrack(Long userId, String title, String artist) {
        // 제목과 가수를 바탕으로 track Id 받아오기
        String trackId = null;

        // AI 서버에 추천 리스트 요청

        // 리턴값 받아서 야무지게 처리 후 ResponseData 만들기
        ResponseTrackIds response = new ResponseTrackIds();

        // DB 저장
        Recommend recommend = Recommend.builder()
                .userId(userId)
                .trackId(trackId)
                .build();

        // Id값 생성되는 시점?
        recommend = recommendRepository.save(recommend);

        return response;
    }

    @Override
    public ResponseTrackIds recommendByPlaylist(Long userId, String playlistId) {
        // 제목과 가수를 바탕으로 track Id 받아오기
        String trackId = null;

        // AI 서버에 추천 리스트 요청

        // 리턴값 받아서 야무지게 처리 후 ResponseData 만들기
        ResponseTrackIds response = new ResponseTrackIds();

        // DB 저장
        Recommend recommend = Recommend.builder()
                .userId(userId)
                .trackId(trackId)
                .build();

        // Id값 생성되는 시점?
        recommend = recommendRepository.save(recommend);

        return response;
    }

    private void saveTitleAndGenreForWordcloud(List<String> trackIds) {
        // 1. ID 바탕으로 제목, 장르 검색
        List<String> titles = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        for (String trackId : trackIds) {
            // trackId 바탕으로 track 정보 요청하는 API 호출

            // 제목만 추리기

            // 장르만 추리기
            // artistId 바탕으로 https://api.spotify.com/v1/artists/{id} 요청
            // genres에서 가져오기
        }

        // 2. 불용어 제거
        titles = deleteStopword(titles);
        genres = deleteStopword(genres);

        // 3. 개수 카운트

        // 4. DB 저장

    }

    private List<String> deleteStopword(List<String> titles) {
        // 사전바탕으로 불용어 제거
        // 불용어 추가할 수 있도록 설정파일로 분리

        return null;
    }

    @Override
    public ResponseTrack recommendByGenre(String email) {
        // AI 서버에 장르 기반 데일리 추천 요청

        // 결과 리턴

        return null;
    }

    @Override
    public ResponseTrackIds searchTracksByTitle(String title, int offset) {
        String uri = uriBuilder.getSearchTracksByTitleURI(title, offset);
        ResponseEntity<String> response = httpRequestHandler.getSearchTracksByTitle(SPOTIFY_TOKEN, uri);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new BadRequestException();
        }

        log.info("response body {}", response.getBody());

        List<String> uriList = getTrackUriList(response);

        return new ResponseTrackIds(uriList, offset);
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

    @Override
    public ResponseWordcloud getGenreWordcloud(Long recommendId) {

        Map<String, Integer> wordcloud = new HashMap<>();

        String titles = "abc";
        String[] split = titles.split("[^a-zA-Z]");
        for (int i = 0; i < split.length; i++) {
            String genre = split[i];
            if (!genre.equals("")) {
                wordcloud.put(genre, wordcloud.getOrDefault(genre, 0)+1);
            }
        }

        return new ResponseWordcloud(wordcloud);
    }

    @Override
    public ResponseWordcloud getTitleWordcloud(Long recommendId) {
        // DB로 부터 recommendId를 바탕으로 띄어쓰기로 구분된 String 형태의 제목들을 가져오기
        //TODO 1:n 엔티티를 추가하여 조인연산 vs delimeter로 구분하여 문자열로 형태로 저장하고 파싱
        //TODO 공통 로직 처리하는 함수 만들기

        Map<String, Integer> wordcloud = new HashMap<>();

        String titles = "abc";
        String[] split = titles.split("[^a-zA-Z]");
        for (int i = 0; i < split.length; i++) {
            String genre = split[i];
            if (!genre.equals("")) {
                wordcloud.put(genre, wordcloud.getOrDefault(genre, 0)+1);
            }
        }

        return new ResponseWordcloud(wordcloud);
    }
}
