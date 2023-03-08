package com.vivace.recommendservice.controller;

import com.vivace.recommendservice.vo.*;
import com.vivace.recommendservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommend")
public class recommendController {

    @Autowired
    private RecommendService recommendService;

    @GetMapping("")
    public ResponseEntity<ResponseTrackUriList> recommendByTrack(@RequestParam String title, @RequestParam String artist) {
        ResponseTrackUriList responseTrackUrilist = recommendService.recommendByTrack(title, artist);
        return ResponseEntity.status(HttpStatus.OK).body(responseTrackUrilist);
    }

    @GetMapping("/playlist/{playlistId}")
    public ResponseEntity<ResponseTrackUriList> recommendByPlaylist(@PathVariable String playlistId) {
        ResponseTrackUriList responseTrackUrilist = recommendService.recommendByPlaylist(playlistId);
        return ResponseEntity.status(HttpStatus.OK).body(responseTrackUrilist);
    }

    @GetMapping("/genre")
    public ResponseEntity<ResponseTrack> recommendByGenre(@RequestBody RequestEmailDto emailDto) {
        ResponseTrack responseTrack = recommendService.recommendByGenre(emailDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(responseTrack);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseTrackUriList> searchTrackByTitle(@RequestParam String title, @RequestParam int offset) {
        ResponseTrackUriList responseTrackUrilist = recommendService.searchTrackByTitle(title, offset);
        return ResponseEntity.status(HttpStatus.OK).body(responseTrackUrilist);
    }

    @GetMapping("/wordcloud/genre/{recommendId}")
    public ResponseEntity<ResponseWordcloud> getGenreWordcloud(@PathVariable Long recommendId) {
        return ResponseEntity.status(HttpStatus.OK).body(recommendService.getGenreWordcloud(recommendId));
    }

    @GetMapping("/wordcloud/title/{recommendId}")
    public ResponseEntity<ResponseWordcloud> getTitleWordcloud(@PathVariable Long recommendId) {
        return ResponseEntity.status(HttpStatus.OK).body(recommendService.getTitleWordcloud(recommendId));
    }

    @PostMapping("/feedback/{recommendId}")
    public ResponseEntity<String> feedbackRecommend(@PathVariable Long recommendId, @RequestBody RequestFeedback requestFeedback) {
        recommendService.feedbackRecommend(recommendId, requestFeedback.getRating());
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
