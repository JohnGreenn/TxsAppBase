package com.hpkj.txsappbase.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestInterceptor;
import com.hjq.http.listener.HttpCallback;
import com.hjq.http.model.HttpHeaders;
import com.hjq.http.model.HttpParams;
import com.hjq.toast.ToastUtils;
import com.hpkj.txsappbase.R;
import com.hpkj.txsappbase.app.TitleBarFragment;
import com.hpkj.txsappbase.databinding.FragmentFindBinding;
import com.hpkj.txsappbase.databinding.FragmentMeassgeBinding;
import com.hpkj.txsappbase.databinding.FragmentTopBannerBinding;
import com.hpkj.txsappbase.http.model.HttpData;
import com.hpkj.txsappbase.http.request.BannerApi;
import com.hpkj.txsappbase.http.request.GetGoodsListApi;
import com.hpkj.txsappbase.http.response.ShopGoodsListBean;
import com.hpkj.txsappbase.http.response.StartBean;
import com.hpkj.txsappbase.other.EncryptUtil;
import com.hpkj.txsappbase.ui.activity.MainActivity;
import com.hpkj.txsappbase.ui.adapter.GoodsListAdapter;
import com.hpkj.txsappbase.ui.adapter.ImageAdapter;
import com.hpkj.txsappbase.ui.adapter.MessageAdapter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.AlphaPageTransformer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * desc：brvah 使用范例
 * author：Glq
 * time：2021/07/27 17:08
 */
public class FindFragment extends TitleBarFragment<MainActivity, FragmentFindBinding> implements OnRefreshLoadMoreListener {

    private List<String> mDatas;
    private GoodsListAdapter mAdapter;
    private int index = 1;
    private int size = 20;
    FragmentTopBannerBinding itemTop = null;
    Banner banner;

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView() {
        binding.findRecycler.setLayoutManager(new GridLayoutManager(getContext(),2));
        mAdapter = new GoodsListAdapter();
        binding.findRecycler.setAdapter(mAdapter);
        binding.rlFindRefresh.setOnRefreshLoadMoreListener(this);




    }

    @Override
    protected void initData() {

        EasyHttp.post(this)
                .api(new BannerApi()
                        .setBname("home_banner"))
                .request(new HttpCallback<HttpData<ArrayList<StartBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<ArrayList<StartBean>> result) {
                        //ToastUtils.show("banner 请求成功，请看日志");
                        /*添加头部*/
                        //View view = getLayoutInflater().inflate(R.layout.fragment_top_banner, null, false);
                        //banner = view.getRootView().findViewById(R.id.shop_banner);
                        itemTop = DataBindingUtil.inflate(LayoutInflater.from(getActivity()),R.layout.fragment_top_banner,getActivity().findViewById(android.R.id.content),false);

                        if(mAdapter.getHeaderLayoutCount()==0) {
                            //mAdapter.removeHeaderView(itemTop.getRoot());
                            mAdapter.addHeaderView(itemTop.getRoot());
                        }

                        itemTop.shopBanner.setAdapter(new ImageAdapter(result.getData()));
                        itemTop.shopBanner.setIndicator(new CircleIndicator(getContext()));
                        //添加画廊效果
                        itemTop.shopBanner.setBannerGalleryEffect(18, 10);
                        //添加透明效果(画廊配合透明效果更棒)
                        itemTop.shopBanner.addPageTransformer(new AlphaPageTransformer());
                    }
                });

        //EasyConfig.getInstance().addHeader("RequestId",EncryptUtil.getRequestId(5).trim());
        EasyHttp.get(this)
                .api(new GetGoodsListApi()
                .setHeaderPass(EncryptUtil.getRequestId(5).trim())
                .setLabel("11")
                .setSortType(-1)
                .setIndex(index)
                .setPageSize(size))
                .request(new HttpCallback<HttpData<ArrayList<ShopGoodsListBean>>>(this){
                    @Override
                    public void onSucceed(HttpData<ArrayList<ShopGoodsListBean>> result) {
                        //super.onSucceed(result);
                        //ToastUtils.show("商品列表请求成功");
                        if(index==1){
                            mAdapter.setNewInstance(result.getData());
                        } else {
                            if(result.getData() == null || result.getData().size() == 0){
                                binding.rlFindRefresh.finishRefresh();
                                binding.rlFindRefresh.finishLoadMoreWithNoMoreData();//解决没有更多数据的时候，还显示了loading
                                binding.rlFindRefresh.setNoMoreData(true);
                            } else {
                                mAdapter.addData(result.getData());
                                binding.rlFindRefresh.finishLoadMore();
                            }
                        }

                    }
                });



        //mAdapter.setNewInstance(analogData());
    }

    /**
     * 模拟数据
     */
    private List<String> analogData() {
        mDatas = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            mDatas.add("hello:" +i);
        }
        return mDatas;
    }

    @Override
    public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            //mAdapter.clear;
            index = 1;
            initData();
            binding.rlFindRefresh.finishRefresh();
        }, 1000);
    }

    @Override
    public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            index++;
            initData();
            binding.rlFindRefresh.finishRefresh();
        }, 1000);
    }


}
