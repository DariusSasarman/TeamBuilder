package uipackage;

import javax.swing.*;
import java.awt.*;

public class Graph extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0,0,1000,1000);
        g.setColor(new Color(255,255,255));
        g.drawString(new String("No graph added yet"),50, 50);
    }
}
