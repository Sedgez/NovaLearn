package novaLearn;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentDashboardPanel {

    private JFrame frame;
    private JLabel dateLabel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JLabel notifCountLabel = new JLabel(); // Initialize upfront

    private JButton[] sidebarButtons = new JButton[6]; // Dashboard, Courses, Grades, Notifications, Account, Logout
    private final String[] panelNames = { "dashboard", "courses", "grades", "notifications", "account" };

    public static void launchDashboard() {
        EventQueue.invokeLater(() -> {
            try {
                StudentDashboardPanel window = new StudentDashboardPanel();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public StudentDashboardPanel() {
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

        StudentCoursesPanel coursesPanel = new StudentCoursesPanel(contentPanel, cardLayout);
        StudentPendingActivitiesPanel pendingPanel = new StudentPendingActivitiesPanel(contentPanel, cardLayout);
        StudentGradesPanel gradesPanel = new StudentGradesPanel(contentPanel, cardLayout);
        StudentNotificationsPanel notifsPanel = new StudentNotificationsPanel(contentPanel, cardLayout);
        StudentAccountPanel accountPanel = new StudentAccountPanel(contentPanel, cardLayout);
        StudentCourseViewPanel courseView = new StudentCourseViewPanel(contentPanel, cardLayout, "CS101", "Object-Oriented Programming");
        contentPanel.add(courseView, "courseView");
        
        contentPanel.add(pendingPanel, "pendingActivities");
        contentPanel.add(createDashboardView(), "dashboard");
        contentPanel.add(coursesPanel, "courses");
        contentPanel.add(gradesPanel, "grades");
        contentPanel.add(notifsPanel, "notifications");
        contentPanel.add(accountPanel, "account");

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

        String[] buttonNames = { "Dashboard", "Courses", "Grades", "Notifications", "Account", "Logout" };
        String[] icons = {
            "/novaLearn/assets/Dashboard tab icon_blue.png",
            "/novaLearn/assets/Courses tab icon_blue.png",
            "/novaLearn/assets/Grades tab icon_blue.png",
            "/novaLearn/assets/Notif tab icon_blue.png",
            "/novaLearn/assets/Account tab icon.png",
            "/novaLearn/assets/Logout Logo_blue.png"
        };

        int y = 120, height = 60, gap = 10;
        for (int i = 0; i < buttonNames.length; i++) {
            JButton btn = createSidebarButton(buttonNames[i], icons[i], y + (height + gap) * i, i);
            sidebarButtons[i] = btn;
            sidebar.add(btn);

            if (i == 3) { // Notifications
            	notifCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            	notifCountLabel.setForeground(Color.WHITE);
            	notifCountLabel.setBackground(new Color(220, 20, 60));
            	notifCountLabel.setOpaque(true);
            	notifCountLabel.setVisible(true);
            	notifCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
            	notifCountLabel.setVerticalAlignment(SwingConstants.CENTER);
            	notifCountLabel.setBounds(250, btn.getY(), 24, 24); // Bigger size

            	// Rounded border using full corner radius
            	notifCountLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
            	notifCountLabel.setPreferredSize(new Dimension(24, 24));
            	notifCountLabel.setText("0");

                btn.setLayout(null); // Ensure custom layout
                notifCountLabel.setBounds(250, 20, 24, 24); // relative to the button
                btn.add(notifCountLabel);            }
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

        header.add(createHeaderIcon("/novaLearn/assets/profile.png", 980, "account"));
        header.add(createHeaderIcon("/novaLearn/assets/logOut.png", 1030, "logout"));
    }

    private JPanel createDashboardView() {
        JPanel dashboard = new JPanel(null);
        dashboard.add(createBanner());

        JPanel dashboardCards = createDashboardCards(); // extract the panel
        dashboard.add(dashboardCards);

        // Attach listener to the "Pending Activities" card inside the dashboardCards panel
        Component[] cards = dashboardCards.getComponents();
        if (cards.length > 1) {
            JPanel activitiesCard = (JPanel) cards[1]; // Second card (Pending Activities)
            activitiesCard.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    cardLayout.show(contentPanel, "pendingActivities");
                    highlightButton(sidebarButtons[0]); // You can skip or change this if needed
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

        JLabel userName = new JLabel("Sy, Christian Raphael");
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

        JPanel coursesCard = createDashboardCard("/novaLearn/assets/Enrolled courses icon.png", "33", "Enrolled Courses", new Color(255, 147, 29));
        coursesCard.setBounds(20, 20, 320, 180);
        coursesCard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "courses");
                highlightButton(sidebarButtons[1]); // Index 1 corresponds to the "Courses" button
            }
        });
        dashboardContainer.add(coursesCard);

        JPanel activitiesCard = createDashboardCard("/novaLearn/assets/Pending act icon.png", "1", "Pending Activities", new Color(242, 85, 43));
        activitiesCard.setBounds(360, 20, 320, 180);
        dashboardContainer.add(activitiesCard);

        JPanel notificationsCard = createDashboardCard("/novaLearn/assets/Notif tab icon_white.png", "0", "New Notifications", new Color(52, 183, 241));
        notificationsCard.setBounds(700, 20, 320, 180);
        notificationsCard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "notifications");
                highlightButton(sidebarButtons[3]); // Index 3 corresponds to the "Notifications" button
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

//====== RoundedPanel Class ======
class RoundedPanel extends JPanel {
	private Color backgroundColor;
	private int cornerRadius = 15;
	private Image backgroundImage;
	
	public RoundedPanel(Color bgColor) {
	   super();
	   backgroundColor = bgColor;
	   setOpaque(false);
	}
	
	public RoundedPanel(Image bgImage) {
	   super();
	   backgroundImage = bgImage;
	   setOpaque(false);
	}
	
	public void setCornerRadius(int radius) {
	   this.cornerRadius = radius;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   Dimension arcs = new Dimension(cornerRadius, cornerRadius);
	   Graphics2D graphics = (Graphics2D) g;
	   graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	
	   if (backgroundImage != null) {
	       graphics.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcs.width, arcs.height));
	       graphics.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	   } else if (backgroundColor != null) {
	       graphics.setColor(backgroundColor);
	       graphics.fillRoundRect(0, 0, getWidth(), getHeight(), arcs.width, arcs.height);
	   }
	}
}