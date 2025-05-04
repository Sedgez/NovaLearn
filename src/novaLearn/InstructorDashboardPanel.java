package novaLearn;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InstructorDashboardPanel {

    private JFrame frame;
    private JLabel dateLabel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    private JButton[] sidebarButtons = new JButton[6]; // Dashboard, Courses, Grades, Notifications, Account, Logout
    private final String[] panelNames = { "Dashboard", "Manage Courses", "Track Students", "Generate Reports", "Account" };

    public static void launchDashboard() {
        EventQueue.invokeLater(() -> {
            try {
                InstructorDashboardPanel window = new InstructorDashboardPanel();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public InstructorDashboardPanel() {
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

        InstructorCoursesPanel  manageCourse = new InstructorCoursesPanel(contentPanel, cardLayout);
        InstructorNewSubmissionsPanel  newSubmission = new InstructorNewSubmissionsPanel (contentPanel, cardLayout);
        InstructorTrackStudentPanel trackStudent = new InstructorTrackStudentPanel(contentPanel, cardLayout);
        InstructorNotificationsPanel notifsPanel = new InstructorNotificationsPanel(contentPanel, cardLayout);
        InstructorAccountPanel accountPanel = new InstructorAccountPanel(contentPanel, cardLayout);
        InstructorGenerateReportPanel coursePanel = new InstructorGenerateReportPanel(contentPanel, cardLayout);
        InstructorCourseViewPanel courseView = new InstructorCourseViewPanel(contentPanel, cardLayout, "CCS108", "Object-Oriented Programming");
        contentPanel.add(courseView, "courseView");
        
        contentPanel.add(coursePanel, "Generate Reports");
//        
        contentPanel.add(newSubmission, "newSubmission");
        contentPanel.add(createDashboardView(), "Dashboard");
        contentPanel.add(manageCourse, "Manage Courses");
        contentPanel.add(trackStudent, "Track Students");
        contentPanel.add(notifsPanel, "notifications");
        contentPanel.add(accountPanel, "Account");
        
//        StudentCoursesPanel coursesPanel = new StudentCoursesPanel(contentPanel, cardLayout);
//        StudentPendingActivitiesPanel pendingPanel = new StudentPendingActivitiesPanel(contentPanel, cardLayout);
       
        
//        contentPanel.add(pendingPanel, "pendingActivities");
        contentPanel.add(createDashboardView(), "dashboard");
//        contentPanel.add(coursesPanel, "courses");
//        contentPanel.add(gradesPanel, "Track Students");


        cardLayout.show(contentPanel, "Dashboard");
        highlightButton(sidebarButtons[0]);
    }


    private void createSidebar() { // THE SIDE BAR WHICH CONSIST A LIST OF BUTTONS
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
        
        // NOTE THE BUTTON NAMES MUST BE SIMILAR TO THE "panelNames" FIELD BASED ON INDEX
        String[] buttonNames = { "Dashboard", "Manage Courses", "Track Students", "Generate Reports", "Account", "Logout" };
        String[] icons = {
            "/novaLearn/assets/Dashboard tab icon_blue.png",
            "/novaLearn/assets/Courses tab icon_blue.png",
            "/novaLearn/assets/Student icon_blue.png",
            "/novaLearn/assets/genReports icon.png",
            "/novaLearn/assets/Account tab icon.png",
            "/novaLearn/assets/Logout Logo_blue.png"
        };

        int y = 120, height = 60, gap = 10; // THE BOUNDS AND SIZE OF EACH BUTTON 
        for (int i = 0; i < buttonNames.length; i++) {
            JButton btn = createSidebarButton(buttonNames[i], icons[i], y + (height + gap) * i, i);
            sidebarButtons[i] = btn;
            sidebar.add(btn);
        }
    }
    
    // SIDEBAR BUTTONS WITH SPECIFIC INDEX 
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
                logout(); // TOGGELS THE LOGOUT FRAME
            } else {
                highlightButton(button); // HIGHLIGHTS THE SELECTED BUTTON
                cardLayout.show(contentPanel, panelNames[index]); // SHOWS THE SPECIFIC PANEL OF THE BUTTON BASED ON THE NAME
                // NOTE: THE THIS USES THE "panelNames"
            }
        });

        return button;
    }

    private void highlightButton(JButton selectedButton) { // THIS HIGHLIGHTS THE CLICKED BUTTON
        for (JButton btn : sidebarButtons) {
            if (btn != null) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(new Color(55, 71, 79));
            }
        }
        selectedButton.setBackground(new Color(220, 235, 252));
        selectedButton.setForeground(new Color(0, 102, 204));
    }

    private void createHeader() { // THIS IS THE HEADER PANEL OF THE DASHBOARD WHICH IS THE CLOCK & DATE
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

        header.add(createHeaderIcon("/novaLearn/assets/profile.png", 980, "Account"));
        header.add(createHeaderIcon("/novaLearn/assets/logOut.png", 1030, "logout"));
    }

    private JPanel createDashboardView() { // THE CONTENT PANEL OF THE DASHBOARD
        JPanel dashboard = new JPanel(null);
        dashboard.add(createBanner());

        JPanel dashboardCards = createDashboardCards(); // extract the panel
        dashboard.add(dashboardCards);

        Component[] cards = dashboardCards.getComponents();
        if (cards.length > 1) {
            JPanel activitiesCard = (JPanel) cards[1];
            activitiesCard.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    cardLayout.show(contentPanel, "newSubmission");
                    highlightButton(sidebarButtons[0]);
                }
            });
        }

        dashboard.add(createNoticeBoard());
        return dashboard;
    }

    private JPanel createBanner() { // THE BANNER OF THE DASHBOARD
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

        String honorific = "Prof."; // HONORIFIC FOR EACH PROF
        
        JLabel userName = new JLabel(honorific + " Janus Raymond Tan"); // IDEA: "honorific + (class)OfProfSamp" kaya nyo nayan HAHAHAHA
        userName.setFont(new Font("Segoe UI", Font.BOLD, 26));
        userName.setForeground(Color.WHITE);
        userName.setBounds(10, 30, 380, 30);
        welcomeContainer.add(userName);

        return banner;
    }

    private JPanel createDashboardCards() { // THE CARDS 
        JPanel dashboardContainer = new RoundedPanel(Color.WHITE);
        dashboardContainer.setLayout(null);
        dashboardContainer.setBounds(25, 197, 1043, 217);
        ((RoundedPanel) dashboardContainer).setCornerRadius(30);

        JPanel coursesCard = createDashboardCard("/novaLearn/assets/Enrolled courses icon.png", "33", "Assigned Courses", new Color(255, 147, 29));
        coursesCard.setBounds(20, 20, 320, 180);
        coursesCard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "Manage Courses");
                highlightButton(sidebarButtons[1]);
            }
        });
        dashboardContainer.add(coursesCard);

        JPanel newSubmission = createDashboardCard("/novaLearn/assets/new submission.png", "0", "New Submissions", new Color(52, 183, 241));
        newSubmission.setBounds(700, 20, 320, 180);
        dashboardContainer.add(newSubmission);
        
        JPanel trackStudent = createDashboardCard("/novaLearn/assets/Student icon_white.png", "1", "Total Students", new Color(242, 85, 43));
        trackStudent.setBounds( 360, 20, 320, 180);
        trackStudent.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "Track Students");
                highlightButton(sidebarButtons[2]); // Index 3 corresponds to the "Notifications" button
            }
        });
        dashboardContainer.add(trackStudent);
        return dashboardContainer;
    }

    private JPanel createNoticeBoard() { // THE NOTICEBOARD PANEL 
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

        JTextArea noticeText = new JTextArea("NovaLearn Scheduled Maintenance\n\nPlease be advised that NovaLearn will undergo scheduled maintenance on May 20, 2025, and will be unavailable from 12:00 AM to 5:00 AM. This downtime is necessary to ensure the platform continues to run smoothly and efficiently.\n\nThank you for your understanding and support.");
        noticeText.setEditable(false);
        noticeText.setBackground(new Color(229, 245, 252));
        noticeText.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        noticeText.setForeground(Color.BLACK);
        noticeText.setLineWrap(true);
        noticeText.setWrapStyleWord(true);
        noticeText.setOpaque(false);
        noticeText.setBounds(20, 60, 940, 180);
        noticeBoard.add(noticeText);

        return noticeBoard;
    }

    private JButton createHeaderIcon(String path, int x, String targetPanel) { // THIS IS RELATED TO THE 3 BUTTON ON THE TOP RIGHT
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

    private JPanel createDashboardCard(String iconPath, String number, String labelText, Color backgroundColor) { // THIS IS RELATED TO WHAT MAKES THE CARDS ROUND
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
        numberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numberLabel.setBounds(70, 0, 280, 90);
        card.add(numberLabel);

        JLabel textLabel = new JLabel("<html><body style='text-align:right;'>" + labelText.replace(" ", "<br>") + "</html>");
        textLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        textLabel.setForeground(Color.WHITE);
        textLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        textLabel.setBounds(50, 30, 280, 35);
        card.add(textLabel);

        card.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                numberLabel.setBounds(card.getWidth() - 130 - 20, 30, 130, 90);
                textLabel.setBounds(card.getWidth() - 130 - 20, 120, 130, 50);
            }
        });

        return card;
    }

    private void updateDate() { // THE DATE THAT IS IN THE HEADER
        new Timer(1000, e -> dateLabel.setText(new SimpleDateFormat("MMMM dd, hh:mm:ss a").format(new Date()))).start();
    }

    private void logout() { 
        if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            frame.dispose(); // THIS DISPOSES THE LOGOUT FRAME
            NovaLearn.main(null); // RESTARTS THE PROGRAM BACK TO "NovaLearn.java"
        }
    }

    private ImageIcon loadImage(String path, int width, int height) { // METHOD TO LOAD IMAGE 
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
