package ms.bhargav.noteapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Bhargav on 7/7/2016.
 */
public class NoteGridAdapter extends RecyclerView.Adapter<NoteGridAdapter.NoteItemViewHolder> {
    private List<NoteViewModel> noteViewModels;

    public void setData(List<NoteViewModel> modesl) {
        this.noteViewModels = modesl;
    }

    public NoteGridAdapter(List<NoteViewModel> noteViewModels) {
        this.noteViewModels = noteViewModels;
    }

    @Override
    public NoteGridAdapter.NoteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteGridAdapter.NoteItemViewHolder holder, int position) {
        NoteViewModel item = noteViewModels.get(position);
        holder.item = item;
        holder.title.setText(item.getTitle());
        holder.text.setText(item.getText());
//        holder.createTime.setText(item.getCreateTime());
        holder.modifiedTime.setText(item.getModifiedTime());
        CardView cardView = (CardView) holder.itemView;
        cardView.setCardBackgroundColor(ColorGenerator.getRandomMaterialColor(cardView.getContext()));
    }

    @Override
    public int getItemCount() {
        return noteViewModels.size();
    }

    public static class NoteItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView title;
        public final TextView text;
//        public final TextView createTime;
        public final TextView modifiedTime;
        public NoteViewModel item;

        public NoteItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title);
            text = (TextView) itemView.findViewById(R.id.text);
//            createTime = (TextView) itemView.findViewById(R.id.create_time);
            modifiedTime = (TextView) itemView.findViewById(R.id.modified_time);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
