package fr.univrouen.rss25SB.client;

import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Rss25UploaderApp extends JFrame {
    private JTextField urlField;
    private JTextField fileField;
    private JTextArea responseArea;
    private File selectedFile;

    public Rss25UploaderApp() {
        setTitle("RSS25SB Uploader");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new GridLayout(3, 1));


        JPanel urlPanel = new JPanel(new BorderLayout());
        urlPanel.add(new JLabel("URL du service REST : "), BorderLayout.WEST);
        urlField = new JTextField("http://localhost:8080/rss25SB/insert");
        urlPanel.add(urlField, BorderLayout.CENTER);
        topPanel.add(urlPanel);


        JPanel filePanel = new JPanel(new BorderLayout());
        filePanel.add(new JLabel("Fichier XML : "), BorderLayout.WEST);
        fileField = new JTextField();
        fileField.setEditable(false);
        JButton chooseButton = new JButton("Parcourir...");
        chooseButton.addActionListener(e -> chooseFile());
        filePanel.add(fileField, BorderLayout.CENTER);
        filePanel.add(chooseButton, BorderLayout.EAST);
        topPanel.add(filePanel);


        JButton sendButton = new JButton("Envoyer le flux");
        sendButton.addActionListener(e -> sendXml());
        topPanel.add(sendButton);

        add(topPanel, BorderLayout.NORTH);


        responseArea = new JTextArea();
        responseArea.setEditable(false);
        add(new JScrollPane(responseArea), BorderLayout.CENTER);
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            fileField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void sendXml() {
        if (selectedFile == null || !selectedFile.exists()) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un fichier XML valide.");
            return;
        }

        String urlString = urlField.getText();
        try {

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setRequestProperty("Accept", "application/xml");


            try (OutputStream os = conn.getOutputStream();
                 FileInputStream fis = new FileInputStream(selectedFile)) {
                fis.transferTo(os);
            }


            int status = conn.getResponseCode();
            InputStream is = (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder responseText = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseText.append(line).append("\n");
            }

            responseArea.setText("Réponse (" + status + "):\n" + responseText.toString());

        } catch (IOException e) {
            responseArea.setText("Erreur lors de l'envoi :\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        SwingUtilities.invokeLater(() -> {
            new Rss25UploaderApp().setVisible(true);
        });
    }
}



