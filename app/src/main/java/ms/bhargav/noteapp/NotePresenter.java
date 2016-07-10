package ms.bhargav.noteapp;

import android.support.annotation.NonNull;

import java.util.List;

import io.realm.Realm;
import ms.bhargav.noteapp.db.models.NoteRealmModel;

public class NotePresenter {
    private NoteView view;
    private Realm realm;
    private List<NoteViewModel> models;
    private NoteViewModel backup;
    private Integer backupPosition;

    public void takeView(@NonNull NoteView view) {
        this.view = view;
        realm = view.getRealm();
        models = Mapper.map(realm.where(NoteRealmModel.class).findAll());
    }

    public void detach() {
        this.view = null;
    }

    public void saveNote(NoteViewModel noteViewModel) {
        realm.beginTransaction();
        realm.copyToRealm(Mapper.map(noteViewModel));
        realm.commitTransaction();
        models.add(noteViewModel);
    }

    public void editNote(NoteViewModel newNote, int position) {
        models.set(position, newNote);
        saveAll();
    }


    private void saveAll() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.copyToRealm(Mapper.mapReverse(models));
        realm.commitTransaction();
    }


    public List<NoteViewModel> getData() {
        return models;
    }

    public void onItemRemoved(NoteViewModel item, int position) {
        backup = item;
        backupPosition = position;
        saveAll();
    }

    public boolean performUndo() {
        if (backup != null) {
            models.add(backupPosition, backup);
            saveAll();
            return true;
        }
        return false;
    }

    public void onOrderChanged() {
        saveAll();
    }

    /*private List<NoteRealmModel> getListFromRealmResults(RealmList<NoteRealmModel> realmList) {
        List<NoteRealmModel> list = new ArrayList<>(realmList.size());
        for (NoteRealmModel model : realmList) {
            list.add(model);
        }
        return list;
    }*/

}
