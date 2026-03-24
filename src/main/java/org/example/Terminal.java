package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Terminal{
   private List<Client> clients;
   private Map<Integer,Integer> totalCoins;
   private Map<Integer,Integer> totalBanknotes;
   private double maxDeposit;
   private double maxWithdraw;
   private boolean isReceip;

   Terminal(double maxDeposit, double maxWithdraw, boolean isReceip){
       this.maxDeposit  = maxDeposit;
       this.maxWithdraw = maxWithdraw;
       this.isReceip    = isReceip;
       this.totalCoins     = new LinkedHashMap<>();
       this.totalBanknotes = new LinkedHashMap<>();
       this.clients        = new ArrayList<>();
   }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Map<Integer, Integer> getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(Map<Integer, Integer> totalCoins) {
        this.totalCoins = totalCoins;
    }

    public Map<Integer, Integer> getTotalBanknotes() {
        return totalBanknotes;
    }

    public void setTotalBanknotes(Map<Integer, Integer> totalBanknotes) {
        this.totalBanknotes = totalBanknotes;
    }

    public double getMaxDeposit() {
        return maxDeposit;
    }

    public void setMaxDeposit(double maxDeposit) {
        this.maxDeposit = maxDeposit;
    }

    public double getMaxWithdraw() {
        return maxWithdraw;
    }

    public void setMaxWithdraw(double maxWithdraw) {
        this.maxWithdraw = maxWithdraw;
    }

    public boolean isReceip() {
        return isReceip;
    }

    public void setReceip(boolean receip) {
        isReceip = receip;
    }

    static Terminal loadTerminalConfig(String cale){
        try(var fisier=new BufferedReader(new FileReader(cale))) {
            fisier.readLine();
            String linie=fisier.readLine();

            var valori=linie.split(",");
            double maxDeposit=Double.parseDouble(valori[0].trim());
            double maxWithdraw=Double.parseDouble(valori[1].trim());
            boolean isReceip=valori[2].trim().equals("1");


            return new Terminal(maxDeposit, maxWithdraw, isReceip);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int totalSumCoins(){
       return totalCoins.entrySet().stream().mapToInt(value -> value.getKey()* value.getValue()).sum();
    }

    public int totalSumBanknotes(){
        return totalBanknotes.entrySet().stream().mapToInt(value -> value.getKey()* value.getValue()).sum();
    }

    public double totalTerminal(){
       return (double) totalSumCoins() /100 + totalSumBanknotes();
    }

    public void deposit(Client client){
       if(client.getCash().total()<=maxDeposit){
            client.getCash().getCoins().forEach((value, amount) ->totalCoins.merge(value, amount,Integer::sum));
            client.getCash().getBanknotes().forEach((value, amount)->totalBanknotes.merge(value, amount,Integer::sum));
            System.out.println("The amount deposited: " + client.getCash().total());
            if(isReceip) {
                Receipt receipt = new Receipt(client, true);
                receipt.writeReceipt("Receipt_DEPOSIT_" + receipt.getReceiptNumber() + ".txt");
            }
       }
       else {
           System.out.println("The amount you wanted to deposit is too large.");
           Receipt receipt = new Receipt(client, false);
           receipt.writeReceipt("Receipt_DEPOSIT_FAILED_" + receipt.getReceiptNumber() + ".txt");
       }
    }

    public void withdraw(Client client) {
        System.out.println("The amount deposited: " + client.getCash().total());
        System.out.printf("The amount you want to withdraw: ");
        Scanner input = new Scanner(System.in);
        String inputStr = input.next();
        double sumWithdraw = Double.parseDouble(inputStr.replace(",", "."));
        if(sumWithdraw>maxWithdraw)
            System.out.println("The amount you want to withdraw is greater than the allowed amount.");
        else {
            if (sumWithdraw > client.getCash().total()) {
                System.out.println("The value you entered is greater than the deposited value.");
                Receipt receipt = new Receipt(client, false);
                receipt.writeReceipt("Receipt_WITHDRAW_FAILED_" + receipt.getReceiptNumber() + ".txt");
            }

            else if (sumWithdraw < client.getCash().total()) {
                System.out.println("The value you entered is less than the deposited value.");
                Receipt receipt = new Receipt(client, false);
                receipt.writeReceipt("Receipt_WITHDRAW_FAILED_" + receipt.getReceiptNumber() + ".txt");
            }
            else {
                int remainAmount = (int) Math.round(sumWithdraw * 100);
                Map<Integer, Integer> banknotesSorted = new TreeMap<>(Collections.reverseOrder());
                banknotesSorted.putAll(totalBanknotes);
                Map<Integer, Integer> coinsSorted = new TreeMap<>(Collections.reverseOrder());
                coinsSorted.putAll(totalCoins);

                Map<Integer, Integer> givenBanknotes = new LinkedHashMap<>();
                Map<Integer, Integer> givenCoins = new LinkedHashMap<>();

                for (Map.Entry<Integer, Integer> entry : banknotesSorted.entrySet()) {
                    int valCash = entry.getKey() * 100;
                    int numCash = entry.getValue();

                    if (remainAmount >= valCash && numCash > 0) {
                        int finalVal = Math.min(remainAmount / valCash, numCash);
                        remainAmount -= valCash * finalVal;
                        banknotesSorted.put(entry.getKey(), numCash - finalVal);
                        givenBanknotes.put(entry.getKey(), finalVal);
                    }
                }

                for (Map.Entry<Integer, Integer> entry : coinsSorted.entrySet()) {
                    int valCash = entry.getKey();
                    int numCash = entry.getValue();

                    if (remainAmount >= valCash && numCash > 0) {
                        int finalVal = Math.min(remainAmount / valCash, numCash);
                        remainAmount -= valCash * finalVal;
                        coinsSorted.put(entry.getKey(), numCash - finalVal);
                        givenCoins.put(entry.getKey(), finalVal);
                    }
                }

                if (remainAmount != 0) {
                    System.out.println("Can't return exact amount. Transaction failed.");
                } else {
                    totalBanknotes.putAll(banknotesSorted);
                    totalCoins.putAll(coinsSorted);
                    client.getCash().updateAfterWithdraw(givenBanknotes, givenCoins);
                    if(isReceip) {
                        Receipt receipt = new Receipt(client, true);
                        receipt.writeReceipt("Receipt_WITHDRAW_" + receipt.getReceiptNumber() + ".txt");
                    }

                }
            }
        }
    }
}
