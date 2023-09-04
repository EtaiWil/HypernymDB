import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Discover hypernym.
 */
public class DiscoverHypernym {
    private static final int NUM_OF_ARGS = 2;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        if (args.length != NUM_OF_ARGS) {
            throw new RuntimeException("don't have 2 args");
        }
        ArrayList<Hypernim> result = new ArrayList<>();
        String fileInput = args[0];
        String lemma = args[1];
        ArrayList<Hypernim> hypernims = CreateHypernymDatabase.getHypernim(fileInput);
        Hyponym check = new Hyponym(lemma);


        for (Hypernim hypernim : hypernims) {
            if (hypernim.getHyponyms().contains((check))) {
                result.add(hypernim);
            }
        }
        Collections.sort(result);
        if (result.isEmpty()) {
            System.out.println("The lemma doesn't appear in the corpus.");
        }
        for (Hypernim hypernim : result) {
            System.out.println(hypernim.getWord() + ":" + "("
                    + hypernim.getHyponyms().get(hypernim.getHyponyms().indexOf(check)).getCount() + ")");
        }
    }

}
