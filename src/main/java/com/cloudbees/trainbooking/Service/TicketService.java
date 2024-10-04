package com.cloudbees.trainbooking.Service;

import com.cloudbees.trainbooking.DTO.PurchaseDTO;
import com.cloudbees.trainbooking.DTO.UpdateDTO;
import com.cloudbees.trainbooking.Domain.Ticket;
import com.cloudbees.trainbooking.Domain.User;
import com.cloudbees.trainbooking.Repository.TicketRepository;
import com.cloudbees.trainbooking.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TicketService {

    private static final Long MAX_SEATS = 100L;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket purchaseTicket(PurchaseDTO purchaseDTO) {

        if (userRepository.existsByEmail(purchaseDTO.getUser().getEmail())) {
            throw new RuntimeException("Email already exists");
        }


        User user = new User(purchaseDTO.getUser().getFirstName(),
                purchaseDTO.getUser().getLastName(),
                purchaseDTO.getUser().getEmail());
        user = userRepository.save(user);


        String section = assignSection();
        Long seatNumber = generateSeatNumber(section);


        Ticket ticket = new Ticket();
        ticket.setFromLocation(purchaseDTO.getFrom());
        ticket.setToLocation(purchaseDTO.getTo());
        ticket.setPrice(purchaseDTO.getPrice());
        ticket.setSection(section);
        ticket.setSeatNumber(seatNumber);
        ticket.setUser(user);

        return ticketRepository.save(ticket);
    }

    public Ticket getReceipt(Long userId) {
        return ticketRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public List<Ticket> viewUsersBySection(String section) {
        if (!section.equals("A") && !section.equals("B")) {
            throw new RuntimeException("Invalid section. Only 'A' or 'B' are allowed");
        }
        return ticketRepository.findBySection(section);
    }

    public void removeUser(Long userId) {
        ticketRepository.deleteByUserId(userId);
    }

    public Ticket modifySeat(Long ticketId, UpdateDTO updateDTO) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));


        if (ticketRepository.existsBySectionAndSeatNumber(updateDTO.getSection(), updateDTO.getSeatNumber())) {
            throw new RuntimeException("Seat not available at this time, already booked");
        }


        ticket.setSection(updateDTO.getSection());
        ticket.setSeatNumber(updateDTO.getSeatNumber());

        return ticketRepository.save(ticket);
    }

    private Long generateSeatNumber(String section) {

        Ticket lastTicket = ticketRepository.findTopBySectionOrderBySeatNumberDesc(section);
        long lastBookedSeat = (lastTicket != null) ? lastTicket.getSeatNumber() : 0L;

        return lastBookedSeat + 1;
    }

    private String assignSection() {

        Long sectionACount = ticketRepository.countSeatsBySection("A");
        Long sectionBCount = ticketRepository.countSeatsBySection("B");


        return (sectionACount < sectionBCount && sectionACount < MAX_SEATS) ? "A" : "B";
    }
}
