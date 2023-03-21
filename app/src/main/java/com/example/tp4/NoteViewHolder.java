package com.example.tp4;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    public TextView titre;
    public TextView desc;
    public ImageView image;
    public Button modifyButton;


    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        findViews(itemView);
    }

    private void findViews(View view) {
        titre = (TextView) view.findViewById(R.id.titre);
        desc = (TextView) view.findViewById(R.id.desc);
        image = (ImageView) view.findViewById(R.id.image);
        modifyButton=(Button) view.findViewById(R.id.modify_button);
    }
    public void setItem(final Note note ) {
        titre.setText(note.getTitre());
        desc.setText(note.getDescription());
        image.setImageResource(note.getImgId());
    }}
