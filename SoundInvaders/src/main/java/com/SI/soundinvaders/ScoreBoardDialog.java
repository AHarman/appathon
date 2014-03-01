package com.SI.soundinvaders;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by alex on 01/03/14.
 */
@SuppressLint("NewApi")
public class ScoreBoardDialog extends DialogFragment {
    public ScoreBoardDialog() {
        //empty
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scoreboardfragment, container);
        getDialog().setTitle("Hello");

        getDialog().setTitle("Score Board");
        return view;
    }

    public static ScoreBoardDialog newInstance() {
        ScoreBoardDialog frag = new ScoreBoardDialog();
        Bundle args = new Bundle();
        args.putString("title", "asdf");
        frag.setArguments(args);

        return frag;
    }
}
