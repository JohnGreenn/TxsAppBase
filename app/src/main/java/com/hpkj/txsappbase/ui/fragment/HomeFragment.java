package com.hpkj.txsappbase.ui.fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;

import androidx.core.content.ContextCompat;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hpkj.txsappbase.R;
import com.hpkj.txsappbase.app.AppFragment;
import com.hpkj.txsappbase.app.TitleBarFragment;
import com.hpkj.txsappbase.base.FragmentPagerAdapter;
import com.hpkj.txsappbase.databinding.FragmentHomeBinding;
import com.hpkj.txsappbase.http.model.HttpData;
import com.hpkj.txsappbase.http.request.BannerApi;
import com.hpkj.txsappbase.http.response.StartBean;
import com.hpkj.txsappbase.ui.activity.MainActivity;
import com.hpkj.txsappbase.ui.adapter.ImageAdapter;
import com.hpkj.txsappbase.ui.utils.ClickUtil;
import com.hpkj.txsappbase.widget.layout.XCollapsingToolbarLayout;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.AlphaPageTransformer;

import java.util.ArrayList;

/**
 * desc：首页 Fragment
 * author：Glq
 * time：2021/07/23 10:26
 */
public final class HomeFragment extends TitleBarFragment<MainActivity, FragmentHomeBinding> implements XCollapsingToolbarLayout.OnScrimsListener{

    private FragmentPagerAdapter<AppFragment<?,?>> mPagerAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

        mPagerAdapter = new FragmentPagerAdapter<>(this);
        mPagerAdapter.addFragment(StatusFragment.newInstance(), "列表演示");
        mPagerAdapter.addFragment(TestListFragment.newInstance(), "TestList演示");
        binding.vpHomePager.setAdapter(mPagerAdapter);
        binding.tlHomeTab.setupWithViewPager(binding.vpHomePager);

        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
        ImmersionBar.setTitleBar(getAttachActivity(), binding.tbHomeTitle);

        //设置渐变监听
        binding.ctlHomeBar.setOnScrimsListener(this);

        binding.setClick(new ClickUtil());

    }

    @Override
    protected void initData() {

        EasyHttp.post(this)
                .api(new BannerApi()
                        .setBname("home_banner"))
                .request(new HttpCallback<HttpData<ArrayList<StartBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<ArrayList<StartBean>> result) {
                        ToastUtils.show("banner 请求成功，请看日志");
                        binding.banner1.setAdapter(new ImageAdapter(result.getData()));
                        binding.banner1.setIndicator(new CircleIndicator(getContext()));
                        //添加画廊效果
                        binding.banner1.setBannerGalleryEffect(18, 10);
                        //添加透明效果(画廊配合透明效果更棒)
                        binding.banner1.addPageTransformer(new AlphaPageTransformer());

                    }
                });
    }

    /**
     * CollapsingToolbarLayout 渐变回调
     *
     * {@link XCollapsingToolbarLayout.OnScrimsListener}
     */
    @SuppressLint("RestrictedApi")
    @Override
    public void onScrimsStateChange(XCollapsingToolbarLayout layout,boolean shown) {
        if (shown) {
            binding.tvHomeAddress.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.black));
            binding.tvHomeHint.setBackgroundResource(R.drawable.home_search_bar_gray_bg);
            binding.tvHomeHint.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.black60));
            binding.ivHomeSearch.setSupportImageTintList(ColorStateList.valueOf(getColor(R.color.common_icon_color)));
            getStatusBarConfig().statusBarDarkFont(true).init();
        } else {
            binding.tvHomeAddress.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.white));
            binding.tvHomeHint.setBackgroundResource(R.drawable.home_search_bar_transparent_bg);
            binding.tvHomeHint.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.white60));
            binding.ivHomeSearch.setSupportImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
            getStatusBarConfig().statusBarDarkFont(false).init();
        }
    }

}
