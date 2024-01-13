import java.util.*;
import java.sql.*;

public class Transaction {
    private Connection connection;
    private Scanner scanner;

    Transaction(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public boolean isAccountExist(int AccountNo) {

        try {
            String query = "Select * from useraccounts where AccountNo = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, AccountNo);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                System.out.println("Please enter valid account number!!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void ViewBalance() {
        ViewBalance(0);
    }

    public void ViewBalance(int AccountNo) {

        if (AccountNo < 10000) {
            // get account number
            System.out.print("\nEnter your Account number: ");
            AccountNo = scanner.nextInt();
        }
        if (isAccountExist(AccountNo) == true) {
            try {

                // print sql statement
                String query = "Select AccountNo,Username,Balance from useraccounts where AccountNo = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, AccountNo);
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("Balance Details :");
                System.out.println("+--------------+------------------+----------------------+");
                System.out.println("| AccountNo    | User Name        | Balance              |");
                System.out.println("+--------------+------------------+----------------------+");

                while (resultSet.next()) {

                    System.out.printf("| %-12s | %-16s | %-20s |\n", resultSet.getString(1), resultSet.getString(2),
                            resultSet.getString(3));
                    System.out.println("+--------------+------------------+----------------------+");
                }

            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }

    public void Deposit() {

        int AccountNo;
        int Amount;

        System.out.print("\nEnter Account number: ");
        AccountNo = scanner.nextInt();

        if (isAccountExist(AccountNo)) {
            try {
                System.out.print("Please enter the Amount: ");
                Amount = scanner.nextInt();

                String query = "Update useraccounts set Balance = Balance + ? where AccountNo = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Amount);
                preparedStatement.setInt(2, AccountNo);
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Amount credited successfully");
                    ViewBalance(AccountNo);
                    recordTransaction(AccountNo, "Deposit", Amount);

                } else
                    System.out.println("Something went wrong. Please try again.");

            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }

    public void recordTransaction(int AccountNo, String TransactionType, double Amount) {
        try {
            String depositQuery = "INSERT INTO TransactionHistory (AccountNo, TransactionType, Amount) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(depositQuery);
            preparedStatement.setInt(1, AccountNo);
            preparedStatement.setString(2, TransactionType);
            preparedStatement.setDouble(3, Amount);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Withdrawl() {
        int AccountNo;
        int Amount;

        System.out.print("Enter your Account number: ");
        AccountNo = scanner.nextInt();

        if (isAccountExist(AccountNo) == true) {
            try {
                System.out.print("Please Enter Amount: ");
                Amount = scanner.nextInt();

                // check balance
                String CheckBalance = "Select Balance from useraccounts where AccountNo = ?";
                PreparedStatement CheckBalanceStatement = connection.prepareStatement(CheckBalance);
                CheckBalanceStatement.setInt(1, AccountNo);
                ResultSet result = CheckBalanceStatement.executeQuery();
                double balance = 0;
                if (result.next()) {
                    balance = result.getDouble("Balance");
                }
                // balance 0 sout then return
                if (balance == 0 || balance < Amount) {
                    System.out.println("Insufficient Balance!!!");
                } else {
                    // blanace == amount

                    String query = "Update useraccounts set Balance = Balance - ? where AccountNo = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, Amount);
                    preparedStatement.setInt(2, AccountNo);
                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("Amount debited successfully");
                        ViewBalance(AccountNo);
                        recordTransaction(AccountNo, "Withdraw", Amount);
                    } else
                        System.out.println("Something went wrong. Please try again.");

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        else
            System.out.println("Please enter valid Account number!!");
    }

    public void GetTransactionHistory() {

        System.out.print("Enter Account Number: ");
        int AccountNo = scanner.nextInt();

        if (isAccountExist(AccountNo)) {
            try {

                String query = "SELECT * FROM TransactionHistory WHERE AccountNo = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, AccountNo);
                ResultSet resultSet = preparedStatement.executeQuery();

                System.out.println("Transaction History :");
                System.out.println("+--------------+-------------+----------------------+-----------------------+");
                System.out.println("| AccountNo    | Type        | Balance              | Date                  |");
                System.out.println("+--------------+-------------+----------------------+-----------------------+");

                while (resultSet.next()) {

                    System.out.printf("| %-12s | %-16s | %-18s | %-21s |\n", resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4), resultSet.getString(5));
                    System.out.println("+--------------+-------------+----------------------+-----------------------+");
                }

            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }

    // set history
    // get history

}
