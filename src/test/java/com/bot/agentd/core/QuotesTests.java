package com.bot.agentd.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuotesTests {
    Quotes qRepo;

    @BeforeEach
    public void setUp() throws Exception {
        try {
            qRepo = new Quotes();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testRepoInitiation() throws Exception {
        qRepo.repoInitiation();
        assertNotNull(qRepo.quotesRepo);
    }

    @Test
    public void testGetQuoteReturnASentence() throws Exception {
        qRepo.repoInitiation();

        //sentence = more than 1 word, mean have at least 1 space
        String quote = qRepo.getQuote();
        int spaceExistence = quote.indexOf(' ');
        boolean testValue = false;
        if (spaceExistence != -1) {
            testValue = true;
        }
        assertTrue(testValue);
    }

    @Test
    public void testGetQuoteSuccesiveCallDontReturnTheSameQuote() throws Exception {
        qRepo.repoInitiation();

        String quote1 = qRepo.getQuote();
        String quote2 = qRepo.getQuote();
        String quote3 = qRepo.getQuote();

        boolean testValue = true;

        if(quote1.equals(quote2)) {
            testValue = false;
        }
        else if(quote2.equals(quote3)) {
            testValue = false;
        }
        else if(quote1.equals(quote3)) {
            testValue = false;
        }

        assertTrue(testValue);
    }

    @Test
    public void testIsInitiatedReturnFalseIfNotInitiated() {
        assertTrue(!qRepo.isInitiated());
    }

    @Test
    public void testIsInitiatedReturnTrueIfInitiated() throws Exception {
        qRepo.repoInitiation();
        assertTrue(qRepo.isInitiated());
    }

}

