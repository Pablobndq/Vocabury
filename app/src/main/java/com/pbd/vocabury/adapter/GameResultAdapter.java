package com.pbd.vocabury.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pbd.vocabury.R;
import com.pbd.vocabury.model.GameMode;
import com.pbd.vocabury.model.GameResult;

import java.util.ArrayList;

public class GameResultAdapter
        extends RecyclerView.Adapter<GameResultAdapter.ViewHolder> {

    private final ArrayList<GameResult> list;
    private final GameMode mode;

    public GameResultAdapter(ArrayList<GameResult> list,
                             GameMode mode){

        this.list = list;
        this.mode = mode;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textWord;
        TextView textUserAnswer;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            textWord = itemView.findViewById(R.id.textWord);
            textUserAnswer = itemView.findViewById(R.id.textUserAnswer);

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game_result,
                        parent,
                        false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {

        GameResult result = list.get(position);

        String text;

        if(mode == GameMode.SPANISH_TO_FOREIGN){

            text = result.getWord().getSpanish()
                    + " → "
                    + result.getCorrectAnswer();

        }else{

            text = result.getWord().getForeign()
                    + " → "
                    + result.getCorrectAnswer();

        }

        holder.textWord.setText(
                (result.isCorrect() ? "🟢 " : "🔴 ") + text);

        if(result.isCorrect()){

            holder.textWord.setTextColor(Color.rgb(0,140,0));

            holder.textUserAnswer.setVisibility(View.GONE);

        }else{

            holder.textWord.setTextColor(Color.RED);

            holder.textUserAnswer.setVisibility(View.VISIBLE);

            holder.textUserAnswer.setText(
                    "Tu respuesta: " + result.getUserAnswer());

        }

    }

    @Override
    public int getItemCount() {

        return list.size();

    }

}
