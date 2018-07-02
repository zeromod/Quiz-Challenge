package org.zero.quizchallenge.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.zero.quizchallenge.R;

public class CheckBoxAnswerFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "question";
    private static final String ARG_PARAM2 = "options";

    private String mQuestion;
    private String[] mOptions;
    boolean checkbox1, checkbox2, checkbox3;

    private OnFragmentInteractionListener mListener;

    public CheckBoxAnswerFragment() {
        // Required empty public constructor
    }

    public static CheckBoxAnswerFragment newInstance(String question, String[] options) {
        CheckBoxAnswerFragment fragment = new CheckBoxAnswerFragment();
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
        View view = inflater.inflate(R.layout.fragment_check_answer, container, false);
        TextView question = view.findViewById(R.id.check_question);
        question.setText(mQuestion);
        CheckBox checkBox1 = view.findViewById(R.id.check_1);
        checkBox1.setText(mOptions[0]);
        checkBox1.setOnClickListener(this);
        CheckBox checkBox2 = view.findViewById(R.id.check_2);
        checkBox2.setText(mOptions[1]);
        checkBox2.setOnClickListener(this);
        CheckBox checkBox3 = view.findViewById(R.id.check_3);
        checkBox3.setText(mOptions[2]);
        checkBox3.setOnClickListener(this);
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
        boolean checked = ((CheckBox) v).isChecked();
        // Check which radio button was clicked
        switch(v.getId()) {
            case R.id.check_1:
                if (checked)
                    checkbox1 =true;
                break;
            case R.id.check_2:
                if (checked)
                    checkbox2 =true;
                break;
            case R.id.check_3:
                if (checked)
                    checkbox3 =true;
                break;
        }
        if (mListener != null)
        mListener.UserAnswer(checkbox1, checkbox2, checkbox3);
    }

    public interface OnFragmentInteractionListener {
        void UserAnswer(Boolean checkbox1,Boolean checkbox2,Boolean checkbox3);
    }
}
