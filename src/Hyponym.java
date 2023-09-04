/**
 * The type Hyponym.
 */
public class Hyponym implements Comparable<Hyponym> {
    private String word;
    private int count;

    /**
     * Instantiates a new Hyponym.
     *
     * @param word the word
     */
    public Hyponym(String word) {
        this.word = word;
        this.count = 1;
    }

    /**
     * Gets word.
     *
     * @return the word
     */
    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return word + "(" + count + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Hyponym) {
            Hyponym convertedObj = (Hyponym) obj;
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

    /**
     * Increase count.
     */
    public void increaseCount() {
        this.count++;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(Hyponym o) {
        if (count - o.count == 0) {
            return word.compareTo(o.word);
        }
        return count - o.count;

    }
}
