package com.hpkj.txsappbase.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hpkj.txsappbase.R;
import com.hpkj.txsappbase.app.AppAdapter;
import com.hpkj.txsappbase.http.response.ShopGoodsListBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * desc：
 * author：Glq
 * time：2021/07/28 10:06
 */
public final class MessageAdapter extends AppAdapter<ShopGoodsListBean> {

    public MessageAdapter(Context context) {
        super(context);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent,int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        private final TextView mTextView;

        private ViewHolder() {
            super(R.layout.status_item);
            mTextView = findViewById(R.id.tv_status_text);
        }

        @Override
        public void onBindView(int position) {
            mTextView.setText(getItem(position).getGoodName());
        }
    }

}
