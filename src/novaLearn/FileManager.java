package novaLearn;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FileManager {
    private static final String ATTACHMENT_BASE_PATH = "attachments/";

    public static void openAttachment(Component parent, String filename) {
        if (filename == null || filename.isBlank()) {
            JOptionPane.showMessageDialog(parent, "No attachment available.");
            return;
        }

        File file = new File(ATTACHMENT_BASE_PATH + filename);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(parent, "File not found: " + filename);
            return;
        }

        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent, "Unable to open file.");
        }
    }
}
