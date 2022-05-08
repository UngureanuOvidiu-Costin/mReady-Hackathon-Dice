package net.mready.dicejava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import net.mready.dicejava.ui.dice.DiceFragment;
import net.mready.dicejava.ui.history.HistoryFragment;

public class MainActivity extends AppCompatActivity {

    private Button rollDiceButton;
    View view;
    android.app.FragmentManager fragmentManager = getFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.activity_root).setPadding(0, getStatusBarHeight(), 0, 0);
        loadFragment(DiceFragment.newInstance(), false);

    }

    public void navigateToHistory() {
        Fragment existingFragment = getSupportFragmentManager().findFragmentByTag(HistoryFragment.class.getSimpleName());

        if (existingFragment != null) {
            getSupportFragmentManager().popBackStack(existingFragment.getClass().getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            loadFragment(HistoryFragment.newInstance(), true);
        }
    }

    public void navigateBack() {
        getSupportFragmentManager().popBackStack();
    }

    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.container_fragments, fragment, fragment.getClass().getSimpleName());

        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }

        transaction.commit();
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}