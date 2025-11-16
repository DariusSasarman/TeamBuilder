package persistencepackage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.security.MessageDigest;
import java.util.Base64;

public class LoginManager {
    private static final String APP_FOLDER = "TeamBuilder";
    private static final String CREDENTIALS_FILE = "credentials.dat";
    private static final Color BG_COLOR = new Color(0x2B2C30);

    private String credentialsPath;
    private User currentUser;

    public LoginManager() {
        credentialsPath = getCredentialsPath();
    }

    public User login() {
        File credFile = new File(credentialsPath);

        if (credFile.exists()) {
            currentUser = loadCredentials();
        }

        if (currentUser == null || !authenticateWithDBHashed(currentUser.getUsername(), currentUser.getPasswordHash())) {
            currentUser = showLoginDialog();
        }

        return currentUser;
    }

    public User changeAccount(String username, String password) {
        if (authenticateWithDB(username, password)) {
            logout(); // Delete old credentials
            currentUser = new User(username, hashPassword(password));
            saveCredentials(currentUser);
            return currentUser;
        }
        return null;
    }

    public void logout() {
        new File(credentialsPath).delete();
        currentUser = null;
    }

    private String getCredentialsPath() {
        String userHome = System.getProperty("user.home");
        File appFolder = new File(userHome, ".TeamBuilder");
        appFolder.mkdirs();

        return new File(appFolder, CREDENTIALS_FILE).getAbsolutePath();
    }

    private User showLoginDialog() {
        JDialog dialog = new JDialog((Frame) null, "We need to get to know you first!", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(null);
        dialog.setBackground(BG_COLOR);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        Font largeFont = new Font("Dialog", Font.PLAIN, 20);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(largeFont);
        usernameLabel.setForeground(Color.WHITE);

        JTextField usernameField = new JTextField();
        usernameField.setFont(largeFont);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(largeFont);
        passwordLabel.setForeground(Color.WHITE);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(largeFont);

        centerPanel.add(usernameLabel);
        centerPanel.add(usernameField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(BG_COLOR);

        User[] result = new User[1];

        JButton exitButton = new JButton("Exit");
        styleButton(exitButton, largeFont);
        exitButton.addActionListener(e -> System.exit(0));

        JButton signUpButton = new JButton("Sign up");
        styleButton(signUpButton, largeFont);
        signUpButton.addActionListener(e -> {
            result[0] = showSignUpDialog();
            if(result[0] != null)
            {
                saveCredentials(result[0]);
                dialog.dispose();
            }
        });


        JButton loginButton = new JButton("Log in");
        styleButton(loginButton, largeFont);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter both username and password!");
                return;
            }

            if (authenticateWithDB(username, password)) {
                result[0] = new User(username, hashPassword(password));
                saveCredentials(result[0]);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Invalid username or password!");
            }
        });

        bottomPanel.add(exitButton);
        bottomPanel.add(signUpButton);
        bottomPanel.add(loginButton);

        dialog.add(centerPanel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);

        return result[0];
    }

    private User showSignUpDialog() {
        JDialog dialog = new JDialog((Frame) null, "Nice to meet you!", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(null);
        dialog.setBackground(BG_COLOR);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        Font largeFont = new Font("Dialog", Font.PLAIN, 20);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(largeFont);
        usernameLabel.setForeground(Color.WHITE);

        JTextField usernameField = new JTextField();
        usernameField.setFont(largeFont);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(largeFont);
        passwordLabel.setForeground(Color.WHITE);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(largeFont);

        JLabel passwordLabelAgain = new JLabel("Password (again):");
        passwordLabelAgain.setFont(largeFont);
        passwordLabelAgain.setForeground(Color.WHITE);

        JPasswordField passwordFieldAgain = new JPasswordField();
        passwordFieldAgain.setFont(largeFont);

        centerPanel.add(usernameLabel);
        centerPanel.add(usernameField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);
        centerPanel.add(passwordLabelAgain);
        centerPanel.add(passwordFieldAgain);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(BG_COLOR);

        User[] result = new User[1];

        JButton exitButton = new JButton("Exit");
        styleButton(exitButton, largeFont);
        exitButton.addActionListener(e -> System.exit(0));


        JButton loginButton = new JButton("Sign up");
        styleButton(loginButton, largeFont);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String passwordRetry = new String(passwordFieldAgain.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter both username and password!");
                return;
            }
            if(!password.equals(passwordRetry))
            {
                JOptionPane.showMessageDialog(dialog, "Both passwords should be identical");
                return;
            }

            if (createUserOnDB(username, password)) {
                result[0] = new User(username, hashPassword(password));
                saveCredentials(result[0]);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Invalid username or password!");
            }
        });

        bottomPanel.add(exitButton);
        bottomPanel.add(loginButton);

        dialog.add(centerPanel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);

        return result[0];
    }


    private void styleButton(JButton button, Font font) {
        button.setBackground(BG_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(font);
    }

    private boolean authenticateWithDB(String username, String password) {
        return authenticateWithDBHashed(username,hashPassword(password));
    }

    private boolean authenticateWithDBHashed(String username, String hashedPassword) {
        // TODO: Implement database authentication
        System.out.println("Authenticating: " + username);
        /// TODO: Replace with actual DB check
        /// TODO: DB stores the hashed password (SHA-256 Base64)
        return true;
    }

    private boolean createUserOnDB(String username, String password) {
        /// TODO: Implement database user Creation
        System.out.println("Making new account: " + username);
        /// TODO: Replace with actual DB check
        return true;
    }

    private void saveCredentials(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(credentialsPath))) {
            oos.writeObject(user);
            System.out.println("Credentials saved to: " + credentialsPath);
        } catch (Exception ex) {
            System.err.println("Error saving credentials: " + ex.getMessage());
        }
    }

    private User loadCredentials() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(credentialsPath))) {
            return (User) ois.readObject();
        } catch (Exception ex) {
            return null;
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception ex) {
            throw new RuntimeException("Error hashing password", ex);
        }
    }
}