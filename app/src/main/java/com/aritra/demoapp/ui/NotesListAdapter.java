/*
 * Copyright (c) 2019 Aritra Nath. All rights reserved.
 */

package com.aritra.demoapp.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aritra.demoapp.R;
import com.aritra.demoapp.activities.SecondaryActivity;
import com.aritra.demoapp.model.Note;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {
    private Context context;
    private List<Note> noteList;

    public NotesListAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public NotesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_note, parent, false);
        return new ViewHolder(view, context);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {#onBindViewHolder(ViewHolder, int)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull NotesListAdapter.ViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.subject.setText(note.getSubject());
        holder.content.setText(note.getContent());
        holder.dateAdded.setText(String.format("Created on: %s at %s",
                note.getDateAdded(), note.getTimeAdded()));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        TextView subject;
        TextView content;
        TextView dateAdded;
        public int id;

        ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            subject = itemView.findViewById(R.id.card_subject);
            content = itemView.findViewById(R.id.card_content);
            dateAdded = itemView.findViewById(R.id.card_date_time_added);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                Note note = noteList.get(position);
                context.startActivity(new Intent(context, SecondaryActivity.class)
                .putExtra("ID", note.getId()));
            });
        }

        @Override
        public boolean onLongClick(View view) {
            Toast.makeText(context, "Adapter position + " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
