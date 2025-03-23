package com.att.tdp.popcorn_palace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.att.tdp.popcorn_palace.Entity.Showtime;
import com.att.tdp.popcorn_palace.Entity.Ticket;
import com.att.tdp.popcorn_palace.Repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.Repository.TicketRepository;

import jakarta.transaction.Transactional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Transactional
    public Ticket bookTicket(Ticket ticket) {
        Showtime showtime = showtimeRepository.findById(ticket.getShowtime().getId())
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        String seatKey = showtime.getId() + "-" + ticket.getSeatNumber();
        ticket.setSeatKey(seatKey);

        try {
            return ticketRepository.save(ticket);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Seat already taken");
        }
    }

}
