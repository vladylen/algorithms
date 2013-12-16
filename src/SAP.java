public class SAP {
    private Digraph diGraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        diGraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return getGraphInfo(v, w, "length");
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return getGraphInfo(v, w, "ancestor");
    }

    private int getGraphInfo(int v, int w, String type) {
        int root = getRoot(v);
        int ancestor;
        int size;

        GraphInfo rootGraphInfo;
        GraphInfo directGraphInfo;

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(diGraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(diGraph, w);

        directGraphInfo = getDirectPath(v, w, bfsV, bfsW);
        rootGraphInfo = getPathTroughRoot(root, bfsV, bfsW);

        if (directGraphInfo.size > -1 && ((rootGraphInfo.size > -1 && directGraphInfo.size < rootGraphInfo.size) || (directGraphInfo.size > -1 && rootGraphInfo.size < 0))) {
            size = directGraphInfo.size;
            ancestor = directGraphInfo.ancestor;
        } else {
            size = rootGraphInfo.size;
            ancestor = rootGraphInfo.ancestor;
        }

        if (type.equals("length")) {
            return size;
        } else {
            return ancestor;
        }
    }

    private GraphInfo getDirectPath(int v, int w, BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
        GraphInfo directGraphInfo = new GraphInfo();

        if (bfsV.hasPathTo(w)) {
            directGraphInfo.shortestPath = bfsV.pathTo(w);
            directGraphInfo.size = getSize(directGraphInfo.shortestPath);
            directGraphInfo.ancestor = w;
        } else if (bfsW.hasPathTo(v)) {
            directGraphInfo.shortestPath = bfsW.pathTo(v);
            directGraphInfo.size = getSize(directGraphInfo.shortestPath);
            directGraphInfo.ancestor = v;
        }

        return directGraphInfo;
    }

    private GraphInfo getPathTroughRoot(int root, BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
        if (bothHasPathTo(bfsV, bfsW, root)) {
            GraphInfo rootGraphInfoV = getPathToRoot(root, bfsW, bfsV);
            GraphInfo rootGraphInfoW = getPathToRoot(root, bfsV, bfsW);

            if (rootGraphInfoV.size > -1 && rootGraphInfoV.size < rootGraphInfoW.size) {
                return rootGraphInfoV;
            } else {
                return rootGraphInfoW;
            }
        }

        return new GraphInfo();
    }

    private GraphInfo getPathToRoot(int root, BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
        int additionalSize;
        Iterable<Integer> pathToRoot = bfsW.pathTo(root);
        additionalSize = 0;

        GraphInfo rootGraphInfo = new GraphInfo();
        for (Integer vertex : pathToRoot) {
            if (bfsV.hasPathTo(vertex)) {
                rootGraphInfo.shortestPath = bfsV.pathTo(vertex);
                rootGraphInfo.size = getSize(rootGraphInfo.shortestPath) + additionalSize;
                rootGraphInfo.ancestor = vertex;
                break;
            }
            additionalSize++;
        }

        return rootGraphInfo;
    }

    private class GraphInfo {
        public Iterable<Integer> shortestPath = null;
        public int size = -1;
        public int ancestor = -1;
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
        boolean test = true;

        if (test) {
            args = new String[1];
            args[0] = "C:\\Users\\vlsh\\Dropbox\\Algorithms\\wordnet\\digraph5.txt";
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
