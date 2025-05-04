package novaLearn;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminViewInstructorCoursesPanel extends JPanel {

	private Image backgroundImage;
	private JTable table;
	private DefaultTableModel model;
    private final JPanel parentPanel;
    private final CardLayout cardLayout;

	public AdminViewInstructorCoursesPanel(String instructorName, JPanel parentPanel, CardLayout cardLayout) {
		this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;
		
        setLayout(null);
        setPreferredSize(new Dimension(1400, 800));
        
        loadAssets();
        setupBanner(instructorName);
        setupBackButton();
        setupTable();
    }

	private void loadAssets() {
		backgroundImage = new ImageIcon(getClass().getResource("/novaLearn/assets/dash_bg.png")).getImage();
	}

	private void setupBanner(String instructorName) {
		RoundedPanel banner = new RoundedPanel(backgroundImage);
		banner.setBounds(25, 10, 1043, 171);
		banner.setLayout(null);
		add(banner);
		
		JLabel titleLabel = new JLabel((instructorName).toUpperCase());
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 60));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(59, 38, 900, 71);
		banner.add(titleLabel);
		
		JLabel instructorLabel = new JLabel("INSTRUCTOR");
		instructorLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
		instructorLabel.setForeground(Color.WHITE);
		instructorLabel.setBounds(59, 120, 900, 40);
		banner.add(instructorLabel);
	}

	private void setupTable() {
		RoundedPanel tablePanel = new RoundedPanel(Color.WHITE);
		tablePanel.setCornerRadius(30);
		tablePanel.setLayout(null);
		tablePanel.setBounds(25, 197, 1043, 464);
		add(tablePanel);

		String[] columns = { "Course", "Course Description", "Units", "Number of Students", "Action" };
		Object[][] data = { { "CCS108", "Object-Oriented Programming", "3", "20", "" }, { "ITEW2", "Client/Server-Side Scripting", "3", "49", ""},
		};

		model = new DefaultTableModel(data, columns) {
			public boolean isCellEditable(int row, int column) {
				return column == 4;
			}
		};

		table = new JTable(model);
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
        
        table.getColumn("Action").setCellRenderer(new ActionIconRenderer());
        table.getColumn("Action").setCellEditor(new ActionIconEditor(new JCheckBox(), table));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(21, 22, 1001, 350);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 2, true));
        tablePanel.add(scrollPane);
	}
	
	private void setupBackButton() {
		 // Load and scale the icon
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/novaLearn/assets/Back icon.png"));
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Create the Back button
        JButton backBtn = new JButton("Back", scaledIcon);
        backBtn.setForeground(Color.ORANGE);
        backBtn.setFocusPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setBounds(21, 600, 100, 32);

        backBtn.addActionListener(e -> {
        String previousPanelName = "adminInstructorTracker"; 
        cardLayout.show(parentPanel, previousPanelName); 
        });

        add(backBtn);
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

	    // Handles click actions on view/delete icons
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
	                        String courseName = table.getValueAt(row, 1).toString();

	                        fireEditingStopped();

	                        String panelName = "adminView_" + courseName;
	                        Component existingPanel = findComponentByName(panelName);

	                        if (existingPanel == null) {
	                            AdminViewCoursePanel viewPanel = new AdminViewCoursePanel(parentPanel, cardLayout, courseName); // Pass courseName!
	                            viewPanel.setName(panelName);
	                            parentPanel.add(viewPanel, panelName);
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
	                    String courseCode = table.getValueAt(row, 0).toString();
	                    int confirm = JOptionPane.showConfirmDialog(table,
	                            "Delete record for " + courseCode + "?",
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
