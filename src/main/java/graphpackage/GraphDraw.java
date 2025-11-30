package main.java.graphpackage;

import main.java.datapackage.Bond;
import main.java.datapackage.Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
        if(bonds == null)
        {
            return;
        }
        ArrayList<Triple> positions = graph.getNodePositions(panelWidth,panelHeight,calculatePhotoRadius(panelWidth,panelHeight,graph.getNodes().size()));

        for (Triple t : positions)
        {
            int radiusPhoto=calculatePhotoRadius(panelWidth,panelHeight,positions.size());
            int nameWidth=2*radiusPhoto;
            Node newNode = new Node(t.first(),t.second(),t.third(),radiusPhoto,nameWidth);
            nodes.put(t.first(),newNode);
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
        return (int) (Math.min(panelHeight,panelWidth)/(5* Math.sqrt(nodeCount)));
    }

    public LinkedHashMap<Integer, String> ascendingDirectCentrality() {
        LinkedHashMap<Integer,String> ret = new LinkedHashMap<>();
        for(Integer id : graph.directCentrality())
        {
            ret.put(id,Model.getPerson(id).getName());
        }
        return ret;
    }

    public LinkedHashMap<Integer, String> ascendingIndirectCentrality() {
        LinkedHashMap<Integer,String> ret = new LinkedHashMap<>();
        for(Integer id : graph.indirectCentrality())
        {
            ret.put(id,Model.getPerson(id).getName());
        }
        return ret;
    }

    public LinkedHashMap<Integer, String> activeGroupPartitions(int groupCount) {
        ArrayList<ArrayList<Integer>> lists = graph.groupPartitions(groupCount);
        LinkedHashMap<Integer,String> ret = new LinkedHashMap<>();
        int index = 0;
        for(List<Integer> list : lists)
        {
            String peopleInGroup = "";
            for(Integer id : list)
            {
                peopleInGroup = peopleInGroup + Model.getPerson(id).getName() + " ";
            }
            ret.put(index++,peopleInGroup);
        }
        return ret;
    }

    public LinkedHashMap<Integer, String> clustering() {
        ArrayList<ArrayList<Integer>> lists = graph.clustering();
        LinkedHashMap<Integer,String> ret = new LinkedHashMap<>();
        int index = 0;
        for(List<Integer> list : lists)
        {
            String peopleInGroup = "";
            for(Integer id : list)
            {
                peopleInGroup = peopleInGroup + Model.getPerson(id).getName() + " ";
            }
            ret.put(index++,peopleInGroup);
        }
        return ret;
    }

    public LinkedHashMap<Integer, String> dijkstraRoute(int id1, int id2) {
        LinkedHashMap<Integer,String> ret = new LinkedHashMap<>();
        ArrayList<Triple> route = graph.dijkstra(id1,id2);
        int index = 0;
        for(Triple triple : route)
        {
            ret.put(index++, Model.getPerson(triple.first()).getName() + " knows(" + (11-triple.second()) +"/10) " + Model.getPerson(triple.third()).getName());
        }
        return ret;
    }

    public double activeGroupRating() {
        return graph.groupRating();
    }

    public LinkedHashMap<Integer, String> kCoreDecomposition() {
        LinkedHashMap<Integer,String> ret = new LinkedHashMap<>();
        ArrayList<Integer> group = graph.maxKCoreDecomposition();
        for(int i =0; i<group.size();i++)
        {
            ret.put(i,Model.getPerson(group.get(i)).getName());
        }
        return ret;
    }
}
