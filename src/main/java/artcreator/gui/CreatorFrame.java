package artcreator.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class CreatorFrame extends JFrame {
    private JButton ImageImportButton;
    private JButton vorlageDruckenButton;
    private JTextField a210mmTextField;
    private JTextField a297mmTextField;
    private JSlider gridSizeSlider;
    private JTextField gridSizetextField;
    private JLabel settingsLabel;
    private JPanel creatorJFrame;
    private JPanel vorlagengrößePanel;
    private JPanel rastergröße;
    private JPanel InputPanel;
    private JPanel TemplatePenel;
    private JButton reset_btn;
    private JRadioButton radioButton0Grad;
    private JRadioButton radioButton45Grad;
    private JRadioButton radioButton180Grad;
    private JRadioButton radioButton270Grad;
    private JButton createButton;
    private JTextField markSizeTextField;
    private JSlider markSizeSlider;

    public CreatorFrame() {
        ImageImportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                // Setze den Dateiauswahlmodus (Dateien oder Verzeichnisse)
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                // Zeige den Dialog zum Öffnen einer Datei
                int result = fileChooser.showOpenDialog(null);

                // Überprüfe, ob der Benutzer eine Datei ausgewählt hat
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(null, "Selected file: " + selectedFile.getAbsolutePath());
                }
            }
        });

        vorlageDruckenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("vorlageDruckenButton");

            }
        });
        reset_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("reset_btn");

            }
        });
        markSizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                markSizeTextField.setText(String.valueOf(source.getValue()) + "mm");
            }
        });
        gridSizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                gridSizetextField.setText(String.valueOf(source.getValue()) + "mm");
                markSizeSlider.setMaximum(source.getValue());
            }
        });
    }

    public static void main(String[] args) {
        CreatorFrame form = new CreatorFrame();
        form.setContentPane(form.creatorJFrame);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setSize(1000, 800);
        form.setVisible(true);
    }

}

