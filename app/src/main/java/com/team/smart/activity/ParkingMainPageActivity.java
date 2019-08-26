package com.team.smart.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.team.smart.R;

public class ParkingMainPageActivity extends HeaderActivity implements OnMapReadyCallback {
    LinearLayout chooseLayout;
    Button carNumPayBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //공통헤더 쓸때 필요한 부분=================
        setView(R.layout.activity_parkingmainpage);
        setMenuDisplay(true);
        setSearchDisplay(true);
        setTitle("주차장");
        //=======================================

        //지도 Fragment
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.parkingMap);
        mapFragment.getMapAsync(this);


//        //내정보로 이동
//        Button mypageBtn = findViewById(R.id.mypageBtn);
//        mypageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), parkingmypageActivity.class); //parkingmypageActivity 이동
//                startActivity(intent);
//            }
//        });
//
//        //검색페이지이동
//        ImageButton searchbtn = findViewById(R.id.searchbtn);
//        searchbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), parkingsearchActivity.class); //parkingsearchActivity 이동
//                startActivity(intent);
//            }
//        });

        //슬라이딩페이지 홈으로 버튼 클릭시 메인페이지로 이동
        TextView homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); //parkingsearchActivity 이동
                startActivity(intent);
            }
        });

        //차번호로 결제 버튼 클릭시 로그인 전이면 로그인/비회원 묻는 페이지 나오기
        carNumPayBtn = findViewById(R.id.carNumPayBtn);
        carNumPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), MainActivity.class); //parkingsearchActivity 이동
               // startActivity(intent);
                chooseLayout = findViewById(R.id.chooseLayout);

                if(chooseLayout.getVisibility() == view.GONE) {
                    chooseLayout.setVisibility(view.VISIBLE);
                    carNumPayBtn.setText("닫기");
                } else {
                    chooseLayout.setVisibility(view.GONE);
                    carNumPayBtn.setText("차번호로 바로 결제하기");
                }

            }
        });

        //비회원 결제 눌렀을때 차번호 찾기 페이지로 이동
        Button anyonePayBtn = findViewById(R.id.anyonePayBtn);

        anyonePayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParkingCarSearchActivity.class); //parkingsearchActivity 이동
                startActivity(intent);
            }
        });

    }

    //지도 위치 설정
    @Override
    public void onMapReady(final GoogleMap map) {

        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));

    }


    @Override
    void findid() {

    }

    @Override
    void configuListner() {

    }

    @Override
    void init() {

    }
}
