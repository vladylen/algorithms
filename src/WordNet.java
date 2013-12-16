public class WordNet {
    private SAP sap;
    private Queue<Node> hypernyms = new Queue<Node>();
    private SeparateChainingHashST<String, Sunset> sunsetsHashST = new SeparateChainingHashST<String, Sunset>(10);

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        readSunsets(synsets);
        readHypernyms(hypernyms);

        Digraph digraph = new Digraph(sunsetsHashST.size());
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

    private void readSunsets(String synsets) {
        In inS = new In(synsets);
        while (!inS.isEmpty()) {
            String line = inS.readLine();
            String[] fields = line.split(",");

            int id = Integer.parseInt(fields[0]);
            String sunset = fields[1];
            String description = fields[2];

            Sunset item = new Sunset(id, sunset, description);
            sunsetsHashST.put(id + sunset, item);
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
        public int id;
        public String sunset;
        public String description;

        public Sunset(int id, String sunset, String description) {
            this.id = id;
            this.sunset = sunset;
            this.description = description;
        }
    }

    // the set of nouns (no duplicates), returned as an Iterable
    public Iterable<String> nouns() {
        return new Stack<String>();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return false;
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
            args[0] = "C:\\Users\\vlsh\\Dropbox\\Algorithms\\wordnet\\synsets15.txt";
            args[1] = "C:\\Users\\vlsh\\Dropbox\\Algorithms\\wordnet\\hypernymsPath15.txt";
        }

        WordNet wordnet = new WordNet(args[0], args[1]);
    }
}
