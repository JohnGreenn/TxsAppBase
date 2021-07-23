package com.hpkj.txsappbase.action;

import androidx.annotation.StringRes;
import com.hjq.toast.ToastUtils;


/**
 *    author : Glq
 *    desc   : 吐司意图
 */
public interface ToastAction {

    default void toast(CharSequence text) {
        ToastUtils.show(text);
    }

    default void toast(@StringRes int id) {
        ToastUtils.show(id);
    }

    default void toast(Object object) {
        ToastUtils.show(object);
    }
}