package com.vivace.recommendservice.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class SpotifyUriBuilder {

    private static final int PAGE_LIMIT = 10;

    public String getSearchTrackByTitleURI(String title, int offset) {
        //TODO 설정파일로 분리하기
        String uri = "https://api.spotify.com/v1/search";

        String uriWithParams = UriComponentsBuilder
                .fromHttpUrl(uri)
                .queryParam("q", title)
                .queryParam("type", "track")
                .queryParam("limit", PAGE_LIMIT)
                .queryParam("offset", offset)
                .toUriString();

        return uriWithParams;
    }
}
