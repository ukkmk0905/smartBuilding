package com.team.smart.fragment;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.team.smart.network.APIInterface;
import com.team.smart.vo.FoodVO;
import com.team.smart.vo.Foods;

import java.util.ArrayList;

public class Dishes extends Fragment {

    private APIInterface apiInterface;

    //통신
    protected ArrayList<FoodVO> callApiFoodList() {
        /**
         GET List Resources
         **/
//        HashMap<String,String> param = new HashMap<>();
//        param.put("cmd","DisheList");
//
//        Call<Foods> call = apiInterface.DishesList(param);
//        call.enqueue(new Callback<Foods>() {
//            @Override
//            public void onResponse(Call<Foods> call, Response<Foods> response) {
//                Log.d("TAG",response.code()+"");
//
//                Foods resource = response.body();
//
//                Gson gson = new Gson();
//                List<Foods.Food> datumList = resource.getFoods();
//                for (Foods.Food datum : resource.getFoods()) {
////                    displayResponse += datum.id + " " + datum.name + " " + datum.pantoneValue + " " + datum.year + "\n";
//                }
////                responseText.setText(displayResponse);
//            }
//            @Override
//            public void onFailure(Call<Foods> call, Throwable t) {
//                call.cancel();
//            }
//        });

        Gson gson = new Gson();
        Foods foods = new Foods();
        ArrayList<FoodVO> foodlist = new ArrayList<>();
        FoodVO food = new FoodVO();
        food.setComp_org("육삼냉면");
        food.setComp_coment("살얼음 둥둥떠있는 육수 제공");
        food.setF_ImageUrl("http://www.naver.com");
        food.setF_name("물냉면");
        food.setF_price("3000");
        food.setStar("4.7");
        foodlist.add(food);

        FoodVO food2 = new FoodVO();
        food2.setComp_org("짜글이 삼시세끼");
        food2.setComp_coment("돼지초벌구이 묵은지 김치찜");
        food2.setF_ImageUrl("http://www.naver.cos");
        food2.setF_name("돼지초벌구이");
        food2.setF_price("12000");
        food2.setStar("3.8");
        foodlist.add(food2);

        String json = gson.toJson(foods);
        Log.d("msg", json);

        //Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
        //startActivity(intent);
        return foodlist;
    }
}
