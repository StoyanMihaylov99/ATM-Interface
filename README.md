# ATM-Interface
  A personal project for practicing and learning new skills. 

  This is an ATM Interface Desktop application. The user will be able to make registration, add new bank accounts, make deposits, transactions and withdraw funds.


# UI/UX View

This is the welcome page, after you run the application. You have to choose to make a registration or log in existing one.

![welcome](https://github.com/StoyanMihaylov99/ATM-Interface/assets/107346999/6e082703-58aa-4c7a-9baa-97ade86bfc8c)

In the register view, we have some validations, such as regular expresion for the field "email" and a custom validator for fields "first name"
and "last name", to contains only letters. If the user, tries to insert other type of characters, the program ignore it, and wait for the next character.

![register](https://github.com/StoyanMihaylov99/ATM-Interface/assets/107346999/d7774ce1-b247-49fc-959d-16e88476bcbe)

This is the dashboard of the user, it contains tableview of all bank accounts and their balance.

![dashboard](https://github.com/StoyanMihaylov99/ATM-Interface/assets/107346999/fbda8613-4251-4d41-9253-a3cc36fa4c28)

This is the dashboard of the current Bank Account, it contains meta data such as Iban, balance and transaction history.
Also you can make deposits,withdraws and make transaction to other users.

![account](https://github.com/StoyanMihaylov99/ATM-Interface/assets/107346999/f6a93897-3fe6-4af5-aefa-0cd8f9eed462)
![transfer](https://github.com/StoyanMihaylov99/ATM-Interface/assets/107346999/69c888b4-a41e-4345-bdb9-d952d276cc17)



# Built With
  - Java/JavaFX
  - MySQL
  - Hibernate
 - Maven


# Usage
  - You can make bank registration.
  - You can open bank Accounts.
  - You can make deposits, withdraws and transactions to other users.
  - Also you can delete your own bank accounts.


# Entities
  The entities were instantiated using jakarta.persistance JPQL with the @Entity annotation.

  - User;
  - Account;
  - Transaction;

# How to test it

  - first you will need installed java on your machine, it's recommended to be java version 17+.
  - And just click on the ATM.jar file.
  - https://www.dropbox.com/scl/fi/oxza9ii6lcm2pqxwsvji3/ATM.jar?rlkey=96fy6nuzmjmhum8ept96qbbbc&dl=0
  - If there is no responce, you will need to downloald jarfix, and run it once, the program just config your machine.
  - if there is issue please contact me: stoqn.business@gmail.com.
    


