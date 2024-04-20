package ATMmachineUsingOOP;

import java.util.Scanner;


public class ATM {
    float balance;
    public int PIN = 1234;
    Scanner scanner = new Scanner(System.in);

    public void checkPIN() throws InterruptedException {

        System.out.println("Enter your PIN : ");

        int enteredPIN = scanner.nextInt();
        if(PIN==enteredPIN){
            menu();

        }else{
            System.out.println("Enter a valid PIN");
            System.out.println();
            System.out.println();
        }
    }

    public void menu() throws InterruptedException {
        System.out.println();
        System.out.println();
        System.out.println("Enter Your choice");
        System.out.println("1. Check A/C Balance");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Deposit Money");
        System.out.println("4. EXIT");

        int choice = scanner.nextInt();
        switch (choice){
            case 1:
                checkBalance();
                break;
            case 2:
                withdrawMoney();
                break;
            case 3:
                depositMoney();
                break;
            case 4:
                exit();
                break;
            default:
                System.out.println("Enter valid choice");
                System.out.println();
                System.out.println();
                menu();

        }

    }

    public void checkBalance() throws InterruptedException {
        System.out.println("Balance : " + balance);
        System.out.println();
        System.out.println();
        menu();
    }

    public void withdrawMoney() throws InterruptedException {
        System.out.println("Enter amount to withdraw : ");
        int withdrawAmount = scanner.nextInt();
        if(withdrawAmount > balance){
            System.out.println("Insufficient balance.");
        }else{
            balance = balance - withdrawAmount;
            System.out.println("Money withdrawl successful");
            System.out.println("Balance after withdrawl :" + balance);
        }
        System.out.println();
        System.out.println();
        menu();
    }

    public void depositMoney() throws InterruptedException {
        System.out.println("Enter amount to deposit : ");
        int depositAmount = scanner.nextInt();
        balance = balance + depositAmount;
        System.out.println("Amount deposited successfully!!");
        System.out.println("Balance after deposit : " + balance);
        System.out.println();
        System.out.println();
        menu();
    }

    public void exit() throws InterruptedException{
        System.out.print("Exiting System");
        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            Thread.sleep(400);
        }
        System.out.println();
        System.out.println("Thank You for using ATM");
    }


}
