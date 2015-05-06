package com.l.recorder.presenter;

import android.os.Bundle;
import com.l.recorder.ui.IRecorderView;

/**
 * Created by dupengtao on 15-5-5.
 */
public interface IRecordPresenter {


    void onViewCreate(Bundle savedState);

    void onViewDestroy();

    void onTakeView(IRecorderView view,int sign);

    //void onViewSave(Bundle state);
    //
    //void onDropView(int sign);
    //
    //void onChangedView(int sign);
}
