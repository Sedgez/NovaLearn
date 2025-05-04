package novaLearn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminGenerateReportPanel extends JPanel {
    private Image backgroundImage;

    public AdminGenerateReportPanel(JPanel parentPanel, CardLayout cardLayout) {
        setLayout(null);
        setPreferredSize(new Dimension(1400, 800));
        loadAssets();
        setupBanner();
        setupReportSection();
    }

    private void loadAssets() {
        backgroundImage = new ImageIcon(getClass().getResource("/novaLearn/assets/dash_bg.png")).getImage();
    }

    private void setupBanner() {
        RoundedPanel banner = new RoundedPanel(backgroundImage);
        banner.setBounds(25, 10, 1043, 171);
        banner.setLayout(null);
        add(banner);

        JLabel icon = new JLabel(loadImage("/novaLearn/assets/genReports-white icon.png", 40, 40));
        icon.setBounds(30, 100, 40, 40);
        banner.add(icon);

        JLabel title = new JLabel("Generate Administrative Reports");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBounds(80, 105, 600, 40);
        banner.add(title);
    }

    private void setupReportSection() {
        RoundedPanel container = new RoundedPanel(Color.WHITE);
        container.setCornerRadius(30);
        container.setLayout(null);
        container.setBounds(25, 197, 1043, 464);
        add(container);

        JLabel title = new JLabel("Select Report Type");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBounds(60, 50, 400, 40);
        container.add(title);

        JComboBox<String> reportDropdown = new JComboBox<>(new String[]{
                "Select...",
                "Student Report",
                "Instructor Report",
                "Course Report",
                "Overall Summary"
        });
        reportDropdown.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        reportDropdown.setBounds(60, 120, 400, 45);
        container.add(reportDropdown);

        RoundedButton generateBtn = new RoundedButton("Generate Report", 30);
        generateBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        generateBtn.setBounds(480, 120, 220, 45);
        container.add(generateBtn);

        generateBtn.addActionListener(e -> {
            String selected = (String) reportDropdown.getSelectedItem();
            if (selected == null || selected.equals("Select...")) {
                JOptionPane.showMessageDialog(this, "Please choose a report type.", "Missing Selection", JOptionPane.WARNING_MESSAGE);
            } else {
                showReportPopup(selected);
            }
        });
    }

    private void showReportPopup(String reportTitle) {
        JFrame frame = new JFrame(reportTitle);
        frame.setSize(850, 550);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

        JPanel panel = new JPanel(null);
        panel.setBounds(0, 0, 850, 550);
        panel.setBackground(Color.WHITE);
        frame.add(panel);

        JLabel logo = new JLabel(loadImage("/novaLearn/assets/logoD.png", 150, 50));
        logo.setBounds(30, 15, 150, 50);
        panel.add(logo);

        JLabel title = new JLabel(reportTitle, SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(55, 98, 144));
        title.setBounds(220, 15, 400, 40);
        panel.add(title);

        JLabel subtitle = new JLabel("Generated on: " + java.time.LocalDate.now(), SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setBounds(220, 50, 400, 30);
        panel.add(subtitle);

        String[] columnNames = {"ID", "Name", "Details"};
        Object[][] data = {
            {"001", "John Doe", "Sample Info"},
            {"002", "Jane Smith", "More Info"}
        };

        JTable table = new JTable(data, columnNames);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 750, 300);
        panel.add(scrollPane);

        JPanel summaryPanel = new JPanel(null);
        summaryPanel.setBackground(new Color(55, 98, 144));
        summaryPanel.setBounds(0, 420, 850, 100);

        JLabel total = new JLabel("Total Records: " + data.length);
        total.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        total.setForeground(Color.WHITE);
        total.setBounds(100, 30, 200, 30);
        summaryPanel.add(total);

        JLabel note = new JLabel("Note: This is a demo summary.");
        note.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        note.setForeground(Color.WHITE);
        note.setBounds(500, 30, 300, 30);
        summaryPanel.add(note);

        panel.add(summaryPanel);
        frame.setVisible(true);
    }

    private ImageIcon loadImage(String path, int width, int height) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                return new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ImageIcon();
    }

    public class RoundedButton extends JButton {
	    private int radius;

	    public RoundedButton(String text, int radius) {
	        super(text);
	        this.radius = radius;
	        setOpaque(false);
	        setFocusPainted(false);
	        setContentAreaFilled(false);
	        setBorderPainted(false);
	        setForeground(Color.WHITE);
	        setFont(new Font("Segoe UI", Font.BOLD, 19));
	        setCursor(new Cursor(Cursor.HAND_CURSOR));
	        setBackground(new Color(72, 181, 209)); // Light blue background
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        Graphics2D g2 = (Graphics2D) g.create();
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	        // Background
	        g2.setColor(getBackground());
	        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

	        // Text
	        FontMetrics fm = g2.getFontMetrics();
	        Rectangle stringBounds = fm.getStringBounds(getText(), g2).getBounds();
	        int textX = (getWidth() - stringBounds.width) / 2;
	        int textY = (getHeight() - stringBounds.height) / 2 + fm.getAscent();

	        g2.setColor(getForeground());
	        g2.drawString(getText(), textX, textY);

	        g2.dispose();
	    }

	    @Override
	    protected void paintBorder(Graphics g) {
	        // Optional: Draw border if needed
	    }

	    @Override
	    public Dimension getPreferredSize() {
	        return new Dimension(super.getPreferredSize().width + 20, super.getPreferredSize().height + 10);
	    }
	}
}