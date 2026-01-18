package graphpackage;

import datapackage.Bond;
import datapackage.Model;

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
    int panelWidth = 0;
    int panelHeight = 0;

    public GraphDraw(Set<Integer>nodes, Set<Integer> bonds, int panelWidth, int panelHeight)
    {
        this.graph = new Graph(nodes,bonds);
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
        this.panelHeight = panelHeight;
        this.panelWidth = panelWidth;
        ArrayList<Triple> positions = graph.getNodePositions(panelWidth,panelHeight,calculatePhotoRadius(graph.getNodes().size()));

        for (Triple t : positions)
        {
            int radiusPhoto=calculatePhotoRadius(positions.size());
            int nameWidth=2*radiusPhoto;
            Node newNode = new Node(t.first(),t.second(),t.third(),radiusPhoto,nameWidth);
            this.nodes.put(t.first(),newNode);
        }

        for(Integer bondId : bonds)
        {
            Bond bond = Model.getBond(bondId);
            Edge newEdge = new Edge(bondId,
                                this.nodes.get(bond.getHeadId()).getX(),
                                this.nodes.get(bond.getTailId()).getX(),
                                this.nodes.get(bond.getHeadId()).getY(),
                                this.nodes.get(bond.getTailId()).getY());
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
        if(edges.isEmpty() && nodes.isEmpty())
        {
            Color originalColor = g.getColor();
            g.setColor(Color.WHITE);
            String message = "No active group found. Try making one!";
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(message);
            int x = (this.panelWidth - textWidth) / 2;
            int y = this.panelHeight / 2;
            g.drawString(message, x, y);
            g.setColor(originalColor);
        }
    }

    public void onClick(int x, int y) {
        int nodeRadius = calculatePhotoRadius(nodes.size());
        for(Node node : nodes.values())
        {
            if( node.getX() - nodeRadius <= x && node.getX()+nodeRadius >= x &&
                node.getY() - nodeRadius <= y && node.getY()+nodeRadius >= y)
            {
                node.onClick();
                return;
            }
        }
        for(Edge edge : edges.values())
        {
            /// Distance from a point to a line
            /// y = mx + n;
            double distance;
            if(edge.getX1() == edge.getX2())
            {
                distance = Math.abs( x - edge.getX1());
            }
            else
            {
                double m = ((double) edge.getY1() - edge.getY2()) / ((double)edge.getX1() - edge.getX2());
                double n = edge.getY2() - edge.getX2() * m;
                distance = Math.abs((-1)*m*x + y - n)/Math.sqrt((double)(m*m) + 1);
            }
            int lineWidth = 30/ Math.max(1, 5 * edge.getBondId() / 10);
            if(distance <= ((double) lineWidth /2) + 5)
            {
                edge.onClick();
                return;
            }
        }
    }

    private int calculatePhotoRadius( int nodeCount)
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
                if(id==-1) continue;
                peopleInGroup = peopleInGroup + Model.getPerson(id).getName() + " ";
                if(!list.get(list.size()-1).equals(id))
                {
                    peopleInGroup = peopleInGroup + ',';
                }
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
                if(!list.get(list.size()-1).equals(id))
                {
                    peopleInGroup = peopleInGroup + ',';
                }
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
