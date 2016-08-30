package com.tianzunchina.sample.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tianzunchina.android.api.base.TZFragment;
import com.tianzunchina.sample.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class EventActivityFragment extends TZFragment {

    public EventActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }
}
