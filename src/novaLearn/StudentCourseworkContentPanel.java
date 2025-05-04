package novaLearn;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class StudentCourseworkContentPanel extends JPanel {

    private JLabel uploadedFileLabel;
    private JButton submitButton;
    private boolean isSubmitted = false;
    private final JPanel parentPanel;
    private final CardLayout cardLayout;
    private final String courseworkTitle;

    public static final String COURSE_VIEW = "courseView";

    public StudentCourseworkContentPanel(JPanel parentPanel, CardLayout cardLayout, String title, String description, String dueDate, String grade, String attachmentLabel) {
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;
        this.courseworkTitle = title;

        setLayout(null);
        setBounds(0, 0, 1100, 700);
        setBackground(new Color(242, 242, 242));

        setupBanner();
        setupContent(title, description, dueDate, grade, attachmentLabel);
    }

    private void setupBanner() {
        ImageIcon bannerIcon = new ImageIcon(getClass().getResource("/novaLearn/assets/dash_bg.png"));
        Image bannerImage = bannerIcon.getImage();

        RoundedPanel banner = new RoundedPanel(bannerImage);
        banner.setBounds(25, 10, 1043, 100);
        banner.setLayout(null);
        add(banner);
    }

    private void setupContent(String title, String description, String dueDate, String grade, String attachmentLabel) {
        RoundedPanel contentPanel = new RoundedPanel(Color.WHITE);
        contentPanel.setCornerRadius(30);
        contentPanel.setLayout(null);
        contentPanel.setBounds(25, 130, 1043, 538);
        add(contentPanel);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(255, 153, 0));
        headerPanel.setBounds(20, 20, 1000, 40);
        JLabel headerLabel = new JLabel("Coursework Details");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        contentPanel.add(headerPanel);

        JLabel titleLabel = new JLabel("Title: " + title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBounds(40, 75, 900, 30);
        contentPanel.add(titleLabel);

        JLabel dueLabel = new JLabel("Due Date: " + dueDate);
        dueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        dueLabel.setForeground(Color.GRAY);
        dueLabel.setBounds(40, 105, 400, 25);
        contentPanel.add(dueLabel);

        JLabel gradeLabel = new JLabel("Grade: " + grade);
        gradeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        gradeLabel.setForeground(new Color(0, 102, 0));
        gradeLabel.setBounds(200, 105, 200, 25);
        contentPanel.add(gradeLabel);

        JTextArea bodyArea = new JTextArea(description);
        bodyArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        bodyArea.setLineWrap(true);
        bodyArea.setWrapStyleWord(true);
        bodyArea.setEditable(false);
        bodyArea.setOpaque(false);
        bodyArea.setFocusable(false);
        bodyArea.setBounds(40, 140, 920, 120);
        contentPanel.add(bodyArea);

        JLabel attachmentText = new JLabel("Attachment:");
        attachmentText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        attachmentText.setBounds(40, 270, 100, 25);
        contentPanel.add(attachmentText);

        JLabel fileLabel = new JLabel("<html><u>" + attachmentLabel + "</u></html>");
        fileLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        fileLabel.setForeground(new Color(0, 102, 204));
        fileLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fileLabel.setBounds(140, 270, 400, 25);
        fileLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileManager.openAttachment(StudentCourseworkContentPanel.this, attachmentLabel);
            }
        });
        contentPanel.add(fileLabel);

        JButton uploadButton = new JButton("Choose File...");
        uploadButton.setBounds(40, 310, 160, 35);
        uploadButton.setBackground(new Color(0, 102, 204));
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        uploadButton.setFocusPainted(false);
        uploadButton.setBorder(new LineBorder(new Color(0, 102, 204), 1, true));
        uploadButton.addActionListener(e -> chooseFile());
        contentPanel.add(uploadButton);

        uploadedFileLabel = new JLabel("No file uploaded");
        uploadedFileLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        uploadedFileLabel.setForeground(Color.GRAY);
        uploadedFileLabel.setBounds(210, 317, 600, 25);
        contentPanel.add(uploadedFileLabel);

        submitButton = new JButton("Submit");
        submitButton.setBounds(40, 360, 160, 35);
        submitButton.setBackground(new Color(0, 153, 76));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        submitButton.setFocusPainted(false);
        submitButton.setBorder(new LineBorder(new Color(0, 153, 76), 1, true));
        submitButton.setEnabled(false);
        submitButton.addActionListener(e -> submitCoursework());
        contentPanel.add(submitButton);

        JButton backButton = new JButton("← Back");
        backButton.setBounds(30, 475, 100, 40);
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        backButton.setBackground(new Color(255, 153, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(new LineBorder(new Color(255, 153, 0), 1, true));
        backButton.addActionListener(e -> goBack());
        contentPanel.add(backButton);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select file to upload");
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            uploadedFileLabel.setText("Uploaded: " + selectedFile.getName());
            submitButton.setEnabled(true);
        }
    }

    private void submitCoursework() {
        isSubmitted = true;
        JOptionPane.showMessageDialog(this, "Coursework submitted successfully!");

        for (Component comp : parentPanel.getComponents()) {
            if (comp instanceof StudentCourseViewPanel viewPanel) {
                viewPanel.markCourseworkAsSubmitted(courseworkTitle);
                break;
            }
        }

        goBack();
    }

    private void goBack() {
        cardLayout.show(parentPanel, COURSE_VIEW);
    }
}  