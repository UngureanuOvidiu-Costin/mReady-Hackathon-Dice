package net.mready.dicejava.model;

import androidx.annotation.NonNull;

public class DiceRoll {
    private int val1;
    private int val2;

    public DiceRoll(int val1, int val2){
        this.val1 = val1;
        this.val2 = val2;
    }

    public int getDice_1(){
        return this.val1;
    }


    public int getDice_2(){
        return this.val2;
    }

    public int getSum(){
        return getDice_1() + getDice_2();
    }

    public boolean getDouble(){
        return getDice_1() == getDice_2();
    }

    @NonNull
    @Override
    public String toString() {

    }
}
