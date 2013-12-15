public class SAP {
    private Digraph diGraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        diGraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return graphInfo(v, w, "length");
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return graphInfo(v, w, "ancestor");
    }

    private int graphInfo(int v, int w, String type) {
        Iterable<Integer> directShortestPath;
        int directAncestor = -1;
        int directSize = -1;
        int rootAncestor = -1;
        int rootSize = -1;
        Iterable<Integer> rootShortestPath1;
        int rootAncestor1 = -1;
        int rootSize1 = -1;
        Iterable<Integer> rootShortestPath2;
        int rootAncestor2 = -1;
        int rootSize2 = -1;
        int root = getRoot(v);
        int ancestor;
        int size;

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(diGraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(diGraph, w);

        // Direct path from v to w or from w to v
        if (bfsV.hasPathTo(w)) {
            directShortestPath = bfsV.pathTo(w);
            directSize = getSize(directShortestPath);
            directAncestor = w;
        } else if (bfsW.hasPathTo(v)) {
            directShortestPath = bfsW.pathTo(v);
            directSize = getSize(directShortestPath);
            directAncestor = v;
        }

        // Path throw root vertex
        if (bothHasPathTo(bfsV, bfsW, root)) {
            Iterable<Integer> pathV2Top = bfsV.pathTo(root);
            int additionalSize = 0;

            for (Integer vertex : pathV2Top) {
                if (bfsW.hasPathTo(vertex)) {
                    rootShortestPath1 = bfsW.pathTo(vertex);
                    rootSize1 = getSize(rootShortestPath1) + additionalSize;
                    rootAncestor1 = vertex;
                    break;
                }
                additionalSize++;
            }

            Iterable<Integer> pathW2Top = bfsW.pathTo(root);
            additionalSize = 0;

            for (Integer vertex : pathW2Top) {
                if (bfsV.hasPathTo(vertex)) {
                    rootShortestPath2 = bfsV.pathTo(vertex);
                    rootSize2 = getSize(rootShortestPath2) + additionalSize;
                    rootAncestor2 = vertex;
                    break;
                }
                additionalSize++;
            }

            if (rootSize1 > -1 && rootSize1 < rootSize2) {
                rootSize = rootSize1;
                rootAncestor = rootAncestor1;
            } else {
                rootSize = rootSize2;
                rootAncestor = rootAncestor2;
            }
        }

        if (directSize > -1 && directSize < rootSize) {
            size = directSize;
            ancestor = directAncestor;
        } else {
            size = rootSize;
            ancestor = rootAncestor;
        }

        if (type.equals("length")) {
            return size;
        } else {
            return ancestor;
        }
    }


    private int getRoot(int v) {
        SET<Integer> visited = new SET<Integer>();
        return getRoot(v, visited);
    }

    private int getRoot(int v, SET<Integer> visited) {
        for (Integer vertex : diGraph.adj(v)) {
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                return getRoot(vertex, visited);
            } else {
                return v;
            }
        }

        return v;
    }

    private boolean bothHasPathTo(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW, int root) {
        return bfsV.hasPathTo(root) && bfsW.hasPathTo(root);
    }

    private int getSize(Iterable<Integer> shortestPath) {
        int size = -1;
        for (Integer ignored : shortestPath) {
            size++;
        }

        return size;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return 1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return 1;
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        boolean test = false;

        if (test) {
            args = new String[1];
            args[0] = "C:\\Users\\Vlad\\IdeaProjects\\Algorithms\\wordnet\\digraph1.txt";
        }

        /*
        StdOut.println("ancestor(3, 2) = " + sap.ancestor(3, 2));
        StdOut.println("length(3, 2) = " + sap.length(3, 2));
        StdOut.println("ancestor(3, 0) = " + sap.ancestor(3, 0));
        StdOut.println("length(3, 0) = " + sap.length(3, 0));
        StdOut.println("ancestor(3, 11) = " + sap.ancestor(3, 11));
        StdOut.println("length(3, 11) = " + sap.length(3, 11));
        StdOut.println("ancestor(5, 11) = " + sap.ancestor(5, 11));
        StdOut.println("length(5, 11) = " + sap.length(5, 11));
        StdOut.println("ancestor(5, 5) = " + sap.ancestor(5, 5));
        StdOut.println("length(5, 5) = " + sap.length(5, 5));
        StdOut.println("ancestor(5, 6) = " + sap.ancestor(5, 6));
        StdOut.println("length(5, 6) = " + sap.length(5, 6));
        */
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
