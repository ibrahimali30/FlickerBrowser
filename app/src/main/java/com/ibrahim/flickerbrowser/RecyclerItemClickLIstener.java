package com.ibrahim.flickerbrowser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

class RecyclerItemClickLIstener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickLIsten";

    interface OnRecyclerClickListener{
        void OnItemClick(View view, int position);
        void OnItemLongClick(View view,int position);
    }

    private final OnRecyclerClickListener mListener;
    private final GestureDetectorCompat mGestureDetector;

    public RecyclerItemClickLIstener(Context context, final RecyclerView recyclerView , final OnRecyclerClickListener listener) {
        mListener = listener;
        mGestureDetector=new GestureDetectorCompat(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: starts");
                View view = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (mListener != null && view != null){
                    Log.d(TAG, "onSingleTapUp: calling listener.onitemclick");
                    listener.OnItemClick(view,recyclerView.getChildAdapterPosition(view));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: starts");
                View view = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (mListener!=null && view !=null){
                    Log.d(TAG, "onLongPress: calling listener.OnItemLOngClick");
                    listener.OnItemLongClick(view,recyclerView.getChildAdapterPosition(view));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");

        if (mGestureDetector!=null){
            boolean result =mGestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: returns "+result);
            return result;
        }else {
            Log.d(TAG, "onInterceptTouchEvent: returns false");
            return false;
        }
    }
}
