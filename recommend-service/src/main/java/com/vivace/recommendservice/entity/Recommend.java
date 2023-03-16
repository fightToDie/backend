package com.vivace.recommendservice.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "track_id")
    private String trackId;
    @Column(name = "playlist_id")
    private String playlistId;
    private Double rating;
    @OneToMany(mappedBy = "recommend", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecommendPlaylist> tracks;

    public Recommend() {

    }

    public void updateRating(Double rating) {
        this.rating = rating;
    }
}
