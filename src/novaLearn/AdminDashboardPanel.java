package novaLearn;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminDashboardPanel {

    private JFrame frame;
    private JLabel dateLabel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JTextArea noticeTextArea;

    private JButton[] sidebarButtons = new JButton[6]; // Dashboard, Courses, Instructors, Students, Reports, Logout
    private final String[] panelNames = { "dashboard", "adminCoursesTracker", "adminInstructorTracker", "adminStudentsTracker", "generateReports" };

    public static void launchDashboard() {
        EventQueue.invokeLater(() -> {
            try {
                AdminDashboardPanel window = new AdminDashboardPanel();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AdminDashboardPanel() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("NovaLearn - Learning Management System");
        frame.setResizable(false);
        frame.setBounds(100, 100, 1400, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

        createSidebar();
        createHeader();

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBounds(290, 80, 1110, 683);
        frame.getContentPane().add(contentPanel);

        AdminCoursesTrackerPanel coursesPanel = new AdminCoursesTrackerPanel(contentPanel, cardLayout);
        AdminInstructorTrackerPanel instructorPanel = new AdminInstructorTrackerPanel(contentPanel, cardLayout);
        AdminStudentTrackerPanel studentsPanel = new AdminStudentTrackerPanel(contentPanel, cardLayout);
        AdminGenerateReportPanel reportsPanel = new AdminGenerateReportPanel(contentPanel, cardLayout);
        
        contentPanel.add(createDashboardView(), "dashboard");
        contentPanel.add(coursesPanel, "adminCoursesTracker");
        contentPanel.add(instructorPanel, "adminInstructorTracker");
        contentPanel.add(studentsPanel, "adminStudentsTracker");
        contentPanel.add(reportsPanel, "generateReports");

        cardLayout.show(contentPanel, "dashboard");
        highlightButton(sidebarButtons[0]);
    }

    private void createSidebar() {
        JPanel sidebar = new JPanel(null);
        sidebar.setBackground(Color.WHITE);
        sidebar.setBounds(0, 0, 290, 763);
        frame.getContentPane().add(sidebar);

        JLabel logoLabel = new JLabel(loadImage("/novaLearn/assets/logoD.png", 220, 77));
        logoLabel.setBounds(35, 10, 220, 77);
        sidebar.add(logoLabel);

        JLabel lmsLabel = new JLabel("LEARNING MANAGEMENT SYSTEM", SwingConstants.CENTER);
        lmsLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lmsLabel.setForeground(new Color(100, 100, 100));
        lmsLabel.setBounds(10, 90, 270, 20);
        sidebar.add(lmsLabel);

        String[] buttonNames = { "Dashboard", "Courses Tracker", "Instructor Tracker", "Students Tracker", "Generate Reports", "Logout" };
        String[] icons = {
            "/novaLearn/assets/Dashboard tab icon_blue.png",
            "/novaLearn/assets/Courses tab icon_blue.png",
            "/novaLearn/assets/instructorAdmin_blue.png",
            "/novaLearn/assets/studentAdmin_blue.png",
            "/novaLearn/assets/GenerateReport_Admin.png",
            "/novaLearn/assets/Logout Logo_blue.png"
        };

        int y = 120, height = 60, gap = 10;
        for (int i = 0; i < buttonNames.length; i++) {
            JButton btn = createSidebarButton(buttonNames[i], icons[i], y + (height + gap) * i, i);
            sidebarButtons[i] = btn;
            sidebar.add(btn);
        }
    }

    private JButton createSidebarButton(String name, String iconPath, int y, int index) {
        JButton button = new JButton(name, loadImage(iconPath, 30, 30));
        button.setBounds(0, y, 290, 60);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(20);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setForeground(new Color(55, 71, 79));
        button.setBackground(Color.WHITE);

        button.addActionListener(e -> {
            if (name.equals("Logout")) {
                logout();
            } else {
                highlightButton(button);
                cardLayout.show(contentPanel, panelNames[index]);
            }
        });

        return button;
    }

    private void highlightButton(JButton selectedButton) {
        for (JButton btn : sidebarButtons) {
            if (btn != null) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(new Color(55, 71, 79));
            }
        }
        selectedButton.setBackground(new Color(220, 235, 252));
        selectedButton.setForeground(new Color(0, 102, 204));
    }

    private void createHeader() {
        JPanel header = new JPanel(null);
        header.setBackground(new Color(245, 247, 251));
        header.setBounds(290, 0, 1110, 80);
        frame.getContentPane().add(header);

        JLabel dayLabel = new JLabel(new SimpleDateFormat("EEEE").format(new Date()));
        dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        dayLabel.setBounds(24, 10, 300, 30);
        header.add(dayLabel);

        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dateLabel.setBounds(24, 40, 300, 20);
        header.add(dateLabel);
        updateDate();

        header.add(createHeaderIcon("/novaLearn/assets/logOut.png", 1030, "logout"));
    }

    private JPanel createDashboardView() {
        JPanel dashboard = new JPanel(null);
        dashboard.add(createBanner());

        JPanel dashboardCards = createDashboardCards();
        dashboard.add(dashboardCards);

        Component[] cards = dashboardCards.getComponents();
        if (cards.length > 1) {
            JPanel activitiesCard = (JPanel) cards[1]; 
            activitiesCard.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    cardLayout.show(contentPanel, "adminInstructorTracker");
                    highlightButton(sidebarButtons[2]);
                }
            });
        }

        dashboard.add(createNoticeBoard());
        return dashboard;
    }

    private JPanel createBanner() {
        Image bannerImage = loadImageRaw("/novaLearn/assets/dash_bg.png");
        RoundedPanel banner = new RoundedPanel(bannerImage);
        banner.setBounds(25, 10, 1043, 171);
        banner.setLayout(null);

        JPanel welcomeContainer = new JPanel(null);
        welcomeContainer.setOpaque(false);
        welcomeContainer.setBounds(24, 79, 400, 70);
        banner.add(welcomeContainer);

        JLabel welcomeText = new JLabel("Welcome back,");
        welcomeText.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        welcomeText.setForeground(Color.WHITE);
        welcomeText.setBounds(10, 5, 380, 25);
        welcomeContainer.add(welcomeText);

        JLabel userName = new JLabel("Admin");
        userName.setFont(new Font("Segoe UI", Font.BOLD, 26));
        userName.setForeground(Color.WHITE);
        userName.setBounds(10, 30, 380, 30);
        welcomeContainer.add(userName);

        return banner;
    }

    private JPanel createDashboardCards() {
        JPanel dashboardContainer = new RoundedPanel(Color.WHITE);
        dashboardContainer.setLayout(null);
        dashboardContainer.setBounds(25, 197, 1043, 217);
        ((RoundedPanel) dashboardContainer).setCornerRadius(30);

        JPanel coursesCard = createDashboardCard("/novaLearn/assets/studentAdmin_white.png", "69", "Active Students", new Color(255, 147, 29));
        coursesCard.setBounds(20, 20, 320, 180);
        coursesCard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "adminStudentsTracker");
                highlightButton(sidebarButtons[3]);
            }
        });
        dashboardContainer.add(coursesCard);

        JPanel activitiesCard = createDashboardCard("/novaLearn/assets/instructorAdmin_white.png", "8", "Active Instructors", new Color(242, 85, 43));
        activitiesCard.setBounds(360, 20, 320, 180);
        activitiesCard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "adminInstructorTracker");
                highlightButton(sidebarButtons[2]);
            }
        });
        dashboardContainer.add(activitiesCard);

        JPanel notificationsCard = createDashboardCard("/novaLearn/assets/Courses tab icon_white.png", "5", "Active Courses", new Color(52, 183, 241));
        notificationsCard.setBounds(700, 20, 320, 180);
        notificationsCard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "adminCoursesTracker");
                highlightButton(sidebarButtons[1]);
            }
        });
        dashboardContainer.add(notificationsCard);

        return dashboardContainer;
    }

    private JPanel createNoticeBoard() {
        JPanel noticeBoard = new JPanel(null);
        noticeBoard.setBackground(new Color(229, 245, 252));
        noticeBoard.setBounds(25, 430, 1043, 230);
        noticeBoard.setBorder(new LineBorder(new Color(0, 102, 204), 2, true));

        JLabel noticeIcon = new JLabel(loadImage("/novaLearn/assets/Announcement icon.png", 40, 40));
        noticeIcon.setBounds(20, 10, 40, 40);
        noticeBoard.add(noticeIcon);

        JLabel noticeTitle = new JLabel("Notice Board");
        noticeTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        noticeTitle.setForeground(new Color(0, 51, 102));
        noticeTitle.setBounds(70, 15, 400, 30);
        noticeBoard.add(noticeTitle);
        
        JButton editBtn = new JButton(loadImage("/novaLearn/assets/edit_blue.png", 27, 27));
        editBtn.setBounds(1000, 15, 24, 24);               
        editBtn.setBorderPainted(false);
        editBtn.setContentAreaFilled(false);
        editBtn.setToolTipText("Edit notice");
        editBtn.addActionListener(e -> showEditNoticePopup(noticeTextArea));
        noticeBoard.add(editBtn);

        noticeTextArea = new JTextArea("NovaLearn Scheduled Maintenance\n\nPlease be advised that NovaLearn will undergo scheduled maintenance on May 20, 2025, and will be unavailable from 12:00 AM to 5:00 AM. This downtime is necessary to ensure the platform continues to run smoothly and efficiently.\n\nThank you for your understanding and support.");
        noticeTextArea.setEditable(false);
        noticeTextArea.setBackground(new Color(229, 245, 252));
        noticeTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        noticeTextArea.setForeground(Color.BLACK);
        noticeTextArea.setLineWrap(true);
        noticeTextArea.setWrapStyleWord(true);
        noticeTextArea.setOpaque(false);
        noticeTextArea.setBounds(20, 60, 940, 180);
        noticeBoard.add(noticeTextArea);

        return noticeBoard; 
    }


    private JPanel createPlaceholderPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 26));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JButton createHeaderIcon(String path, int x, String targetPanel) {
        JButton button = new JButton(loadImage(path, 30, 30));
        button.setBounds(x, 20, 40, 40);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        if (targetPanel.equals("logout")) {
            button.addActionListener(e -> logout());
        } else {
            button.addActionListener(e -> cardLayout.show(contentPanel, targetPanel));
        }

        return button;
    }

    private JPanel createDashboardCard(String iconPath, String number, String labelText, Color backgroundColor) {
        JPanel card = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(backgroundColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 40, 40);
                g2.dispose();
            }
        };

        card.setOpaque(false);

        card.add(new JLabel(loadImage(iconPath, 150, 150)) {{
            setBounds(10, 20, 150, 150);
        }});

        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(new Font("Arial", Font.BOLD, 90));
        numberLabel.setForeground(Color.WHITE);
        numberLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Align text inside the label
        numberLabel.setBounds(70, 0, 280, 90); // Nudged up by adjusting the y coordinate
        card.add(numberLabel);

        JLabel textLabel = new JLabel("<html><body style='text-align:right;'>" + labelText.replace(" ", "<br>") + "</html>");
        textLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        textLabel.setForeground(Color.WHITE);
        textLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Align text inside the label
        textLabel.setBounds(50, 30, 280, 35); // Nudged up by adjusting the y coordinate
        card.add(textLabel);


        // Optional: Add a ComponentListener to dynamically adjust when the card resizes
        card.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                numberLabel.setBounds(card.getWidth() - 130 - 20, 30, 130, 90);
                textLabel.setBounds(card.getWidth() - 130 - 20, 120, 130, 50);
            }
        });

        return card;
    }
    
    private void showEditNoticePopup(JTextArea noticeTextArea) {
        JFrame noticeFrame = new JFrame("Edit Notice Board");
        noticeFrame.setSize(725, 496);
        noticeFrame.setLocationRelativeTo(null);
        noticeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        noticeFrame.setLayout(null);
        noticeFrame.setResizable(false);
        noticeFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 0, 725, 466);
        noticeFrame.add(mainPanel);

        JLabel logoLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/novaLearn/assets/logoD.png"))
                .getImage().getScaledInstance(185, 61, Image.SCALE_SMOOTH)));
        logoLabel.setBounds(10, 10, 185, 61);
        mainPanel.add(logoLabel);

        JLabel titleLabel = new JLabel("<html><body style='text-align:right;'>EDIT NOTICE BOARD</body></html>");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        titleLabel.setForeground(new Color(55, 98, 144));
        titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        titleLabel.setBounds(423, 31, 280, 40);
        mainPanel.add(titleLabel);

        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(new Color(55, 98, 144));
        formPanel.setBounds(0, 75, 715, 390);
        mainPanel.add(formPanel);

        JTextArea inputArea = new JTextArea(noticeTextArea.getText());
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(inputArea);
        scrollPane.setBounds(30, 30, 655, 250);
        formPanel.add(scrollPane);

        JLabel charCountLabel = new JLabel(inputArea.getText().length() + "/330");
        charCountLabel.setForeground(Color.WHITE);
        charCountLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        charCountLabel.setBounds(600, 290, 80, 20);
        formPanel.add(charCountLabel);

        inputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (inputArea.getText().length() >= 330) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                charCountLabel.setText(inputArea.getText().length() + "/330");
            }
        });

        JButton saveBtn = new JButton("SAVE");
        styleButton(saveBtn);
        saveBtn.setBounds(215, 320, 120, 40);
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(Color.DARK_GRAY);
        saveBtn.addActionListener(ev -> {
            noticeTextArea.setText(inputArea.getText().trim());
            JOptionPane.showMessageDialog(noticeFrame, "Notice Board Updated Successfully!");
            noticeFrame.dispose();
        });
        formPanel.add(saveBtn);

        JButton cancelBtn = new JButton("CANCEL");
        styleButton(cancelBtn);
        cancelBtn.setBounds(370, 322, 120, 40);
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setForeground(Color.DARK_GRAY);
        cancelBtn.addActionListener(ev -> noticeFrame.dispose());
        formPanel.add(cancelBtn);

        noticeFrame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(55, 98, 144));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(30, 60, 90), 1, true));
    }
    
    private void updateDate() {
        new Timer(1000, e -> dateLabel.setText(new SimpleDateFormat("MMMM dd, hh:mm:ss a").format(new Date()))).start();
    }

    private void logout() {
        if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            frame.dispose();
            NovaLearn.main(null);
        }
    }

    private ImageIcon loadImage(String path, int width, int height) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) return new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            System.err.println("Image not found: " + path);
        } catch (Exception e) {
            System.err.println("Error loading image: " + path);
            e.printStackTrace();
        }
        return new ImageIcon();
    }

    private Image loadImageRaw(String path) {
        try {
            return new ImageIcon(getClass().getResource(path)).getImage();
        } catch (Exception e) {
            System.err.println("Could not load image: " + path);
            return null;
        }
    }
}