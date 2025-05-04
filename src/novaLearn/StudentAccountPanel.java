package novaLearn;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StudentAccountPanel extends JPanel {

    private JPanel parentPanel;
    private CardLayout cardLayout;

    public StudentAccountPanel(JPanel parentPanel, CardLayout cardLayout) {
        setLayout(null);
        setPreferredSize(new Dimension(1400, 800));
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;
        setupBanner();
        createAccountInfo();
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(55, 98, 144));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(30, 60, 90), 1, true));
    }

    private ImageIcon loadImage(String path, int width, int height) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                return new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            }
            System.err.println("Image not found: " + path);
            return new ImageIcon();
        } catch (Exception e) {
            System.err.println("Error loading image: " + path);
            return new ImageIcon();
        }
    }

    private void setupBanner() {
        Image bannerBg = new ImageIcon(getClass().getResource("/novaLearn/assets/dash_bg.png")).getImage();
        RoundedPanel banner = new RoundedPanel(bannerBg);
        banner.setBounds(25, 10, 1043, 171);
        banner.setLayout(null);
        add(banner);

        JLabel studentIcon = new JLabel(loadImage("/novaLearn/assets/Account tab icon (2).png", 100, 100));
        studentIcon.setBounds(30, 35, 100, 100);
        banner.add(studentIcon);

        JLabel nameLabel = new JLabel("SY, CHRISTIAN RAPHAEL");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(150, 50, 600, 40);
        banner.add(nameLabel);

        JLabel studentLabel = new JLabel("Student");
        studentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        studentLabel.setForeground(Color.WHITE);
        studentLabel.setBounds(150, 85, 200, 30);
        banner.add(studentLabel);
    }

    private void createAccountInfo() {
        RoundedPanel accountPanel = new RoundedPanel(Color.WHITE);
        accountPanel.setLayout(null);
        accountPanel.setBounds(25, 197, 1043, 461);
        accountPanel.setCornerRadius(30);
        add(accountPanel);

        String[][] details = {
            {"Student Number", "2301010"},
            {"Sex at Birth", "MALE"},
            {"Civil Status", "SINGLE"},
            {"Nationality", "FILIPINO"},
            {"Religion", "ROMAN CATHOLIC"},
            {"Date of Birth", "JANUARY 1, 2004"},
            {"Complete Address", "BRGY. PULO, CABUYAO CITY, LAGUNA"},
            {"Program", "BACHELOR OF SCIENCE IN COMPUTER SCIENCE"},
        };

        int startY = 20;
        for (int i = 0; i < details.length; i++) {
            int x = 30 + (i % 2) * 500;
            int y = startY + (i / 2) * 60;

            JLabel title = new JLabel(details[i][0]);
            title.setFont(new Font("Arial", Font.BOLD, 18));
            title.setBounds(x, y, 300, 25);
            accountPanel.add(title);

            JLabel value = new JLabel(details[i][1]);
            value.setFont(new Font("Arial", Font.PLAIN, 18));
            value.setBounds(x, y + 25, 450, 25);
            accountPanel.add(value);
        }

        JButton updateButton = new JButton("UPDATE PROFILE");
        styleButton(updateButton);
        updateButton.setBounds(251, 378, 200, 50);
        updateButton.addActionListener(e -> showUpdateProfilePopup());
        accountPanel.add(updateButton);

        JButton changePassButton = new JButton("CHANGE PASSWORD");
        styleButton(changePassButton);
        changePassButton.setBounds(548, 378, 220, 50);
        changePassButton.addActionListener(e -> showChangePasswordPopup());
        accountPanel.add(changePassButton);
    }

    private void showUpdateProfilePopup() {
        JFrame updateFrame = new JFrame("Update Profile");
        updateFrame.setSize(725, 496);
        updateFrame.setLocationRelativeTo(null);
        updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateFrame.setLayout(null);
        updateFrame.setResizable(false);
        updateFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 0, 725, 466);
        updateFrame.add(mainPanel);

        JLabel logoLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/novaLearn/assets/logoD.png"))
                .getImage().getScaledInstance(185, 61, Image.SCALE_SMOOTH)));
        logoLabel.setBounds(10, 10, 185, 61);
        mainPanel.add(logoLabel);

        JLabel titleLabel = new JLabel("<html><body style='text-align:right;'>UPDATE PROFILE</body></html>");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        titleLabel.setForeground(new Color(55, 98, 144));
        titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        titleLabel.setBounds(423, 31, 280, 40);
        mainPanel.add(titleLabel);

        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(new Color(55, 98, 144));
        formPanel.setBounds(0, 75, 715, 390);
        mainPanel.add(formPanel);

        // Buttons
        JButton saveBtn = new JButton("SAVE");
        styleButton(saveBtn);
        saveBtn.setBounds(215, 320, 120, 40);
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(Color.DARK_GRAY);
        saveBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(updateFrame, "Profile Updated Successfully!");
            updateFrame.dispose();
        });
        formPanel.add(saveBtn);

        JButton cancelBtn = new JButton("CANCEL");
        styleButton(cancelBtn);
        cancelBtn.setBounds(370, 322, 120, 40);
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setForeground(Color.DARK_GRAY);
        cancelBtn.addActionListener(e -> updateFrame.dispose());
        formPanel.add(cancelBtn);

        // Fields
        JLabel studentNumberLabel = new JLabel("STUDENT NUMBER: ");
        studentNumberLabel.setForeground(Color.WHITE);
        studentNumberLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        studentNumberLabel.setBounds(15, 15, 176, 13);
        formPanel.add(studentNumberLabel);

        JTextField studentNumberTf = new JTextField("2301010");
        studentNumberTf.setBounds(190, 11, 210, 25);
        studentNumberTf.setEditable(false);
        studentNumberTf.setBackground(Color.LIGHT_GRAY);
        formPanel.add(studentNumberTf);

        JLabel lastNameLabel = new JLabel("LAST NAME:");
        lastNameLabel.setForeground(Color.WHITE);
        lastNameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lastNameLabel.setBounds(15, 55, 150, 13);
        formPanel.add(lastNameLabel);

        JTextField lastNameTf = new JTextField();
        lastNameTf.setBounds(190, 46, 210, 25);
        formPanel.add(lastNameTf);

        JLabel firstNameLabel = new JLabel("FIRST NAME:");
        firstNameLabel.setForeground(Color.WHITE);
        firstNameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        firstNameLabel.setBounds(15, 91, 150, 13);
        formPanel.add(firstNameLabel);

        JTextField firstNameTf = new JTextField();
        firstNameTf.setBounds(190, 81, 210, 25);
        formPanel.add(firstNameTf);

        JLabel sexLabel = new JLabel("SEX:");
        sexLabel.setForeground(Color.WHITE);
        sexLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        sexLabel.setBounds(420, 55, 56, 13);
        formPanel.add(sexLabel);

        JComboBox<String> sexComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        sexComboBox.setBounds(567, 46, 122, 25);
        formPanel.add(sexComboBox);

        JLabel dobLabel = new JLabel("DATE OF BIRTH:");
        dobLabel.setForeground(Color.WHITE);
        dobLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        dobLabel.setBounds(420, 91, 132, 13);
        formPanel.add(dobLabel);

        JTextField dobTf = new JTextField();
        dobTf.setBounds(567, 81, 122, 25);
        formPanel.add(dobTf);

        JLabel programLabel = new JLabel("PROGRAM:");
        programLabel.setForeground(Color.WHITE);
        programLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        programLabel.setBounds(15, 130, 150, 20);
        formPanel.add(programLabel);

        JComboBox<String> programComboBox = new JComboBox<>(new String[]{
            "BS Computer Science", "BS Information Technology", "BS Information Systems",
            "BS Software Engineering", "BS Data Science"
        });
        programComboBox.setBounds(190, 125, 210, 25);
        formPanel.add(programComboBox);

        JLabel addressLabel = new JLabel("ADDRESS:");
        addressLabel.setForeground(Color.WHITE);
        addressLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        addressLabel.setBounds(15, 170, 150, 20);
        formPanel.add(addressLabel);

        JTextField addressTf = new JTextField();
        addressTf.setBounds(190, 165, 500, 25);
        formPanel.add(addressTf);

        JLabel religionLabel = new JLabel("RELIGION:");
        religionLabel.setForeground(Color.WHITE);
        religionLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        religionLabel.setBounds(15, 210, 150, 20);
        formPanel.add(religionLabel);

        JTextField religionTf = new JTextField();
        religionTf.setBounds(190, 205, 210, 25);
        formPanel.add(religionTf);

        JLabel nationalityLabel = new JLabel("NATIONALITY:");
        nationalityLabel.setForeground(Color.WHITE);
        nationalityLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        nationalityLabel.setBounds(15, 250, 150, 20);
        formPanel.add(nationalityLabel);

        JTextField nationalityTf = new JTextField();
        nationalityTf.setBounds(190, 245, 210, 25);
        formPanel.add(nationalityTf);

        JLabel civilStatusLabel = new JLabel("CIVIL STATUS:");
        civilStatusLabel.setForeground(Color.WHITE);
        civilStatusLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        civilStatusLabel.setBounds(420, 130, 150, 20);
        formPanel.add(civilStatusLabel);

        JComboBox<String> civilStatusComboBox = new JComboBox<>(new String[]{"Single", "Married", "Widowed", "Divorced"});
        civilStatusComboBox.setBounds(567, 125, 122, 25);
        formPanel.add(civilStatusComboBox);

        updateFrame.setVisible(true);
    }
    
    

    private void showChangePasswordPopup() {
        JFrame passFrame = new JFrame("Change Password");
        passFrame.setSize(483, 410);
        passFrame.setLocationRelativeTo(this);
        passFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        passFrame.setLayout(null);
        passFrame.setResizable(false);
        passFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 0, 483, 410);
        passFrame.add(mainPanel);

        // Logo
        JLabel logoLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/novaLearn/assets/logoD.png"))
                .getImage().getScaledInstance(185, 61, Image.SCALE_SMOOTH)));
        logoLabel.setBounds(10, 10, 185, 61);
        mainPanel.add(logoLabel);

        // Title
        JLabel titleLabel = new JLabel("CHANGE PASSWORD");
        titleLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        titleLabel.setForeground(new Color(55, 98, 144));
        titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        titleLabel.setBounds(205, 30, 257, 40);
        mainPanel.add(titleLabel);

        JPanel lowerPanel = new JPanel(null);
        lowerPanel.setBackground(new Color(55, 98, 144));
        lowerPanel.setBounds(0, 90, 483, 280);
        mainPanel.add(lowerPanel);

        // Load icons
        ImageIcon showIcon = loadIcon("/novaLearn/assets/showPW.png", 24, 24);
        ImageIcon hideIcon = loadIcon("/novaLearn/assets/hidePW.png", 24, 24);

        // Inner helper class
        class PasswordFieldWithToggle {
            JPasswordField field;
            JButton toggleBtn;
            boolean visible = false;

            PasswordFieldWithToggle(int x, int y) {
                field = new JPasswordField();
                field.setFont(new Font("Tahoma", Font.PLAIN, 14));
                field.setBounds(x, y, 380, 30);
                field.setEchoChar('•');
                lowerPanel.add(field);

                toggleBtn = new JButton(hideIcon);
                toggleBtn.setBounds(x + 385, y, 30, 30);
                toggleBtn.setOpaque(false);
                toggleBtn.setContentAreaFilled(false);
                toggleBtn.setBorderPainted(false);
                toggleBtn.setFocusPainted(false);
                toggleBtn.setBorder(null);
                toggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                toggleBtn.addActionListener(e -> {
                    visible = !visible;
                    field.setEchoChar(visible ? (char) 0 : '•');
                    toggleBtn.setIcon(visible ? showIcon : hideIcon);
                });
                lowerPanel.add(toggleBtn);
            }
        }

        JLabel currentLabel = new JLabel("CURRENT PASSWORD:");
        currentLabel.setForeground(Color.WHITE);
        currentLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        currentLabel.setBounds(30, 20, 200, 20);
        lowerPanel.add(currentLabel);
        PasswordFieldWithToggle current = new PasswordFieldWithToggle(30, 45);

        JLabel newLabel = new JLabel("NEW PASSWORD:");
        newLabel.setForeground(Color.WHITE);
        newLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        newLabel.setBounds(30, 85, 200, 20);
        lowerPanel.add(newLabel);
        PasswordFieldWithToggle newPw = new PasswordFieldWithToggle(30, 110);

        JLabel confirmLabel = new JLabel("CONFIRM NEW PASSWORD:");
        confirmLabel.setForeground(Color.WHITE);
        confirmLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        confirmLabel.setBounds(30, 150, 250, 20);
        lowerPanel.add(confirmLabel);
        PasswordFieldWithToggle confirm = new PasswordFieldWithToggle(30, 175);

        // Save Button
        JButton saveButton = new JButton("SAVE");
        styleButton(saveButton);
        saveButton.setBounds(125, 230, 100, 35);
        saveButton.setBackground(Color.WHITE);
        saveButton.setForeground(Color.DARK_GRAY);
        saveButton.addActionListener(e -> {
            String curr = new String(current.field.getPassword());
            String newPass = new String(newPw.field.getPassword());
            String confirmPass = new String(confirm.field.getPassword());

            if (curr.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                JOptionPane.showMessageDialog(passFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(passFrame, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // TODO: Implement actual password update logic
                JOptionPane.showMessageDialog(passFrame, "Password Updated Successfully!");
                passFrame.dispose();
            }
        });
        lowerPanel.add(saveButton);

        // Cancel Button
        JButton cancelButton = new JButton("CANCEL");
        styleButton(cancelButton);
        cancelButton.setBounds(250, 230, 100, 35);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.DARK_GRAY);
        cancelButton.addActionListener(e -> passFrame.dispose());
        lowerPanel.add(cancelButton);

        passFrame.setVisible(true);
    }

    private ImageIcon loadIcon(String path, int width, int height) {
        try {
            BufferedImage original = ImageIO.read(getClass().getResource(path));
            Image scaled = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage transparentImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = transparentImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(scaled, 0, 0, null);
            g2d.dispose();
            return new ImageIcon(transparentImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}