package net.mready.dicejava.ui.dice;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.seismic.ShakeDetector;

import net.mready.dicejava.MainActivity;
import net.mready.dicejava.R;
import net.mready.dicejava.ui.dialogs.DoubleDialog;

import java.util.Map;
import java.util.Random;

public class DiceFragment extends Fragment implements ShakeDetector.Listener {
    private static final String TAG = DiceFragment.class.getSimpleName();
    FrameLayout containerHistoryPreview;
    private Button rollDiceButton;
    private View view;
    private LottieAnimationView dice1;
    private LottieAnimationView dice2;
    private final int LAYOUT_WIDTH_HEIGHT = 300;
    final DoubleDialog dialog = new DoubleDialog();

    private void setView(){
        this.view = getView();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getViewReferences(view);
        containerHistoryPreview.setOnClickListener((v) -> ((MainActivity) requireActivity()).navigateToHistory());
        setUp_Seismic();
    }

    private void setUp_Seismic(){
        SensorManager sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        ShakeDetector shakeDetector = new ShakeDetector(this);
        shakeDetector.start(sensorManager);
    }

    private void setRollDiceButton_GUI(View view){
        Log.d(TAG, "setRollDiceButton_GUI: called");

        this.rollDiceButton = (Button) view.findViewById(R.id.btn_roll);
        this.rollDiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: called");
                Toast.makeText(getContext(), "Rolling...", Toast.LENGTH_SHORT).show();


                dice1.setVisibility(View.VISIBLE);
                dice1.playAnimation();
                ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(2000);
                animator.addUpdateListener(animation -> {
                    dice1.setProgress((Float) animation.getAnimatedValue());
                });

                dice1.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        dice1.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });

                dice2.setVisibility(View.VISIBLE);
                dice2.playAnimation();
                ValueAnimator animator2 = ValueAnimator.ofFloat(0f, 1f).setDuration(2000);
                animator2.addUpdateListener(animation -> {
                    dice1.setProgress((Float) animation.getAnimatedValue());
                });

                dice2.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        dice2.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                animator2.start();
                animator.start();


                Random pseudoRandomGenerator_1 = new Random();
                Random pseudoRandomGenerator_2 = new Random();

                int dice11 = pseudoRandomGenerator_1.nextInt(6);
                int dice22 = pseudoRandomGenerator_2.nextInt(6);

                setDice_GUI(dice11, true);
                setDice_GUI(dice22, false);
                saveLocalScores(dice11, dice22);

                if(dice11 == dice22){
                    dialog.show(getFragmentManager(), "Dialog");
                    dialog.setCancelable(true);
                    Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        // E deprecated
                        vibrator.vibrate(500);
                    }
                }
            }
        });
    }

    private void saveLocalScores(int diceValue1, int diceValue2){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        int size = sharedPreferences.getAll().size();
        String saved = String.valueOf(diceValue1) + " " + String.valueOf(diceValue2);
        String id = String.valueOf(size + 1);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(id, saved);
        editor.apply();

        int size2 = sharedPreferences.getAll().size();
        Toast.makeText(getContext(), String.valueOf(size2), Toast.LENGTH_SHORT).show();
    }

    private void setDice_GUI(int diceValue, boolean firstDice){
        Log.d(TAG, "setDice_GUI: called");
        ImageView imageView;
        if(firstDice == true){
            imageView = this.view.findViewById(R.id.iv_dice_left);
        }else {
            imageView = this.view.findViewById(R.id.iv_dice_right);
        }

        imageView.getLayoutParams().height = LAYOUT_WIDTH_HEIGHT;
        imageView.getLayoutParams().width = LAYOUT_WIDTH_HEIGHT;

        switch (diceValue){
            case 1:
                imageView.setImageResource(R.drawable.dice_face_01);
                break;
            case 2:
                imageView.setImageResource(R.drawable.dice_face_02);
                break;
            case 3:
                imageView.setImageResource(R.drawable.dice_face_03);
                break;
            case 4:
                imageView.setImageResource(R.drawable.dice_face_04);
                break;
            case 5:
                imageView.setImageResource(R.drawable.dice_face_05);
                break;
            default:
                imageView.setImageResource(R.drawable.dice_face_06);
                break;
        }
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: called");
        super.onStart();
        this.setView();
        this.setRollDiceButton_GUI(this.view);

        dice1 = this.view.findViewById(R.id.idAnimatieDice);
        dice2 = this.view.findViewById(R.id.idAnimatieDice2);
    }

    private void getViewReferences(View view) {
        containerHistoryPreview = view.findViewById(R.id.container_history_preview);
    }

    public static DiceFragment newInstance() {
        return new DiceFragment();
    }

    @Override
    public void hearShake() {
        Log.d(TAG, "hearShake: called");
        //Toast.makeText(getContext(), "Brrrrrr...", Toast.LENGTH_SHORT).show();
        this.rollDiceButton.performClick();
        this.rollDiceButton.setPressed(true);
        this.rollDiceButton.invalidate();
        this.rollDiceButton.setPressed(false);
        this.rollDiceButton.invalidate();
    }
}