package com.SI.soundinvaders;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        TextView tv;
        //Button pissoff = (Button) view.findViewById(R.id.close);
        //pissoff.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View view) {
              //  dismiss();
            //}
        //});
        tv = (TextView)view.findViewById(R.id.score0); tv.setText(Integer.toString(ScoreBoard.getScore(0)));
        tv = (TextView)view.findViewById(R.id.score1); tv.setText(Integer.toString(ScoreBoard.getScore(1)));
        tv = (TextView)view.findViewById(R.id.score2); tv.setText(Integer.toString(ScoreBoard.getScore(2)));
        tv = (TextView)view.findViewById(R.id.score3); tv.setText(Integer.toString(ScoreBoard.getScore(3)));
        tv = (TextView)view.findViewById(R.id.score4); tv.setText(Integer.toString(ScoreBoard.getScore(4)));
        tv = (TextView)view.findViewById(R.id.score5); tv.setText(Integer.toString(ScoreBoard.getScore(5)));
        tv = (TextView)view.findViewById(R.id.score6); tv.setText(Integer.toString(ScoreBoard.getScore(6)));
        tv = (TextView)view.findViewById(R.id.score7); tv.setText(Integer.toString(ScoreBoard.getScore(7)));
        tv = (TextView)view.findViewById(R.id.score8); tv.setText(Integer.toString(ScoreBoard.getScore(8)));
        tv = (TextView)view.findViewById(R.id.score9); tv.setText(Integer.toString(ScoreBoard.getScore(9)));
        tv = (TextView)view.findViewById(R.id.scoreName0); tv.setText(ScoreBoard.getName(0));
        tv = (TextView)view.findViewById(R.id.scoreName1); tv.setText(ScoreBoard.getName(1));
        tv = (TextView)view.findViewById(R.id.scoreName2); tv.setText(ScoreBoard.getName(2));
        tv = (TextView)view.findViewById(R.id.scoreName3); tv.setText(ScoreBoard.getName(3));
        tv = (TextView)view.findViewById(R.id.scoreName4); tv.setText(ScoreBoard.getName(4));
        tv = (TextView)view.findViewById(R.id.scoreName5); tv.setText(ScoreBoard.getName(5));
        tv = (TextView)view.findViewById(R.id.scoreName6); tv.setText(ScoreBoard.getName(6));
        tv = (TextView)view.findViewById(R.id.scoreName7); tv.setText(ScoreBoard.getName(7));
        tv = (TextView)view.findViewById(R.id.scoreName8); tv.setText(ScoreBoard.getName(8));
        tv = (TextView)view.findViewById(R.id.scoreName9); tv.setText(ScoreBoard.getName(9));



        return view;
    }

    public static ScoreBoardDialog newInstance() {
        ScoreBoardDialog frag = new ScoreBoardDialog();
        Bundle args = new Bundle();
        frag.setArguments(args);

        return frag;
    }
}