package org.example;
import org.example.config.Connector;
import org.example.services.AccountService;
import org.example.services.UserService;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Column(name = "password",nullable = false)
    private String password;
    @OneToMany(mappedBy = "holder")
    private List<Account> bankAccounts;
    @Column(name = "email",nullable = false)
    private String email;


    public User() {

    }

    public User(String firstName, String lastName, String password, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setPassword(password);
        setEmail(email);
        setBankAccounts();
        //print log message
        System.out.printf("New user %s, %s with ID %s created.\n", firstName, lastName, id);


    }

    public void setPassword(String password) {
            this.password = hashPassword(password);
    }


    public void setEmail(String email) {

        if(validateEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalStateException("email isn't correct or is already in use. Please try with another.");
            //TODO: display that the email isn't correct.
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

    public String getPassword() {
        return password;
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
        this.bankAccounts = new ArrayList<>();
    }

    public List<Account> getBankAccounts() {
        return bankAccounts;
    }
}
