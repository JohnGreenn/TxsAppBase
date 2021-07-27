package com.hpkj.txsappbase.ui.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.hpkj.txsappbase.R;
import com.hpkj.txsappbase.app.AppActivity;
import com.hpkj.txsappbase.app.TitleBarFragment;
import com.hpkj.txsappbase.databinding.TestFragmentBinding;
import com.hpkj.txsappbase.ui.adapter.DemoAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * desc：测试recyclerview
 * author：Glq
 * time：2021/07/27 11:17
 */
public class TestListFragment extends TitleBarFragment<AppActivity, TestFragmentBinding> {

    private List<String> mDatas;
    private DemoAdapter adapter;

    public static TestListFragment newInstance() {
        return new TestListFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.test_fragment;
    }

    @Override
    protected void initView() {

        adapter = new DemoAdapter(R.layout.test_item,mDatas);
        binding.rvTestList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvTestList.setAdapter(adapter);

    }

    @Override
    protected void initData() {

        // 设置新的数据方法
        //adapter.setNewInstance(analogData());
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'Z'; i++) {
            mDatas.add("" + (char) i);
        }

    }

    /**
     * 模拟数据
     */
    private List<String> analogData() {
        List<String> data = new ArrayList<>();
        for (int i = adapter.getItemCount(); i < adapter.getItemCount() + 20; i++) {
            data.add("test你是第" + i + "条目");
        }
        return data;
    }

}
