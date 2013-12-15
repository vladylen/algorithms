import java.lang.reflect.Field;

public class SAP {
    private Digraph diGraph;
    private int top;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        diGraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {

        return 1;
    }

    private boolean hasPath(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
        return bfsV.hasPathTo(top) && bfsW.hasPathTo(top);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        Iterable<Integer> shortestPath = null;
        int ancestor = -1;
        int size = -1;

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
            if (hasPath(bfsV, bfsW)) {
                Iterable<Integer> pathV2Top = bfsV.pathTo(top);
                int additionalSize = 0;

                for (Integer vertex : pathV2Top) {
                    additionalSize++;
                    if (bfsW.hasPathTo(vertex)) {
                        shortestPath = bfsW.pathTo(vertex);
                        size = getSize(shortestPath) + additionalSize;
                        ancestor = vertex;
                        break;
                    }
                }
            }
        }

        if (shortestPath != null) {
            StdOut.println("shortestPath = " + shortestPath.toString());
            StdOut.println("ancestor = " + ancestor);
            StdOut.println("size = " + size);
        } else {
            StdOut.println("shortestPath LOOSER");
            StdOut.println("ancestor = " + ancestor);
            StdOut.println("size = " + size);
        }

        return ancestor;
    }

    private int getSize(Iterable<Integer> shortestPath) {
        try {
            Field field = shortestPath.getClass().getDeclaredField("N");
            field.setAccessible(true);
            try {
                return field.getInt(shortestPath);
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
            sap.top = 0;


            StdOut.println("sap.ancestor(3, 2) = " + sap.ancestor(3, 2));
            StdOut.println("sap.ancestor(3, 0) = " + sap.ancestor(3, 0));
            StdOut.println("sap.ancestor(3, 10) = " + sap.ancestor(3, 10));
            StdOut.println("sap.ancestor(5, 11) = " + sap.ancestor(5, 11));
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
