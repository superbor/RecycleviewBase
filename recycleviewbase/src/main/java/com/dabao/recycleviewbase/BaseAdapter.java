package com.dabao.recycleviewbase;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.dabao.recycleviewbase.holder.CollectionUtil;
import com.dabao.recycleviewbase.holder.WrapperUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public  class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>  {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();
    private Boolean canselect=false; //为true 表示可以选择
    private int  maxselect = 100;
    private int   qujian= -1;
    private int   unselect= -1;
    private Set<Integer> mSelectedView = new HashSet<Integer>();
    private int selectedPosition = -5;
    /**
     * data source
     */
    public List<T> dataList;

    public List<T> type;

    public T object;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    /**
     * onClick onLongClick callback
     */
    public onItemClickListener listener;

    public onFindDataLister findDataLister;
     public onBinderHeader onBinderHeader;

    public onBinderHeader getOnBinderHeader() {
        return onBinderHeader;
    }

    public void setOnBinderHeader(onBinderHeader onBinderHeader) {
        this.onBinderHeader = onBinderHeader;
    }

    public onFindDataLister getFindDataLister() {
        return findDataLister;
    }

    public void setFindDataLister(onFindDataLister findDataLister) {
        this.findDataLister = findDataLister;
    }

    /**VID_15123530122301jpg
     * constructor view holder delegate
     */
    public BaseDelegate delegate;

    public void setQujian(int qujian) {
        this.qujian = qujian;
    }

    public SparseBooleanArray getmSelectedPositions() {
        return mSelectedPositions;
    }
    public void addItem(int position,T item){
        dataList.add(position,item);
        notifyItemInserted(position+getHeadersCount());
  
         notifyItemRangeChanged(position+getHeadersCount(), dataList.size()+1);
        Log.e("dataList.size","dataList.size"+dataList.size());
    }

    public void replace(int position,T item){
        dataList.set(position,item);
   
          notifyItemChanged(position+getHeadersCount());
         notifyItemRangeChanged(position+getHeadersCount(), dataList.size());
        Log.e("dataList.size","dataList.size"+dataList.size());
    }
  //删除指定列
    public void remove(int position){
        if(dataList.size()<position||position<0){
            return;
        }

                    try {
                        dataList.remove(position);
                        notifyItemRemoved(position+getHeadersCount());
                        notifyItemRangeChanged(position+getHeadersCount(), dataList.size());
            }catch (Exception e){
                notifyDataSetChanged();


                e.printStackTrace();
            }



    }
    public void setSelectedcount(int position,boolean isselected) {
        this.mSelectedPositions.append(position,isselected);
        mSelectedView.remove(position);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, dataList.size());

    }

    SparseBooleanArray mSelectedPositions = new SparseBooleanArray();

    public void setMaxselect(int maxselect) {
        this.maxselect = maxselect;
    }

    public void setSelect(Boolean canselect,int maxselect) {
        this.maxselect = maxselect;
        this.canselect = canselect;
    }
    public void setunSelectposition(int postion) {
        this.unselect = postion;
    }
    public void setSelect(Boolean canselect,int maxselect,SparseBooleanArray mSelectedPositions) {
        this.maxselect = maxselect;
        this.canselect = canselect;
        this.mSelectedPositions = mSelectedPositions;
    }
    public BaseAdapter setSelect(Boolean canselect) {
        this.maxselect = maxselect;
        this.canselect = canselect;
        this.mSelectedPositions = mSelectedPositions;
        return this;
    }

    public void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.append(position, isChecked);
        mSelectedView.add(position);
        notifyItemRangeChanged(position, dataList.size());
        notifyItemChanged(position);

    }

    public Boolean getCanselect() {
        return canselect;
    }

    public void setCanselect(Boolean canselect) {
        this.canselect = canselect;
    }

    public Set<Integer> getmSelectedView() {
        return mSelectedView;
    }

    public void setmSelectedView(Set<Integer> mSelectedView) {
        this.mSelectedView = mSelectedView;
    }

    /**
     * 单选
     * @param position
     */
    public void setCheckPosition(int position){
        int i=mSelectedPositions.indexOfValue(true);
        if (i==-1){
            mSelectedPositions.append(position,true);

        }
        else {
            int mposition=mSelectedPositions.keyAt(i);
            mSelectedPositions.append(mposition,false);
            notifyItemChanged(mposition);
            if (  mSelectedPositions.get(position)){
                mSelectedPositions.append(position,false);
                mSelectedView.remove(position);
            }
            else {
                mSelectedPositions.append(position,true);
                mSelectedView.add(position);

            }
            mSelectedPositions.append(position,true);
        }
        notifyItemChanged(position);
    }
    /**
     * constructor
     *
     * @param dataList
     * @param delegate
     */
    public BaseAdapter(List<T> dataList, BaseDelegate delegate) {
        this(dataList, delegate, null);
    }

    /**
     * constructor
     *
     * @param dataList
     * @param delegate
     * @param listener
     */
    public BaseAdapter(List<T> dataList, BaseDelegate delegate, onItemClickListener listener) {
        checkData(dataList);
        this.delegate = delegate;
        this.listener = listener;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    /**
     * just is empty
     *
     * @param dataList
     */
    private void checkData(List<T> dataList) {
        if (dataList == null) {
            dataList = Collections.emptyList();
        }
        this.dataList = dataList;
    }

    /**
     * set onclick & onLongClick callback
     *
     * @param listener
     */
    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * create view holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        Log.e("header","获取headerview");
        if (mHeaderViews.get(viewType) != null)
        {

            return delegate.onCreateHeadHolder(parent,  mHeaderViews.get(viewType));



        } else if (mFootViews.get(viewType) != null)
        {
            BaseViewHolder holder = new weHolder(parent, mFootViews.get(viewType));;
            return holder;
        }

        return delegate.onCreateViewHolder(parent, viewType);
    }

    /**
     * bind view holder
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {



//        Log.e("onBindViewHolder","position"+position);
        if (isHeaderViewPos(position))
        {
            holder.onBindHeader(object);
            return ;
        } else if (isFooterViewPos(position))
        {
            holder.onBindHeader(object);
            return ;
        }
        if (canselect){
            holder.onBindViewHolder(dataList.get(position-getHeadersCount()),mSelectedPositions,position);
        }
        else {

            holder.onBindViewHolder(dataList.get(position-getHeadersCount()),position-getHeadersCount(),object);

        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (listener != null && holder.enable()) {



                    if(CollectionUtil.isEmpty(dataList) || dataList.size() <= position-getHeadersCount()){
                        return;
                    }




                    listener.onClick(v, dataList.get(position-getHeadersCount()));
                    listener.onClick(v, position-getHeadersCount());
                    listener.onClick(v, dataList.get(position-getHeadersCount()),position-getHeadersCount());

                }
                //如果设置了多选
                if (canselect){
                    if (qujian==-1){
                        //如果单选
                        if (maxselect==1){
                            int i=mSelectedPositions.indexOfValue(true);
                            if (i==-1){
                                mSelectedPositions.append(position,true);

                            }
                            else {
                                int mposition=mSelectedPositions.keyAt(i);
                                mSelectedPositions.append(mposition,false);
                                notifyItemChanged(mposition);
                                if (  mSelectedPositions.get(position)){
                                    mSelectedPositions.append(position,false);
                                    mSelectedView.remove(position);
                                }
                                else {
                                    mSelectedPositions.append(position,true);
                                    mSelectedView.add(position);

                                }
                                mSelectedPositions.append(position,true);
                            }
                            notifyItemChanged(position);
                        }
                        //如果多选
                        else {
                            if (mSelectedView.size()<maxselect){

                                if (  mSelectedPositions.get(position)){

                                    mSelectedPositions.append(position,false);
                                    mSelectedView.remove(position);


                                }
                                else {
                                    mSelectedPositions.append(position,true);
                                    mSelectedView.add(position);

                                }
                                Log.e("选择了",""+mSelectedView);
                                notifyItemChanged(position);
                            }
                            else {
                                Log.e("选择了","超过了max了啊啊啊啊啊啊啊");
                                if (  mSelectedPositions.get(position)){

                                    mSelectedPositions.append(position,false);
                                    mSelectedView.remove(position);
                                    notifyItemChanged(position);

                                }

                                if (listener != null && holder.enable()) {

                                    listener.overMAX(v, dataList.get(position));

                                }


                            }
                        }
                    }
                    else {
                        Log.e("设置了区间了","设置了区间了"+mSelectedView);
                        if (position>qujian){


                            Log.e("设置了区间了","单选"+mSelectedView);
                            Log.e("设置了区间了","单选AA"+mSelectedPositions);
                            int i=mSelectedPositions.indexOfValue(true);
                            if (i==-1){
                                mSelectedPositions.append(position,true);

                            }
                            else {
                                SparseBooleanArray ss=new SparseBooleanArray();
                                ss=mSelectedPositions;

                                Log.e("设置了区间了","ssssssssss"+ss.size());
                                for (int  j= 0;  j< ss.size(); ++j){
                                    int pkey=ss.keyAt(j);
                                    if(pkey>qujian&&pkey!=position){
                                        if (ss.valueAt(i)==true){
                                            Log.e("设置了区间了","jjj"+ pkey);
                                            mSelectedPositions.append(pkey,false);
                                            notifyItemChanged(pkey);
                                        }
                                    }
                                }



                                if (  mSelectedPositions.get(position)){
                                    mSelectedPositions.append(position,false);
                                    mSelectedView.remove(position);
                                }
                                else {
                                    mSelectedPositions.append(position,true);
                                    mSelectedView.add(position);

                                }
                                mSelectedPositions.append(position,true);
                            }
                            notifyItemChanged(position);
                        }
                        else {//小于区间

                            Log.e("设置了区间了","多选"+mSelectedView);
                            if (mSelectedView.size()<maxselect){


                                if (  mSelectedPositions.get(position)){
                                    if (position==unselect){

                                        Log.e("SS","unselect="+position);
                                    }else {
                                        mSelectedPositions.append(position,false);
                                        mSelectedView.remove(position);
                                    }

                                }
                                else {
                                    mSelectedPositions.append(position,true);
                                    mSelectedView.add(position);

                                }
                                Log.e("选择了",""+mSelectedView);
                                notifyItemChanged(position);
                            }
                            else {

                                if (listener != null && holder.enable()) {

                                    listener.overMAX(v, dataList.get(position));

                                }


                            }

                        }
                    }

                }  if (listener != null && holder.enable()) {

                    listener.selectchange(position);
                }
            }


        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (listener != null && holder.enable()) {

                    listener.onLongClick(v, dataList.get(position-getHeadersCount()));
                    listener.onLongClick(v, dataList.get(position-getHeadersCount()) , position-getHeadersCount());

                }
                return false;
            }
        });



    }
    public void addHeaderView(View view)
    {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }
    /**
     * get item count
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return dataList.size()+getHeadersCount();
    }

    /**
     * get item view type
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position))
        {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position))
        {
            return mFootViews.keyAt(position - getHeadersCount() - getHeadersCount());
        }
        return delegate.getItemViewType(dataList.get(position-getHeadersCount()),position);
    }


    public  List<T> getDataList() {


        return dataList;
    }
    public void addeAll(List<T> rdataList) {

        if (rdataList != null && rdataList.size() > 0) {
            dataList.addAll(rdataList);
        }
        Log.e("addeAll","dy"+dataList.size());
        notifyDataSetChanged();
    }
    public void replaceAll(List<T> rdataList) {
        dataList.clear();
        if (rdataList != null && rdataList.size() > 0) {
            dataList.addAll(rdataList);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == BASE_ITEM_TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {


        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position))
        {
            WrapperUtils.setFullSpan(holder);
        }
    }



    public int getHeadersCount()
    {
        return mHeaderViews.size();
    }

    private boolean isHeaderViewPos(int position )
    {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position)
    {
        return position >= getHeadersCount() + getRealItemCount();
    }

    private int getRealItemCount()
    {
        return dataList.size();
    }


    /**
     * delegate
     */

}
