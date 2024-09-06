package gui;

import db_object.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankingAppGui extends BaseFrame implements ActionListener {
    private JTextField balanceField;
    public JTextField getBalanceField(){
        return balanceField;
    }

    public BankingAppGui(User user){
        super("ATM Interface - Home", user );

    }
    @Override
    protected void addGuiComponents() {

        String welcomeMessage = "<html>" +
                "<body style='text-align:center'>" +
                "<b>Hello " + user.getFirstName() + "</b><br>" +
                "What would you like to do today ?</body></html>";

        JLabel welcomeMessageLabel = new JLabel(welcomeMessage);
        welcomeMessageLabel.setBounds(0,20,getWidth() - 10, 40);
        welcomeMessageLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        welcomeMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeMessageLabel);

        JLabel currentBalanceLabel = new JLabel("Current Balance");
        currentBalanceLabel.setBounds(0,80,getWidth() - 10,30);
        currentBalanceLabel.setFont(new Font("Dialog",Font.BOLD,22));
        currentBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(currentBalanceLabel);

        balanceField = new JTextField("$" + user.getBalance());
        balanceField.setBounds(15,120,getWidth() - 50,34);
        balanceField.setFont(new Font("Dialog",Font.BOLD,28));
        balanceField.setHorizontalAlignment(SwingConstants.RIGHT);
        balanceField.setEditable(false);
        add(balanceField);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(15,170,getWidth() - 50, 50);
        depositButton.setFont(new Font("Dialog",Font.BOLD,22));
        depositButton.setHorizontalAlignment(SwingConstants.CENTER);
        depositButton.addActionListener(this);
        add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(15,250,getWidth() - 50, 50);
        withdrawButton.setFont(new Font("Dialog",Font.BOLD,22));
        withdrawButton.setHorizontalAlignment(SwingConstants.CENTER);
        withdrawButton.addActionListener(this);
        add(withdrawButton);

        JButton pastTransaction = new JButton("Past Transaction");
        pastTransaction.setBounds(15,320,getWidth() - 50, 50);
        pastTransaction.setFont(new Font("Dialog",Font.BOLD,22));
        pastTransaction.setHorizontalAlignment(SwingConstants.CENTER);
        pastTransaction.addActionListener(this);
        add(pastTransaction);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(15,390,getWidth() - 50, 50);
        transferButton.setFont(new Font("Dialog",Font.BOLD,22));
        transferButton.setHorizontalAlignment(SwingConstants.CENTER);
        transferButton.addActionListener(this);
        add(transferButton);

        JButton logOutButton = new JButton("Log out");
        logOutButton.setBounds(15,500,getWidth() - 50, 50);
        logOutButton.setFont(new Font("Dialog",Font.BOLD,22));
        logOutButton.setHorizontalAlignment(SwingConstants.CENTER);
        logOutButton.addActionListener(this);
        add(logOutButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed =  e.getActionCommand();
        if(buttonPressed.equalsIgnoreCase("Log out")){
            new LoginGui().setVisible(true);
            this.dispose();
            return;
        }

        AppDialog dialog = new AppDialog(this,user);
        dialog.setTitle(buttonPressed);

        if(buttonPressed.equalsIgnoreCase("Deposit") || buttonPressed.equalsIgnoreCase("Transfer") || buttonPressed.equalsIgnoreCase("Withdraw")){
            dialog.addCurrentAmount();
            dialog.addActionButton(buttonPressed);

            if(buttonPressed.equalsIgnoreCase("Transfer")){
                dialog.addUserField();
            }


        } else if(buttonPressed.equalsIgnoreCase("Past Transaction")){
            dialog.addPastTransactionComponents();
        }
            dialog.setVisible(true);
    }
}
