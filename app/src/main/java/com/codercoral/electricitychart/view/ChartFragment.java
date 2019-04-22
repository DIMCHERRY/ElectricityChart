package com.codercoral.electricitychart.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codercoral.electricitychart.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChartFragment extends Fragment {
    private static final String Chart = "Chart";
    private int mPage;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;

    public static ChartFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(Chart, page);
        ChartFragment fragment = new ChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mPage = getArguments().getInt(Chart);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick({R.id.button1,R.id.button2,R.id.button3})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button1:
                startActivity(new Intent(getActivity(), AnotherBarActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(getActivity(), LineChartActivity.class));
                break;
            case R.id.button3:
                startActivity(new Intent(getActivity(), MultiLineChartActivity.class));
                break;
                default:
                    break;
        }
    }
}
