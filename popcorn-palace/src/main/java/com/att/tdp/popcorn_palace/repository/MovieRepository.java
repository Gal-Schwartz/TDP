package com.att.tdp.popcorn_palace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.att.tdp.popcorn_palace.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {}
