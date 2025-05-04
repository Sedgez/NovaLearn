package novaLearn;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableCellEditor;
import java.awt.event.*;


public class InstructorCourseViewPanel extends JPanel {

    private final JPanel parentPanel;
    private final CardLayout cardLayout;
    private final String courseCode;
    private final String courseTitle;

    private JButton streamButton;
    private JButton courseworkButton;
    private JScrollPane tableScrollPane;

    private final List<String> openedTitles = new ArrayList<>();
    private final List<String> submittedCourseworks = new ArrayList<>();
    private ViewType currentView = ViewType.STREAM;

    private enum ViewType { STREAM, COURSEWORK }
    
    private DefaultTableModel courseworkTableModel; // INSTRUCTOR COURSEWORK PANEL
    private DefaultTableModel streamTableModel; // INSTRUCTOR STREAM CONTENT PANEL
 
    private JButton addBtn;

    public InstructorCourseViewPanel(JPanel parentPanel, CardLayout cardLayout, String courseCode, String courseTitle) {
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        setLayout(null);
        setPreferredSize(new Dimension(1400, 800));
        initialize();
    }

    private void initialize() {
        setupBanner(); // BANNER
        setupCourseContent(); // CONTENT OF THE PANEL
     
    }

    private void setupBanner() { // BANNER OF THE PANEL
        ImageIcon bannerIcon = new ImageIcon(getClass().getResource("/novaLearn/assets/dash_bg.png"));
        Image bannerImage = bannerIcon.getImage();
        RoundedPanel banner = new RoundedPanel(bannerImage);
        banner.setBounds(25, 10, 1043, 100);
        banner.setLayout(null);
        add(banner);
    }

    private void setupCourseContent() {
        RoundedPanel contentPanel = new RoundedPanel(Color.WHITE);
        contentPanel.setCornerRadius(30);
        contentPanel.setLayout(null);
        contentPanel.setBounds(25, 130, 1043, 538);
        add(contentPanel);

        addCourseInfo(contentPanel);
        addButtons(contentPanel);

        tableScrollPane = new JScrollPane(createStreamTable());
        tableScrollPane.setBounds(30, 90, 980, 369);
        contentPanel.add(tableScrollPane);

        addBackButton(contentPanel);
    }
    
    

    private void addCourseInfo(JPanel contentPanel) {
        JLabel title = new JLabel(courseTitle);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBounds(30, 20, 600, 30);
        contentPanel.add(title);

        JLabel codeLabel = new JLabel(courseCode);
        codeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        codeLabel.setForeground(Color.GRAY);
        codeLabel.setBounds(30, 50, 400, 25);
        contentPanel.add(codeLabel);
    }


    private void addButtons(JPanel contentPanel) {
        // Scaled icon
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/novaLearn/assets/Add course icon.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton addBtn = new JButton(scaledIcon);
        addBtn.setBounds(750, 25, 35, 35);
        addBtn.setContentAreaFilled(false);
        addBtn.setBorderPainted(false);
        addBtn.setFocusPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add button action changes based on current view
        addBtn.addActionListener(e -> {
            if (currentView == ViewType.STREAM) {
                showAddModulePopup();
            } else if (currentView == ViewType.COURSEWORK) {
                showAddAssignmentPopup();
            }
        });

        streamButton = new JButton("Stream");
        courseworkButton = new JButton("Coursework");

        streamButton.setBounds(800, 25, 100, 35);
        courseworkButton.setBounds(900, 25, 110, 35);

        styleButton(streamButton, true);
        styleButton(courseworkButton, false);

        streamButton.addActionListener(e -> {
            switchView(ViewType.STREAM);
            currentView = ViewType.STREAM; // update current view
        });

        courseworkButton.addActionListener(e -> {
            switchView(ViewType.COURSEWORK);
            currentView = ViewType.COURSEWORK; // update current view
        });

        contentPanel.add(addBtn);
        contentPanel.add(streamButton);
        contentPanel.add(courseworkButton);
    }



    private void showAddAssignmentPopup() { // METHOD TO ADD COURSEWORK // INSTRUCTOR COURSEWORK 
        JFrame addFrame = new JFrame("Assignment Details");
        addFrame.setSize(838, 466);
        addFrame.setLocationRelativeTo(null);
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFrame.setLayout(null);
        addFrame.setResizable(false);
        addFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 0, 838, 466);
        addFrame.add(mainPanel);

        // Logo
        JLabel logoLabel = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/novaLearn/assets/logo1.png"))
            .getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
        logoLabel.setBounds(20, 10, 70, 70);
        mainPanel.add(logoLabel);
 
        // Title
        JLabel titleLabel = new JLabel("ASSIGNMENT DETAILS:");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        titleLabel.setForeground(new Color(55, 98, 144));
        titleLabel.setBounds(110, 25, 400, 40);
        mainPanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(new Color(55, 98, 144));
        formPanel.setBounds(0, 100, 838, 366);
        mainPanel.add(formPanel);

        // Task Title
        JLabel taskTitleLabel = new JLabel("TASK TITLE:");
        taskTitleLabel.setForeground(Color.WHITE);
        taskTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        taskTitleLabel.setBounds(40, 20, 150, 25);
        formPanel.add(taskTitleLabel);

        JTextField taskTitleField = new JTextField("Enter Task Title");
        taskTitleField.setBounds(40, 45, 320, 30);
        taskTitleField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        formPanel.add(taskTitleField);

        // Deadline
        JLabel deadlineLabel = new JLabel("DEADLINE:");
        deadlineLabel.setForeground(Color.WHITE);
        deadlineLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        deadlineLabel.setBounds(440, 20, 150, 25);
        formPanel.add(deadlineLabel);

        JTextField deadlineField = new JTextField("Enter Deadline");
        deadlineField.setBounds(440, 45, 320, 30);
        deadlineField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        formPanel.add(deadlineField);

        // Instruction
        JLabel instructionLabel = new JLabel("INSTRUCTION:");
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        instructionLabel.setBounds(40, 90, 150, 25);
        formPanel.add(instructionLabel);

        JTextArea instructionArea = new JTextArea("Enter Instruction");
        instructionArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
        instructionArea.setLineWrap(true);
        instructionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(instructionArea);
        scrollPane.setBounds(40, 115, 720, 150);
        formPanel.add(scrollPane);

        // Save Button
        JButton saveBtn = new JButton("Save");
        styleButton(saveBtn, false); // or true, depending on which button you're styling
        saveBtn.setBounds(530, 290, 100, 35);
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(Color.DARK_GRAY);
        saveBtn.addActionListener(e -> {
            String taskTitle = taskTitleField.getText().trim();
            String deadline = deadlineField.getText().trim();
            String instruction = instructionArea.getText().trim();

            if (taskTitle.isEmpty() || deadline.isEmpty() || instruction.isEmpty()) {
                JOptionPane.showMessageDialog(addFrame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add to coursework table model
            courseworkTableModel.addRow(new Object[] { deadline, taskTitle, null });

            JOptionPane.showMessageDialog(addFrame, "Assignment Saved!");
            addFrame.dispose();
        });


        formPanel.add(saveBtn);

        // Cancel Button
        JButton cancelBtn = new JButton("Cancel");
        styleButton(saveBtn, false); // or true, depending on which button you're styling
        cancelBtn.setBounds(650, 290, 100, 35);
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setForeground(Color.DARK_GRAY);
        cancelBtn.addActionListener(e -> addFrame.dispose());
        formPanel.add(cancelBtn);

        addFrame.setVisible(true);
    }

    private void showAddModulePopup() { // METHOD TO ADD MODULE TO THE TABLE // STREAM
        JFrame addFrame = new JFrame("Module Details");
        addFrame.setSize(838, 466);
        addFrame.setLocationRelativeTo(null);
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFrame.setLayout(null);
        addFrame.setResizable(false);
        addFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 0, 838, 466);
        addFrame.add(mainPanel);

        // Logo
        JLabel logoLabel = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/novaLearn/assets/logo1.png"))
            .getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
        logoLabel.setBounds(20, 10, 70, 70);
        mainPanel.add(logoLabel);

        // Title
        JLabel titleLabel = new JLabel("MODULE DETAILS:");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        titleLabel.setForeground(new Color(55, 98, 144));
        titleLabel.setBounds(110, 25, 400, 40);
        mainPanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(new Color(55, 98, 144));
        formPanel.setBounds(0, 100, 838, 366);
        mainPanel.add(formPanel);

        // Module Title
        JLabel moduleTitleLabel = new JLabel("TITLE:");
        moduleTitleLabel.setForeground(Color.WHITE);
        moduleTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        moduleTitleLabel.setBounds(40, 20, 150, 25);
        formPanel.add(moduleTitleLabel);

        JTextField moduleTitleField = new JTextField("Enter Module Title");
        moduleTitleField.setBounds(40, 45, 320, 30);
        moduleTitleField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        formPanel.add(moduleTitleField);

        // Date Posted
        JLabel datePostedLabel = new JLabel("DATE POSTED:");
        datePostedLabel.setForeground(Color.WHITE);
        datePostedLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        datePostedLabel.setBounds(440, 20, 150, 25);
        formPanel.add(datePostedLabel);

        JTextField datePostedField = new JTextField("Enter Date (e.g., 2025-05-03)");
        datePostedField.setBounds(440, 45, 320, 30);
        datePostedField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        formPanel.add(datePostedField);

        // File Upload
        JLabel fileLabel = new JLabel("UPLOAD FILE:");
        fileLabel.setForeground(Color.WHITE);
        fileLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        fileLabel.setBounds(40, 90, 150, 25);
        formPanel.add(fileLabel);

        JTextField filePathField = new JTextField();
        filePathField.setEditable(false);
        filePathField.setBounds(40, 115, 520, 30);
        formPanel.add(filePathField);

        JButton browseBtn = new JButton("Browse...");
        browseBtn.setBounds(580, 115, 180, 30);
        browseBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(addFrame);
            if (result == JFileChooser.APPROVE_OPTION) {
                filePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        formPanel.add(browseBtn);

        // Save Button
        JButton saveBtn = new JButton("Save");
        styleButton(saveBtn, false);
        saveBtn.setBounds(530, 290, 100, 35);
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(Color.DARK_GRAY);
        saveBtn.addActionListener(e -> {
            String datePosted = datePostedField.getText().trim();
            String title = moduleTitleField.getText().trim();

            if (datePosted.isEmpty() || title.isEmpty()) {
                JOptionPane.showMessageDialog(addFrame, "Please fill in all required fields.");
                return;
            }

            // Add to stream table model
            streamTableModel.addRow(new Object[]{datePosted, title, null});

            JOptionPane.showMessageDialog(addFrame, "Module Saved!");
            addFrame.dispose();
        });
        formPanel.add(saveBtn);  // ✅ This was missing

        // Cancel Button
        JButton cancelBtn = new JButton("Cancel");
        styleButton(cancelBtn, false);
        cancelBtn.setBounds(650, 290, 100, 35);
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setForeground(Color.DARK_GRAY);
        cancelBtn.addActionListener(e -> addFrame.dispose());
        formPanel.add(cancelBtn);

        addFrame.setVisible(true);
    }


    
    private void styleButton(JButton button, boolean selected) {
        if (selected) {
            button.setBackground(new Color(255, 153, 0));
            button.setFont(new Font("Segoe UI", Font.BOLD, 15));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder());
        } else {
            button.setFont(new Font("Segoe UI", Font.BOLD, 15));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.GRAY);
            button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
    }

    private void switchView(ViewType view) {
        if (currentView == view) return;
        currentView = view;

        styleButton(streamButton, view == ViewType.STREAM);
        styleButton(courseworkButton, view == ViewType.COURSEWORK);

        JTable newTable = (view == ViewType.STREAM) ? createStreamTable() : createCourseworkTable();
        tableScrollPane.setViewportView(newTable);

        // Update Add button tooltip or icon depending on view
        if (addBtn != null) {
            if (view == ViewType.STREAM) {
                addBtn.setToolTipText("Add Module");
                // Optionally: change icon here too
            } else {
                addBtn.setToolTipText("Add Assignment");
                // Optionally: change icon here too
            }
        }
    }



    private void addBackButton(JPanel contentPanel) { // THE BACK BUTTON FOR STREAM AND COURSEWORK
    	
        JButton backButton = new JButton("\u2190 Back");
        backButton.setBounds(30, 475, 100, 40);
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        backButton.setBackground(new Color(255, 153, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(new LineBorder(new Color(255, 153, 0), 1, true));
        backButton.addActionListener(e -> cardLayout.show(parentPanel, "Manage Courses"));
        contentPanel.add(backButton);
    }


    private JTable createStreamTable() {
        // Create main panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBounds(0, 0, 1050, 460);

        // Load icons
        ImageIcon editIcon = loadImage("/novaLearn/assets/edit_icon.png", 24, 24);
        ImageIcon deleteIcon = loadImage("/novaLearn/assets/X icon.png", 24, 24);

        // Table data
        String[] columns = {"Date Posted", "Title", "Action"};
        Object[][] data = {
            {"04-20-2025", "FINALS LEARNING MATERIALS (WEEK 13)", null},
            {"04-20-2025", "FINALS LEARNING MATERIALS (WEEK 14)", null},
            {"04-21-2025", "FINALS GROUP RESEARCH PROJECT", null},
            {"04-24-2025", "ACADEMIC PERFORMANCE IMPROVEMENT CONSULTATION - MIDTERM", null}
        };

        // Table model
        streamTableModel = new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        // JTable
        JTable table = new JTable(streamTableModel);
        table.setRowHeight(50);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(55, 98, 144));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(220, 230, 241));
        table.setShowGrid(true);
        table.getTableHeader().setReorderingAllowed(false);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable auto column resizing
        table.getTableHeader().setResizingAllowed(false); // Disable resizing via mouse
        
        // Lock individual column widths
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setResizable(false);
        }
        
        // Center cell alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        // Custom buttons
        table.getColumnModel().getColumn(2).setCellRenderer(new StreamActionRenderer(editIcon, deleteIcon));
        table.getColumnModel().getColumn(2).setCellEditor(new StreamActionEditor(editIcon, deleteIcon, table, streamTableModel, this));

        
        // ✅ Declare and add scrollPane after the table is created
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(21, 22, 1001, 418);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 2, true));
        tablePanel.add(scrollPane);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row != -1 && col != 2) { // ignore clicks on Action column (index 2)
                	handleRowClick(row, streamTableModel, data);
                }
            }
        });


        return table;
    }


    private JTable createCourseworkTable() {
        ImageIcon editIcon = loadImage("/novaLearn/assets/edit_icon.png", 24, 24);   // black pencil icon
        ImageIcon deleteIcon = loadImage("/novaLearn/assets/X icon.png", 24, 24);    // red trash icon

        Object[][] data = {
            {"04-25-2025", "MIDTERM PRACTICE SET #3", "actions"},
            {"04-27-2025", "MIDTERM REFLECTION PAPER", "actions"},
            {"05-01-2025", "FINALS GROUP RESEARCH PROJECT", "actions"}
        };
        String[] columnNames = {"Date Posted", "Title", "Action"};

        courseworkTableModel = new DefaultTableModel(data, columnNames) {
            public Class<?> getColumnClass(int column) {
                return column == 2 ? ImageIcon.class : String.class;
            }

            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        JTable table = new JTable(courseworkTableModel);
        table.setRowHeight(50);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(55, 98, 144));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(220, 230, 241));
        table.setShowGrid(true);
        table.getTableHeader().setReorderingAllowed(false);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable auto column resizing
        table.getTableHeader().setResizingAllowed(false); // Disable resizing via mouse
        
        //Lock individual column widths
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setResizable(false);
        }

        // Center-align columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        // Set custom renderer and editor for action column
        table.getColumnModel().getColumn(2).setCellRenderer(new StreamActionRenderer(editIcon, deleteIcon));
        table.getColumnModel().getColumn(2).setCellEditor(
            new StreamActionEditor(editIcon, deleteIcon, table, courseworkTableModel, this)
        );

        // Handle clicks on non-action columns to open coursework content
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = table.columnAtPoint(e.getPoint());
                if (column == 2) return; // Ignore action buttons

                int row = table.rowAtPoint(e.getPoint());
                if (row != -1) {
                    String title = (String) courseworkTableModel.getValueAt(row, 1);
                    String dueDate = (String) courseworkTableModel.getValueAt(row, 0);
                    String attachment = "coursework_" + title.replaceAll("\\s+", "_") + ".pdf";

                    String panelKey = "courseworkContent_" + title.replaceAll("\\s+", "_");
                    boolean panelExists = false;

                    for (Component comp : parentPanel.getComponents()) {
                        if (panelKey.equals(comp.getName())) {
                            panelExists = true;
                            break;
                        }
                    }

                    if (!panelExists) {
                        InstructorCourseworkContentPanel courseworkPanel = new InstructorCourseworkContentPanel(
                            parentPanel, cardLayout, title, "Submit your output file here.", dueDate, attachment
                        );
                        courseworkPanel.setName(panelKey);
                        parentPanel.add(courseworkPanel, panelKey);
                    }

                    cardLayout.show(parentPanel, panelKey);
                }
            }
        });

        return table;
    }


 
    public void openEditAssignmentWindow(String title, String instruction, JTable table, int rowIndex) {
        JFrame editFrame = new JFrame("Edit Assignment");
        editFrame.setSize(600, 400);
        editFrame.setLocationRelativeTo(null);
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editFrame.setLayout(null);
        editFrame.setResizable(false);
        editFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 0, 600, 400);
        editFrame.add(mainPanel);

        JLabel logoLabel = new JLabel(new ImageIcon(loadImage("/novaLearn/assets/logo1.png", 50, 50).getImage()));
        logoLabel.setBounds(30, 20, 50, 50);
        mainPanel.add(logoLabel);

        JLabel titleLabel = new JLabel("<html><body style='text-align:right;'>EDIT ASSIGNMENT</body></html>");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setBounds(100, 30, 280, 30);
        mainPanel.add(titleLabel);

        JPanel lowerPanel = new JPanel(null);
        lowerPanel.setBackground(new Color(55, 98, 144));
        lowerPanel.setBounds(0, 80, 600, 320);
        mainPanel.add(lowerPanel);

        RoundedTextField titleField = new RoundedTextField(20);
        titleField.setText(title);
        titleField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        titleField.setBounds(30, 30, 530, 40);
        lowerPanel.add(titleField);

        JTextArea instructionArea = new JTextArea();
        instructionArea.setText(instruction);
        instructionArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
        instructionArea.setLineWrap(true);
        instructionArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(instructionArea);
        scrollPane.setBounds(30, 90, 530, 100);
        lowerPanel.add(scrollPane);

        JButton cancelButton = createRoundedButton("Cancel");
        cancelButton.setBounds(120, 220, 120, 40);
        lowerPanel.add(cancelButton);

        JButton saveButton = createRoundedButton("Save");
        saveButton.setBounds(320, 220, 120, 40);
        lowerPanel.add(saveButton);

        cancelButton.addActionListener(e -> editFrame.dispose());

        saveButton.addActionListener(e -> {
            String updatedTitle = titleField.getText().trim();
            String updatedInstruction = instructionArea.getText().trim();
            if (!updatedTitle.isEmpty() && !updatedInstruction.isEmpty()) {
                // Update JTable row
                table.setValueAt(updatedTitle, rowIndex, 1); // Update the title column (index 1)
                JOptionPane.showMessageDialog(editFrame, "Assignment updated!");
                editFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(editFrame, "Please fill out all fields.");
            }
        });

        editFrame.setVisible(true);
    }


    private JTable createBasicStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(50);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(55, 98, 144));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionBackground(new Color(220, 230, 241));
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            if (!table.getColumnName(i).equalsIgnoreCase("Title")) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        for (int i = 0; i < table.getColumnCount(); i++) {
            if (model.getColumnClass(i) == ImageIcon.class) {
                final int colIndex = i;
                table.getColumnModel().getColumn(colIndex).setCellRenderer(new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        if (value instanceof ImageIcon) {
                            JLabel label = new JLabel((ImageIcon) value);
                            label.setHorizontalAlignment(SwingConstants.CENTER);
                            return label;
                        }
                        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    }
                });
            }
        }

        return table;
    }

    private void handleRowClick(int row, DefaultTableModel model, Object[][] streamData) {
        String title = (String) model.getValueAt(row, 1);
        if (!openedTitles.contains(title)) {
            model.setValueAt(loadImage("/novaLearn/assets/Check icon_green.png", 20, 20), row, 2);
            openedTitles.add(title);
        }
        
        String attachmentLabel = "";
        String datePosted = (String) streamData[row][0];
        String content = row < streamData.length && streamData[row].length > 2 ? (String) streamData[row][2] : "";
        String attachment = row < streamData.length && streamData[row].length > 3 ? (String) streamData[row][3] : "";

        InstructorStreamContentPanel streamPanel = new InstructorStreamContentPanel(
                parentPanel, cardLayout, title, content, datePosted, attachmentLabel, attachment
        );
        parentPanel.add(streamPanel, "streamContent");
        cardLayout.show(parentPanel, "streamContent");
    }

  

    private ImageIcon loadImage(String path, int width, int height) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                return new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            } else {
                System.err.println("Image not found: " + path);
                return new ImageIcon();
            }
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
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        return button;
    }

    class ActionPanelEditor extends AbstractCellEditor implements CellEditor {
    	  private JPanel panel;
    	    private JButton editButton;
    	    private JButton deleteButton;
    	    private int editingRow;
    	    private JTable table;

    	    public ActionPanelEditor() {
    	        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
    	        panel.setOpaque(true);

    	        // Create interactive edit button
    	        editButton = new JButton() {
    	            @Override
    	            protected void paintComponent(Graphics g) {
    	                Graphics2D g2 = (Graphics2D) g.create();
    	                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	                
    	                // Draw circular background
    	                if (getModel().isRollover()) {
    	                    g2.setColor(new Color(245, 245, 245)); // Darker blue hover
    	                } else {
    	                    g2.setColor(new Color(245, 245, 245)); // Light gray default
    	                }
    	                g2.fillOval(0, 0, getSize().width-1, getSize().height-1);
    	                
    	                // Draw icon
    	                ImageIcon icon = loadImage("/novaLearn/assets/edit_icon.png", 18, 18);
    	                icon.paintIcon(this, g2, (getWidth()-icon.getIconWidth())/2, 
    	                                     (getHeight()-icon.getIconHeight())/2);
    	                
    	                // Draw circular border
    	                g2.setColor(new Color(245, 245, 245));
    	                g2.drawOval(0, 0, getSize().width-1, getSize().height-1);
    	                
    	                g2.dispose();
    	            }
    	        };
    	        editButton.setToolTipText("Edit coursework");
    	        editButton.setContentAreaFilled(false);
    	        editButton.setBorderPainted(false);
    	        editButton.setFocusPainted(false);
    	        editButton.setPreferredSize(new Dimension(30, 30));
    	        editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	        editButton.addActionListener(e -> {
    	            fireEditingStopped();
    	            handleEditAction(editingRow);
    	        });

    	        // Create interactive delete button
    	        deleteButton = new JButton() {
    	            @Override
    	            protected void paintComponent(Graphics g) {
    	                Graphics2D g2 = (Graphics2D) g.create();
    	                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	                
    	                // Draw circular background
    	                if (getModel().isRollover()) {
    	                    g2.setColor(new Color(245, 245, 245)); // Darker red hover
    	                } else {
    	                    g2.setColor(new Color(245, 245, 245)); // Light gray default
    	                }
    	                g2.fillOval(0, 0, getSize().width-1, getSize().height-1);
    	                
    	                // Draw icon
    	                ImageIcon icon = loadImage("/novaLearn/assets/X icon.png", 18, 18);
    	                icon.paintIcon(this, g2, (getWidth()-icon.getIconWidth())/2, 
    	                                     (getHeight()-icon.getIconHeight())/2);
    	                
    	                // Draw circular border
    	                g2.setColor(new Color(245, 245, 245));
    	                g2.drawOval(0, 0, getSize().width-1, getSize().height-1);
    	                
    	                g2.dispose();
    	            }
    	        };
    	        deleteButton.setToolTipText("Delete coursework");
    	        deleteButton.setContentAreaFilled(false);
    	        deleteButton.setBorderPainted(false);
    	        deleteButton.setFocusPainted(false);
    	        deleteButton.setPreferredSize(new Dimension(30, 30));
    	        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	        deleteButton.addActionListener(e -> {
    	            fireEditingStopped();
    	            handleDeleteAction(editingRow);
    	        });

    	        panel.add(editButton);
    	        panel.add(deleteButton);
    	    }

        private void handleEditAction(int row) {
            JTable table = (JTable) panel.getParent().getParent();
            String courseworkTitle = (String) table.getValueAt(row, 1);
            JOptionPane.showMessageDialog(panel, 
                "Editing: " + courseworkTitle, 
                "Edit Coursework", 
                JOptionPane.INFORMATION_MESSAGE);
        }

        private void handleDeleteAction(int row) {
            JTable table = (JTable) panel.getParent().getParent();
            String courseworkTitle = (String) table.getValueAt(row, 1);
            
            int confirm = JOptionPane.showConfirmDialog(panel, 
                "Delete coursework: " + courseworkTitle + "?", 
                "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                ((DefaultTableModel) table.getModel()).removeRow(row);
            }
        }

        public Component getTableCellEditorComponent(JTable table, Object value, 
                boolean isSelected, int row, int column) {
            this.table = table;
            editingRow = row;
            panel.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }
}
    
    class StreamActionPanel extends JPanel {
        public JButton editButton;
        public JButton deleteButton;

        public StreamActionPanel(ImageIcon editIcon, ImageIcon deleteIcon) {
            setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
            setOpaque(true);

            editButton = new JButton(editIcon);
            editButton.setBorderPainted(false);
            editButton.setFocusPainted(false);
            editButton.setContentAreaFilled(false);

            deleteButton = new JButton(deleteIcon);
            deleteButton.setBorderPainted(false);
            deleteButton.setFocusPainted(false);
            deleteButton.setContentAreaFilled(false);

            add(editButton);
            add(deleteButton);
        }
    }

    class StreamActionRenderer extends DefaultTableCellRenderer {
        private final ImageIcon editIcon;
        private final ImageIcon deleteIcon;

        public StreamActionRenderer(ImageIcon editIcon, ImageIcon deleteIcon) {
            this.editIcon = editIcon;
            this.deleteIcon = deleteIcon;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            JPanel panel = new JPanel(new GridBagLayout()); // Ensures center alignment
            panel.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);

            JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            iconPanel.setBackground(panel.getBackground());

            JLabel editLabel = new JLabel(editIcon);
            JLabel deleteLabel = new JLabel(deleteIcon);

            iconPanel.add(editLabel);
            iconPanel.add(deleteLabel);

            panel.add(iconPanel); // Center the icon panel inside the cell

            return panel;
        }
    }


    class StreamActionEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final JButton editButton;
        private final JButton deleteButton;
        private final JTable table;
        private final DefaultTableModel model;
        private final InstructorCourseViewPanel instructorViewPanel;

        public StreamActionEditor(ImageIcon editIcon, ImageIcon deleteIcon, JTable table, DefaultTableModel model, InstructorCourseViewPanel instructorViewPanel) {
            this.table = table;
            this.model = model;
            this.instructorViewPanel = instructorViewPanel;

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            panel.setBackground(Color.WHITE);

            editButton = new JButton(editIcon);
            deleteButton = new JButton(deleteIcon);

            editButton.setBorder(null);
            deleteButton.setBorder(null);
            editButton.setContentAreaFilled(false);
            deleteButton.setContentAreaFilled(false);

            panel.add(editButton);
            panel.add(deleteButton);

            // Delete action
            deleteButton.addActionListener(e -> {
                int row = table.getEditingRow();
                if (row != -1) {
                    model.removeRow(row);
                    fireEditingStopped();
                }
            });

            // Edit action
            editButton.addActionListener(e -> {
                int row = table.getEditingRow();
                fireEditingStopped();
                if (row != -1) {
                    String title = (String) table.getValueAt(row, 1); // Column 1 = Title
                    String instruction = "Edit your instructions here..."; // Optionally store real instructions
                    instructorViewPanel.openEditAssignmentWindow(title, instruction, table, row);
                }
            });

        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    
}
