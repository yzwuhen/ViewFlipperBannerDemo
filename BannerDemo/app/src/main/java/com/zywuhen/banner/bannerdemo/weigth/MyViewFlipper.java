package com.zywuhen.banner.bannerdemo.weigth;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.zywuhen.banner.bannerdemo.R;

import java.util.List;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：BannerDemo
 * 类描述：
 * 创建人：yqw
 * 创建时间：2017/7/11 9:45
 * 修改人：yqw
 * 修改时间：2017/7/11 9:45
 * 修改备注：
 * Version:  1.0.0
 */
public class MyViewFlipper extends ViewFlipper implements View.OnTouchListener{

    private GestureDetector mDetector;
    private int page=1;
    private int maxPage;
    private boolean isFling;

    //
    Animation lInAnim;
    Animation lOutAnim;
    public MyViewFlipper(Context context) {
        super(context);
    }

    public MyViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(List<Integer> mList){
        lInAnim = AnimationUtils.loadAnimation(getContext(), R.anim.push_left_in);        // 向左滑动左侧进入的渐变效果（alpha 0.1  -> 1.0）
        lOutAnim = AnimationUtils.loadAnimation(getContext(), R.anim.push_left_out);    // 向左滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）
        removeAllViews();
        maxPage = mList.size();
        for (int i = 0; i < mList.size(); i++) {
            // 添加图片源
            addView(getImageView(mList.get(i)), new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        }
        this.setOnTouchListener(this);
        mDetector = new GestureDetector(new simpleGestureListener());
    }
    public void setTime(int mTime){
        setAutoStart(true);
        setFlipInterval(mTime);

        setInAnimation(lInAnim);
        setOutAnimation(lOutAnim);
    }

    private ImageView getImageView(Integer id) {
        ImageView imageView = new ImageView(getContext());
        // imageView.setImageResource(R.mipmap.ic_launcher);
       Glide.with(getContext()).load(id).into(imageView);
        return imageView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 点击事件后，停止自动播放
        stopFlipping();
        setAutoStart(false);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:
                //防止触摸后没滑动不会继续自动轮播
                if (!isFling){
                   postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           setInAnimation(lInAnim);
                           setOutAnimation(lOutAnim);
                           startFlipping();
                       }
                   },2000);
                }
                break;
        }
        return mDetector.onTouchEvent(event);
    }

    public void setShowPageListener(ShowPageListener showPageListener) {
        this.mShowPageListener =showPageListener;
    }

    private class simpleGestureListener extends GestureDetector.SimpleOnGestureListener {
        final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;
        @Override
        public boolean onDown(MotionEvent e) {
            isFling =false;
            return true;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                isFling =true;

                setInAnimation(lInAnim);
                setOutAnimation(lOutAnim);
                showNext();
                startFlipping();
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                isFling =true;
                Animation rInAnim = AnimationUtils.loadAnimation(getContext(), R.anim.push_right_in);    // 向右滑动左侧进入的渐变效果（alpha  0.1 -> 1.0）
                Animation rOutAnim = AnimationUtils.loadAnimation(getContext(), R.anim.push_right_out); // 向右滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）
                setInAnimation(rInAnim);
                setOutAnimation(rOutAnim);
                showPrevious();
                startFlipping();
            }
            return true;
        }

    }

    @Override
    public void showNext() {
        super.showNext();
        if (mShowPageListener!=null){
            page++;
            if (page>maxPage){
                page =1;
            }
            mShowPageListener.returnpage(page);
        }
    }

    @Override
    public void showPrevious() {
        super.showPrevious();
        if (mShowPageListener!=null){
            page--;
            if (page<=1){
                page =1;
            }
            mShowPageListener.returnpage(page);
        }
    }

    public ShowPageListener mShowPageListener;
    public interface ShowPageListener{
        void returnpage(int position);
    }
}
