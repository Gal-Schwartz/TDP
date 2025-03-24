package com.att.tdp.popcorn_palace;

import com.att.tdp.popcorn_palace.Entity.Movie;
import com.att.tdp.popcorn_palace.Entity.Showtime;
import com.att.tdp.popcorn_palace.Entity.Ticket;
import com.att.tdp.popcorn_palace.Repository.MovieRepository;
import com.att.tdp.popcorn_palace.Repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.Repository.TicketRepository;
import com.att.tdp.popcorn_palace.service.MovieService;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
import com.att.tdp.popcorn_palace.service.TicketService;
import com.att.tdp.popcorn_palace.validation.ReleaseYearValidator;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import jakarta.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PopcornPalaceApplicationTests {

	@Mock
	private MovieRepository movieRepository;
	@Mock
	private ShowtimeRepository showtimeRepository;
	@Mock
	private TicketRepository ticketRepository;

	@InjectMocks
	private MovieService movieService;
	@InjectMocks
	private ShowtimeService showtimeService;
	@InjectMocks
	private TicketService ticketService;

	Movie movie;
	Showtime showtime;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		movie = new Movie();
		movie.setId(1L);
		movie.setTitle("how to lose a guy in 10 days");
		movie.setGenre("Romance");
		movie.setDuration(120);
		movie.setRating(9.0);
		movie.setReleaseYear(2000);

		showtime = new Showtime();
		showtime.setId(1L);
		showtime.setMovie(movie);
		showtime.setTheater("A1");
		showtime.setStartTime(LocalDateTime.of(2025, 3, 21, 20, 0));
		showtime.setEndTime(LocalDateTime.of(2025, 3, 21, 22, 0));
		showtime.setPrice(35.0);
	}

	// MovieService
	@Test
	void testAddMovie_success() {
		when(movieRepository.save(any(Movie.class))).thenReturn(movie);
		Movie saved = movieService.addMovie(movie);
		assertEquals("how to lose a guy in 10 days", saved.getTitle());
	}

	@Test
	void testUpdateMovie_notFound() {
		when(movieRepository.findById(2L)).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> movieService.updateMovie(2L, movie));
	}

	@Test
	void testDeleteMovie_success() {
		doNothing().when(movieRepository).deleteById(1L);
		assertDoesNotThrow(() -> movieService.deleteMovie(1L));
		verify(movieRepository, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteMovie_notFound() {
		doThrow(new EmptyResultDataAccessException(1)).when(movieRepository).deleteById(99L);
		assertThrows(EmptyResultDataAccessException.class, () -> movieService.deleteMovie(99L));
	}

	// ShowtimeService

	@Test
	void testAddShowtime_overlap_shouldFail() {
		Showtime overlap = new Showtime();
		overlap.setMovie(movie);
		overlap.setTheater("A1");
		overlap.setStartTime(LocalDateTime.of(2025, 3, 21, 21, 0)); 
		overlap.setEndTime(LocalDateTime.of(2025, 3, 21, 23, 0));
		overlap.setPrice(35.0);

		when(showtimeRepository.findByTheater("A1")).thenReturn(List.of(showtime));

		assertThrows(RuntimeException.class, () -> showtimeService.addShowtime(overlap));
	}

	@Test
	void testAddShowtime_nonOverlapping_shouldSucceed() {
		Showtime newShow = new Showtime();
		newShow.setTheater("A1");
		newShow.setStartTime(LocalDateTime.of(2025, 3, 21, 22, 30));
		newShow.setEndTime(LocalDateTime.of(2025, 3, 21, 23, 30));
	
		when(showtimeRepository.findByTheater("A1")).thenReturn(List.of(showtime));
		when(showtimeRepository.save(any())).thenReturn(newShow);
	
		Showtime saved = showtimeService.addShowtime(newShow);
		assertEquals("A1", saved.getTheater());
	}
	


	@Test
	void testUpdateShowtime__shouldSucceed() {
		Showtime existing = new Showtime();
		existing.setId(1L);
		existing.setTheater("A1");
		existing.setStartTime(LocalDateTime.of(2025, 3, 21, 20, 0));
		existing.setEndTime(LocalDateTime.of(2025, 3, 21, 22, 0));
		existing.setPrice(35.0);
		existing.setMovie(movie);

		Showtime updatedInput = new Showtime();
		updatedInput.setTheater("B2");
		updatedInput.setStartTime(LocalDateTime.of(2025, 3, 22, 18, 0));
		updatedInput.setEndTime(LocalDateTime.of(2025, 3, 22, 20, 0));
		updatedInput.setPrice(40.0);
		updatedInput.setMovie(movie);

		when(showtimeRepository.findById(1L)).thenReturn(Optional.of(existing));
		when(showtimeRepository.findByTheater("B2")).thenReturn(List.of());
		when(showtimeRepository.save(any(Showtime.class))).thenAnswer(inv -> inv.getArgument(0));

		Showtime result = showtimeService.updateShowtime(1L, updatedInput);

		assertEquals("B2", result.getTheater());
		assertEquals(40.0, result.getPrice());
		assertEquals(LocalDateTime.of(2025, 3, 22, 18, 0), result.getStartTime());
	}

	// TicketService
	@Test
	void testBookTicket_thenTryToBookSameSeat_shouldThrowOnSecond() {
		Ticket first = new Ticket();
		first.setShowtime(showtime);
		first.setSeatNumber(7);
		first.setCustomerName("Gal");

		Ticket second = new Ticket();
		second.setShowtime(showtime);
		second.setSeatNumber(7);
		second.setCustomerName("Noa");

		when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));

		// success in the first ticket
		when(ticketRepository.save(first)).thenReturn(first);
		Ticket saved = ticketService.bookTicket(first);
		assertEquals("Gal", saved.getCustomerName());

		// fail in the other ticket for the same seat
		when(ticketRepository.save(second)).thenThrow(DataIntegrityViolationException.class);
		assertThrows(RuntimeException.class, () -> ticketService.bookTicket(second));
	}

	// Validators

	@Test
	void testValidReleaseYear_valid() {
		ReleaseYearValidator validator = new ReleaseYearValidator();
		assertTrue(validator.isValid(2025, mock(ConstraintValidatorContext.class)));
		assertTrue(validator.isValid(1900, mock(ConstraintValidatorContext.class)));
	}

	@Test
	void testValidReleaseYear_invalid() {
		ReleaseYearValidator validator = new ReleaseYearValidator();
		assertFalse(validator.isValid(1800, mock(ConstraintValidatorContext.class)));
		assertFalse(validator.isValid(9999, mock(ConstraintValidatorContext.class)));
	}


	@Test
	void testTicket_customerName_blank_shouldFailValidation() {
		Ticket ticket = new Ticket();
		ticket.setCustomerName("   "); 

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<Ticket>> violations = validator.validate(ticket);

		assertFalse(violations.isEmpty(), "Should fail validation when customerName is blank");
	}

}
