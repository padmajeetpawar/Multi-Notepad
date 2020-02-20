package com.padmajeet.multinotepad.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.padmajeet.multinotepad.R;
import com.padmajeet.multinotepad.models.Note;
import com.padmajeet.multinotepad.utility.OnItemClick;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {
    private List<Note> notes;
    private Context context;
    private OnItemClick onItemClick;

    public NoteListAdapter(Context context, List<Note> notes, OnItemClick onItemClick) {
        this.context = context;
        this.notes = notes;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Note note = notes.get(position);
        if (note != null) {
            holder.tvtitle.setText(note.getTitle());
            holder.tvLastSavedDate.setText(note.getLastSavedDate());
            //holder.tvNote.setText(note.getNoteText());

            if(note.getNoteText().length() > 80){
                String new_Note = note.getNoteText().substring(0, 79);
                new_Note = new_Note.concat("...");
                holder.tvNote.setText(new_Note);
            }
            else {
                holder.tvNote.setText(note.getNoteText());
            }

            holder.llListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClick != null) {
                        onItemClick.onItemClick(note);
                    }
                }
            });

            holder.llListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClick != null) {
                        onItemClick.onItemClick(note);
                    }
                }
            });

            holder.llListItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onItemClick != null) {
                        onItemClick.onItemLongClick(note);
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llListItem;
        TextView tvtitle;
        TextView tvLastSavedDate;
        TextView tvNote;

        ViewHolder(View itemView) {
            super(itemView);
            llListItem = itemView.findViewById(R.id.ll_list_item);
            tvtitle = itemView.findViewById(R.id.tv_title);
            tvLastSavedDate = itemView.findViewById(R.id.tv_saved_date);
            tvNote = itemView.findViewById(R.id.tv_note);
        }
    }
}
