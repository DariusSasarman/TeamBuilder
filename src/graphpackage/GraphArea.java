package graphpackage;

import datapackage.Model;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class GraphArea extends JPanel {

    private GraphDraw graphDraw;
    public GraphArea() {
        super();
        setMinimumSize(new Dimension(400, 400));
        setPreferredSize(new Dimension(600, 600));
        initializeGraph();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        initializeGraph();
        graphDraw.onDraw(g);
    }

    private void initializeGraph()
    {
        graphDraw = new GraphDraw(Model.getBondsInCurrentGroup().keySet(),
                getWidth(),
                getHeight());
    }
}
