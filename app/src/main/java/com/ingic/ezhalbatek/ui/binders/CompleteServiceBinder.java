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
 * Created on 6/6/18.
 */
public class CompleteServiceBinder extends RecyclerViewBinder<String> {
    private RecyclerItemListener itemClickListener;

    public CompleteServiceBinder(RecyclerItemListener itemClickListener) {
        super(R.layout.row_item_service);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(String entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.btnCallRate.setText(context.getResources().getString(R.string.rate_technicain));
        holder.btnCallRate.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(entity, position, v.getId());
            }
        });
        holder.btnDetails.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(entity, position, v.getId());
            }
        });
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtJobTitle)
        AnyTextView txtJobTitle;
        @BindView(R.id.txtTechnicainName)
        AnyTextView txtTechnicainName;
        @BindView(R.id.txtTechnicianNumber)
        AnyTextView txtTechnicianNumber;
        @BindView(R.id.btnCallRate)
        Button btnCallRate;
        @BindView(R.id.btnDetails)
        Button btnDetails;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
