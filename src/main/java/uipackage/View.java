package main.java.uipackage;

import main.java.graphpackage.GraphArea;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class View {
    private final Color BG_COLOR = new Color(0x2B2C30);
    private Controller handler;
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
    private JButton changeAccountButton;

    private JButton changeCurrentGroupButton;
    private JButton addPersonToCurrentGroupButton;
    private JButton removePersonFromCurrentGroupButton;
    private JButton raiseBondFromCurrentGroupButton;
    private JButton lowerBondFromCurrentGroupButton;
    private JButton raiseAllBondsFromCurrentGroupButton;
    private JButton lowerAllBondsFromCurrentGroupButton;

    private JButton generatePartitionsBasedOnCurrentGroupButton;
    private JButton getMaxDirectCentralityPersonButton;
    private JButton getMaxIndirectCentralityPersonButton;
    private JButton getMinDirectCentralityPersonButton;
    private JButton getMinIndirectCentralityPersonButton;
    private JButton getClusteringButton;
    private JButton getDijkstraButton;
    private JButton getClosenessCentralityButton;
    private JButton getKCoreDecompositionButton;

    public View(Controller handler) {
        this.handler = handler;
        setupPanels();
        setupButtons();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("TeamBuilder");
            frame.setBackground(BG_COLOR);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setContentPane(this.contentPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }

    private void setupPanels() {
        generalSettingsButtonPanel.setBackground(BG_COLOR);
        graphActionsButtonPanel.setBackground(BG_COLOR);
        groupSettingsButtonPanel.setBackground(BG_COLOR);
    }

    private void setupButtons() {
        setButtonStyle(addPersonDataButton);
        addPersonDataButton.addActionListener(e -> addPersonUi());
        setButtonStyle(editPersonDataButton);
        editPersonDataButton.addActionListener(e -> {
            Integer id = showList(handler.handleGetPersonList(), "Here's who you know:", true);
            if (id != null) {
                editPersonUi(id);
            }
        });
        setButtonStyle(addBondDataButton);
        addBondDataButton.addActionListener(e -> addBondUi());
        setButtonStyle(editBondDataButton);
        editBondDataButton.addActionListener(e -> {
            Integer id = showList(handler.handleGetBondList(), "Here's who knows who:", true);
            if (id != null) {
                editBondUi(id);
            }
        });
        setButtonStyle(addGroupDataButton);
        addGroupDataButton.addActionListener(e -> {
            addGroupUi();
        });
        setButtonStyle(editGroupDataButton);
        editGroupDataButton.addActionListener(e -> {
            Integer id = showList(handler.handleGetGroupList(), "Here's who you're taking care of", true);
            if (id != null) {
                editGroupUi(id);
            }
        });

        setButtonStyle(changeAccountButton);
        changeAccountButton.addActionListener(e -> {
            changeAccountButtonUi();
        });



        setButtonStyle(changeCurrentGroupButton);
        changeCurrentGroupButton.addActionListener(e -> {
            Integer selectedGroupId = showList(handler.handleGetGroupList(), "Who are we working with now?", true);
            if (selectedGroupId != null) {
                changeCurrentGroupUi(selectedGroupId);
            }
        });

        setButtonStyle(addPersonToCurrentGroupButton);
        addPersonToCurrentGroupButton.addActionListener(e -> {
            Integer selectedPersonId = showList(handler.handleGetPeopleNotInCurrentGroup(), "Who joined the crew?", true);
            if (selectedPersonId != null) {
                addPersonToCurrentGroupUi(selectedPersonId);
            }
        });

        setButtonStyle(removePersonFromCurrentGroupButton);
        removePersonFromCurrentGroupButton.addActionListener(e -> {
            Integer selectedPersonId = showList(handler.handleGetPeopleInCurrentGroup(), "Who left the crew?", true);
            if (selectedPersonId != null) {
                removePersonFromCurrentGroupUi(selectedPersonId);
            }
        });

        setButtonStyle(raiseBondFromCurrentGroupButton);
        raiseBondFromCurrentGroupButton.addActionListener(e -> {
            Integer selectedBondId = showList(handler.handleGetBondsInCurrentGroup(), "Which bond is getting stronger?", true);
            if (selectedBondId != null) {
                raiseBondFromCurrentGroupUi(selectedBondId);
            }
        });
        setButtonStyle(lowerBondFromCurrentGroupButton);
        lowerBondFromCurrentGroupButton.addActionListener(e -> {
            Integer selectedBondId = showList(handler.handleGetBondsInCurrentGroup(), "Which bond is weakening?", true);
            if (selectedBondId != null) {
                lowerBondFromCurrentGroupUi(selectedBondId);
            }
        });
        setButtonStyle(raiseAllBondsFromCurrentGroupButton);
        raiseAllBondsFromCurrentGroupButton.addActionListener(e -> {
            raiseAllBondsFromCurrentGroupUi();
        });
        setButtonStyle(lowerAllBondsFromCurrentGroupButton);
        lowerAllBondsFromCurrentGroupButton.addActionListener(e -> {
            lowerAllBondsFromCurrentGroupUi();
        });

        setButtonStyle(generatePartitionsBasedOnCurrentGroupButton);
        generatePartitionsBasedOnCurrentGroupButton.addActionListener(e -> {
            generatePartitionsUi();
        });
        setButtonStyle(getMaxDirectCentralityPersonButton);
        getMaxDirectCentralityPersonButton.addActionListener(e -> {
            getMaxDirectCentralityUi();
        });
        setButtonStyle(getMaxIndirectCentralityPersonButton);
        getMaxIndirectCentralityPersonButton.addActionListener(e -> {
            getMaxIndirectCentralityUi();
        });
        setButtonStyle(getMinDirectCentralityPersonButton);
        getMinDirectCentralityPersonButton.addActionListener(e -> {
            getMinDirectCentralityUi();
        });
        setButtonStyle(getMinIndirectCentralityPersonButton);
        getMinIndirectCentralityPersonButton.addActionListener(e -> {
            getMinIndirectCentralityUi();
        });
        setButtonStyle(getClusteringButton);
        getClusteringButton.addActionListener(e -> {
            getClusteringUi();
        });

        setButtonStyle(getDijkstraButton);
        getDijkstraButton.addActionListener(e -> {
            getDijkstraUi();
        });

        setButtonStyle(getClosenessCentralityButton);
        getClosenessCentralityButton.addActionListener(e -> {
            getClosenessCentralityUi();
        });
        setButtonStyle(getKCoreDecompositionButton);
        getKCoreDecompositionButton.addActionListener(e -> {
            getKCoreDecompositionUi();
        });
    }

    private void setButtonStyle(JButton b) {
        b.setBackground(BG_COLOR);
        b.setForeground(new Color(255, 255, 255));
        b.setFocusPainted(false);
    }

    private void addPersonUi() {
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(0, 0));
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Who did you meet?");
        /// LEFT: Image Selection Panel
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(180, 180));
        imagePanel.setBackground(BG_COLOR);
        imagePanel.setForeground(new Color(255, 255, 255));
        imagePanel.setBorder(BorderFactory.createTitledBorder(null, "Selected image", 0, 0, null, new Color(255, 255, 255)));
        JLabel imageLabel = new JLabel("No Image", SwingConstants.CENTER);
        imageLabel.setForeground(new Color(255, 255, 255));
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        /// Allows the user to pop up the File-selection panel
        BufferedImage[] selectedImage = new BufferedImage[1];
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
        formPanel.setBackground(BG_COLOR);

        JTextField nameField = new JTextField();
        JLabel textNameInput = new JLabel("Name:");
        textNameInput.setForeground(new Color(255, 255, 255));
        formPanel.add(textNameInput);
        formPanel.add(nameField);

        JTextField notesField = new JTextField();
        JLabel textNotesInput = new JLabel("Initial notes:");
        textNotesInput.setForeground(new Color(255, 255, 255));
        formPanel.add(textNotesInput);
        formPanel.add(notesField);

        // SOUTH : Buttons Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(BG_COLOR);
        JButton cancelButton = new JButton("Cancel");
        setButtonStyle(cancelButton);
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        JButton saveButton = new JButton("Save");
        setButtonStyle(saveButton);
        saveButton.addActionListener(e -> {
            try {
                handler.handleAddPersonRequest(selectedImage[0], nameField.getText(), notesField.getText());
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "An error occurred :" + error.getMessage());
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

    private void addBondUi() {
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(0, 0));
        dialog.setSize(500, 300);
        dialog.setBackground(BG_COLOR);
        dialog.setForeground(new Color(255, 255, 255));
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Who met who?");
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(BG_COLOR);
        HashMap<Integer, String> peopleList = handler.handleGetPersonList();

        JPanel idSelect = new JPanel();
        idSelect.setBackground(BG_COLOR);
        idSelect.setForeground(new Color(255, 255, 255));
        JComboBox<String> dropdown1 = new JComboBox<>(peopleList.values().toArray(new String[peopleList.size()]));
        Font largeFont = new Font(dropdown1.getFont().getName(), Font.PLAIN, 20);
        dropdown1.setFont(largeFont);
        JComboBox<String> dropdown2 = new JComboBox<>(peopleList.values().toArray(new String[peopleList.size()]));
        dropdown2.setFont(largeFont);
        JLabel metText = new JLabel("met");
        metText.setFont(largeFont);
        metText.setForeground(new Color(255, 255, 255));
        idSelect.add(dropdown1);
        idSelect.add(metText);
        idSelect.add(dropdown2);

        JPanel ratingSelect = new JPanel();
        ratingSelect.setBackground(BG_COLOR);
        ratingSelect.setForeground(new Color(255, 255, 255));
        ratingSelect.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JLabel ratingIntroWriting = new JLabel("Initial bond rating (out of 10):");
        ratingIntroWriting.setFont(largeFont);
        ratingIntroWriting.setForeground(new Color(255, 255, 255));
        JComboBox<Integer> dropdown3 = new JComboBox<>();
        dropdown3.setFont(largeFont);
        for (int i = 1; i <= 10; i++) {
            dropdown3.addItem(i);
        }
        ;
        ratingSelect.add(ratingIntroWriting);
        ratingSelect.add(dropdown3);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(BG_COLOR);
        buttonsPanel.setForeground(new Color(255, 255, 255));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
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
                if (id1 == id2) {
                    throw new Exception("People already know themselves.");
                }

                Integer rating = (Integer) dropdown3.getSelectedItem();
                handler.handleAddBondRequest(id1, id2, rating);
                graphPanel.repaint();
                graphArea.repaint();
                dialog.dispose();
            } catch (Exception ex) {
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

    private void addGroupUi() {
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(0, 0));
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("So who are we talking about");
        dialog.setBackground(BG_COLOR);

        // TOP: Title input
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(BG_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        Font largeFont = new Font("Dialog", Font.PLAIN, 20);
        JLabel titleLabel = new JLabel("Group name:");
        titleLabel.setFont(largeFont);
        titleLabel.setForeground(new Color(255, 255, 255));

        JTextField titleField = new JTextField();
        titleField.setFont(largeFont);
        titleField.setPreferredSize(new Dimension(0, 45)); // Height 45, width auto

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(titleField, BorderLayout.CENTER);

        // CENTER: Member selection
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel membersLabel = new JLabel("Who's in from the start?");
        membersLabel.setFont(largeFont);
        membersLabel.setForeground(new Color(255, 255, 255));
        centerPanel.add(membersLabel, BorderLayout.NORTH);

        // Get person list and create checkboxes
        HashMap<Integer, String> peopleList = handler.handleGetPersonList();
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        checkboxPanel.setBackground(BG_COLOR);

        HashMap<Integer, JCheckBox> checkboxMap = new HashMap<>();
        Font checkboxFont = new Font("Dialog", Font.PLAIN, 18);

        for (Map.Entry<Integer, String> entry : peopleList.entrySet()) {
            JCheckBox checkbox = new JCheckBox(entry.getValue());
            checkbox.setFont(checkboxFont);
            checkbox.setBackground(BG_COLOR);
            checkbox.setForeground(new Color(223, 225, 229));
            checkbox.setFocusPainted(false);
            checkboxMap.put(entry.getKey(), checkbox);
            checkboxPanel.add(checkbox);
            checkboxPanel.add(Box.createVerticalStrut(5));
        }

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setBackground(BG_COLOR);
        scrollPane.getViewport().setBackground(BG_COLOR);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0x3E3F44), 1));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // BOTTOM: Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(BG_COLOR);

        JButton cancelButton = new JButton("Cancel");
        setButtonStyle(cancelButton);
        cancelButton.setFont(largeFont);
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton createButton = new JButton("Create Group");
        setButtonStyle(createButton);
        createButton.setFont(largeFont);
        createButton.addActionListener(e -> {
            try {
                String title = titleField.getText().trim();
                if (title.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please give this crew a name!");
                    return;
                }

                // Collect selected person IDs
                ArrayList<Integer> selectedIds = new ArrayList<>();
                for (Map.Entry<Integer, JCheckBox> entry : checkboxMap.entrySet()) {
                    if (entry.getValue().isSelected()) {
                        selectedIds.add(entry.getKey());
                    }
                }

                handler.handleAddGroupRequest(title, selectedIds);
                graphPanel.repaint();
                graphArea.repaint();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error creating group: " + ex.getMessage());
            }
        });

        bottomPanel.add(cancelButton);
        bottomPanel.add(createButton);

        // Assemble dialog
        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(centerPanel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void editPersonUi(int id) {
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(0, 0));
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("So what's new about them?");
        /// LEFT: Image Selection Panel
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(180, 180));
        imagePanel.setBackground(BG_COLOR);
        imagePanel.setForeground(new Color(255, 255, 255));
        imagePanel.setBorder(BorderFactory.createTitledBorder(null, "Selected image", 0, 0, null, new Color(255, 255, 255)));
        JLabel imageLabel = new JLabel("No Image", SwingConstants.CENTER);
        imageLabel.setForeground(new Color(255, 255, 255));
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        /// Allows the user to pop up the File-selection panel
        BufferedImage[] selectedImage = new BufferedImage[1];
        selectedImage[0] = handler.handleGetPersonImage(id);
        if (selectedImage[0] != null) {
            Image scaled = selectedImage[0].getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
            imageLabel.setText(null);
        }
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
        formPanel.setBackground(BG_COLOR);

        JTextField nameField = new JTextField();
        JLabel textNameInput = new JLabel("Name:");
        textNameInput.setForeground(new Color(255, 255, 255));
        nameField.setText(handler.handleGetPersonName(id));
        formPanel.add(textNameInput);
        formPanel.add(nameField);

        JTextField notesField = new JTextField();
        JLabel textNotesInput = new JLabel("Notes:");
        textNotesInput.setForeground(new Color(255, 255, 255));
        notesField.setText(handler.handleGetPersonNotes(id));
        formPanel.add(textNotesInput);
        formPanel.add(notesField);

        // SOUTH : Buttons Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(BG_COLOR);
        JButton cancelButton = new JButton("Cancel");
        setButtonStyle(cancelButton);
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        JButton saveButton = new JButton("Save");
        setButtonStyle(saveButton);
        saveButton.addActionListener(e -> {
            try {
                handler.handleEditPersonRequest(id, selectedImage[0], nameField.getText(), notesField.getText());
                graphPanel.repaint();
                graphArea.repaint();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "An error occurred :" + error.getMessage());
            }
            dialog.dispose();
        });
        JButton deleteButton = new JButton("Delete information");
        setButtonStyle(deleteButton);
        deleteButton.setForeground(new Color(255, 100, 100));
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    dialog,
                    "Delete this person and all their bonds? Can't be undone.",
                    "Delete the information about this person?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    handler.handleDeletePersonRequest(id);
                    graphPanel.repaint();
                    graphArea.repaint();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error deleting person: " + ex.getMessage());
                }
            }
        });

        bottomPanel.add(deleteButton);
        bottomPanel.add(cancelButton);
        bottomPanel.add(saveButton);

        // Display the panels on the frame
        dialog.add(imagePanel, BorderLayout.WEST);
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
        return;
    }

    private void editBondUi(int id) {
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(0, 0));
        dialog.setSize(600, 350);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("How are things between them?");
        dialog.setBackground(BG_COLOR);

        // Get bond data
        int headId = handler.handleGetBondHeadId(id);
        int tailId = handler.handleGetBondTailId(id);
        int currentRating = handler.handleGetBondRating(id);
        String currentNotes = handler.handleGetBondNotes(id);
        String person1Name = handler.handleGetPersonName(headId);
        String person2Name = handler.handleGetPersonName(tailId);

        // TOP: Display the two people
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        topPanel.setBackground(BG_COLOR);

        Font largeFont = new Font("Dialog", Font.BOLD, 42);
        JLabel person1Label = new JLabel(person1Name);
        person1Label.setFont(largeFont);
        person1Label.setForeground(new Color(255, 255, 255));

        JLabel arrowLabel = new JLabel(" and ");
        arrowLabel.setFont(new Font("Dialog", Font.PLAIN, 42));
        arrowLabel.setForeground(new Color(100, 200, 255));

        JLabel person2Label = new JLabel(person2Name);
        person2Label.setFont(largeFont);
        person2Label.setForeground(new Color(255, 255, 255));

        topPanel.add(person1Label);
        topPanel.add(arrowLabel);
        topPanel.add(person2Label);

        // CENTER: Form Fields
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        formPanel.setBackground(BG_COLOR);

        Font mediumFont = new Font("Dialog", Font.PLAIN, 18);

        JLabel ratingLabel = new JLabel("Bond strength:");
        ratingLabel.setFont(mediumFont);
        ratingLabel.setForeground(new Color(255, 255, 255));

        JComboBox<Integer> ratingDropdown = new JComboBox<>();
        ratingDropdown.setFont(mediumFont);
        for (int i = 1; i <= 10; i++) {
            ratingDropdown.addItem(i);
        }
        ratingDropdown.setSelectedItem(currentRating);

        JLabel notesLabel = new JLabel("Notes:");
        notesLabel.setFont(mediumFont);
        notesLabel.setForeground(new Color(255, 255, 255));

        JTextField notesField = new JTextField(currentNotes);
        notesField.setFont(mediumFont);

        formPanel.add(ratingLabel);
        formPanel.add(ratingDropdown);
        formPanel.add(notesLabel);
        formPanel.add(notesField);

        // BOTTOM: Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(BG_COLOR);

        JButton deleteButton = new JButton("Delete Bond");
        setButtonStyle(deleteButton);
        deleteButton.setFont(mediumFont);
        deleteButton.setForeground(new Color(255, 100, 100));
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    dialog,
                    "Are you sure? This can't be undone.",
                    "Delete this bond?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    handler.handleDeleteBondRequest(id);
                    graphPanel.repaint();
                    graphArea.repaint();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error deleting bond: " + ex.getMessage());
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        setButtonStyle(cancelButton);
        cancelButton.setFont(mediumFont);
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton saveButton = new JButton("Save");
        setButtonStyle(saveButton);
        saveButton.setFont(mediumFont);
        saveButton.addActionListener(e -> {
            try {
                int newRating = (Integer) ratingDropdown.getSelectedItem();
                String newNotes = notesField.getText();
                handler.handleEditBondRequest(id, newRating, newNotes);
                graphPanel.repaint();
                graphArea.repaint();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error saving bond: " + ex.getMessage());
            }
        });

        bottomPanel.add(deleteButton);
        bottomPanel.add(cancelButton);
        bottomPanel.add(saveButton);

        // Assemble dialog
        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void editGroupUi(int id) {
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(0, 0));
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Who's in this crew?");
        dialog.setBackground(BG_COLOR);

        // Get group data
        String currentTitle = handler.handleGetGroupTitle(id);
        ArrayList<Integer> currentMembers = handler.handleGetGroupMembers(id);

        // TOP: Title input
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(BG_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        Font largeFont = new Font("Dialog", Font.PLAIN, 20);
        JLabel titleLabel = new JLabel("Group name:");
        titleLabel.setFont(largeFont);
        titleLabel.setForeground(new Color(255, 255, 255));

        JTextField titleField = new JTextField(currentTitle);
        titleField.setFont(largeFont);
        titleField.setPreferredSize(new Dimension(0, 45));

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(titleField, BorderLayout.CENTER);

        // CENTER: Member selection
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel membersLabel = new JLabel("Who's in this crew?");
        membersLabel.setFont(largeFont);
        membersLabel.setForeground(new Color(255, 255, 255));
        centerPanel.add(membersLabel, BorderLayout.NORTH);

        // Get person list and create checkboxes
        HashMap<Integer, String> peopleList = handler.handleGetPersonList();
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        checkboxPanel.setBackground(BG_COLOR);

        HashMap<Integer, JCheckBox> checkboxMap = new HashMap<>();
        Font checkboxFont = new Font("Dialog", Font.PLAIN, 18);

        for (Map.Entry<Integer, String> entry : peopleList.entrySet()) {
            JCheckBox checkbox = new JCheckBox(entry.getValue());
            checkbox.setFont(checkboxFont);
            checkbox.setBackground(BG_COLOR);
            checkbox.setForeground(new Color(223, 225, 229));
            checkbox.setFocusPainted(false);

            // Check if person is already in group
            if (currentMembers.contains(entry.getKey())) {
                checkbox.setSelected(true);
            }

            checkboxMap.put(entry.getKey(), checkbox);
            checkboxPanel.add(checkbox);
            checkboxPanel.add(Box.createVerticalStrut(5));
        }

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setBackground(BG_COLOR);
        scrollPane.getViewport().setBackground(BG_COLOR);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0x3E3F44), 1));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // BOTTOM: Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(BG_COLOR);

        JButton deleteButton = new JButton("Delete Group");
        setButtonStyle(deleteButton);
        deleteButton.setFont(largeFont);
        deleteButton.setForeground(new Color(255, 100, 100));
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    dialog,
                    "Delete this whole crew? Can't be undone.",
                    "Delete group?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    handler.handleDeleteGroupRequest(id);
                    graphPanel.repaint();
                    graphArea.repaint();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error deleting group: " + ex.getMessage());
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        setButtonStyle(cancelButton);
        cancelButton.setFont(largeFont);
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton saveButton = new JButton("Save");
        setButtonStyle(saveButton);
        saveButton.setFont(largeFont);
        saveButton.addActionListener(e -> {
            try {
                String newTitle = titleField.getText().trim();
                if (newTitle.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please give this crew a name!");
                    return;
                }

                // Collect selected person IDs
                ArrayList<Integer> selectedIds = new ArrayList<>();
                for (Map.Entry<Integer, JCheckBox> entry : checkboxMap.entrySet()) {
                    if (entry.getValue().isSelected()) {
                        selectedIds.add(entry.getKey());
                    }
                }

                handler.handleEditGroupRequest(id, newTitle, selectedIds);
                graphPanel.repaint();
                graphArea.repaint();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error saving group: " + ex.getMessage());
            }
        });

        bottomPanel.add(deleteButton);
        bottomPanel.add(cancelButton);
        bottomPanel.add(saveButton);

        // Assemble dialog
        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(centerPanel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void changeAccountButtonUi() {
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(0, 0));
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Let's take you to where you belong!");
        dialog.setBackground(BG_COLOR);

        // CENTER: Login fields
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        Font largeFont = new Font("Dialog", Font.PLAIN, 20);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(largeFont);
        usernameLabel.setForeground(new Color(255, 255, 255));

        JTextField usernameField = new JTextField();
        usernameField.setFont(largeFont);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(largeFont);
        passwordLabel.setForeground(new Color(255, 255, 255));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(largeFont);

        centerPanel.add(usernameLabel);
        centerPanel.add(usernameField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);

        // BOTTOM: Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(BG_COLOR);

        JButton cancelButton = new JButton("Cancel");
        setButtonStyle(cancelButton);
        cancelButton.setFont(largeFont);
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton loginButton = new JButton("Log in");
        setButtonStyle(loginButton);
        loginButton.setFont(largeFont);
        loginButton.addActionListener(e -> {
            try {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please enter both username and password!");
                    return;
                }

                handler.handleLogin(username, password);
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error logging in: " + ex.getMessage());
            }
        });

        bottomPanel.add(cancelButton);
        bottomPanel.add(loginButton);

        // Assemble dialog
        dialog.add(centerPanel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void changeCurrentGroupUi(Integer selectedGroupId) {
        if (selectedGroupId != null) {
            try {
                handler.handleChangeCurrentGroup(selectedGroupId);
                graphPanel.repaint();
                graphArea.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error switching group: " + ex.getMessage());
            }
        }
    }

    private void addPersonToCurrentGroupUi(Integer selectedPersonId) {
        if (selectedPersonId != null) {
            try {
                handler.handleAddPersonToCurrentGroup(selectedPersonId);
                graphPanel.repaint();
                graphArea.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error adding person to group: " + ex.getMessage());
            }
        }
    }

    private void removePersonFromCurrentGroupUi(Integer selectedPersonId) {
        if (selectedPersonId != null) {
            try {
                handler.handleRemovePersonFromCurrentGroup(selectedPersonId);
                graphPanel.repaint();
                graphArea.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error removing person from group: " + ex.getMessage());
            }
        }
    }

    private void raiseBondFromCurrentGroupUi(Integer selectedBondId) {
        if (selectedBondId != null) {
            try {
                handler.handleRaiseBondInCurrentGroup(selectedBondId);
                graphPanel.repaint();
                graphArea.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error raising bond: " + ex.getMessage());
            }
        }
    }

    private void lowerBondFromCurrentGroupUi(Integer selectedBondId) {
        if (selectedBondId != null) {
            try {
                handler.handleLowerBondInCurrentGroup(selectedBondId);
                graphPanel.repaint();
                graphArea.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error lowering bond: " + ex.getMessage());
            }
        }
    }

    private void raiseAllBondsFromCurrentGroupUi() {
        try {
            handler.handleRaiseAllBondsInCurrentGroup();
            JOptionPane.showMessageDialog(null, "Raised all bonds by 1/10!");
            graphPanel.repaint();
            graphArea.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error raising bonds: " + ex.getMessage());
        }
    }

    private void lowerAllBondsFromCurrentGroupUi() {
        try {
            handler.handleLowerAllBondsInCurrentGroup();
            JOptionPane.showMessageDialog(null, "Lowered all bonds by 1/10!");
            graphPanel.repaint();
            graphArea.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error lowering bonds: " + ex.getMessage());
        }
    }

    private void generatePartitionsUi() {
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(700, 350);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("How many groups should be in this activity?");


        JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        container.setBackground(BG_COLOR);
        container.setForeground(BG_COLOR);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        topPanel.setBackground(BG_COLOR);
        topPanel.setForeground(BG_COLOR);

        JLabel textLabel = new JLabel("Pick how many groups are in this activity:");
        textLabel.setFont(new Font("Dialog", Font.BOLD, 25));
        textLabel.setForeground(new Color(255, 255, 255));
        topPanel.add(textLabel);

        JComboBox<Integer> options = new JComboBox<>();
        int count = handler.handleGetPeopleInCurrentGroup().size();
        for (int i = 2; i <= count/2; i++) {
            options.addItem(i);
        }
        options.setFont(new Font("Dialog", Font.BOLD, 32));
        topPanel.add(options);

        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        botPanel.setBackground(BG_COLOR);
        botPanel.setForeground(BG_COLOR);

        JButton cancelButton = new JButton("Cancel");
        setButtonStyle(cancelButton);
        cancelButton.setFont(new Font("Dialog", Font.BOLD, 42));
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });
        botPanel.add(cancelButton);

        JButton runButton = new JButton("Let's see the groups");
        setButtonStyle(runButton);
        runButton.setForeground(new Color(50, 255, 50));
        runButton.setFont(new Font("Dialog", Font.BOLD, 42));
        runButton.addActionListener(e -> {
            Integer groupCount = options.getItemAt(options.getSelectedIndex());
            try {
                showList(handler.handleGetActiveGroupPartitions(groupCount, (GraphArea) graphArea), "Here are the groups:", false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error getting the groups:" + ex.getMessage());
            }
            dialog.dispose();
        });
        botPanel.add(runButton);

        container.add(topPanel);
        container.add(botPanel);
        dialog.add(container);
        dialog.setVisible(true);
    }

    private void getMaxDirectCentralityUi() {
        try {
            showList(handler.handleGetAscendingDirectCentrality((GraphArea)graphArea), "Here's the group, in order of popularity:", false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error getting the list:" + ex.getMessage());
        }
    }

    private void getMaxIndirectCentralityUi() {
        try {
            showList(handler.handleGetAscendingIndirectCentrality((GraphArea) graphArea), "Here's the group, in order of influence:", false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error getting the list:" + ex.getMessage());
        }
    }

    private void getMinDirectCentralityUi() {
        HashMap<Integer, String> ascendingList = handler.handleGetAscendingDirectCentrality((GraphArea) graphArea);
        HashMap<Integer, String> descendingList = new HashMap<>();
        ArrayList<String> values = new ArrayList<>(ascendingList.values());
        Collections.reverse(values);
        int index = 0;
        for (Integer key : ascendingList.keySet()) {
            descendingList.put(key, values.get(index++));
        }

        try {
            showList(descendingList, "Here's the group, in order of unpopularity:", false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error getting the list:" + ex.getMessage());
        }
    }

    private void getMinIndirectCentralityUi() {
        HashMap<Integer, String> ascendingList = handler.handleGetAscendingIndirectCentrality((GraphArea) graphArea);
        HashMap<Integer, String> descendingList = new HashMap<>();
        ArrayList<String> values = new ArrayList<>(ascendingList.values());
        Collections.reverse(values);
        int index = 0;
        for (Integer key : ascendingList.keySet()) {
            descendingList.put(key, values.get(index++));
        }

        try {
            showList(descendingList, "Here's the group, in order of irrelevance:", false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error getting the list:" + ex.getMessage());
        }
    }

    private void getClusteringUi() {
        try {
            showList(handler.handleGetClustering((GraphArea) graphArea), "Here's the cliques we found:", false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error getting clustering:" + ex.getMessage());
        }
    }

    private void getDijkstraUi() {
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout(0, 0));
        dialog.setSize(500, 300);
        dialog.setBackground(BG_COLOR);
        dialog.setForeground(new Color(255, 255, 255));
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Who should meet who?");
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(BG_COLOR);
        HashMap<Integer, String> peopleList = handler.handleGetPersonList();

        JLabel prefixText = new JLabel("I want ");
        Font largeFont = new Font(prefixText.getFont().getName(), Font.PLAIN, 20);
        prefixText.setForeground(new Color(255, 255, 255));
        prefixText.setFont(largeFont);
        JPanel idSelect = new JPanel();
        idSelect.setBackground(BG_COLOR);
        idSelect.setForeground(new Color(255, 255, 255));
        JComboBox<String> dropdown1 = new JComboBox<>(peopleList.values().toArray(new String[peopleList.size()]));
        dropdown1.setFont(largeFont);
        JComboBox<String> dropdown2 = new JComboBox<>(peopleList.values().toArray(new String[peopleList.size()]));
        dropdown2.setFont(largeFont);
        JLabel metText = new JLabel("to meet");
        metText.setFont(largeFont);
        metText.setForeground(new Color(255, 255, 255));
        idSelect.add(prefixText);
        idSelect.add(dropdown1);
        idSelect.add(metText);
        idSelect.add(dropdown2);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(BG_COLOR);
        buttonsPanel.setForeground(new Color(255, 255, 255));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });
        setButtonStyle(cancelButton);
        cancelButton.setFont(largeFont);

        JButton saveButton = new JButton("Show me how");
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
                if (id1 == id2) {
                    throw new Exception("People already know themselves.");
                }
                HashMap<Integer, String> list = handler.handleGetDijkstraRoute(id1, id2, (GraphArea) graphArea);
                if (list.values().size() == 0) {
                    JOptionPane.showMessageDialog(dialog, "They should meet directly.");
                } else {
                    showList(list, "Here's how the two could meet:", false);
                }
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error finding route: " + ex.getMessage());
            }
        });
        setButtonStyle(saveButton);
        saveButton.setFont(largeFont);

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(saveButton);

        container.add(Box.createVerticalGlue());
        container.add(idSelect);
        container.add(Box.createVerticalStrut(10));
        container.add(buttonsPanel);
        container.add(Box.createVerticalGlue());
        dialog.add(container);
        dialog.setVisible(true);

    }

    private void getClosenessCentralityUi() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Group Rating");
        dialog.setModal(true);
        dialog.setSize(400, 150);

        JPanel container = new JPanel();
        container.setBackground(BG_COLOR);
        container.setForeground(Color.WHITE);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(BG_COLOR);
        JLabel explanationText = new JLabel("On a scale of 1 to 10, your group would be a ");
        Font largeFont = new Font(explanationText.getFont().getName(), Font.PLAIN, 40);
        explanationText.setFont(largeFont);
        explanationText.setForeground(Color.WHITE);
        topPanel.add(explanationText);

        JPanel botPanel = new JPanel();
        botPanel.setBackground(BG_COLOR);

        double rating = -1;
        try {
            rating = handler.handleGetActiveGroupRating((GraphArea) graphArea);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(dialog, "Getting rating: " + ex.getMessage());
        }

        String displayedRating;
        Color ratingColor;

        if(rating < 0) {
            displayedRating = "???";
            ratingColor = Color.GRAY;
        }
        else
        {
            displayedRating = String.format("%.1f", rating);
            ratingColor = new Color((int) ((10 - rating)*235/10), (int) (rating * 235/10), 0);
        }

        JLabel ratingLabel = new JLabel(displayedRating + "/10");
        ratingLabel.setFont(largeFont);
        ratingLabel.setForeground(ratingColor);
        botPanel.add(ratingLabel);

        container.add(topPanel);
        container.add(Box.createVerticalStrut(10));
        container.add(botPanel);

        dialog.add(container);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void getKCoreDecompositionUi() {
        showList(handler.handleGetKCoreDecomposition((GraphArea) graphArea), "Here's who the og's are:", false);
    }

    private Integer showList(HashMap<Integer, String> dataList, String titleText, boolean clickable) {
        JDialog dialog = new JDialog((Frame) null, "", true);
        dialog.setLayout(new BorderLayout(0, 0));
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(null);
        dialog.setBackground(BG_COLOR);
        dialog.setForeground(BG_COLOR);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        titlePanel.setBackground(BG_COLOR);
        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 28));
        titleLabel.setForeground(new Color(255, 255, 255));
        titlePanel.add(titleLabel);

        DefaultListModel<Integer> listModel = new DefaultListModel<>();
        for (Integer key : dataList.keySet()) {
            listModel.addElement(key);
        }

        JList<Integer> displayList = new JList<>(listModel);
        displayList.setBackground(BG_COLOR);
        displayList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            String text = dataList.get(value);
            JTextArea textArea = new JTextArea(text);
            Border border = BorderFactory.createLineBorder(Color.WHITE);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setFocusable(false);
            textArea.setFont(new Font("Dialog", Font.PLAIN, 40));
            textArea.setColumns(15);

            /// This is for limit cases when the string is too big
            int width = 700;
            textArea.setSize(new Dimension(width, Integer.MAX_VALUE));
            Dimension d = textArea.getPreferredSize();
            textArea.setPreferredSize(new Dimension(width, d.height + 20));

            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setOpaque(true);

            if (isSelected && clickable) {
                textArea.setBackground(new Color(0x3E3F44));
                wrapper.setBackground(new Color(0x3E3F44));
                textArea.setForeground(Color.WHITE);
            } else {
                textArea.setBackground(BG_COLOR);
                wrapper.setBackground(BG_COLOR);
                textArea.setForeground(Color.LIGHT_GRAY);
            }
            textArea.setBorder(BorderFactory.createCompoundBorder(border,
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            wrapper.add(textArea, BorderLayout.CENTER);
            return wrapper;
        });

        Integer[] targetId = {null};

        if (clickable) {
            displayList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int index = displayList.locationToIndex(e.getPoint());
                        if (index != -1) {
                            targetId[0] = displayList.getModel().getElementAt(index);
                            dialog.dispose();
                        }
                    }
                }
            });
        } else {
            displayList.setEnabled(false);
        }

        JScrollPane scrollPane = new JScrollPane(displayList);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        dialog.add(titlePanel, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.setVisible(true);
        return targetId[0];
    }

    private void createUIComponents() {
        this.graphArea = new GraphArea();
    }
}
