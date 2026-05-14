package com.challenge.invoice_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Email(message = "Debe proporcionar un email válido")
    @NotBlank(message = "El email es obligatorio")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "La dirección es obligatoria")
    private String address;
}