package com.vivace.recommendservice.entity;

import javax.persistence.*;

@Entity
public class Title {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title_id")
    private Long id;
    private Long recommendId;
    private String title;
    private Long count;
}
