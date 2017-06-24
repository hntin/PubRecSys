package uit.prs.model;

import java.util.List;

/**
 *
 * @author Huynh Ngoc Tin
 */
public class Paper {
    private int id;
    private String title;
    private String abs;
    private int year;
    private String venue;
    private List<Author> authorList;
    private List<Paper> refPaperList;

    //<editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public List<Paper> getRefPaperList() {
        return refPaperList;
    }

    public void setRefPaperList(List<Paper> refPaperList) {
        this.refPaperList = refPaperList;
    }
    //</editor-fold>  
}
