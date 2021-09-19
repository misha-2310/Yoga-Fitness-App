package com.example.meditation_yoga;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.meditation_yoga.Database.YogaDB;
import com.example.meditation_yoga.Model.Exercise;
import com.example.meditation_yoga.Utils.Common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Daily_Training extends AppCompatActivity {

    Button btnStart;
    ImageView ex_image;
    TextView txtGetReady,txtCountdown,txtTimer,ex_name;
    ProgressBar progressBar;
    LinearLayout layoutGetReady;

    int ex_id=0,limit_time=0;

    List<Exercise> list = new ArrayList<>();

    YogaDB yogaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily__training);

        initData();

        yogaDB = new YogaDB(this);


        btnStart = (Button)findViewById(R.id.btnStart);

        ex_image = (ImageView)findViewById(R.id.detail_image);

        txtCountdown = (TextView)findViewById(R.id.txtCountDown);
        txtGetReady = (TextView)findViewById(R.id.txtGetReady);
        txtTimer = (TextView)findViewById(R.id.timer);
        ex_name = (TextView)findViewById(R.id.ex_name);

        layoutGetReady = (LinearLayout)findViewById(R.id.layout_get_ready);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        //Set data
        progressBar.setMax(list.size());

        setExercisesInformation(ex_id);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(btnStart.getText().toString().toLowerCase().equals("start"))
                {
                    showGetReady();
                    btnStart.setText("done");
                }
                else if(btnStart.getText().toString().toLowerCase().equals("done"))
                {
                    if (yogaDB.getSettingMode() == 0)
                        exercisesEasyModeCountDown.cancel();
                    else if (yogaDB.getSettingMode() == 1)
                        exercisesMediumModeCountDown.cancel();
                    else if (yogaDB.getSettingMode() == 2)
                        exercisesHardModeCountDown.cancel();
                    restTimeCountDown.cancel();

                    if(ex_id < list.size())
                    {
                        showRestTime();
                        ex_id++;
                        progressBar.setProgress(ex_id);
                        txtTimer.setText("");
                    }
                    else
                        showFinished();

                }
                else
                {
                    if (yogaDB.getSettingMode() == 0)
                        exercisesEasyModeCountDown.cancel();
                    else if (yogaDB.getSettingMode() == 1)
                        exercisesMediumModeCountDown.cancel();
                    else if (yogaDB.getSettingMode() == 2)
                        exercisesHardModeCountDown.cancel();

                    restTimeCountDown.cancel();

                    if(ex_id < list.size())
                        setExercisesInformation(ex_id);
                    else
                        showFinished();
                }

            }
        });
    }

    private void showRestTime() {
        ex_image.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        btnStart.setText("Skip");
        btnStart.setVisibility(View.VISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);

        restTimeCountDown.start();

        txtGetReady.setText("REST TIME");
    }

    @SuppressLint("SetTextI18n")
    private void showGetReady() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("GET READY");
        new CountDownTimer(6000,1000)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                txtCountdown.setText(""+(1-1000)/1000);
            }

            @Override
            public void onFinish() {
                showExercises();
            }
        }.start();
    }

    private void showExercises() {
        if(ex_id<list.size()) // list size contains all exercises
        {
            ex_image.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            layoutGetReady.setVisibility(View.INVISIBLE);

            if (yogaDB.getSettingMode() == 0)
                exercisesEasyModeCountDown.start();
            else if (yogaDB.getSettingMode() == 1)
                exercisesMediumModeCountDown.start();
            else if (yogaDB.getSettingMode() == 2)
                exercisesHardModeCountDown.start();


            //Set data
            ex_image.setImageResource(list.get(ex_id).getImage_id());
            ex_name.setText(list.get(ex_id).getName());


        }
        else
            showFinished();
    }

    @SuppressLint("SetTextI18n")
    private void showFinished() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("FINISHED!!");
        txtCountdown.setText("Congratulation!! \nYou're done with today's exercises");
        txtCountdown.setTextSize(20);

        //Save workout done to DB
        yogaDB.saveDay(""+ Calendar.getInstance().getTimeInMillis());
    }

    //Countdown
    CountDownTimer exercisesEasyModeCountDown = new CountDownTimer(Common.TIME_LIMIT_EASY,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText(""+(1/1000));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onFinish() {
            if(ex_id <list.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExercisesInformation(ex_id);
                btnStart.setText("Start");
            }
            else{
                showFinished();
            }
        }
    };
    CountDownTimer exercisesMediumModeCountDown = new CountDownTimer(Common.TIME_LIMIT_MEDIUM,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText(""+(1/1000));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onFinish() {
            if(ex_id <list.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExercisesInformation(ex_id);
                btnStart.setText("Start");
            }
            else{
                showFinished();
            }
        }
    };
    CountDownTimer exercisesHardModeCountDown = new CountDownTimer(Common.TIME_LIMIT_HARD,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText(""+(1/1000));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onFinish() {
            if(ex_id <list.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExercisesInformation(ex_id);
                btnStart.setText("Start");
            }
            else{
                showFinished();
            }
        }
    };

    CountDownTimer restTimeCountDown = new CountDownTimer(10000,1000)  {
        @Override
        public void onTick(long millisUntilFinished) {
            txtCountdown.setText(""+(1/1000));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onFinish() {
           setExercisesInformation(ex_id);
           showExercises();
        }
    };

    @SuppressLint("SetTextI18n")
    private void setExercisesInformation(int id) {
        ex_image.setImageResource(list.get(id).getImage_id());
        ex_name.setText(list.get(id).getName());
        btnStart.setText("Start");

        ex_image.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.INVISIBLE);
    }

    private void initData() {

        list.add(new Exercise(R.drawable.easy_pose,"Easy Pose"));
        list.add(new Exercise(R.drawable.cobra_pose,"Cobra Pose"));
        list.add(new Exercise(R.drawable.downward_facing_dog,"Downward Facing Dog"));
        list.add(new Exercise(R.drawable.boat_pose,"Boat Pose"));
        list.add(new Exercise(R.drawable.half_pigeon,"Half Pigeon"));
        list.add(new Exercise(R.drawable.low_lunge,"Low Lunge"));
        list.add(new Exercise(R.drawable.upward_bow,"Upward Bow"));
        list.add(new Exercise(R.drawable.crescent_lunge,"Crescent Lunge"));
        list.add(new Exercise(R.drawable.warrior_pose,"Warrior Pose"));
        list.add(new Exercise(R.drawable.warrior_pose_2,"Warrior Pose 2"));
        list.add(new Exercise(R.drawable.bow_pose,"Bow Pose"));
    }
}