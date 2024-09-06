package gui;

import db_object.MyJDBC;
import util.EmailValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterGui extends BaseFrame {

    public RegisterGui() {
        super("ATM Interface - Register");
    }

    @Override
    protected void addGuiComponents() {
        // Build the home screen.
        JLabel titleLabel = new JLabel("Welcome to ATM");
        titleLabel.setBounds(0, 20, super.getWidth(), 40);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        JLabel titleInfo = new JLabel("Let's make a bank account!");
        titleInfo.setBounds(0, 20, super.getWidth(), 110);
        titleInfo.setFont(new Font("Dialog", Font.PLAIN, 20));
        titleInfo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleInfo);

        JLabel firstNameProperty = new JLabel("First name:");
        firstNameProperty.setBounds(20, 100, getWidth() - 30, 24);
        firstNameProperty.setFont(new Font("Dialog", Font.PLAIN, 17));
        add(firstNameProperty);

        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(20, 125, getWidth() - 50, 30);
        firstNameField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(firstNameField);

        JLabel lastNameProperty = new JLabel("Last name:");
        lastNameProperty.setBounds(20, 170, getWidth() - 30, 24);
        lastNameProperty.setFont(new Font("Dialog", Font.PLAIN, 17));
        add(lastNameProperty);

        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(20, 195, getWidth() - 50, 30);
        lastNameField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(lastNameField);

        JLabel emailProperties = new JLabel("Email:");
        emailProperties.setBounds(20, 240, getWidth() - 30, 24);
        emailProperties.setFont(new Font("Dialog", Font.PLAIN, 17));
        add(emailProperties);

        JTextField emailField = new JTextField();
        emailField.setBounds(20, 265, getWidth() - 50, 30);
        emailField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(emailField);

        JLabel passwordProperties = new JLabel("Password:");
        passwordProperties.setBounds(20, 310, getWidth() - 50, 24);
        passwordProperties.setFont(new Font("Dialog", Font.PLAIN, 17));
        add(passwordProperties);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 335, getWidth() - 50, 30);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        JLabel rePassowrdProperties = new JLabel("Re-entry password:");
        rePassowrdProperties.setBounds(20, 375, getWidth() - 50, 24);
        rePassowrdProperties.setFont(new Font("Dialog", Font.PLAIN, 17));
        add(rePassowrdProperties);

        JPasswordField rePasswordField = new JPasswordField();
        rePasswordField.setBounds(20, 400, getWidth() - 50, 30);
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(rePasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(65, 470, getWidth() - 150, 30);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String rePassword = String.valueOf(rePasswordField.getPassword());

                if(!EmailValidator.isValidEmail(email)){
                    JOptionPane.showMessageDialog(RegisterGui.this,"Email should be valid.");
                } else if (validateUserInput(firstName, lastName, email, password, rePassword)) {
                    if (MyJDBC.register(firstName, lastName, email, password)) {
                        RegisterGui.this.dispose();
                        LoginGui loginGui = new LoginGui();
                        loginGui.setVisible(true);
                        JOptionPane.showMessageDialog(loginGui, "Registered Account Successfully !");
                    } else {
                        JOptionPane.showMessageDialog(RegisterGui.this, "Error: Email already taken.");
                    }
                } else {
                    JOptionPane.showMessageDialog(RegisterGui.this, "Error: Passwords must match and all fields are required.");
                }
            }
        });
        add(registerButton);

        JLabel registerLabel = new JLabel("<html><a href=\"#\">You have an account? Log in then! </a></html>");
        registerLabel.setBounds(0, 510, getWidth() - 10, 30);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterGui.this.dispose();
                new LoginGui().setVisible(true);
            }
        });
        add(registerLabel);
    }

    private boolean validateUserInput(String firstName, String lastName, String email,String password, String rePassword) {
        if (firstName.length() == 0 || lastName.length() == 0 || email.length() == 0 || password.length() == 0 || rePassword.length() == 0) return false;
        if(!password.equals(rePassword)) return false;
        return true;
    }


}

