package com.dabao.recycleviewbase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hzsunyj on 16/12/30.
 */
public abstract class BaseDelegate<T> {
   int headtype =8888;
    /**
     * crate view holder by view type
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    public BaseViewHolder onCreateHeadHolder(ViewGroup parent, View viewType) {


        return null;
    }

    /**
     * get view type by data
     *
     * @param data
     * @return
     */
    public abstract int getItemViewType(T data,int postion);

    /**
     * get layout id by view type
     *
     * @param viewType
     * @return
     */
    public abstract int getLayoutId(int viewType);

    /**
     * get item view
     *
     * @param parent
     * @param viewType
     * @return
     */
    public View getItemView(ViewGroup parent, int viewType) {



        return LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
    }



}
