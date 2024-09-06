package gui;

import db_object.MyJDBC;
import db_object.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGui extends BaseFrame{
    public LoginGui(){
        super("ATM Interface - Login");
    }
    @Override
    protected void addGuiComponents() {
        // Build the home screen.
        JLabel titleLabel = new JLabel("Welcome to ATM");
        titleLabel.setBounds(0,20,super.getWidth(),40);
        titleLabel.setFont(new Font("Dialog",Font.BOLD,32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        JLabel titleInfo = new JLabel("Please log in.");
        titleInfo.setBounds(0,20,super.getWidth(),110);
        titleInfo.setFont(new Font("Dialog",Font.PLAIN,20));
        titleInfo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleInfo);

        JLabel emailProperties = new JLabel("Email:");
        emailProperties.setBounds(20,120,getWidth() - 30, 24);
        emailProperties.setFont(new Font("Dialog", Font.PLAIN, 17));
        add(emailProperties);

        JTextField emailField = new JTextField();
        emailField.setBounds(20,160,getWidth() - 50,40);
        emailField.setFont(new Font("Dialog", Font.PLAIN,28));
        add(emailField);

        JLabel passwordProperties = new JLabel("Password:");
        passwordProperties.setBounds(20,280,getWidth() - 50, 24);
        passwordProperties.setFont(new Font("Dialog", Font.PLAIN, 17));
        add(passwordProperties);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20,320,getWidth() - 50,40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN,28));
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20,460,getWidth() - 50,40);
        loginButton.setFont(new Font("Dialog", Font.BOLD,20));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = String.valueOf(passwordField.getPassword());
                User user = MyJDBC.validateLogin(email,password);

                if(user != null){
                    LoginGui.this.dispose();
                    BankingAppGui bankingAppGui = new BankingAppGui(user);
                    bankingAppGui.setVisible(true);
                    JOptionPane.showMessageDialog(bankingAppGui,"Login Successfully!");
                } else{
                    JOptionPane.showMessageDialog(LoginGui.this,"Login failed...");

                }
            }
        });
        add(loginButton);

        JLabel registerLabel = new JLabel("<html><a href=\"#\">Don't have a bank account? Let's make one! </a></html>");
        registerLabel.setBounds(0,510,getWidth() - 10, 30);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN,17));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginGui.this.dispose();
                new RegisterGui().setVisible(true);
            }

        });
        add(registerLabel);


    }
}
