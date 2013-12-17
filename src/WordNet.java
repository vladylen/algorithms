import java.util.Iterator;

public class WordNet {
    private SAP sap;
    private Queue<HypernymNode> hypernyms = new Queue<HypernymNode>();
    private SeparateChainingHashST<String, Sunset> sunsets = new SeparateChainingHashST<String, Sunset>(16);
    private SeparateChainingHashST<Integer, String> sunsetIds = new SeparateChainingHashST<Integer, String>(16);

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        readSunsets(synsets);
        readHypernyms(hypernyms);

        Digraph digraph = new Digraph(this.sunsets.size());
        for (HypernymNode node : this.hypernyms) {
            digraph.addEdge(node.v, node.w);
        }

        sap = new SAP(digraph);
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
                    item.addSunset(id, sunset, description);
                } else {
                    item = new Sunset(id, sunset, description);
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

            for (Item itemA : sunsetA) {
                for (Item itemB : sunsetB) {
                    int tmpLength = sap.length(itemA.id, itemB.id);

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

            for (Item itemA : sunsetA) {
                for (Item itemB : sunsetB) {
                    int tmpLength = sap.length(itemA.id, itemB.id);

                    if (length == -1 || tmpLength < length) {
                        length = tmpLength;
                        int ancestorId = sap.ancestor(itemA.id, itemB.id);

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
        public int v;
        public int w;

        public HypernymNode(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    private class Sunset implements Iterable<Item> {
        public Queue<Item> items = new Queue<Item>();

        public Sunset(int id, String sunset, String description) {
            addSunset(id, sunset, description);
        }

        public Sunset(Sunset sunset) {
            this.items = sunset.items;
        }

        public Sunset addSunset(int id, String sunset, String description) {
            this.items.enqueue(new Item(id, sunset, description));

            return this;
        }

        public Iterator<Item> iterator() {
            return items.iterator();
        }
    }

    private class Item {
        public int id;
        public String sunset;
        public String description;

        public Item(int id, String sunset, String description) {
            this.id = id;
            this.sunset = sunset;
            this.description = description;
        }
    }

    // for unit testing of this class
    public static void main(String[] args) {
        boolean test = false;

        if (test) {
            args = new String[2];
            args[0] = "C:\\Users\\vlsh\\Dropbox\\Algorithms\\wordnet\\synsets.txt";
            args[1] = "C:\\Users\\vlsh\\Dropbox\\Algorithms\\wordnet\\hypernyms.txt";
        }

        WordNet wordnet = new WordNet(args[0], args[1]);

        if (test) {
            /*
            StdOut.println("isNoun(a) = " + wordnet.isNoun("a"));
            StdOut.println("isNoun(c) = " + wordnet.isNoun("c"));
            StdOut.println("isNoun(zz) = " + wordnet.isNoun("zz"));
            StdOut.println("nouns = " + wordnet.nouns());
            StdOut.println("distance(a, c) = " + wordnet.distance("a", "c"));
            StdOut.println("sap(a, c) = " + wordnet.sap("a", "c"));
            */
            /**/
            StdOut.println("distance(Black_Plague, black_marlin) = " + wordnet.distance("Black_Plague", "black_marlin"));
            StdOut.println("distance(American_water_spaniel, histology) = " + wordnet.distance("American_water_spaniel", "histology"));
            StdOut.println("distance(Brown_Swiss, barrel_roll) = " + wordnet.distance("Brown_Swiss", "barrel_roll"));
            StdOut.println("distance(municipality, region) = " + wordnet.distance("municipality", "region"));
            StdOut.println("sap(municipality, region) = " + wordnet.sap("municipality", "region"));
            /**/
        }
    }
}
