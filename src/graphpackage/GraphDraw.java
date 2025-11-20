package graphpackage;

import datapackage.Bond;
import datapackage.Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

class GraphDraw {
    HashMap<Integer,Node> nodes;
    HashMap<Integer,Edge> edges;
    Graph graph;

    public GraphDraw(Set<Integer> bonds, int panelWidth, int panelHeight)
    {
        graph = new Graph(bonds);
        nodes = new HashMap<>();
        edges = new HashMap<>();
        ArrayList<Triple> positions = graph.getNodePositions(panelWidth,panelHeight,calculatePhotoRadius(panelWidth,panelHeight,graph.getNodes().size()));

        for (Triple t : positions)
        {
            int radiusPhoto=calculatePhotoRadius(panelWidth,panelHeight,positions.size());
            int nameWidth=2*radiusPhoto;
            Node newNode = new Node(t.id(),t.x(),t.y(),radiusPhoto,nameWidth);
            nodes.put(t.id(),newNode);
        }

        for(Integer bondId : bonds)
        {
            Bond bond = Model.getBond(bondId);
            Edge newEdge = new Edge(bondId,
                                nodes.get(bond.getHeadId()).getX(),
                                nodes.get(bond.getTailId()).getX(),
                                nodes.get(bond.getHeadId()).getY(),
                                nodes.get(bond.getTailId()).getY());
            edges.put(bondId,newEdge);
        }
    }

    public void onDraw(Graphics g)
    {
        for(Edge edge : edges.values())
        {
            edge.onDraw(g);
        }
        for(Node node : nodes.values())
        {
            node.onDraw(g);
        }
    }

    private int calculatePhotoRadius(int panelWidth, int panelHeight, int nodeCount)
    {
        return Math.min(panelHeight,panelWidth)/((int)Math.log(nodeCount + 1<<15));
    }
}
