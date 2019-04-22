package com.codercoral.electricitychart.progress;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

public class ProgressBarHandler extends Handler {
    static final int SHOW_PROGRESS_DIALOG = 1;
    static final int DISMISS_PROGRESS_DIALOG = 2;

    private ProgressBar pb;

    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    ProgressBarHandler(Context context, ProgressCancelListener
            mProgressCancelListener, boolean cancelable) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

/*    private void initProgressBar() {
        if (pb == null) {
            pb = new ProgressBar(context);

            pb.setCancelable(cancelable);

            if (cancelable) {
                pb.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }

            if (!pb.isShowing()) {
                pb.show();
            }
        }
    }

    private void dismissProgressBar() {
        if (pb != null) {
            pb.;
            pb = null;
        }
    }*/

/*    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressBar();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressBar();
                break;
        }
    }*/
}
