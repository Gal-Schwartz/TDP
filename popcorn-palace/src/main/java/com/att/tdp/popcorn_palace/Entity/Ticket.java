package com.att.tdp.popcorn_palace.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Showtime is required")
    @ManyToOne
    private Showtime showtime;
    @Min(value = 1, message = "Seat number must be positive")
    private int seatNumber;
    @NotBlank(message = "Customer name is required")
    private String customerName;

    // Unique constraint to avoid double booking
    @Column(unique = true)
    private String seatKey; // format: showtimeId-seatNumber

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Showtime getShowtime() { return showtime; }
    public void setShowtime(Showtime showtime) { this.showtime = showtime; }
    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getSeatKey() { return seatKey; }
    public void setSeatKey(String seatKey) { this.seatKey = seatKey; }
}
