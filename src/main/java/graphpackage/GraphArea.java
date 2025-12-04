package graphpackage;

import datapackage.Model;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;

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
        graphDraw = new GraphDraw(Model.getPeopleInActiveGroup().keySet(),Model.getBondsInCurrentGroup().keySet(),
                getWidth(),
                getHeight());
    }

    public LinkedHashMap<Integer, String> getAscendingDirectCentrality() {
        return graphDraw.ascendingDirectCentrality();
    }

    public LinkedHashMap<Integer, String> getAscendingIndirectCentrality() {
        return graphDraw.ascendingIndirectCentrality();
    }

    public LinkedHashMap<Integer, String> getActiveGroupPartitions(int groupCount) {
        return graphDraw.activeGroupPartitions(groupCount);
    }

    public LinkedHashMap<Integer, String> getClustering() {
        return graphDraw.clustering();
    }

    public LinkedHashMap<Integer, String> getDijkstraRoute(int id1, int id2) {
        return graphDraw.dijkstraRoute(id1,id2);
    }

    public double getActiveGroupRating() {
        return graphDraw.activeGroupRating();
    }

    public LinkedHashMap<Integer, String> getKCoreDecomposition() {
        return graphDraw.kCoreDecomposition();
    }
}
