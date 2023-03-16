package com.vivace.recommendservice.entity;

import javax.persistence.*;

@Entity
public class Genre {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long id;
    private Long recommendId;
    private String genre;
    private Long count;
}
