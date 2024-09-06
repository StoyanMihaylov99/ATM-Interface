package db_object;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class MyJDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/bank_app";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static User validateLogin(String email, String password) {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?");

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userID = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                BigDecimal currentBalance = resultSet.getBigDecimal("balance");
                return new User(userID, firstName, lastName, email, password, currentBalance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean register(String firstName, String lastName, String email, String password) {
        try {
            if (!checkUser(email)) {
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO  user(first_name,last_name,email,password,balance)" +
                        "VALUES(?,?,?,?,?)");
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, password);
                preparedStatement.setBigDecimal(5, BigDecimal.ZERO);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean checkUser(String email) {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean addTransactionToDatabase(Transaction transaction) {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            PreparedStatement insertTransaction = connection.prepareStatement(
                    "INSERT transactions (user_id, type, amount, date) " +
                            "VALUES(?, ?, ?, NOW())"
            );

            insertTransaction.setInt(1, transaction.getUserId());
            insertTransaction.setString(2, transaction.getType());
            insertTransaction.setBigDecimal(3, transaction.getAmount());
            insertTransaction.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateCurrentBalance(User user) {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            PreparedStatement updateBalance = connection.prepareStatement(
                    "UPDATE user SET balance = ? WHERE id = ?"
            );

            updateBalance.setBigDecimal(1, user.getBalance());
            updateBalance.setInt(2, user.getId());
            updateBalance.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static boolean transfer(User user, String transferredEmail, double amount) {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement queryUser = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
            queryUser.setString(1, transferredEmail);
            ResultSet resultSet = queryUser.executeQuery();
            while (resultSet.next()) {
                User transferedUser =
                        new User(resultSet.getInt("id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getBigDecimal("balance"));

                Transaction transferedTransaction = new Transaction(user.getId(), "Transfer", BigDecimal.valueOf(-amount), null);
                Transaction recievedTransaction = new Transaction(transferedUser.getId(), "Transfer", BigDecimal.valueOf(amount), null);

                transferedUser.setBalance(transferedUser.getBalance().add(BigDecimal.valueOf(amount)));
                updateCurrentBalance(transferedUser);

                user.setBalance(user.getBalance().subtract(BigDecimal.valueOf(amount)));
                updateCurrentBalance(user);

                addTransactionToDatabase(transferedTransaction);
                addTransactionToDatabase(recievedTransaction);

                return true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Transaction> getPastTransaction(User user){
        ArrayList<Transaction> pastTransactions = new ArrayList<>();
        try{
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            PreparedStatement selectAllTransaction = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE user_id = ?"
            );
            selectAllTransaction.setInt(1, user.getId());

            ResultSet resultSet = selectAllTransaction.executeQuery();
            while(resultSet.next()){
                Transaction transaction = new Transaction(
                        user.getId(),
                        resultSet.getString("type"),
                        resultSet.getBigDecimal("amount"),
                        resultSet.getDate("date")
                );
                pastTransactions.add(transaction);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return pastTransactions;
    }

}

