package graphpackage;

import datapackage.Bond;
import datapackage.Model;

import java.util.*;

class Graph {
    HashMap<Integer, HashMap<Integer,Integer>> adjacencyMatrix;
    public Graph(Set<Integer> nodes, Set<Integer> bonds)
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
        for(Integer i : nodes)
        {
            adjacencyMatrix.computeIfAbsent(i,k->new HashMap<>());
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
        HashMap<Integer,Pair> ret = new HashMap<>();
        double delta = 2 * Math.PI / getNodes().size();

        List<Integer> nodes = directCentrality();
        int len = nodes.size();
        for(int i = 0; i<len;i++)
        {
            int nodeIndex = i;
            int leftIndex = (nodeIndex - 1 + len)%len;
            int rightIndex = (nodeIndex+1)%len;

            int nodeId = nodes.get(nodeIndex);
            int leftId = nodes.get(leftIndex);
            int rightId= nodes.get(rightIndex);

            if(getWeight(nodeId,leftId) != null || getWeight(nodeId, rightId)!=null) continue;

            while(getWeight(nodeId,leftId)==null && getWeight(nodeId, rightId)==null)
            {
                nodeIndex = (nodeIndex + 1) % len;
                leftIndex = (nodeIndex - 1 + len) % len;
                rightIndex = (nodeIndex+1) % len;
                if(nodeIndex == i) break;

                nodeId = nodes.get(nodeIndex);
                leftId = nodes.get(leftIndex);
                rightId= nodes.get(rightIndex);
            }
            Collections.swap(nodes,i,nodeIndex);
        }
        int i = 0;
        for (Integer id : nodes) {
            double angle = i * delta;

            int x = (int)(centerX + placementRadius * Math.cos(angle));
            int y = (int)(centerY + placementRadius * Math.sin(angle));

            ret.put(id, new Pair(x, y));
            i++;
        }

        return ret;
    }

    private double attractionForce(double distance, double coefficient, int head, int tail)
    {
        return 2*distance * distance / coefficient * normalizeEdge(getWeight(head,tail));
    }

    private double repulsionForce(double distance, double coefficient)
    {
        return coefficient * coefficient / distance;
    }

    private double modulus(Pair pair)
    {
        double mod = Math.sqrt(pair.first()*pair.first() + pair.second()*pair.second());
        return Math.max(mod, 0.00001);
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
                            double overlap = 2*radius + 1 - modulus(delta);
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
                    Math.min(maxWidth-radius-10,Math.max(newPosition.first(),radius+10)),
                    Math.min(maxHeight-radius-10,Math.max(newPosition.second(),radius+10))
            );
            ret.add(new Triple( id,(int)newPosition.first(),(int)newPosition.second()));
        }
        return ret;
    }

    public List<Integer> directCentrality() {
        List<Integer> nodes = new ArrayList<>(getNodes());

        /// Sort based on:
        /// Go from the reverse :
        /// The sum <- of the stream <- of values <- of edges
        /// I.e. the sum of total ratings towards target node.
        nodes.sort((a, b) -> {
            int scoreA = adjacencyMatrix.get(a).values().stream()
                    .mapToInt(w -> 11 - w).sum();
            int scoreB = adjacencyMatrix.get(b).values().stream()
                    .mapToInt(w -> 11 - w).sum();
            return Integer.compare(scoreB, scoreA);
        });

        return nodes;
    }

    public List<Integer> indirectCentrality() {
        List<Integer> nodes = new ArrayList<>(getNodes());
        /// Same logic as above, but on the connected nodes, not on the node itself
        nodes.sort((a,b) -> {
            int scoreA = adjacencyMatrix.get(a).keySet().stream().mapToInt(
                    w -> adjacencyMatrix.get(w).values().stream().mapToInt(v-> 11-v).sum()
            ).sum();
            int scoreB = adjacencyMatrix.get(b).keySet().stream().mapToInt(
                    w -> adjacencyMatrix.get(w).values().stream().mapToInt( v -> 11-v).sum()
            ).sum();
            return Integer.compare(scoreB,scoreA);
        });
        return nodes;
    }

    private int getFurthestFromGroup ( ArrayList<Integer> group , TreeSet<Integer> peopleInGroups)
    {
        if(group.isEmpty())
        {
            for(Integer id : getNodes())
            {
                if(!peopleInGroups.contains(id))
                {
                    return id;
                }
            }
        }
        int addedId = -1;
        int maxDistance = 0;
        for(Integer candidate : getNodes())
        {
            /// If they're already in it, it wouldn't be fun to add them again.
            if(group.contains(candidate) || peopleInGroups.contains(candidate))continue;
            int candidateDistances = 0;
            for(Integer member : group)
            {
                /// Add the sum of distances
                Integer distance = getWeight(member,candidate);
                if(distance == null)
                {
                    /// Disconnected means they should interact
                    candidateDistances+= 100;
                }
                else
                {
                    /// Connected means they should get to know each-other better
                    candidateDistances+= distance;
                }
            }
            /// May the best one win
            if(candidateDistances > maxDistance)
            {
                maxDistance= candidateDistances;
                addedId = candidate;
            }
        }
        return addedId;
    }

    public ArrayList<ArrayList<Integer>> groupPartitions(int groupCount) {
        List<Integer> nodes = new LinkedList<>(directCentrality().reversed());
        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
        TreeSet<Integer> inAGroup = new TreeSet<>();
        /// Remove edge cases
        if(groupCount <= 1 || groupCount >= nodes.size()/2)
        {
            for(Integer id : nodes)
            {
                if(inAGroup.contains(id))continue;
                ArrayList<Integer> added = new ArrayList<>();
                added.add(id);
                inAGroup.add(id);
                int partner = getFurthestFromGroup(added,inAGroup);
                added.add(partner);
                inAGroup.add(partner);
                ret.add(added);
            }
            return ret;
        }

        int peoplePerGroup = nodes.size() / groupCount;
        int peopleLeftOutside = nodes.size() % groupCount;


        /// This is where we put people in groups
        for (int i = 0; i < groupCount; i++) {
            /// Making the group
            ArrayList<Integer> added = new ArrayList<>();
            for (int j = 0; j < peoplePerGroup; j++) {
                /// Adding the furthest from the group
                int id = getFurthestFromGroup(added,inAGroup);
                if(id == -1) break;
                added.add(id);
                inAGroup.add(id);
            }
            /// This is so everybody is included (First size%groupCount got a +1 member)
            if(peopleLeftOutside > 0)
            {
                int id = getFurthestFromGroup(added,inAGroup);
                if(id == -1) break;
                added.add(id);
                inAGroup.add(id);
                peopleLeftOutside--;
            }
            ret.add(added);
        }
        return ret;
    }

    private double computeMean()
    {
        double sum = 0;
        double count = 0;
        TreeSet<String> counted = new TreeSet<>();
        for(Integer head : adjacencyMatrix.keySet())
        {
            for(Integer tail : adjacencyMatrix.get(head).keySet())
            {
                Integer add = getWeight(head,tail);
                if(counted.contains(Math.max(head,tail)+" "+Math.min(head,tail)) || add == null)continue;
                sum += add;
                count++;
                counted.add(Math.max(head,tail)+" "+Math.min(head,tail));
            }
        }
        if(count == 0) return 11;
        return sum/count;
    }

    private double computeVariance()
    {
        double mean = computeMean();
        double sum = 0;
        double count = 0;
        TreeSet<String> counted = new TreeSet<>();
        for(Integer head : adjacencyMatrix.keySet())
        {
            for(Integer tail : adjacencyMatrix.get(head).keySet())
            {
                Integer add = getWeight(head,tail);
                if(counted.contains(Math.max(head,tail)+" "+Math.min(head,tail)) || add == null)continue;
                counted.add(Math.max(head,tail)+" "+Math.min(head,tail));
                sum+=(add-mean)*(add-mean);
                count++;
            }
        }
        if(count == 0 ) return 11;
        return sum/count;
    }

    private double computeStandardDeviation()
    {
        return Math.sqrt(computeVariance());
    }

    private double computeAverageDistanceBetweenGroups(ArrayList<Integer> group1, ArrayList<Integer> group2) {
        if(group1.isEmpty() || group2.isEmpty()) {
            return Double.MAX_VALUE;
        }

        double sum = 0;
        int count = 0;

        for(Integer node1 : group1) {
            for(Integer node2 : group2) {
                Integer weight = getWeight(node1, node2);
                sum += (weight != null) ? weight : 11;
                count++;
            }
        }

        return sum / count;
    }

    public ArrayList<ArrayList<Integer>> clustering() {

        double thresholdDistance = computeMean() + 0.5 * computeStandardDeviation();
        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
        UnionFind groups = new UnionFind(getNodes());
        HashMap<Integer,ArrayList<Integer>> sets = groups.getSets();
        if(thresholdDistance < 10)
        {
            boolean continues;
            do {
                continues=false;
                for (Integer head : sets.keySet())
                {
                    for(Integer tail : sets.keySet())
                    {
                        if(head.compareTo(tail) >= 0 )continue;

                        double distance = computeAverageDistanceBetweenGroups(sets.get(head),sets.get(tail));

                        if(distance < thresholdDistance)
                        {
                            groups.unite(head,tail);
                            continues = true;
                            break;
                        }
                    }
                    if(continues)
                    {
                        break;
                    }
                }
                sets = groups.getSets();
            }while (continues);
        }

        for(Integer set : sets.keySet())
        {
            ret.add(sets.get(set));
        }
        return ret;
    }

    public ArrayList<Triple> dijkstra(int id1, int id2) {
        if (id1 == id2) {
            return new ArrayList<>();
        }

        HashMap<Integer, Integer> distances = new HashMap<>();
        HashMap<Integer, Integer> parent = new HashMap<>();
        HashSet<Integer> visited = new HashSet<>();
        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        for (Integer id : getNodes()) {
            distances.put(id, Integer.MAX_VALUE);
        }
        distances.put(id1, 0);
        heap.add(new int[]{id1, 0});

        while (!heap.isEmpty()) {
            int[] current = heap.poll();
            int u = current[0];
            int distU = current[1];

            // Skip if already visited
            if (visited.contains(u)) continue;
            visited.add(u);

            if (u == id2) break; // reached destination

            for (Integer v : adjacencyMatrix.get(u).keySet()) {
                if (visited.contains(v)) continue;

                Integer weight = getWeight(u, v);
                if(weight == null) continue;

                int newDist = distU + weight;

                if (newDist < distances.get(v)) {
                    distances.put(v, newDist);
                    parent.put(v, u);
                    heap.add(new int[]{v, newDist});
                }
            }
        }

        if (!parent.containsKey(id2)) {
            return new ArrayList<>();
        }

        ArrayList<Triple> path = new ArrayList<>();
        Integer node = id2;
        while (parent.containsKey(node)) {
            int prev = parent.get(node);
            Integer weight = getWeight(prev, node);
            if(weight == null) return new ArrayList<>();
            path.add( new Triple(prev, weight, node));
            node = prev;
        }
        Collections.reverse(path);
        // If path has only one edge (direct connection), return empty
        if (path.size() == 1 ) {
            return new ArrayList<>();
        }

        return path;
    }

    public double groupRating() {
        double sum = 0;
        for(Integer head : adjacencyMatrix.keySet())
        {
            for(Integer tail : adjacencyMatrix.get(head).keySet())
            {
                Integer add = getWeight(head,tail);
                if(add != null)
                {
                    sum+=(11-add);
                }
            }
        }
        double count = getNodes().size();
        double max = count * (count-1) * 5.0;
        /// Double counting
        return (sum/2)/max*10;
    }

    private ArrayList<Integer> kCoreDecomposition(int k)
    {
        double cutOff = computeMean() - 0.5 * computeStandardDeviation();
        HashMap<Integer,Integer> deg = new HashMap<>();
        Queue<Integer> core = new LinkedList<>();
        HashSet<Integer> ret = new HashSet<>();
        for(Integer head : getNodes())
        {
             int count = 0;
             for(Integer tail : adjacencyMatrix.get(head).keySet())
             {
                 Integer weight =getWeight(head,tail);

                 if(weight!=null && weight >= cutOff)
                 {
                     count++;
                 }
             }
             deg.put(head,count);
             if(deg.get(head) < k) core.add(head);
             ret.add(head);
        }

         while (!core.isEmpty())
         {
             Integer target = core.poll();
             if(ret.contains(target))
             {
                 ret.remove(target);

                 for(Integer adjacent : adjacencyMatrix.get(target).keySet())
                 {
                     Integer weight = getWeight(target,adjacent);
                     if(ret.contains(adjacent))
                     {
                         if(weight != null && weight >= cutOff)
                         {
                             deg.put(adjacent,deg.get(adjacent)-1);
                             if(deg.get(adjacent) < k) core.add(adjacent);
                         }
                     }
                 }
             }
         }
         return new ArrayList<>(ret);
    }

    public ArrayList<Integer> maxKCoreDecomposition() {
        ArrayList<Integer> ret = new ArrayList<>(kCoreDecomposition(1));
        ArrayList<Integer> copy = new ArrayList<>(kCoreDecomposition(2));
        int k = 3;
        while (!copy.isEmpty())
        {
            ret = copy;
            k++;
            copy = new ArrayList<>(kCoreDecomposition(k));
        }
        ret.sort((a,b) -> {
            int scoreA = adjacencyMatrix.get(a).values().stream()
                    .mapToInt(w -> 11 - w).sum();
            int scoreB = adjacencyMatrix.get(b).values().stream()
                    .mapToInt(w -> 11 - w).sum();
            return Integer.compare(scoreB, scoreA);
        });
        return ret;
    }
}
