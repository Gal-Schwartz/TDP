package com.att.tdp.popcorn_palace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.popcorn_palace.Entity.Ticket;
import com.att.tdp.popcorn_palace.service.TicketService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/book")
    public ResponseEntity<?> bookTicket(@RequestBody @Valid Ticket ticket) {
        try {
            Ticket savedTicket = ticketService.bookTicket(ticket);
            return ResponseEntity.ok(savedTicket);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

