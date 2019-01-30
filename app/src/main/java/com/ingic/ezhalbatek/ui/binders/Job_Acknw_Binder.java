package com.ingic.ezhalbatek.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.ServicsList;
import com.ingic.ezhalbatek.entities.getAdditionalJobsEnt;
import com.ingic.ezhalbatek.helpers.BasePreferenceHelper;
import com.ingic.ezhalbatek.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Job_Acknw_Binder extends RecyclerViewBinder<getAdditionalJobsEnt> {
    private Double amount;
    BasePreferenceHelper preferenceHelper;


    public Job_Acknw_Binder(BasePreferenceHelper preferenceHelper) {
        super(R.layout.row_item_additional_job);
        this.preferenceHelper = preferenceHelper;
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(getAdditionalJobsEnt entity, final int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;


        holder.txtJobselectedtext.setText(entity.getItem().getName()+"");
        holder.txtQuantity.setText("Qty " + entity.getQuantity() + "");
      //  Double amount = Double.valueOf(entity.getItem().getAmount())*entity.getItem().getQuantity();
        amount=0.0;
        amount = Double.valueOf(entity.getItem().getAmount());
        holder.txtJobselectedAmount.setText("AED " + amount + "");

    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_jobselectedtext)
        AnyTextView txtJobselectedtext;
        @BindView(R.id.txt_jobselectedAmount)
        AnyTextView txtJobselectedAmount;
        @BindView(R.id.txt_Quantity)
        AnyTextView txtQuantity;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

