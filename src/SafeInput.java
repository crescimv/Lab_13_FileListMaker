import java.util.Scanner;

public class SafeInput {
    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = "";  // Set this to zero length. Loop runs until it isn’t
        do {
            System.out.print("\n" + prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        } while(retString.length() == 0);

        return retString;

    }
    public static int getInt(Scanner pipe, String prompt) {

        int retInt=0;
        System.out.println("\n"+prompt+": ");
        while (!pipe.hasNextInt()) { //checks for non integer
            System.out.println("Invalid, please enter an integer");
            pipe.next();//discards non integer
        }
        return pipe.nextInt();
    }

    public static double getDouble(Scanner pipe, String prompt) {
        double retDouble=0;
        System.out.println("\n"+prompt+": ");
        while (!pipe.hasNextDouble()) { //checks for non double
            System.out.println("Invalid, please enter a double");
            pipe.next();//discards non double
        }
        return pipe.nextDouble();
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int retRangedInt=SafeInput.getInt(pipe, prompt);
        while ((retRangedInt < low) || (retRangedInt > high)) {
            retRangedInt=SafeInput.getInt(pipe, prompt);
        }
        return retRangedInt;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        double retRangedDouble=SafeInput.getDouble(pipe, prompt);
        while ((retRangedDouble < low) || (retRangedDouble > high)) {
            retRangedDouble=SafeInput.getDouble(pipe, prompt);
        }
        return retRangedDouble;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        System.out.println("\n"+prompt+": ");
        String answer = pipe.next();
        boolean yesNo=false;

        while (!(answer.equalsIgnoreCase("Y")||answer.equalsIgnoreCase("N"))) {
            System.out.println("Invalid, please enter [Y,N]");
            answer=pipe.next();
        }
        if (answer.equalsIgnoreCase("Y")) {
            yesNo=true;
        }
        if (answer.equalsIgnoreCase("N")) {
            yesNo=false;
        }
        return yesNo;
    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String value="";
        boolean gotAValue = false;
        while (!gotAValue) {
            System.out.println(prompt+": ");
            value=pipe.nextLine();
            if (value.matches(regEx)) {
                gotAValue=true;
            } else {
                System.out.println("\nInvalid input: "+value);
            }
        }
        return value;
    }

    public static void prettyHeader(String msg) {
        int whiteSpace = (53 - msg.length()) / 2;
        for (int x=0;x<=59;x++) {
            System.out.print("*");
        }
        System.out.print("\n***");
        for (int x=0;x<=whiteSpace;x++) {
            System.out.print(" ");
        }
        System.out.print(msg);
        for (int x=0;x<=whiteSpace;x++) {
            System.out.print(" ");
        }
        System.out.println("***");
        for (int x=0;x<= 59;x++) {
            System.out.print("*");
        }
    }
}
