package graphpackage;

import datapackage.Bond;
import datapackage.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

class Graph {
    HashMap<Integer, HashMap<Integer,Integer>> adjacencyMatrix;
    public Graph(Set<Integer> bonds)
    {
        adjacencyMatrix = new HashMap<>();
        for(Integer i : bonds)
        {
            Bond bond = Model.getBond(i);
            adjacencyMatrix.computeIfAbsent(bond.getHeadId(), k -> new HashMap<>());
            adjacencyMatrix.computeIfAbsent(bond.getTailId(), k -> new HashMap<>());
            adjacencyMatrix.get(bond.getHeadId()).put(bond.getTailId(),11 - bond.getRating());
            adjacencyMatrix.get(bond.getTailId()).put(bond.getHeadId(),11 - bond.getRating());
        }
    }

    public Set<Integer> getNodes()
    {
        return adjacencyMatrix.keySet();
    }
    private int getWeight(int head, int tail)
    {
        return adjacencyMatrix.get(head).get(tail);
    }

    ArrayList<Triple> getNodePositions(int maxWidth, int maxHeight, int radius)
    {
        /// Assume rectangle starts from 0.0
        ArrayList<Triple> ret = new ArrayList<>();
        Set<Integer> people = Model.getPeopleInActiveGroup().keySet();
        for(Integer uid : people)
        {
            /// TODO: This, but, properly.
            int x = (int)(Math.random()*(maxWidth-2*radius)) ;
            x+= radius;
            int y = (int)(Math.random()*(maxHeight-2*radius));
            y+= radius;
            ret.add(new Triple(uid, x, y));
        }

        return ret;
    }

}
