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

public class StudentCourseViewPanel extends JPanel {

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

    public StudentCourseViewPanel(JPanel parentPanel, CardLayout cardLayout, String courseCode, String courseTitle) {
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        setLayout(null);
        setPreferredSize(new Dimension(1400, 800));
        initialize();
    }

    private void initialize() {
        setupBanner();
        setupCourseContent();
    }

    private void setupBanner() {
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
        streamButton = new JButton("Stream");
        courseworkButton = new JButton("Coursework");

        streamButton.setBounds(800, 25, 100, 35);
        courseworkButton.setBounds(900, 25, 110, 35);

        styleButton(streamButton, true);
        styleButton(courseworkButton, false);

        streamButton.addActionListener(e -> switchView(ViewType.STREAM));
        courseworkButton.addActionListener(e -> switchView(ViewType.COURSEWORK));

        contentPanel.add(streamButton);
        contentPanel.add(courseworkButton);
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
    }

    private void addBackButton(JPanel contentPanel) {
        JButton backButton = new JButton("\u2190 Back");
        backButton.setBounds(30, 475, 100, 40);
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        backButton.setBackground(new Color(255, 153, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(new LineBorder(new Color(255, 153, 0), 1, true));
        backButton.addActionListener(e -> cardLayout.show(parentPanel, "courses"));
        contentPanel.add(backButton);
    }

    private JTable createStreamTable() {
        Object[][] streamData = getStreamData();
        String[] columnNames = {"Date Posted", "Title", "Status"};

        Object[][] data = new Object[streamData.length][3];
        for (int i = 0; i < streamData.length; i++) {
            String title = (String) streamData[i][1];
            boolean opened = openedTitles.contains(title);
            data[i][0] = streamData[i][0];
            data[i][1] = title;
            data[i][2] = loadImage(
                opened ? "/novaLearn/assets/Check icon_green.png" : "/novaLearn/assets/Check icon_black.png",
                20, 20
            );
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            public Class<?> getColumnClass(int column) {
                return column == 2 ? ImageIcon.class : String.class;
            }
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = createBasicStyledTable(model);
        table.getColumnModel().getColumn(0).setMinWidth(150);
        table.getColumnModel().getColumn(0).setMaxWidth(150);
        table.getColumnModel().getColumn(2).setMinWidth(80);
        table.getColumnModel().getColumn(2).setMaxWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(750);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != -1) handleRowClick(row, model, streamData);
            }
        });

        return table;
    }

    private JTable createCourseworkTable() {
        Object[][] data = {
            {"04-25-2025", "Final Project Report", loadImage("/novaLearn/assets/Check icon_black.png", 20, 20), "85"},
            {"04-27-2025", "Quiz: Abstraction", loadImage("/novaLearn/assets/Check icon_black.png", 20, 20), "92"},
            {"05-01-2025", "Lab Activity: Encapsulation", loadImage("/novaLearn/assets/Check icon_black.png", 20, 20), "N/A"}
        };
        String[] columnNames = {"Due Date", "Title", "Status", "Grade"};

        for (int i = 0; i < data.length; i++) {
            String title = (String) data[i][1];
            if (submittedCourseworks.contains(title)) {
                data[i][2] = loadImage("/novaLearn/assets/Check icon_green.png", 20, 20);
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            public Class<?> getColumnClass(int column) {
                return column == 2 ? ImageIcon.class : String.class;
            }
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = createBasicStyledTable(model);
        table.getColumnModel().getColumn(0).setMinWidth(150);
        table.getColumnModel().getColumn(0).setMaxWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(580);
        table.getColumnModel().getColumn(2).setMinWidth(80);
        table.getColumnModel().getColumn(2).setMaxWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != -1) {
                    String title = (String) model.getValueAt(row, 1);
                    String dueDate = (String) model.getValueAt(row, 0);
                    String grade = (String) model.getValueAt(row, 3);

                    StudentCourseworkContentPanel courseworkPanel = new StudentCourseworkContentPanel(
                        parentPanel, cardLayout, title, "Submit your output file here.", dueDate, grade,
                        "coursework_" + title.replaceAll("\\s+", "_") + ".pdf"
                    );
                    parentPanel.add(courseworkPanel, "courseworkContent");
                    cardLayout.show(parentPanel, "courseworkContent");
                }
            }
        });

        return table;
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

        String datePosted = (String) streamData[row][0];
        String content = (String) streamData[row][2];
        String attachment = (String) streamData[row][3];

        StudentStreamContentPanel streamPanel = new StudentStreamContentPanel(
                parentPanel, cardLayout, title, content, datePosted, attachment
        );
        parentPanel.add(streamPanel, "streamContent");
        cardLayout.show(parentPanel, "streamContent");
    }

    public void markCourseworkAsSubmitted(String title) {
        JTable table = (JTable) tableScrollPane.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            if (title.equals(model.getValueAt(i, 1))) {
                model.setValueAt(loadImage("/novaLearn/assets/Check icon_green.png", 20, 20), i, 2);
                if (!submittedCourseworks.contains(title)) {
                    submittedCourseworks.add(title);
                }
                break;
            }
        }
    }

    private Object[][] getStreamData() {
        return new Object[][]{
            {"04-20-2025", "FINALS LEARNING MATERIAL (WEEK 13)", "Attached is the learning material for Week 13.", "Object-Oriented-Programming-Week-13.pdf"},
            {"04-20-2025", "FINALS LEARNING MATERIAL (WEEK 14)", "This week's material focuses on encapsulation and abstraction.", "OOP-Week-14.pdf"},
            {"04-21-2025", "FINALS GROUP RESEARCH PROJECT", "Review the attached guidelines for the project.", "Final-Project-Guidelines.pdf"},
            {"04-24-2025", "MIDTERM CONSULTATION", "Read the attached schedule and come prepared.", "Midterm-Consultation-Schedule.pdf"}
        };
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
} 