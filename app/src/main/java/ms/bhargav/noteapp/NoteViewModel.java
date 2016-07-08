package ms.bhargav.noteapp;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

/**
 * Created by Bhargav on 7/7/2016.
 */
public class NoteViewModel {
    private String text;
    private String title;
    private String createTime;
    private String modifiedTime;
    private Date createDate;
    private Date modifiedDate;

    public NoteViewModel(String text, String title, Date createDate, Date modifiedDate) {
        this.text = text;
        this.title = title;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        PrettyTime prettyTime = Configuration.getPrettyTime();
        prettyTime.setReference(new Date(System.currentTimeMillis()));
        this.createTime = prettyTime.format(createDate);
        this.modifiedTime = prettyTime.format(modifiedDate);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
