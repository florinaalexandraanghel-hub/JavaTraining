package org.example;

import java.util.HashMap;
import java.util.Map;

public class Cash {
    //public static final double WITHDRAWL_FEE=0.05;
    private Map<Integer,Integer> coins;
    private Map<Integer,Integer> banknotes;

    Cash(Map<Integer,Integer> coins, Map<Integer,Integer> banknotes){
        this.coins=coins;
        this.banknotes=banknotes;
    }

    public Map<Integer, Integer> getCoins() {
        return coins;
    }

    public void setCoins(Map<Integer, Integer> coins) {
        this.coins = coins;
    }

    public Map<Integer, Integer> getBanknotes() {
        return banknotes;
    }

    public void setBanknotes(Map<Integer, Integer> banknotes) {
        this.banknotes = banknotes;
    }

    public double getSumCoins(){
        return coins.entrySet().stream().mapToDouble(value -> value.getKey()* value.getValue()).sum();
    }
    public double getSumBanknotes(){
        return banknotes.entrySet().stream().mapToInt(value -> value.getKey()* value.getValue()).sum();
    }

    public double total(){
        return getSumCoins()/100.0+getSumBanknotes();
    }

    public void updateAfterWithdraw(Map<Integer,Integer> newBanknotes, Map<Integer,Integer> newCoins){
        banknotes.replaceAll((b,c)->0);
        coins.replaceAll((b,c)->0);

        newBanknotes.forEach((value, quant)->banknotes.merge(value, quant, Integer::sum));
        newCoins.forEach((value, quant)->coins.merge(value, quant, Integer::sum));
    }
}
