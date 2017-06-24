package uit.prs.arnetminer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import uit.prs.model.Author;
import uit.prs.model.Paper;
import uit.prs.utility.common.TextFileUtility;

/**
 *
 * @author Huynh Ngoc Tin
 */
public class AMinerDataSet {

    public static HashMap<Integer, Author> authors = new HashMap<>();
    public static HashMap<Integer, Paper> papers = new HashMap<>();

    /**
     * Reading file of Authors
     *
     * @param fileName
     */
    public void readFile_AMinerAuthor(String fileName) {
        StringBuffer strBuffer = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            Reader reader = new InputStreamReader(fis, "UTF8");
            BufferedReader bufferReader = new BufferedReader(reader);
            Author author = null;
            String arrTemp[] = null;
            String line = bufferReader.readLine();
            while (line != null) {
                // A new author
                if (line.contains("#index ")) {
                    author = new Author();
                    author.setAuthorId(Integer.parseInt(line.split(" ")[1]));
                }
                if (line.contains("#n ")) {
                    author.setAuthorName(line.substring(3));
                }
                if (line.contains("#a ")) {
                    author.setAffiliation(line.substring(3));
                }
                if (line.contains("#pc ")) {
                    arrTemp = line.split(" ");
                    if (arrTemp != null && arrTemp.length == 2) {
                        author.setPubCount(Integer.parseInt(line.split(" ")[1]));
                    }
                }
                if (line.contains("#cn ")) {
                    arrTemp = line.split(" ");
                    if (arrTemp != null && arrTemp.length == 2) {
                        author.setCiteCount(Integer.parseInt(line.split(" ")[1]));
                    }
                }
                if (line.contains("#hi ")) {
                    arrTemp = line.split(" ");
                    if (arrTemp != null && arrTemp.length == 2) {
                        author.sethIndex(Float.parseFloat(line.split(" ")[1]));
                    }
                }
                if (line.contains("#pi ")) {
                    arrTemp = line.split(" ");
                    if (arrTemp != null && arrTemp.length == 2) {
                        author.setpIndex(Float.parseFloat(line.split(" ")[1]));
                    }
                }
                if (line.contains("#t ")) {
                    arrTemp = line.substring(3).split(";");
                    ArrayList keywords = new ArrayList();
                    for (int i = 0; i < arrTemp.length; i++) {
                        keywords.add(arrTemp[i]);
                    }
                    author.setInterestKeywords(keywords);
                    authors.put(author.getAuthorId(), author);
                }

                line = bufferReader.readLine();
            }

            System.out.println("DONE");

            bufferReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reading file of Papers
     *
     * @param fileName
     */
    public void readFile_AMinerPaper(String fileName) {
        StringBuffer strBuffer = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            Reader reader = new InputStreamReader(fis, "UTF8");
            BufferedReader bufferReader = new BufferedReader(reader);
            String arrTemp1[] = null;
            String arrTemp2[] = null;
            String line = bufferReader.readLine();
            while (line != null) {
                // Start a new paper
                if (line.contains("#index ")) {
                    Paper paper = null;
                    arrTemp1 = line.split(" ");
                    if (arrTemp1 != null && arrTemp1.length == 2) {
                        int paperID = Integer.parseInt(arrTemp1[1]);
                        paper = papers.get(paperID);
                        if (paper == null) {
                            paper = new Paper();
                            paper.setId(Integer.parseInt(arrTemp1[1]));
                            // Adding the current paper to the collection of papers
                            papers.put(paper.getId(), paper);
                        }
                    }

                    line = bufferReader.readLine();
                    while (line != null && !line.equalsIgnoreCase("")) {
                        //<editor-fold defaultstate="collapsed" desc="Adding authors for current paper">
                        if (line.contains("#@ ")) {
                            // list of authors
                            ArrayList authorsOfPaper = new ArrayList();
                            arrTemp1 = line.substring(3).split(";");

                            // list fo associated aff
                            line = bufferReader.readLine();
                            if (line.contains("#o ")) {
                                arrTemp2 = line.substring(3).split(";");
                            }

                            for (int i = 0; i < arrTemp1.length; i++) {
                                String authorName = arrTemp1[i];
                                int authorID = this.getAuthorID(authorName, arrTemp2[i]);
                                Author author = authors.get(authorID);
                                // Adding authors of a paper
                                if (author != null) {
                                    authorsOfPaper.add(author);
                                } else {
                                    author = new Author();
                                    author.setAuthorId(-1);
                                    author.setAuthorName(authorName);
                                    authorsOfPaper.add(author);
                                    // Adding a new author to the collection of authors
                                    authors.put(author.getAuthorId(), author);
                                }

                                // Updating papers of an author
                                if (author.getPaperList() != null) {
                                    author.getPaperList().add(paper);
                                } else {
                                    ArrayList pList = new ArrayList();
                                    pList.add(paper);
                                    author.setPaperList(pList);
                                }
                            }

                            // Updating authors of a paper
                            paper.setAuthorList(authorsOfPaper);
                        }
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="Updating other information for current paper">
                        if (line.contains("#* ")) {
                            paper.setTitle(line.substring(3));
                        }

                        if (line.contains("#t ")) {
                            arrTemp1 = line.split(" ");
                            if (arrTemp1 != null && arrTemp1.length == 2) {
                                paper.setYear(Integer.parseInt(arrTemp1[1]));
                            }
                        }

                        if (line.contains("#c ")) {
                            paper.setVenue(line.substring(3));
                        }

                        if (line.contains("#! ")) {
                            paper.setAbs(line.substring(3));
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Adding references for current paper">
                        if (line.contains("#% ")) {
                            int refID = Integer.parseInt(line.split(" ")[1]);
                            Paper refPaper = papers.get(refID);
                            if (refPaper == null) { // not exist
                                refPaper = new Paper();
                                refPaper.setId(refID);
                            }
                            paper.getRefPaperList().add(refPaper);
                        }
                        //</editor-fold>
                        line = bufferReader.readLine();
                    }
                }

                line = bufferReader.readLine();
            }

            bufferReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("DONE");
    }

    public static void readFile_AMinerCoAuthor(String fileName) {
    }

    public int getAuthorID(String inAuthorName, String inAffName) {
        int authorID = -1;
        if (inAffName.equalsIgnoreCase("-")) {
            inAffName = "";
        }

        for (Integer id : authors.keySet()) {
            Author author = authors.get(id);
            String authorName = author.getAuthorName();
            String affName = author.getAffiliation();
            if (authorName.equalsIgnoreCase(inAuthorName) && affName.contains(inAffName)) {
                authorID = id;
                break;
            }
        }

        return authorID;
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
