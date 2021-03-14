package com.chenjimou.bannertest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    List<String> list;
    Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init() {
        list = new ArrayList<>();
        list.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2300866869,450363821&fm=26&gp=0.jpg");
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1775577503,3169780224&fm=26&gp=0.jpg");
        list.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3030891628,4244578109&fm=26&gp=0.jpg");

        // 1.实例化banner控件
        banner = findViewById(R.id.banner);

        // 2.给banner设置图片url
        banner.setBannerImgUrlData(list);

        // 3.设置轮播间隔（可以不设置，默认3秒轮播一次）
        banner.setRollInterval(3000);

        // 4.开始轮播
        banner.startRoll();
    }
}