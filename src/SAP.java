public class SAP {
    private Digraph diGraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        diGraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) throws IndexOutOfBoundsException {
        return getGraphInfo(v, w, "length");
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) throws IndexOutOfBoundsException {
        return getGraphInfo(v, w, "ancestor");
    }

    private int getGraphInfo(int v, int w, String type) throws IndexOutOfBoundsException {
        if (v < 0 || v >= diGraph.V() || w < 0 || w >= diGraph.V()) {
            throw new IndexOutOfBoundsException();
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(diGraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(diGraph, w);

        GraphInfo graphInfo = processGraph(bfsV, bfsW);

        if (type.equals("length")) {
            return graphInfo.length;
        } else {
            return graphInfo.ancestor;
        }
    }

    private int getGraphInfo(Iterable<Integer> v, Iterable<Integer> w, String type) throws IndexOutOfBoundsException {
        for (int value : v) {
            if (value < 0 || value >= diGraph.V()) {
                throw new IndexOutOfBoundsException();
            }
        }
        for (int value : w) {
            if (value < 0 || value >= diGraph.V()) {
                throw new IndexOutOfBoundsException();
            }
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(diGraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(diGraph, w);

        GraphInfo graphInfo = processGraph(bfsV, bfsW);

        if (type.equals("length")) {
            return graphInfo.length;
        } else {
            return graphInfo.ancestor;
        }
    }

    private GraphInfo processGraph(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
        GraphInfo graphInfo = new GraphInfo();

        for (int i = 0; i < diGraph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int tmpLength = bfsV.distTo(i) + bfsW.distTo(i);

                if (graphInfo.length == -1 || (tmpLength < graphInfo.length)) {
                    graphInfo.length = tmpLength;
                    graphInfo.ancestor = i;
                }
            }
        }

        return graphInfo;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return getGraphInfo(v, w, "length");
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return getGraphInfo(v, w, "ancestor");
    }

    private class GraphInfo {
        private int length = -1;
        private int ancestor = -1;

        public int getLength(){
            return length;
        }

        public int getAncestor(){
            return ancestor;
        }
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        boolean test = false;

        if (test) {
            args = new String[1];
            args[0] = "C:\\Users\\vlsh\\Dropbox\\Algorithms\\wordnet\\digraph5.txt";
        }

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
