package lw03.prelab;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        // problem 1
        System.out.println("===== Problem 1 =====");

        List<String> playlist = new ArrayList<>();

        try (Scanner scanner = new Scanner(Main.class.getResourceAsStream("playlist.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("ADD ")) {
                    String song = line.substring(4);
                    playlist.add(song);

                } else if (line.startsWith("INSERT ")) {
                    String[] parts = line.split(" ", 3);

                    int index = Integer.parseInt(parts[1]);
                    String song = parts[2];

                    playlist.add(index, song);

                } else if (line.startsWith("REMOVE ")) {
                    String song = line.substring(7);
                    playlist.remove(song);
                }
            }

        }

        System.out.println("Total songs: " + playlist.size());

        for (int i = 0; i < playlist.size(); i++) {
            System.out.println((i + 1) + ": " + playlist.get(i));
        }

        //problem 2
        System.out.println("===== Problem 2 =====");

        Set<String> participants = new LinkedHashSet<>();
        int duplicates = 0;

        try (Scanner scanner = new Scanner(Main.class.getResourceAsStream("participant.txt"))) {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();

                if (!participants.add(name)) {
                    duplicates++;
                }
            }
        }

        System.out.println("Unique participants: " + participants.size());

        int number = 1;

        for (String name : participants) {
            System.out.println(number + ". " + name);
            number++;
        }

        System.out.println("Duplicate registrations: " + duplicates);

        //problem 3
        System.out.println("===== Problem 3 =====");

        Map<String, Integer> inventory = new LinkedHashMap<>();
        int failedSales = 0;

        try (Scanner scanner = new Scanner(Main.class.getResourceAsStream("inventory.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");

                String type = parts[0];
                String product = parts[1];
                int quantity = Integer.parseInt(parts[2]);

                if (type.equals("ADD")) {

                    if (inventory.containsKey(product)) {
                        int currentStock = inventory.get(product);
                        inventory.put(product, currentStock + quantity);

                    } else {
                        inventory.put(product, quantity);
                    }

                } else if (type.equals("SELL")) {

                    if (inventory.containsKey(product)
                            && inventory.get(product) >= quantity) {

                        int currentStock = inventory.get(product);
                        inventory.put(product, currentStock - quantity);

                    } else {
                        failedSales++;
                    }
                }
            }

        }

        for (String product : inventory.keySet()) {
            System.out.println(product + ": " + inventory.get(product));
        }

        System.out.println("Failed sales: " + failedSales);
    }
}