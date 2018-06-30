package org.zero.quizchallenge;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class QuestionsPageAdapter extends FragmentPagerAdapter {
    private Context mContext;
    QuestionsPageAdapter(FragmentManager supportFragmentManager, Context context) {
        super(supportFragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        final String[] QUESTIONS = mContext.getResources().getStringArray(R.array.questions);
        final String[] OPTIONS_Q2 = mContext.getResources().getStringArray(R.array.options_q2);
        final String[] OPTIONS_Q3 = mContext.getResources().getStringArray(R.array.options_q3);
        final String[] OPTIONS_Q5 = mContext.getResources().getStringArray(R.array.options_q5);
        switch (position)
        {
            case 0:return TextAnswerFragment.newInstance(QUESTIONS[0]);
            case 1:return RadioAnswerFragment.newInstance(QUESTIONS[1],OPTIONS_Q2);
            case 2:return CheckBoxAnswerFragment.newInstance(QUESTIONS[2],OPTIONS_Q3);
            case 3:return TextAnswerFragment.newInstance(QUESTIONS[3]);
            case 4:return CheckBoxAnswerFragment.newInstance(QUESTIONS[4],OPTIONS_Q5);
            default:return TextAnswerFragment.newInstance(QUESTIONS[0]);
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
