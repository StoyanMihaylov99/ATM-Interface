import com.mysql.cj.log.Log;
import db_object.User;
import gui.BankingAppGui;
import gui.LoginGui;
import gui.RegisterGui;

import javax.swing.*;
import java.math.BigDecimal;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGui().setVisible(true);
            }
        });
    }
}
