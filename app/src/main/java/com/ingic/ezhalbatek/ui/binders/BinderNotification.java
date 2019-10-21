package com.ingic.ezhalbatek.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.NotificationsEnt;
import com.ingic.ezhalbatek.helpers.BasePreferenceHelper;
import com.ingic.ezhalbatek.interfaces.OnLongTap;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.ui.viewbinders.abstracts.ViewBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by khan_muhammad on 9/15/2017.
 */

public class BinderNotification extends RecyclerViewBinder<NotificationsEnt> {

    private RecyclerItemListener itemClickListener;
    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;
    private OnLongTap onLongTap;

    public BinderNotification(Context context, BasePreferenceHelper prefHelper, RecyclerItemListener itemClickListener, OnLongTap onLongTap) {
        super(R.layout.row_item_notification_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
        this.itemClickListener = itemClickListener;
        this.onLongTap = onLongTap;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    public void bindView(NotificationsEnt entity, int position, Object holder, Context context) {

        ViewHolder viewHolder = (ViewHolder) holder;
        if (preferenceHelper.isLanguageArabian()) {
            viewHolder.txtJobNotification.setText(entity.getArMessage());
        } else {
            viewHolder.txtJobNotification.setText(entity.getMessage());
        }

        if (entity.getState()!=null && entity.getState().equals("0") || entity.getState().equals("1")) {
            viewHolder.mainFrameLayout.setBackground(context.getResources().getDrawable(R.drawable.unread_background));
          /*  viewHolder.txtJobNotification.setTypeface(Typeface.create(context.getResources().getString(R.string.font_bold), Typeface.BOLD));*/
            viewHolder.txtJobNotification.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            viewHolder.mainFrameLayout.setBackground(context.getResources().getDrawable(R.color.transparent));
        }

        viewHolder.llMainFrame.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(entity, position, v.getId());
            }
        });

        viewHolder.llMainFrame.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onLongTap.onClick(entity, position);
                return true;
            }
        });
               /*if(entity.getCreatedAt() != null && entity.getCreatedAt().length() > 0) {
            viewHolder.tv_date.setText(DateHelper.dateFormat(entity.getCreatedAt(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT));
            viewHolder.tv_time.setText(DateHelper.dateFormat(entity.getCreatedAt(), DateHelper.TIME_FORMAT, DateHelper.DATE_TIME_FORMAT));
        }*/
    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_Notificationlogo)
        ImageView ivNotificationlogo;
        @BindView(R.id.txt_jobNotification)
        AnyTextView txtJobNotification;
        @BindView(R.id.iv_next)
        ImageView ivNext;
        @BindView(R.id.ll_mainFrame)
        LinearLayout llMainFrame;
        @BindView(R.id.mainFrameLayout)
        LinearLayout mainFrameLayout;
        @BindView(R.id.txt_line)
        View txtLine;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}