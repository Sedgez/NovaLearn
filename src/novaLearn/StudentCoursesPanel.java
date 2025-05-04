package novaLearn;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class StudentCoursesPanel extends JPanel {

    private Image backgroundImage;
    private Image logoImage;
    private JPanel parentPanel;
    private CardLayout cardLayout;

    public StudentCoursesPanel(JPanel parentPanel, CardLayout cardLayout) {
        setLayout(null);
        setPreferredSize(new Dimension(1400, 800));
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;
        loadAssets();
        setupBanner();
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

        JLabel userName = new JLabel("Courses");
        userName.setFont(new Font("Segoe UI", Font.BOLD, 26));
        userName.setForeground(Color.WHITE);
        userName.setBounds(75, 105, 300, 40);
        banner.add(userName);

        JButton addCourseButton = new JButton(loadImage("/novaLearn/assets/Add course icon.png", 30, 30));
        addCourseButton.setBounds(980, 105, 40, 40);
        addCourseButton.setFocusPainted(false);
        addCourseButton.setBorderPainted(false);
        addCourseButton.setContentAreaFilled(false);
        addCourseButton.addActionListener(e -> openEnrollWindow());
        banner.add(addCourseButton);
    }

    private void setupTable() {
        RoundedPanel tablePanel = new RoundedPanel(Color.WHITE);
        tablePanel.setCornerRadius(30);
        tablePanel.setLayout(null);
        tablePanel.setBounds(25, 197, 1043, 464);
        add(tablePanel);

        String[] columns = {"Course Code", "Course Description", "Units", "Instructor", "Action"};
        Object[][] data = {
            {"CCS108", "Object-Oriented Programming", 3, "Prof. Janus Raymond Tan", ""},
            {"CCS110", "Information Management", 3, "Prof. Kier Panollera", ""},
            {"CSP107", "Assembly Language", 3, "Engr. Carissa Morano", ""}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        JTable table = new JTable(model);
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

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(0).setMaxWidth(135);
        table.getColumnModel().getColumn(0).setMinWidth(105);

        table.getColumnModel().getColumn(1).setPreferredWidth(340);
        table.getColumnModel().getColumn(1).setMaxWidth(355);
        table.getColumnModel().getColumn(1).setMinWidth(325);

        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setMaxWidth(95);
        table.getColumnModel().getColumn(2).setMinWidth(65);

        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setPreferredWidth(280);
        table.getColumnModel().getColumn(3).setMaxWidth(295);
        table.getColumnModel().getColumn(3).setMinWidth(265);

        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setMaxWidth(135);
        table.getColumnModel().getColumn(4).setMinWidth(105);

        table.getColumn("Course Description").setCellRenderer(new CourseDescriptionRenderer());
        table.addMouseListener(new CourseDescriptionClickListener(table));

        table.getColumn("Action").setCellRenderer(new UnenrollButtonRenderer());
        table.getColumn("Action").setCellEditor(new UnenrollButtonEditor(new JCheckBox(), table));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(21, 22, 1001, 418);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 2, true));
        tablePanel.add(scrollPane);
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

    private void openEnrollWindow() {
        JFrame enrollFrame = new JFrame("Enroll on a New Course");
        enrollFrame.setSize(400, 350);
        enrollFrame.setLocationRelativeTo(null);
        enrollFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        enrollFrame.setLayout(null);
        enrollFrame.setResizable(false);
        enrollFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 0, 400, 350);
        enrollFrame.add(mainPanel);

        JLabel logoLabel = new JLabel(new ImageIcon(loadImage("/novaLearn/assets/logo1.png", 50, 50).getImage()));
        logoLabel.setBounds(30, 20, 50, 50);
        mainPanel.add(logoLabel);

        JLabel titleLabel = new JLabel("ENROLL ON A NEW COURSE");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setBounds(100, 30, 280, 30);
        mainPanel.add(titleLabel);

        JPanel lowerPanel = new JPanel(null);
        lowerPanel.setBackground(new Color(55, 98, 144));
        lowerPanel.setBounds(0, 80, 400, 270);
        mainPanel.add(lowerPanel);

        RoundedTextField courseCodeField = new RoundedTextField(20);
        courseCodeField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        courseCodeField.setHorizontalAlignment(JTextField.CENTER);
        courseCodeField.setBounds(50, 30, 300, 40);
        lowerPanel.add(courseCodeField);

        JLabel instructionLabel = new JLabel("Ask your teacher for the class code, then enter it here.");
        instructionLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        instructionLabel.setBounds(25, 80, 350, 30);
        lowerPanel.add(instructionLabel);

        JButton cancelButton = createRoundedButton("Cancel");
        cancelButton.setBounds(60, 150, 120, 50);
        lowerPanel.add(cancelButton);

        JButton enrollButton = createRoundedButton("Enroll");
        enrollButton.setBounds(220, 150, 120, 50);
        lowerPanel.add(enrollButton);

        cancelButton.addActionListener(e -> enrollFrame.dispose());

        enrollButton.addActionListener(e -> {
            String courseCode = courseCodeField.getText().trim();
            if (!courseCode.isEmpty()) {
                JOptionPane.showMessageDialog(enrollFrame, "You enrolled with code: " + courseCode);
                enrollFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(enrollFrame, "Please enter a course code.");
            }
        });

        enrollFrame.setVisible(true);
    }

    private void openCourseDetailPanel(String courseCode, String description) {
        String panelName = "courseDetail_" + courseCode;

        Component existingPanel = findComponentByName(panelName);
        if (existingPanel == null) {
            StudentCourseViewPanel courseDetailPanel = new StudentCourseViewPanel(parentPanel, cardLayout, courseCode, description);
            parentPanel.add(courseDetailPanel, panelName);
        }

        cardLayout.show(parentPanel, panelName);
    }

    private Component findComponentByName(String name) {
        for (Component component : parentPanel.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            }
        }
        return null;
    }

    private class CourseDescriptionRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel(value.toString());
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            label.setForeground(Color.BLACK);
            label.setOpaque(true);
            if (isSelected) {
                label.setBackground(table.getSelectionBackground());
            } else {
                label.setBackground(Color.WHITE);
            }
            return label;
        }
    }

    private class CourseDescriptionClickListener extends MouseAdapter {
        private JTable table;

        public CourseDescriptionClickListener(JTable table) {
            this.table = table;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = table.rowAtPoint(e.getPoint());
            int col = table.columnAtPoint(e.getPoint());
            if (col == 1) {
                String courseCode = table.getValueAt(row, 0).toString();
                String description = table.getValueAt(row, 1).toString();
                openCourseDetailPanel(courseCode, description);
            }
        }
    }

    private class UnenrollButtonRenderer extends JButton implements TableCellRenderer {
        public UnenrollButtonRenderer() {
            setOpaque(true);
            setText("Unenroll");
            setBackground(Color.RED);
            setForeground(Color.WHITE);
            setFont(new Font("Tahoma", Font.BOLD, 14));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private class UnenrollButtonEditor extends DefaultCellEditor {
        private JButton button;
        private JTable table;

        public UnenrollButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;
            button = new JButton("Unenroll");
            button.setOpaque(true);
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Tahoma", Font.BOLD, 14));

            button.addActionListener(e -> {
                fireEditingStopped();
                int row = table.getSelectedRow();
                String courseCode = (String) table.getValueAt(row, 0);

                int result = JOptionPane.showConfirmDialog(
                        button,
                        "Are you sure you want to unenroll from " + courseCode + "?",
                        "Confirm Unenroll",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    ((DefaultTableModel) table.getModel()).removeRow(row);
                    JOptionPane.showMessageDialog(button, "Unenrolled from " + courseCode);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                     int row, int column) {
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Unenroll";
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
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        return button;
    }
}
