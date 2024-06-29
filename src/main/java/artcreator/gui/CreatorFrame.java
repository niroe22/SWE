package artcreator.gui;

import artcreator.creator.CreatorFactory;
import artcreator.creator.impl.ColorMatcher;
import artcreator.creator.port.Creator;
import artcreator.domain.DomainFactory;
import artcreator.domain.port.Domain;
import artcreator.domain.port.Profile;
import artcreator.domain.port.Template;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.State.S;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;

import java.util.HashMap;
import java.util.Map;

public class CreatorFrame extends JFrame implements Observer {

    private final Creator creator;
    private final Domain domain;

    private JButton ImageImportButton;
    private JButton vorlageDruckenButton;
    private JTextField templateLengthTextField;
    private JTextField templateWidthTextField;
    private JSlider granularitySlider;
    private JTextField granularityTextField;
    private JLabel settingsLabel;
    private JPanel creatorJFrame;
    private JPanel vorlagengrößePanel;
    private JPanel rastergröße;
    private JPanel InputPanel;
    private JPanel templatePenel;
    private JButton reset_btn;
    private JRadioButton radioButton0Grad;
    private JRadioButton radioButton90Grad;
    private JRadioButton radioButton180Grad;
    private JRadioButton radioButton270Grad;
    private JTextField markDiameterTextField;
    private JSlider markDiameterSlider;
    private JButton createTemplateButton;
    private JPanel materialListPanel;
    private JList materialList;
    private JCheckBox borderCheckBox;

    public CreatorFrame() {
        creator = CreatorFactory.FACTORY.creator();
        domain = DomainFactory.FACTORY.domain();
        StateMachineFactory.FACTORY.subject().attach(this);

        ImageImportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Bilddateien", "jpg", "jpeg", "png", "gif", "bmp");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);

                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    creator.setImage(fileChooser.getSelectedFile());
                    resetProfileToCurrentProfile();
                }

                // just for faster testing
//                creator.setImage(new File("C:\\Users\\besch\\Documents\\mario.png"));
//                resetProfileToCurrentProfile();
            }
        });

        vorlageDruckenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Template export not Implemented", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        reset_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Profile profile = new Profile();
                creator.updateProfile(profile);
            }
        });

        markDiameterSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                markDiameterTextField.setText(source.getValue() + "mm");
            }
        });

        granularitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                granularityTextField.setText(source.getValue() + "mm");
                granularityTextField.setText(source.getValue() + "mm");
                markDiameterSlider.setMaximum(source.getValue());
            }
        });

        templateLengthTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                String t = templateLengthTextField.getText();
                templateLengthTextField.setText(t.replaceAll("m", ""));
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                String t = templateLengthTextField.getText();
                templateLengthTextField.setText(t + "mm");
            }
        });

        templateWidthTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                String t = templateWidthTextField.getText();
                templateWidthTextField.setText(t.replaceAll("m", ""));
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                String t = templateWidthTextField.getText();
                templateWidthTextField.setText(t + "mm");
            }
        });

        templateLengthTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

        templateWidthTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

        createTemplateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int width = Integer.parseInt(templateWidthTextField.getText().replaceAll("m", ""));
                int length = Integer.parseInt(templateLengthTextField.getText().replaceAll("m", ""));
                int granularity = granularitySlider.getValue();
                int markDiameter = markDiameterSlider.getValue();
                int rotation = getSelectedRotation();

                Profile profile = new Profile(width, length, granularity, rotation, markDiameter);
                creator.updateProfile(profile);
            }
        });

        templatePenel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                render(templatePenel.getGraphics());
            }
        });
        borderCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                render(templatePenel.getGraphics());
            }
        });
    }

    public static void main(String[] args) {
        CreatorFrame form = new CreatorFrame();
        form.setContentPane(form.creatorJFrame);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setSize(1000, 800);
        form.setTitle("Pinanalizer");
        form.materialListPanel.setVisible(false);
        form.setVisible(true);


    }

    private void resetProfileToCurrentProfile() {
        Profile profile = domain.getProfile();
        markDiameterSlider.setValue(profile.getMarkDiameter());
        granularitySlider.setValue(profile.getGranularity());
        templateLengthTextField.setText(profile.getLength() + "mm");
        templateWidthTextField.setText(profile.getWidth() + "mm");
        switch (profile.getRotation()) {
            case 0:
                radioButton0Grad.setSelected(true);
                break;
            case 90:
                radioButton90Grad.setSelected(true);
                break;
            case 180:
                radioButton180Grad.setSelected(true);
                break;
            case 270:
                radioButton270Grad.setSelected(true);
                break;
        }
    }

    public void update(State newState) {
        boolean canChangeProfile = !newState.isSubStateOf(S.NO_IMAGE_LOADED);
        markDiameterSlider.setEnabled(canChangeProfile);
        granularitySlider.setEnabled(canChangeProfile);
        templateLengthTextField.setEnabled(canChangeProfile);
        templateWidthTextField.setEnabled(canChangeProfile);
        radioButton0Grad.setEnabled(canChangeProfile);
        radioButton90Grad.setEnabled(canChangeProfile);
        radioButton180Grad.setEnabled(canChangeProfile);
        radioButton270Grad.setEnabled(canChangeProfile);
        reset_btn.setEnabled(canChangeProfile);
        createTemplateButton.setEnabled(canChangeProfile);
        borderCheckBox.setEnabled(canChangeProfile);

        if (newState.isSubStateOf(S.PROFILE_UPDATED)) {
            resetProfileToCurrentProfile();
        } else if (newState.isSubStateOf(S.TEMPLATE_CREATED)) {
            HashMap<Color, Integer> colorCountList = domain.getTemplate().getColorCountList();
            DefaultListModel<String> model = (DefaultListModel<String>) materialList.getModel();
            model.clear();

            int marksCount = 0;

            for (Map.Entry<Color, Integer> color : colorCountList.entrySet()) {
                model.addElement(ColorMatcher.COLORS.get(color.getKey()) + ": " + color.getValue());
                marksCount += color.getValue();
            }

            model.addElement("Total Marks: " + marksCount);
            materialListPanel.setVisible(true);


            this.render(templatePenel.getGraphics());
        }
    }

    private int getSelectedRotation() {
        if (radioButton0Grad.isSelected()) {
            return 0;
        } else if (radioButton90Grad.isSelected()) {
            return 90;
        } else if (radioButton180Grad.isSelected()) {
            return 180;
        }
        return 270;
    }

    private void render(Graphics g) {
        g.setColor(new Color(253, 245, 230));
        g.fillRect(0, 0, templatePenel.getWidth(), templatePenel.getHeight());

        Template template = domain.getTemplate();
        Profile profile = domain.getProfile();

        if (template != null) {


            Color[][] grid = template.getGrid();
            int[] ratioPanel = calculateRatioPanel();
            double ratio = (double) ratioPanel[0] / (double) profile.getWidth();

            double panelOffsetX = ((double) templatePenel.getWidth() - (double) ratioPanel[0]) / 2.d;
            double panelOffsetY = ((double) templatePenel.getHeight() - (double) ratioPanel[1]) / 2.d;

            g.setColor(Color.white);
            g.fillRect((int) panelOffsetX, (int) panelOffsetY, ratioPanel[0], ratioPanel[1]);


            double gridSize = (double) profile.getGranularity() * ratio;

            double markGridOffset = (gridSize - (double) profile.getMarkDiameter() * ratio) / 2.d;

            double gridOffsetX =  ((double) ratioPanel[0] -  gridSize * (double) grid.length) / 2.d;
            double gridOffsetY = ((double) ratioPanel[1] - gridSize * (double) grid[0].length) / 2.d;

            double sumOffsetX = markGridOffset + panelOffsetX + gridOffsetX;
            double sumOffsetY = markGridOffset + panelOffsetY + gridOffsetY;

            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[x].length; y++) {
                    int xPos = (int) (x * gridSize + sumOffsetX);
                    int yPos = (int) (y * gridSize + sumOffsetY);
                    int width = (int) ((double) profile.getMarkDiameter() * ratio);
                    int height = (int) ((double) profile.getMarkDiameter() * ratio);

                    if (xPos >= panelOffsetX  && xPos + width <= panelOffsetX + ratioPanel[0] &&
                            yPos >= panelOffsetY && yPos + height <= panelOffsetY + ratioPanel[1]) {
                        g.setColor(grid[x][y]);
                        g.fillOval(xPos, yPos, width, height);
                        g.setColor(Color.BLACK);
                        if (borderCheckBox.isSelected()) {
                            g.drawOval(xPos, yPos, width - 1, height - 1);
                        }
                    }
                }
            }

            g.setColor(Color.BLACK);
            g.drawRect((int) panelOffsetX, (int) panelOffsetY, ratioPanel[0] - 1, ratioPanel[1] - 1);

        }

    }

    protected int[] calculateRatioPanel() {

        int originalWidth = domain.getProfile().getWidth();
        int originalHeight = domain.getProfile().getLength();
        ;

        int panelWidth = templatePenel.getWidth();
        int panelHeight = templatePenel.getHeight();

        double aspectRatio = (double) originalWidth / originalHeight;

        int width, height;
        if (panelWidth / aspectRatio <= panelHeight) {
            width = panelWidth;
            height = (int) (panelWidth / aspectRatio);
        } else {
            height = panelHeight;
            width = (int) (panelHeight * aspectRatio);
        }

        return new int[]{width, height};
    }

    private void createUIComponents() {
        DefaultListModel<String> model = new DefaultListModel<>();
        materialList = new JList<>(model);
    }
}

