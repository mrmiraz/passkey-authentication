package com.example.passkey_authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all fields
@Data
public class Event {
    private String subject;
    private LocalDate dateOfEvent;
    private LocalTime startTime;
    private LocalTime endTime;
    private String organizedBy;
    private int expectedParticipantCount;
    private String location;
    private List<SupportItem> requiredSupportList;
}
