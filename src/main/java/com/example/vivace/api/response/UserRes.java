package com.example.vivace.api.response;

import com.example.vivace.domain.entity.User;
import lombok.Getter;

@Getter
public class UserRes {
    private final Long userSeq;
    private final String email;
    //최근 검색 플레이리스트
    //최근 검색 곡
    //별 갯수(???)

    public UserRes(User user){
        this.userSeq = user.getUserSeq();
        this.email = user.getEmail();
    }
}
