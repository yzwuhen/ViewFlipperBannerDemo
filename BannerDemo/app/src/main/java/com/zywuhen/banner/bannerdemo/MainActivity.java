package com.zywuhen.banner.bannerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zywuhen.banner.bannerdemo.weigth.MyViewFlipper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyViewFlipper mMyViewFlipper;
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyViewFlipper = (MyViewFlipper) findViewById(R.id.view_flipper);
        mTextView = (TextView) findViewById(R.id.tv_tv);

        final List<Integer> ss = new ArrayList<>();
       // ss.add("http://n.7k7kimg.cn/2013/1101/1383291711366.gif");
       /* ss.add("http://img1.imgtn.bdimg.com/it/u=1049265651,3459529508&fm=26&gp=0.jpg");
        ss.add("http://img1.imgtn.bdimg.com/it/u=619035232,399014571&fm=26&gp=0.jpg");
        ss.add("http://img3.imgtn.bdimg.com/it/u=494530847,2467953064&fm=26&gp=0.jpg");
        ss.add("http://img1.imgtn.bdimg.com/it/u=3396313657,797964756&fm=26&gp=0.jpg");
        ss.add("http://img1.imgtn.bdimg.com/it/u=619035232,399014571&fm=26&gp=0.jpg");*/
        ss.add(R.mipmap.view1);
        ss.add(R.mipmap.view2);
        ss.add(R.mipmap.view3);
        ss.add(R.mipmap.view5);
        mMyViewFlipper.init(ss);
        mMyViewFlipper.setTime(2000);
        mMyViewFlipper.setShowPageListener(new MyViewFlipper.ShowPageListener() {
            @Override
            public void returnpage(int position) {
                mTextView.setText(position+"/"+ss.size());
            }
        });
    }
}
