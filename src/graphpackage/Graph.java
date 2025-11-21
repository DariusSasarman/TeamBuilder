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

    private ArrayList<Triple> getInitialRadialPositions(int centerX, int centerY, int placementRadius)
    {
        double degree = 360.0 / getNodes().size();
        degree = Math.toRadians(degree);
        ArrayList<Triple> ret = new ArrayList<>();
        int placementX = centerX - placementRadius;
        int placementY = centerY;
        for(Integer id : getNodes())
        {
            ret.add(new Triple(id,placementX,placementY));
            int newPlacementX = (int) (centerX + (placementX-centerX) * Math.cos(degree) - (placementY-centerY) * Math.sin(degree));
            int newPlacementY = (int) (centerY + (placementX-centerX) * Math.sin(degree) + (placementY-centerY) * Math.cos(degree));
            placementX = newPlacementX;
            placementY = newPlacementY;
        }
        return ret;
    }

    ArrayList<Triple> getNodePositions(int maxWidth, int maxHeight, int radius)
    {
        /// Assume rectangle starts from 0
        ArrayList<Triple> ret = new ArrayList<>();
        ret=getInitialRadialPositions(maxWidth/2,maxHeight/2,Math.min(maxWidth,maxHeight)/2-radius);
        /// Now follows the placement code
        return ret;
    }

}
