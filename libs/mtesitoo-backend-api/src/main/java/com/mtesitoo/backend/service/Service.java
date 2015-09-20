package com.mtesitoo.backend.service;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mtesitoo.backend.service.logic.ILoginService;

/**
 * Created by Nan on 9/13/2015.
 */
public abstract class Service {
    protected Context mContext;
    protected RequestQueue mRequestQueue;
    protected ILoginService mILoginService;

    public Service(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
    }
}