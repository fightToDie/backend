package com.example.vivace.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserVisitLog {
    @Id
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime connectLog;

    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime closeLog;
}
