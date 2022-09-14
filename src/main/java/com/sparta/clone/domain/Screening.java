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
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "movie", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CGVmovie movie;


    @JoinColumn(name = "cinema", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Cinema cinema;

    @Column
    private String date;
    //9월 13/14/15

    @Column
    private String time;

    @Column
    private String booked;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "screening")
    private List<Ticketing> ticketings ;

    public void book(String seat) {
        if(this.booked.equals("")){
            this.booked = this.booked+seat;
        }
        else {
            this.booked = this.booked+","+seat;
        }
    }
}
