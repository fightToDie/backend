package com.example.vivace.domain.entity;

import javax.persistence.*;

@Entity
public class Recommend {
    @Id
    private Long recommendSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @Column(length = 40)
    private String trackId;

    @Column(length = 40)
    private String playlistId;

    @Column//erd에 있는거 그대로 적었습니다..
    private Long field4;

    @Column
    private Long rating;
}
