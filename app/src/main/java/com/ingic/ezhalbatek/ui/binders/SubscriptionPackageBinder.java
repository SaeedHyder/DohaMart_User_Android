package com.ingic.ezhalbatek.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.SubscriptionPackagesEnt;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SubscriptionPackageBinder extends RecyclerViewBinder<SubscriptionPackagesEnt> {
    private RecyclerItemListener itemClickListener;
    private boolean isCompleted;

    public SubscriptionPackageBinder(RecyclerItemListener itemClickListener) {
        super(R.layout.package_row_item);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(SubscriptionPackagesEnt entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.txtPackageName.setText(entity.getName() + "");
        holder.txtPackageNo.setText(String.valueOf(position + 1));
        holder.btnPlatinium.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(entity, position, v.getId());
            }
        });
    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_package_no)
        AnyTextView txtPackageNo;
        @BindView(R.id.txt_package_name)
        AnyTextView txtPackageName;
        @BindView(R.id.btn_platinium)
        LinearLayout btnPlatinium;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

