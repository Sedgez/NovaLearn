package novaLearn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.AbstractBorder;

public class NovaLearn {

    private JFrame frame;
    private Image backgroundImage;
    private Image logoImage;
    private Image logoSmallImage;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                NovaLearn window = new NovaLearn();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public NovaLearn() {
        loadAssets();
        initialize();
    }

    private void loadAssets() {
        backgroundImage = loadImage("/novaLearn/assets/logP.png");
        logoImage = loadImage("/novaLearn/assets/logo.png");
        logoSmallImage = loadImage("/novaLearn/assets/logo1.png");
    }

    private Image loadImage(String resourcePath) {
        try {
            return new ImageIcon(getClass().getResource(resourcePath)).getImage();
        } catch (Exception e) {
            System.out.println("Failed to load image: " + resourcePath);
            e.printStackTrace();
            return null;
        }
    }

    private void initialize() {
        frame = new JFrame("NovaLearn - Learning Management System");
        frame.setResizable(false);
        frame.setBounds(100, 100, 1400, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));
        
        // Left Panel with background image
        JPanel schoolPic = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        schoolPic.setBounds(0, 0, 963, 763);
        frame.getContentPane().add(schoolPic);
        schoolPic.setLayout(null);

     // Right Panel (Login Panel)
        JPanel logInPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image loginBg = loadImage("/novaLearn/assets/loginPanel.png");
                if (loginBg != null) {
                    g.drawImage(loginBg, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        logInPanel.setBounds(961, 0, 425, 763);
        frame.getContentPane().add(logInPanel);
        logInPanel.setLayout(null);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setOpaque(false); // Make it transparent
        topPanel.setBounds(0, 0, 415, 295);
        logInPanel.add(topPanel);

        // Small Logo
        if (logoSmallImage != null) {
            JLabel smallLogoLabel = new JLabel(new ImageIcon(logoSmallImage.getScaledInstance(105, 105, Image.SCALE_SMOOTH)));
            smallLogoLabel.setBounds(160, 70, 115, 115);
            topPanel.add(smallLogoLabel);
        }

        // Big Logo
        if (logoImage != null) {
            JLabel logoLabel = new JLabel(new ImageIcon(logoImage.getScaledInstance(290, 84, Image.SCALE_SMOOTH)));
            logoLabel.setBounds(65, 190, 300, 87);
            topPanel.add(logoLabel);
        }

        // LMS Text
        JLabel lmsLabel = new JLabel("LEARNING MANAGEMENT SYSTEM", SwingConstants.CENTER);
        lmsLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
        lmsLabel.setForeground(new Color(55, 98, 144));
        lmsLabel.setBounds(17, 270, 395, 30);
        topPanel.add(lmsLabel);

        // Login Buttons
        JButton studentLogIn = createRoundedButton("STUDENT LOGIN");
        studentLogIn.setBounds(80, 370, 257, 58);
        logInPanel.add(studentLogIn);

        JButton instructorLogIn = createRoundedButton("INSTRUCTOR LOGIN");
        instructorLogIn.setBounds(80, 460, 257, 58);
        logInPanel.add(instructorLogIn);

        JButton adminLogIn = createRoundedButton("ADMIN LOGIN");
        adminLogIn.setBounds(80, 550, 257, 58);
        logInPanel.add(adminLogIn);

        // Actions
        studentLogIn.addActionListener(e -> openLoginWindow("STUDENT LOGIN"));
        instructorLogIn.addActionListener(e -> openLoginWindow("INSTRUCTOR LOGIN"));
        adminLogIn.addActionListener(e -> openLoginWindow("ADMIN LOGIN"));
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            protected void paintComponent(Graphics g) {
                if (getModel().isArmed()) {
                    g.setColor(new Color(45, 88, 134));
                } else {
                    g.setColor(new Color(55, 98, 144));
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }

            protected void paintBorder(Graphics g) {
                g.setColor(new Color(55, 98, 144));
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(0, 0, 0, 0));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        return button;
    }

    private void openLoginWindow(String title) {
        // Create the login frame
        JFrame loginFrame = new JFrame(title);
        loginFrame.setSize(400, 450);
        loginFrame.setLocationRelativeTo(frame);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setLayout(null);
        loginFrame.setResizable(false);
        loginFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

        // Main content panel
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 400, 410);
        loginFrame.add(panel);
        
        JLabel logoLabel = new JLabel(new ImageIcon(loadImage("/novaLearn/assets/logoD.png").getScaledInstance(185, 61, Image.SCALE_SMOOTH)));
        logoLabel.setBounds(8, 8, 185, 61);
        panel.add(logoLabel);

        // Lower panel for inputs and buttons
        JPanel lowerPanel = new JPanel(null);
        lowerPanel.setBackground(new Color(55, 98, 144));
        lowerPanel.setBounds(0, 80, 400, 370);
        panel.add(lowerPanel);
        
        JLabel loginTitle = new JLabel(title, SwingConstants.CENTER); 
        loginTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        loginTitle.setForeground(Color.WHITE);

        int frameWidth = 400;
        int titleWidth = 300; 
        int centerX = (frameWidth - titleWidth) / 2; 
        loginTitle.setBounds(centerX, 11, titleWidth, 40);

        lowerPanel.add(loginTitle);

        String loginLabel = title.equalsIgnoreCase("Student Login") ? "student number" : "username";
        String usernamePlaceholder = "Enter your " + loginLabel;

        // Add username field
        RoundedTextField usernameField = new RoundedTextField(10);
        usernameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        usernameField.setBounds(40, 60, 300, 40);
        usernameField.setForeground(Color.GRAY);
        usernameField.setText(usernamePlaceholder);
        lowerPanel.add(usernameField);

        // Only clear when actual typing starts
        usernameField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (usernameField.getText().equals(usernamePlaceholder)) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }
        });

        usernameField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setForeground(Color.GRAY);
                    usernameField.setText(usernamePlaceholder);
                }
            }
        });

        // Add password field
        RoundedPasswordField passwordField = new RoundedPasswordField(10);
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        passwordField.setBounds(40, 115, 300, 40);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0); // Show plain text for placeholder
        passwordField.setText("Enter your password");
        lowerPanel.add(passwordField);

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("Enter your password")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('\u2022'); // bullet character
                }
            }
        });

        passwordField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);
                    passwordField.setText("Enter your password");
                }
            }
        });
        
     // Add "Show Password" checkbox
        JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        showPasswordCheckBox.setForeground(Color.WHITE);
        showPasswordCheckBox.setOpaque(false);
        showPasswordCheckBox.setFocusPainted(false);
        showPasswordCheckBox.setBounds(45, 150, 150, 30); // Aligned with forgotLabel
        lowerPanel.add(showPasswordCheckBox);

        // Toggle password isibility
        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('\u2022');
            }
        });

        // Add login button
        JButton loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(Color.WHITE);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(Color.WHITE);
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 18));
        loginButton.setForeground(new Color(55, 98, 144));
        loginButton.setBounds(110, 220, 160, 50);
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setOpaque(false);
        lowerPanel.add(loginButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if ("1".equals(username) && "1".equals(password) && title.equals("STUDENT LOGIN")) {
                loginFrame.dispose();
                frame.dispose();
                StudentDashboardPanel.launchDashboard(); // Student Dashboard
            } else if ("2".equals(username) && "2".equals(password) && title.equals("INSTRUCTOR LOGIN")) {
                loginFrame.dispose();
                frame.dispose();
                InstructorDashboardPanel.launchDashboard(); // Instructor Dashboard
            } else if ("3".equals(username) && "3".equals(password) && title.equals("ADMIN LOGIN")) {
                loginFrame.dispose();
                frame.dispose();
                AdminDashboardPanel.launchDashboard(); // ✅ Admin Dashboard
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });


        // Display the login frame
        loginFrame.setVisible(true);
    }
}

// Rounded Border
class RoundedBorder extends AbstractBorder {
    private int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2d.dispose();
    }
}

// Rounded TextField
class RoundedTextField extends JTextField {
    public RoundedTextField(int columns) {
        super(columns);
        setOpaque(false);
        setBorder(new RoundedBorder(30));
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setCaretColor(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        g2.dispose();
        super.paintComponent(g);
    }
}

// Rounded PasswordField
class RoundedPasswordField extends JPasswordField {
    public RoundedPasswordField(int columns) {
        super(columns);
        setOpaque(false);
        setBorder(new RoundedBorder(30));
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setCaretColor(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        g2.dispose();
        super.paintComponent(g);
    }
}
