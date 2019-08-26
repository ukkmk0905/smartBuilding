package com.team.smart.vo;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FoodDetailVO extends BaseRespon  {
    //@SerializedName("foods")
    private ArrayList<FoodDetail> foodDetails;

    public FoodDetailVO()
    {
        foodDetails = new ArrayList<>();
        FoodDetail detail = new FoodDetail();
        detail.setName("물냉면");
        detail.setSubname("물냉면 + 숯불고기");
        detail.setPrice("8000");
        foodDetails.add(detail);


        FoodDetail detail2 = new FoodDetail();
        detail2.setName("비빔냉면");
        detail2.setSubname("비빔냉면 + 숯불고기");
        detail2.setSubname("비빔냉면 + 숯불고기");
        detail2.setPrice("5500");
        foodDetails.add(detail2);

//        Gson gson = new Gson();
//        String json = gson.toJson(foodDetails);
//        Log.d("msgDetail", json);

    }

    public ArrayList<FoodDetail> getFoodDetails() {
        return foodDetails;
    }


    public class FoodDetail
    {
        @SerializedName("name")
        private String name;

        private String subname;

        private String price;


        public String getPrice() {
            return price;
        }

        public String getName() {
            return name;
        }

        public String getSubname() {
            return subname;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setSubname(String subname) {
            this.subname = subname;
        }
    }

}

