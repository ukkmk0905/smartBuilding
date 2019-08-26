package com.team.smart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.team.smart.R;
import com.team.smart.adapter.FoodMainMenuAdapter;
import com.team.smart.vo.FoodDetailVO;


public class DetailActivity extends AppCompatActivity {

    private Button btnMenu,btnInfo;
    private FrameLayout frTab;
    private FoodMainMenuAdapter foodListAdapter;

    private String paramCompName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findid();
        configuListner();

        btnMenu.performClick(); //메뉴버튼 강제 클릭

    }

    private void findid()
    {
        btnInfo = (Button)findViewById(R.id.btn_detail_info);
        btnMenu = (Button)findViewById(R.id.btn_detail_menu);
        frTab   = (FrameLayout)findViewById(R.id.fr_tab);

        Intent intent = getIntent(); /*데이터 수신*/
        paramCompName = intent.getExtras().getString("comp_seq"); /*String형*/

        //세팅
        TextView txCompName = (TextView)findViewById(R.id.tx_comp_name);
        txCompName.setText(paramCompName);
    }

    private void configuListner()
    {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //눌렀을때
                btnMenu.setTextColor(Color.argb(255,0,0,0));
                btnInfo.setTextColor(Color.argb(125,0,0,0));
                frTab.removeAllViews();

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View view2 = inflater.inflate(R.layout.fragment_food_detail_menu, null);

                RecyclerView recyclerView = view2.findViewById(R.id.rv_mainMenu);
                //recyclerview 사용시 필수  LayoutManager
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(DetailActivity.this);
                mLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(mLayoutManager);
                foodListAdapter = new FoodMainMenuAdapter(DetailActivity.this,new FoodDetailVO());
                recyclerView.setAdapter(foodListAdapter);
//                view2.findViewById(R.id)
                frTab.addView(view2);//프레임뷰
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMenu.setTextColor(Color.argb(125,0,0,0));
                btnInfo.setTextColor(Color.argb(255,0,0,0));
                frTab.removeAllViews();

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View view1 = inflater.inflate(R.layout.food_detail_desc, null);

                frTab.addView(view1);//프레임뷰

            }
        });
    }

}
