package com.example.webim_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Note> notes;

    private Listener listener;

    NoteAdapter(Context context, List<Note> notes){
        this.layoutInflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    interface Listener{
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        public ViewHolder(CardView view){
            super(view);
            cardView = view;
        }
    }

    //используем макет для представлений CardView
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        Note note = notes.get(position);
        TextView textView_title = cardView.findViewById(R.id.textView_title);
        textView_title.setText(note.getmTitle());

        TextView textView_text = cardView.findViewById(R.id.textView_text);
        textView_text.setText(note.getmText());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }
}
