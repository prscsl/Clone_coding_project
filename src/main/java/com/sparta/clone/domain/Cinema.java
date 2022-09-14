package com.sparta.clone.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

    /*
    예시 : 강원 춘천 1관 총135석
    I(9):15
               <화면>
               1  2  3  4
            A  -  -  O  O
            B  -  -  O  O
            C  O  X  X  O
            D  O  X  X  O
            E  O  X  X  -
            -:없는좌석, O:예매가능 좌석 X:예매완료좌석
     */

public class Cinema {

    @Id
    private String town;
    // 춘천

    @Column
    private int seats;
    // 135

    @Column
    private String cinemaSize;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "cinema")
    private List<Screening> screenings ;
    // (9,15)

//    @Column
//    private String bans;
    // (1,1)/(1,2)/(2,1)/(2,2)/(4,4)

    }
