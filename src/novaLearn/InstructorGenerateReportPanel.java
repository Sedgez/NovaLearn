package novaLearn;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
public class InstructorGenerateReportPanel extends JPanel {
   private Image backgroundImage;
   private JPanel parentPanel;
   private CardLayout cardLayout;
   public InstructorGenerateReportPanel(JPanel parentPanel, CardLayout cardLayout) {
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
       JLabel generateReportIcon = new JLabel(loadImage("/novaLearn/assets/genReports-white icon.png", 40, 40));
       generateReportIcon.setBounds(30, 100, 40, 40);
       banner.add(generateReportIcon);
       JLabel title = new JLabel("Generate Report");
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

	    JLabel reportTypeTitle = new JLabel("Generate a Report");
	    reportTypeTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
	    reportTypeTitle.setForeground(new Color(33, 33, 33));
	    reportTypeTitle.setBounds(60, 30, 400, 40);
	    tablePanel.add(reportTypeTitle);

	    // Radio buttons with visual grouping
	    JPanel radioPanel = new JPanel();
	    radioPanel.setLayout(new GridLayout(1, 2, 40, 0));
	    radioPanel.setBackground(Color.WHITE);
	    radioPanel.setBounds(120, 90, 800, 50);

	    JRadioButton studentPerformanceReport = new JRadioButton("Student Performance Report");
	    JRadioButton courseActivityReport = new JRadioButton("Course Activity Report");

	    Font radioFont = new Font("Segoe UI", Font.PLAIN, 22);
	    Color radioFg = new Color(50, 50, 50);

	    studentPerformanceReport.setFont(radioFont);
	    studentPerformanceReport.setForeground(radioFg);
	    studentPerformanceReport.setBackground(Color.WHITE);
	    studentPerformanceReport.setFocusPainted(false);

	    courseActivityReport.setFont(radioFont);
	    courseActivityReport.setForeground(radioFg);
	    courseActivityReport.setBackground(Color.WHITE);
	    courseActivityReport.setFocusPainted(false);

	    ButtonGroup btnGroup = new ButtonGroup();
	    btnGroup.add(studentPerformanceReport);
	    btnGroup.add(courseActivityReport);

	    radioPanel.add(studentPerformanceReport);
	    radioPanel.add(courseActivityReport);
	    tablePanel.add(radioPanel);

	    // Dropdown for course selection
	    JLabel courseLabel = new JLabel("Select Course:");
	    courseLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
	    courseLabel.setBounds(120, 160, 200, 30);
	    tablePanel.add(courseLabel);

	    String[] coursesReport = {
	        "CCS108  |  Object-Oriented Programming",
	        "CCS110  |  Information Management",
	        "CSP107  |  Assembly Language"
	    };
	    JComboBox<String> courseOption = new JComboBox<>(coursesReport);
	    courseOption.setFont(new Font("Segoe UI", Font.PLAIN, 22));
	    courseOption.setForeground(Color.DARK_GRAY);
	    courseOption.setBorder(new RoundedBorder(20));
	    courseOption.setBounds(120, 200, 800, 40);
	    tablePanel.add(courseOption);

	    // Generate Button
	    RoundedButton generateButton = new RoundedButton("Generate Report", 30);
	    generateButton.setBounds(430, 300, 200, 50);
	    tablePanel.add(generateButton);

	    // Popup logic (separated for clarity)
	    generateButton.addActionListener(e -> {
	        String selectedCourse = (String) courseOption.getSelectedItem();

	        if (studentPerformanceReport.isSelected()) {
	            showStudentPerformancePopup(selectedCourse);
	        } else if (courseActivityReport.isSelected()) {
	            showCourseActivityPopup(selectedCourse);
	        } else {
	            JOptionPane.showMessageDialog(this, "Please select a report type.", "Missing Selection", JOptionPane.WARNING_MESSAGE);
	        }
	    });
	}
   
   private void showStudentPerformancePopup(String course) {
	    JFrame frame = new JFrame("Student Performance Report");
	    frame.setSize(850, 550);
	    frame.setLocationRelativeTo(null);
	    frame.setLayout(null);
	    frame.setResizable(false);
	    frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

	    JPanel panel = new JPanel(null);
	    panel.setBounds(0, 0, 850, 550);
	    panel.setBackground(Color.WHITE);
	    frame.add(panel);

	    // Header with logo
	    JLabel logo = new JLabel(loadImage("/novaLearn/assets/logoD.png", 150, 50));
	    logo.setBounds(20, 10, 150, 50);
	    panel.add(logo);

	    JLabel title = new JLabel("Student Performance Report", SwingConstants.CENTER);
	    title.setFont(new Font("Segoe UI", Font.BOLD, 22));
	    title.setForeground(new Color(55, 98, 144));
	    title.setBounds(230, 15, 400, 40);
	    panel.add(title);

	    JLabel courseDetails = new JLabel("Course: " + course, SwingConstants.CENTER);
	    courseDetails.setFont(new Font("Segoe UI", Font.PLAIN, 16));
	    courseDetails.setBounds(230, 50, 400, 30);
	    panel.add(courseDetails);

	    // Table
	    String[] columnNames = {"Student ID", "Name", "Grade"};
	    Object[][] data = {
	        {"202301", "Alice Perez", "92"},
	        {"202302", "John Reyes", "Not Yet Graded"},
	        {"202303", "Clara Tan", "88"},
	    };

	    JTable table = new JTable(data, columnNames);
	    table.setRowHeight(30);
	    table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
	    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setBounds(50, 100, 750, 300);
	    panel.add(scrollPane);

	    // Summary
	    JPanel summaryPanel = new JPanel(null);
	    summaryPanel.setBackground(new Color(55, 98, 144));
	    summaryPanel.setBounds(0, 420, 850, 100);

	    JLabel total = new JLabel("Total Students: 3");
	    total.setFont(new Font("Segoe UI", Font.PLAIN, 16));
	    total.setForeground(Color.WHITE);
	    total.setBounds(100, 25, 200, 30);
	    summaryPanel.add(total);

	    JLabel avg = new JLabel("Average Grade: 90");
	    avg.setFont(new Font("Segoe UI", Font.PLAIN, 16));
	    avg.setForeground(Color.WHITE);
	    avg.setBounds(500, 25, 200, 30);
	    summaryPanel.add(avg);

	    panel.add(summaryPanel);
	    frame.setVisible(true);
	}

   private void showCourseActivityPopup(String course) {
	    JFrame frame = new JFrame("Course Activity Report");
	    frame.setSize(750, 500);
	    frame.setLocationRelativeTo(null);
	    frame.setLayout(null);
	    frame.setResizable(false);
	    frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

	    JPanel panel = new JPanel(null);
	    panel.setBounds(0, 0, 750, 500);
	    panel.setBackground(Color.WHITE);
	    frame.add(panel);

	    // Logo
	    JLabel logo = new JLabel(loadImage("/novaLearn/assets/logoD.png", 150, 50));
	    logo.setBounds(20, 10, 150, 50);
	    panel.add(logo);

	    // Title
	    JLabel title = new JLabel("Course Activity Report", SwingConstants.CENTER);
	    title.setFont(new Font("Segoe UI", Font.BOLD, 24));
	    title.setForeground(new Color(55, 98, 144));
	    title.setBounds(180, 15, 400, 40);
	    panel.add(title);

	    JLabel courseDetails = new JLabel("Course: " + course, SwingConstants.CENTER);
	    courseDetails.setFont(new Font("Segoe UI", Font.PLAIN, 16));
	    courseDetails.setForeground(new Color(80, 80, 80));
	    courseDetails.setBounds(180, 50, 400, 30);
	    panel.add(courseDetails);

	    // Cards Panel
	    JPanel cardPanel = new JPanel();
	    cardPanel.setBounds(50, 110, 650, 300);
	    cardPanel.setBackground(Color.WHITE);
	    cardPanel.setLayout(new GridLayout(2, 2, 30, 30));
	    panel.add(cardPanel);

	    // Metric Cards
	    cardPanel.add(createMetricCard("Number of Contents Uploaded", "12"));
	    cardPanel.add(createMetricCard("Number of Activities Given", "5"));
	    cardPanel.add(createMetricCard("Number of Students Enrolled", "30"));
	    cardPanel.add(createMetricCard("Average Student Grade", "87.5"));

	    // Bottom bar
	    JPanel footer = new JPanel();
	    footer.setBounds(0, 430, 750, 40);
	    footer.setBackground(new Color(55, 98, 144));
	    panel.add(footer);

	    frame.setVisible(true);
	}

   private JPanel createMetricCard(String label, String value) {
	    JPanel card = new JPanel(null);
	    card.setBackground(Color.WHITE);
	    card.setBorder(BorderFactory.createCompoundBorder(
	        BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
	        BorderFactory.createEmptyBorder(10, 10, 10, 10)
	    ));

	    JLabel labelText = new JLabel(label);
	    labelText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
	    labelText.setForeground(new Color(80, 80, 80));
	    labelText.setBounds(10, 10, 250, 30);
	    card.add(labelText);

	    JLabel valueText = new JLabel(value);
	    valueText.setFont(new Font("Segoe UI", Font.BOLD, 28));
	    valueText.setForeground(new Color(55, 98, 144));
	    valueText.setBounds(10, 50, 200, 40);
	    card.add(valueText);

	    return card;
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
