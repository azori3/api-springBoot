package com.example.application.Invoice;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Invoice {

    @Id
    @GeneratedValue
    private Long id;

    private String idFacture;
    private String facture;

    private String type;

    private String dateFacture;

    private String datePayment;

    private String Mode;

    private  double soldFactorization;

    private double soldPayment;
}
