import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * The type File handler.
 */
public class FileHandler {
    /**
     * Reads file.
     *
     * @param fileName the file name
     * @return  connected string.
     */
    public static String readFile(String fileName) {
        try {

            File myObj = new File(fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(myObj));
            StringBuilder data = new StringBuilder(bufferedReader.readLine());
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                if (s.contains("such") || s.contains("including") || s.contains("especially")
                        || s.contains("which is")) {
                    data.append("\n").append(s);
                }

            }
            return data.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * writeFile.
     *
     * @param fileName the file name
     * @param data     the data
     */
    public static void writeFile(String fileName, String data) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            bufferedWriter.write(data);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}