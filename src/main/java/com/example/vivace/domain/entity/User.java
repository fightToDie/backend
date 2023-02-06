package com.example.vivace.domain.entity;

import com.example.vivace.domain.entity.enumtype.Gender;
import com.example.vivace.domain.entity.enumtype.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User { //유저 spotify 회원가입 받은다음 정보 입력받아도 좋을 것 같습니다...
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private Long userSeq;

    @Column(length = 40)
    private String email;

    @Column(length = 100) //token
    private String pwd;

    @Column(length = 10)
    private String nickname;

    @Column
    private Long age;

    //성별(enum, not null)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime updatedAt;

    //마지막이용시간(timestamp)
    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany
    private List<Recommend> recommendList = new ArrayList<>();

    @Builder
    public User(Long userSeq, String email, String pwd, String nickname, Long age, Gender gender, Role role) {
        this.userSeq = userSeq;
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.role = role;
    }
}
