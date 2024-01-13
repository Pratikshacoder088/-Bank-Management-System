import java.util.*;
import java.sql.*;

public class Bank {
    private static final String url = "jdbc:mysql://localhost:3306/bank";
    private static final String username = "root";
    private static final String password = "Database@123";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            User user = new User(scanner, connection);
            Transaction transaction = new Transaction(connection, scanner);

            // while ((true)) {
            InitiateBank(scanner, user, transaction);
            // transaction.GetTransactionHistory();
            // }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void UserDetails(Scanner scanner, User user, Transaction transaction) {
        System.out.println("1. Create Account");
        System.out.println("2. Delete Account");
        System.out.println("3. Account Details");
        System.out.println("4. Transaction History");
        System.out.println("5. << Back");
        System.out.print("\nPlease enter your choice: ");
        int AccountOption = scanner.nextInt();

        switch (AccountOption) {
            case 1:
                // Add user
                user.AddUser();
                InitiateBank(scanner, user, transaction);
                break;

            case 2:
                // Delete user
                user.DeleteUser();
                InitiateBank(scanner, user, transaction);
                break;

            case 3:
                // Get user by email id
                user.getUserByEmail();
                InitiateBank(scanner, user, transaction);
                break;

            case 4:
                // Transaction History
                transaction.GetTransactionHistory();
                InitiateBank(scanner, user, transaction);
                break;

            case 5:
                // Back
                InitiateBank(scanner, user, transaction);
                break;

            default:
                System.out.println("Please enter valid choice!!");
                break;

        }
    }

    public static void DoTransaction(Scanner scanner, User user, Transaction transaction) {
        System.out.println("Transactions: ");
        System.out.println("1. Balance");
        System.out.println("2. Withdrawl");
        System.out.println("3. Deposit");
        System.out.println("4. << Back");
        System.out.print("\nEnter your choice: ");
        int TransactionOption = scanner.nextInt();

        switch (TransactionOption) {
            case 1:
                // chcek balance
                transaction.ViewBalance();
                InitiateBank(scanner, user, transaction);
                break;

            case 2:
                // debit
                transaction.Withdrawl();
                InitiateBank(scanner, user, transaction);
                break;

            case 3:
                // credit
                transaction.Deposit();
                InitiateBank(scanner, user, transaction);
                break;

            case 4:
                // back
                InitiateBank(scanner, user, transaction);
                break;

            default:
                System.out.println("Please enter valid choice!!");
                break;
        }
    }

    public static void InitiateBank(Scanner scanner, User user, Transaction transaction) {
        System.out.println("\nWelcome to AP Bank");
        System.out.println("1. User Details");
        System.out.println("2. Transaction");
        System.err.println("3. Exit");
        System.out.print("\nPlease enter your choice: ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                UserDetails(scanner, user, transaction);
                break;
            case 2:
                DoTransaction(scanner, user, transaction);
                break;
            case 3:
                System.out.println("Thanks for usign AP bank!!");
                break;
            default:
                System.out.println("Please enter valid choice!!");
                break;

        }
    }
}
