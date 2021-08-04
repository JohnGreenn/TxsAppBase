package com.hpkj.txsappbase.ui.activity;

import android.media.Image;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.OnHttpListener;
import com.hpkj.txsappbase.R;
import com.hpkj.txsappbase.app.AppActivity;
import com.hpkj.txsappbase.databinding.ActivityGoodsDetailBinding;
import com.hpkj.txsappbase.http.model.HttpData;
import com.hpkj.txsappbase.http.request.BannerApi;
import com.hpkj.txsappbase.http.request.GetGoodsDetailApi;
import com.hpkj.txsappbase.http.response.ImgListBean;
import com.hpkj.txsappbase.http.response.ShopGoodsDetailBean;
import com.hpkj.txsappbase.http.response.StartBean;
import com.hpkj.txsappbase.http.response.TestBean;
import com.hpkj.txsappbase.other.EncryptUtil;
import com.hpkj.txsappbase.other.NumIndicator;
import com.hpkj.txsappbase.ui.adapter.ImageAdapter;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.BaseIndicator;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.AlphaPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class GoodsDetailActivity extends AppActivity<ActivityGoodsDetailBinding> {

    private int gid = 0;
    TestBean testBean;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initBundleData() {
        gid = 1763;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        EasyHttp.get(this)

                .api(new GetGoodsDetailApi()
                        .setHeaderPass(EncryptUtil.getRequestId(5).trim())
                        .setGid(gid))
                .request(new OnHttpListener<HttpData<ShopGoodsDetailBean>>() {

                    @Override
                    public void onSucceed(HttpData<ShopGoodsDetailBean> result) {
                        //ToastUtils.show("banner 请求成功，请看日志");
                        //添加banner
                        useBanner(result.getData().getImageList());
                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                });


    }

    private void useBanner(List<String> imglist) {

        List<ImgListBean> list = new ArrayList<>();
        for(int i = 0; i < imglist.size(); i++) {
            list.add(new ImgListBean(imglist.get(i)));
        }

        binding.goodsDetailBanner.setAdapter(new BannerImageAdapter<ImgListBean>(list) {

            @Override
            public void onBindView(BannerImageHolder holder,ImgListBean data,int position,int size) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                        .load(data.imgUrl)
                        //.apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }
        })
                .addBannerLifecycleObserver(this)
                .setIndicator(new NumIndicator(getContext()));

        //binding.goodsDetailBanner.setIndicator(new CircleIndicator(getContext()));
        //添加画廊效果
        //binding.goodsDetailBanner.setBannerGalleryEffect(18, 10);
        //添加透明效果(画廊配合透明效果更棒)
        //binding.goodsDetailBanner.addPageTransformer(new AlphaPageTransformer());
    }
}