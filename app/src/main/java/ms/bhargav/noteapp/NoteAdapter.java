package ms.bhargav.noteapp;

import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ms.bhargav.noteapp.helpers.recyclerview.ItemTouchHelperAdapter;
import ms.bhargav.noteapp.helpers.recyclerview.ItemTouchHelperViewHolder;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteItemViewHolder>
        implements ItemTouchHelperAdapter {
    private List<NoteViewModel> noteViewModels;
    private OnItemChangedListener mListener;
    private int mItemViewType;

    public static final int VIEW_TYPE_GRID = 214;
    public static final int VIEW_TYPE_LIST = 213;

    public NoteAdapter(List<NoteViewModel> noteViewModels,
                       OnItemChangedListener listener) {
        this.noteViewModels = noteViewModels;
        mListener = listener;
        mItemViewType = VIEW_TYPE_LIST;
    }

    @Override
    public NoteAdapter.NoteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (mItemViewType == VIEW_TYPE_GRID) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_note, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_note, parent, false);
        }
        return new NoteItemViewHolder(view, mListener);
    }

    public void setItemViewType(int itemViewType) {
        mItemViewType = itemViewType;
    }


    @Override
    public int getItemViewType(int position) {
        return mItemViewType;
    }

    @Override
    public void onBindViewHolder(NoteAdapter.NoteItemViewHolder holder, final int position) {
        NoteViewModel item = noteViewModels.get(position);
        holder.item = item;
        if (mItemViewType == VIEW_TYPE_LIST) {
            holder.title.setText(item.getTitle());
            holder.text.setText(item.getText());
            holder.modifiedTime.setText(item.getModifiedTime());
            holder.backgroundColor = Color.TRANSPARENT;
        } else {
            holder.title.setText(item.getTitle());
            holder.text.setText(item.getText());
            holder.modifiedTime.setText(item.getModifiedTime());
            CardView cardView = (CardView) holder.itemView;
            holder.backgroundColor = ColorGenerator.getRandomMaterialColor(cardView.getContext());
            cardView.setCardBackgroundColor(holder.backgroundColor);
        }
    }

    @Override
    public int getItemCount() {
        return noteViewModels.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(noteViewModels, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        NoteViewModel item = noteViewModels.get(position);
        noteViewModels.remove(position);
        notifyItemRemoved(position);
        // on item removed triggers save all, so do save all after removing item from noteviewmodels
        mListener.onItemRemoved(item, position);
    }

    @Override
    public void onDrop(int fromPosition, int toPosition) {
        mListener.onOrderChanged();
    }

    public static class NoteItemViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder, View.OnTouchListener, View.OnClickListener {
        public final TextView title;
        public final TextView text;
        public final TextView modifiedTime;
        public NoteViewModel item;
        public int backgroundColor;
        private OnItemChangedListener onItemChangedListener;

        public NoteItemViewHolder(View itemView, OnItemChangedListener startDragListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title);
            text = (TextView) itemView.findViewById(R.id.text);
            modifiedTime = (TextView) itemView.findViewById(R.id.modified_time);
            onItemChangedListener = startDragListener;
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(backgroundColor);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                onItemChangedListener.onStartDrag(this);
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == itemView.getId())
                onItemChangedListener.onItemEdit(item, getAdapterPosition());
        }
    }
}
