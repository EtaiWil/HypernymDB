import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The type Hypernim.
 */
public class Hypernim implements Comparable<Hypernim> {
    private String word;
    private ArrayList<Hyponym> hyponyms;


    /**
     * Instantiates a new Hypernim.
     *
     * @param word the word
     */
    public Hypernim(String word) {
        this.word = word;
        this.hyponyms = new ArrayList<>();
    }

    //print as described.
    @Override
    public String toString() {
        String string = word + ":";
        for (Hyponym hyponym : hyponyms) {
            string += hyponym.toString() + ",";
        }
        return string.substring(0, string.length() - 1) + "\n";
    }

    /**
     * Add hyponym.
     *
     * @param hyponym the hyponym
     */
    public void addHyponym(String hyponym) {
        Hyponym objHyponym = new Hyponym(hyponym);
        Comparator<Hyponym> hyponymComparator = new Comparator<Hyponym>() {
            @Override
            public int compare(Hyponym o1, Hyponym o2) {
                return o1.getWord().compareTo(o2.getWord());
            }

        };
        int indexOfHypoym = Collections.binarySearch(hyponyms, objHyponym, hyponymComparator);
        //if exists increase and if not adding new.
        if (indexOfHypoym >= 0) {

            hyponyms.get(indexOfHypoym).increaseCount();
        } else {
            hyponyms.add(-indexOfHypoym - 1, objHyponym);
        }
    }

    /**
     * return word.
     *
     * @return the word
     */
    public String getWord() {
        return word;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Hypernim) {
            Hypernim convertedObj = (Hypernim) obj;
            return convertedObj.getWord().equals(getWord());
        }
        return false;
    }

    //don't really care about the has code so just override
    @Override
    public int hashCode() {
        int result = 1;
        return result;
    }


    @Override
    public int compareTo(Hypernim o) {
        return word.compareTo(o.word);
    }

    /**
     * Gets hyponyms.
     *
     * @return the hyponyms
     */
    public ArrayList<Hyponym> getHyponyms() {
        return hyponyms;
    }
}
