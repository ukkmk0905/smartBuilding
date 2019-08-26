package com.team.smart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.vo.FoodDetailVO;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FoodMainMenuAdapter extends RecyclerView.Adapter<FoodMainMenuAdapter.Holder> {

    private Context context;
    private FoodDetailVO foodDetailVO;
    private LayoutInflater inflater;

    public FoodMainMenuAdapter(Context context, FoodDetailVO list) {
        this.context = context;
        foodDetailVO = list;
        this.inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //View convertView = inflater.inflate(R.layout.food_main_menu, null);
        View convertView = inflater.inflate(R.layout.food_detail_menu_list, null);
        return new Holder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        View convertView = holder.view;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.food_main_menu, null);
            holder = new Holder(convertView); // ViewHolder을 생성
            holder.tvBestMenu = (TextView) convertView.findViewById(R.id.tv_food_best_menu);
            holder.liDesc = (LinearLayout) convertView.findViewById(R.id.li_detail_desc);
            holder.tvSubName = (TextView) convertView.findViewById(R.id.tv_food_sub_name);
            holder.tvMainName = (TextView) convertView.findViewById(R.id.tv_food_main_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_food_price);
            convertView.setTag(holder); // setTag
        } else {
            //convertView = holder.view;
           // holder = (Holder) convertView.getTag(); // rootView에서 holder을 꺼내온다
            holder.tvBestMenu = (TextView) convertView.findViewById(R.id.tv_food_best_menu);
            holder.liDesc = (LinearLayout) convertView.findViewById(R.id.li_detail_desc);
            holder.tvSubName = (TextView) convertView.findViewById(R.id.tv_food_sub_name);
            holder.tvMainName = (TextView) convertView.findViewById(R.id.tv_food_main_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_food_price);
        }

        final FoodDetailVO.FoodDetail detail = foodDetailVO.getFoodDetails().get(position);
        Gson gson = new Gson();
        String json = gson.toJson(foodDetailVO.getFoodDetails().get(position));
        Log.d("ajskdjaskldjklasdjklas", json);

        holder.tvMainName.setText(detail.getName());
        holder.tvSubName.setText(detail.getSubname());
        holder.tvBestMenu.setText("대표메뉴");
        holder.tvPrice.setText(detail.getPrice());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Log.d("FOOD DATA SUB ====> ", detail.getName());

            }
        });
        //for(int i = 0; i<2;i++)
        //{
        //    View child = inflater.inflate(R.layout.child_menu, null);
        //    holder.liDesc.addView(child);
        //}
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return  foodDetailVO.getFoodDetails().size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        public LinearLayout liDesc;
        public TextView tvBestMenu, tvMainName, tvSubName, tvPrice;
        public View view;
        public Holder(View view) {
            super(view);
            this.view = view;
        }

    }
}
