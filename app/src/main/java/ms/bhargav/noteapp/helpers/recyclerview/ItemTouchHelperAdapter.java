package ms.bhargav.noteapp.helpers.recyclerview;

/**
 * Created by Bhargav on 7/10/2016.
 */
public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

    void onDrop(int fromPosition, int toPosition);
}
