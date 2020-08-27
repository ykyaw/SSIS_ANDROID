package iss.team1.ad.ssis_android.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.comm.utils.ApplicationUtil;

public class WelcomeFragment extends Fragment {

    private TextView fragment_welcome_name;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    public static WelcomeFragment newInstance(String param1, String param2) {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_welcome, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        fragment_welcome_name=view.findViewById(R.id.fragment_welcome_name);

        String name = ApplicationUtil.getCurrentUser().getName();
        fragment_welcome_name.setText(name);
    }
}