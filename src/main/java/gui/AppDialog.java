package gui;
import db_object.MyJDBC;
import db_object.Transaction;
import db_object.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

public class AppDialog extends JDialog implements ActionListener {
    private User user;
    private BankingAppGui bankingAppGui;
    private JButton actionButton;
    private JLabel balanceLabel, enteredAmountLabel, enterUserLabel;
    private JTextField enteredAmountField, enteredUserField;
    private JPanel pastTransactionsPanel;
    private ArrayList<Transaction> pastTransactions;


    public AppDialog(BankingAppGui bankingAppGui, User user) {
        setSize(400, 400);
        setModal(true);
        setLocationRelativeTo(bankingAppGui);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        this.bankingAppGui = bankingAppGui;
        this.user = user;

    }

    public void addCurrentAmount() {
        balanceLabel = new JLabel("Balance: " + user.getBalance());
        balanceLabel.setBounds(0, 10, getWidth() - 20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        enteredAmountLabel = new JLabel("Enter Amount: ");
        enteredAmountLabel.setBounds(0, 50, getWidth() - 20, 20);
        enteredAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enteredAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enteredAmountLabel);

        enteredAmountField = new JTextField();
        enteredAmountField.setBounds(15, 80, getWidth() - 50, 40);
        enteredAmountField.setFont(new Font("Dialog", Font.BOLD, 20));
        enteredAmountField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enteredAmountField);
    }

    public void addActionButton(String actionType) {
        actionButton = new JButton(actionType);
        actionButton.setBounds(15, 300, getWidth() - 50, 40);
        actionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        actionButton.addActionListener(this);
        add(actionButton);
    }

    public void addUserField() {
        enterUserLabel = new JLabel("Enter User's email:");
        enterUserLabel.setBounds(0, 160, getWidth() - 20, 20);
        enterUserLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserLabel);

        enteredUserField = new JTextField();
        enteredUserField.setBounds(15, 190, getWidth() - 50, 40);
        enteredUserField.setFont(new Font("Dialog", Font.BOLD, 20));
        enteredUserField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enteredUserField);
    }

    public void addPastTransactionComponents() {
        pastTransactionsPanel = new JPanel();
        pastTransactionsPanel.setLayout(new BoxLayout(pastTransactionsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(pastTransactionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 20, getWidth() - 15, getHeight() - 80);
        pastTransactions = MyJDBC.getPastTransaction(user);

        for(int i = 0; i < pastTransactions.size(); i++){
            Transaction pastTransaction = pastTransactions.get(i);

            JPanel pastTransactionContainer = new JPanel();
            pastTransactionContainer.setLayout(new BorderLayout());

            JLabel transactionTypeLabel = new JLabel(pastTransaction.getType());
            transactionTypeLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            JLabel transactionAmountLabel = new JLabel(String.valueOf(pastTransaction.getAmount()));
            transactionAmountLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            JLabel transactionDateLabel = new JLabel(String.valueOf(pastTransaction.getDate()));
            transactionDateLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            pastTransactionContainer.add(transactionTypeLabel, BorderLayout.WEST);
            pastTransactionContainer.add(transactionAmountLabel, BorderLayout.EAST);
            pastTransactionContainer.add(transactionDateLabel, BorderLayout.SOUTH);
            pastTransactionContainer.setBackground(Color.WHITE);
            pastTransactionContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pastTransactionsPanel.add(pastTransactionContainer);
        }
        add(scrollPane);

    }


    public void handleTransaction(String type, double amount) {
        Transaction transaction;
        if (amount <= 0) {
            JOptionPane.showMessageDialog(AppDialog.this, "The amount should be greater than 0.");
            return;
        }

        if (type.equalsIgnoreCase("Deposit")) {
            user.setBalance(user.getBalance().add(new BigDecimal(amount)));
            transaction = new Transaction(user.getId(), type, BigDecimal.valueOf(amount), null);
        } else {
            user.setBalance(user.getBalance().subtract(new BigDecimal(amount)));
            transaction = new Transaction(user.getId(), type, BigDecimal.valueOf(-amount), null);
        }

        if (MyJDBC.addTransactionToDatabase(transaction) && MyJDBC.updateCurrentBalance(user)) {
            JOptionPane.showMessageDialog(this, type + " Successfully!");
            resetFieldsAndUpdateCurrentBalance();
        } else {
            JOptionPane.showMessageDialog(this, type + " Failed...");
        }

    }

    private void resetFieldsAndUpdateCurrentBalance() {
        enteredAmountField.setText("");
        if (enteredUserField != null) {
            enteredUserField.setText("");
        }
        balanceLabel.setText("Balance: $" + user.getBalance());
        bankingAppGui.getBalanceField().setText("$" + user.getBalance());
    }

    private void handleTransfer(User user, String transferredUser, double amount){
        if (amount <= 0) {
            JOptionPane.showMessageDialog(AppDialog.this, "The amount should be greater than 0.");
        } else if (MyJDBC.transfer(user, transferredUser, amount)){
            JOptionPane.showMessageDialog(this, "Transfer Success!");
            resetFieldsAndUpdateCurrentBalance();
        }else{
            JOptionPane.showMessageDialog(this, "Transfer Failed...");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();
        try {
            double amountVal = Double.parseDouble(enteredAmountField.getText());
            if (buttonPressed.equalsIgnoreCase("Deposit")) {
                handleTransaction(buttonPressed, amountVal);
            } else {
                int result = user.getBalance().compareTo(BigDecimal.valueOf(amountVal));
                if (result < 0) {
                    JOptionPane.showMessageDialog(this, "Error: Input value is more than current balance");
                    return;
                }

                // check to see if withdraw or transfer was pressed
                if (buttonPressed.equalsIgnoreCase("Withdraw")) {
                    handleTransaction(buttonPressed, amountVal);
                } else {
                    // transfer
                    String transferredUser = enteredUserField.getText();

                    // handle transfer
                    handleTransfer(user, transferredUser, amountVal);
                }
            }
        } catch (NumberFormatException number) {
            JOptionPane.showMessageDialog(AppDialog.this, "The amount should contains only digits.");
        }

    }
}
