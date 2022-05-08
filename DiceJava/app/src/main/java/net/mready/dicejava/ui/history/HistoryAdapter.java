package net.mready.dicejava.ui.history;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.mready.dicejava.R;
import net.mready.dicejava.model.DiceRoll;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.DiceHistoryViewHolder> {

    private final List<DiceRoll> historyList;

    public HistoryAdapter(List<DiceRoll> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public DiceHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dice_history, parent, false);
        return new DiceHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiceHistoryViewHolder holder, int position) {
        holder.bindDiceHistory(historyList.get(position));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class DiceHistoryViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = HistoryAdapter.class.getSimpleName();
        TextView tvDiceHistory;
        TextView tvSum;
        TextView tvDouble;

        public DiceHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDiceHistory = itemView.findViewById(R.id.tv_dice_history);
            tvSum = itemView.findViewById(R.id.idSum);
            tvDouble = itemView.findViewById(R.id.idDubla);
        }

        public void bindDiceHistory(DiceRoll diceHistory) {
            tvDiceHistory.setText(String.valueOf(diceHistory.getDice_1() +"-"+ diceHistory.getDice_2()));
            tvSum.setText(String.valueOf(diceHistory.getSum()));
            if(diceHistory.getDouble() == true){
                Log.d(TAG, "bindDiceHistory: " + diceHistory.getDice_1() + " " + diceHistory.getDice_2());
                tvDouble.setText("Dubla");
                tvSum.setTextColor(Color.parseColor("#D87153"));
                tvDouble.setTextColor(Color.parseColor("#D87153"));
                tvDiceHistory.setTextColor(Color.parseColor("#D87153"));
            }else {
                tvDouble.setText("");
            }
        }
    }
}
