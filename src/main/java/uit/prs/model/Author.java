package uit.prs.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Huynh Ngoc Tin
 */
public class Author implements Serializable {
    private String authorId;
    private String authorName;
    private String authorType;
    private String affiliation;
    private List<Paper> paperList;

   //<editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public String getAuthorId() {
        return authorId;
    }
    
    public void setAuthorId(String authorId) {
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
//</editor-fold>
        
}