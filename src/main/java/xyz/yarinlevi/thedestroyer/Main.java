package xyz.yarinlevi.thedestroyer;

import lombok.Getter;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

public class Main {
    @Getter public static TheDestroyer instance;

    public static void main(String[] args) throws LoginException, InterruptedException, SQLException {
        instance = new TheDestroyer().start(args);
    }
}
