package com.obito.recitewords.callbackinterface;

import com.obito.recitewords.bmobobject.Words;

/**
 * Created by obito on 17-8-27.
 */

public interface TranlateCallBack {
    void onFinish(Words words);
    void onError(Exception e);
}
