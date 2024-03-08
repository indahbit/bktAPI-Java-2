package com.bhakti.bktapijava2;

import com.bhakti.bktapijava2.exception.GlobalHandledErrorException;
import com.bhakti.bktapijava2.service.impl.TandaTerimaService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public class StringParseTests {

    @Test
    public void testExtractNoFakturPajakValidInput() {
        TandaTerimaService yourClass = new TandaTerimaService(); // Replace with the actual name of your class

        String inputString = "FK_018598896038000-0700102215615731_018033019215000-09122022_JKTFP20221201460";

        try {
            String result = yourClass.getNoFakturPajakFromFakturPajakFileName(inputString);
            assertEquals("0700102215615731", result);
        } catch (GlobalHandledErrorException e) {
            fail("Unexpected GlobalHandledErrorException for a valid input string");
        }
    }

    @Test
    public void testExtractNoFakturPajakInvalidInputMissingUnderscore() {
        TandaTerimaService yourClass = new TandaTerimaService(); // Replace with the actual name of your class

        String inputString = "InvalidInputWithoutUnderscore";

        try {
            yourClass.getNoFakturPajakFromFakturPajakFileName(inputString);
            fail("Expected GlobalHandledErrorException for missing '_' in the input string");
        } catch (GlobalHandledErrorException e) {
            assertEquals("Invalid format: Missing '_' in the input string", e.getMessage());
        }
    }

    @Test
    public void testExtractNoFakturPajakInvalidInputMissingMinusInSecondPart() {
        TandaTerimaService yourClass = new TandaTerimaService(); // Replace with the actual name of your class

        String inputString = "InvalidI_nputWithoutMinusInSecondPart";

        try {
            yourClass.getNoFakturPajakFromFakturPajakFileName(inputString);
            fail("Expected GlobalHandledErrorException for missing '-' in the second part");
        } catch (GlobalHandledErrorException e) {
            assertEquals("Invalid format: Missing '-' in the second part", e.getMessage());
        }
    }

    /**
     * Tujuan Test :
     * Karakter titik dan spasi diganti ke single underscore
     * Karakter titik diganti ke single underscore
     * Karakter / diganti ke single underscore
     * Karakter , diganti ke single underscore
     * Karakter ' diganti ke empty
     * Karakter spasi diganti ke single underscore
     */
    @Test
    public void testRemoveSpecialCharacterForPartnerName() {
        TandaTerimaService yourClass = new TandaTerimaService();

        String inputString = "PT. ACE HARD/WARE IN,DON'ESIA TBK";

        try {
            String result = yourClass.removeSpecialCharacterForPartnerName(inputString);
            log.info(result);

            assertEquals("PT_ACE_HARD_WARE_IN_DONESIA_TBK", result);
        } catch (GlobalHandledErrorException e) {
            fail("Expected GlobalHandledErrorException for missing '-' in the second part");

        }
    }

}
