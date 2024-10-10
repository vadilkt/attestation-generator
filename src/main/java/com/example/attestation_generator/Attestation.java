package com.example.attestation_generator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;

@Getter
@Setter
@Entity
public class Attestation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomCandidat;
    private String nomFormation;
    private String attestationId;
    private String dateGeneration;

    Attestation(){}
    Attestation(String nomCandidat, String nomFormation, String attestationId, String dateGeneration){
        this.nomCandidat = nomCandidat;
        this.nomFormation = nomFormation;
        this.attestationId = attestationId;
        this.dateGeneration = dateGeneration;
    }
}
