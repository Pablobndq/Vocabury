package com.pbd.vocabury.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pbd.vocabury.R;
import com.pbd.vocabury.model.Word;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder>{

    public interface OnItemLongClickListener {

        void onItemLongClick(Word word);

    }

    private OnItemLongClickListener longClickListener;

    private final ArrayList<Word> list;

    public WordAdapter(ArrayList<Word> list){

        this.list = list;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textWord;

        public ViewHolder(@NonNull View itemView){

            super(itemView);

            textWord = itemView.findViewById(R.id.textWord);

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType){

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position){

        Word word = list.get(position);

        holder.textWord.setText(word.toString());

        holder.itemView.setOnLongClickListener(v -> {

            if(longClickListener != null){

                longClickListener.onItemLongClick(word);

            }

            return true;

        });

    }

    @Override
    public int getItemCount(){

        return list.size();

    }

    public void updateData(ArrayList<Word> newList){

        list.clear();

        list.addAll(newList);

        notifyDataSetChanged();

    }

    public void setOnItemLongClickListener(
            OnItemLongClickListener listener){

        this.longClickListener = listener;

    }

}
