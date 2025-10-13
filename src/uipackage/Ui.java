package uipackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setLayout(new BorderLayout());
        setTitle("TeamBuilder");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        
        JPanel panel = new JPanel();
        JLabel label = new JLabel("This is just testing out writing");
        JButton button = new JButton("This is just testing out buttons");
        JLabel label1 = new JLabel("The button has been pressed " + count + " times");

        button.addActionListener(e -> {
            count++;
            handle.increment(count, label1);
        });

        panel.add(label, JPanel.TOP_ALIGNMENT);
        panel.add(button,JPanel.CENTER_ALIGNMENT);
        panel.add(label1,JPanel.BOTTOM_ALIGNMENT);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}
