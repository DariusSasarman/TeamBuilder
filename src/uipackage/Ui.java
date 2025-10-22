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

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.graphArea = new Graph();
    }
}
