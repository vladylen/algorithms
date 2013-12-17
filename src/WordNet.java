import java.util.Iterator;

public class WordNet {
    private SAP sap;
    private boolean[] marked;
    private boolean[] grey;
    private SET<Integer> uniqueSunsets = new SET<Integer>();
    private Queue<HypernymNode> hypernyms = new Queue<HypernymNode>();
    private SeparateChainingHashST<String, Sunset> sunsets = new SeparateChainingHashST<String, Sunset>(16);
    private SeparateChainingHashST<Integer, String> sunsetIds = new SeparateChainingHashST<Integer, String>(16);

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) throws IllegalArgumentException {
        readSunsets(synsets);
        readHypernyms(hypernyms);

        Digraph digraph = new Digraph(this.sunsets.size());
        for (HypernymNode node : this.hypernyms) {
            digraph.addEdge(node.v, node.w);
        }

        // Check root uniqueness
        int root = -1;
        int count = 0;
        for (int v = 0; v < digraph.V(); ++v) {
            // Count number of vertices with edges (not root vertices).
            if (digraph.adj(v).iterator().hasNext()) {
                count++;
            }
        }

        int rootCount = uniqueSunsets.size() - count;
        if (rootCount != 1) {
            throw new java.lang.IllegalArgumentException();
        }

        for (int v : this.uniqueSunsets) {
            if (!digraph.adj(v).iterator().hasNext()) {
                root = v;
            }
        }

        Digraph reverse = digraph.reverse();
        marked = new boolean[reverse.V()];
        grey = new boolean[reverse.V()];
        grey[root] = true;

        if (dfs(reverse, root)) {
            throw new java.lang.IllegalArgumentException();
        }

        sap = new SAP(digraph);
    }

    private boolean dfs(Digraph G, int v) {
        marked[v] = true;

        for (int w : G.adj(v)) {
            if (grey[w]) {
                return true;
            }

            if (!marked[w]) {
                grey[w] = true;
                boolean cycle = dfs(G, w);
                grey[w] = false;

                if (cycle) {
                    return cycle;
                }
            }
        }

        return false;
    }

    private void readHypernyms(String hypernymsFile) {
        In inH = new In(hypernymsFile);

        while (!inH.isEmpty()) {
            String line = inH.readLine();
            String[] fields = line.split(",");

            int v = -1;
            for (int i = 0; i < fields.length; i++) {
                int w = -1;
                if (i == 0) {
                    v = Integer.parseInt(fields[i]);
                } else {
                    w = Integer.parseInt(fields[i]);
                }

                if (w > -1) {
                    HypernymNode node = new HypernymNode(v, w);
                    uniqueSunsets.add(v);
                    uniqueSunsets.add(w);
                    hypernyms.enqueue(node);
                }
            }
        }
    }

    private void readSunsets(String synsetFile) {
        In inS = new In(synsetFile);
        while (!inS.isEmpty()) {
            String line = inS.readLine();
            String[] fields = line.split(",");

            int id = Integer.parseInt(fields[0]);
            String sunset = fields[1];
            String description = fields[2];

            sunsetIds.put(id, sunset);
            String[] nouns = sunset.split(" ");

            for (int i = 0; i < nouns.length; i++) {
                Sunset item;

                if (sunsets.contains(nouns[i])) {
                    item = new Sunset(sunsets.get(nouns[i]));
                    item.addSunset(id);
                } else {
                    item = new Sunset(id);
                }

                sunsets.put(nouns[i], item);
            }
        }
    }

    // the set of nouns (no duplicates), returned as an Iterable
    public Iterable<String> nouns() {
        return sunsets.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return sunsets.contains(word);
    }

    // is the word a WordNet noun?
    private Sunset getNoun(String word) {
        return sunsets.get(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) throws IllegalArgumentException {
        int length = -1;

        if (isNoun(nounA) && isNoun(nounB)) {
            Sunset sunsetA = getNoun(nounA);
            Sunset sunsetB = getNoun(nounB);

            for (int itemA : sunsetA) {
                for (int itemB : sunsetB) {
                    int tmpLength = sap.length(itemA, itemB);

                    if (length == -1 || tmpLength < length) {
                        length = tmpLength;
                    }
                }
            }

            return length;
        } else {
            throw new IllegalArgumentException();
        }
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) throws IllegalArgumentException {
        String ancestor = "";

        if (isNoun(nounA) && isNoun(nounB)) {
            Sunset sunsetA = getNoun(nounA);
            Sunset sunsetB = getNoun(nounB);
            int length = -1;

            for (int itemA : sunsetA) {
                for (int itemB : sunsetB) {
                    int tmpLength = sap.length(itemA, itemB);

                    if (length == -1 || tmpLength < length) {
                        length = tmpLength;
                        int ancestorId = sap.ancestor(itemA, itemB);

                        ancestor = sunsetIds.get(ancestorId);
                    }
                }
            }

            return ancestor;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private class HypernymNode {
        private int v;
        private int w;

        public HypernymNode(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    private class Sunset implements Iterable<Integer> {
        private Queue<Integer> ids = new Queue<Integer>();

        public Sunset(int id) {
            addSunset(id);
        }

        public Sunset(Sunset sunset) {
            this.ids = sunset.ids;
        }

        public Sunset addSunset(int id) {
            this.ids.enqueue(id);

            return this;
        }

        public Iterator<Integer> iterator() {
            return ids.iterator();
        }
    }

    // for unit testing of this class
    public static void main(String[] args) {
        boolean test = false;

        if (test) {
            args = new String[2];
            args[0] = "C:\\Users\\vlsh\\Dropbox\\Algorithms\\wordnet\\synsets.txt";
            args[1] = "C:\\Users\\vlsh\\Dropbox\\Algorithms\\wordnet\\hypernyms.txt";
            /**/
             args[0] = "C:\\Users\\vlsh\\Dropbox\\Algorithms\\wordnet\\synsets3.txt";
             args[1] = "C:\\Users\\vlsh\\Dropbox\\Algorithms\\wordnet\\hypernymsInvalidCycle.txt";
             /**/
        }

        WordNet wordnet = new WordNet(args[0], args[1]);

        if (test) {
            StdOut.println("distance(Black_Plague, black_marlin) = " + wordnet.distance("Black_Plague", "black_marlin"));
            StdOut.println("distance(American_water_spaniel, histology) = " + wordnet.distance("American_water_spaniel", "histology"));
            StdOut.println("distance(Brown_Swiss, barrel_roll) = " + wordnet.distance("Brown_Swiss", "barrel_roll"));
            StdOut.println("distance(municipality, region) = " + wordnet.distance("municipality", "region"));
            StdOut.println("sap(municipality, region) = " + wordnet.sap("municipality", "region"));
        }
    }
}
