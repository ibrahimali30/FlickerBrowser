package com.ibrahim.flickerbrowser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickerResyclerViewAdapter extends RecyclerView.Adapter<FlickerResyclerViewAdapter.FlickerImageViewHolder> {
    private static final String TAG = "FlickerResyclerViewAdap";
    private List<Photo> mPhotoList;
    private Context mContext;

    public FlickerResyclerViewAdapter(List<Photo> photoList, Context context) {
        mPhotoList = photoList;
        mContext = context;
    }

    @NonNull
    @Override
    public FlickerImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //called out by the layout manager whenn it needs new view
        Log.d(TAG, "onCreateViewHolder: request new view");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.browse,viewGroup,false);
        return new FlickerImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickerImageViewHolder flickerImageViewHolder, int i) {
        //callyd by the layout manager when it wants new data in an excisting raw

        if (mPhotoList.size()==0 || mPhotoList ==null){
            flickerImageViewHolder.title.setText("\nno photos match \n\ntry another search");
        }else {
            Photo photoitem = mPhotoList.get(i);
            Log.d(TAG, "onBindViewHolder: "+photoitem.getTitle()+"--> "+i);
            Picasso.get().load(photoitem.getImage())
                    .error(R.drawable.placeholder)                   //place holder is an image icon
                    .placeholder(R.drawable.placeholder)
                    .into(flickerImageViewHolder.thumbnail);

            flickerImageViewHolder.title.setText(photoitem.getTitle());
        }


    }

    @Override
    public int getItemCount() {
        return ((mPhotoList!=null)&&(mPhotoList.size()!=0)?mPhotoList.size():1);
    }

    void loadDAta(List<Photo> newPhotos){
        mPhotoList=newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position){
       return  ((mPhotoList!=null)&&(mPhotoList.size()!=0)?mPhotoList.get(position):null);
    }

    static class FlickerImageViewHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        TextView title;

        public FlickerImageViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "FlickerImageViewHolder: starts");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail) ;
            this.title = (TextView) itemView.findViewById(R.id.title);
            Log.d(TAG, "FlickerImageViewHolder: ends");

        }
    }
}
