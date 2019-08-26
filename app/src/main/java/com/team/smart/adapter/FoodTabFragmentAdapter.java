package com.team.smart.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.team.smart.fragment.ChinaDishes;
import com.team.smart.fragment.Dessert;
import com.team.smart.fragment.JapaneseDishes;
import com.team.smart.fragment.KoreanDishes;


/**
 * Created by wolfsoft4 on 18/7/18.
 */

public class FoodTabFragmentAdapter extends FragmentStatePagerAdapter {
    int mNumofTabs;

    public FoodTabFragmentAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.mNumofTabs = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("탭메뉴 누름~~~", position+"~~~~~~");
        switch (position) {
            case 0:
                KoreanDishes tab1 = new KoreanDishes();
                return tab1;

            case 1:
                ChinaDishes tab2 = new ChinaDishes();
                return tab2;

            case 2:
                JapaneseDishes tab3 = new JapaneseDishes();
                return tab3;

            case 3:
                Dessert tab4 = new Dessert();
                return tab4;

            default:
                return null;
        }
    }
        @Override
        public int getCount()

        { return mNumofTabs;}

}
