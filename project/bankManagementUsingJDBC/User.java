package bankManagementUsingJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {

    private final Connection connection;
    private final Scanner scanner;

    User(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;

    }

    public boolean user_exists(String email) {
        String query = "SELECT * FROM user WHERE email = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;

    }

    public String login() {
        scanner.nextLine();
        System.out.println("Email : ");
        String email = scanner.nextLine();
        System.out.println("Password : ");
        String password = scanner.nextLine();

        String loginQuery = "SELECT * FROM user WHERE email = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(loginQuery);

            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return email;
            }else{
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public void register(){
        scanner.nextLine();
        System.out.println("Enter full name : ");
        String name = scanner.nextLine();
        System.out.println("Enter email : ");
        String email = scanner.nextLine();
        System.out.println("Enter password : ");
        String password = scanner.nextLine();

        if(user_exists(email)){
            return;
        }

        String registerQuery = "INSERT INTO user(full_name,email,password) VALUES (?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(registerQuery);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Regitration Successful!!!");
            }else{
                System.out.println("Registration failed.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
