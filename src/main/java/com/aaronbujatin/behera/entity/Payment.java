package com.aaronbujatin.behera.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private String expirationDate;
    private String cvv;

    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;
}
