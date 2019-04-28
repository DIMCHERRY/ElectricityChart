package com.codercoral.electricitychart.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codercoral.electricitychart.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;

public class FeedbackFragment extends Fragment {
    private static final String Feedback = "Feedback";
    Button submit;
    @BindView(R.id.snackbar_text)
    TextView snackbarText;
    private int mPage;


    public static FeedbackFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(Feedback, page);
        FeedbackFragment fragment = new FeedbackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mPage = getArguments().getInt(Feedback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        submit = view.findViewById(R.id.submit);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        submit.setOnClickListener(v -> {
            Snackbar sb = Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "提交成功！", Snackbar.LENGTH_SHORT);
            sb.setAction("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            sb.setActionTextColor(getResources().getColor(R.color.pink));

            View view = sb.getView();
            TextView tv = (TextView) view.findViewById(R.id.snackbar_text);
            tv.setTextColor(Color.YELLOW);
            sb.show();
        });
    }

}
