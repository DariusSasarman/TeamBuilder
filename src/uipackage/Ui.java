package uipackage;

import java.awt.*;
import javax.swing.*;

public class Ui extends JFrame {
    private UiHandler handle = new UiHandler();
    private int count = 0;
    public Ui(UiHandler handle)
    {
        this.handle = handle;
        SwingUtilities.invokeLater(() -> draw());
    }

    private void draw()
    {  
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        setTitle("TeamBuilder");
        setSize(screenSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel generalSettings = new JPanel();

        generalSettings.setBackground(Color.BLUE);

        JPanel groupSettings = new JPanel();

        groupSettings.setBackground(Color.green);

        JPanel graphActions = new JPanel();

        graphActions.setBackground(Color.red);

        JPanel graphSpace = new JPanel();

        graphSpace.setBackground(Color.yellow);

        // Top panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.gridheight = 1;
        add(generalSettings, gbc);

        // Left panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 3;
        add(groupSettings, gbc);

        // Center panel
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        add(graphSpace, gbc);

        // Right panel
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 3;
        add(graphActions, gbc);

        setVisible(true);
    }

}
