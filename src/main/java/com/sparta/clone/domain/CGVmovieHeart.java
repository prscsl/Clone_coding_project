package com.sparta.clone.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CGVmovieHeart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    //찜하기 한 타이틀 id
    @Column(nullable = false)
    private Long movieId;

    //찜하기 누른 유저 정보
    @Column(nullable = false)
    private String memberId;
}
