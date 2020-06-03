package com.bot.agentd.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Quotes {
    ArrayList<String> quotesRepo;
    static int quotesRepoIndexPointer;
    int repoSize;

    public Quotes() {
        this.quotesRepoIndexPointer = 0;
        this.repoSize = 0;
    }

    public void repoInitiation() throws Exception{
        ArrayList<String> quotesRepo = new ArrayList<String>();

        try {
            File file = new File("src/main/java/com/bot/agentd/core/daftarQuotes.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String tmp;
            while((tmp = br.readLine()) != null) {
                quotesRepo.add(tmp);
            }

            this.quotesRepo = quotesRepo;
            this.repoSize = quotesRepo.size();
        }
        catch(Exception e) {
            System.out.println("Error");
        }
    }

    public String getQuote(){
        String quote = this.quotesRepo.get(this.quotesRepoIndexPointer);
        this.quotesRepoIndexPointer = (this.quotesRepoIndexPointer+1) % (this.repoSize);
        return quote;
    }

    public boolean isInitiated() {
        if(repoSize > 0) {
            return true;
        }
        return false;
    }

}
