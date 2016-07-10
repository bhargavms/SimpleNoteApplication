package ms.bhargav.noteapp;

import ms.bhargav.noteapp.helpers.recyclerview.OnStartDragListener;

/**
 * Created by Bhargav on 7/10/2016.
 */
public interface OnItemChangedListener extends OnStartDragListener {
    void onItemRemoved(NoteViewModel item, int position);

    void onOrderChanged();

    void onItemEdit(NoteViewModel item, int position);
}
