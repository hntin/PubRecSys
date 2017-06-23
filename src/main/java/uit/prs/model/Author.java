package uit.prs.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Huynh Ngoc Tin
 */
public class Author implements Serializable {
    private int authorId;
    private String authorName;
    private String authorType;
    private String affiliation;
    private int pubCount;
    private int citeCount;
    private float hIndex;
    private float pIndex;
    private List<String> interestKeywords;
    private List<Paper> paperList;

   //<editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public int getAuthorId() {
        return authorId;
    }
    
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
    
    public String getAuthorName() {
        return authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    
    public String getAuthorType() {
        return authorType;
    }
    
    public void setAuthorType(String authorType) {
        this.authorType = authorType;
    }
    
    public String getAffiliation() {
        return affiliation;
    }
    
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }
    
    public List<Paper> getPaperList() {
        return paperList;
    }
    
    public void setPaperList(List<Paper> paperList) {
        this.paperList = paperList;
    }
    
    public int getPubCount() {
        return pubCount;
    }

    public void setPubCount(int pubCount) {
        this.pubCount = pubCount;
    }

    public int getCiteCount() {
        return citeCount;
    }

    public void setCiteCount(int citeCount) {
        this.citeCount = citeCount;
    }

    public float gethIndex() {
        return hIndex;
    }

    public void sethIndex(float hIndex) {
        this.hIndex = hIndex;
    }

    public float getpIndex() {
        return pIndex;
    }

    public void setpIndex(float pIndex) {
        this.pIndex = pIndex;
    }

    public List<String> getInterestKeywords() {
        return interestKeywords;
    }

    public void setInterestKeywords(List<String> interestKeywords) {
        this.interestKeywords = interestKeywords;
    }
        
    //</editor-fold>
}