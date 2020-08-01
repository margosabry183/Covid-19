package margo.covid.detector.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Mario Gamal on 7/31/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 */
public class ProgressDialogManager {

    private static ProgressDialogManager manager = null;

    private Context context;

    private ProgressDialog pDialog = null;

    private ProgressDialogManager(Context context) {
        this.context = context;
    }

    public static ProgressDialogManager getInstance(Context context) {
        if (manager == null)
            manager = new ProgressDialogManager(context);
        return manager;
    }

    public void showDialog(String msg) {
        if (pDialog == null)
            pDialog = new ProgressDialog(this.context);
        pDialog.setMessage(msg);
        pDialog.show();
    }

    public void closeDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }
}