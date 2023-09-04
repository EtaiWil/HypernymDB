
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * The type Create hypernym database.
 */
public class CreateHypernymDatabase {
    private static final int NUM_OF_ARGS = 2;
    //all the pattern that I'm looking.
    /**
     * The constant npPattern.
     */

    private static Pattern npPattern = Pattern.compile("<np>([^<]*)</np>");

    /**
     * The Regex 1.
     */
    private static String regex1 = "<np>([^>]+)</np> ?,? such as <np>([^>]+)</np> "
            + "((,? ?<np>([^>]+)</np> ?)*,? ?((and)|(or))? ?<np>([^>]+)</np>)?";

    /**
     * The Regex 2.
     */
    private static String regex2 = "such <np>([^>]+)</np> as <np>([^>]+)</np> "
            + "((,? ?<np>([^>]+)</np> ?)*,? ?((and)|(or))? ?<np>([^>]+)</np>)?";
    /**
     * The Regex 3.
     */
    private static String regex3 = "<np>([^>]+)</np> ?,? including <np>([^>]+)</np> "
            + "((,? ?<np>([^>]+)</np> ?)*,? ?((and)|(or))? ?<np>([^>]+)</np>)?";
    /**
     * The Regex 4.
     */
    private static String regex4 = "<np>([^>]+)</np> ?,? especially <np>([^>]+)</np> "
            + "((,? ?<np>([^>]+)</np> ?)*,? ?((and)|(or))? ?<np>([^>]+)</np>)?";
    /**
     * The Regex 5.
     */
    private static String regex5 = "<np>([^>]+)</np> ?,? which is((( an example)|( a kind)|( a class))? of)?"
            + "+ <np>([^>]+)</np>";

    // return compiled version of a regular expression into the pattern.
    /**
     * The constant p1.
     */
    public static final Pattern PATTERN1 = Pattern.compile(regex1);
    /**
     * The constant p2.
     */
    public static final Pattern PATTERN2 = Pattern.compile(regex2);
    /**
     * The constant p3.
     */
    public static final Pattern PATTERN3 = Pattern.compile(regex3);
    /**
     * The constant p4.
     */
    public static final Pattern PATTERN4 = Pattern.compile(regex4);
    /**
     * The constant p5.
     */
    public static final Pattern PATTERN5 = Pattern.compile(regex5);

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        if (args.length != NUM_OF_ARGS) {
            throw new RuntimeException("don't have 2 args");
        }
        String fileInput = args[0];
        String fileOutput = args[1];
        String data = "";
        ArrayList<Hypernim> hypernims = getHypernim(fileInput);
        for (Hypernim hypernim : hypernims) {

            if (hypernim.getHyponyms().size() >= 3) {
                data += hypernim.toString();
            }
        }
        FileHandler.writeFile(fileOutput, data);
    }

    /**
     * Cut np string. remove all the "<> and <np/>".
     *
     * @param string the string
     * @return the string
     */
    public static String cutNp(String string) {
        String str = string.substring(4, string.length() - 5);
        return str;
    }

    /**
     * Gets hypernim.
     *
     * @param fileINput the file input
     * @return the hypernim
     */
    public static ArrayList<Hypernim> getHypernim(String fileINput) {

        ArrayList<String> relations = new ArrayList<>();
        ArrayList<Hypernim> hypernims = new ArrayList<>();
        try {
            Stream<Path> paths = Files.walk(Paths.get(fileINput)).filter(Files::isRegularFile);
            Matcher m;
            //find all the patterns.
            for (Object currentFile : paths.toArray()) {
                String fileText = FileHandler.readFile(String.valueOf(currentFile)).toLowerCase();
                m = PATTERN1.matcher(fileText);
                while (m.find()) {
                    relations.add(m.group());
                }
                m = PATTERN2.matcher(fileText);
                while (m.find()) {
                    relations.add(m.group());
                }
                m = PATTERN3.matcher(fileText);
                while (m.find()) {
                    relations.add(m.group());
                }
                m = PATTERN4.matcher(fileText);
                while (m.find()) {
                    relations.add(m.group());
                }
                m = PATTERN5.matcher(fileText);
                while (m.find()) {
                    relations.add(m.group());
                }
            }

            for (String relation : relations) {
                //running on all the relations and separates the words.
                m = npPattern.matcher(relation);
                m.find();
                String strHypernim = m.group();
                strHypernim = cutNp(strHypernim);
                Hypernim hypernim = new Hypernim(strHypernim);

                int indexOfHypernim = Collections.binarySearch(hypernims, hypernim);
                if (indexOfHypernim >= 0) {
                    hypernim = hypernims.get(indexOfHypernim);
                } else {
                    hypernims.add(-indexOfHypernim - 1, hypernim);
                }
                while (m.find()) {
                    String hyponym = cutNp(m.group());
                    hypernim.addHyponym(hyponym);
                }
            }
            //sort as  described in the exercise
            Collections.sort(hypernims);
            for (Hypernim hypernim : hypernims) {
                Collections.sort(hypernim.getHyponyms());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return hypernims;
    }
}



