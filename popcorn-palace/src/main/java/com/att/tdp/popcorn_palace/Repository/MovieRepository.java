package com.att.tdp.popcorn_palace.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.att.tdp.popcorn_palace.Entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {}
