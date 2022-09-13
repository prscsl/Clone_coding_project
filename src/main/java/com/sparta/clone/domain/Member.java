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
public class Member {
    @Id
    private String id;

    @Column
    private String name;

    @Column
    private int gender;

    @Column
    private int birthyear;

    @Column
    private String userRole;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Ticketing> ticketings ;

}
