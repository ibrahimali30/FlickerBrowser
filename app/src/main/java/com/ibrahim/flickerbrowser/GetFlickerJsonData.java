package com.ibrahim.flickerbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetFlickerJsonData extends AsyncTask<String , String,List<Photo>> implements GetRawData.OndownloadComplete {
    private static final String TAG = "GetFlickerJsonData";

    public static List<Photo> mPhotoList;
    private String mBaseUrl;
    private String mLanguage;
    private boolean mMatchAll;

    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread =false;

    interface OnDataAvailable{
        void onDataAvailable(List<Photo> data , DownloadStatus status);
    }

    public GetFlickerJsonData( OnDataAvailable callBack ,String baseUrl, String language, boolean matchAll) {
        Log.d(TAG, "GetFlickerJsonData: called");
        mBaseUrl = baseUrl;
        mLanguage = language;
        mMatchAll = matchAll;
        mCallBack = callBack;
    }



    @Override
    protected void onPostExecute(List<Photo> photos) {
        Log.d(TAG, "onPostExecute: starts");
        mCallBack.onDataAvailable(photos,DownloadStatus.OK);
        Log.d(TAG, "onPostExecute: ends");
    }

    @Override
    protected List<Photo> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: called");
        String destinationUri = CreateUri(params[0],mLanguage,mMatchAll);
        GetRawData getRawData = new GetRawData(this);
        getRawData.runOnSameThread(destinationUri);
        Log.d(TAG, "doInBackground: ends");
        return mPhotoList;
    }

    String CreateUri(String seartchCriteria , String language , Boolean matchAll){

        return Uri.parse(mBaseUrl).buildUpon()
                .appendQueryParameter("tags",seartchCriteria)
                .appendQueryParameter("tagmode",matchAll ?"ALL" :"ANY")
                .appendQueryParameter("Lang",language)
                .appendQueryParameter("format","json")
                .appendQueryParameter("nojsoncallback","1")
                .build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: starts");

        if (status == DownloadStatus.OK){
            try {
                JSONObject jsondata = new JSONObject(data);
                JSONArray  itemsArray = jsondata.getJSONArray("items");
                mPhotoList=new ArrayList<>();
                for (int i=0 ; i<itemsArray.length() ; i++){

                    JSONObject jsonphoto =  itemsArray.getJSONObject(i);
                    String title = jsonphoto.getString("title");
                    String author = jsonphoto.getString("author");
                    String authorId = jsonphoto.getString("author_id");
                    String tags = jsonphoto.getString("tags");

                    JSONObject jsonMedia = jsonphoto.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");

                    String link = photoUrl.replaceFirst("_m.","_b.");

                    Photo photoObject = new Photo(title,author,authorId,link,tags,photoUrl);
                    mPhotoList.add(photoObject);

                    Log.d(TAG, "onDownloadComplete:/ "+photoObject.toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "onDownloadComplete: error processing json data "+e.getMessage());
                status = DownloadStatus.FAILD_OR_EMPTY;
            }
        }

        if (runningOnSameThread && mCallBack!=null){
            //now inform the caller that processing is done
            mCallBack.onDataAvailable(mPhotoList,status);
        }
        Log.d(TAG, "onDownloadComplete:  ends");
    }
}
