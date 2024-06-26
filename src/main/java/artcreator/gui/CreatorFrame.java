package artcreator.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatorFrame extends JFrame{
    private JButton button1;
    private JButton button2;
    private JTextField textField1;
    private JTextField textField2;
    private JSlider slider1;
    private JTextField textField3;
    private JLabel settingsLabel;
    private JPanel creatorJFrame;

    public CreatorFrame() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        CreatorFrame cf = new CreatorFrame();
        cf.setContentPane();
    }

