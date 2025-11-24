package graphpackage;

import datapackage.Bond;
import datapackage.Model;

import java.security.KeyPair;
import java.util.*;

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
    private Integer getWeight(int head, int tail)
    {
        if(adjacencyMatrix.get(head).get(tail) == null)
        {
            return null;
        }

        return adjacencyMatrix.get(head).get(tail);
    }

    private double normalizeEdge(Integer value)
    {
        if(value == null)
        {
            return 1;
        }
        return  1.0 - (value - 1.0)/9.0;
    }

    private HashMap<Integer,Pair> getInitialRadialPositions(int centerX, int centerY, int placementRadius)
    {
        double degree = 360.0 / getNodes().size();
        degree = Math.toRadians(degree);
        HashMap<Integer,Pair> ret = new HashMap<>();
        int placementX = centerX - placementRadius;
        int placementY = centerY;
        for(Integer id : getNodes())
        {
            ret.put(id,new Pair(placementX,placementY));
            int newPlacementX = (int) (centerX + (placementX-centerX) * Math.cos(degree) - (placementY-centerY) * Math.sin(degree));
            int newPlacementY = (int) (centerY + (placementX-centerX) * Math.sin(degree) + (placementY-centerY) * Math.cos(degree));
            placementX = newPlacementX;
            placementY = newPlacementY;
        }
        return ret;
    }

    private double attractionForce(double distance, double coefficient, int head, int tail)
    {
        return distance * distance / coefficient * normalizeEdge(getWeight(head,tail));
    }

    private double repulsionForce(double distance, double coefficient)
    {
        return coefficient * coefficient / distance;
    }

    private double modulus(Pair pair)
    {
        double mod = Math.sqrt(pair.first()*pair.first() + pair.second()*pair.second());
        if(mod < 0.00001)
        {
            return 0.00001;
        }
        return mod;
    }


    ArrayList<Triple> getNodePositions(int maxWidth, int maxHeight, int radius)
    {
        HashMap<Integer,Pair> positions = getInitialRadialPositions(maxWidth/2,maxHeight/2,Math.min(maxWidth,maxHeight)/2-radius);
        HashMap<Integer, Pair> displacement = new HashMap<>();
        double temperature = 1.0;
        double coolingCoefficient = 0.99;
        int area = maxWidth * maxHeight;
        double coefficient = Math.sqrt((double) area / getNodes().size());
        while(temperature > 0.1)
        {
            /// Calculate Repulsion between nodes
            for(Integer v : getNodes())
            {
                displacement.put(v,new Pair(0.0,0.0));
                for(Integer u : getNodes())
                {
                    if(!Objects.equals(u, v))
                    {
                        Pair delta = new Pair(
                                positions.get(v).first() - positions.get(u).first(),
                                positions.get(v).second() - positions.get(u).second());
                        Pair newDisplacement = new Pair(
                                displacement.get(v).first() + delta.first()/modulus(delta) * repulsionForce(modulus(delta),coefficient),
                                displacement.get(v).second() + delta.second()/modulus(delta) * repulsionForce(modulus(delta),coefficient)
                        );
                        if(modulus(delta) < 2*radius)
                        {
                            double overlap = 2*radius - modulus(delta);
                            double push = overlap / modulus(delta);
                            newDisplacement = new Pair(
                                    newDisplacement.first() + delta.first() * push,
                                    newDisplacement.second() + delta.second() * push
                            );
                        }
                        displacement.put(v,newDisplacement);
                    }
                }
            }
            /// Calculate Attraction between nodes,based on edges
            for(Integer headNodeId : adjacencyMatrix.keySet())
            {
                for(Integer tailNodeId : adjacencyMatrix.get(headNodeId).keySet())
                {
                    Pair delta = new Pair(
                            positions.get(headNodeId).first() - positions.get(tailNodeId).first(),
                            positions.get(headNodeId).second() - positions.get(tailNodeId).second()
                    );
                    double attractionCoefficient = attractionForce(modulus(delta),coefficient,headNodeId,tailNodeId);
                    Pair newDisplacementHead = new Pair(
                            displacement.get(headNodeId).first() - delta.first()/modulus(delta) * attractionCoefficient,
                            displacement.get(headNodeId).second() - delta.second()/modulus(delta) * attractionCoefficient
                    );
                    displacement.put(headNodeId,newDisplacementHead);
                    Pair newDisplacementTail = new Pair(
                            displacement.get(tailNodeId).first() + delta.first()/modulus(delta) * attractionCoefficient,
                            displacement.get(tailNodeId).second() + delta.second()/modulus(delta) * attractionCoefficient
                    );
                    displacement.put(tailNodeId,newDisplacementTail);
                }
            }
            /// Apply changes
            for(Integer v : getNodes())
            {
                Pair newPosition = new Pair(
                        positions.get(v).first() + displacement.get(v).first()/modulus(displacement.get(v))*temperature,
                        positions.get(v).second()+ displacement.get(v).second()/modulus(displacement.get(v))*temperature
                );

                positions.put(v,newPosition);
            }
            temperature *= coolingCoefficient;
        }
        ArrayList<Triple> ret = new ArrayList<>();
        for(Integer id : positions.keySet())
        {
            Pair newPosition = positions.get(id);
            newPosition = new Pair(
                    Math.min(maxWidth-radius,Math.max(newPosition.first(),radius)),
                    Math.min(maxHeight-radius,Math.max(newPosition.second(),radius))
            );
            ret.add(new Triple( id,(int)newPosition.first(),(int)newPosition.second()));
        }
        return ret;
    }

}
