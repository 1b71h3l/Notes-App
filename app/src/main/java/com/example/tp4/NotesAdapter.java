package com.example.tp4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private final List<Note> mData;
    private OnNoteLongClickListener onNoteLongClickListener;
    private OnNoteModifyClickListener onNoteModifyClickListener;
    private Context context;

    public NotesAdapter(List<Note> notes, Context context ,OnNoteLongClickListener onNoteLongClickListener,OnNoteModifyClickListener onNoteModifyClickListener) {
        this.mData = notes;
        this.context = context;
        this.onNoteLongClickListener = onNoteLongClickListener;
        this.onNoteModifyClickListener = onNoteModifyClickListener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new NoteViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(NoteViewHolder holder, @SuppressLint("RecyclerView") int position)
    {   holder.setItem(mData.get(position));
        holder.modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = mData.get(position);
                //Toast.makeText(v.getContext(),"edit mee ",Toast.LENGTH_LONG).show();
                onNoteModifyClickListener.showModifyDialog(note, position);
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),mData.get(position).description,Toast.LENGTH_LONG).show();
                Note note = mData.get(position);
                Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra("title", note.getTitre());
                intent.putExtra("content", note.description);
                intent.putExtra("date", note.getDate());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(v.getContext(),"Hi",Toast.LENGTH_LONG).show();
                onNoteLongClickListener.onNoteLongClick(position);
                return true;
            }
        });
    }
    @Override
    public int getItemCount() {
        return mData.size();}
    public interface OnNoteLongClickListener {
        void onNoteLongClick(int position);
    }

    public interface OnNoteModifyClickListener {
        void  showModifyDialog(Note note ,int position);
    }
}
