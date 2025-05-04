package novaLearn;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class AdminViewCourseInstructorPanel extends JPanel {

    private Image backgroundImage;
    private DefaultTableModel model;
    private JPanel parentPanel;
    private CardLayout cardLayout;
    private String courseTitle;

    public AdminViewCourseInstructorPanel(String courseCode, String courseTitle, String instructorName, JPanel parentPanel, CardLayout cardLayout) {
    	 this.parentPanel = parentPanel;
         this.cardLayout = cardLayout;
         this.courseTitle = courseTitle;
    	
        setLayout(null);
        setPreferredSize(new Dimension(1400, 800));
        
        loadAssets();
        setupBanner(courseCode, courseTitle, instructorName);
        setupBackButton();
        setupTable();

    }

    private void loadAssets() {
        backgroundImage = new ImageIcon(getClass().getResource("/novaLearn/assets/dash_bg.png")).getImage();
    }

    private void setupBanner(String courseCode, String courseTitle, String instructorName) {
        RoundedPanel banner = new RoundedPanel(backgroundImage);
        banner.setBounds(25, 10, 1043, 171);
        banner.setLayout(null);
        add(banner);

        JLabel courseCodeLabel = new JLabel((courseCode).toUpperCase());
        courseCodeLabel.setFont(new Font("Segoe UI", Font.BOLD, 45));
        courseCodeLabel.setForeground(Color.WHITE);
        courseCodeLabel.setBounds(55, 30, 900, 45);
        banner.add (courseCodeLabel);
        
        JLabel titleLabel = new JLabel((courseTitle).toUpperCase());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(55, 75, 900, 40);
        banner.add(titleLabel);
        
        JLabel nameLabel = new JLabel((instructorName).toUpperCase());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(55, 110, 900, 40);
        banner.add(nameLabel);

    }

    private void setupTable() {
        RoundedPanel tablePanel = new RoundedPanel(Color.WHITE);
        tablePanel.setCornerRadius(30);
        tablePanel.setLayout(null);
        tablePanel.setBounds(25, 197, 1043, 464);
        add(tablePanel);

        //Table title columns and row data
        String[] columns = {"Student Name", "Student ID", "Section", "Action"};
        Object[][] data = {
            {"Balinado, Christian", "20231234", "2CS-A",""},
            {"Carreon, Charles", "24895678", "2CS-A", ""},
            {"Cayaga, Kurt Joshua", "2085476", "2CS-B", ""},
            {"Oquindo, Kaye Ann Joy", "20212355", "2CS-A", ""},
            {"Sy, Christian Raphael", "29873455", "2CS-B", ""}

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
        scrollPane.setBounds(21, 22, 1001, 355);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 2, true));
        tablePanel.add(scrollPane);
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
        	String previousPanelName = "adminView_" + courseTitle;
            cardLayout.show(parentPanel, previousPanelName);
        });

        add(backButton); // Add the button to the panel
    }

    
    private class ActionIconRenderer extends JPanel implements TableCellRenderer {
		private final JLabel deleteInstructor;

		public ActionIconRenderer() {
			setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
			setOpaque(true);
			
			deleteInstructor = new JLabel(loadImage("/novaLearn/assets/X icon.png", 24, 24));
			
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
		private final JLabel deleteInstructor;
		private int currentRow;

		public ActionIconEditor(JCheckBox checkBox, JTable table) {
			panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
			panel.setBackground(Color.WHITE);

			deleteInstructor = new JLabel(loadImage("/novaLearn/assets/X icon.png", 24, 24));

			panel.add(deleteInstructor);

			deleteInstructor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


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