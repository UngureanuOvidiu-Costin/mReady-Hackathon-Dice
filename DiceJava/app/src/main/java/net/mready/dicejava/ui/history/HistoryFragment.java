package net.mready.dicejava.ui.history;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import net.mready.dicejava.MainActivity;
import net.mready.dicejava.R;
import net.mready.dicejava.model.DiceRoll;

import java.util.ArrayList;
import java.util.Arrays;

public class HistoryFragment extends Fragment {
    private static final String TAG = HistoryFragment.class.getSimpleName();
    Toolbar toolbar;
    RecyclerView rvHistory;
    ImageView dice;
    TextView tv1;
    TextView tv2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: called");
        super.onViewCreated(view, savedInstanceState);
        getViewReferences(view);

        ArrayList<DiceRoll> arrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        int size = sharedPreferences.getAll().size();
        if(size != 0) {
            for (int i = 1; i <= size; i++) {
                String id = String.valueOf(i);
                String dices = sharedPreferences.getString(id, "");
                String[] arr = dices.split(" ", 2);
                arrayList.add(new DiceRoll(Integer.valueOf(arr[0]), Integer.valueOf(arr[1])));
            }
        }

        HistoryAdapter adapter = new HistoryAdapter(arrayList);
        rvHistory.setAdapter(adapter);

        toolbar.setOnClickListener((v) -> ((MainActivity) requireActivity()).navigateBack());
    }

    private void getViewReferences(View view) {
        Log.d(TAG, "getViewReferences: called");
        toolbar = view.findViewById(R.id.toolbar);
        rvHistory = view.findViewById(R.id.rv_history);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        int size = sharedPreferences.getAll().size();
        Toast.makeText(getContext(), String.valueOf(size), Toast.LENGTH_SHORT).show();

        if (size == 0) {
            dice = view.findViewById(R.id.idDice);
            tv1 = view.findViewById(R.id.idTextViewNu);
            tv2 = view.findViewById(R.id.idTextViewSfat);
            rvHistory.setVisibility(View.INVISIBLE);
        }else {
            dice = view.findViewById(R.id.idDice);
            tv1 = view.findViewById(R.id.idTextViewNu);
            tv2 = view.findViewById(R.id.idTextViewSfat);
            tv1.setVisibility(View.INVISIBLE);
            tv2.setVisibility(View.INVISIBLE);
            dice.setVisibility(View.INVISIBLE);
        }

    }

    public static HistoryFragment newInstance() {
        Log.d(TAG, "newInstance: called");
        return new HistoryFragment();
    }
}
