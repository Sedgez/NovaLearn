package novaLearn;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;

public class AdminCoursesTrackerPanel extends JPanel {

    private Image backgroundImage;
    private Image logoImage;
    private JPanel parentPanel;
    private CardLayout cardLayout;
    private JTable table; 

    public AdminCoursesTrackerPanel(JPanel parentPanel, CardLayout cardLayout) {
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
    	//Bg Image
        RoundedPanel banner = new RoundedPanel(backgroundImage);
        banner.setBounds(25, 10, 1043, 171);
        banner.setLayout(null);
        add(banner);

        //Icon
        JLabel courseIcon = new JLabel(loadImage("/novaLearn/assets/Courses tab icon_white.png", 40, 40));
        courseIcon.setBounds(30, 100, 40, 40);
        banner.add(courseIcon);

        //Title
        JLabel course = new JLabel("Courses Tracker");
        course.setFont(new Font("Segoe UI", Font.BOLD, 26));
        course.setForeground(Color.WHITE);
        course.setBounds(75, 105, 300, 40);
        banner.add(course);

        //Add course Button
        JButton addCourseButton = new JButton(loadImage("/novaLearn/assets/Add course icon.png", 30, 30));
        addCourseButton.setBounds(980, 105, 40, 40);
        addCourseButton.setFocusPainted(false);
        addCourseButton.setBorderPainted(false);
        addCourseButton.setContentAreaFilled(false);
        addCourseButton.addActionListener(e -> openAddNewCourse());
        banner.add(addCourseButton);
        
        
    }

    //Table
    private void setupTable() {
        RoundedPanel tablePanel = new RoundedPanel(Color.WHITE);
        tablePanel.setCornerRadius(30);
        tablePanel.setLayout(null);
        tablePanel.setBounds(25, 197, 1043, 464);
        add(tablePanel);

        //Table title and data
        String[] columns = {"Course Code", "Course Name", "Action"};
        Object[][] data = {
            {"CCS108", "Object-Oriented Programming", ""},
            {"CCS110", "Information Management", ""},
            {"CSP107", "Assembly Language", ""},
            {"CSP102", "Discrete Structures", ""},
            {"ITEW2", "Client/Server-Side Scripting", ""},
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int row, int column) {
                return column == 2;  
            }
        };

        //Table 
        table = new JTable(model);
        table.setRowHeight(50);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(55, 98, 144));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionBackground(new Color(220, 230, 241));
        table.setShowGrid(true);

        JTableHeader header = table.getTableHeader();
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(650);

        table.getColumnModel().getColumn(2).setCellRenderer(new IconButtonRenderer());
        table.getColumnModel().getColumn(2).setCellEditor(new IconButtonEditor(table));
        
        table.getColumn("Action").setCellRenderer(new ActionIconRenderer());
        table.getColumn("Action").setCellEditor(new ActionIconEditor(new JCheckBox(), table));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(21, 22, 1001, 418);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        tablePanel.add(scrollPane);
    }

    private class ActionIconRenderer extends JPanel implements TableCellRenderer {
    	private final JLabel editLabel;
        private final JLabel viewLabel;
        private final JLabel deleteLabel;

        public ActionIconRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
            setOpaque(true);

            editLabel = new JLabel(loadImage("/novaLearn/assets/edit_black.png",24, 24));
            viewLabel = new JLabel(loadImage("/novaLearn/assets/View icon.png", 24, 24));
            deleteLabel = new JLabel(loadImage("/novaLearn/assets/X icon.png", 24, 24));

            add(editLabel);
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
        private final JLabel editLabel;
        private int currentRow = -1; // Store the row being edited

        public ActionIconEditor(JCheckBox checkBox, JTable table) {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            panel.setBackground(Color.WHITE);

            editLabel = new JLabel(loadImage("/novaLearn/assets/edit_black.png", 24, 24));
            viewLabel = new JLabel(loadImage("/novaLearn/assets/View icon.png", 24, 24));
            deleteLabel = new JLabel(loadImage("/novaLearn/assets/X icon.png", 24, 24));

            panel.add(editLabel);
            panel.add(viewLabel);
            panel.add(deleteLabel);

            editLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            viewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            deleteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            editLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (currentRow >= 0) {
                        String courseCode = table.getValueAt(currentRow, 0).toString();
                        String courseName = table.getValueAt(currentRow, 1).toString();
                        openEditCourseForm(courseCode, courseName, currentRow);
                        fireEditingStopped();
                    }
                }
            });

            viewLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (currentRow >= 0) {
                        String courseName = table.getValueAt(currentRow, 1).toString();
                        fireEditingStopped();

                        String panelName = "adminView_" + courseName;
                        Component existingPanel = findComponentByName(panelName);

                        if (existingPanel == null) {
                            AdminViewCoursePanel viewPanel = new AdminViewCoursePanel(parentPanel, cardLayout, courseName);
                            viewPanel.setName(panelName);
                            parentPanel.add(viewPanel, panelName);
                        }

                        cardLayout.show(parentPanel, panelName);
                    }
                }
            });

            deleteLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (currentRow >= 0) {
                        String instructorName = table.getValueAt(currentRow, 0).toString();
                        int confirm = JOptionPane.showConfirmDialog(table,
                                "Delete record for " + instructorName + "?",
                                "Confirm Delete", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            ((DefaultTableModel) table.getModel()).removeRow(currentRow);
                        }
                        fireEditingStopped();
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.currentRow = row;
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
            return new ImageIcon();
        } catch (Exception e) {
            return new ImageIcon();
        }
    }

    
    //ADDING NEW COURSE
    private void openAddNewCourse() {
        JFrame newCourseFrame = new JFrame("Add New Course");
        newCourseFrame.setSize(400, 350);
        newCourseFrame.setLocationRelativeTo(null);
        newCourseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newCourseFrame.setResizable(false);
        newCourseFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        newCourseFrame.getContentPane().add(mainPanel);

        JLabel logoLabel = new JLabel(new ImageIcon(loadImage("/novaLearn/assets/logo1.png", 50, 50).getImage()));
        logoLabel.setBounds(30, 20, 50, 50);
        mainPanel.add(logoLabel);

        JLabel titleLabel = new JLabel("ADD NEW COURSE");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setBounds(100, 30, 280, 30);
        mainPanel.add(titleLabel);

        JPanel lowerPanel = new JPanel(null);
        lowerPanel.setBackground(new Color(55, 98, 144));
        lowerPanel.setBounds(0, 80, 400, 270);
        mainPanel.add(lowerPanel);

        RoundedTextField courseField = new RoundedTextField(20);
        courseField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        courseField.setHorizontalAlignment(JTextField.CENTER);
        courseField.setBounds(152, 21, 215, 40);
        lowerPanel.add(courseField);

        RoundedTextField courseCodeField = new RoundedTextField(20);
        courseCodeField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        courseCodeField.setHorizontalAlignment(JTextField.CENTER);
        courseCodeField.setBounds(152, 71, 215, 40);
        lowerPanel.add(courseCodeField);

        JLabel courseLabel = new JLabel("COURSE:");
        courseLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        courseLabel.setForeground(Color.WHITE);
        courseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        courseLabel.setBounds(10, 22, 135, 40);
        lowerPanel.add(courseLabel);

        JLabel courseCodeLabel = new JLabel("COURSE CODE:");
        courseCodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        courseCodeLabel.setForeground(Color.WHITE);
        courseCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        courseCodeLabel.setBounds(20, 72, 145, 40);
        lowerPanel.add(courseCodeLabel);

        JButton cancelButton = createRoundedButton("Cancel");
        cancelButton.setBounds(60, 150, 120, 50);
        lowerPanel.add(cancelButton);

        JButton addButton = createRoundedButton("Add");
        addButton.setBounds(220, 150, 120, 50);
        lowerPanel.add(addButton);

        cancelButton.addActionListener(e -> newCourseFrame.dispose());

        addButton.addActionListener(e -> {
            String course = courseField.getText().trim();
            String courseCode = courseCodeField.getText().trim();
            if (!course.isEmpty() && !courseCode.isEmpty()) {
                ((DefaultTableModel) table.getModel()).addRow(new Object[]{courseCode, course, ""});
                newCourseFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(newCourseFrame, "Please enter both course name and code.");
            }
        });

        newCourseFrame.setVisible(true);
    }

    
    //EDITING COURSES
    private void openEditCourseForm(String courseCode, String courseName, int row) {
        JFrame editCourseFrame = new JFrame("Edit Course");
        editCourseFrame.setSize(400, 350);
        editCourseFrame.setLocationRelativeTo(null);
        editCourseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editCourseFrame.setResizable(false);
        editCourseFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        editCourseFrame.getContentPane().add(mainPanel);

        JLabel logoLabel = new JLabel(new ImageIcon(loadImage("/novaLearn/assets/logo1.png", 50, 50).getImage()));
        logoLabel.setBounds(30, 20, 50, 50);
        mainPanel.add(logoLabel);

        JLabel titleLabel = new JLabel("EDIT COURSE");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setBounds(100, 30, 280, 30);
        mainPanel.add(titleLabel);

        JPanel lowerPanel = new JPanel(null);
        lowerPanel.setBackground(new Color(55, 98, 144));
        lowerPanel.setBounds(0, 80, 400, 270);
        mainPanel.add(lowerPanel);

        RoundedTextField courseField = new RoundedTextField(20);
        courseField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        courseField.setHorizontalAlignment(JTextField.CENTER);
        courseField.setBounds(152, 21, 215, 40);
        courseField.setText(courseName); // Pre-fill
        lowerPanel.add(courseField);

        RoundedTextField courseCodeField = new RoundedTextField(20);
        courseCodeField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        courseCodeField.setHorizontalAlignment(JTextField.CENTER);
        courseCodeField.setBounds(152, 71, 215, 40);
        courseCodeField.setText(courseCode); // Pre-fill
        lowerPanel.add(courseCodeField);

        JLabel courseLabel = new JLabel("COURSE:");
        courseLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        courseLabel.setForeground(Color.WHITE);
        courseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        courseLabel.setBounds(10, 22, 135, 40);
        lowerPanel.add(courseLabel);

        JLabel courseCodeLabel = new JLabel("COURSE CODE:");
        courseCodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        courseCodeLabel.setForeground(Color.WHITE);
        courseCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        courseCodeLabel.setBounds(20, 72, 145, 40);
        lowerPanel.add(courseCodeLabel);

        JButton cancelButton = createRoundedButton("Cancel");
        cancelButton.setBounds(60, 150, 120, 50);
        lowerPanel.add(cancelButton);

        JButton saveButton = createRoundedButton("Save");
        saveButton.setBounds(220, 150, 120, 50);
        lowerPanel.add(saveButton);

        cancelButton.addActionListener(e -> editCourseFrame.dispose());

        saveButton.addActionListener(e -> {
            String updatedCourse = courseField.getText().trim();
            String updatedCode = courseCodeField.getText().trim();
            if (!updatedCourse.isEmpty() && !updatedCode.isEmpty()) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setValueAt(updatedCode, row, 0);
                model.setValueAt(updatedCourse, row, 1);
                editCourseFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(editCourseFrame, "Please enter both course name and code.");
            }
        });

        editCourseFrame.setVisible(true);
    }


    private class IconButtonRenderer extends JPanel implements TableCellRenderer {
        public IconButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            this.removeAll();
            add(new JLabel(loadImage("/novaLearn/assets/edit_black.png", 20, 20)));
            add(new JLabel(loadImage("/novaLearn/assets/View icon.png", 20, 20)));
            add(new JLabel(loadImage("/novaLearn/assets/X icon.png", 20, 20)));
            return this;
        }
    }
    
    

    private class IconButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;

        public IconButtonEditor(JTable table) {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

            JButton viewButton = new JButton(loadImage("/novaLearn/assets/view_icon.png", 20, 20));
            JButton editButton = new JButton(loadImage("/novaLearn/assets/edit_icon.png", 20, 20));
            JButton deleteButton = new JButton(loadImage("/novaLearn/assets/delete_icon.png", 20, 20));

            makeIconButton(viewButton);
            makeIconButton(editButton);
            makeIconButton(deleteButton);

            // View Button Action
            viewButton.addActionListener(e -> {
                int row = table.getEditingRow();
                String courseCode = (String) table.getValueAt(row, 0);
                String courseName = (String) table.getValueAt(row, 1);
                JOptionPane.showMessageDialog(table, "Viewing " + courseCode + ": " + courseName);
                openCourseDetailPanel(courseCode, courseName);                 
                stopCellEditing();
            });

            // Edit Button Action (reuse Add form layout)
            editButton.addActionListener(e -> {
                int row = table.getEditingRow();
                String courseCode = (String) table.getValueAt(row, 0);
                String courseName = (String) table.getValueAt(row, 1);

                JFrame editCourseFrame = new JFrame("Edit Course");
                editCourseFrame.setSize(400, 350);
                editCourseFrame.setLocationRelativeTo(null);
                editCourseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                editCourseFrame.setResizable(false);
                editCourseFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

                JPanel mainPanel = new JPanel(null);
                mainPanel.setBackground(Color.WHITE);
                editCourseFrame.getContentPane().add(mainPanel);

                JLabel logoLabel = new JLabel(new ImageIcon(loadImage("/novaLearn/assets/logo1.png", 50, 50).getImage()));
                logoLabel.setBounds(30, 20, 50, 50);
                mainPanel.add(logoLabel);

                JLabel titleLabel = new JLabel("EDIT COURSE");
                titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
                titleLabel.setBounds(100, 30, 280, 30);
                mainPanel.add(titleLabel);

                JPanel lowerPanel = new JPanel(null);
                lowerPanel.setBackground(new Color(55, 98, 144));
                lowerPanel.setBounds(0, 80, 400, 270);
                mainPanel.add(lowerPanel);

                RoundedTextField courseField = new RoundedTextField(20);
                courseField.setFont(new Font("Tahoma", Font.PLAIN, 16));
                courseField.setHorizontalAlignment(JTextField.CENTER);
                courseField.setBounds(152, 21, 215, 40);
                courseField.setText(courseName);
                lowerPanel.add(courseField);

                RoundedTextField courseCodeField = new RoundedTextField(20);
                courseCodeField.setFont(new Font("Tahoma", Font.PLAIN, 16));
                courseCodeField.setHorizontalAlignment(JTextField.CENTER);
                courseCodeField.setBounds(152, 71, 215, 40);
                courseCodeField.setText(courseCode);
                lowerPanel.add(courseCodeField);

                JLabel courseLabel = new JLabel("COURSE:");
                courseLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                courseLabel.setForeground(Color.WHITE);
                courseLabel.setHorizontalAlignment(SwingConstants.CENTER);
                courseLabel.setBounds(10, 22, 135, 40);
                lowerPanel.add(courseLabel);

                JLabel courseCodeLabel = new JLabel("COURSE CODE:");
                courseCodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                courseCodeLabel.setForeground(Color.WHITE);
                courseCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                courseCodeLabel.setBounds(20, 72, 145, 40);
                lowerPanel.add(courseCodeLabel);

                JButton cancelButton = createRoundedButton("Cancel");
                cancelButton.setBounds(60, 150, 120, 50);
                lowerPanel.add(cancelButton);

                JButton saveButton = createRoundedButton("Save");
                saveButton.setBounds(220, 150, 120, 50);
                lowerPanel.add(saveButton);

                cancelButton.addActionListener(ev -> editCourseFrame.dispose());

                saveButton.addActionListener(ev -> {
                    String updatedCourse = courseField.getText().trim();
                    String updatedCode = courseCodeField.getText().trim();
                    if (!updatedCourse.isEmpty() && !updatedCode.isEmpty()) {
                        ((DefaultTableModel) table.getModel()).setValueAt(updatedCode, row, 0);
                        ((DefaultTableModel) table.getModel()).setValueAt(updatedCourse, row, 1);
                        editCourseFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(editCourseFrame, "Please enter both course name and code.");
                    }
                });

                editCourseFrame.setVisible(true);
                stopCellEditing();
            });

            // Delete Button Action
            deleteButton.addActionListener(e -> {
                int row = table.getEditingRow();
                String courseCode = (String) table.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(table, "Are you sure you want to delete " + courseCode + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    ((DefaultTableModel) table.getModel()).removeRow(row);
                    JOptionPane.showMessageDialog(table, "Course " + courseCode + " has been deleted.");
                }
                stopCellEditing();
            });

            panel.add(viewButton);
            panel.add(editButton);
            panel.add(deleteButton);
        }

        private void makeIconButton(JButton button) {
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setOpaque(false);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }


    private void openCourseDetailPanel(String courseCode, String courseName) {
        String panelName = "courseDetail_" + courseCode;

        // Check if the panel already exists
        Component existingPanel = findComponentByName(panelName);
        if (existingPanel == null) {
            StudentCourseViewPanel courseDetailPanel = new StudentCourseViewPanel(parentPanel, cardLayout, courseCode, courseName);
            courseDetailPanel.setName(panelName);
            parentPanel.add(courseDetailPanel, panelName);
        }

        cardLayout.show(parentPanel, panelName);
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