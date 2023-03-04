package com.vivace.recommendservice.entity;

import javax.persistence.*;

@Entity
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
    @Column(name = "recommend_playlist_id")
    private String recommendPlaylist;
    private Double rating;

    public void updateRating(Double rating) {
        this.rating = rating;
    }
}
