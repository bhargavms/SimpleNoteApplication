package ms.bhargav.noteapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Bhargav on 7/7/2016.
 */
public class NoteApplication extends Application {
    private NotePresenter presenter;
    private Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        presenter = new NotePresenter();
        initRealm();
    }

    public NotePresenter getPresenter() {
        return presenter;
    }

    private void initRealm() {
        realm = Realm.getInstance(new RealmConfiguration.Builder(this).build());
    }

    public Realm getRealm() {
        return this.realm;
    }
}
