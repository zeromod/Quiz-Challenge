package org.zero.quizchallenge;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextAnswerFragment.OnFragmentInteractionListener,RadioAnswerFragment.OnFragmentInteractionListener,CheckBoxAnswerFragment.OnFragmentInteractionListener {

    ViewPager viewPager;
    TextView resultView, scoreView;
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

        resultView = findViewById(R.id.result);
        scoreView = findViewById(R.id.score);

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

    //Validator Class
    private class Validator {
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
            if (viewPager.getCurrentItem() == 4) {
                nextButton.setVisibility(View.GONE);
            }
            if (answer){
                score++;
                scoreView.setText(String.valueOf(score));
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1,true);
            }
            else
            {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1,true);
            }
        }

        private void noAnswerWarning() {
        }
    }
}
