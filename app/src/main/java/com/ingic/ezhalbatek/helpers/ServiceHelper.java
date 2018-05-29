package com.ingic.ezhalbatek.helpers;

import android.util.Log;

import com.ingic.ezhalbatek.activities.DockActivity;
import com.ingic.ezhalbatek.entities.ResponseWrapper;
import com.ingic.ezhalbatek.global.WebServiceConstants;
import com.ingic.ezhalbatek.interfaces.webServiceResponseLisener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 7/17/2017.
 */

public class ServiceHelper<T> {
    private webServiceResponseLisener serviceResponseLisener;
    private DockActivity context;

    public ServiceHelper(webServiceResponseLisener serviceResponseLisener, DockActivity conttext) {
        this.serviceResponseLisener = serviceResponseLisener;
        this.context = conttext;
    }

    public void enqueueCall(Call<ResponseWrapper<T>> call, final String tag) {
        if (InternetHelper.CheckInternetConectivityandShowToast(context)) {
            context.onLoadingStarted();
            call.enqueue(new Callback<ResponseWrapper<T>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<T>> call, Response<ResponseWrapper<T>> response) {
                    context.onLoadingFinished();
                    if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                        serviceResponseLisener.ResponseSuccess(response.body().getResult(), tag);
                    } else {
                        UIHelper.showShortToastInCenter(context, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<ResponseWrapper<T>> call, Throwable t) {
                    context.onLoadingFinished();
                    t.printStackTrace();
                    Log.e(ServiceHelper.class.getSimpleName() + " by tag: " + tag, t.toString());
                }
            });
        }
    }

}
