package novaLearn;

import javax.swing.*;
import javax.swing.border.Border; // Required for RoundedBorder or general Border usage
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer; // Import for BasicComboBoxRenderer
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.EventObject; // Import for EventObject

public class AdminStudentTrackerPanel extends JPanel {

	private Image backgroundImage;
	private JPanel parentPanel;
	private CardLayout cardLayout;
	private DefaultTableModel studentTableModel; // <--- ADD THIS: Class member to hold the table model

	// No-argument constructor
	public AdminStudentTrackerPanel() {
		this(new JPanel(), new CardLayout());
	}

	// Main constructor
	public AdminStudentTrackerPanel(JPanel parentPanel, CardLayout cardLayout) {
		setLayout(null);
		setPreferredSize(new Dimension(1400, 800));
		this.parentPanel = parentPanel;
		this.cardLayout = cardLayout;
		loadAssets();
		setupBanner();
		setupTable(); // setupTable will now initialize studentTableModel
	}

	// Load image resources
	private void loadAssets() {
		backgroundImage = loadImage("/novaLearn/assets/dash_bg.png").getImage();
	}

	// Setup the top banner
	private void setupBanner() {
		RoundedPanel banner = new RoundedPanel(backgroundImage);
		banner.setBounds(25, 10, 1043, 171);
		banner.setLayout(null);
		add(banner);

		JLabel studentIcon = new JLabel(loadImage("/novaLearn/assets/studentAdmin_white.png", 40, 40));
		studentIcon.setBounds(30, 100, 40, 40);
		banner.add(studentIcon);

		JLabel panelTitle = new JLabel("Student Tracker");
		panelTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
		panelTitle.setForeground(Color.WHITE);
		panelTitle.setBounds(75, 105, 300, 40);
		banner.add(panelTitle);

		JButton addStudentButton = new JButton(loadImage("/novaLearn/assets/Add course icon.png", 30, 30));
		addStudentButton.setBounds(980, 105, 40, 40);
		addStudentButton.setFocusPainted(false);
		addStudentButton.setBorderPainted(false);
		addStudentButton.setContentAreaFilled(false);
		addStudentButton.addActionListener(e -> openAddStudentWindow());
		banner.add(addStudentButton);
	}

	private void setupTable() {
	    RoundedPanel tablePanel = new RoundedPanel(Color.WHITE);
	    tablePanel.setCornerRadius(30);
	    tablePanel.setLayout(null);
	    tablePanel.setBounds(25, 197, 1043, 464);
	    add(tablePanel);

	    String[] columns = { "Student", "Student ID", "Status", "Action" };
	    Object[][] data = {
	        { "Balinado, Christian", "2401059", "Active", "" },
	        { "Carreon, Charles", "2202191", "Active", "" },
	        { "Cayaga, Kurt John", "2300127", "Active", "" },
	        { "Oquindo, Kaye Ann Joy", "2410061", "Active", "" },
	        { "Sy, Christian Raphael", "2301010", "Active", "" }
	    };

	    this.studentTableModel = new DefaultTableModel(data, columns) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return column == 3; // Only "Action" column is editable
	        }
	    };

	    JTable table = new JTable(this.studentTableModel);
	    table.setRowHeight(50);
	    table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
	    table.setShowHorizontalLines(true);
	    table.setShowVerticalLines(false);
	    table.setGridColor(new Color(220, 220, 220));
	    table.setIntercellSpacing(new Dimension(0, 1));
	    table.setSelectionBackground(new Color(220, 230, 241));
	    table.setSelectionForeground(Color.BLACK);

	    JTableHeader tableHeader = table.getTableHeader();
	    tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
	    tableHeader.setBackground(new Color(55, 98, 144));
	    tableHeader.setForeground(Color.WHITE);
	    tableHeader.setPreferredSize(new Dimension(1000, 40));
	    tableHeader.setReorderingAllowed(false);

	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

	    // Apply to all columns except "Action"
	    for (int i = 0; i < table.getColumnCount() - 1; i++) {
	        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	    }

	    // Setup "Action" column
	    table.getColumn("Action").setCellRenderer(new ActionIconRenderer());
	    table.getColumn("Action").setCellEditor(new ActionIconEditor(new JCheckBox(), table));

	    // Column widths
	    TableColumnModel columnModel = table.getColumnModel();
	    columnModel.getColumn(0).setPreferredWidth(450); // Student name
	    columnModel.getColumn(1).setPreferredWidth(180); // Student ID
	    columnModel.getColumn(2).setPreferredWidth(180); // Status
	    columnModel.getColumn(3).setPreferredWidth(120); // Action

	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setBounds(21, 22, 1001, 418);
	    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1, true));
	    scrollPane.getViewport().setBackground(Color.WHITE);
	    tablePanel.add(scrollPane);
	}
	
	// --- Method to open the Add Student Window ---
	private void openAddStudentWindow() {
		// Making jframe
		JFrame addStudentFrame = new JFrame("Add New Student");
		addStudentFrame.setSize(725, 512); // Adjusted height slightly for buttons
		addStudentFrame.setLocationRelativeTo(this); // Center relative to parent component
		addStudentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addStudentFrame.getContentPane().setLayout(null); // Add layout to content pane
		addStudentFrame.setResizable(false);
		// Ensure frame icon path is correct relative to your classpath
		try {
			URL iconUrl = getClass().getResource("/novaLearn/assets/frame_icon.png");
			if (iconUrl != null) {
				addStudentFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(iconUrl));
			} else {
				System.err.println("Frame icon not found: /novaLearn/assets/frame_icon.png");
			}
		} catch (Exception e) {
			System.err.println("Error loading frame icon: " + e.getMessage());
		}

		// Background white (using content pane directly)
		JPanel mainPanel = (JPanel) addStudentFrame.getContentPane();
		mainPanel.setBackground(Color.WHITE);

		// Logo (using loadImage helper)
		JLabel logoLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/novaLearn/assets/logoD.png"))
                .getImage().getScaledInstance(185, 61, Image.SCALE_SMOOTH)));
        logoLabel.setBounds(10, 10, 185, 61);
        mainPanel.add(logoLabel);

		// Title
		JLabel titleLabel = new JLabel("ADD STUDENT");
		titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22)); // Matched font size
		titleLabel.setForeground(new Color(0, 64, 128));
		titleLabel.setBounds(421, 25, 280, 40); // Adjusted position
		mainPanel.add(titleLabel);

		// Background color blue (form panel)
		JPanel formPanel = new JPanel(null);
		formPanel.setBackground(new Color(55, 98, 144));
		// Position form panel below header, leave space for buttons at bottom
		formPanel.setBounds(0, 70, 711, 537); // Adjusted height
		mainPanel.add(formPanel);

		// --- Form Fields ---
		int labelX = 30;
		int fieldX = 30;
		int fieldWidthFull = 540; // Width spanning most of the panel
		int fieldWidthHalf = 260; // Width for side-by-side fields
		int fieldXSecond = fieldX + fieldWidthHalf + 20; // X for second column fields
		int rowHeight = 25; // Height of text fields/combos
		int labelHeight = 20;
		int vGap = 10; // Gap between label and field
		int rowGap = 15; // Gap between rows
		int currentY = 20; // Starting Y inside formPanel
		formPanel.setLayout(null);

		// --- Row 1: Last Name & First Name ---
		JLabel lastNameLabel = new JLabel("LAST NAME:");
		lastNameLabel.setBounds(10, 50, 150, 20);
		lastNameLabel.setForeground(Color.WHITE);
		lastNameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		formPanel.add(lastNameLabel);

		JTextField lastNameField = new JTextField();
		lastNameField.setBounds(170, 48, 222, 25);
		lastNameField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		formPanel.add(lastNameField);

		JLabel firstNameLabel = new JLabel("FIRST NAME:");
		firstNameLabel.setBounds(10, 92, 150, 20);
		firstNameLabel.setForeground(Color.WHITE);
		firstNameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		formPanel.add(firstNameLabel);

		JTextField firstNameField = new JTextField();
		firstNameField.setBounds(170, 90, 222, 25);
		firstNameField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		formPanel.add(firstNameField);

		currentY += labelHeight + rowHeight + rowGap; // Move Y for next row

		// --- Row 2: Complete Address ---
		JLabel addressLabel = new JLabel("COMPLETE ADDRESS:");
		addressLabel.setBounds(10, 175, 200, 20);
		addressLabel.setForeground(Color.WHITE);
		addressLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		formPanel.add(addressLabel);

		JTextField addressField = new JTextField();
		addressField.setBounds(170, 173, 517, 25);
		addressField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		formPanel.add(addressField);

		// --- Row 3: Program (Aligned with Address) ---
		JLabel programLabel = new JLabel("PROGRAM:");
		programLabel.setBounds(10, 133, 150, 20);
		programLabel.setForeground(Color.WHITE);
		programLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		formPanel.add(programLabel);

		JComboBox<String> programComboBox = new JComboBox<>(new String[] { "Select Program", "BSIT", "BSCS", "BSCpE",
				"BSIS" });
		programComboBox.setBounds(170, 131, 222, 25);
		programComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		programComboBox.setBackground(Color.WHITE);
		programComboBox.setForeground(Color.BLACK);
		programComboBox.setRenderer(new BasicComboBoxRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (component instanceof JLabel) {
					((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
				}
				return component;
			}
		});
		formPanel.add(programComboBox);

		currentY += labelHeight + rowHeight + rowGap; // Move Y for next row

		// --- Row 4: DOB & Religion ---
		JLabel dobLabel = new JLabel("DATE OF BIRTH:");
		dobLabel.setBounds(402, 92, 150, 20);
		dobLabel.setForeground(Color.WHITE);
		dobLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		formPanel.add(dobLabel);

		JTextField dobField = new JTextField();
		dobField.setBounds(540, 90, 147, 25);
		dobField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dobField.setToolTipText("Enter date as YYYY-MM-DD");
		formPanel.add(dobField);

		JLabel religionLabel = new JLabel("RELIGION:");
		religionLabel.setBounds(10, 221, 120, 20);
		religionLabel.setForeground(Color.WHITE);
		religionLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		formPanel.add(religionLabel);

		JTextField religionField = new JTextField();
		religionField.setBounds(170, 219, 222, 25);
		religionField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		formPanel.add(religionField);

		currentY += labelHeight + rowHeight + rowGap; // Move Y for next row

		// --- Row 5: Nationality & Civil Status ---
		JLabel nationalityLabel = new JLabel("NATIONALITY:");
		nationalityLabel.setBounds(10, 261, 134, 20);
		nationalityLabel.setForeground(Color.WHITE);
		nationalityLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		formPanel.add(nationalityLabel);

		JTextField nationalityField = new JTextField();
		nationalityField.setBounds(170, 259, 222, 25);
		nationalityField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		formPanel.add(nationalityField);

		JLabel civilStatusLabel = new JLabel("CIVIL STATUS:");
		civilStatusLabel.setBounds(402, 133, 150, 20);
		civilStatusLabel.setForeground(Color.WHITE);
		civilStatusLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		formPanel.add(civilStatusLabel);

		JComboBox<String> civilStatusComboBox = new JComboBox<>(
				new String[] { "Select Status", "Single", "Married", "Widowed", "Divorced" });
		civilStatusComboBox.setBounds(540, 131, 147, 25);
		civilStatusComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		civilStatusComboBox.setBackground(Color.WHITE);
		civilStatusComboBox.setForeground(Color.BLACK);
		civilStatusComboBox.setRenderer(new BasicComboBoxRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (component instanceof JLabel) {
					((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
				}
				return component;
			}
		});
		formPanel.add(civilStatusComboBox);

		currentY += labelHeight + rowHeight + rowGap; // Move Y for next row

		// --- Row 6: Temporary Password ---
		JLabel passwordLabel = new JLabel("TEMPORARY PASSWORD:");
		passwordLabel.setBounds(10, 305, 200, 20);
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		formPanel.add(passwordLabel);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(197, 303, 195, 25);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		formPanel.add(passwordField);

		currentY += labelHeight + rowHeight + rowGap + 15;

		// --- Buttons ---
		int buttonWidth = 120;
		int buttonHeight = 40;
		// Position buttons relative to the *bottom* of the formPanel
		int buttonY = formPanel.getHeight() - buttonHeight - 30;
		int buttonX1 = (formPanel.getWidth() / 2) - buttonWidth - 15;
		int buttonX2 = (formPanel.getWidth() / 2) + 15;

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(388, 356, 120, 40);
		styleButton(cancelButton);
		formPanel.add(cancelButton);

		JButton addButton = new JButton("Add");
		addButton.setBounds(211, 356, 120, 40);
		styleButton(addButton);
		formPanel.add(addButton);
		
		JLabel lblStudentId = new JLabel("STUDENT NUMBER:  ");
		lblStudentId.setBounds(10, 10, 150, 30);
		lblStudentId.setForeground(Color.WHITE);
		lblStudentId.setFont(new Font("Tahoma", Font.BOLD, 14));
		formPanel.add(lblStudentId);

		// --- Button Actions ---
		cancelButton.addActionListener(e -> addStudentFrame.dispose());

		// --- Inside the addButton ActionListener ---
		addButton.addActionListener(e -> {
		    String lastName = lastNameField.getText().trim();
		    String firstName = firstNameField.getText().trim();
		    String address = addressField.getText().trim();
		    String program = (String) programComboBox.getSelectedItem();
		    String dob = dobField.getText().trim();
		    String religion = religionField.getText().trim();
		    String nationality = nationalityField.getText().trim();
		    String civilStatus = (String) civilStatusComboBox.getSelectedItem();
		    String tempPassword = new String(passwordField.getPassword());

		    List<String> errors = new ArrayList<>();

		    if (lastName.isEmpty() || !lastName.matches("[A-Za-z\\s\\-]+")) {
		        errors.add("Please enter a valid last name.");
		    }
		    if (firstName.isEmpty() || !firstName.matches("[A-Za-z\\s\\-]+")) {
		        errors.add("Please enter a valid first name.");
		    }
		    if (address.isEmpty()) {
		        errors.add("Address cannot be empty.");
		    }
		    if ("Select Program".equals(program)) {
		        errors.add("Please select a valid program.");
		    }
		    if (!dob.matches("\\d{4}-\\d{2}-\\d{2}")) {
		        errors.add("Date of birth must be in YYYY-MM-DD format.");
		    }
		    if (religion.isEmpty() || !religion.matches("[A-Za-z\\s\\-]+")) {
		        errors.add("Please enter a valid religion.");
		    }
		    if (nationality.isEmpty() || !nationality.matches("[A-Za-z\\s\\-]+")) {
		        errors.add("Please enter a valid nationality.");
		    }
		    if ("Select Status".equals(civilStatus)) {
		        errors.add("Please select a valid civil status.");
		    }
		    if (tempPassword.isEmpty() || tempPassword.length() < 6) {
		        errors.add("Temporary password must be at least 6 characters.");
		    }

		    if (!errors.isEmpty()) {
		        JOptionPane.showMessageDialog(addStudentFrame, String.join("\n", errors), "Input Error", JOptionPane.WARNING_MESSAGE);
		        return;
		    }

		    // If all inputs are valid
		    String fullName = lastName + ", " + firstName;
		    String studentId = "TEMP" + (this.studentTableModel.getRowCount() + 1);
		    String status = "Active";
		    Object[] newRowData = {fullName, studentId, status, ""};
		    this.studentTableModel.addRow(newRowData);

		    JOptionPane.showMessageDialog(addStudentFrame, "Student added successfully!",
		            "Success", JOptionPane.INFORMATION_MESSAGE);
		    addStudentFrame.dispose();
		});

		addStudentFrame.setVisible(true);
	} // --- END OF openAddStudentWindow METHOD ---


	// --- Helper method to style buttons (needs to be present in the class) ---
	private void styleButton(JButton button) {
		button.setFocusPainted(false);
		button.setBackground(Color.WHITE);
		button.setForeground(new Color(55, 98, 144));
		button.setFont(new Font("Segoe UI", Font.BOLD, 16));
		button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 15, 5, 15)));
	}

	// --- Action Icon Renderer (Inner Class) ---
	private class ActionIconRenderer extends JPanel implements TableCellRenderer {
		private final JLabel viewLabel;
		private final JLabel deleteLabel;

		public ActionIconRenderer() {
			// Adjust vertical gap for better alignment within the row
			setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Adjusted vgap
			setOpaque(true);
			viewLabel = new JLabel(loadImage("/novaLearn/assets/View icon.png", 24, 24));
			deleteLabel = new JLabel(loadImage("/novaLearn/assets/X icon.png", 24, 24));
			add(viewLabel);
			add(deleteLabel);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setBackground(table.getSelectionBackground());
			} else {
				setBackground(table.getBackground());
			}
			// setOpaque(true); // Already true in constructor
			return this;
		}
	}

	// --- Action Icon Editor (Inner Class) ---
	private class ActionIconEditor extends AbstractCellEditor implements TableCellEditor {
	    private final JPanel panel;
	    private final JLabel viewLabel;
	    private final JLabel deleteLabel;
	    private JTable table;

	    public ActionIconEditor(JCheckBox checkBox, JTable table) {
	        this.table = table;
	        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Adjusted vgap
	        panel.setBackground(Color.WHITE); // Default background
	        viewLabel = new JLabel(loadImage("/novaLearn/assets/View icon.png", 24, 24));
	        deleteLabel = new JLabel(loadImage("/novaLearn/assets/X icon.png", 24, 24));
	        panel.add(viewLabel);
	        panel.add(deleteLabel);
	        viewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        deleteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

	        viewLabel.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mousePressed(MouseEvent e) {
	                int row = table.convertRowIndexToModel(table.getEditingRow());
	                if (row != -1) {
	                    // Get the student ID or any necessary info to fetch details
	                    String studentId = (String) table.getModel().getValueAt(row, 1); // Adjust index if needed

	                    // Call popup instead of loading a new panel
	                    showStudentViewPopup(studentId);
	                }
	                fireEditingStopped(); // Stop editing after action
	            }
	        });

			deleteLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					int row = table.convertRowIndexToModel(table.getEditingRow());
					if (row != -1) {
						String studentName = (String) table.getModel().getValueAt(row, 0);
						int confirm = JOptionPane.showConfirmDialog(table, "Delete record for " + studentName + "?",
								"Confirm Delete", JOptionPane.YES_NO_OPTION);
						if (confirm == JOptionPane.YES_OPTION) {
							// Remove the row from the table model
							((DefaultTableModel) table.getModel()).removeRow(row);
						}
					}
					fireEditingStopped(); // Stop editing after action
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			// Set background for the editor based on selection state
			if (isSelected) {
				panel.setBackground(table.getSelectionBackground());
			} else {
				panel.setBackground(table.getBackground());
			}
			return panel;
		}

		@Override
		public Object getCellEditorValue() { return null; } // Action icons don't return a value
		@Override
		public boolean shouldSelectCell(EventObject anEvent) { return true; } // Allow cell selection on click
		@Override
		public boolean isCellEditable(EventObject e) {
			// Make cell editable on a single mouse click
			return e instanceof MouseEvent && ((MouseEvent) e).getClickCount() == 1;
		}
	}

	private void showStudentViewPopup(String studentId) {
	    JFrame viewFrame = new JFrame("Student Information");
	    viewFrame.setSize(750, 520);
	    viewFrame.setLocationRelativeTo(null);
	    viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    viewFrame.setLayout(null);
	    viewFrame.setResizable(false);
	    viewFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

	    JPanel mainPanel = new JPanel(null);
	    mainPanel.setBackground(Color.WHITE);
	    mainPanel.setBounds(0, 0, 750, 500);
	    viewFrame.add(mainPanel);

	    JLabel logoLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/novaLearn/assets/logoD.png"))
	            .getImage().getScaledInstance(185, 61, Image.SCALE_SMOOTH)));
	    logoLabel.setBounds(10, 10, 185, 61);
	    mainPanel.add(logoLabel);

	    JLabel titleLabel = new JLabel("STUDENT PROFILE");
	    titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
	    titleLabel.setForeground(new Color(55, 98, 144));
	    titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	    titleLabel.setBounds(420, 20, 300, 40);
	    mainPanel.add(titleLabel);

	    JPanel infoPanel = new JPanel(null);
	    infoPanel.setBackground(new Color(55, 98, 144));
	    infoPanel.setBounds(0, 80, 740, 380);
	    mainPanel.add(infoPanel);

	    // --- Sample Data (replace with real DB retrieval using studentId) ---
	    String studentNumber = "2301010";
	    String fullName = "John Doe";
	    String status = "Active";
	    String[][] courseData = {
	        {"CS101", "Introduction to Computer Science"},
	        {"IT102", "Information Technology Fundamentals"},
	        {"SE103", "Software Engineering Principles"}
	    };

	    // --- Info Labels ---
	    JLabel numberLabel = new JLabel("STUDENT NUMBER: " + studentNumber);
	    numberLabel.setForeground(Color.WHITE);
	    numberLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
	    numberLabel.setBounds(30, 20, 350, 25);
	    infoPanel.add(numberLabel);

	    JLabel nameLabel = new JLabel("NAME: " + fullName);
	    nameLabel.setForeground(Color.WHITE);
	    nameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
	    nameLabel.setBounds(30, 50, 350, 25);
	    infoPanel.add(nameLabel);

	    JLabel statusLabel = new JLabel("STATUS: " + status);
	    statusLabel.setForeground(Color.WHITE);
	    statusLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
	    statusLabel.setBounds(30, 80, 350, 25);
	    infoPanel.add(statusLabel);

	    JLabel countLabel = new JLabel("ENROLLED COURSES: " + courseData.length);
	    countLabel.setForeground(Color.WHITE);
	    countLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
	    countLabel.setBounds(30, 110, 350, 25);
	    infoPanel.add(countLabel);

	    // --- Course Table ---
	    String[] columnNames = {"Course Code", "Course Description"};
	    JTable courseTable = new JTable(courseData, columnNames);
	    courseTable.setEnabled(false);
	    courseTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    courseTable.setRowHeight(28);
	    courseTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
	    courseTable.setBackground(Color.WHITE);
	    courseTable.setShowGrid(true);
	    courseTable.setGridColor(Color.LIGHT_GRAY);

	    // Fixed column widths
	    TableColumnModel columnModel = courseTable.getColumnModel();
	    columnModel.getColumn(0).setPreferredWidth(150);
	    columnModel.getColumn(0).setMaxWidth(150);
	    columnModel.getColumn(0).setMinWidth(150);
	    columnModel.getColumn(1).setPreferredWidth(540);

	    JScrollPane scrollPane = new JScrollPane(courseTable);
	    scrollPane.setBounds(30, 150, 680, 170);
	    infoPanel.add(scrollPane);

	    // Close Button
	    JButton closeBtn = new JButton("CLOSE");
	    closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
	    closeBtn.setBounds(310, 335, 120, 30);
	    closeBtn.setBackground(Color.WHITE);
	    closeBtn.setForeground(Color.DARK_GRAY);
	    closeBtn.addActionListener(e -> viewFrame.dispose());
	    infoPanel.add(closeBtn);

	    viewFrame.setVisible(true);
	}

	// --- loadImage methods (Helpers) ---
	private ImageIcon loadImage(String path, int width, int height) {
		try {
			URL imgURL = getClass().getResource(path);
			if (imgURL != null) {
				return new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
			}
			System.err.println("Image not found: " + path);
			return new ImageIcon();
		} catch (Exception e) {
			System.err.println("Error loading image: " + path);
			e.printStackTrace();
			return new ImageIcon();
		}
	}

	private ImageIcon loadImage(String path) {
		try {
			URL imgURL = getClass().getResource(path);
			if (imgURL != null) {
				return new ImageIcon(imgURL);
			}
			System.err.println("Image not found: " + path);
			return new ImageIcon();
		} catch (Exception e) {
			System.err.println("Error loading image: " + path);
			e.printStackTrace();
			return new ImageIcon();
		}
	}

	// --- Other methods moved outside openAddStudentWindow ---

	// Search for a component by name
	private Component findComponentByName(String name) {
		if (parentPanel == null) return null; // Add null check for safety
		for (Component component : parentPanel.getComponents()) {
			if (name.equals(component.getName())) {
				return component;
			}
		}
		return null;
	}

	

	// --- Dummy RoundedPanel and RoundedTextField (Inner Classes) ---

	private static class RoundedPanel extends JPanel {
		private int cornerRadius = 15;
		private Image backgroundImage = null;
		private Color backgroundColor = getBackground(); // Store background

		public RoundedPanel(LayoutManager layout, int radius) { super(layout); this.cornerRadius = radius; setOpaque(false); }
		public RoundedPanel(int radius) { this(new FlowLayout(), radius); }
		public RoundedPanel() { this(new FlowLayout(), 15); }
		public RoundedPanel(Color bgColor) { this(); setBackground(bgColor); }
		public RoundedPanel(Image bgImage) { this(); this.backgroundImage = bgImage; }
		public void setCornerRadius(int radius) { this.cornerRadius = radius; repaint(); }

		@Override public void setBackground(Color bg) { super.setBackground(bg); this.backgroundColor = bg; repaint();}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			int width = getWidth(); int height = getHeight();
			Shape clip = g2.getClip();
			g2.setClip(new java.awt.geom.RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius));
			if (backgroundImage != null) {
				g2.drawImage(backgroundImage, 0, 0, width, height, this);
			} else {
				g2.setColor(this.backgroundColor); // Use stored color
				g2.fillRect(0, 0, width, height);
			}
			g2.setClip(clip);
			g2.dispose();
		}

		@Override
		protected void paintBorder(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(getForeground()); // Or a fixed color like Color.GRAY
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
			g2.dispose();
		}
	}

	private static class RoundedTextField extends JTextField {
		private int cornerRadius = 15;
		private Shape shape;

		public RoundedTextField(int size) {
			super(size);
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5)); // Padding
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
			g2.dispose();
			super.paintComponent(g);
		}

		@Override
		protected void paintBorder(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(getForeground()); // Or Color.GRAY
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
			g2.dispose();
		}

		@Override public boolean contains(int x, int y) {
			if (shape == null || !shape.getBounds().equals(getBounds())) {
				shape = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, cornerRadius, cornerRadius);
			}
			return shape.contains(x, y);
		}
	}
}