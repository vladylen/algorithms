public class Outcast {
    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int distanceMax = 0;
        int outcastNumber = -1;

        for (int i = 0; i < nouns.length; i++) {
            int distance = 0;

            for (int j = 0; j < nouns.length; j++) {
                distance += wordnet.distance(nouns[i], nouns[j]);
            }

            if (outcastNumber == -1 || (distance > distanceMax)) {
                distanceMax = distance;
                outcastNumber = i;
            }
        }

        return nouns[outcastNumber];
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        boolean test = false;

        if (test) {
            args = new String[3];
            args[0] = "C:\\Users\\vlsh\\Dropbox\\Algorithms\\wordnet\\synsets.txt";
            args[1] = "C:\\Users\\vlsh\\Dropbox\\Algorithms\\wordnet\\hypernyms.txt";
            args[2] = "C:\\Users\\vlsh\\Dropbox\\Algorithms\\wordnet\\outcast8b.txt";
        }

        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
