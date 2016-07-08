package ms.bhargav.noteapp;

import io.realm.Realm;

/**
 * Created by Bhargav on 7/7/2016.
 */
public interface NoteView {
    void publishNote(NoteViewModel model);

    Realm getRealm();
}
