package lw01.unguided;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(Main.class.getResourceAsStream("rental.txt"));

        int index = scanner.nextInt();
        Rental[] rental = new Rental [index];

        for(int i = 0; i < index; i++){
            String type = scanner.next();
            String id = scanner.next();
            int days = scanner.nextInt();
            int units = scanner.nextInt();

            if (type.equals("LAPTOP")) {
                rental[i] = new LaptopRental(id, days);
            } else if (type.equals("PROJECTOR")) {
                rental[i] = new ProjectorRental(id, days);
            }

            System.out.println(rental[i].summary());
        }
    }
}
