package com.dabao.recycleviewbase;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hzsunyj on 16/12/30.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {



    /**
     * TODO
     * single view may be direct construction, eg: TextView view = new TextView(context);
     *
     * @param parent current no use, may be future use
     * @param view
     */
    public BaseViewHolder(ViewGroup parent, View view) {
        super(view);
        findViews();
    }


    /**
     * TODO
     * single view may be direct construction, eg: TextView view = new TextView(context);
     *
     * @param parent current no use, may be future use
     * @param view
     */
    public BaseViewHolder(ViewGroup parent, View view , int viewtype) {
        super(view);
        findViews(viewtype);
    }

    /**
     * find all views
     */
    public abstract void findViews();

    /**
     * find all views
     */
    public void findViews(int viewtype){};


    public  void onBindViewHolder(T data,int postion){

    }
    public  void onBindHeader(Object object){

    }
    public  void onBindViewHolder(T data,int postion,Object object){

    }
    /**
     *  onBindViewHolder
     *     实现多选功能 使用此
     * @return
     */
    public void onBindViewHolder(T data,SparseBooleanArray mSelectedPositions,int position)
    {

    }


    /**
     * holder click enable
     *
     * @return
     */
    public boolean enable() {
        return true;
    }



}

