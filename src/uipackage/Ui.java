package uipackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

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
    private enum OPERAITON_TYPE { PERSON, BOND, GROUP}

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
        editPersonDataButton.addActionListener(e -> {
            Integer id = showList(handler.handleGetPersonList(),"Here's who you know?");
            editPersonUi(id);
        });
        setButtonStyle(addBondDataButton);
        addBondDataButton.addActionListener(e -> addBondUi());
        setButtonStyle(editBondDataButton);
        editBondDataButton.addActionListener(e -> {
            Integer id = showList(handler.handleGetBondList(),"Here's who knows who:");
            editBondUi(id);
        });
        setButtonStyle(addGroupDataButton);

        setButtonStyle(editGroupDataButton);
        editGroupDataButton.addActionListener(e -> {
            Integer id = showList(handler.handleGetGroupList(),"Here's who you're taking care of");
            editGroupUi(id);
        });
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

    private void addPersonUi() {
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(0,0));
        dialog.setSize(500,300);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Who did you meet?");
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

    private void addBondUi(){
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(0,0));
        dialog.setSize(500,300);
        dialog.setBackground(new Color(0x2B2C30));
        dialog.setForeground(new Color(255,255,255));
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Who met who?");
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(0x2B2C30));
        HashMap<Integer,String> peopleList = handler.handleGetPersonList();

        JPanel idSelect = new JPanel();
        idSelect.setBackground(new Color(0x2B2C30));
        idSelect.setForeground(new Color(255,255,255));
        JComboBox<String> dropdown1 = new JComboBox<>(peopleList.values().toArray(new String[peopleList.size()]));
        Font largeFont = new Font(dropdown1.getFont().getName(), Font.PLAIN, 20);
        dropdown1.setFont(largeFont);
        JComboBox<String> dropdown2 = new JComboBox<>(peopleList.values().toArray(new String[peopleList.size()]));
        dropdown2.setFont(largeFont);
        JLabel metText = new JLabel("met");
        metText.setFont(largeFont);
        metText.setForeground(new Color(255,255,255));
        idSelect.add(dropdown1);
        idSelect.add(metText);
        idSelect.add(dropdown2);

        JPanel ratingSelect = new JPanel();
        ratingSelect.setBackground(new Color(0x2B2C30));
        ratingSelect.setForeground(new Color(255,255,255));
        ratingSelect.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        JLabel ratingIntroWriting = new JLabel("Initial bond rating (out of 10):");
        ratingIntroWriting.setFont(largeFont);
        ratingIntroWriting.setForeground(new Color(255,255,255));
        JComboBox<Integer> dropdown3 = new JComboBox<>();
        dropdown3.setFont(largeFont);
        for(int i = 1; i<=10; i++)
        {
            dropdown3.addItem(i);
        };
        ratingSelect.add(ratingIntroWriting);
        ratingSelect.add(dropdown3);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(0x2B2C30));
        buttonsPanel.setForeground(new Color(255,255,255));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener( e -> {
            dialog.dispose();
        });
        setButtonStyle(cancelButton);
        cancelButton.setFont(largeFont);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                String name1 = (String) dropdown1.getSelectedItem();
                String name2 = (String) dropdown2.getSelectedItem();

                // Get corresponding IDs by searching the HashMap
                Integer id1 = null;
                Integer id2 = null;
                for (HashMap.Entry<Integer, String> entry : peopleList.entrySet()) {
                    if (entry.getValue().equals(name1)) id1 = entry.getKey();
                    if (entry.getValue().equals(name2)) id2 = entry.getKey();
                }

                if (id1 == null || id2 == null) {
                    throw new Exception("Could not find IDs for selected people.");
                }
                if (id1 == id2)
                {
                    throw new Exception("People already know themselves.");
                }

                Integer rating = (Integer) dropdown3.getSelectedItem();
                handler.handleAddBondRequest(id1,id2,rating);
                dialog.dispose();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(dialog, "Error saving bond: " + ex.getMessage());
            }
        });
        setButtonStyle(saveButton);
        saveButton.setFont(largeFont);

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(saveButton);

        container.add(Box.createVerticalGlue());
        container.add(idSelect);
        container.add(Box.createVerticalStrut(10));
        container.add(ratingSelect);
        container.add(Box.createVerticalStrut(10));
        container.add(buttonsPanel);
        container.add(Box.createVerticalGlue());
        dialog.add(container);
        dialog.setVisible(true);
    }

    private void editPersonUi(int id) {
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(0,0));
        dialog.setSize(500,300);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("So what's new about them?");
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
        selectedImage[0] = handler.handleGetPersonImage(id);
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
        nameField.setText(handler.handleGetPersonName(id));
        formPanel.add(textNameInput);
        formPanel.add(nameField);

        JTextField notesField = new JTextField();
        JLabel textNotesInput = new JLabel("Notes:");
        textNotesInput.setForeground(new Color(255,255,255));
        notesField.setText(handler.handleGetPersonNotes(id));
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
                handler.handleEditPersonRequest(id,selectedImage[0],nameField.getText(),notesField.getText());
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

    private void editBondUi(int id)
    {
        System.out.println("Edit Bond id " + id);
    }

    private void editGroupUi(int id)
    {
        System.out.println("Edit group id " + id);
    }

    private Integer showList(HashMap <Integer, String > dataList, String titleText)
    {
        JDialog dialog = new JDialog((Frame) null, titleText, true);
        dialog.setLayout(new BorderLayout(0,0));
        dialog.setSize(800,500);
        dialog.setLocationRelativeTo(null);
        dialog.setBackground(new Color(0x2B2C30));
        dialog.setForeground(new Color(255,255,255));
        DefaultListModel<String> stringList = new DefaultListModel<>();

        for(String name : dataList.values())
        {
            stringList.addElement(name);
        }

        JList<String> displayList = new JList<>(stringList);
        displayList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value); // 'value' is the actual String (not 'project' or 'value.getName()')
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            Font labelFont = label.getFont();
            int fontSize = 50;
            label.setFont(new Font(labelFont.getName(), Font.PLAIN,fontSize));
            label.setOpaque(true);

            if (isSelected) {
                label.setBackground(new Color(0x3E3F44));
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(new Color(0x2B2C30));
                label.setForeground(Color.LIGHT_GRAY);
            }
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            return label;
        });
        Integer[] targetId = {null};
        displayList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = displayList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        String selectedValue = displayList.getModel().getElementAt(index);

                        for (HashMap.Entry<Integer, String> entry : dataList.entrySet()) {
                            if (entry.getValue().equals(selectedValue)) {
                                targetId[0] = entry.getKey();
                                break;
                            }
                        }
                        dialog.dispose();
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(displayList);
        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.setVisible(true);
        return targetId[0];
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.graphArea = new Graph();
    }
}
