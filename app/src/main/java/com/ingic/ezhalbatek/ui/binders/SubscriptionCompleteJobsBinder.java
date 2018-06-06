package com.ingic.ezhalbatek.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/2/18.
 */
public class SubscriptionCompleteJobsBinder extends RecyclerViewBinder<String> {
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
    public void bindView(String entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
