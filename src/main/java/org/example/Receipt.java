package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class Receipt {
    private static int inc=123005;
    private int receiptNumber;
    private LocalDate dateReceipt;
    private boolean receiptStatus;
    private Client c;

    Receipt(Client c, boolean receiptStatus){
        this.receiptNumber=inc++;
        this.dateReceipt= LocalDate.now();
        this.receiptStatus=receiptStatus;
        this.c=c;

    }

    public int getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(int receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public LocalDate getDateReceipt() {
        return dateReceipt;
    }

    public void setDateReceipt(LocalDate dateReceipt) {
        this.dateReceipt = dateReceipt;
    }

    public boolean isReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(boolean receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public Client getC() {
        return c;
    }

    public void setC(Client c) {
        this.c = c;
    }

    public void afisare(){
        System.out.printf("Nr chitanta: "+receiptNumber);
        System.out.printf("Clientul: "+c.getFirstName(), " "+c.getLastName());
        System.out.printf("Data tranzactiei: "+dateReceipt);
        System.out.println("Suma: "+c.getCash().total());
        System.out.println("Banknotes: ");
        for(Map.Entry<Integer,Integer> entry: c.getCash().getBanknotes().entrySet()){
            if(entry.getValue()!=0)
                System.out.println(entry.getKey()+" x "+entry.getValue());
        }
        System.out.println("Coins: ");
        for(Map.Entry<Integer,Integer> entry: c.getCash().getCoins().entrySet()){
            if(entry.getValue()!=0)
                System.out.println(entry.getKey()+" x "+entry.getValue());
        }
        if(receiptStatus){
            System.out.println("Tranzactie reusita");
        }
        else
            System.out.println("Tranzactie refuzata");
    }

    public void writeReceipt(String cale) {
        try (var write = new BufferedWriter(new FileWriter(cale))) {

            String status = isReceiptStatus() ? "Successful transaction" : "Transaction failed";
            if(isReceiptStatus()) {
                write.write("Receipt number: " + getReceiptNumber());
                write.newLine();
                write.write("Date: " + getDateReceipt().toString());
                write.newLine();
                write.write("Client: " + getC().getFirstName() + " " + getC().getLastName());
                write.newLine();
                write.write("Status: " + status);
                write.newLine();
                write.write("Amount: " + getC().getCash().total());
                write.newLine();

                write.write("Banknotes: ");
                write.newLine();
                for (Map.Entry<Integer, Integer> entry : getC().getCash().getBanknotes().entrySet()) {
                    if (entry.getValue() != 0) {
                        write.write(entry.getKey() + " x " + entry.getValue());
                        write.newLine();
                    }
                }

                write.write("Coins: ");
                write.newLine();
                for (Map.Entry<Integer, Integer> entry : getC().getCash().getCoins().entrySet()) {
                    if (entry.getValue() != 0) {
                        write.write(entry.getKey() + " x " + entry.getValue());
                        write.newLine();
                    }
                }

                write.write("---");
                write.newLine();
            }else {
                write.write("Receipt number: " + getReceiptNumber());
                write.newLine();
                write.write("Date: " + getDateReceipt().toString());
                write.newLine();
                write.write("Client: " + getC().getFirstName() + " " + getC().getLastName());
                write.newLine();
                write.write("Status: " + status);
                write.newLine();

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
