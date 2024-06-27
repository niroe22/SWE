package artcreator.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatorFrame extends JFrame {
    private JButton button1;
    private JButton button2;
    private JTextField textField1;
    private JTextField textField2;
    private JSlider slider1;
    private JTextField textField3;
    private JLabel settingsLabel;
    private JPanel creatorJFrame;
    private JPanel vorlagengrößePanel;
    private JPanel rastergröße;
    private JPanel InputPanel;
    private JPanel TemplatePenel;
    private JButton reset_btn;
    private JRadioButton sdfRadioButton;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;

    public CreatorFrame() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        CreatorFrame form = new CreatorFrame();
        form.setContentPane(form.creatorJFrame);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setSize(2000, 800);
        form.setVisible(true);
    }

}

