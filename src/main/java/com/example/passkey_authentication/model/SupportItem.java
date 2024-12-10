package com.example.passkey_authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all fields
@Data
public class SupportItem {
    private String itemName;
    private int count;
    private boolean isRequired;
}