import java.io.*;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Map;

/**
* Improvements that can be made:
* 1. More rigorous error checking for bad lines.
**/
public class gift1 {

    /**
     * A private function to calculate the balances of each friend.
     * @param filename The name of the file to open.
     * @return A formatted string of their present balances.
     */
    private static String calculate(String filename) {

        Scanner scanner;

        try {
            scanner = new Scanner(new File(filename));
        } catch (Exception FileNotFoundException) {
            System.out.println("Error: Couldn't find file of name " + filename);
            return "Error: Couldn't find file of name " + filename + "\n";
        }

        // A kv map of a friend's name (key) to their balance (value).
        Map<String, Integer> friendToBalance = new LinkedHashMap<>();

        // Get the number of friends.
        int numFriends;

        if (scanner.hasNextLine()) {
            try {
                numFriends = Integer.parseInt(scanner.nextLine());
            } catch (Exception NumberFormatException){
                System.out.println("Error: File not formatted correctly; expected an integer line 1.");
                return "Error: File not formatted correctly; expected an integer line 1.\n";
            }
        }
        else {
            System.out.println("Error: File is empty.");
            return "Error: File is empty.\n";
        }

        // Set the names in the map.
        for (int i = 0; i < numFriends; i++) {
            if (scanner.hasNextLine()) {
                friendToBalance.put(scanner.nextLine(), 0);
            }
            else {
                System.out.println("Error: File not formatted correctly; expected a String name at line " + (i + 1));
                return "Error: File not formatted correctly; expected a String name at line " + (i + 1) + "\n";
            }
        }

        // Loop through the file while we still have lines left.
        while (scanner.hasNextLine()) {

            // The person who is gifting the money.
            String giver = scanner.nextLine();

            String[] numVals = scanner.nextLine().split("\\s+");

            int amount, numRecipients;
            try {
                amount = Integer.parseInt(numVals[0]);
                numRecipients = Integer.parseInt(numVals[1]);
            } catch (Exception NumberFormatException) {
                System.out.println("Error: Expected a number but got something else.");
                return "Error: Expected a number but got something else.\n";
            }

            // Take out the amount of money gifted out of the account.
            friendToBalance.put(giver, friendToBalance.get(giver) - amount);

            // Divide it and give it to each recipient.

            if (numRecipients > 0) {
                int giftAmount = amount / numRecipients;

                for (int i = 0; i < numRecipients; i++) {
                    String recipient = scanner.nextLine();
                    friendToBalance.put(recipient, friendToBalance.get(recipient) + giftAmount);
                }
            }

        }

        // Format output string.
        StringBuilder output = new StringBuilder();

        for (String name : friendToBalance.keySet()) {
            output.append(name).append(" ").append(friendToBalance.get(name)).append("\n");
        }

        System.out.println(output.toString());

        return output.toString();
    }

    public static void main(String[] args) {

        try {
            // Create and write the output file.
            File file = new File("/Users/Amber/IdeaProjects/sampleproj/src/gift1.out");
            FileWriter writer = new FileWriter(file);
            writer.write(calculate("/Users/Amber/IdeaProjects/sampleproj/src/gift1.in"));
            writer.flush();
            writer.close();

        } catch (Exception IOException) {
            System.out.println("Error: Creating & writing output file.");
            System.exit(0);
        }



  }

}
