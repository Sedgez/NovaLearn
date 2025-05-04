package novaLearn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class InstructorNotificationsPanel extends JPanel {

    private Image backgroundImage;
    private Image logoImage;
    private JPanel parentPanel;
    private CardLayout cardLayout;
    private JPanel notificationListPanel;

    public InstructorNotificationsPanel(JPanel parentPanel, CardLayout cardLayout) {
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;

        setLayout(null);
        setPreferredSize(new Dimension(1400, 800));

        loadAssets();
        setupBanner();
        setupNotifications();
    }

    private void loadAssets() {
        backgroundImage = new ImageIcon(getClass().getResource("/novaLearn/assets/dash_bg.png")).getImage();
        logoImage = new ImageIcon(getClass().getResource("/novaLearn/assets/logoD.png")).getImage();
    }

    private void setupBanner() {
        RoundedPanel banner = new RoundedPanel(backgroundImage);
        banner.setBounds(25, 10, 1043, 171);
        banner.setLayout(null);
        banner.setCornerRadius(30);
        add(banner);

        JLabel notifIcon = new JLabel(loadImage("/novaLearn/assets/Notif tab icon_white.png", 40, 40));
        notifIcon.setBounds(30, 100, 40, 40);
        banner.add(notifIcon);

        JLabel notifLabel = new JLabel("Notifications");
        notifLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        notifLabel.setForeground(Color.WHITE);
        notifLabel.setBounds(75, 105, 300, 40);
        banner.add(notifLabel);

        JButton markAllReadButton = createRoundedButton("Mark All as Read");
        markAllReadButton.setBounds(850, 105, 160, 40);
        markAllReadButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        banner.add(markAllReadButton);

        markAllReadButton.addActionListener(e -> markAllAsRead());
    }

    private void setupNotifications() {
        RoundedPanel notifPanel = new RoundedPanel(Color.WHITE);
        notifPanel.setCornerRadius(30);
        notifPanel.setLayout(new BorderLayout());
        notifPanel.setBounds(25, 197, 1043, 464);
        add(notifPanel);

        notificationListPanel = new JPanel();
        notificationListPanel.setLayout(new BoxLayout(notificationListPanel, BoxLayout.Y_AXIS));
        notificationListPanel.setBackground(Color.WHITE);
        notificationListPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(notificationListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        notificationListPanel.setOpaque(false);

        notifPanel.add(scrollPane, BorderLayout.CENTER);

        // Sample notifications for instructors
        addNotification("New assignment submissions are available for review.");
        addNotification("You have been added as an instructor for CCS112 - Software Engineering.");
        addNotification("A student has posted a question in the CCS110 discussion forum.");
    }

    private void addNotification(String message) {
        Color unreadColor = new Color(208, 231, 255); // Light blue
        RoundedPanel notifItem = new RoundedPanel(unreadColor);
        notifItem.setLayout(new BorderLayout());
        notifItem.setCornerRadius(20);
        notifItem.setMaximumSize(new Dimension(1000, 60));
        notifItem.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel notifLabel = new JLabel(message);
        notifLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        notifItem.add(notifLabel, BorderLayout.CENTER);
        notificationListPanel.add(notifItem);
        notificationListPanel.add(Box.createVerticalStrut(10));
        revalidate();
        repaint();
    }

    private void markAllAsRead() {
        Component[] components = notificationListPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof RoundedPanel) {
                comp.setBackground(new Color(180, 180, 180)); // Gray
                comp.repaint();
            }
        }
        JOptionPane.showMessageDialog(this, "All notifications marked as read.");
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

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            protected void paintComponent(Graphics g) {
                g.setColor(Color.WHITE);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }

            protected void paintBorder(Graphics g) {
                g.setColor(Color.BLACK);
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        return button;
    }

    // Inner class for rounded panels
    class RoundedPanel extends JPanel {
        private int cornerRadius = 20;
        private Image backgroundImage;

        public RoundedPanel(Color bgColor) {
            setOpaque(false);
            setBackground(bgColor);
        }

        public RoundedPanel(Image bgImage) {
            this.backgroundImage = bgImage;
            setOpaque(false);
        }

        public void setCornerRadius(int radius) {
            this.cornerRadius = radius;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Shape clip = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.setClip(clip);

            if (backgroundImage != null) {
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g2.setColor(getBackground());
                g2.fill(clip);
            }

            g2.dispose();
        }
    }
}
