package com.hpkj.txsappbase.ui.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.hpkj.txsappbase.R;
import com.hpkj.txsappbase.app.TitleBarFragment;
import com.hpkj.txsappbase.databinding.FragmentMeassgeBinding;
import com.hpkj.txsappbase.ui.activity.MainActivity;
import com.hpkj.txsappbase.ui.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * desc：
 * author：Glq
 * time：2021/07/27 17:08
 */
public class MessageFragment extends TitleBarFragment<MainActivity, FragmentMeassgeBinding> {

    private List<String> mDatas;
    private MessageAdapter mAdapter;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meassge;
    }

    @Override
    protected void initView() {

        binding.messRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MessageAdapter(R.layout.test_item);
        binding.messRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
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


}
