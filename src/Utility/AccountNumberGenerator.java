package Utility;

public class AccountNumberGenerator {
    private static int counter = 1;
    public static int generateAccountNumber() {
        return  counter++;
    }
}
