package com.hpkj.txsappbase.ui.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hpkj.txsappbase.R;
import com.hpkj.txsappbase.app.TitleBarFragment;
import com.hpkj.txsappbase.databinding.FragmentFindBinding;
import com.hpkj.txsappbase.databinding.FragmentMeassgeBinding;
import com.hpkj.txsappbase.http.model.HttpData;
import com.hpkj.txsappbase.http.request.GetGoodsListApi;
import com.hpkj.txsappbase.http.response.ShopGoodsListBean;
import com.hpkj.txsappbase.ui.activity.MainActivity;
import com.hpkj.txsappbase.ui.adapter.MessageAdapter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * desc：
 * author：Glq
 * time：2021/07/27 17:08
 */
public class FindFragment extends TitleBarFragment<MainActivity, FragmentFindBinding> implements OnRefreshLoadMoreListener {

    private List<String> mDatas;
    private MessageAdapter mAdapter;
    private int index = 1;
    private int size = 20;

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView() {

        binding.findRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MessageAdapter(R.layout.test_item);
        binding.findRecycler.setAdapter(mAdapter);
        binding.rlFindRefresh.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        EasyHttp.get(this)
                .api(new GetGoodsListApi()
                .setLabel("11")
                .setSortType(-1)
                .setIndex(index)
                .setPageSize(size))
                .request(new HttpCallback<HttpData<ArrayList<ShopGoodsListBean>>>(this){
                    @Override
                    public void onSucceed(HttpData<ArrayList<ShopGoodsListBean>> result) {
                        //super.onSucceed(result);
                        ToastUtils.show("商品列表请求成功");
                    }
                });


        mAdapter.setNewInstance(analogData());
    }

    /**
     * 模拟数据
     */
    private List<String> analogData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'Z'; i++) {
            mDatas.add("" + (char) i);
        }
        return mDatas;
    }


    @Override
    public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            index++;
            initData();
            binding.rlFindRefresh.finishLoadMore();
        }, 1000);
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
}
