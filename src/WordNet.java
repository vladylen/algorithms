public class WordNet {
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
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
    }
}
