package com.example.attestation_generator;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AttestationService {
    @Autowired
    AttestationRepository attestationRepository;
    public ByteArrayOutputStream createAttestationPdf(String nomCandidat, String nomFormation) throws IOException{
        ClassPathResource templatePath = new ClassPathResource("Attestation_Chillo.pdf");
        ClassPathResource fontRegularResource = new ClassPathResource("Montserrat-SemiBold.otf");
        ClassPathResource fontBoldResource = new ClassPathResource("Montserrat-Bold.otf");

        String attestationId ="ID: "+System.currentTimeMillis();
        String currentDate = new SimpleDateFormat("dd MMMM yyyy").format(new Date());

        PdfFont fontRegular = PdfFontFactory.createFont(fontRegularResource.getPath());
        PdfFont fontBold = PdfFontFactory.createFont(fontBoldResource.getPath());
        float largeurPDF= 1575.36f;

        Attestation attestation = new Attestation(nomCandidat, nomFormation, attestationId, currentDate);
        attestationRepository.save(attestation);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfReader reader = new PdfReader(templatePath.getInputStream());
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(reader, writer);

        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());

        float fontSize = 40f;
        float textWidthNomCandidat = fontBold.getWidth(nomCandidat, fontSize);
        float positionXNomCandidat = (largeurPDF - textWidthNomCandidat)/2;
        canvas.beginText();
        canvas.setFontAndSize(fontBold, fontSize);
        canvas.moveText(positionXNomCandidat, 500.2f);
        canvas.showText(nomCandidat);
        canvas.endText();

        float fontSizeFormation = 30.01f;
        float textWidthFormation = fontBold.getWidth(nomFormation, fontSizeFormation);
        float positionXFormation = (largeurPDF - textWidthFormation)/2;
        canvas.beginText();
        canvas.setFontAndSize(fontBold, fontSizeFormation);
        canvas.moveText(positionXFormation, 380f);
        canvas.showText(nomFormation);
        canvas.endText();

        canvas.beginText();
        canvas.setFontAndSize(PdfFontFactory.createFont(), 14);
        canvas.moveText(1176.56, 1046.99);
        canvas.showText(attestationId);
        canvas.endText();

        canvas.beginText();
        canvas.setFontAndSize(fontRegular, 16);
        canvas.moveText(250.56, 220.68);
        canvas.showText(currentDate);
        canvas.endText();

          pdfDoc.close();
        return outputStream;
    }
}
