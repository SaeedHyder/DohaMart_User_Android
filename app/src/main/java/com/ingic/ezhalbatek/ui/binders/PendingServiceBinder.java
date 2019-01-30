package com.ingic.ezhalbatek.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.ServiceStatus.Service;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/6/18.
 */
public class PendingServiceBinder extends RecyclerViewBinder<Service> {
    private RecyclerItemListener itemClickListener;
    private boolean isCompleted;

    public PendingServiceBinder(RecyclerItemListener itemClickListener, boolean isCompleted) {
        super(R.layout.row_item_service);
        this.itemClickListener = itemClickListener;
        this.isCompleted = isCompleted;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(Service entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        if (isCompleted) {
            holder.btnCallRate.setText(context.getResources().getString(R.string.rate_technicain));
        } else {
            holder.btnCallRate.setText(context.getResources().getString(R.string.call_technician));
        }

        holder.txtJobTitle.setText(entity.getJobTitle() + "");
        if (entity.getAssignTechnician() != null) {
            holder.btnCallRate.setAlpha((float) 1);
            holder.txtTechnicainName.setText(entity.getAssignTechnician().getTechnicianDetails().getFullName() != null ? entity.getAssignTechnician().getTechnicianDetails().getFullName() : "-");
            holder.txtTechnicianNumber.setText(entity.getAssignTechnician().getTechnicianDetails().getPhoneNo() != null ? entity.getAssignTechnician().getTechnicianDetails().getCountryCode()+entity.getAssignTechnician().getTechnicianDetails().getPhoneNo() : "-");


            if (entity.getStatus() == 1 || entity.getStatus() == 2) {
                holder.txtStatus.setText(AppConstants.Technician_Assigned);
            } else if (entity.getStatus() == 0) {
                holder.txtStatus.setText(AppConstants.Technician_Not_Assigned);
            } else if (entity.getStatus() == 4) {
                holder.txtStatus.setText(AppConstants.Cancelled);
                holder.btnCallRate.setEnabled(false);
                holder.btnCallRate.setAlpha((float) 0.6);
            } else {
                holder.btnCallRate.setEnabled(true);
                holder.btnCallRate.setAlpha((float) 1);
                holder.txtStatus.setText(AppConstants.Completed);
            }


            if (entity.getFeedback() == null) {
                holder.btnCallRate.setOnClickListener(v -> {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClicked(entity, position, v.getId());
                    }
                });
            } else {
                holder.btnCallRate.setAlpha((float) 0.6);
                holder.btnCallRate.setOnClickListener(v -> {
                    if (entity.getStatus() != 4) {
                        UIHelper.showShortToastInCenter(context, "Already rating submitted");
                    }
                });
            }

        } else {
            if (!isCompleted) {
                holder.btnCallRate.setAlpha((float) 0.6);
                holder.btnCallRate.setOnClickListener(v -> {
                    UIHelper.showShortToastInCenter(context, "Technician is not assigned");
                });
            } else {
                if (entity.getFeedback() == null) {
                    holder.btnCallRate.setOnClickListener(v -> {
                        if (itemClickListener != null) {
                            itemClickListener.onItemClicked(entity, position, v.getId());
                        }
                    });
                } else {
                    holder.btnCallRate.setAlpha((float) 0.6);
                    holder.btnCallRate.setOnClickListener(v -> {
                        UIHelper.showShortToastInCenter(context, "Already rating submitted");
                    });
                }

            }

            if (entity.getStatus() == 1 || entity.getStatus() == 2) {
                holder.txtStatus.setText(AppConstants.Technician_Assigned);
            } else if (entity.getStatus() == 0) {
                holder.txtStatus.setText(AppConstants.Technician_Not_Assigned);
            } else if (entity.getStatus() == 4) {
                holder.txtStatus.setText(AppConstants.Cancelled);
                holder.btnCallRate.setEnabled(false);
                holder.btnCallRate.setAlpha((float) 0.6);
            } else {
                holder.btnCallRate.setEnabled(true);
                holder.btnCallRate.setAlpha((float) 1);
                holder.txtStatus.setText(AppConstants.Completed);
            }
        }


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
        @BindView(R.id.txtStatus)
        AnyTextView txtStatus;
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
