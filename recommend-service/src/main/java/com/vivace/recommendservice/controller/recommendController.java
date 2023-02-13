package com.vivace.recommendservice.controller;

import com.vivace.recommendservice.service.RecommendService;
import com.vivace.recommendservice.vo.RequestEmailDto;
import com.vivace.recommendservice.vo.RequestFeedback;
import com.vivace.recommendservice.vo.ResponsePlaylist;
import com.vivace.recommendservice.vo.ResponseTrack;
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
    public ResponseEntity<ResponsePlaylist> recommendByTrack(@RequestParam String title, @RequestParam String artist) {
        ResponsePlaylist responsePlaylist = recommendService.recommendByTrack(title, artist);
        return ResponseEntity.status(HttpStatus.OK).body(responsePlaylist);
    }

    @GetMapping("/playlist/{playlistId}")
    public ResponseEntity<ResponsePlaylist> recommendByPlaylist(@PathVariable String playlistId) {
        ResponsePlaylist responsePlaylist = recommendService.recommendByPlaylist(playlistId);
        return ResponseEntity.status(HttpStatus.OK).body(responsePlaylist);
    }

    @GetMapping("/genre")
    public ResponseEntity<ResponseTrack> recommendByGenre(@RequestBody RequestEmailDto emailDto) {
        ResponseTrack responseTrack = recommendService.recommendByGenre(emailDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(responseTrack);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponsePlaylist> searchTrackByTitle(@RequestParam String title) {
        ResponsePlaylist responsePlaylist = recommendService.searchTrackByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(responsePlaylist);
    }

    @PostMapping("/feedback/{recommendId}")
    public ResponseEntity<String> feedbackRecommend(@PathVariable Long recommendId, @RequestBody RequestFeedback requestFeedback) {
        recommendService.feedbackRecommend(recommendId, requestFeedback.getRating());
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
