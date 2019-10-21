package com.ingic.ezhalbatek.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.NavigationEnt;
import com.ingic.ezhalbatek.helpers.BasePreferenceHelper;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 12/15/2017.
 */

public class NavigationBinder extends RecyclerViewBinder<NavigationEnt> {
    private RecyclerItemListener listener;
    private BasePreferenceHelper preferenceHelper;

    public NavigationBinder(RecyclerItemListener listener,BasePreferenceHelper preferenceHelper) {
        super(R.layout.row_item_nav);
        this.listener = listener;
        this.preferenceHelper=preferenceHelper;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final NavigationEnt entity, final int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        if(preferenceHelper.isLanguageArabian()){
            holder.mainFrame.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }else{
            holder.mainFrame.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        holder.imgSelected.setImageResource(entity.getResId());
        holder.txtHome.setText(entity.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClicked(entity, position,view.getId());
                }
            }
        });
    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.img_selected)
        ImageView imgSelected;
        @BindView(R.id.txt_home)
        AnyTextView txtHome;
        @BindView(R.id.mainFrame)
        RelativeLayout mainFrame;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
