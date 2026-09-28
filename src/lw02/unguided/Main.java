package lw02.unguided;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(Main.class.getResourceAsStream("borrowing.txt"));

        LinkedList<String[]> requests = new LinkedList<>();
        LinkedList<String[]> books = new LinkedList<>();
        LinkedList<String[]> members = new LinkedList<>();

        while (scanner.hasNext()) {
            String[] request = new String[2];
            request[0] = scanner.next();
            request[1] = scanner.next();
            requests.add(request);
        }

        scanner.close();

        String[] book1 = new String[]{"Kalkulus", "2"};
        String[] book2 = new String[]{"Fisika", "1"};
        String[] book3 = new String[]{"Statistika", "2"};

        books.add(book1);
        books.add(book2);
        books.add(book3);

        LinkedList<String[]> successful = new LinkedList<>();

        Queue<String[]> queue = new LinkedList<>();
        Stack<String[]> failed = new Stack<>();

        queue.addAll(requests);

        while (!queue.isEmpty()) {
            String[] request = queue.poll();

            String name = request[0];
            String bookName = request[1];

            String[] book = null;

            for (String[] data : books) {
                if (data[0].equals(bookName)) {
                    book = data;
                    break;
                }
            }

            String[] member = null;

            for (String[] data : members) {
                if (data[0].equals(name)) {
                    member = data;
                    break;
                }
            }

            if (member == null) {
                member = new String[]{name, "0"};
                members.add(member);
            }

            int stock = Integer.parseInt(book[1]);
            int borrowed = Integer.parseInt(member[1]);

            if (stock > 0 && borrowed < 2) {
                stock--;
                borrowed++;

                book[1] = String.valueOf(stock);
                member[1] = String.valueOf(borrowed);

                successful.add(request);

            } else {
                failed.push(request);
            }
        }

        System.out.println("=== Successfully Processed Requests ===");

        for (String[] request : successful) {
            System.out.println(request[0] + " " + request[1]);
        }

        System.out.println("=== Remaining Book Stock ===");

        for (String[] book : books) {
            System.out.println(book[0] + " : " + book[1]);
        }

        System.out.println("=== Failed Requests ===");

        while (!failed.isEmpty()) {
            String[] request = failed.pop();

            System.out.println(request[0] + " " + request[1]);
        }
    }
}