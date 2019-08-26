package com.team.smart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.team.smart.R;

public class ParkingUserCarInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_user_car_info);


        //뒤로가기버튼
        ImageButton backHomeBtn = findViewById(R.id.backHomeBtn);
        backHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParkingMainPageActivity.class); //ParkingMainActivity 이동
                startActivity(intent);
            }
        });
    }

}
