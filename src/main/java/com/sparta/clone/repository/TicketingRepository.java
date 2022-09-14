package com.sparta.clone.repository;

import com.sparta.clone.domain.Member;
import com.sparta.clone.domain.Screening;
import com.sparta.clone.domain.Ticketing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketingRepository extends JpaRepository<Ticketing, Long> {

    List<Ticketing> findByMember(Member member);
}
