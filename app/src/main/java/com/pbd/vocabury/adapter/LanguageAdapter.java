package com.pbd.vocabury.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pbd.vocabury.R;
import com.pbd.vocabury.model.Language;

import java.util.ArrayList;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder>{

    private final ArrayList<Language> list;
    private final OnLanguageClickListener listener;
    public interface OnItemLongClickListener {

        void onItemLongClick(Language language);

    }

    private OnItemLongClickListener longClickListener;

    public LanguageAdapter(ArrayList<Language> list, OnLanguageClickListener listener){
        this.list = list;
        this.listener = listener;
    }

    public interface OnLanguageClickListener{

        void onLanguageClick(Language language);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView text;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            text = itemView.findViewById(R.id.textLanguage);

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_language,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){

        Language language = list.get(position);

        holder.text.setText(language.getName());

        holder.itemView.setOnClickListener(v -> {

            listener.onLanguageClick(language);

        });

        holder.itemView.setOnLongClickListener(v -> {

            if (longClickListener != null) {

                longClickListener.onItemLongClick(language);

            }

            return true;

        });

    }

    @Override
    public int getItemCount(){

        return list.size();

    }

    public void updateData(ArrayList<Language> newList){

        list.clear();

        list.addAll(newList);

        notifyDataSetChanged();

    }

    public void setOnItemLongClickListener(
            OnItemLongClickListener listener){

        this.longClickListener = listener;

    }


}
