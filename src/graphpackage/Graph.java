package graphpackage;

import datapackage.Bond;
import datapackage.Person;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class Graph {
    ArrayList<Node> nodes;
    ArrayList<Edge> edges;

    public Graph(Set<Integer> people, Set<Integer> bonds)
    {
        /// Handle placing logic here.
    }

    public void onDraw(Graphics g)
    {
        for(Edge edge : edges)
        {
            edge.onDraw(g);
        }
        for(Node node : nodes)
        {
            node.onDraw(g);
        }
    }

}
