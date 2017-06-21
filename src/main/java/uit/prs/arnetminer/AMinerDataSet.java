package uit.prs.arnetminer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import uit.prs.model.Author;
import uit.prs.utility.common.TextFileUtility;

/**
 *
 * @author Huynh Ngoc Tin
 */
public class AMinerDataSet {

    ArrayList authorList = new ArrayList<>();

    public static void readFile_AMinerAuthor(String fileName) {
        StringBuffer strBuffer = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            Reader reader = new InputStreamReader(fis, "UTF8");
            BufferedReader bufferReader = new BufferedReader(reader);
            Author author = null;
            String line = bufferReader.readLine();
            while (line != null && !line.equals("")) {
                // A new author
                if (line.contains("#index ")) {
                    author = new Author();
                    author.setAuthorId(line.split(" ")[1]);
                }
                if (line.contains("#n ")) {
                    author.setAuthorName("");
                }
                if (line.contains("#a ")) {

                }
                if (line.contains("#pc ")) {

                }
                if (line.contains("#cn ")) {

                }
                if (line.contains("#hi ")) {

                }
                if (line.contains("#pi ")) {

                }
                if (line.contains("#upi ")) {

                }
                if (line.contains("#t ")) {

                }

                line = bufferReader.readLine();
            }

            bufferReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFile_AMinerPaper(String fileName) {

    }

    public static void readFile_AMinerCoAuthor(String fileName) {

    }

    /**
     * portAMinerDataToPRSData PRS Data files: Training & Testing Data Files;
     * Refer to https://github.com/hntin/PRS-Framework.git
     *
     * @param fileAuthor
     * @param filePaper
     * @param fileCoAuthor
     */
    public static void portAMinerDataToPRSData(String fileAuthor_AMiner, String filePaper_AMiner, String fileCoAuthor_AMiner, String fileAuthor_PRS) {
        createFile_InputAuthor(fileAuthor_AMiner, fileAuthor_PRS);
    }

    public static void createFile_Author_Cite_Paper() {
        // AuthorID|||PaperID

    }

    public static void createFile_Author_Paper() {

    }

    public static void createFile_Paper_Cite_Paper() {

    }

    public static void createFile_Paper() {

    }

    /**
     *
     * @param fileAuthor_AMiner
     * @param fileAuthor_PRS
     */
    public static void createFile_InputAuthor(String fileAuthor_AMiner, String fileAuthor_PRS) {
        StringBuffer strBuffer = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream(fileAuthor_AMiner);
            Reader reader = new InputStreamReader(fis, "UTF8");
            BufferedReader bufferReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferReader.readLine()) != null) {
                if (line.contains("#index ")) {
                    String[] strArr = line.split(" ");
                    strBuffer.append(strArr[1]);
                    line = bufferReader.readLine();
                    if (line != null && line.contains("#n ")) {
                        String authorName = line.substring(3);
                        strBuffer.append("|||" + authorName + "\n");
                    }
                }
            }
            bufferReader.close();
            TextFileUtility.writeTextFile(fileAuthor_PRS, strBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
