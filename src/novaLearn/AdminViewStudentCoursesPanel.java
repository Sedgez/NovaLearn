package novaLearn;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminViewStudentCoursesPanel extends JPanel {

    private Image backgroundImage;
    private JTable table;
    private DefaultTableModel model;
    private final JPanel parentPanel;
    private final CardLayout cardLayout;

    // Overloaded constructor
    public AdminViewStudentCoursesPanel(String studentName) {
        this(new JPanel(new CardLayout()), new CardLayout(), studentName);
    }

    
    public AdminViewStudentCoursesPanel(JPanel parentPanel, CardLayout cardLayout, String studentName) {
        // Initialize parentPanel and cardLayout
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;

        setLayout(null);
        setPreferredSize(new Dimension(1400, 800));
        loadAssets();
        setupBanner(studentName);
        setupTable();
    }

    private void loadAssets() {
        backgroundImage = new ImageIcon(getClass().getResource("/novaLearn/assets/dash_bg.png")).getImage();
    }

    private void setupBanner(String studentName) {
        // Create the banner panel with the background image
        RoundedPanel banner = new RoundedPanel(backgroundImage);
        banner.setBounds(25, 10, 1043, 171); // Adjusted size for better layout
        banner.setLayout(null);
        add(banner);

        // Add the student's name as the title
        JLabel titleLabel = new JLabel(studentName.toUpperCase()); // Student's name in uppercase
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 48)); // Adjusted font size for better fit
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
        titleLabel.setBounds(30, 40, 1000, 60); // Adjusted bounds for better alignment
        banner.add(titleLabel);

        // Add a label below the title for additional context (e.g., "STUDENT")
        JLabel studentLabel = new JLabel("STUDENT");
        studentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20)); // Smaller, secondary font
        studentLabel.setForeground(Color.WHITE);
        studentLabel.setHorizontalAlignment(SwingConstants.LEFT);
        studentLabel.setBounds(30, 110, 200, 30); // Positioned below the title
        banner.add(studentLabel);
    }

    private void setupTable() {
        RoundedPanel tablePanel = new RoundedPanel(Color.WHITE);
        tablePanel.setCornerRadius(30);
        tablePanel.setLayout(null);
        tablePanel.setBounds(25, 197, 1043, 464);
        add(tablePanel);

        // Updated columns array (removed "Units" and "Action")
        String[] columns = { "Course", "Course Description", "Number of Students" };
        Object[][] data = {
            { "CCS108", "Object-Oriented Programming", "20" },
            { "ITEW2", "Client/Server-Side Scripting", "49" },
        };

     // Update the table model
        model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All columns are non-editable
            }
        };

        table = new JTable(model); // Use the class-level "table" variable
        table.setRowHeight(50);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(55, 98, 144));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionBackground(new Color(220, 230, 241));
        table.setShowGrid(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

     // Configure columns (no "Units" or "Action" columns anymore)
        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            if (i == 0) { // "Course" column
                column.setPreferredWidth(120);
            } else if (i == 1) { // "Course Description" column
                column.setPreferredWidth(340);
            } else if (i == 2) { // "Number of Students" column
                column.setPreferredWidth(280);
                column.setCellRenderer(centerRenderer); // Center-align the "Number of Students" column
            }
        }


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(21, 22, 1001, 350);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 2, true));
        tablePanel.add(scrollPane);

     // Back button
        JButton backBtn = new JButton("Back", loadImage("/novaLearn/assets/Back icon.png", 24, 24));
        backBtn.setForeground(Color.ORANGE);
        backBtn.setFocusPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setBounds(21, 408, 100, 32);

        backBtn.addActionListener(e -> cardLayout.previous(parentPanel));
        tablePanel.add(backBtn);
    }

    private ImageIcon loadImage(String path, int width, int height) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                return new ImageIcon(
                        new ImageIcon(imgURL).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            }
            System.err.println("Image not found: " + path);
            return new ImageIcon();
        } catch (Exception e) {
            System.err.println("Error loading image: " + path);
            return new ImageIcon();
        }
    }
}