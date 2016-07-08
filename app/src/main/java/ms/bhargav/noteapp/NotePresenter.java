package ms.bhargav.noteapp;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import ms.bhargav.noteapp.db.models.NoteRealmModel;

/**
 * Created by Bhargav on 7/7/2016.
 */
public class NotePresenter {
    private NoteView view;
    private Realm realm;

    public void takeView(NoteView view) {
        this.view = view;
        realm = view.getRealm();
    }

    public void saveNote(NoteViewModel noteViewModel) {
        realm.beginTransaction();
        realm.copyToRealm(Mapper.map(noteViewModel));
        realm.commitTransaction();
    }

    public List<NoteViewModel> getData() {
        RealmQuery<NoteRealmModel> query = realm.where(NoteRealmModel.class);
        return Mapper.map(query.findAll());
    }

    private List<NoteRealmModel> getListFromRealmResults(RealmList<NoteRealmModel> realmList) {
        List<NoteRealmModel> list = new ArrayList<>(realmList.size());
        for (NoteRealmModel model : realmList) {
            list.add(model);
        }
        return list;
    }

}
