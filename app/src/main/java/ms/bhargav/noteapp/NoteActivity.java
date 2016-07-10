package ms.bhargav.noteapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import java.util.Date;

import io.realm.Realm;
import ms.bhargav.noteapp.helpers.recyclerview.SimpleItemTouchHelperCallback;

public class NoteActivity extends AppCompatActivity implements NoteView,
        OnItemChangedListener {
    private Dialog addNoteDialog;
    private NoteAdapter adapter;
    private NotePresenter presenter;
    private RecyclerView listNote;

    private ItemTouchHelper mListItemTouchHelper;
    private boolean isList;
    private DividerItemDecoration dividerItemDecoration;

    private LinearLayoutManager listLayoutManager;
    private StaggeredGridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        presenter = ((NoteApplication) getApplication()).getNotePresenter();
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
        listNote = (RecyclerView) findViewById(R.id.list_note);
        assert listNote != null;
        adapter = new NoteAdapter(presenter.getData(), this);

        gridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        listLayoutManager = new LinearLayoutManager(this);
        dividerItemDecoration = new DividerItemDecoration(this);
        listNote.setAdapter(adapter);
        ItemTouchHelper.Callback callback1 = new SimpleItemTouchHelperCallback(adapter);
        mListItemTouchHelper = new ItemTouchHelper(callback1);
        mListItemTouchHelper.attachToRecyclerView(listNote);
        setList();
    }

    private void setGrid() {
        listNote.setLayoutManager(gridLayoutManager);
        listNote.removeItemDecoration(dividerItemDecoration);
        adapter.setItemViewType(NoteAdapter.VIEW_TYPE_GRID);
        isList = false;
        adapter.notifyDataSetChanged();
    }


    private void setList() {
        listNote.setLayoutManager(listLayoutManager);
        listNote.addItemDecoration(dividerItemDecoration);
        adapter.setItemViewType(NoteAdapter.VIEW_TYPE_LIST);
        isList = true;
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list:
                setList();
                return true;
            case R.id.action_grid:
                setGrid();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
        ((NoteApplication) getApplication()).releaseNotePresenter();
    }

    private void showNote(NoteViewModel model, final int position) {
        addNoteDialog = new Dialog(this);
        addNoteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addNoteDialog.setContentView(R.layout.layout_dialog_add_note);
        addNoteDialog.show();
        addNoteDialog.setCancelable(true);
        final EditText titleView = (EditText) addNoteDialog.findViewById(R.id.edit_note_title);
        final EditText bodyView = (EditText) addNoteDialog.findViewById(R.id.body);
        titleView.setText(model.getTitle());
        bodyView.setText(model.getText());
        FloatingActionButton saveButton = (FloatingActionButton) addNoteDialog.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addNoteDialog.isShowing()) {
                    String title =
                            titleView.getText().toString();
                    String body =
                            bodyView.getText().toString();
                    long createTime = System.currentTimeMillis();
                    NoteViewModel noteViewModel = new NoteViewModel(
                            body, title, new Date(createTime), new Date(createTime)
                    );
                    presenter.editNote(noteViewModel, position);
                    adapter.notifyItemChanged(position);
                    addNoteDialog.dismiss();
                }
            }
        });
    }


    private void showAddNoteDialog() {
        addNoteDialog = new Dialog(this);
        addNoteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        addNoteDialog.setTitle(R.string.title_add_new_note);
        addNoteDialog.setContentView(R.layout.layout_dialog_add_note);
        addNoteDialog.show();
        addNoteDialog.setCancelable(true);
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
//                    adapter.setData(presenter.getData());
                    adapter.notifyItemInserted(presenter.getData().size() - 1);
                    addNoteDialog.dismiss();
                }
            }
        });
    }

    @Override
    public Realm getRealm() {
        return ((NoteApplication) getApplication()).getRealm();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mListItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onItemRemoved(NoteViewModel item, final int position) {
        presenter.onItemRemoved(item, position);
        Snackbar.make(findViewById(R.id.coordinator_layout),
                R.string.message_items_removed, Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (presenter.performUndo()) {
                            adapter.notifyItemInserted(position);
                        }
                    }
                })
                .show();
    }

    @Override
    public void onItemEdit(NoteViewModel item, int position) {
        showNote(item, position);
    }

    @Override
    public void onOrderChanged() {
        presenter.onOrderChanged();
    }
}
