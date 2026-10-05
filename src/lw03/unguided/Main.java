package lw03.unguided;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(Main.class.getResourceAsStream("enrollment.txt"));
        Map<String, Integer> enrollMap = new LinkedHashMap<>();
        int rejected = 0;
        String[] result = new String[10];
        int count = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            int space = line.indexOf(" ");
            String type = line.substring(0, space);
            String details = line.substring(space + 1);

            if (type.equals("REGISTER")) {
                int space2 = details.indexOf(" ");
                String courseCode = details.substring(0, space2);
                int qty = Integer.parseInt(details.substring(space2 + 1));

                if (qty > 0) {
                    int currentEnroll = 0;

                    if (enrollMap.containsKey(courseCode)) {
                        currentEnroll = enrollMap.get(courseCode);
                    }

                    enrollMap.put(courseCode,currentEnroll + qty);
                } else {
                    rejected++;
                }
            } else if (type.equals("WITHDRAW")) {
                int secondSpace = details.indexOf(" ");
                String courseCode = details.substring(0, secondSpace);
                int qty = Integer.parseInt(details.substring(secondSpace + 1));

                if (qty > 0 && enrollMap.containsKey(courseCode)) {                    
                    int currentEnroll = enrollMap.get(courseCode);

                    if (currentEnroll >= qty) {
                        enrollMap.put(courseCode, currentEnroll - qty);
                    } else {
                        rejected++;
                    }
                } else {
                    rejected++;
                }
            } else if (type.equals("CHECK")) {
                String courseCode = details;

                if (enrollMap.containsKey(courseCode)) {
                    result[count] = courseCode + ": " + enrollMap.get(courseCode) + " students";
                } else {
                    result[count] = courseCode + ": Not found";
                }

                count++;
            }
        }

        // Enrollment Check
        System.out.println("===== Enrollment Checks =====");
        for (int i = 0; i < count; i++) {
            System.out.println(result[i]);
        }

        // Final Enrollment
        System.out.println("\n===== Final Enrollment =====");
        for (String courseCode : enrollMap.keySet()) {
            System.out.println(courseCode + ": "+ enrollMap.get(courseCode)+ " students");
        }

        System.out.println("\nRejected operations: " + rejected);
    }
}