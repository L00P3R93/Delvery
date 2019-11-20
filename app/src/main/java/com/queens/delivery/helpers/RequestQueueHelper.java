package com.queens.delivery.helpers;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueHelper {
    private static RequestQueueHelper mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private RequestQueueHelper(Context context){
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized RequestQueueHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new RequestQueueHelper(context);
        }
        return mInstance;
    }


    private RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());

        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

}
