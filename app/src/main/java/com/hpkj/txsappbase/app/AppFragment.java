package com.hpkj.txsappbase.app;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.listener.OnHttpListener;
import com.hpkj.txsappbase.action.ToastAction;
import com.hpkj.txsappbase.base.BaseFragment;
import com.hpkj.txsappbase.http.model.HttpData;

import okhttp3.Call;

/**
 *    author : glq
 *    desc   : 业务 Fragment 基类
 */
public abstract class AppFragment<A extends AppActivity> extends BaseFragment<A>
        implements ToastAction, OnHttpListener<Object> {

    /** 状态栏沉浸 */
    private ImmersionBar mImmersionBar;



    /**
     * 当前加载对话框是否在显示中
     */
    public boolean isShowDialog() {
        A activity = getAttachActivity();
        if (activity != null) {
            return activity.isShowDialog();
        }

        return false;
    }

    /**
     * 显示加载对话框
     */
    public void showDialog() {
        A activity = getAttachActivity();
        if (activity != null) {
            activity.showDialog();
        }
    }

    /**
     * 隐藏加载对话框
     */
    public void hideDialog() {
        A activity = getAttachActivity();
        if (activity != null) {
            activity.hideDialog();
        }
    }

    /**
     * {@link OnHttpListener}
     */

    @Override
    public void onStart(Call call) {
        showDialog();
    }

    @Override
    public void onSucceed(Object result) {
        if (result instanceof HttpData) {
            toast(((HttpData<?>) result).getMessage());
        }
    }

    @Override
    public void onFail(Exception e) {
        toast(e.getMessage());
    }

    @Override
    public void onEnd(Call call) {
        hideDialog();
    }
}