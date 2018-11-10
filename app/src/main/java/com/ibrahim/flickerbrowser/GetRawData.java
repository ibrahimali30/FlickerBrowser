package com.ibrahim.flickerbrowser;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus {IDLE , PROCESSING , NOT_INITIALIZED, OK ,FAILD_OR_EMPTY}

class GetRawData extends AsyncTask<String , String ,String> {
    private static final String TAG = "GetRawData";

    private DownloadStatus mDownloadStatus;
    private final OndownloadComplete mCallback;

    interface OndownloadComplete {
        void onDownloadComplete(String data ,DownloadStatus status);
    }

    public GetRawData(OndownloadComplete callback) {
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mCallback = callback;

    }

    void runOnSameThread (String s){
        Log.d(TAG, "runOnSameThread: starts");
        onPostExecute(doInBackground(s));
        Log.d(TAG, "runOnSameThread: ends");
    }

    @Override
    protected void onPostExecute(String s) {
//        Log.d(TAG, "onPostExecute: parameter (json) = "+s);
        if (mCallback != null){
            mCallback.onDownloadComplete(s,mDownloadStatus);
        }
        Log.d(TAG, "onPostExecute: ends");

    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader =null;

        if (strings==null){
            mDownloadStatus=DownloadStatus.NOT_INITIALIZED;
            return null;
        }

        try {
            mDownloadStatus=DownloadStatus.PROCESSING;

            URL url= new URL(strings[0] );
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: the response code was "+response);

            StringBuilder result = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while (null != (line=reader.readLine())){
                result.append(line).append("\n");
            }

            mDownloadStatus = DownloadStatus.OK;
            return result.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: invalid url "+e.getMessage() );
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: io exception reading data "+e.getMessage() );
        }catch (SecurityException e){
            Log.e(TAG, "doInBackground: permissino needed"+e.getMessage());
        }

        finally {
            if (connection !=null){
                connection.disconnect();
            }
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: error closing stream"+e.getMessage());
                }
            }
        }

        mDownloadStatus = DownloadStatus.FAILD_OR_EMPTY;

        return null;
    }
}






















