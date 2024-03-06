package com.bhakti.bktapijava2.controller;

import com.bhakti.bktapijava2.dto.RequestResponseDTO;
import com.bhakti.bktapijava2.dto.query_result.TandaTerimaWithKdPlgAndNmPlgDTO;
import com.bhakti.bktapijava2.entity.TandaTerima;
import com.bhakti.bktapijava2.service.ITandaTerimaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/tanda-terima")
@Slf4j
public class TandaTerimaController {

    @Autowired
    private ITandaTerimaService tandaTerimaService;

    @GetMapping("/merge-not-merged-pdf")
    public ResponseEntity<RequestResponseDTO<Void>> mergeNotMergedPdf() {
        tandaTerimaService.mergePdfByNoKwitansi();

        RequestResponseDTO<Void> response = new RequestResponseDTO<>();
        response.setMessage("Merge PDF Sukses");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/rename-pdf-files-ke-nomor-faktur-test-only")
    public ResponseEntity<Void> renamePDFFileName() {
        String folderPath = "D:\\bkt_dev_ebilling\\pdf_scanfaktur\\2022\\12";
        List<TandaTerimaWithKdPlgAndNmPlgDTO> tandaTerimaList = tandaTerimaService.getPdfNotMergedTandaTerima();

        List<String> newNoFakturList = tandaTerimaList.stream()
                .map(x -> x.getNoFaktur().replace("/", "_")).toList();

        File folder = new File(folderPath);

        File[] files = folder.listFiles();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    String oldName = file.getName();
                    String newName = newNoFakturList.get(i) + ".pdf";
                    File newFile = new File(folder, newName);

                    if (file.renameTo(newFile)) {
                        System.out.println("Renamed file: " + oldName + " to " + newName);
                    } else {
                        System.out.println("Failed to rename file: " + oldName);
                    }
                }
            }
//            for (File file : files) {
//                if (file.isFile()) {
//                    String oldName = file.getName();
//                    String newName = generateNewFileName(oldName); // Replace with your custom logic for renaming
//                    File newFile = new File(folder, newName);
//
//                    if (file.renameTo(newFile)) {
//                        System.out.println("Renamed file: " + oldName + " to " + newName);
//                    } else {
//                        System.out.println("Failed to rename file: " + oldName);
//                    }
//                }
//            }
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/rename-pdf-files-ke-nomor-sj-test-only")
    public ResponseEntity<Void> renamePDFFileNameSJ() {
        String folderPath = "D:\\bkt_dev_ebilling\\pdf_efaktur\\2022\\sparepart";
        List<TandaTerimaWithKdPlgAndNmPlgDTO> tandaTerimaList = tandaTerimaService.getPdfNotMergedTandaTerima();

        List<String> noFakturPajakList = tandaTerimaList.stream()
                .map(x -> x.getNoFakturPajak().replace("/", "_")).toList();

        File folder = new File(folderPath);

        File[] files = folder.listFiles();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    String oldName = file.getName();
                    String newName = noFakturPajakList.get(i) + ".pdf";
                    File newFile = new File(folder, newName);

                    if (file.renameTo(newFile)) {
                        System.out.println("Renamed file: " + oldName + " to " + newName);
                    } else {
                        System.out.println("Failed to rename file: " + oldName);
                    }
                }
            }
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/create-random-pdf-test-purpose")
    public ResponseEntity<Void> createRandomPDF() {
        String baseTemplate = "FK_018598896038000-%s_018033019215000-09122022_JKTFP20221201460_03a28e4a-8b4c-4dec-a0c5-6e1c27061803_signed";

        List<String> replacementProducts = List.of(
                "010.002-22.30814529", "010.002-22.30814530", "010.002-22.30967376", "010.002-22.30967354",
                "010.002-22.30967377", "010.002-22.30967355", "010.002-22.30967356", "010.002-22.30967233",
                "010.002-22.30967237", "010.002-22.30967191", "010.002-22.30967192", "010.002-22.30967353",
                "010.002-22.30814639", "010.002-22.30814640", "010.002-22.30814641", "010.002-22.30814642",
                "010.002-22.30814751", "010.002-22.30814752", "010.002-22.30814374", "010.002-22.30814375",
                "010.002-22.30814376", "010.002-22.30814602", "010.002-22.30814426", "010.002-22.30814371",
                "010.002-22.30814372", "010.002-22.30814373", "010.002-22.30814636", "010.002-22.30814637"
        );

        List<String> replacementSpareparts = List.of(
                "010.002-22.30814638", "010.002-22.30814398", "010.002-22.30814399", "010.002-22.30814400",
                "010.002-22.30814401", "010.002-22.30814395", "010.002-22.30814396", "010.002-22.30814397",
                "010.002-22.30967378", "010.002-22.30904101", "010.002-22.30904102", "010.002-22.30904103",
                "010.002-22.30967325", "010.002-22.30967360", "010.002-22.30967361", "010.002-22.30967362",
                "010.002-22.30967238", "010.002-22.30967363", "010.002-22.30967364", "010.002-22.30967365",
                "010.002-22.30967366", "010.002-22.30967357", "010.002-22.30967358", "010.002-22.30967359",
                "010.002-22.30967285", "010.002-22.30967286", "010.002-22.30967287", "010.002-22.30967289",
                "010.002-22.30967288", "010.002-22.30967322", "010.002-22.30967323", "010.002-22.30967324",
                "010.002-22.30967282", "010.002-22.30967283", "010.002-22.30967284", "010.002-22.30967368",
                "010.002-22.30967381", "010.002-22.30967382", "010.002-22.30967234", "010.002-22.30967235",
                "010.002-22.30967239", "010.002-22.30967240", "010.002-22.30904100", "010.002-22.30967379",
                "010.002-22.30967367", "010.002-22.30967380"
        );

        List<String> generatedTemplates = new ArrayList<>();

        for (String replacement : replacementSpareparts) {
            String generatedTemplate = String.format(baseTemplate, replacement.replace(".", "").replace("-", ""));
            generatedTemplates.add(generatedTemplate);
        }

        for (int i = 0; i < 46; i++) { // 46 dan 28
            String content = generateRandomString(50);
            createPDF(generatedTemplates.get(i) + ".pdf", content);
        }

        return ResponseEntity.ok().build();
    }

    private static void createPDF(String fileName, String content) {
        String directoryPath = "D:\\bkt_dev_ebilling\\pdf_efaktur\\2022\\sparepart\\";

        try {
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);

            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.beginText();
            contents.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contents.newLineAtOffset(100, 700);
            contents.showText(content);
            contents.endText();
            contents.close();

            doc.save(directoryPath + fileName);
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateRandomString(int targetStringLength) {
        int leftLimit = 97; // 'a'
        int rightLimit = 122; // 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    private static String generateNewFileName(String oldFileName) {

        return "new_" + oldFileName;
    }

}
