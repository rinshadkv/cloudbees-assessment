package com.cloudbees.trainbooking.Repository;


import com.cloudbees.trainbooking.Domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findBySection(String section);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.section = :section")
    Long countSeatsBySection(@Param("section") String section);

    Ticket findTopBySectionOrderBySeatNumberDesc(String section);

    boolean existsBySectionAndSeatNumber(String newSection, Long newSeat);

    void deleteByUserId(Long userId);
}