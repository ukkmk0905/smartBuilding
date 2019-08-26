package com.team.smart.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.smart.activity.DetailActivity;
import com.team.smart.vo.FoodVO;
import com.team.smart.vo.Foods;

import java.util.ArrayList;

import com.team.smart.R;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.Holder> {
    private ArrayList<FoodVO> foodList;
    private Context mCtx;

    public FoodListAdapter(Context context, ArrayList<FoodVO> pList) {
        this.mCtx = context;
        this.foodList = pList;
    }

    @NonNull
    @Override
    public FoodListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_food_list, parent, false);
        return new FoodListAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.Holder holder, int position) {
        final FoodVO food = foodList.get(position);

        holder.txCompName.setText(food.getComp_org());
        holder.txCompComent.setText(food.getComp_coment());
        holder.txReviewCnt.setText(food.getReviewCnt());
        //holder.imgUrl.setImageResource(R.drawable.food);

        Log.d("FOOD DATA ====> ", food.getComp_org());
        //상세 페이지 이동
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("FOOD DATA SUB ====> ", food.getComp_org());
                Intent intent = new Intent(mCtx, DetailActivity.class);
                intent.putExtra("comp_seq", food.getComp_org()); /*업체명*/

                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }



    public class Holder extends RecyclerView.ViewHolder
    {
        ImageView imgUrl;
        TextView txCompName, txStar, txReviewCnt, txCompComent;

        public Holder(View view) {
            super(view);

            txCompName  = (TextView) view.findViewById(R.id.tx_comp_name);
            txStar       = (TextView) view.findViewById(R.id.tx_star);
            txReviewCnt = (TextView) view.findViewById(R.id.tx_reviewCnt);
            txCompComent = (TextView)view.findViewById(R.id.tx_comp_coment);
            //imgUrl = (ImageView) view.findViewById(R.id.img_url);
        }
    }
}
