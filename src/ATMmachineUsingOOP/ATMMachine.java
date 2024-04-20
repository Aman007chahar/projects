package ATMmachineUsingOOP;

import java.util.Scanner;

public class ATMMachine {

    public static void main(String[] args) throws InterruptedException {

        ATM atm = new ATM();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Your PIN : ");
        int password = scanner.nextInt();
        if (password == atm.PIN) {
            atm.menu();
        }

    }
}


