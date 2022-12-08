package com.example.seg2105_project;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void dateAfterNumDays() {
        String expectedTrue = "17-12-2022";
        String test1 = new ChefBannedOrSuspended().dateAfterNumDays(10);

        assertEquals(expectedTrue, test1);
    }

    @Test
    public void addComplaintToDB() {
        String existingEmail = "new@chef.com";
        String nonExistingEmail = "idont@exist.com";
        String description = "This can be anything";

        assertTrue(new MakeComplaintActivity().addComplaintToDB(existingEmail, description));
        assertFalse(new MakeComplaintActivity().addComplaintToDB(nonExistingEmail, description));
    }

    @Test
    public  void validateCreditInfo() {
        String correctCardNb = "000000000000000";
        String correctExpDate = "0000";
        String correctCVV = "000";

        String wrongCardNb = "0000";
        String wrongExpDate = "00";
        String wrongCVV = "33";

        assertTrue(new RegisterClient().validateCreditInfo(correctCardNb, correctExpDate, correctCVV));

        assertFalse(new RegisterClient().validateCreditInfo(correctCardNb, wrongExpDate, correctCVV));
        assertFalse(new RegisterClient().validateCreditInfo(wrongCardNb, correctExpDate, correctCVV));
        assertFalse(new RegisterClient().validateCreditInfo(wrongCardNb, correctExpDate, wrongCVV));

    }


    @Test
    public void validateSuspension() {
        assertTrue(new HandleComplaint().validateSuspension("23"));
        assertFalse(new HandleComplaint().validateSuspension("0"));
        assertFalse(new HandleComplaint().validateSuspension(""));
        assertFalse(new HandleComplaint().validateSuspension("999"));
    }
}