package graphpackage;

import datapackage.Model;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Set;

public class GraphArea extends JPanel {

    private Graph graph;

    public GraphArea() {
        initializeGraph();
    }

    @Override
    protected void paintComponent(Graphics g) {
        initializeGraph();
        //graph.onDraw(g);
        g.drawRect(10,15,g.getClipBounds().width-20,g.getClipBounds().height-20);
    }

    private void initializeGraph()
    {
        Set<Integer> peopleId = Model.getPeopleInActiveGroup().keySet();
        Set<Integer> bondsId = Model.getBondsInCurrentGroup().keySet();
        graph = new Graph(peopleId,bondsId);
    }
}
