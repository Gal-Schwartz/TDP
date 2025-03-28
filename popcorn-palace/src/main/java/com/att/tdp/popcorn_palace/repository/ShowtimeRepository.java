package com.att.tdp.popcorn_palace.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.att.tdp.popcorn_palace.entity.Showtime;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    List<Showtime> findByTheaterAndStartTimeBetween(String theater, LocalDateTime start, LocalDateTime end);
    List<Showtime> findByTheater(String theater);
}