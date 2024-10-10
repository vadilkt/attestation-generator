package com.example.attestation_generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/attestation")
public class AttestationController {

    @Autowired
    private AttestationService attestationService;

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateAttestation (@RequestParam String nomCandidat, @RequestParam String nomFormation){
        try{
            ByteArrayOutputStream outputStream = attestationService.createAttestationPdf(nomCandidat,nomFormation);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=attestation_"+nomCandidat+".pdf");
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headers)
                    .body(outputStream.toByteArray());
        }catch (IOException e){
            System.err.println("Erreur lors de la génération du PDF: "+ e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
