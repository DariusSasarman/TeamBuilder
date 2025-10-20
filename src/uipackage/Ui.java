package uipackage;

import javax.swing.*;
import java.awt.*;

public class Ui {
    private UiHandler handler;
    private JPanel contentPanel;
    private JPanel generalSettingsPanel;
    private JPanel graphPanel;
    private JPanel graphActionsPanel;
    private JPanel groupSettingsPanel;
    private JPanel GraphArea;
    private JScrollPane generalSettingsScrollPane;
    private JScrollPane groupSettingsScrollPane;
    private JScrollPane graphActionsScrollPane;
    private JPanel generalSettingsButtonPanel;
    private JPanel graphActionsButtonPanel;
    private JPanel groupSettingsButtonPanel;
    private JButton ChangeCurrentGroupButton;

    public Ui(UiHandler handler){
        this.handler = handler;
        setupButtonPanels();
        setupButtons();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("TeamBuilder");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(this.contentPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }

    private void setupButtonPanels()
    {
        generalSettingsButtonPanel.setBackground(new Color(0x2B2C30));
        graphActionsButtonPanel.setBackground(new Color(0x2B2C30));
        groupSettingsButtonPanel.setBackground(new Color(0x2B2C30));
    }

    private void setupButtons()
    {
        ChangeCurrentGroupButton.addActionListener( e -> {
            handler.changeCurrentGroup();
        });
    }
}
