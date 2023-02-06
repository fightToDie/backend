package com.example.vivace.api.controller;

import com.example.vivace.api.request.FeedbackReq;
import com.example.vivace.api.service.RecommendService;
import com.example.vivace.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping("/")
    ResponseEntity<?> recommendByTitleAndArtist(@RequestParam String title, @RequestParam String artist) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/playlist/{playlistId}")
    ResponseEntity<?> recommendByPlaylist(@PathVariable String playlistId) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    //api설계 수정 필요
    @GetMapping("/playlist/wordcloud/genre")
    ResponseEntity<?> wordCloudByGenre(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/playlist/wordcloud/title")
    ResponseEntity<?> wordCloudByTitle(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/feedback")
    ResponseEntity<?> getFeedback(@RequestBody FeedbackReq feedbackReq){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/genre")
    ResponseEntity<?> recommendByGenre(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/search")
    ResponseEntity<?> searchByTitle(@RequestParam String title) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
