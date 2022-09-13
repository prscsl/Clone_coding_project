package com.sparta.clone.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class CGVmovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String rank;

    @Column
    private String title;

    @Column
    private String titleEng;

    @Column
    private String img;

    @Column
    private String date;

    @Column
    private String director;

    @Column
    private String actor;

    @Column
    private String rate;

    @Column
    private String genre;

    @Column
    private String base;

    @Column
    private String detail;

    @Column
    private int status = 2;

    @Column
    private int likes;


    @OneToMany (fetch = FetchType.LAZY, mappedBy = "movie")
    private List<Screening> screenings ;


    public CGVmovie(String rank, String title, String titleEng, String director, String actor, String img, String rate, String genre,  String base, String detail, String date) {
        this.rank = rank;
        this.title = title;
        this.titleEng = titleEng;
        this.director = director;
        this.actor = actor;
        this.img = img;
        this.rate = rate;
        this.genre = genre;
        this.base = base;
        this.detail = detail;
        this.date = date;
    }

    public void updateLikes(int num){
        this.likes = num;
    }
}
