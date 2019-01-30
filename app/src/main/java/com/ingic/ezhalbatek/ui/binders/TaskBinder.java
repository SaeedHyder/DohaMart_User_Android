package com.ingic.ezhalbatek.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.ServiceStatus.AdditionalJob;
import com.ingic.ezhalbatek.entities.ServicsList;
import com.ingic.ezhalbatek.helpers.BasePreferenceHelper;
import com.ingic.ezhalbatek.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TaskBinder extends RecyclerViewBinder<AdditionalJob> {
    BasePreferenceHelper preferenceHelper;


    public TaskBinder(BasePreferenceHelper preferenceHelper) {
        super(R.layout.row_item_selectedjobs);
        this.preferenceHelper = preferenceHelper;
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(AdditionalJob entity, final int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.txtJobselectedAmount.setVisibility(View.VISIBLE);
        holder.deleteText.setVisibility(View.GONE);
        holder.txtJobselectedtext.setText(entity.getItem().getName() + "");
        Double amount = Double.valueOf(entity.getItem().getAmount());
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

