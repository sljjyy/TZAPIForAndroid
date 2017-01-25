package com.tianzunchina.android.api.network.download;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by zwt on 2017/1/12.
 */

public class TZUpdateDialog {
    private Activity activity;
    private TZAppVersion version;

    public TZUpdateDialog(Activity activity,TZAppVersion version){
        this.activity = activity;
        this.version = version;
    }

    public void showDialog(String tag){
        if(tag==null||tag.isEmpty()){
            tag = "updateDialog";
        }

        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        TZUpdateDialogFragment updateDialogFragment = new TZUpdateDialogFragment();
        if (null != updateDialogFragment) {
            ft.remove(updateDialogFragment);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(TZUpdateDialogFragment.VERSION, version);
        updateDialogFragment.setArguments(bundle);
        updateDialogFragment.show(activity.getFragmentManager(), tag);
    }

}
