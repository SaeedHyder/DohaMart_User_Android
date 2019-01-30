package com.ingic.ezhalbatek.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.JobDone.ServicsList;
import com.ingic.ezhalbatek.entities.getAdditionalJobsEnt;
import com.ingic.ezhalbatek.helpers.BasePreferenceHelper;
import com.ingic.ezhalbatek.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class JobDoneBinder extends RecyclerViewBinder<ServicsList> {
    BasePreferenceHelper preferenceHelper;


    public JobDoneBinder(BasePreferenceHelper preferenceHelper) {
        super(R.layout.row_item_selectedjobs);
        this.preferenceHelper = preferenceHelper;
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(ServicsList entity, final int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.deleteText.setVisibility(View.GONE);
        holder.txtJobselectedtext.setText(entity.getServiceDetail().getTitle() + "");
        Double amount = Double.valueOf(entity.getServiceDetail().getAmount());
        holder.txtJobselectedAmount.setText("AED " + amount + "");

    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_jobselectedtext)
        AnyTextView txtJobselectedtext;
        @BindView(R.id.txt_jobselectedAmount)
        AnyTextView txtJobselectedAmount;
        @BindView(R.id.delete_text)
        ImageView deleteText;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
