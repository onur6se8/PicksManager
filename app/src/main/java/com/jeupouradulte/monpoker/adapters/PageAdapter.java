package com.jeupouradulte.monpoker.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jeupouradulte.monpoker.fragments.AccueilPageFragment;
import com.jeupouradulte.monpoker.fragments.ParamPageFragment;
import com.jeupouradulte.monpoker.fragments.ProfilePageFragment;

public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        return(3);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: //Page number 1
                return AccueilPageFragment.newInstance();
            case 1: //Page number 2
                return ParamPageFragment.newInstance();
            case 2: //Page number 3
                return ProfilePageFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: //Page number 1
                return "Accueil";
            case 1: //Page number 2
                return "Photo";
            case 2: //Page number 3
                return "Galerie";
            default:
                return "Photo";
        }
    }

}
