package org.example;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Terminal {
   private List<Client> clients;
   private Map<Integer,Integer> totalCoins;
   private Map<Integer,Integer> totalBanknotes;


   Terminal(){
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

    public int totalSumCoins(){
       return totalCoins.entrySet().stream().mapToInt(value -> value.getKey()* value.getValue()).sum();
    }

    public int totalSumBanknotes(){
        return totalBanknotes.entrySet().stream().mapToInt(value -> value.getKey()* value.getValue()).sum();
    }

    public double totalTerminal(){
       return (double) (totalSumCoins() /100) + totalSumBanknotes();
    }

    public double deposit(Client client){
       client.getCash().getCoins().forEach((value, amount)->totalCoins.merge(value, amount,Integer::sum));
       client.getCash().getBanknotes().forEach((value, amount)->totalBanknotes.merge(value, amount,Integer::sum));
       return totalTerminal();
    }
}
