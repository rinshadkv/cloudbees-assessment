package com.cloudbees.trainbooking.Controller;

import com.cloudbees.trainbooking.DTO.PurchaseDTO;
import com.cloudbees.trainbooking.DTO.UpdateDTO;
import com.cloudbees.trainbooking.Domain.Ticket;
import com.cloudbees.trainbooking.Repository.TicketRepository;
import com.cloudbees.trainbooking.Service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketRepository ticketRepository;

    public TicketController(TicketService ticketService,
                            TicketRepository ticketRepository) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
    }

    @PostMapping("/purchase")
    public Ticket purchaseTicket(@RequestBody PurchaseDTO purchaseDTO) {
        return ticketService.purchaseTicket(purchaseDTO);
    }

    @GetMapping("/receipt/{userId}")
    public Ticket getReceipt(@PathVariable Long userId) {
        if (userId == null) {
            throw new RuntimeException("userId cannot be null ");
        }
        return ticketService.getReceipt(userId);

    }

    @GetMapping("/section/{section}")
    public List<Ticket> viewUsersBySection(@PathVariable String section) {
        if (section == null) {
            throw new RuntimeException("section cannot be null");
        }
        return ticketService.viewUsersBySection(section);
    }

    @DeleteMapping("/remove/{userId}")
    public void removeUser(@PathVariable Long userId) {
        if (userId == null) {
            throw new RuntimeException("userId cannot be null ");
        }
        ticketService.removeUser(userId);
    }

    @PutMapping("{ticketId}")
    public Ticket modifySeat(@RequestBody UpdateDTO updateDTO, @PathVariable Long
            ticketId) {
        if (ticketId == null) {
            throw new RuntimeException("ticketId cannot be null ");
        }
        return ticketService.modifySeat(ticketId, updateDTO);
    }

    @GetMapping("")
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
