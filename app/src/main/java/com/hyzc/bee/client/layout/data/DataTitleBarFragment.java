package com.hyzc.bee.client.layout.data;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyzc.bee.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataTitleBarFragment extends Fragment {
    @BindView(R.id.data_buttons)
    LinearLayout dataButtons;
    @BindView(R.id.button_store)
    ImageButton buttonStore;
    @BindView(R.id.store_name)
    TextView storeName;
    @BindView(R.id.store_area)
    LinearLayout storeArea;


    @BindView(R.id.button_share)
    ImageButton shareBtn;
    @BindView(R.id.button_toggle)
    ImageButton toggleBtn;
    private OnFragmentInteractionListener mListener;

    public DataTitleBarFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_title_bar, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick({R.id.button_toggle, R.id.button_share, R.id.store_area})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_toggle:
                Toast.makeText(getActivity(),"button_toggle",Toast.LENGTH_LONG).show();
                break;
            case R.id.button_share:
                Toast.makeText(getActivity(),"button_share",Toast.LENGTH_LONG).show();
                break;
            case R.id.store_area:
                Toast.makeText(getActivity(),"store_area",Toast.LENGTH_LONG).show();
                break;
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
