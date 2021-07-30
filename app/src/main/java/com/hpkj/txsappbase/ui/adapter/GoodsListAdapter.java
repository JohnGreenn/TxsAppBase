package com.hpkj.txsappbase.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.hpkj.txsappbase.R;
import com.hpkj.txsappbase.databinding.ItemGoodslistBinding;
import com.hpkj.txsappbase.http.response.ShopGoodsListBean;
import com.hpkj.txsappbase.ui.utils.ClickUtil;

import org.jetbrains.annotations.NotNull;

/**
 * desc：
 * author：Glq
 * time：2021/07/28 10:06
 */
public class GoodsListAdapter extends BaseQuickAdapter<ShopGoodsListBean, BaseDataBindingHolder<ItemGoodslistBinding>> {


    public GoodsListAdapter() {
        super(R.layout.item_goodslist);
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemGoodslistBinding> holder,ShopGoodsListBean data) {
        // 获取 Binding
        ItemGoodslistBinding binding = holder.getDataBinding();

        if (binding != null) {
            binding.setData(data);
            binding.setClick(new ClickUtil());
            binding.executePendingBindings();
        }

    }
}
