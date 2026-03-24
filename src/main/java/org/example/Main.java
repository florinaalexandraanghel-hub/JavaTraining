package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

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
        loadClients(clients, "transactions.csv");

        Terminal terminal=Terminal.loadTerminalConfig("terminalConfig.csv");
        terminal.deposit(clients.get(1));
        terminal.deposit(clients.get(0));
        terminal.deposit(clients.get(2));


        terminal.withdraw(clients.get(1));
        terminal.withdraw(clients.get(0));
        terminal.withdraw(clients.get(2));


    }
}


