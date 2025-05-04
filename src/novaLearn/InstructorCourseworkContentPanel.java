package novaLearn;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class InstructorCourseworkContentPanel extends JPanel {

    private JTextField titleField, dueDateField;
    private JTextArea descriptionArea;
    private JLabel uploadedFileLabel;
    private JButton saveButton;
    private String attachmentPath = "";
    private final JPanel parentPanel;
    private final CardLayout cardLayout;
    private final String courseworkTitle;

    public InstructorCourseworkContentPanel(JPanel parentPanel, CardLayout cardLayout, String title,
    										String description, String dueDate, String attachmentLabel) {
    	
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;
        this.courseworkTitle = title;

        setLayout(null);
        setBounds(0, 0, 1100, 700);
        setBackground(new Color(242, 242, 242));

        setupBanner();
        setupContent(title, description, dueDate, attachmentLabel);
    }

    private void setupBanner() {
        ImageIcon bannerIcon = new ImageIcon(getClass().getResource("/novaLearn/assets/dash_bg.png"));
        Image bannerImage = bannerIcon.getImage();

        RoundedPanel banner = new RoundedPanel(bannerImage);
        banner.setBounds(25, 10, 1043, 100);
        banner.setLayout(null);
        add(banner);
    }

    private void setupContent(String title, String description, 
    							String dueDate, String attachmentLabel) {
    	
        RoundedPanel contentPanel = new RoundedPanel(Color.WHITE);
        contentPanel.setCornerRadius(30);
        contentPanel.setLayout(null);
        contentPanel.setBounds(25, 130, 1043, 538);
        add(contentPanel);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(55, 98, 144));
        headerPanel.setBounds(20, 20, 1000, 40);
        JLabel headerLabel = new JLabel("Edit Coursework Details");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        contentPanel.add(headerPanel);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setBounds(40, 75, 100, 25);
        contentPanel.add(titleLabel);

        titleField = new JTextField(title);
        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        titleField.setBounds(140, 75, 400, 30);
        contentPanel.add(titleField);

        JLabel dueLabel = new JLabel("Due Date:");
        dueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        dueLabel.setBounds(40, 120, 100, 25);
        contentPanel.add(dueLabel);

        dueDateField = new JTextField(dueDate);
        dueDateField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dueDateField.setBounds(140, 120, 250, 30);
        contentPanel.add(dueDateField);

        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        descLabel.setBounds(40, 170, 150, 25);
        contentPanel.add(descLabel);

        descriptionArea = new JTextArea(description);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        descScroll.setBounds(40, 200, 920, 100);
        contentPanel.add(descScroll);

        JLabel fileLabel = new JLabel("Attachment:");
        fileLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        fileLabel.setBounds(40, 315, 150, 25);
        contentPanel.add(fileLabel);

        JButton uploadButton = new JButton("Choose File...");
        uploadButton.setBounds(160, 315, 160, 30);
        uploadButton.setBackground(new Color(0, 102, 204));
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        uploadButton.setFocusPainted(false);
        uploadButton.setBorder(new LineBorder(new Color(0, 102, 204), 1, true));
        uploadButton.addActionListener(e -> chooseFile());
        contentPanel.add(uploadButton);

        uploadedFileLabel = new JLabel(attachmentLabel.isEmpty() ? "No file selected" : "Selected: " + attachmentLabel);
        uploadedFileLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        uploadedFileLabel.setForeground(Color.GRAY);
        uploadedFileLabel.setBounds(330, 320, 600, 25);
        contentPanel.add(uploadedFileLabel);

        saveButton = new JButton("Save Changes");
        saveButton.setBounds(40, 375, 160, 35);
        saveButton.setBackground(new Color(0, 153, 76));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        saveButton.setFocusPainted(false);
        saveButton.setBorder(new LineBorder(new Color(0, 153, 76), 1, true));
        saveButton.addActionListener(e -> saveChanges());
        contentPanel.add(saveButton);

        JButton backButton = new JButton("← Back");
        backButton.setBounds(30, 475, 100, 40);
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        backButton.setBackground(new Color(255, 153, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(new LineBorder(new Color(255, 153, 0), 1, true));
        backButton.addActionListener(e -> cardLayout.show(parentPanel, "courseView"));
        contentPanel.add(backButton);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select file to upload");
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            attachmentPath = selectedFile.getAbsolutePath();
            uploadedFileLabel.setText("Selected: " + selectedFile.getName());
        }
    }

    private void saveChanges() {
        String newTitle = titleField.getText().trim();
        String newDescription = descriptionArea.getText().trim();
        String newDueDate = dueDateField.getText().trim();

        if (newTitle.isEmpty() || newDescription.isEmpty() || newDueDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // TODO: Update database or model
        JOptionPane.showMessageDialog(this, "Coursework updated successfully!");

        // Go back to coursework list view
        cardLayout.show(parentPanel, "courseView");
    }
}
