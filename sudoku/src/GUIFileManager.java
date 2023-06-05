import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GUIFileManager extends JFrame {

    private JTextArea fileExplorer;
    private JFileChooser fileChooser;

    public GUIFileManager() {
        setTitle("Файловый менеджер");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        fileExplorer = new JTextArea();
        fileExplorer.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(fileExplorer);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton moveButton = new JButton("Переместить");
        moveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveFile();
            }
        });
        buttonPanel.add(moveButton);

        JButton renameButton = new JButton("Переименовать");
        renameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renameFile();
            }
        });
        buttonPanel.add(renameButton);

        JButton createButton = new JButton("create txt file");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createTextFile();
            }
        });
        buttonPanel.add(createButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void moveFile() {
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Переместить файл");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSize(1000,1000);

        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fileToMove = fileChooser.getSelectedFile();

            fileChooser.setDialogTitle("Выбери папку назначения");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File destinationFolder = fileChooser.getSelectedFile();

                File newFile = new File(destinationFolder, fileToMove.getName());
                if (fileToMove.renameTo(newFile)) {
                    fileExplorer.append("Перемещенный файл: " + fileToMove.getName() + "\n");
                } else {
                    fileExplorer.append("Неудалось переместить файл: " + fileToMove.getName() + "\n");
                }
            }
        }
    }

    private void renameFile() {
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Переименовать файл");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSize(1000,1000);

        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fileToRename = fileChooser.getSelectedFile();

            String newName = JOptionPane.showInputDialog(this, "Введи новое имя:");
            if (newName != null) {
                File newFile = new File(fileToRename.getParent(), newName);
                if (fileToRename.renameTo(newFile)) {
                    fileExplorer.append("Переименованный файл: " + fileToRename.getName() + " на " + newName + "\n");
                } else {
                    fileExplorer.append("Неудалось переименовать: " + fileToRename.getName() + "\n");
                }
            }
        }
    }

    private void createTextFile() {
        String fileName = JOptionPane.showInputDialog(this, "Введите имя файла:");
        if (fileName != null) {
            String fileContent = JOptionPane.showInputDialog(this, "Содержимое файла:");
            if (fileContent != null) {
                try {
                    File file = new File(fileName + ".txt");
                    if (file.createNewFile()) {

                        FileWriter writer = new FileWriter(file);
                        writer.write(fileContent);
                        writer.close();
                        fileExplorer.append("Созданный файл: " + file.getName() + "\n");
                    } else {
                        fileExplorer.append("Неудалось создать файл: " + file.getName() + "\n");
                    }
                } catch (IOException e) {
                    fileExplorer.append("Ошибка в создании файла: " + e.getMessage() + "\n");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUIFileManager fileManager = new GUIFileManager();
                fileManager.setVisible(true);
            }
        });
    }
}