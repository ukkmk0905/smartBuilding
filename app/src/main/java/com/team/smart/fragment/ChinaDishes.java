package com.team.smart.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.smart.R;
import com.team.smart.adapter.FoodListAdapter;
import com.team.smart.vo.FoodVO;

import java.util.ArrayList;

public class ChinaDishes extends Dishes {
    //부모 통~신
    RecyclerView rv_foodlist;
    ArrayList<FoodVO> list;
    FoodListAdapter foodListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_food_list, container, false);

        rv_foodlist = view.findViewById(R.id.rv_foodlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_foodlist.setLayoutManager(layoutManager);
        rv_foodlist.setItemAnimator(new DefaultItemAnimator());

        list = callApiFoodList();
        foodListAdapter = new FoodListAdapter(getActivity(), list);
        rv_foodlist.setAdapter(foodListAdapter);
        return view;


    }
}
