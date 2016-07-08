package ms.bhargav.noteapp.db.models;

import io.realm.RealmObject;

/**
 * Created by Bhargav on 7/7/2016.
 */
public class NoteRealmModel extends RealmObject {
    private String note;
    private String title;
    private long modifiedTime;
    private long createTime;

    public NoteRealmModel(String note, String title, long modifiedTime, long createTime) {
        this.note = note;
        this.title = title;
        this.modifiedTime = modifiedTime;
        this.createTime = createTime;
    }

    public NoteRealmModel() {
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
