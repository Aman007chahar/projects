package bankManagementUsingJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private Connection connection;
    private Scanner scanner;
    AccountManager(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void creditMoney(long account_number) throws SQLException{
        scanner.nextLine();
        System.out.println("Enter Amount");
        double creditAmount = scanner.nextDouble();
        System.out.println("Enter password : ");
        String password = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            if (account_number!=0) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?");
                preparedStatement.setLong(1,account_number);
                preparedStatement.setString(2,password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    String creditQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(creditQuery);
                    preparedStatement1.setDouble(1,creditAmount);
                    preparedStatement1.setString(2,password);

                    int rowsAffected = preparedStatement1.executeUpdate();

                    if(rowsAffected > 0){
                        System.out.println("Amount " + creditAmount + "credited successfully");
                        connection.commit();
                        connection.setAutoCommit(true);
                    }else{
                        throw new RuntimeException("Transaction Failed");
                    }
                }

                System.out.println("Enter correct password.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        connection.setAutoCommit(true);

    }

    public void debitMoney(long account_number) throws SQLException{
        scanner.nextLine();
        System.out.println("Enter Amount : ");
        int debitAmount = scanner.nextInt();
        System.out.println("Enter password : ");
        String password = scanner.nextLine();

        try {
            if (account_number!=0) {
                String query = "SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1,account_number);
                preparedStatement.setString(2,password);

                ResultSet resultSet = preparedStatement.executeQuery(query);

                if(resultSet.next()){
                    double currentBalance = resultSet.getDouble("balance");
                    if(debitAmount <= currentBalance){
                        String debitQuery = "UPDATE accounts SET balance = balance - ? where account_number = ?";

                        PreparedStatement preparedStatement1 = connection.prepareStatement(debitQuery);
                        preparedStatement1.setDouble(1,currentBalance);
                        preparedStatement1.setLong(2,account_number);
                        int rowsAffected = preparedStatement.executeUpdate();

                        if(rowsAffected > 0){
                            System.out.println("Amount "+ debitAmount + "debited successfylly!!");
                        }else{
                            System.out.println("Transaction Failed");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    }else{
                        System.out.println("Insufficient balance");
                    }

                }else{
                    System.out.println("Enter valid credentials");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        connection.setAutoCommit(true);
    }

    public void transferMoney(long senderAccountNumber) throws SQLException{
        scanner.nextLine();
        System.out.println("Enter the Receiver Account number : ");
        int receiverAccountNumber = scanner.nextInt();
        System.out.println("Enter the amount : ");
        long receiverAmount = scanner.nextLong();
        System.out.println("Enter securty pin");
        String password = scanner.nextLine();

        String query = "SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?";

        try {
            connection.setAutoCommit(false);
            if (senderAccountNumber != 0 && receiverAccountNumber != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,receiverAccountNumber);
                preparedStatement.setString(2,password);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    double currentBalance = resultSet.getDouble("balance");
                    if(currentBalance >= receiverAmount){
                        String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
                        String creditQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";


                        PreparedStatement debitPreparedStatement = connection.prepareStatement(debitQuery);
                        debitPreparedStatement.setLong(1,receiverAmount);
                        debitPreparedStatement.setLong(2,senderAccountNumber);

                        PreparedStatement creditPreparedStatement = connection.prepareStatement(creditQuery);
                        creditPreparedStatement.setLong(1,receiverAmount);
                        creditPreparedStatement.setLong(2,receiverAccountNumber);

                        int rowsAffected1 = debitPreparedStatement.executeUpdate();
                        int rowsAffected2 = creditPreparedStatement.executeUpdate();

                        if(rowsAffected1 > 0 && rowsAffected2 > 0){
                            System.out.println("Trasaction Successful!!!");
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        }else{
                            System.out.println("Transaction failed.");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }


                    }else {
                        System.out.println("Insufficient balance.");
                    }
                }else {
                    System.out.println("Invalid Password");
                }

            }else{
                System.out.println("Invalid Account number");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        connection.setAutoCommit(true);
    }

    public void checkBalance(long account_number){
        scanner.nextLine();
        System.out.println("Enter Security PIN : ");
        String securityPIN = scanner.nextLine();

        String query = "SELECT * FROM accounts WHERE account_number = ? AND security_pin  = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,account_number);
            preparedStatement.setString(2,securityPIN);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                double balance = resultSet.getDouble("balance");
                System.out.println("The balance is : " + balance);
            }else{
                System.out.println("Enter correct Security PIN.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
