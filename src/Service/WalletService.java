package Service;

import Dao.WalletDao;
import model.Account;
import model.Transaction;

import java.math.BigDecimal;
import java.util.Date;

public class WalletService {
    private WalletDao walletDao;

    public WalletService() {
        this.walletDao = new WalletDao();
    }

    public void createWallet(String name, String email, String phoneNumber, Double amount) {
        // Implement wallet creation logic here
        Account account = new Account(name, email, phoneNumber, amount);
        walletDao.getAccountMap().put(account.getAccountNumber(), account);
        System.out.println("Account created for user " + name + " with account number " + account.getAccountNumber());
    }
    public void transfer(int fromAccount, int toAccount, Double transferAmount) {
        if(!validate(fromAccount, toAccount, transferAmount)){
            return;
        }

        Transaction transaction = new Transaction(fromAccount, toAccount, transferAmount, new Date());
        Account from = walletDao.getAccountMap().get(fromAccount);
        Account to = walletDao.getAccountMap().get(toAccount);
        if(from.getBalance() < transferAmount){
            System.out.println("Insufficient balance in account " + fromAccount);
            return;
        }
        from.setBalance(from.getBalance() - transferAmount);
        to.setBalance(to.getBalance() + transferAmount);
        from.getTransactions().add(transaction);
        to.getTransactions().add(transaction);
        System.out.println("Transfer Successful");
    }
    private boolean validate(int fromAccount, int toAccount, Double transferAmount) {
     if(transferAmount.compareTo(new Double(0.0001)) < 0 ){
         System.out.println("Invalid transfer amount");
         return false;
     }
     if(fromAccount == toAccount){
         System.out.println("Transfer to same account");
         return false;
     }
     if(!walletDao.getAccountMap().containsKey(toAccount)){
         System.out.println("Invalid recipient account");
         return false;
     }
     return true;
    }
    public void statement(int accountNum){
        Account account = walletDao.getAccountMap().get(accountNum);
        if(account == null){
            System.out.println("Account not found");
            return;
        }
        System.out.println("Summary of the account number: " + accountNum);
        System.out.println("Current Balance: " + account.getBalance());
        System.out.println("Your Transaction History:");
        System.out.println(account.getTransactions());
    }
    public void overview(){
        for(int accountNum : walletDao.getAccountMap().keySet()){
            System.out.println("Balance for account number " + accountNum + ": ");
            System.out.println(walletDao.getAccountMap().get(accountNum).getBalance());
        }
    }

    public void setWalletDao(WalletDao walletDao) {
        this.walletDao = walletDao;
    }

    public WalletDao getWalletDao() {
        return walletDao;
    }
}
