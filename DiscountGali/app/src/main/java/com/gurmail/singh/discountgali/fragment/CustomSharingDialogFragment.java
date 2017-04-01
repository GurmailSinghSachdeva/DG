package com.gurmail.singh.discountgali.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gurmail.singh.discountgali.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dushyant Singh on 1/20/2017.
 */

public class CustomSharingDialogFragment extends DialogFragment {

    public int a = 0;
    public static final String TAG = CustomSharingDialogFragment.class.getName();
    private AppAdapter adapter;
    private String mContent;
    private String mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Translucent_NoTitleBar);
        mContent = getArguments().getString("content");
        mTitle = getArguments().getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_share, container, false);
        ((TextView)view.findViewById(R.id.txTitle)).setText(mTitle);
        view.findViewById(R.id.outsideview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PackageManager pm = getActivity().getPackageManager();
        Intent main = new Intent(Intent.ACTION_SEND);
        main.putExtra(Intent.EXTRA_TEXT, mContent);
        main.setType("text/plain");
//        dialog_share.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> launchables = pm.queryIntentActivities(main, 0);
        removeFB(launchables);

        Collections.sort(launchables,
                new ResolveInfo.DisplayNameComparator(pm));
        adapter = new AppAdapter(pm, launchables);
        GridView gridView = (GridView) getView().findViewById(R.id.grid_view_share_apps);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResolveInfo launchable = adapter.getItem(position);
                ActivityInfo activity = launchable.activityInfo;
                ComponentName name = new ComponentName(activity.applicationInfo.packageName,
                        activity.name);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, mContent);

                i.setType("text/plain");
//                i.addCategory(Intent.CATEGORY_LAUNCHER);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                i.setComponent(name);

                startActivity(i);
                dismiss();
            }
        });
        //getView().findViewById(R.id.animated_dialog).setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_translate_down_up));
    }

    private void removeFB(List<ResolveInfo> launchables) {
        try {
            if(launchables != null) {
                for (int i = 0; i < launchables.size(); ) {
                    ResolveInfo r = launchables.get(i);
                    if(r != null && r.activityInfo != null
                            && r.activityInfo.taskAffinity != null
                            && r.activityInfo.taskAffinity.contains("facebook")) {
                        launchables.remove(i);
                    } else {
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class AppAdapter extends ArrayAdapter<ResolveInfo> {
        private PackageManager pm = null;

        AppAdapter(PackageManager pm, List<ResolveInfo> apps) {
            super(getActivity(), R.layout.row_share, apps);
            this.pm = pm;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = newView(parent);
            }

            bindView(position, convertView);

            return (convertView);
        }

        private View newView(ViewGroup parent) {
            return (getActivity().getLayoutInflater().inflate(R.layout.row_share, parent, false));
        }

        private void bindView(int position, View row) {
            TextView label = (TextView) row.findViewById(R.id.label);

            label.setText(getItem(position).loadLabel(pm));

            ImageView icon = (ImageView) row.findViewById(R.id.icon);

            icon.setImageDrawable(getItem(position).loadIcon(pm));
        }
    }

}
