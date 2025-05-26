package fr.univrouen.rss25SB.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.FlatLightLaf;

public class RssConverterSwingApp extends JFrame {

    private JComboBox<String> sourceComboBox;
    private JButton convertButton;
    private JTextArea outputArea;

    public RssConverterSwingApp() {
        setTitle("Convertisseur RSS → RSS25SB");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());


        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Choisissez une source :"));

        sourceComboBox = new JComboBox<>(RssConverterApp.SOURCES.keySet().toArray(new String[0]));
        topPanel.add(sourceComboBox);

        convertButton = new JButton("Convertir");
        topPanel.add(convertButton);

        panel.add(topPanel, BorderLayout.NORTH);


        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);


        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSource = (String) sourceComboBox.getSelectedItem();
                if (selectedSource != null) {
                    new Thread(() -> {
                        try {
                            String sourceUrl = RssConverterApp.SOURCES.get(selectedSource);
                            appendOutput("Téléchargement du flux depuis : " + sourceUrl);
                            RssConverterApp.convertRssToRss25(sourceUrl, selectedSource);
                            appendOutput("Conversion réussie pour la source : " + selectedSource + "\n");
                        } catch (Exception ex) {
                            appendOutput("Erreur : " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }).start();
                }
            }
        });
    }

    private void appendOutput(String message) {
        SwingUtilities.invokeLater(() -> {
            outputArea.append(message + "\n");
        });
    }

    public static void main(String[] args) {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Exception ex) {
                System.err.println("Failed to initialize LaF");
            }

            SwingUtilities.invokeLater(() -> {
                new RssConverterSwingApp().setVisible(true);
            });
        }
    }

