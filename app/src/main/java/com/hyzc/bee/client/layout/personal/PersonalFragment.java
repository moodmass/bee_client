package com.hyzc.bee.client.layout.personal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.balysv.materialripple.MaterialRippleLayout;
import com.hy.bee.common.RiseNumberTextView;
import com.hyzc.bee.client.R;

import net.steamcrafted.materialiconlib.MaterialIconView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/11/29.
 */
public class PersonalFragment extends Fragment {

    @BindView(R.id.status_bar_view)
    View statusBarView;
    @BindView(R.id.profile_image_back)
    CircleImageView profileImageBack;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.icon_left)
    MaterialIconView iconLeft;
    @BindView(R.id.records_1)
    RiseNumberTextView records1;
    @BindView(R.id.records)
    RiseNumberTextView records;
    @BindView(R.id.expense)
    RiseNumberTextView expense;
    @BindView(R.id.record_layout)
    MaterialRippleLayout recordLayout;
    @BindView(R.id.device_layout_panel)
    LinearLayout deviceLayoutPanel;

    private ExpandableLayout deviceExpandableLayout;

    @BindView(R.id.switcher_device)
    MaterialIconView switcherDevice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, view);
        initDeviceLayout(view);
        return view;
    }

    private void initDeviceLayout(View view) {
        deviceExpandableLayout = (ExpandableLayout) view.findViewById(R.id.device_expandable_layout);
        deviceExpandableLayout.setSwitcher(switcherDevice);
        deviceExpandableLayout.setExpandInterpolator(new AccelerateDecelerateInterpolator());
        deviceExpandableLayout.setCollapseInterpolator(new AccelerateDecelerateInterpolator());
        deviceExpandableLayout.setExpandDuration(400);
        deviceExpandableLayout.setCollapseDuration(400);

        deviceExpandableLayout.setOnStateChangedListener(new ExpandableLayout.OnStateChangedListener() {
            @Override
            public void onPreExpand() {
                Log.d("ExpandableLayout", "onPreExpand");
            }

            @Override
            public void onPreCollapse() {
                Log.d("ExpandableLayout", "onPreCollapse");
            }

            @Override
            public void onExpanded() {
                Log.d("ExpandableLayout", "onExpanded");
            }

            @Override
            public void onCollapsed() {
                Log.d("ExpandableLayout", "onCollapsed");
            }
        });
    }

    @OnClick({R.id.profile_image, R.id.icon_left,R.id.device_layout_panel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                break;
            case R.id.icon_left:
                break;
            case R.id.device_layout_panel:
                deviceExpandableLayout.toggle();
                break;
        }
    }

    @OnClick(R.id.device_layout_panel)
    public void onClick() {
    }
}
