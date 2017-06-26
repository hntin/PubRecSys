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
    public static HashMap<Integer, HashMap<Integer, Integer>> coAuthors = new HashMap<>();

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
            int count = 0;
            String line = bufferReader.readLine();
            count++;
            while (line != null) {
                System.out.println("Doing line" + count);
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
                    count++;
                    while (line != null && !line.equalsIgnoreCase("")) {
                        //<editor-fold defaultstate="collapsed" desc="Adding authors for current paper">
                        if (line.contains("#@ ")) {
                            // list of authors
                            ArrayList authorsOfPaper = new ArrayList();
                            arrTemp1 = line.substring(3).split(";");

                            // list fo associated aff
                            line = bufferReader.readLine();
                            count++;
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
                            if (paper.getRefPaperList() != null) {
                                paper.getRefPaperList().add(refPaper);
                            } else {
                                List<Paper> refPaperList = new ArrayList<Paper>();
                                refPaperList.add(refPaper);
                                paper.setRefPaperList(refPaperList);
                            }
                        }
                        //</editor-fold>
                        line = bufferReader.readLine();
                        count++;
                    }
                }

                line = bufferReader.readLine();
                count++;
            }

            bufferReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("DONE");
    }

    /**
     * Reading file of coAuthors
     *
     * @param fileName
     */
    public static void readFile_AMinerCoAuthor(String fileName) {
        StringBuffer strBuffer = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            Reader reader = new InputStreamReader(fis, "UTF8");
            BufferedReader bufferReader = new BufferedReader(reader);
            String arrTemp[] = null;
            String line = bufferReader.readLine();
            while (line != null) {
                arrTemp = line.split("\t");
                if (arrTemp != null && (arrTemp.length == 3)) {
                    int author1 = Integer.parseInt(arrTemp[0].substring(arrTemp[0].indexOf("#") + 1));
                    int author2 = Integer.parseInt(arrTemp[1]);
                    int numOfCollocation = Integer.parseInt(arrTemp[2]);
                    HashMap<Integer, Integer> coAuthorHM = coAuthors.get(author1);
                    if (coAuthors.get(author1) == null) {
                        coAuthorHM = new HashMap<>();
                        coAuthorHM.put(author2, numOfCollocation);
                    } else {
                        if (coAuthorHM.get(author2) == null) {
                            coAuthorHM.put(author2, numOfCollocation);
                        }
                    }
                    coAuthors.put(author1, coAuthorHM);
                }

                line = bufferReader.readLine();
            }
            System.out.println("DONE");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
     * Paper.csv 
     * Paper ID|||Paper title|||Paper's abstract|||Published year
     * @param file_Paper_PRS
     */
    public static void createFile_Paper(String file_Paper_PRS) {
        StringBuilder str = new StringBuilder();
        for (int id : papers.keySet()) {
            Paper p = papers.get(id);
            if (p != null) {
                str.append(p.getId() + "|||");
                str.append(p.getTitle() + "|||");
                str.append(p.getAbs() + "|||");
                str.append(p.getYear() + "\n");
            }
        }
        TextFileUtility.writeTextFile(file_Paper_PRS, str.toString());
    }

    /**
     * Author_Paper.csv 
     * Author ID|||Paper ID written by this author
     * @param file_AuthorPaper_PRS
     */
    public static void createFile_Author_Paper(String file_AuthorPaper_PRS) {
        StringBuilder str = new StringBuilder();
        for (int id : authors.keySet()) {
            Author author = authors.get(id);
            if (author != null) {
                List<Paper> paperList = author.getPaperList();
                if (paperList != null && paperList.size() > 0) {
                    for (Paper p : paperList) {
                        str.append(id + "|||" + p.getId() + "\n");
                    }
                }
            }
        }
        TextFileUtility.writeTextFile(file_AuthorPaper_PRS, str.toString());
    }

    /**
     * Paper_Cite_Paper.csv Paper ID|||Paper ID cited by this paper
     *
     * @param file_PaperCitePaper_PRS
     */
    public static void createFile_Paper_Cite_Paper(String file_PaperCitePaper_PRS) {
        StringBuilder str = new StringBuilder();
        for (int id : papers.keySet()) {
            Paper paper = papers.get(id);
            if (paper != null) {
                List<Paper> refList = paper.getRefPaperList();
                if (refList != null && refList.size() > 0) {
                    for (Paper p : refList) {
                        str.append(id + "|||" + p.getId() + "\n");
                    }
                }
            }
        }
        TextFileUtility.writeTextFile(file_PaperCitePaper_PRS, str.toString());
    }

    /**
     * Author_Cite_Paper.csv Author ID|||Paper ID cited by this author|||Year
     * when the citation happens
     *
     * @param file_AuthorCitePaper_PRS
     */
    public static void createFile_Author_Cite_Paper(String file_AuthorCitePaper_PRS) {
        StringBuilder str = new StringBuilder();
        for (int idAuthor : authors.keySet()) {
            Author author = authors.get(idAuthor);
            if (author != null) {
                List<Paper> paperList = author.getPaperList();
                if (paperList != null && paperList.size() > 0) {
                    for (Paper p : paperList) {
                        if (p != null) {
                            List<Paper> refList = p.getRefPaperList();
                            if (refList != null) {
                                for (Paper pRef : refList) {
                                    if (pRef != null) {
                                        str.append(idAuthor + "|||" + pRef.getId() + "|||" + p.getYear() + "\n");
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        TextFileUtility.writeTextFile(file_AuthorCitePaper_PRS, str.toString());
    }

    /**
     * Target_Researcher.csv Author ID|||Author name
     *
     * @param file_Author_PRS
     */
    public static void createFile_Author(String file_Author_PRS) {
        StringBuilder str = new StringBuilder();
        for (int id : authors.keySet()) {
            Author author = authors.get(id);
            if (author != null) {
                str.append(id + "|||" + author.getAuthorName() + "\n");
            }
        }
        TextFileUtility.writeTextFile(file_Author_PRS, str.toString());
    }

    /**
     * Ground_Truth.csv
     * Author ID|||Paper ID of relevant paper|||Weight of relevancy
     * @param file_GroundTruth_PRS 
     */
    public static void createFile_GroundTruth(String file_GroundTruth_PRS) {
        StringBuilder str = new StringBuilder();
        try {

            TextFileUtility.writeTextFile(file_GroundTruth_PRS, str.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
