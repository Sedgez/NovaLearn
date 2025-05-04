package novaLearn;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class AdminInstructorTrackerPanel extends JPanel {

    private Image backgroundImage;
    private Image logoImage;
    private JPanel parentPanel;
    private CardLayout cardLayout;
    private JPasswordField passwordField;
    
    public AdminInstructorTrackerPanel(JPanel parentPanel, CardLayout cardLayout) {
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

    //Window Banner
    private void setupBanner() {
        RoundedPanel banner = new RoundedPanel(backgroundImage);
        banner.setBounds(25, 10, 1043, 171);
        banner.setLayout(null);
        add(banner);

        //Banner Icon
        JLabel courseIcon = new JLabel(loadImage("/novaLearn/assets/Courses tab icon_white.png", 40, 40));
        courseIcon.setBounds(30, 100, 40, 40);
        banner.add(courseIcon);

        //Banner Title
        JLabel userName = new JLabel("Instructor Tracker");
        userName.setFont(new Font("Segoe UI", Font.BOLD, 26));
        userName.setForeground(Color.WHITE);
        userName.setBounds(75, 105, 300, 40);
        banner.add(userName);

        //Add instructor button
        JButton addCourseButton = new JButton(loadImage("/novaLearn/assets/Add course icon.png", 30, 30));
        addCourseButton.setBounds(980, 105, 40, 40);
        addCourseButton.setFocusPainted(false);
        addCourseButton.setBorderPainted(false);
        addCourseButton.setContentAreaFilled(false);
        addCourseButton.addActionListener(e -> addInstructorWindow());
        banner.add(addCourseButton);
    }

    //Creating table 
    private void setupTable() {
        RoundedPanel tablePanel = new RoundedPanel(Color.WHITE);
        tablePanel.setCornerRadius(30);
        tablePanel.setLayout(null);
        tablePanel.setBounds(25, 197, 1043, 464);
        add(tablePanel);

        //Table title columns and row data
        String[] columns = {"Instructor", "Email", "Status", "Action"};
        Object[][] data = {
            {"Tan, Janus Raymond", "janus@example.com", "Active", ""},
            {"Morano, Carissa", "carissa@example.com", "Active", ""},
            {"Dimaculangan, Melissa", "melissa@example.com", "Active", ""},
            {"Tolentino, Daryl", "daryl@example.com", "Active", ""},
            {"Panollera, Kier", "kier@example.com", "Active", ""},


        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        
        //Creating table for instructor
        JTable instructorTable = new JTable(model);
        instructorTable.setRowHeight(50);
        instructorTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        instructorTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        instructorTable.getTableHeader().setBackground(new Color(55, 98, 144));
        instructorTable.getTableHeader().setForeground(Color.WHITE);
        instructorTable.getTableHeader().setReorderingAllowed(false);
        instructorTable.setSelectionBackground(new Color(220, 230, 241));
        instructorTable.setShowGrid(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        instructorTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Name
        instructorTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Email
        instructorTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Status

        instructorTable.getColumn("Action").setCellRenderer(new ActionIconRenderer());
        instructorTable.getColumn("Action").setCellEditor(new ActionIconEditor(new JCheckBox(), instructorTable));

        JScrollPane scrollPane = new JScrollPane(instructorTable);
        scrollPane.setBounds(21, 22, 1001, 418);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 2, true));
        tablePanel.add(scrollPane);
    }    
    
    private class ActionIconRenderer extends JPanel implements TableCellRenderer {
        private final JLabel viewLabel;
        private final JLabel deleteLabel;

        public ActionIconRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
            setOpaque(true);

            viewLabel = new JLabel(loadImage("/novaLearn/assets/View icon.png", 24, 24));
            deleteLabel = new JLabel(loadImage("/novaLearn/assets/X icon.png", 24, 24));

            add(viewLabel);
            add(deleteLabel);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            return this;
        }
    }

    private class ActionIconEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final JLabel viewLabel;
        private final JLabel deleteLabel;

        public ActionIconEditor(JCheckBox checkBox, JTable table) {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            panel.setBackground(Color.WHITE);

            viewLabel = new JLabel(loadImage("/novaLearn/assets/View icon.png", 24, 24));
            deleteLabel = new JLabel(loadImage("/novaLearn/assets/X icon.png", 24, 24));

            panel.add(viewLabel);
            panel.add(deleteLabel);
            
            viewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            deleteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            
            viewLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String courseName = table.getValueAt(row, 1).toString(); // Assuming course name is column 1

                        fireEditingStopped(); // Always stop editing before changing UI

                        String panelName = "adminView_" + courseName;
                        Component existingPanel = findComponentByName(panelName);
                        String instructorName = table.getValueAt(row, 0).toString();

                        if (existingPanel == null) {
                            AdminViewInstructorCoursesPanel viewCourseInstructor = new AdminViewInstructorCoursesPanel (instructorName, parentPanel ,cardLayout);
                            viewCourseInstructor.setName(panelName);
                            parentPanel.add(viewCourseInstructor, panelName);
                        }

                        cardLayout.show(parentPanel, panelName);
                    }
                }
            });


            // Confirm and delete row when 'delete' is clicked
            deleteLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int row = table.getSelectedRow();
                    String instructorName = table.getValueAt(row, 0).toString();
                    int confirm = JOptionPane.showConfirmDialog(table,
                            "Delete record for " + instructorName + "?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        ((DefaultTableModel) table.getModel()).removeRow(row);
                    }
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
        		boolean isSelected, int row, int column) {
        	return panel;
        }

        @Override
        public Object getCellEditorValue() {
        	return null;
        }
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

    private void addInstructorWindow() {
    	JFrame updateFrame = new JFrame("Add Instructor");
        updateFrame.setSize(725, 496);
        updateFrame.setLocationRelativeTo(null);
        updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateFrame.setLayout(null);
        updateFrame.setResizable(false);
        updateFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 0, 725, 466);
        updateFrame.add(mainPanel);

        JLabel logoLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/novaLearn/assets/logoD.png"))
                .getImage().getScaledInstance(185, 61, Image.SCALE_SMOOTH)));
        logoLabel.setBounds(10, 10, 185, 61);
        mainPanel.add(logoLabel);

        JLabel titleLabel = new JLabel("<html><body style='text-align:right;'>ADD INSTRUCTOR</body></html>");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        titleLabel.setForeground(new Color(55, 98, 144));
        titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        titleLabel.setBounds(423, 31, 280, 40);
        mainPanel.add(titleLabel);

        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(new Color(55, 98, 144));
        formPanel.setBounds(0, 75, 715, 390);
        mainPanel.add(formPanel);

        // Buttons
        JButton saveBtn = new JButton("ADD");
        styleButton(saveBtn);
        saveBtn.setBounds(215, 320, 120, 40);
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(Color.DARK_GRAY);
        saveBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(updateFrame, "Profile Updated Successfully!");
            updateFrame.dispose();
        });
        formPanel.add(saveBtn);

        JButton cancelBtn = new JButton("CANCEL");
        styleButton(cancelBtn);
        cancelBtn.setBounds(370, 322, 120, 40);
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setForeground(Color.DARK_GRAY);
        cancelBtn.addActionListener(e -> updateFrame.dispose());
        formPanel.add(cancelBtn);

        // Fields
        JLabel lastNameLabel = new JLabel("LAST NAME:");
        lastNameLabel.setForeground(Color.WHITE);
        lastNameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lastNameLabel.setBounds(15, 55, 150, 13);
        formPanel.add(lastNameLabel);

        JTextField lastNameTf = new JTextField();
        lastNameTf.setBounds(190, 46, 210, 25);
        formPanel.add(lastNameTf);

        JLabel firstNameLabel = new JLabel("FIRST NAME:");
        firstNameLabel.setForeground(Color.WHITE);
        firstNameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        firstNameLabel.setBounds(15, 91, 150, 13);
        formPanel.add(firstNameLabel);

        JTextField firstNameTf = new JTextField();
        firstNameTf.setBounds(190, 81, 210, 25);
        formPanel.add(firstNameTf);

        JLabel sexLabel = new JLabel("SEX:");
        sexLabel.setForeground(Color.WHITE);
        sexLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        sexLabel.setBounds(420, 55, 56, 13);
        formPanel.add(sexLabel);

        JComboBox<String> sexComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        sexComboBox.setBounds(567, 46, 122, 25);
        formPanel.add(sexComboBox);

        JLabel dobLabel = new JLabel("DATE OF BIRTH:");
        dobLabel.setForeground(Color.WHITE);
        dobLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        dobLabel.setBounds(420, 91, 132, 13);
        formPanel.add(dobLabel);

        JTextField dobTf = new JTextField();
        dobTf.setBounds(567, 81, 122, 25);
        formPanel.add(dobTf);

        JLabel programLabel = new JLabel("PROGRAM:");
        programLabel.setForeground(Color.WHITE);
        programLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        programLabel.setBounds(15, 130, 150, 20);
        formPanel.add(programLabel);

        JComboBox<String> programComboBox = new JComboBox<>(new String[]{
            "BS Computer Science", "BS Information Technology", "BS Information Systems",
            "BS Software Engineering", "BS Data Science"
        });
        programComboBox.setBounds(190, 125, 210, 25);
        formPanel.add(programComboBox);

        JLabel addressLabel = new JLabel("ADDRESS:");
        addressLabel.setForeground(Color.WHITE);
        addressLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        addressLabel.setBounds(15, 170, 150, 20);
        formPanel.add(addressLabel);

        JTextField addressTf = new JTextField();
        addressTf.setBounds(190, 165, 500, 25);
        formPanel.add(addressTf);

        JLabel religionLabel = new JLabel("RELIGION:");
        religionLabel.setForeground(Color.WHITE);
        religionLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        religionLabel.setBounds(15, 210, 150, 20);
        formPanel.add(religionLabel);

        JTextField religionTf = new JTextField();
        religionTf.setBounds(190, 205, 210, 25);
        formPanel.add(religionTf);

        JLabel nationalityLabel = new JLabel("NATIONALITY:");
        nationalityLabel.setForeground(Color.WHITE);
        nationalityLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        nationalityLabel.setBounds(15, 250, 150, 20);
        formPanel.add(nationalityLabel);

        JTextField nationalityTf = new JTextField();
        nationalityTf.setBounds(190, 245, 210, 25);
        formPanel.add(nationalityTf);

        JLabel civilStatusLabel = new JLabel("CIVIL STATUS:");
        civilStatusLabel.setForeground(Color.WHITE);
        civilStatusLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        civilStatusLabel.setBounds(420, 130, 150, 20);
        formPanel.add(civilStatusLabel);

        JComboBox<String> civilStatusComboBox = new JComboBox<>(new String[]{"Single", "Married", "Widowed", "Divorced"});
        civilStatusComboBox.setBounds(567, 125, 122, 25);
        formPanel.add(civilStatusComboBox);

        updateFrame.setVisible(true);
    }
    
    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(55, 98, 144));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBorder(new RoundedBorder(20));
    }
    
    private Component findComponentByName(String name) {
        for (Component component : parentPanel.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            }
        }
        return null;
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
