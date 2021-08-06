package com.hpkj.txsappbase.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hpkj.txsappbase.R;
import com.hpkj.txsappbase.app.AppActivity;
import com.hpkj.txsappbase.databinding.ActivitySearchBinding;

public class SearchActivity extends AppActivity<ActivitySearchBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {

        binding.setClick(this);

    }

    @Override
    protected void initData() {

    }

    //取消点击事件
    public void clickExit(View view) {
        finish();
    }

    //点击搜搜
    public void clickSearch(View view,int flg) {

    }
}