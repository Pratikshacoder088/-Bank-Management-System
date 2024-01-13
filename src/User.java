import java.util.*;
import java.sql.*;

public class User {

    private Scanner scanner;
    private Connection connection;

    User(Scanner scanner, Connection connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public void AddUser() {
        String UserName;
        String DOB;
        String PhoneNo;
        String Email;
        String Address;
        String Branch;

        // ask username
        System.out.print("Enter your name: ");
        UserName = scanner.next();
        // ask Date of birth
        System.out.print("Enter your DOB (YYYY-MM-DD): ");
        DOB = scanner.next();
        // ask Phone number
        System.out.print("Enter your Phone no.: ");
        PhoneNo = scanner.next();
        // ask Email
        System.out.print("Enter your Email: ");
        Email = scanner.next();
        // ask Address
        System.out.print("Enter your Address: ");
        Address = scanner.next();
        // ask Branch
        System.out.print("Enter your Branch Name: ");
        Branch = scanner.next();

        try {
            // write query
            String query = "INSERT INTO useraccounts(Username, DOB, PhoneNo, Email, Address, Branch) VALUES (?,?,?,?,?,?)";
            // create statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // pass all values in statement
            preparedStatement.setString(1, UserName);
            preparedStatement.setString(2, DOB);
            preparedStatement.setString(3, PhoneNo);
            preparedStatement.setString(4, Email);
            preparedStatement.setString(5, Address);
            preparedStatement.setString(6, Branch);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User Added Successfully!!");
            } else {
                System.out.println("Failed to add User!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteUser() {

        // ask AccountNo
        System.out.print("Enter your Account number: ");
        int AccountNo = scanner.nextInt();

        try {
            // QUERY for delete record
            String query = "Delete from useraccounts where AccountNo=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, AccountNo);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User Removed Successfully!!");
            } else {
                System.out.println("Failed to Remove User!!");
            }

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public void getUserByEmail() {
        // ask email Id
        System.out.print("Enter your email: ");
        String Email = scanner.next();

        try {
            // query for getUser
            String query = "SELECT AccountNo, UserName, Balance FROM useraccounts where Email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, Email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Account Details :");
                System.out.println("+--------------+------------------+-------------+");
                System.out.println("| AccountNo    | User Name        | Balance     |");
                System.out.println("+--------------+------------------+-------------+");
                System.out.printf("| %-12s | %-16s | %-11s |\n", resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3));
                System.out.println("+--------------+------------------+-------------+");
            } else
                System.out.println("No data found!!");

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void AddUserSeeder() {

        List<String> usernames = Arrays.asList("john_doe", "jane_smith", "alex_miller", "sara_jones", "robert_white",
                "emily_brown", "michael_jackson", "amanda_martin", "david_davis", "lisa_johnson");
        List<String> datesOfBirth = Arrays.asList("1985-05-12", "1990-09-20", "1982-03-15", "1995-12-08", "1988-07-24",
                "1992-11-02", "1980-06-30", "1987-04-15", "1993-10-18", "1984-02-28");
        List<String> phoneNumbers = Arrays.asList("555-1234", "555-5678", "555-9876", "555-4321", "555-8765",
                "555-3456", "555-6543", "555-7890", "555-2345", "555-8901");
        List<String> emails = Arrays.asList("john.doe@example.com", "jane.smith@example.com", "alex.miller@example.com",
                "sara.jones@example.com", "robert.white@example.com", "emily.brown@example.com",
                "michael.jackson@example.com", "amanda.martin@example.com", "david.davis@example.com",
                "lisa.johnson@example.com");
        List<String> addresses = Arrays.asList("123 Main St, Cityville", "456 Oak St, Townsville",
                "789 Pine St, Villagetown", "101 Cedar St, Hamletville", "202 Elm St, Suburbia",
                "303 Maple St, Countryside", "404 Birch St, Riverside", "505 Pine St, Lakeside", "606 Oak St, Hilltop",
                "707 Cedar St, Mountainside");
        List<String> branches = Arrays.asList("IT", "Finance", "Marketing", "HR", "Sales", "IT", "Finance", "Marketing",
                "HR", "Sales");

        try {
            for (int i = 0; i < usernames.size(); i++) {
                // write query
                String query = "INSERT INTO useraccounts(Username, DOB, PhoneNo, Email, Address, Branch) VALUES (?,?,?,?,?,?)";
                // create statement
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                // pass all values in statement
                preparedStatement.setString(1, usernames.get(i));
                preparedStatement.setString(2, datesOfBirth.get(i));
                preparedStatement.setString(3, phoneNumbers.get(i));
                preparedStatement.setString(4, emails.get(i));
                preparedStatement.setString(5, addresses.get(i));
                preparedStatement.setString(6, branches.get(i));
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("User Added Successfully!!");
                } else {
                    System.out.println("Failed to add User!!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
