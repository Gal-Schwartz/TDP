package com.att.tdp.popcorn_palace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.tdp.popcorn_palace.Entity.Movie;
import com.att.tdp.popcorn_palace.Repository.MovieRepository;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie updatedMovie) {
        
        Movie existMovie= movieRepository.findById(id).orElseThrow(()-> new RuntimeException("Movie not found"));
        existMovie.setDuration(updatedMovie.getDuration());
        existMovie.setGenre(updatedMovie.getGenre());
        existMovie.setRating(updatedMovie.getRating());
        existMovie.setReleaseYear(updatedMovie.getReleaseYear());
        existMovie.setTitle(updatedMovie.getTitle());

        return movieRepository.save(existMovie);
        
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}



   
 
