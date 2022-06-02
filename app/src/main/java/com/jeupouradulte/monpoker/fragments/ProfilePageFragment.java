package com.jeupouradulte.monpoker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeupouradulte.monpoker.R;

public class ProfilePageFragment extends Fragment {

    public static ProfilePageFragment newInstance() {
        return (new ProfilePageFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_page, container, false);
    }
}