package com.ingic.ezhalbatek.helpers;

import android.util.Log;
import android.view.View;

import com.ingic.ezhalbatek.activities.DockActivity;
import com.ingic.ezhalbatek.entities.ResponseWrapper;
import com.ingic.ezhalbatek.fragments.LoginFragment;
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
    private BasePreferenceHelper preferenceHelper;

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
                    preferenceHelper= new BasePreferenceHelper(context);
                    if (response != null && response.body() != null && response.body().getResponse() != null) {
                        if (!response.body().isIs_blocked()) {
                            if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                                serviceResponseLisener.ResponseSuccess(response.body().getResult(), tag, response.body().getMessage());
                            } else {
                                serviceResponseLisener.ResponseFailureNoResonse(tag);
                                if (!tag.equals(WebServiceConstants.FACEBOOKLOGIN)) {
                                    UIHelper.showShortToastInCenter(context, response.body().getMessage());
                                }
                            }
                        } else {
                            DialogHelper dialogHelper = new DialogHelper(context);
                            dialogHelper.BlockAccountDialoge(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogHelper.hideDialog();
                                }
                            });
                            dialogHelper.showDialog();
                            if (preferenceHelper.isLogin()) {
                                serviceResponseLisener.ResponseBlockAccount(tag);
                            }

                        }
                    } else {
                        UIHelper.showShortToastInCenter(context, "No response from server");
                    }

                }

                @Override
                public void onFailure(Call<ResponseWrapper<T>> call, Throwable t) {
                    context.onLoadingFinished();
                    t.printStackTrace();
                    serviceResponseLisener.ResponseFailure(tag);
                    Log.e(ServiceHelper.class.getSimpleName() + " by tag: " + tag, t.toString());
                }
            });
        }
    }

}
