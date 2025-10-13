package uipackage;

import java.awt.*;
import javax.swing.*;

public class Ui extends JFrame {
    private UiHandler handle = new UiHandler();

    public Ui(UiHandler handle)
    {
        this.handle = handle;
        SwingUtilities.invokeLater(() -> draw());
    }

    private void draw()
    {
        setLayout(new BorderLayout());
        setTitle("TeamBuilder");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        
        JPanel panel = new JPanel();
        JLabel label = new JLabel("TeamBuilder");
        panel.add(label);

        add(panel, BorderLayout.NORTH);
        setVisible(true);
    }
}
