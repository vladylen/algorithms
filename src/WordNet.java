public class WordNet {
    private SAP sap;
    private Queue<Node> hypernyms = new Queue<Node>();
    private SeparateChainingHashST<String, Sunset> sunsets = new SeparateChainingHashST<String, Sunset>(16);

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        readSunsets(synsets);
        readHypernyms(hypernyms);

        Digraph digraph = new Digraph(this.sunsets.size());
        for (Node node : this.hypernyms) {
            digraph.addEdge(node.v, node.w);
        }

        sap = new SAP(digraph);
        StdOut.println(sap.length(0, 10));
        StdOut.println(sap.length(1, 10));
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
                    Node node = new Node(v, w);
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

            String[] nouns = sunset.split(" ");

            for (int i = 0; i < nouns.length; i++) {
                Sunset item;

                if (sunsets.contains(nouns[i])) {
                    item = new Sunset(sunsets.get(nouns[i]));
                    item.addSunset(id, sunset, description);
                } else {
                    item = new Sunset(id, sunset, description);
                }

                sunsets.put(nouns[i], item);
            }
        }
    }

    private class Node {
        public int v;
        public int w;

        public Node(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    private class Sunset {
        public Queue<Integer> id = new Queue<Integer>();
        public Queue<String> sunset = new Queue<String>();
        public Queue<String> description = new Queue<String>();

        public Sunset(int id, String sunset, String description) {
            addSunset(id, sunset, description);
        }

        public Sunset(Sunset sunset) {
            this.id = sunset.id;
            this.sunset = sunset.sunset;
            this.description = sunset.description;
        }

        public Sunset addSunset(int id, String sunset, String description) {
            this.id.enqueue(id);
            this.sunset.enqueue(sunset);
            this.description.enqueue(description);

            return this;
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

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return -1;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
// in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return "";
    }

    // for unit testing of this class
    public static void main(String[] args) {
        boolean test = true;

        if (test) {
            args = new String[2];
            args[0] = "C:\\Users\\Vlad\\IdeaProjects\\Algorithms\\wordnet\\synsets15.txt";
            args[1] = "C:\\Users\\Vlad\\IdeaProjects\\Algorithms\\wordnet\\hypernymsPath15.txt";
        }

        WordNet wordnet = new WordNet(args[0], args[1]);

        StdOut.println("isNoun(a) = " + wordnet.isNoun("a"));
        StdOut.println("isNoun(c) = " + wordnet.isNoun("c"));
        StdOut.println("isNoun(zz) = " + wordnet.isNoun("zz"));
        StdOut.println("nouns = " + wordnet.nouns());
    }
}
