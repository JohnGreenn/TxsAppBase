package com.hpkj.txsappbase.ui.fragment;

import com.hpkj.txsappbase.R;
import com.hpkj.txsappbase.app.AppFragment;
import com.hpkj.txsappbase.base.FragmentPagerAdapter;
import com.hpkj.txsappbase.ui.activity.MainActivity;

/**
 * desc：首页 Fragment
 * author：Glq
 * time：2021/07/23 10:26
 */
public final class HomeFragment extends AppFragment<MainActivity> {

    private FragmentPagerAdapter<AppFragment<?>> mPagerAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
