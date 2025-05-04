package novaLearn;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class InstructorNewSubmissionsPanel extends JPanel {

    private JPanel parentPanel;
    private CardLayout cardLayout;

    public InstructorNewSubmissionsPanel(JPanel parentPanel, CardLayout cardLayout) {
        setLayout(null);
        setPreferredSize(new Dimension(1400, 800));
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;

        setupBanner();
        setupTablePanel();
    }

    private void setupBanner() {
        Image bannerBg = new ImageIcon(getClass().getResource("/novaLearn/assets/dash_bg.png")).getImage();
        RoundedPanel banner = new RoundedPanel(bannerBg);
        banner.setBounds(25, 10, 1043, 171);
        banner.setLayout(null);
        add(banner);

        JLabel iconLabel = new JLabel(loadImage("/novaLearn/assets/new submission.png", 40, 40));
        iconLabel.setBounds(30, 100, 40, 40);
        banner.add(iconLabel);

        JLabel title = new JLabel("New Submissions");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBounds(75, 105, 400, 40);
        banner.add(title);
    }

    private void setupTablePanel() {
        RoundedPanel tablePanel = new RoundedPanel(Color.WHITE);
        tablePanel.setCornerRadius(30);
        tablePanel.setLayout(null);
        tablePanel.setBounds(25, 200, 1043, 468);
        add(tablePanel);

        String[] columns = {"Course Code", "Activity Title", "Submitted By", "Date Submitted"};
        Object[][] data = {
            {"CCS108", "OOP Final Project", "Christian Balinado", "2025-04-30"},
            {"CCS110", "ER Diagram", "Kaye Ann Joy Oquindo", "2025-04-29"},
            {"CSP107", "Assembly Mini Program", "Charles Carreon", "2025-04-28"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(45);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(55, 98, 144));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(220, 230, 241));
        table.setShowGrid(true);
        table.setAutoCreateRowSorter(true); // Optional: Enable sorting

        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);
        columnModel.getColumn(0).setMinWidth(150);
        columnModel.getColumn(0).setMaxWidth(150);

        columnModel.getColumn(1).setPreferredWidth(400);
        columnModel.getColumn(1).setMinWidth(400);
        columnModel.getColumn(1).setMaxWidth(400);

        columnModel.getColumn(2).setPreferredWidth(250);
        columnModel.getColumn(2).setMinWidth(250);
        columnModel.getColumn(2).setMaxWidth(250);

        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(3).setMinWidth(200);
        columnModel.getColumn(3).setMaxWidth(200);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 25, 1000, 362);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 2, true));
        tablePanel.add(scrollPane);

        JButton backButton = new JButton("← Back");
        backButton.setBounds(20, 406, 100, 40);
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        backButton.setBackground(new Color(255, 153, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(new LineBorder(new Color(255, 153, 0), 1, true));
        backButton.addActionListener(e -> cardLayout.show(parentPanel, "dashboard"));
        tablePanel.add(backButton);
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
