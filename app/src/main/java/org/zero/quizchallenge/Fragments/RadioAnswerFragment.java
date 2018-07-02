package org.zero.quizchallenge.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import org.zero.quizchallenge.R;

public class RadioAnswerFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "question";
    private static final String ARG_PARAM2 = "options";

    private String mQuestion;
    private String[] mOptions;

    private OnFragmentInteractionListener mListener;

    public RadioAnswerFragment() {
        // Required empty public constructor
    }

    public static RadioAnswerFragment newInstance(String question, String[] options) {
        RadioAnswerFragment fragment = new RadioAnswerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, question);
        args.putStringArray(ARG_PARAM2, options);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestion = getArguments().getString(ARG_PARAM1);
            mOptions = getArguments().getStringArray(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_radio_answer, container, false);
        TextView question = view.findViewById(R.id.radio_question);
        question.setText(mQuestion);
        RadioButton radioButton1 = view.findViewById(R.id.radio_1);
        radioButton1.setOnClickListener(this);
        radioButton1.setText(mOptions[0]);
        RadioButton radioButton2 = view.findViewById(R.id.radio_2);
        radioButton2.setOnClickListener(this);
        radioButton2.setText(mOptions[1]);
        RadioButton radioButton3 = view.findViewById(R.id.radio_3);
        radioButton3.setText(mOptions[2]);
        radioButton3.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        // Check which radio button was clicked
        switch(v.getId()) {
            case R.id.radio_1:
                if (checked)
                    if (mListener != null)
                    mListener.UserAnswer(1);
                break;
            case R.id.radio_2:
                if (checked)
                    if (mListener != null)
                    mListener.UserAnswer(2);
                break;
            case R.id.radio_3:
                if (checked)
                    if (mListener != null)
                    mListener.UserAnswer(3);
                break;
        }

    }

    public interface OnFragmentInteractionListener {
        void UserAnswer(int userAnswer);
    }
}
