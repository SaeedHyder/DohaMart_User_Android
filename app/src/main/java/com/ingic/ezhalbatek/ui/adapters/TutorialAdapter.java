package com.ingic.ezhalbatek.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.activities.MainActivity;
import com.ingic.ezhalbatek.interfaces.TutorialInterface;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class TutorialAdapter extends PagerAdapter {
    MainActivity context;
    ArrayList<String> images;
    LayoutInflater layoutInflater;
    ImageLoader imageLoader;
    private TutorialInterface tutorialInterface;


    public TutorialAdapter(MainActivity context, ArrayList<String> images, TutorialInterface tutorialInterface) {
        this.context = context;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = ImageLoader.getInstance();
        this.tutorialInterface=tutorialInterface;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.tutorail_row_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        imageLoader.displayImage(images.get(position), imageView);

        tutorialInterface.onClick();

        container.addView(itemView);

       /* imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });*/

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
