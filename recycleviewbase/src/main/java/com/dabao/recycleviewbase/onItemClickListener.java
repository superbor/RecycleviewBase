package com.dabao.recycleviewbase;

import android.view.View;

/**
 * Created by hzsunyj on 16/12/30.
 */
public  abstract class onItemClickListener<T> {
    public  void onClick(View v, T data){

    };
    public  void onClick(View v, int position){

    };

    public  void onClick(View v, T data  ,int position){

    };

    public boolean onLongClick(View v, T data){
        return false;
    };
    public boolean onLongClick(View v, T data , int postition){
        return false;
    };

    public void overMAX(View v, T data){};

    public  void selectchange( int postition){};



}




