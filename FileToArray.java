import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

    /**
     * Class used to read a file into an array
     * Produces a list of words from the named file. Words in the file are
     * organised such that there is a single word per line.
     * 
     * Since the file contains a copyright notice,
     * the beginning of the data should be marked by the word 'START'
     */

public class FileToArray {

    private final static String START_LABEL = "START";

    public static String[] read(String filename) throws IOException, FileNotFoundException {    
        BufferedReader buffer = new BufferedReader(new FileReader(filename));        

        String line = buffer.readLine();
        while (! line.equals(START_LABEL) ) {
            line = buffer.readLine();
        }

        List<String> words = new ArrayList<String>();
        line = buffer.readLine();
        
        while (line !=null) {
            words.add(line.trim());
            line = buffer.readLine();
        }
        buffer.close();
        String[] arrayResult = new String[words.size()];
        arrayResult = words.toArray(arrayResult);
        return arrayResult;
    }

}
