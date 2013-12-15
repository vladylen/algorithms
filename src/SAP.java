import java.lang.reflect.Field;

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
        Iterable<Integer> shortestPath = null;
        int ancestor = -1;
        int size = -1;
        int root = getRoot(v);

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(diGraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(diGraph, w);

        // Direct path from v to w or from w to v
        if (bfsV.hasPathTo(w)) {
            shortestPath = bfsV.pathTo(w);
            size = getSize(shortestPath);
            ancestor = w;
        } else if (bfsW.hasPathTo(v)) {
            shortestPath = bfsW.pathTo(v);
            size = getSize(shortestPath);
            ancestor = v;
        }

        if (shortestPath == null) {
            if (bothHasPathTo(bfsV, bfsW, root)) {
                Iterable<Integer> pathV2Top = bfsV.pathTo(root);
                int additionalSize = 0;

                for (Integer vertex : pathV2Top) {
                    if (bfsW.hasPathTo(vertex)) {
                        shortestPath = bfsW.pathTo(vertex);
                        size = getSize(shortestPath) + additionalSize;
                        ancestor = vertex;
                        break;
                    }
                    additionalSize++;
                }
            }
        }

        if (type.equals("length")) {
            return size;
        } else {
            return ancestor;
        }
    }

    private int getRoot(int v) {
        for (Integer vertex : diGraph.adj(v)) {
            return getRoot(vertex);
        }

        return v;
    }

    private boolean bothHasPathTo(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW, int root) {
        return bfsV.hasPathTo(root) && bfsW.hasPathTo(root);
    }

    private int getSize(Iterable<Integer> shortestPath) {
        try {
            Field field = shortestPath.getClass().getDeclaredField("N");
            field.setAccessible(true);
            try {
                return field.getInt(shortestPath) - 1;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return -1;
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
        args = new String[1];
        args[0] = "C:\\Users\\Vlad\\IdeaProjects\\Algorithms\\wordnet\\digraph1.txt";
        if (args.length > 0) {
            String argFileName = args[0];

            In in = new In(argFileName);
            Digraph digraph = new Digraph(in);
            SAP sap = new SAP(digraph);

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
            /*
            int N = in.readInt();
            int M = in.readInt();
            for (int i = 0; i < M; i++) {
                int v = in.readInt();
                int w = in.readInt();
                digraph.addEdge(v, w);
            }
            */
        } else {
            StdOut.println("main() - No arguments.");
        }
    }
}
