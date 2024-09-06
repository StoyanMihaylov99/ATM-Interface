package gui;

import db_object.User;

import javax.swing.*;

/* Creating base gui frame */
public abstract class BaseFrame extends JFrame {
    protected User user;

    public BaseFrame(String tittle, User user){
        this.user = user;
        initialize(tittle);
    }

    public BaseFrame(String tittle){
        initialize(tittle);
    }

    // instantiate jframe properties and add a tittle to the bar.
    private void initialize(String tittle){
        setTitle(tittle);
        setSize(420,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        addGuiComponents();
    }

    // This method should be defined in subclasses when this class being inherited.
    protected abstract void addGuiComponents();


}
