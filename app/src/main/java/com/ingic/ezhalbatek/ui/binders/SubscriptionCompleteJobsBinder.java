package com.ingic.ezhalbatek.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.ServiceStatus.Subscription;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/2/18.
 */
public class SubscriptionCompleteJobsBinder extends RecyclerViewBinder<Subscription> {
    private RecyclerItemListener clicklistener;

    public SubscriptionCompleteJobsBinder(RecyclerItemListener listener) {
        super(R.layout.row_item_subscription_complete_jobs);
        clicklistener = listener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(Subscription entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;


        holder.txtVisitDateHeading.setText("Date :");

        holder.txtSubscriberID.setText(entity.getSubscription().getId() + "");
        holder.txtPackageType.setText(entity.getSubscription().getTitle() + "");
        holder.txtVisitDate.setText(entity.getVisitDate() + "");

        if (entity.getStatus().equals("4")) {
            holder.btnRate.setEnabled(false);
            holder.btnRate.setAlpha((float) 0.6);
        } else {
            holder.btnRate.setEnabled(true);
            holder.btnRate.setAlpha((float) 1);
        }

        holder.btnRate.setOnClickListener(v -> {
            if (clicklistener != null) {
                clicklistener.onItemClicked(entity, position, v.getId());
            }
        });
        holder.btnReport.setOnClickListener(v -> {
            if (clicklistener != null) {
                clicklistener.onItemClicked(entity, position, v.getId());
            }
        });
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
        @BindView(R.id.btnReport)
        Button btnReport;
        @BindView(R.id.btnRate)
        Button btnRate;
        @BindView(R.id.txtVisitDateHeading)
        AnyTextView txtVisitDateHeading;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
