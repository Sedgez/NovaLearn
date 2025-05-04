package novaLearn;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InstructorAccountPanel extends JPanel {

	private JPanel parentPanel;
	private CardLayout cardLayout;

	public InstructorAccountPanel(JPanel parentPanel, CardLayout cardLayout) {
		setLayout(null);
		setPreferredSize(new Dimension(1400, 800));
		this.parentPanel = parentPanel;
		this.cardLayout = cardLayout;

		setupBanner();
		createAccountInfo();
	}

	private void styleButton(JButton button) {
		button.setFont(new Font("Segoe UI", Font.BOLD, 16));
		button.setBackground(new Color(55, 98, 144));
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createLineBorder(new Color(30, 60, 90), 1, true));
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

	private void setupBanner() {
		Image bannerBg = new ImageIcon(getClass().getResource("/novaLearn/assets/dash_bg.png")).getImage();
		RoundedPanel banner = new RoundedPanel(bannerBg);
		banner.setBounds(25, 10, 1043, 171);
		banner.setLayout(null);
		add(banner);

		JLabel instructorIcon = new JLabel(loadImage("/novaLearn/assets/Account tab icon (2).png", 100, 100));
		instructorIcon.setBounds(30, 35, 100, 100);
		banner.add(instructorIcon);

		JLabel nameLabel = new JLabel("Prof. Janus Raymond Tan");
		nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setBounds(150, 50, 600, 40);
		banner.add(nameLabel);

		JLabel instructorLabel = new JLabel("Instructor");
		instructorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		instructorLabel.setForeground(Color.WHITE);
		instructorLabel.setBounds(150, 85, 200, 30);
		banner.add(instructorLabel);
	}

	private void createAccountInfo() {
		RoundedPanel accountPanel = new RoundedPanel(Color.WHITE);
		accountPanel.setLayout(null);
		accountPanel.setBounds(25, 197, 1043, 461);
		accountPanel.setCornerRadius(30);
		add(accountPanel);

		String[][] details = {
				{"LMS ID", "2301010"},
				{"Sex at Birth", "MALE"},
				{"Civil Status", "SINGLE"},
				{"Nationality", "FILIPINO"},
				{"Religion", "ROMAN CATHOLIC"},
				{"Date of Birth", "JANUARY 1, 2004"},
				{"Complete Address", "BRGY. PULO, CABUYAO CITY, LAGUNA"},
				{"Employment Status", "FULL-TIME"},
		};

		int startY = 20;
		for (int i = 0; i < details.length; i++) {
			int x = 30 + (i % 2) * 500;
			int y = startY + (i / 2) * 60;

			JLabel title = new JLabel(details[i][0]);
			title.setFont(new Font("Arial", Font.BOLD, 18));
			title.setBounds(x, y, 300, 25);
			accountPanel.add(title);

			JLabel value = new JLabel(details[i][1]);
			value.setFont(new Font("Arial", Font.PLAIN, 18));
			value.setBounds(x, y + 25, 450, 25);
			accountPanel.add(value);
		}

		JButton updateButton = new JButton("UPDATE PROFILE");
		styleButton(updateButton);
		updateButton.setBounds(251, 378, 200, 50);
		updateButton.addActionListener(e -> showUpdateProfilePopup());
		accountPanel.add(updateButton);

		JButton changePassButton = new JButton("CHANGE PASSWORD");
		styleButton(changePassButton);
		changePassButton.setBounds(548, 378, 220, 50);
		changePassButton.addActionListener(e -> showChangePasswordPopup());
		accountPanel.add(changePassButton);
	}

	private void showUpdateProfilePopup() {
		JFrame updateFrame = new JFrame("Update Profile");
		updateFrame.setSize(725, 496);
		updateFrame.setLocationRelativeTo(null);
		updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		updateFrame.getContentPane().setLayout(null);
		updateFrame.setResizable(false);
		updateFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

		JPanel mainPanel = new JPanel(null);
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(0, 0, 725, 466);
		updateFrame.getContentPane().add(mainPanel);

		JLabel logoLabel = new JLabel(loadImage("/novaLearn/assets/logoD.png", 185, 61));
		logoLabel.setBounds(10, 10, 185, 61);
		mainPanel.add(logoLabel);

		JLabel titleLabel = new JLabel("UPDATE PROFILE", SwingConstants.RIGHT);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		titleLabel.setForeground(new Color(55, 98, 144));
		titleLabel.setBounds(423, 31, 280, 40);
		mainPanel.add(titleLabel);

		JPanel formPanel = new JPanel(null);
		formPanel.setBackground(new Color(55, 98, 144));
		formPanel.setBounds(0, 75, 715, 390);
		mainPanel.add(formPanel);

		JButton saveBtn = new JButton("SAVE");
		styleButton(saveBtn);
		saveBtn.setBounds(215, 320, 120, 40);
		saveBtn.setBackground(Color.WHITE);
		saveBtn.setForeground(Color.DARK_GRAY);
		saveBtn.addActionListener(e -> {
			JOptionPane.showMessageDialog(updateFrame, "Profile Updated Successfully!");
			updateFrame.dispose();
		});
		formPanel.add(saveBtn);

		JButton cancelBtn = new JButton("CANCEL");
		styleButton(cancelBtn);
		cancelBtn.setBounds(370, 320, 120, 40);
		cancelBtn.setBackground(Color.WHITE);
		cancelBtn.setForeground(Color.DARK_GRAY);
		cancelBtn.addActionListener(e -> updateFrame.dispose());
		formPanel.add(cancelBtn);

		String[] labels = {
				"LMS ID", "LAST NAME", "FIRST NAME", "SEX",
				"DATE OF BIRTH", "STATUS", "ADDRESS", "RELIGION", "NATIONALITY", "CIVIL STATUS"
		};

		int y = 15;
		JTextField instructorNumberTf = new JTextField("2301010");
		instructorNumberTf.setBounds(190, y, 210, 25);
		instructorNumberTf.setEditable(false);
		instructorNumberTf.setBackground(Color.LIGHT_GRAY);
		formPanel.add(createLabel(labels[0], 15, y, formPanel));
		formPanel.add(instructorNumberTf);

		y += 40;
		JTextField lastNameTf = new JTextField();
		formPanel.add(createLabel(labels[1], 15, y, formPanel));
		lastNameTf.setBounds(190, y, 210, 25);
		formPanel.add(lastNameTf);

		y += 40;
		JTextField firstNameTf = new JTextField();
		formPanel.add(createLabel(labels[2], 15, y, formPanel));
		firstNameTf.setBounds(190, y, 210, 25);
		formPanel.add(firstNameTf);

		formPanel.add(createLabel(labels[3], 420, 55, formPanel));
		JComboBox<String> sexComboBox = new JComboBox<>(new String[]{"Male", "Female"});
		sexComboBox.setBounds(567, 46, 122, 25);
		formPanel.add(sexComboBox);

		formPanel.add(createLabel(labels[4], 420, 91, formPanel));
		JTextField dobTf = new JTextField();
		dobTf.setBounds(567, 81, 122, 25);
		formPanel.add(dobTf);

		formPanel.add(createLabel(labels[5], 15, 130, formPanel));
		JComboBox<String> programComboBox = new JComboBox<>(new String[]{
				"Full-Time", "Part-Time", "Contractual"});
		programComboBox.setBounds(190, 125, 210, 25);
		formPanel.add(programComboBox);

		formPanel.add(createLabel(labels[6], 15, 170, formPanel));
		JTextField addressTf = new JTextField();
		addressTf.setBounds(190, 165, 500, 25);
		formPanel.add(addressTf);

		formPanel.add(createLabel(labels[7], 15, 210, formPanel));
		JTextField religionTf = new JTextField();
		religionTf.setBounds(190, 205, 210, 25);
		formPanel.add(religionTf);

		formPanel.add(createLabel(labels[8], 15, 250, formPanel));
		JTextField nationalityTf = new JTextField();
		nationalityTf.setBounds(190, 245, 210, 25);
		formPanel.add(nationalityTf);

		formPanel.add(createLabel(labels[9], 420, 130, formPanel));
		JComboBox<String> civilStatusComboBox = new JComboBox<>(new String[]{"Single", "Married", "Widowed", "Divorced"});
		civilStatusComboBox.setBounds(567, 125, 122, 25);
		formPanel.add(civilStatusComboBox);

		updateFrame.setVisible(true);
	}

	private JLabel createLabel(String text, int x, int y, JPanel panel) {
	    JLabel label = new JLabel(text);
	    label.setForeground(Color.WHITE);
	    label.setFont(new Font("Tahoma", Font.BOLD, 16));
	    label.setBounds(x, y, 200, 20);
	    return label;
	}

	private void showChangePasswordPopup() {
		JFrame passFrame = new JFrame("Change Password");
		passFrame.setSize(483, 410);
		passFrame.setLocationRelativeTo(this);
		passFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		passFrame.getContentPane().setLayout(null);
		passFrame.setResizable(false);
		passFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/novaLearn/assets/frame_icon.png")));

		JPanel mainPanel = new JPanel(null);
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(0, 0, 483, 410);
		passFrame.getContentPane().add(mainPanel);

		JLabel logoLabel = new JLabel(loadImage("/novaLearn/assets/logoD.png", 185, 61));
		logoLabel.setBounds(10, 10, 185, 61);
		mainPanel.add(logoLabel);

		JLabel titleLabel = new JLabel("CHANGE PASSWORD", SwingConstants.RIGHT);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		titleLabel.setForeground(new Color(55, 98, 144));
		titleLabel.setBounds(205, 30, 257, 40);
		mainPanel.add(titleLabel);

		JPanel lowerPanel = new JPanel(null);
		lowerPanel.setBackground(new Color(55, 98, 144));
		lowerPanel.setBounds(0, 90, 483, 280);
		mainPanel.add(lowerPanel);

		ImageIcon showIcon = loadIcon("/novaLearn/assets/showPW.png", 24, 24);
		ImageIcon hideIcon = loadIcon("/novaLearn/assets/hidePW.png", 24, 24);

		addPasswordFieldWithLabel("CURRENT PASSWORD:", 20, lowerPanel, showIcon, hideIcon);
		addPasswordFieldWithLabel("NEW PASSWORD:", 85, lowerPanel, showIcon, hideIcon);
		addPasswordFieldWithLabel("CONFIRM NEW PASSWORD:", 150, lowerPanel, showIcon, hideIcon);

		JButton saveButton = new JButton("SAVE");
		styleButton(saveButton);
		saveButton.setBounds(125, 230, 100, 35);
		saveButton.setBackground(Color.WHITE);
		saveButton.setForeground(Color.DARK_GRAY);
		saveButton.addActionListener(e -> {
			JOptionPane.showMessageDialog(passFrame, "Password Updated Successfully!");
			passFrame.dispose();
		});
		lowerPanel.add(saveButton);

		JButton cancelButton = new JButton("CANCEL");
		styleButton(cancelButton);
		cancelButton.setBounds(250, 230, 100, 35);
		cancelButton.setBackground(Color.WHITE);
		cancelButton.setForeground(Color.DARK_GRAY);
		cancelButton.addActionListener(e -> passFrame.dispose());
		lowerPanel.add(cancelButton);

		passFrame.setVisible(true);
	}

	private void addPasswordFieldWithLabel(String labelText, int y, JPanel panel, ImageIcon showIcon, ImageIcon hideIcon) {
		JLabel label = new JLabel(labelText);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(30, y, 300, 20);
		panel.add(label);

		JPasswordField field = new JPasswordField();
		field.setFont(new Font("Tahoma", Font.PLAIN, 14));
		field.setBounds(30, y + 25, 380, 30);
		field.setEchoChar('•');
		panel.add(field);

		JButton toggleBtn = new JButton(hideIcon);
		toggleBtn.setBounds(415, y + 25, 30, 30);
		toggleBtn.setOpaque(false);
		toggleBtn.setContentAreaFilled(false);
		toggleBtn.setBorderPainted(false);
		toggleBtn.setFocusPainted(false);
		toggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		toggleBtn.addActionListener(e -> {
			boolean visible = field.getEchoChar() == 0;
			field.setEchoChar(visible ? '•' : (char) 0);
			toggleBtn.setIcon(visible ? hideIcon : showIcon);
		});
		panel.add(toggleBtn);
	}

	private ImageIcon loadIcon(String path, int width, int height) {
		try {
			BufferedImage original = ImageIO.read(getClass().getResource(path));
			Image scaled = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage transparentImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = transparentImage.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.drawImage(scaled, 0, 0, null);
			g2d.dispose();
			return new ImageIcon(transparentImage);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
