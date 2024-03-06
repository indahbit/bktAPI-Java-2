package com.bhakti.bktapijava2.service.impl;


import com.bhakti.bktapijava2.dto.enum_dto.OutputFolderModeEnum;
import com.bhakti.bktapijava2.dto.query_result.CofTagihanKwitansiInputOutputLocationDto;
import com.bhakti.bktapijava2.dto.query_result.TandaTerimaWithKdPlgAndNmPlgDTO;
import com.bhakti.bktapijava2.exception.GlobalHandledErrorException;
import com.bhakti.bktapijava2.repository.jdbc.ICofTagihanKwitansiJdbcRepository;
import com.bhakti.bktapijava2.repository.jdbc.ITandaTerimaJdbcRepository;
import com.bhakti.bktapijava2.service.IExternalNotificationService;
import com.bhakti.bktapijava2.service.ITandaTerimaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TandaTerimaService implements ITandaTerimaService {

    private static final Logger logger = LoggerFactory.getLogger(TandaTerimaService.class);

    @Autowired
    private ITandaTerimaJdbcRepository tandaTerimaJdbcRepository;

    @Autowired
    private ICofTagihanKwitansiJdbcRepository cofTagihanKwitansiJdbcRepository;

    @Autowired
    private IExternalNotificationService externalNotificationService;

    @Override
    public void mergePdfByNoKwitansi() {
        CofTagihanKwitansiInputOutputLocationDto pdfLocationConfig = cofTagihanKwitansiJdbcRepository.findInputAndOutputPdfPath();
        String inputRootLocation = pdfLocationConfig.getInputKwitansiMOPdfPath();
        String outputRootLocation = pdfLocationConfig.getOutputKwitansiMOPdfPath();
        List<TandaTerimaWithKdPlgAndNmPlgDTO> tandaTerimaList = getPdfNotMergedTandaTerima();

        // Init default value dari no tanda terima yang sukses atau gagal
        List<String> successMergeTandaTerimaList = new ArrayList<>();
        List<String> successMergeFakturList = new ArrayList<>();
        List<String> failedMergeTandaTerimaList = new ArrayList<>();

        Map<String, List<TandaTerimaWithKdPlgAndNmPlgDTO>> tandaTerimaGroupByNoKwitansi = tandaTerimaList.stream()
                .collect(Collectors.groupingBy(TandaTerimaWithKdPlgAndNmPlgDTO::getNoKwitansi));

        kwitansiForLabel:
        for (Map.Entry<String, List<TandaTerimaWithKdPlgAndNmPlgDTO>> noKwitansiWithTandaTerima : tandaTerimaGroupByNoKwitansi.entrySet()) {
            String noKwitansi = noKwitansiWithTandaTerima.getKey();
            List<TandaTerimaWithKdPlgAndNmPlgDTO> _tandaTerimaPerKwitansiList = noKwitansiWithTandaTerima.getValue();
            LocalDateTime tglKwitansi = _tandaTerimaPerKwitansiList.get(0).getTglKwitansi();
            LocalDate tglKwitansiLocalDate = tglKwitansi.toLocalDate();

            String outputFakturFolderPath = createOutputFolder(tglKwitansiLocalDate, OutputFolderModeEnum.FAKTUR, outputRootLocation);
            String outputSuratJalanFolderPath = createOutputFolder(tglKwitansiLocalDate, OutputFolderModeEnum.SURAT_JALAN, outputRootLocation);
            String outputFakturPajakFolderPath = createOutputFolder(tglKwitansiLocalDate, OutputFolderModeEnum.FAKTUR_PAJAK, outputRootLocation);

            String yearAndMonthPath = tglKwitansiLocalDate.getYear() + "\\" + tglKwitansiLocalDate.getMonthValue();

            //ambil file faktur
            String folderPath = inputRootLocation + "\\pdf_scanfaktur\\" + yearAndMonthPath;
            File folderFileFaktur = new File(folderPath);

            //ambil file SJ
            String sjFolderPath = inputRootLocation + "\\sj_depo\\" + yearAndMonthPath;
            File folderFileSJ = new File(sjFolderPath);

            //ambil file Faktur Pajak
            String fakturPajakSparepartFolderPath = inputRootLocation + "\\pdf_efaktur\\" + yearAndMonthPath + "\\SPAREPART";
            String fakturPajakProdukFolderPath = inputRootLocation + "\\pdf_efaktur\\" + yearAndMonthPath + "\\PRODUK";
            File folderFileProdukFakturPajak = new File(fakturPajakProdukFolderPath);
            File folderFileSparepartFakturPajak = new File(fakturPajakSparepartFolderPath);

            //region merge file
            File[] fileFakturList = folderFileFaktur.listFiles();
            File[] fileSJList = folderFileSJ.listFiles();
            File[] fileFakturPajakSparepartList = folderFileSparepartFakturPajak.listFiles();
            File[] fileFakturPajakProdukList = folderFileProdukFakturPajak.listFiles();

            // Combine the arrays using List
            List<File> combinePDFList = new ArrayList<>();
            if (fileFakturPajakSparepartList != null) {
                combinePDFList.addAll(Arrays.asList(fileFakturPajakSparepartList));
            }

            if (fileFakturPajakProdukList != null) {
                combinePDFList.addAll(Arrays.asList(fileFakturPajakProdukList));
            }

            File[] fileFakturPajakList = combinePDFList.toArray(new File[0]);

            if (fileFakturList != null) {
                Map<String, File> fileGroupByFileNameForFaktur = groupFileByFileName(fileFakturList, false);
                Map<String, File> fileGroupByFileNameForSJ = groupFileByFileName(fileSJList, false);
                Map<String, File> fileGroupByFileNameForFakturPajak = groupFileByFileName(fileFakturPajakList, true);

                PDFMergerUtility mergeFakturPdf = new PDFMergerUtility();
                PDFMergerUtility mergeSJPdf = new PDFMergerUtility();
                PDFMergerUtility mergeFakturPajakPdf = new PDFMergerUtility();

                //region siapin pdf yang akan di merge
                for (TandaTerimaWithKdPlgAndNmPlgDTO _tandaTerima : _tandaTerimaPerKwitansiList) {
                    String noFaktur = _tandaTerima.getNoFaktur();
                    String replacedUnderscoreNoFaktur = noFaktur.replace("/", "_");
                    Optional<File> fileForFaktur = Optional.ofNullable(fileGroupByFileNameForFaktur.get(replacedUnderscoreNoFaktur));

                    //region add file faktur untuk nanti nya di merge
                    if (fileForFaktur.isPresent()) {
                        try {
                            mergeFakturPdf.addSource(fileForFaktur.get());
                        } catch (FileNotFoundException e) {
                            throw new GlobalHandledErrorException(e.getMessage());
                        }
                    } else {
                        // skip no kwitansi, tidak jadi merge
                        failedMergeTandaTerimaList.add(noKwitansi + " (File Faktur tidak ditemukan)");
                        log.info("no faktur tidak tersedia, semua merge pdf untuk no kwitansi : " + noKwitansi + ". transaksi tidak dilanjutkan");
                        continue kwitansiForLabel;
                    }
                    //endregion add file faktur untuk nanti nya di merge

                    //region add file SJ untuk nanti nya di merge
                    Optional<String> _noSuratJalanOptional = Optional.ofNullable(_tandaTerima.getNoSuratJalan());

                    if (_noSuratJalanOptional.isPresent()) {
                        String _noSuratJalan = _noSuratJalanOptional.get().replace("/", "_");
                        Optional<File> fileForSJ = Optional.ofNullable(fileGroupByFileNameForSJ.get(_noSuratJalan));

                        if (fileForSJ.isPresent()) {
                            try {
                                mergeSJPdf.addSource(fileForSJ.get());
                            } catch (FileNotFoundException e) {
                                throw new GlobalHandledErrorException(e.getMessage());
                            }
                        } else {
                            // skip no kwitansi, tidak jadi merge
                            failedMergeTandaTerimaList.add(noKwitansi + " (File Surat Jalan tidak ditemukan)");
                            log.info("tidak jadi merge SJ PDF untuk no kwitansi : " + noKwitansi + ". Karena file tidak ditemukan");
                            continue kwitansiForLabel;
                        }
                    } else {
                        log.info("no SJ tidak tersedia, lanjut ke step persiapan PDF Faktur Pajak");
                    }
                    //endregion add file SJ untuk nanti nya di merge

                    //region add file Faktur Pajak untuk nantinya di merge
                    Optional<String> _noFakturPajakOptional = Optional.ofNullable(_tandaTerima.getNoFakturPajak());

                    if (_noFakturPajakOptional.isPresent()) {
                        String _noFakturPajak = _noFakturPajakOptional.get().replaceAll("[.-]", "");
                        Optional<File> fileForFakturPajak = Optional.ofNullable(fileGroupByFileNameForFakturPajak.get(_noFakturPajak));

                        if (fileForFakturPajak.isPresent()) {
                            try {
                                mergeFakturPajakPdf.addSource(fileForFakturPajak.get());
                            } catch (FileNotFoundException e) {
                                throw new GlobalHandledErrorException(e.getMessage());
                            }

                            //region menentukan suksesnya suatu faktur siap dimerge atau tidak
                            successMergeFakturList.add(noFaktur);
                            //endregion menentukan suksesnya suatu faktur siap dimerge atau tidak
                        } else {
                            // skip no kwitansi, tidak jadi merge
                            failedMergeTandaTerimaList.add(noKwitansi + " (File Faktur Pajak tidak ditemukan)");
                            log.info("tidak jadi merge SJ PDF untuk no kwitansi : " + noKwitansi + ". Karena file tidak ditemukan");
                            continue kwitansiForLabel;
                        }
                    } else {
                        // skip no kwitansi, tidak jadi merge
                        failedMergeTandaTerimaList.add(noKwitansi + " (No Faktur Pajak Tidak ditemukan)");
                        log.info("no Faktur Pajak tidak tersedia, semua merge pdf untuk kwitansi : " + noKwitansi + " tidak dilanjutkan.");
                        continue kwitansiForLabel;
                    }
                    //endregion add file Faktur Pajak untuk nantinya di merge
                }
                //endregion siapin pdf yang akan di merge

                //region persiapkan untuk di mark sebagai pdf telah digabungkan
                successMergeTandaTerimaList.add(noKwitansi);
                //endregion persiapkan untuk di mark sebagai pdf telah digabungkan

                //region lakukan merge pdf
                try {
                    String nmPlg = removeSpecialCharacterForPartnerName(_tandaTerimaPerKwitansiList.get(0).getNmPlg());
                    String kdPlg = _tandaTerimaPerKwitansiList.get(0).getKdPlg();

                    // format tanggal untuk penulisan nama file
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                    String formattedKwitansiDate = tglKwitansi.format(formatter);

                    logger.info("Start merging pdf : " + noKwitansi);

                    String namaFileFakturGroupPDF = formattedKwitansiDate + "_" + nmPlg + "_" + kdPlg + "_FAKTUR.pdf";
                    mergeFakturPdf.setDestinationFileName(outputFakturFolderPath + namaFileFakturGroupPDF);
                    mergeFakturPdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

                    logger.info("Faktur PDF Created : " + mergeFakturPdf.getDestinationFileName());

                    String namaFileSJGroupPDF = formattedKwitansiDate + "_" + nmPlg + "_" + kdPlg + "_SJ.pdf";
                    mergeSJPdf.setDestinationFileName(outputSuratJalanFolderPath + namaFileSJGroupPDF);
                    mergeSJPdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

                    logger.info("Surat Jalan PDF Created : " + mergeFakturPdf.getDestinationFileName());

                    String namaFileFakturPajakGroupPDF = formattedKwitansiDate + "_" + nmPlg + "_" + kdPlg + "_EFAKTUR.pdf";
                    mergeFakturPajakPdf.setDestinationFileName(outputFakturPajakFolderPath + namaFileFakturPajakGroupPDF);
                    mergeFakturPajakPdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

                    logger.info("Faktur Pajak PDF Created : " + mergeFakturPdf.getDestinationFileName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //endregion lakukan merge pdf

                logger.info("Sukses merging pdf");
            } else {
                failedMergeTandaTerimaList.add(noKwitansi + " (No Faktur tidak ditemukan)");
            }
            //endregion merge file
        }

        //region update flag Tanda Terima setelah pdf tercipta dgn sukses
        if (successMergeTandaTerimaList.size() > 0) {
            tandaTerimaJdbcRepository.updateMergePdfByNoFakturList(successMergeFakturList, true);
        }
        //endregion update flag Tanda Terima setelah pdf tercipta dgn sukses

        //region kirim notif ke finance untuk list job yg sukses dan gagal
        String successBody = successMergeTandaTerimaList.isEmpty() ? "Tidak Ada" : String.join(", <br />", successMergeTandaTerimaList);
        String failedBody = failedMergeTandaTerimaList.isEmpty() ? "Tidak Ada" : String.join(", <br />", failedMergeTandaTerimaList);

        String bodyEmail = "        <div style=\"text-align:center; padding: 15px;\">" +
                           "            <h1>List Kwitansi yang Success/Failed di merge</h1>" +
                           "        </div>" +
                           "        <div style=\"padding: 20px\">" +
                           "            <div>No Kwitansi Success Merge : <br />" + successBody + "</div>" +
                           "            <div><br /></div>" +
                           "            <div>No Kwitansi Gagal Merge : <br />" + failedBody + "</div>" +
                           "        </div>";

        String subject = "Notif Merge PDF FK/SJ/FP";

        externalNotificationService.sendEmailToSomebody(bodyEmail, subject, "suatu branch");
        //endregion kirim notif ke finance untuk list job yg sukses dan gagal
    }

    private Map<String, File> groupFileByFileName(File[] fileList, boolean isFakturPajakMode) {
        if (fileList != null) {
            if (isFakturPajakMode) {
                return Arrays.stream(fileList).collect(Collectors.toMap(file -> getNoFakturPajakFromFakturPajakFileName(file.getName()), file -> file));
            }

            return Arrays.stream(fileList).collect(Collectors.toMap(this::getFileNameWithoutExtension, file -> file));
        }

        return new HashMap<>();
    }

    public String getNoFakturPajakFromFakturPajakFileName(String fileName) {
        String[] parts = fileName.split("_");

        if (parts.length > 1) {
            // Taking the second part after the first underscore
            String secondPart = parts[1];

            // Splitting the second part using '-' as a delimiter
            String[] subParts = secondPart.split("-");

            if (subParts.length > 1) {
                // Extracting the value after the first minus
                return subParts[1];
            } else {
                throw new GlobalHandledErrorException("Invalid format: Missing '-' in the second part");
            }
        } else {
            throw new GlobalHandledErrorException("Invalid format: Missing '_' in the input string");
        }
    }


    private String getFileNameWithoutExtension(File file) {
        String fileNameWithExtension = file.getName();

        // cari titik sebagai tanda lokasi format file nya
        int indexTitik = fileNameWithExtension.indexOf(".");

        if (indexTitik > 0) {
            return fileNameWithExtension.substring(0, indexTitik);
        } else {
            return fileNameWithExtension;
        }
    }

    private String createOutputFolder(LocalDate tglKwitansi, OutputFolderModeEnum mode, String outputRootLocation) {
        String folderName = "";
        if (mode.equals(OutputFolderModeEnum.FAKTUR)) {
            folderName = "pdf_scanfaktur";
        } else if (mode.equals(OutputFolderModeEnum.SURAT_JALAN)) {
            folderName = "pdf_sj";
        } else if (mode.equals(OutputFolderModeEnum.FAKTUR_PAJAK)) {
            folderName = "zip_efaktur";
        } else {
            throw new GlobalHandledErrorException("Mode Tidak ditemukan, output folder tidak tahu mau di simpan kemana, transaksi tidak dapat dilanjutkan");
        }
        int year = tglKwitansi.getYear();
        int month = tglKwitansi.getMonthValue();
        log.info(String.valueOf(year), " | " , month);

        String defaultFolderPath = outputRootLocation + "\\" + folderName + "\\" + year + "\\" + month + "\\";
//        String defaultFolderPath = "D:\\test_output_ebilling\\" + folderName + "\\2022\\12\\"; // ini cth string folder lokal

        File folder = new File(defaultFolderPath);

        // Check if the folder exists, create it if not
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                log.info("Output folder is not exists, creating output folder : " + defaultFolderPath);
                return defaultFolderPath;
            } else {
                throw new GlobalHandledErrorException("Failed to create output folder!");
            }
        } else {
            return defaultFolderPath;
        }
    }

    @Override
    public List<TandaTerimaWithKdPlgAndNmPlgDTO> getPdfNotMergedTandaTerima() {
        return tandaTerimaJdbcRepository.findByMergePDFisFalse();
    }

    public String removeSpecialCharacterForPartnerName(String partnerName) {
        return partnerName.replaceAll("[/. ,]+", "_").replace("'", "");
    }

}
