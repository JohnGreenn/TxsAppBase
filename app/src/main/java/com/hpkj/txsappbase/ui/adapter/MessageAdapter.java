package com.hpkj.txsappbase.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hpkj.txsappbase.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * desc：
 * author：Glq
 * time：2021/07/28 10:06
 */
public class MessageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public MessageAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper,String data) {

        helper.setText(R.id.id_num,data);
    }
}
