package com.hpkj.txsappbase.ui.fragment;

import com.hpkj.txsappbase.R;
import com.hpkj.txsappbase.app.TitleBarFragment;
import com.hpkj.txsappbase.databinding.FragmentMyBinding;
import com.hpkj.txsappbase.ui.activity.MainActivity;

public class MyFragment extends TitleBarFragment<MainActivity, FragmentMyBinding> {

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
