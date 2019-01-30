package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.NotificationsEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.helpers.DialogHelper;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.interfaces.OnLongTap;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.adapters.ArrayListAdapter;
import com.ingic.ezhalbatek.ui.binders.BinderNotification;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.leolin.shortcutbadger.ShortcutBadger;

import static com.ingic.ezhalbatek.global.WebServiceConstants.DeleteAllNotificaiton;
import static com.ingic.ezhalbatek.global.WebServiceConstants.DeleteNotification;
import static com.ingic.ezhalbatek.global.WebServiceConstants.MarkUnRead;
import static com.ingic.ezhalbatek.global.WebServiceConstants.getNotifications;


public class NotificationsFragment extends BaseFragment implements OnLongTap {

    @BindView(R.id.lv_notification)
    CustomRecyclerView lvNotification;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    private Integer jobDonePosition;
    private ArrayList<NotificationsEnt> notifications;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  adapter = new ArrayListAdapter<>(getDockActivity(), new BinderNotification(getDockActivity(), prefHelper,ItemClicklistener,this));
    }

   /* @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getNotification:
                bindData((ArrayList<NotificationEnt>) result);
                break;
            case WebServiceConstants.NotificaitonCount:
                prefHelper.setNotificationCount(0);
                break;
        }
    }*/

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.showHideDeleteAllButton(true);
        titleBar.setSubHeading(getResString(R.string.notification));
        titleBar.showDeleteAllButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notifications != null && notifications.size() > 0) {
                    dialogHelper.showCommonDialog(v -> {

                        serviceHelper.enqueueCall(webService.deleteAllNotification(prefHelper.getUser().getId()), DeleteAllNotificaiton);
                        dialogHelper.hideDialog();
                    }, R.string.delete_notification_, R.string.delete_all_message, R.string.yes, R.string.no, true, true);
                    dialogHelper.showDialog();
                    dialogHelper.setCancelable(false);

                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), "No notification found");
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefHelper.setNotificationCount(0);
        serviceHelper.enqueueCall(webService.getNotifications(prefHelper.getUser().getId() + ""), getNotifications);


    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case getNotifications:
                notifications = (ArrayList<NotificationsEnt>) result;
                if (notifications.size() <= 0 && getTitleBar() != null) {
                    getTitleBar().showHideDeleteAllButton(false);
                } else if (getTitleBar() != null) {
                    getTitleBar().showHideDeleteAllButton(true);
                }
                ShortcutBadger.applyCount(getDockActivity(), 0);
                bindData(notifications);
                break;

            case DeleteNotification:
                if (jobDonePosition != null) {
                    notifications.remove((int) jobDonePosition);
                    lvNotification.notifyDataSetChanged();
                    if (notifications.size() <= 0) {
                        txtNoData.setVisibility(View.VISIBLE);
                        lvNotification.setVisibility(View.GONE);
                    } else {
                        txtNoData.setVisibility(View.GONE);
                        lvNotification.setVisibility(View.VISIBLE);
                    }
                }
                break;

            case DeleteAllNotificaiton:
                serviceHelper.enqueueCall(webService.getNotifications(prefHelper.getUser().getId() + ""), getNotifications);
                break;
        }
    }

    public void bindData(ArrayList<NotificationsEnt> result) {


        if (result.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvNotification.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvNotification.setVisibility(View.VISIBLE);
            lvNotification.bindRecyclerView(new BinderNotification(getDockActivity(), prefHelper, ItemClicklistener, this), result, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());

        }
     /*   lvNotification.setAdapter(adapter);
        adapter.clearList();
        adapter.addAll(result);*/

    }

    private RecyclerItemListener ItemClicklistener = ((ent, position, id) -> {
        switch (id) {
            case R.id.ll_mainFrame:
                NotificationsEnt entity = (NotificationsEnt) ent;

                serviceHelper.enqueueCall(webService.markUnRead(prefHelper.getUser().getId(), entity.getId()), MarkUnRead);

                if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.JobPush)) {
                    prefHelper.setIsFromPending(true);
                    getDockActivity().replaceDockableFragment(MyServicesFragment.newInstance(), "MyServicesFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.SubscriptionPush)) {
                    prefHelper.setIsFromVisitSubscriber(true);
                    prefHelper.setIsFromInProgressSubscriber(false);
                    prefHelper.setIsFromCompletedSubscriber(false);
                    getDockActivity().replaceDockableFragment(SubscriptionStatusFragment.newInstance(), "SubscriptionStatusFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.JobReminder)) {
                    getDockActivity().replaceDockableFragment(MyServicesFragment.newInstance(), "MyServicesFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.SubscriptionReminder)) {
                    getDockActivity().replaceDockableFragment(SubscriptionStatusFragment.newInstance(), "SubscriptionStatusFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.block_user)) {
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.JOBDONE)) {
                    getDockActivity().replaceDockableFragment(JobDoneAcknowledgeFragment.newInstance(entity.getActionId() + "", false, false), "JobDoneAcknowledgeFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.SubscriptionDone)) {
                    getDockActivity().replaceDockableFragment(JobDoneAcknowledgeFragment.newInstance(entity.getActionId() + "", true, false), "JobDoneAcknowledgeFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.JobAcknowledge)) {
                    getDockActivity().replaceDockableFragment(JobDoneAcknowledgeFragment.newInstance(entity.getActionId() + "", false, true), "JobDoneAcknowledgeFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.SubscriptionAcknowledge)) {
                    getDockActivity().replaceDockableFragment(JobDoneAcknowledgeFragment.newInstance(entity.getActionId() + "", true, true), "JobDoneAcknowledgeFragment");
                } else if (entity.getActionType().equals(AppConstants.ADDITIONALJOBREQUEST)) {
                    getDockActivity().replaceDockableFragment(AdditionalJobAcknowledFragment.newInstance(entity.getActionId()), "AdditionalJobAcknowledFragment");
                } else if (entity.getActionType().equals(AppConstants.ADDITIONALJOBSUBSCRIPTION)) {
                    getDockActivity().replaceDockableFragment(AdditionalJobAcknowledFragment.newInstance(entity.getActionId()), "AdditionalJobAcknowledFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.AdditionalJobAccepted)) {
                    getDockActivity().replaceDockableFragment(AdditionalJobAcknowledFragment.newInstance(entity.getActionId(), AppConstants.ACCEPTED), "AdditionalJobAcknowledFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.AdditionalJobRejected)) {
                    getDockActivity().replaceDockableFragment(AdditionalJobAcknowledFragment.newInstance(entity.getActionId(), AppConstants.REJECTED), "AdditionalJobAcknowledFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.AdditionalJobSubscriptionAccepted)) {
                    getDockActivity().replaceDockableFragment(AdditionalJobAcknowledFragment.newInstance(entity.getActionId(), AppConstants.ACCEPTED), "AdditionalJobAcknowledFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.AdditionalJobSubscriptionRejected)) {
                    getDockActivity().replaceDockableFragment(AdditionalJobAcknowledFragment.newInstance(entity.getActionId(), AppConstants.REJECTED), "AdditionalJobAcknowledFragment");
                }


                break;
        }
    });

    @Override
    public void onClick(NotificationsEnt entity, int position) {

        dialogHelper.showCommonDialog(v -> {
            jobDonePosition = position;
            serviceHelper.enqueueCall(webService.deleteNotification(prefHelper.getUser().getId(), entity.getId() + ""), DeleteNotification);
            dialogHelper.hideDialog();
        }, R.string.delete_notification, R.string.delete_notification_message, R.string.yes, R.string.no, true, true);
        dialogHelper.showDialog();
        dialogHelper.setCancelable(false);
    }
}
