package Driver;

import Service.WalletService;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        WalletService ws = new WalletService();
        Scanner scanner = new Scanner(System.in);
        outer: while(true) {
            System.out.println("\nOPTIONS");
            System.out.println("1. Create a new wallet");
            System.out.println("2. Transfer Amount");
            System.out.println("3. Statement");
            System.out.println("4. Overview");
            System.out.println("5. Exit");
            switch (scanner.nextInt()) {
                case 1 -> {
                    System.out.println("Enter name: ");
                    String name = scanner.next();
                    System.out.println("Enter email: ");
                    String email = scanner.next();
                    System.out.println("Enter phone number: ");
                    String phoneNumber = scanner.next();
                    System.out.println("Enter initial amount: ");
                    double amount = scanner.nextDouble();
                    ws.createWallet(name, email, phoneNumber, amount);
                }
                case 2 -> {
                    System.out.println("Enter sender's account number: ");
                    int fromAccount = scanner.nextInt();
                    if(!ws.getWalletDao().getAccountMap().containsKey(fromAccount)){
                        System.out.println("Account not found");
                        continue;
                    }
                    System.out.println("Enter recipient's account number: ");
                    int toAccount = scanner.nextInt();
                    if(!ws.getWalletDao().getAccountMap().containsKey(toAccount)){
                        System.out.println("Account not found");
                        continue;
                    }
                    System.out.println("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    ws.transfer(fromAccount, toAccount, transferAmount);
                }
                case 3 -> {
                    System.out.println("Enter account number:");
                    ws.statement(scanner.nextInt());
                }
                case 4 -> {
                    System.out.println("Overview");
                    ws.overview();
                }
                case 5 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> {
                    System.out.println("Invalid option. Please try again.");
                    continue outer;
                }
            }
        }
    }
}