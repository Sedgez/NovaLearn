package novaLearn;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class AdminViewCoursePanel extends JPanel {

    private Image backgroundImage;
    private Image logoImage;
    private JPanel parentPanel;
    private CardLayout cardLayout;
    private String courseTitle;
    private DefaultTableModel model;

    public AdminViewCoursePanel(JPanel parentPanel, CardLayout cardLayout, String courseTitle) {
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;
        this.courseTitle = courseTitle;

        setLayout(null);
        setPreferredSize(new Dimension(1400, 800));

        loadAssets();
        setupBanner();
        setupBackButton();
        setupTable();
    }

    private void loadAssets() {
        backgroundImage = new ImageIcon(getClass().getResource("/novaLearn/assets/dash_bg.png")).getImage();
        logoImage = new ImageIcon(getClass().getResource("/novaLearn/assets/logoD.png")).getImage();
    }

    private void setupBanner() {
        RoundedPanel banner = new RoundedPanel(backgroundImage);
        banner.setBounds(25, 10, 1043, 171);
        banner.setLayout(null);
        add(banner);

        JLabel courseIcon = new JLabel(loadImage("/novaLearn/assets/Courses tab icon_white.png", 40, 40));
        courseIcon.setBounds(30, 100, 40, 40);
        banner.add(courseIcon);

        JLabel titleLabel = new JLabel(courseTitle.toUpperCase());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(75, 105, 600, 40);
        banner.add(titleLabel);

        JButton addCourseButton = new JButton(loadImage("/novaLearn/assets/Add course icon.png", 30, 30));
        addCourseButton.setBounds(980, 105, 40, 40);
        addCourseButton.setFocusPainted(false);
        addCourseButton.setBorderPainted(false);
        addCourseButton.setContentAreaFilled(false);
        addCourseButton.addActionListener(e -> openAddNewInstructor());
        banner.add(addCourseButton);
    }

    private void setupTable() {
        RoundedPanel tablePanel = new RoundedPanel(Color.WHITE);
        tablePanel.setCornerRadius(30);
        tablePanel.setLayout(null);
        tablePanel.setBounds(25, 197, 1043, 464);
        add(tablePanel);

        String[] columns = { "Instructor", "No. of Students", "Action" };
        Object[][] data = { { "Tan, Janus Raymond", "24", "" }, { "Morano, Carissa", "18", "" },
                { "Dimaculangan, Melissa", "12", "" }, { "Tolentino, Daryl", "27", "" } };

        model = new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        JTable courseInstructorTable = new JTable(model);
        courseInstructorTable.setRowHeight(50);
        courseInstructorTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        courseInstructorTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        courseInstructorTable.getTableHeader().setBackground(new Color(55, 98, 144));
        courseInstructorTable.getTableHeader().setForeground(Color.WHITE);
        courseInstructorTable.getTableHeader().setReorderingAllowed(false);
        courseInstructorTable.setSelectionBackground(new Color(220, 230, 241));
        courseInstructorTable.setShowGrid(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        courseInstructorTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        courseInstructorTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        courseInstructorTable.getColumn("Action").setCellRenderer(new ActionIconRenderer());
        courseInstructorTable.getColumn("Action").setCellEditor(new ActionIconEditor(new JCheckBox(), courseInstructorTable));

        JScrollPane scrollPane = new JScrollPane(courseInstructorTable);
        scrollPane.setBounds(21, 22, 1001, 371);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 2, true));
        tablePanel.add(scrollPane);
    }

    private void openAddNewInstructor() {
        JFrame newInstructorFrame = new JFrame("Add New Instructor");
        newInstructorFrame.setSize(400, 350);
        newInstructorFrame.setLocationRelativeTo(null);
        newInstructorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newInstructorFrame.setResizable(false);
        newInstructorFrame.setIconImage(
                Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        newInstructorFrame.getContentPane().add(mainPanel);

        JLabel logoLabel = new JLabel(new ImageIcon(loadImage("/novaLearn/assets/logo1.png", 50, 50).getImage()));
        logoLabel.setBounds(30, 20, 50, 50);
        mainPanel.add(logoLabel);

        JLabel titleLabel = new JLabel("ADD NEW INSTRUCTOR");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setBounds(100, 30, 280, 30);
        mainPanel.add(titleLabel);

        JPanel lowerPanel = new JPanel(null);
        lowerPanel.setBackground(new Color(55, 98, 144));
        lowerPanel.setBounds(0, 80, 400, 270);
        mainPanel.add(lowerPanel);

        newInstructorFrame.setVisible(true);
    }

    private void setupBackButton() {
        // Load and scale the icon
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/novaLearn/assets/Back icon.png"));
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Create the Back button with the scaled icon
        JButton backButton = new JButton("Back", scaledIcon);
        backButton.setForeground(Color.ORANGE); // Text color
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);

        // Position the button below the table on the left side
        backButton.setBounds(40, 608, 100, 32); // Adjusted bounds for left alignment below the table

        // Add ActionListener for navigation
        backButton.addActionListener(e -> {
            String previousPanelName = "adminCoursesTracker"; // Panel to navigate back to
            cardLayout.show(parentPanel, previousPanelName);
        });

        add(backButton); // Add the button to the panel
    }

    private class ActionIconRenderer extends JPanel implements TableCellRenderer {
        private final JLabel viewLabel;
        private final JLabel deleteInstructor;

        public ActionIconRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
            setOpaque(true);

            viewLabel = new JLabel(loadImage("/novaLearn/assets/View icon.png", 24, 24));
            deleteInstructor = new JLabel(loadImage("/novaLearn/assets/X icon.png", 24, 24));

            add(viewLabel);
            add(deleteInstructor);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            return this;
        }
    }

    private class ActionIconEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final JLabel viewLabel;
        private final JLabel deleteInstructor;
        private int currentRow;

        public ActionIconEditor(JCheckBox checkBox, JTable table) {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            panel.setBackground(Color.WHITE);

            viewLabel = new JLabel(loadImage("/novaLearn/assets/View icon.png", 24, 24));
            deleteInstructor = new JLabel(loadImage("/novaLearn/assets/X icon.png", 24, 24));

            panel.add(viewLabel);
            panel.add(deleteInstructor);

            viewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            deleteInstructor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            viewLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (currentRow >= 0) {
                        fireEditingStopped();
                        String instructorName = table.getValueAt(currentRow, 0).toString();
                        String courseCode = "CCS110";
                        String panelName = "adminView_" + courseTitle + "_" + instructorName.replaceAll("\\s+", "");

                        Component existingPanel = findComponentByName(panelName);

                        if (existingPanel == null) {
                            AdminViewCourseInstructorPanel viewCourseStudents = new AdminViewCourseInstructorPanel(courseCode, courseTitle, instructorName, parentPanel, cardLayout);
                            viewCourseStudents.setName(panelName);
                            parentPanel.add(viewCourseStudents, panelName);
                        }

                        cardLayout.show(parentPanel, panelName);
                    }
                }
            });

            deleteInstructor.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (currentRow >= 0) {
                        fireEditingStopped();
                        String instructorName = table.getValueAt(currentRow, 0).toString();
                        int confirm = JOptionPane.showConfirmDialog(table, "Delete record for " + instructorName + "?",
                                "Confirm Delete", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            ((DefaultTableModel) table.getModel()).removeRow(currentRow);
                        }
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            currentRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }

    private Component findComponentByName(String name) {
        for (Component component : parentPanel.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            }
        }
        return null;
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