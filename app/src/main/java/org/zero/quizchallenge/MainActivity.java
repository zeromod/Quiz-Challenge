package org.zero.quizchallenge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextAnswerFragment.OnFragmentInteractionListener,RadioAnswerFragment.OnFragmentInteractionListener,CheckBoxAnswerFragment.OnFragmentInteractionListener {

    ViewPager viewPager;
    TextView scoreView,question1,question2,question3,question4,question5,result;
    Button nextButton;
    static int score = 0;
    String mUserAnswerString;
    int mUserAnswerRadioButton;
    boolean[] mUserAnswerCheckBox = new boolean[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Validator validator = new Validator();

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new QuestionsPageAdapter(getSupportFragmentManager(),this));

        scoreView = findViewById(R.id.score);

        question1 = findViewById(R.id.question_1);
        question2 = findViewById(R.id.question_2);
        question3 = findViewById(R.id.question_3);
        question4 = findViewById(R.id.question_4);
        question5 = findViewById(R.id.question_5);
        result = findViewById(R.id.result);

        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (viewPager.getCurrentItem())
                {
                    case 0:validator.validateTextAnswer(viewPager.getCurrentItem());break;
                    case 1:validator.validateRadioAnswer(viewPager.getCurrentItem());break;
                    case 2:validator.validateCheckAnswer(viewPager.getCurrentItem());break;
                    case 3:validator.validateTextAnswer(viewPager.getCurrentItem());break;
                    case 4:validator.validateCheckAnswer(viewPager.getCurrentItem());break;
                }
            }
        });
    }

    //Get User Response on EditText
    @Override
    public void UserAnswer(String userAnswer) {
       mUserAnswerString = userAnswer;
    }

    //Get User Response on CheckBox
    @Override
    public void UserAnswer(Boolean checkBox1, Boolean checkBox2, Boolean checkBox3) {
        mUserAnswerCheckBox[0] =checkBox1;
        mUserAnswerCheckBox[1] =checkBox2;
        mUserAnswerCheckBox[2] =checkBox3;
    }

    //Get User Response on Radio Button
    @Override
    public void UserAnswer(int userAnswer) {
        mUserAnswerRadioButton = userAnswer;
    }

    @Override
    protected void onResume() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.apply();
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        }
        super.onResume();
    }

    //Validator Class
    private class Validator {
        final int CORRECT = getResources().getColor(android.R.color.holo_green_light);
        final int WRONG = getResources().getColor(android.R.color.holo_red_light);
        private void validateTextAnswer(int currentItem) {
            if (mUserAnswerString == null)
                noAnswerWarning();
            else {
                if (currentItem == 0/*Question 1*/) {
                    if (getResources().getString(R.string.answer_q1).equals(mUserAnswerString.toLowerCase()))
                        handleUI(true);
                    else handleUI(false);
                }
                if (currentItem == 3/*Question 4*/) {
                    if (getResources().getString(R.string.answer_q4).equals(mUserAnswerString.toLowerCase()))
                        handleUI(true);
                    else handleUI(false);
                }
            }
        }
        private void validateRadioAnswer(int currentItem) {
            if ( mUserAnswerRadioButton == 0 )
                noAnswerWarning();
            else
            if (currentItem == 1/*Question 2*/) {
                if (getResources().getInteger(R.integer.answer_q2) == mUserAnswerRadioButton)
                    handleUI(true);
                else handleUI(false);
            }
        }
        private void validateCheckAnswer(int currentItem) {
            if (!mUserAnswerCheckBox[0]&&!mUserAnswerCheckBox[1]&&!mUserAnswerCheckBox[2])
                noAnswerWarning();
            else {
                if (currentItem == 2/*Question 3*/) {
                    if (mUserAnswerCheckBox[0] && mUserAnswerCheckBox[1])
                        handleUI(true);
                    else handleUI(false);
                }
                if (currentItem == 4/*Question 5*/) {
                    if (mUserAnswerCheckBox[0])
                        handleUI(true);
                    else handleUI(false);
                }
            }
        }

        void handleUI(boolean answer){
            int currentItem = viewPager.getCurrentItem();
            if (currentItem == 0) {
                question1.setText(getResources().getString(R.string.answer_q1_string));
                if (answer) {
                    score++;
                    question1.setTextColor(CORRECT);
                }
                else question1.setTextColor(WRONG);
            }

            if (currentItem == 1) {
                question2.setText(getResources().getString(R.string.answer_q2_string));
                if (answer) {
                    score++;
                    question2.setTextColor(CORRECT);
                }
                else
                    question2.setTextColor(WRONG);
            }
            if (currentItem == 2) {
                question3.setText(getResources().getString(R.string.answer_q3_string));
                if (answer) {
                    score++;
                    question3.setTextColor(CORRECT);
                }
                else question3.setTextColor(WRONG);
            }

            if (currentItem == 3) {
                question4.setText(getResources().getString(R.string.answer_q4_string));
                if (answer) {
                    score++;
                    question4.setTextColor(CORRECT);
                }
                else question4.setTextColor(WRONG);
            }

            if (currentItem == 4) {
                question5.setText(getResources().getString(R.string.answer_q5_string));
                if (answer) {
                    score++;
                    question5.setTextColor(CORRECT);
                }
                else question5.setTextColor(WRONG);

                //Reached end of the Quiz,Display final score
                nextButton.setVisibility(View.GONE);
                result.setText(getResources().getString(R.string.result,score));
            }

            //Update Score
            scoreView.setText(getResources().getString(R.string.score_update,score));

            viewPager.setCurrentItem(currentItem + 1 /* Adding 1 to move to next question*/,true);
        }

        private void noAnswerWarning() {
            Toast.makeText(getApplicationContext(),"Please answer the question",Toast.LENGTH_SHORT).show();
        }
    }
}
