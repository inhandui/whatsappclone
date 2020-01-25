package com.cursoandroid.whatsappclone.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {

    private List<Fragment> abas = new ArrayList<>();
    private List<String> tituloAbas = new ArrayList<>();

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return abas.get(position);
    }


    @Override
    public int getCount() {
        return abas.size();
    }

    public void addFragment(Fragment fragment, String title){
        abas.add(fragment);
        tituloAbas.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas.get(position);
    }
}
