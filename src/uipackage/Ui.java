package uipackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Ui {
    private UiHandler handler;
    private JPanel contentPanel;
    private JPanel generalSettingsPanel;
    private JPanel graphPanel;
    private JPanel graphActionsPanel;
    private JPanel groupSettingsPanel;
    private JPanel graphArea;
    private JScrollPane generalSettingsScrollPane;
    private JScrollPane groupSettingsScrollPane;
    private JScrollPane graphActionsScrollPane;
    private JPanel generalSettingsButtonPanel;
    private JPanel graphActionsButtonPanel;
    private JPanel groupSettingsButtonPanel;

    private JButton addPersonDataButton;
    private JButton editPersonDataButton;
    private JButton addBondDataButton;
    private JButton editBondDataButton;
    private JButton addGroupDataButton;
    private JButton editGroupDataButton;
    private JButton setSaveLocationButton;
    private JButton getSaveLocationButton;

    private JButton changeCurrentGroupButton;
    private JButton addPersonToCurrentGroupButton;
    private JButton removePersonFromCurrentGroupButton;
    private JButton raiseBondFromCurrentGroupButton;
    private JButton lowerBondFromCurrentGroupButton;
    private JButton raiseAllBondsFromCurrentGroupButton;
    private JButton lowerAllBondsFromCurrentGroup;

    private JButton generatePartitionsBasedOnCurrentGroupButton;
    private JButton getMaxDirectCentralityPersonButton;
    private JButton getMaxIndirectCentralityPersonButton;
    private JButton getMinDirectCentralityPersonButton;
    private JButton getMinIndirectCentralityPersonButton;
    private JButton getClusteringButton;
    private JButton getDjikstraButton;
    private JButton getClosenessCentralityButton;
    private JButton getKCoreDecompositionButton;

    public Ui(UiHandler handler){
        this.handler = handler;
        setupPanels();
        setupButtons();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("TeamBuilder");
            frame.setBackground(new Color(0x2B2C30));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(this.contentPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }

    private void setupPanels()
    {
        generalSettingsButtonPanel.setBackground(new Color(0x2B2C30));
        graphActionsButtonPanel.setBackground(new Color(0x2B2C30));
        groupSettingsButtonPanel.setBackground(new Color(0x2B2C30));
    }

    private void setupButtons()
    {
        setButtonStyle(addPersonDataButton);
        addPersonDataButton.addActionListener(e -> addPersonUi());
        setButtonStyle(editPersonDataButton);
        setButtonStyle(addBondDataButton);
        setButtonStyle(editBondDataButton);
        setButtonStyle(addGroupDataButton);
        setButtonStyle(editGroupDataButton);
        setButtonStyle(setSaveLocationButton);
        setButtonStyle(getSaveLocationButton);

        setButtonStyle(changeCurrentGroupButton);
        setButtonStyle(addPersonToCurrentGroupButton);
        setButtonStyle(removePersonFromCurrentGroupButton);
        setButtonStyle(raiseBondFromCurrentGroupButton);
        setButtonStyle(lowerBondFromCurrentGroupButton);
        setButtonStyle(raiseAllBondsFromCurrentGroupButton);
        setButtonStyle(lowerAllBondsFromCurrentGroup);

        setButtonStyle(generatePartitionsBasedOnCurrentGroupButton);
        setButtonStyle(getMaxDirectCentralityPersonButton);
        setButtonStyle(getMaxIndirectCentralityPersonButton);
        setButtonStyle(getMinDirectCentralityPersonButton);
        setButtonStyle(getMinIndirectCentralityPersonButton);
        setButtonStyle(getClusteringButton);
        setButtonStyle(getDjikstraButton);
        setButtonStyle(getClosenessCentralityButton);
        setButtonStyle(getKCoreDecompositionButton);
    }
    private void setButtonStyle(JButton b)
    {
        b.setBackground(new Color(0x2B2C30));
        b.setForeground(new Color(223, 225, 229));
        b.setFocusPainted(false);
    }

    private void addPersonUi()
    {
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(0,0));
        dialog.setSize(500,300);
        dialog.setLocationRelativeTo(null);

        /// LEFT: Image Selection Panel
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(180, 180));
        imagePanel.setBackground(new Color(0x2B2C30));
        imagePanel.setForeground(new Color(255,255,255));
        imagePanel.setBorder(BorderFactory.createTitledBorder( null,"Selected image",0,0,null,new Color(255,255,255)));
        JLabel imageLabel = new JLabel("No Image", SwingConstants.CENTER);
        imageLabel.setForeground(new Color(255,255,255));
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        /// Allows the user to pop-up the File-selection pannel
        final BufferedImage[] selectedImage = new BufferedImage[1];
        JButton selectImageButton = new JButton("Select Image");
        setButtonStyle(selectImageButton);
        selectImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(dialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    selectedImage[0] = ImageIO.read(file);
                    Image scaled = selectedImage[0].getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaled));
                    imageLabel.setText(null);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error loading image: " + ex.getMessage());
                }
            }
        });
        imagePanel.add(selectImageButton, BorderLayout.SOUTH);

        // CENTER: Form Fields
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(new Color(0x2B2C30));

        JTextField nameField = new JTextField();
        JLabel textNameInput = new JLabel("Name:");
        textNameInput.setForeground(new Color(255,255,255));
        formPanel.add(textNameInput);
        formPanel.add(nameField);

        JTextField notesField = new JTextField();
        JLabel textNotesInput = new JLabel("Initial notes:");
        textNotesInput.setForeground(new Color(255,255,255));
        formPanel.add(textNotesInput);
        formPanel.add(notesField);

        // SOUTH : Buttons Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(0x2B2C30));
        JButton cancelButton = new JButton("Cancel");
        setButtonStyle(cancelButton);
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        JButton saveButton = new JButton("Save");
        setButtonStyle(saveButton);
        saveButton.addActionListener(e -> {
            try {
                handler.handleAddPersonRequest(selectedImage[0],nameField.getText(),notesField.getText());
            }
            catch (Exception error)
            {
                JOptionPane.showMessageDialog(null,"An error occured :" + error.getMessage());
            }
            dialog.dispose();
        });

        bottomPanel.add(cancelButton);
        bottomPanel.add(saveButton);

        // Display the panels on the frame
        dialog.add(imagePanel, BorderLayout.WEST);
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
        return;
    }



    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.graphArea = new Graph();
    }
}
