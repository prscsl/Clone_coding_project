package com.sparta.clone.repository;

import com.sparta.clone.domain.Screening;
import com.sparta.clone.domain.Ticketing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketingRepository extends JpaRepository<Ticketing, Long> {

}
