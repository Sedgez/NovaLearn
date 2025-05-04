package novaLearn;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentStreamContentPanel extends JPanel {

    public static final String COURSE_VIEW = "courseView";

    public StudentStreamContentPanel(JPanel parentPanel, CardLayout cardLayout, String title, String content, String datePosted, String attachmentLabel) {
        setLayout(null);
        setBounds(0, 0, 1100, 700);
        setBackground(new Color(242, 242, 242));

        setupBanner();
        setupContent(parentPanel, cardLayout, title, content, datePosted, attachmentLabel);
    }

    private void setupBanner() {
        ImageIcon bannerIcon = new ImageIcon(getClass().getResource("/novaLearn/assets/dash_bg.png"));
        Image bannerImage = bannerIcon.getImage();

        RoundedPanel banner = new RoundedPanel(bannerImage);
        banner.setBounds(25, 10, 1043, 100);
        banner.setLayout(null);
        add(banner);
    }

    private void setupContent(JPanel parentPanel, CardLayout cardLayout, String title, String content, String datePosted, String attachmentLabel) {
        RoundedPanel contentPanel = new RoundedPanel(Color.WHITE);
        contentPanel.setCornerRadius(30);
        contentPanel.setLayout(null);
        contentPanel.setBounds(25, 130, 1043, 538);
        add(contentPanel);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(255, 153, 0));
        headerPanel.setBounds(20, 20, 1000, 40);

        JLabel headerLabel = new JLabel("Content Details");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        contentPanel.add(headerPanel);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBounds(40, 75, 800, 30);
        contentPanel.add(titleLabel);

        JLabel dateLabel = new JLabel("Date Posted: " + datePosted);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        dateLabel.setForeground(Color.GRAY);
        dateLabel.setBounds(40, 100, 400, 25);
        contentPanel.add(dateLabel);

        JTextArea bodyArea = new JTextArea(content);
        bodyArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        bodyArea.setLineWrap(true);
        bodyArea.setWrapStyleWord(true);
        bodyArea.setEditable(false);
        bodyArea.setOpaque(false);
        bodyArea.setFocusable(false);
        bodyArea.setBounds(40, 140, 920, 140);
        contentPanel.add(bodyArea);

        JLabel attachmentText = new JLabel("Attachment: ");
        attachmentText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        attachmentText.setBounds(40, 300, 100, 25);
        contentPanel.add(attachmentText);

        JLabel fileLabel = new JLabel("<html><u>" + attachmentLabel + "</u></html>");
        fileLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        fileLabel.setForeground(new Color(0, 102, 204));
        fileLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fileLabel.setBounds(140, 300, 400, 25);
        fileLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileManager.openAttachment(StudentStreamContentPanel.this, attachmentLabel);
            }
        });
        contentPanel.add(fileLabel);

        JButton backButton = new JButton("← Back");
        backButton.setBounds(30, 475, 100, 40);
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        backButton.setBackground(new Color(255, 153, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(new LineBorder(new Color(255, 153, 0), 1, true));
        backButton.addActionListener(e -> cardLayout.show(parentPanel, COURSE_VIEW));
        contentPanel.add(backButton);
    }
} 
