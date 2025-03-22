package com.att.tdp.popcorn_palace.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.att.tdp.popcorn_palace.Entity.Showtime;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {}