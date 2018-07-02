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

import org.zero.quizchallenge.Fragments.CheckBoxAnswerFragment;
import org.zero.quizchallenge.Fragments.RadioAnswerFragment;
import org.zero.quizchallenge.Fragments.TextAnswerFragment;

public class MainActivity extends AppCompatActivity implements TextAnswerFragment.OnFragmentInteractionListener,RadioAnswerFragment.OnFragmentInteractionListener,CheckBoxAnswerFragment.OnFragmentInteractionListener {

    ViewPager viewPager;
    TextView scoreView, answer1, answer2, answer3, answer4, answer5,result;
    Button validateButton;
    static int score;
    String mUserAnswerString;
    int mUserAnswerRadioButton;
    boolean[] mUserAnswerCheckBox = new boolean[3];
    int counterForLastPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Validator validator = new Validator();

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new QuestionsPageAdapter(getSupportFragmentManager(),this));

        scoreView = findViewById(R.id.score);

        answer1 = findViewById(R.id.answer_1);
        answer2 = findViewById(R.id.answer_2);
        answer3 = findViewById(R.id.answer_3);
        answer4 = findViewById(R.id.answer_4);
        answer5 = findViewById(R.id.answer_5);
        result = findViewById(R.id.result);

        validateButton = findViewById(R.id.validate_button);
        validateButton.setOnClickListener(new View.OnClickListener() {
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
                counterForLastPage++;
                answer1.setText(getResources().getString(R.string.answer_q1_string));
                if (answer) {
                    score++;
                    answer1.setTextColor(CORRECT);
                }
                else answer1.setTextColor(WRONG);
            }

            if (currentItem == 1) {
                counterForLastPage++;
                answer2.setText(getResources().getString(R.string.answer_q2_string));
                if (answer) {
                    score++;
                    answer2.setTextColor(CORRECT);
                }
                else
                    answer2.setTextColor(WRONG);
            }
            if (currentItem == 2) {
                counterForLastPage++;
                answer3.setText(getResources().getString(R.string.answer_q3_string));
                if (answer) {
                    score++;
                    answer3.setTextColor(CORRECT);
                }
                else answer3.setTextColor(WRONG);
            }

            if (currentItem == 3) {
                counterForLastPage++;
                answer4.setText(getResources().getString(R.string.answer_q4_string));
                if (answer) {
                    score++;
                    answer4.setTextColor(CORRECT);
                }
                else answer4.setTextColor(WRONG);
            }

            if (currentItem == 4) {
                counterForLastPage++;
                answer5.setText(getResources().getString(R.string.answer_q5_string));
                if (answer) {
                    score++;
                    answer5.setTextColor(CORRECT);
                }
                else answer5.setTextColor(WRONG);
            }

            //Reached end of the Quiz,Display final score
            if (counterForLastPage == 5) {
                validateButton.setVisibility(View.GONE);
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

    //Handle orientation Changes
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("SCORE",score);
        outState.putString("SCORE_VIEW",scoreView.getText().toString());
        outState.putInt("COUNTER_FOR_LAST_PAGE",counterForLastPage);

        outState.putString("USER_ANSWER_STRING",mUserAnswerString);
        outState.putInt("USER_ANSWER_RADIO_BUTTON",mUserAnswerRadioButton);
        outState.putBooleanArray("USER_ANSWER_CHECKBOX",mUserAnswerCheckBox);

        outState.putString("ANSWER_1",answer1.getText().toString());
        outState.putInt("COLOR_ANSWER_1",answer1.getCurrentTextColor());
        outState.putString("ANSWER_2",answer2.getText().toString());
        outState.putInt("COLOR_ANSWER_2",answer2.getCurrentTextColor());
        outState.putString("ANSWER_3",answer3.getText().toString());
        outState.putInt("COLOR_ANSWER_3",answer3.getCurrentTextColor());
        outState.putString("ANSWER_4",answer4.getText().toString());
        outState.putInt("COLOR_ANSWER_4",answer4.getCurrentTextColor());
        outState.putString("ANSWER_5",answer5.getText().toString());
        outState.putInt("COLOR_ANSWER_5",answer5.getCurrentTextColor());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null)
        {
            score = savedInstanceState.getInt("SCORE");
            scoreView.setText(savedInstanceState.getString("SCORE_VIEW"));
            counterForLastPage = savedInstanceState.getInt("COUNTER_FOR_LAST_PAGE");

            mUserAnswerString = savedInstanceState.getString("USER_ANSWER_STRING");
            mUserAnswerRadioButton = savedInstanceState.getInt("USER_ANSWER_RADIO_BUTTON");
            mUserAnswerCheckBox = savedInstanceState.getBooleanArray("USER_ANSWER_CHECKBOX");

            //Reached end of the Quiz,Display final score
            if (counterForLastPage == 5) {
                validateButton.setVisibility(View.GONE);
                result.setText(getResources().getString(R.string.result,score));
            }

            answer1.setText(savedInstanceState.getString("ANSWER_1"));
            answer1.setTextColor(savedInstanceState.getInt("COLOR_ANSWER_1"));
            answer2.setText(savedInstanceState.getString("ANSWER_2"));
            answer2.setTextColor(savedInstanceState.getInt("COLOR_ANSWER_2"));
            answer3.setText(savedInstanceState.getString("ANSWER_3"));
            answer3.setTextColor(savedInstanceState.getInt("COLOR_ANSWER_3"));
            answer4.setText(savedInstanceState.getString("ANSWER_4"));
            answer4.setTextColor(savedInstanceState.getInt("COLOR_ANSWER_4"));
            answer5.setText(savedInstanceState.getString("ANSWER_5"));
            answer5.setTextColor(savedInstanceState.getInt("COLOR_ANSWER_5"));
        }
        super.onRestoreInstanceState(savedInstanceState);
    }
}
