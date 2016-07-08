package ms.bhargav.noteapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;

import java.util.Date;

import io.realm.Realm;

public class NoteActivity extends AppCompatActivity implements NoteView {
    private Dialog addNoteDialog;
    private NoteGridAdapter adapter;
    private NotePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        presenter = ((NoteApplication) getApplication()).getPresenter();
        presenter.takeView(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNoteDialog();
            }
        });
        RecyclerView listNote = (RecyclerView) findViewById(R.id.list_note);
        assert listNote != null;
        listNote.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new NoteGridAdapter(presenter.getData());
        listNote.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.takeView(null);
    }

    private void showAddNoteDialog() {
        addNoteDialog = new Dialog(this);
        addNoteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        addNoteDialog.setTitle(R.string.title_add_new_note);
        addNoteDialog.setContentView(R.layout.layout_dialog_add_note);
        addNoteDialog.show();
        FloatingActionButton saveButton = (FloatingActionButton) addNoteDialog.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addNoteDialog.isShowing()) {
                    String title =
                            ((EditText) addNoteDialog.findViewById(R.id.edit_note_title)).getText()
                                    .toString();
                    String body =
                            ((EditText) addNoteDialog.findViewById(R.id.body)).getText().toString();
                    long createTime = System.currentTimeMillis();
                    NoteViewModel noteViewModel = new NoteViewModel(
                            body, title, new Date(createTime), new Date(createTime)
                    );
                    presenter.saveNote(noteViewModel);
                    adapter.setData(presenter.getData());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public void publishNote(NoteViewModel model) {

    }

    @Override
    public Realm getRealm() {
        return ((NoteApplication) getApplication()).getRealm();
    }
}
