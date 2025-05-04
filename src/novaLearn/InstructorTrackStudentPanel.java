// Place this entire class in the same package: novaLearn

package novaLearn;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class InstructorTrackStudentPanel extends JPanel {

    private Image backgroundImage;
    private JPanel parentPanel;
    private CardLayout cardLayout;
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> courseDropdown;

    private String[] courseCodes = {"CCS108", "CCS110", "CSP107"};
    private String[] courseDescriptions = {
        "CCS108 - Object-Oriented Programming",
        "CCS110 - Database Systems",
        "CSP107 - Assembly Language"
    };

    // Track course student data
    private java.util.List<java.util.List<Object[]>> courseStudentData;

    public InstructorTrackStudentPanel(JPanel parentPanel, CardLayout cardLayout) {
        setLayout(null);
        setPreferredSize(new Dimension(1400, 800));
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;
        loadAssets();
        setupData();
        setupBanner();
        setupTablePanel();
    }

    private void loadAssets() {
        backgroundImage = new ImageIcon(getClass().getResource("/novaLearn/assets/dash_bg.png")).getImage();
    }

    private void setupBanner() {
        RoundedPanel banner = new RoundedPanel(backgroundImage);
        banner.setBounds(25, 10, 1043, 171);
        banner.setLayout(null);
        add(banner);

        JLabel trackerIcon = new JLabel(loadImage("/novaLearn/assets/Student icon_white.png", 40, 40));
        trackerIcon.setBounds(30, 100, 40, 40);
        banner.add(trackerIcon);

        JLabel title = new JLabel("Track Student");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBounds(75, 105, 300, 40);
        banner.add(title);
    }

    private void setupTablePanel() {
        RoundedPanel tablePanel = new RoundedPanel(Color.WHITE);
        tablePanel.setCornerRadius(30);
        tablePanel.setLayout(null);
        tablePanel.setBounds(25, 200, 1043, 462);
        add(tablePanel);

        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        courseLabel.setBounds(30, 20, 150, 30);
        tablePanel.add(courseLabel);

        courseDropdown = new JComboBox<>(courseDescriptions);
        courseDropdown.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        courseDropdown.setBounds(160, 20, 350, 30);
        tablePanel.add(courseDropdown);

        courseDropdown.addActionListener(e -> updateTable(courseDropdown.getSelectedIndex()));

        String[] columns = {"Student Number", "Name", "Grade", "Action"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(50);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(55, 98, 144));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(220, 230, 241));
        table.setShowGrid(true);
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < 3; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        DefaultTableCellRenderer actionRenderer = new DefaultTableCellRenderer() {
            public void setValue(Object value) {
                if (value instanceof ImageIcon) {
                    setIcon((ImageIcon) value);
                    setText("");
                } else {
                    super.setValue(value);
                }
            }
        };
        actionRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(3).setCellRenderer(actionRenderer);

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                int courseIndex = courseDropdown.getSelectedIndex();

                if (col == 2) {
                    String input = JOptionPane.showInputDialog(
                        InstructorTrackStudentPanel.this,
                        "Enter grade for " + model.getValueAt(row, 1),
                        model.getValueAt(row, 2)
                    );

                    if (input != null && !input.trim().isEmpty()) {
                        try {
                            double grade = Double.parseDouble(input.trim());
                            if (grade < 0 || grade > 100) {
                                throw new IllegalArgumentException("Grade must be between 0 and 100.");
                            }
                            String gradeFormatted = String.format("%.2f", grade);
                            model.setValueAt(gradeFormatted, row, 2);
                            courseStudentData.get(courseIndex).get(row)[2] = gradeFormatted;
                        } catch (IllegalArgumentException e) {
                            JOptionPane.showMessageDialog(
                                InstructorTrackStudentPanel.this,
                                e.getMessage(),
                                "Invalid Input",
                                JOptionPane.WARNING_MESSAGE
                            );
                        }
                    }
                } else if (col == 3) {
                    int confirm = JOptionPane.showConfirmDialog(
                        InstructorTrackStudentPanel.this,
                        "Are you sure you want to unenroll this student from the course?",
                        "Confirm Unenroll",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        courseStudentData.get(courseIndex).remove(row);
                        updateTable(courseIndex);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(21, 70, 1001, 372);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 2, true));
        tablePanel.add(scrollPane);

        updateTable(0);
    }

    private void updateTable(int courseIndex) {
        model.setRowCount(0);
        for (Object[] row : courseStudentData.get(courseIndex)) {
            model.addRow(row);
        }
    }

    private void setupData() {
        ImageIcon actionIcon = loadImage("/novaLearn/assets/X icon.png", 30, 30);
        courseStudentData = new java.util.ArrayList<>();

        java.util.List<Object[]> course1 = new java.util.ArrayList<>();
        course1.add(new Object[]{"2401059", "Balinado, Christian", "", actionIcon});
        course1.add(new Object[]{"2410061", "Oquindo, Kaye Ann Joy", "", actionIcon});

        java.util.List<Object[]> course2 = new java.util.ArrayList<>();
        course2.add(new Object[]{"2300127", "Cayaga, Kurt Joshua", "", actionIcon});
        course2.add(new Object[]{"2202191", "Carreon, Charles", "", actionIcon});

        java.util.List<Object[]> course3 = new java.util.ArrayList<>();
        course3.add(new Object[]{"2301010", "Sy, Christian Raphael", "", actionIcon});

        courseStudentData.add(course1);
        courseStudentData.add(course2);
        courseStudentData.add(course3);
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
}
