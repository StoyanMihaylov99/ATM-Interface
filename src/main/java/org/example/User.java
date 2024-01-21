package org.example;

import org.example.config.Connector;
import org.example.config.PinValidator;
import org.example.services.UserService;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import static org.example.config.EmailValidator.validateEmail;
import static org.example.config.PasswordHashing.hashPassword;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string",
            strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "pin",nullable = false)
    private String pin;
    @OneToMany(mappedBy = "holder",cascade = CascadeType.ALL)
    private List<Account> bankAccounts;
    @Column(name = "email",nullable = false)
    private String email;


    public User() {

    }

    public User(String firstName, String lastName, String pin, String email) throws NoSuchAlgorithmException {
        setFirstName(firstName);
        setLastName(lastName);
        setPin(pin);
        setEmail(email);
        setBankAccounts();
        //print log message
        System.out.printf("New user %s, %s with ID %s created.\n", firstName, lastName, id);


    }

    public void setPin(String pin) throws NoSuchAlgorithmException {
        if(PinValidator.validatePin(pin)) {
            this.pin = hashPassword(pin);
        } else {
            //TODO: display that the pin is wrong;
            throw new IllegalStateException("pin shoud be 4 digit only.");
        }
    }


    public void setEmail(String email) {

        if(validateEmail(email) && !verifyDuplicateEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalStateException("email isn't correct or is already in use. Please try with another.");
            //TODO: display that the email isn't correct.
        }
    }

    private boolean verifyDuplicateEmail(String email) {
        Connector.commitTransaction();
        Connector.creating();
        User user;

        try{
            user = UserService.findUserByEmail(email);
            return true;
        } catch (EntityNotFoundException e){
            System.out.println("not found email;");
            return false;
        }
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPin() {
        return pin;
    }

    public String getEmail() {
        return email;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBankAccounts() {
        this.bankAccounts = bankAccounts;
    }

    public List<Account> getBankAccounts() {
        return bankAccounts;
    }
}
