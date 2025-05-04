package novaLearn;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;

public class StudentGradesPanel extends JPanel {

    private Image backgroundImage;
    private JPanel parentPanel;
    private CardLayout cardLayout;

    public StudentGradesPanel(JPanel parentPanel, CardLayout cardLayout) {
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
    }

    private void setupBanner() {
        RoundedPanel banner = new RoundedPanel(backgroundImage);
        banner.setBounds(25, 10, 1043, 171);
        banner.setLayout(null);
        add(banner);

        JLabel gradeIcon = new JLabel(loadImage("/novaLearn/assets/Grades tab icon_white.png", 40, 40));
        gradeIcon.setBounds(30, 100, 40, 40);
        banner.add(gradeIcon);

        JLabel title = new JLabel("Grades");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBounds(75, 105, 300, 40);
        banner.add(title);
    }

    private void setupTable() {
        RoundedPanel tablePanel = new RoundedPanel(Color.WHITE);
        tablePanel.setCornerRadius(30);
        tablePanel.setLayout(null);
        tablePanel.setBounds(25, 197, 1043, 464);
        add(tablePanel);

        String[] columns = {"Course Code", "Course Description", "Grade", "Remarks"};
        Object[][] data = {
            {"CCS108", "Object-Oriented Programming", 90, "Passed"},
            {"CCS110", "Information Management", 89, "Passed"},
            {"CSP107", "Assembly Language", 78, "Passed"},
            {"GE103", "Purposive Communication", 94, "Passed"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(50);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(55, 98, 144));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(220, 230, 241));
        table.setShowGrid(true);
        table.getTableHeader().setReorderingAllowed(false);

        // Center alignment for specific columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Course Code
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Grade
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Remarks

        // Set fixed column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(0).setMinWidth(100);
        columnModel.getColumn(0).setMaxWidth(120);

        columnModel.getColumn(1).setPreferredWidth(400); 
        
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(2).setMinWidth(70);
        columnModel.getColumn(2).setMaxWidth(90);

        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(3).setMinWidth(90);
        columnModel.getColumn(3).setMaxWidth(120);

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
}
