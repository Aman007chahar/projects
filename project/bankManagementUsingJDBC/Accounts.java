package bankManagementUsingJDBC;

import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private Connection connection;
    private Scanner scanner;
    Accounts(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public boolean account_exists(String email){
        String query = "SELECT account_number FROM accounts WHERE email = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public long generate_number(){
        try {
            String query = "SELECT account_number FROM accounts ORDER BY account_number DESC LIMIT 1";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                long last_account_number = resultSet.getLong("account_number");
                return last_account_number+1;
            }else{
                return 10000100;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 110000100;
    }

    public long get_account_number(String email){

        String query = "SELECT account_number FROM accounts WHERE email = ?";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                return resultSet.getLong("account_number");
            }else{
                System.out.println("Account not exists");
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException("Account does not exists.");
    }

    public long open_account(String email){
        if(!account_exists(email)){
            String openAccountQuery = "INSERT INTO accounts(account_number, full_name, email, balance,security_pin) VALUES (?,?,?,?,?)";
            scanner.nextLine();
            System.out.println("Enter full name : ");
            String name = scanner.nextLine();
            System.out.println("Enter initial amount : ");
            double balance = scanner.nextDouble();
            System.out.println("Enter password : ");
            String securityPIN = scanner.nextLine();
            long accountNumber = generate_number();

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(openAccountQuery);
                preparedStatement.setLong(1,accountNumber);
                preparedStatement.setString(2,name);
                preparedStatement.setString(3,email);
                preparedStatement.setDouble(4,balance);
                preparedStatement.setString(5,securityPIN);

                int rowsAffected = preparedStatement.executeUpdate();

                if(rowsAffected > 0){
                    System.out.println("User Registered Successfully!!!");
                }else{
                    throw new RuntimeException("Account generation failed.");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Account generation failed!!!");
    }
}
