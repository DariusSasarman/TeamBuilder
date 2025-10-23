package uipackage;

import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Graph extends JPanel {

    private static final Image PLACEHOLDER_IMAGE;

    static {
        URL imageUrl = Graph.class.getResource("/icons/placeholderGraph.png");
        Image image = null;
        if (imageUrl != null) {
            image = new ImageIcon(imageUrl).getImage();
        } else {
            System.err.println("Placeholder image not found.");
        }
        PLACEHOLDER_IMAGE = image;
    }

    public Graph() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (PLACEHOLDER_IMAGE != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            g.drawImage(PLACEHOLDER_IMAGE, 0, 0, panelWidth, panelHeight, this);
        } else {
            g.clearRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.RED);
            g.drawString("Image not loaded.", 50, 50);
        }
    }
}
