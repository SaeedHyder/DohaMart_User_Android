package com.ingic.ezhalbatek.ui.binders;

import android.content.Context;
import android.view.View;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.ServiceStatus.Subscription;
import com.ingic.ezhalbatek.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/2/18.
 */
public class SubscriptionPendingJobsBinder extends RecyclerViewBinder<Subscription> {

    private boolean isVisit;

    public SubscriptionPendingJobsBinder(boolean isVisit) {
        super(R.layout.row_item_subscription_pending_jobs);
        this.isVisit = isVisit;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(Subscription entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        if (isVisit) {
            holder.txtVisitDateHeading.setText("Next Visit Due :");
        } else {
            holder.txtVisitDateHeading.setText("Visit Date :");
        }

        holder.txtSubscriberID.setText(entity.getSubscription().getId() + "");
        holder.txtPackageType.setText(entity.getSubscription().getTitle() + "");
        holder.txtVisitDate.setText(entity.getVisitDate() + "");


    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtDuration)
        AnyTextView txtDuration;
        @BindView(R.id.txtSubscriberID)
        AnyTextView txtSubscriberID;
        @BindView(R.id.txtPackageType)
        AnyTextView txtPackageType;
        @BindView(R.id.txtVisitDate)
        AnyTextView txtVisitDate;
        @BindView(R.id.txtVisitDateHeading)
        AnyTextView txtVisitDateHeading;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
