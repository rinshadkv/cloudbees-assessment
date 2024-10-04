package com.cloudbees.trainbooking.DTO;

public class UpdateDTO {

    Long seatNumber;
    String section;


    public Long getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Long seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
