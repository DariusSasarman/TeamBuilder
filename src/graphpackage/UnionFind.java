package graphpackage;

import java.util.*;

public class UnionFind {

    private HashMap<Integer,UnionNode> sets;

    public class UnionNode {
        private final int key;
        private int rank;
        private UnionNode parent;

        public UnionNode(int key)
        {
            this.key = key;
            this.rank= 1;
            this.parent = null;
        }

        public UnionNode findSet()
        {
            if(this.parent == null) return this;
            UnionNode root = this.parent.findSet();
            this.parent = root;
            return root;
        }

        public int getKey() {
            return key;
        }
    }

    public UnionFind(Set<Integer> elements)
    {
        this.sets = new HashMap<>();
        for(Integer id : elements)
        {
            this.sets.put(id,new UnionNode(id));
        }
    }

    private void unite(UnionNode x, UnionNode y)
    {
        UnionNode xR = x.findSet();
        UnionNode yR = y.findSet();
        if(sameGroup(x,y))return;
        if(xR.rank == yR.rank)
        {
            yR.parent = xR;
            xR.rank++;
        }
        if(xR.rank > yR.rank)
        {
            yR.parent = xR;
        }
        if(yR.rank > xR.rank)
        {
            xR.parent= yR;
        }
    }

    public void unite(int x, int y)
    {
        unite(sets.get(x),sets.get(y));
    }

    private boolean sameGroup(UnionNode x, UnionNode y)
    {
        return x.findSet() == y.findSet();
    }

    public boolean sameGroup(int x, int y)
    {
        return sets.get(x).findSet() == sets.get(y).findSet();
    }

    public HashMap<Integer,ArrayList<Integer>> getSets()
    {
        HashMap<Integer,ArrayList<Integer>> ret = new HashMap<>();
        for(Integer element : sets.keySet())
        {
            UnionNode set = sets.get(element).findSet();
            ret.computeIfAbsent(set.getKey(), k -> new ArrayList<>());
            ret.get(set.getKey()).add(element);
        }
        return ret;
    }
}
