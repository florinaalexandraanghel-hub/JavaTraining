package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static void loadClients(List<Client> clientList, String cale) {
        try (var fisier = new BufferedReader(new FileReader(cale))) {
            fisier.readLine();
            String linie;

            while ((linie = fisier.readLine()) != null) {
                var valori = linie.split(",");

                String firstName = valori[0].trim();
                String lastName = valori[1].trim();

                Map<Integer, Integer> coins = new LinkedHashMap<>();
                int[] coinValues = {1, 5, 10, 50};
                for (int i = 0; i < coinValues.length; i++)
                    coins.put(coinValues[i], Integer.parseInt(valori[2 + i].trim()));

                Map<Integer, Integer> banknotes = new LinkedHashMap<>();
                int[] banknoteValues = {1, 5, 10, 20, 50, 100, 200, 500};
                for (int i = 0; i < banknoteValues.length; i++)
                    banknotes.put(banknoteValues[i], Integer.parseInt(valori[6 + i].trim()));

                clientList.add(new Client(firstName, lastName, new Cash(coins, banknotes)));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    static void main() {
        List<Client> clients = new ArrayList<>();

        //list of customers who deposited
        loadClients(clients, "src/main/resources/transactions.csv");
        for (Client c : clients) {
            System.out.println(c.getFirstName() + " " + c.getLastName() +
                    " → " + c.getCash().total() + " lei");
        }
        Terminal seif = new Terminal();
        seif.deposit(clients.get(1));
        System.out.println("Total seif: " + seif.totalTerminal() + " lei");

    }
}


/*
        Cash c1=new Cash(10,4);
        Cash c2=new Cash(0.5,4);
        Terminal terminal=new Terminal();
        List<Cash> lista1=new ArrayList<>();
        lista1.add(c1);
        lista1.add(c2);
        terminal.setBalanceClient(lista1);
        System.out.println(terminal.getBalanceClient());
        Cash c3=new Cash(0.5,4);
        lista1.add(c3);
        terminal.setBalanceClient(lista1);
        System.out.println(terminal.getBalanceClient());
        Cash c4=new Cash(0.5,4);
        List<Cash> lista2=new ArrayList<>();
        lista2.add(c4);
        terminal.setBalanceClient(lista2);
        System.out.println(terminal.getBalanceClient());


        2.

        Map<Integer, Integer> coins = new LinkedHashMap<>();
        coins.put(50, 2);
        Map<Integer, Integer> coins2 = new LinkedHashMap<>();
        coins2.put(50, 4);

        Map<Integer, Integer> banknotes = new LinkedHashMap<>();
        banknotes.put(50, 1);
        Map<Integer, Integer> banknotes2 = new LinkedHashMap<>();
        banknotes2.put(100, 1);


        Cash cash = new Cash(coins, banknotes);
        Cash cash2 = new Cash(coins2, banknotes2);

        Client client = new Client("Ion", "Popescu", cash);
        Client client2 = new Client("I", "P", cash2);


        Terminal seif = new Terminal();
        seif.deposit(client);
        seif.deposit(client2);

        System.out.println("Total client1: " + client.getCash().total() + " lei");
        System.out.println("Total client2: " + client2.getCash().total() + " lei");
        System.out.println("Total seif: " + seif.totalTerminal() + " lei");
*/