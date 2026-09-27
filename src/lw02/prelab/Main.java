package lw02.prelab;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        LinkedList<String[]> transactions = new LinkedList<>();

        LinkedList<String[]> customers = new LinkedList<>();

        Stack<String[]> failedTransactions = new Stack<>();

        Scanner scanner = new Scanner(Main.class.getResourceAsStream("transactions.txt"));

        while (scanner.hasNextLine()) {
            String name = scanner.next();
            String type = scanner.next();
            String amount = scanner.next();

            String[] transaction = {name, type, amount};
            transactions.add(transaction);

            boolean found = false;

            for (String[] customer : customers) {
                if (customer[0].equals(name)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                String[] customer = {name, "0"};
                customers.add(customer);
            }
        }

        scanner.close();

        Queue<String[]> queue = new LinkedList<>();

        while (!transactions.isEmpty()) {
            queue.offer(transactions.removeFirst());
        }

        while (!queue.isEmpty()) {

            String[] transaction = queue.poll();

            String name = transaction[0];
            String type = transaction[1];
            int amount = Integer.parseInt(transaction[2]);

            // Mencari customer
            for (String[] customer : customers) {

                if (customer[0].equals(name)) {

                    int balance = Integer.parseInt(customer[1]);

                    if (type.equals("DEPOSIT")) {

                        balance += amount;
                        customer[1] = String.valueOf(balance);

                    } else if (type.equals("WITHDRAW")) {

                        if (amount > balance) {

                            // Withdrawal gagal
                            failedTransactions.push(transaction);

                        } else {

                            balance -= amount;
                            customer[1] = String.valueOf(balance);
                        }
                    }

                    break;
                }
            }
        }

        System.out.println("=== Final Balances ===");

        for (String[] customer : customers) {
            System.out.println(customer[0] + " : " + customer[1]);
        }

        System.out.println("=== Failed Transactions ===");

        while (!failedTransactions.isEmpty()) {

            String[] transaction = failedTransactions.pop();

            System.out.println(transaction[0] + " "+ transaction[1] + " "+ transaction[2]);
        }
    }
}