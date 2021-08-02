package com.hpkj.txsappbase.ui.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hpkj.txsappbase.R;
import com.hpkj.txsappbase.app.TitleBarFragment;
import com.hpkj.txsappbase.base.BaseAdapter;
import com.hpkj.txsappbase.databinding.FragmentMeassgeBinding;
import com.hpkj.txsappbase.http.model.HttpData;
import com.hpkj.txsappbase.http.request.GetGoodsListApi;
import com.hpkj.txsappbase.http.response.ShopGoodsListBean;
import com.hpkj.txsappbase.other.EncryptUtil;
import com.hpkj.txsappbase.ui.activity.MainActivity;
import com.hpkj.txsappbase.ui.adapter.GoodsListAdapter;
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
public class MessageFragment extends TitleBarFragment<MainActivity, FragmentMeassgeBinding> implements OnRefreshLoadMoreListener,
        BaseAdapter.OnItemClickListener {

    private MessageAdapter mAdapter;
    private int index = 1;
    private int size = 20;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meassge;
    }

    @Override
    protected void initView() {

        mAdapter = new MessageAdapter(getAttachActivity());
        mAdapter.setOnItemClickListener(this);
        binding.messRecycler.setAdapter(mAdapter);
        binding.messRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rlMessRefresh.setOnRefreshLoadMoreListener(this);

    }

    @Override
    protected void initData() {

        EasyConfig.getInstance().addHeader("RequestId",EncryptUtil.getRequestId(5).trim());
        EasyHttp.get(this)
                .api(new GetGoodsListApi()
                        .setLabel("11")
                        .setSortType(-1)
                        .setIndex(index)
                        .setPageSize(size))
                .request(new HttpCallback<HttpData<ArrayList<ShopGoodsListBean>>>(this){
                    @Override
                    public void onSucceed(HttpData<ArrayList<ShopGoodsListBean>> result) {
                        //ToastUtils.show("商品列表请求成功");
                        //data = result.getData();
                        if(index==1){
                            mAdapter.setData(result.getData());
                        } else {
                            if(result.getData() == null || result.getData().size() == 0){
                                binding.rlMessRefresh.finishLoadMore();
                                binding.rlMessRefresh.finishLoadMoreWithNoMoreData();//https://github.com/scwang90/SmartRefreshLayout/issues/1003
                                binding.rlMessRefresh.setNoMoreData(true);
                            } else {
                                mAdapter.addData(result.getData());
                                binding.rlMessRefresh.finishLoadMore();
                            }
                        }

                    }
                });
        //return data;
        //mAdapter.setData(analogData());
    }

    List<ShopGoodsListBean> data = new ArrayList<>();



    /**
     * 请求网络数据
     */
    private List<ShopGoodsListBean> analogData() {
        EasyHttp.get(this)
                .api(new GetGoodsListApi()
                        .setLabel("11")
                        .setSortType(-1)
                        .setIndex(index)
                        .setPageSize(size))
                .request(new HttpCallback<HttpData<ArrayList<ShopGoodsListBean>>>(this){
                    @Override
                    public void onSucceed(HttpData<ArrayList<ShopGoodsListBean>> result) {
                        //ToastUtils.show("商品列表请求成功");
                        data = result.getData();
                        //mAdapter.setData(result.getData());
                    }
                });
        return data;
    }

    @Override
    public void onItemClick(RecyclerView recyclerView,View itemView,int position) {
        toast(mAdapter.getItem(position));
    }


    @Override
    public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
        //postDelayed(() -> {
            mAdapter.clearData();
            index = 1;
            //mAdapter.setData(analogData());
            initData();
            binding.rlMessRefresh.finishRefresh();
        //}, 500);
    }

    @Override
    public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            index++;
            //mAdapter.addData(analogData());
            initData();

            //mAdapter.setLastPage(mAdapter.getItemCount() >= 100);
            //binding.rlMessRefresh.setNoMoreData(mAdapter.isLastPage());
        }, 500);
    }
}
