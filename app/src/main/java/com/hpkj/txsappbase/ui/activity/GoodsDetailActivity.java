package com.hpkj.txsappbase.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;

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
import com.hpkj.txsappbase.widget.view.GradationScrollView;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.BaseIndicator;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.AlphaPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class GoodsDetailActivity extends AppActivity<ActivityGoodsDetailBinding> implements GradationScrollView.ScrollViewListener{

    private int gid = 0;
    TestBean testBean;
    private int imageHeight; //图片高度

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

        //渐变
        initListener();
    }

    private void initListener() {
        // 获取顶部图片高度后，设置滚动监听
        ViewTreeObserver treeObserver = binding.goodsDetailBanner.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.goodsDetailBanner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                imageHeight = binding.goodsDetailBanner.getHeight();
                binding.scrollView.setScrollViewListener(GoodsDetailActivity.this);
            }
        });
    }

    @Override
    public void onScrollChanged(GradationScrollView scrollView,int x,int y,int oldx,int oldy) {
        if(y <= 0) {
            //设置渐变的头部的背景颜色
            binding.toolbar.setBackgroundColor(Color.argb((int) 0,255,255,255));
            binding.toolbar.setTitleColor(Color.TRANSPARENT);
        } else if(y > 0 && y <= imageHeight) {
            //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / imageHeight;
            int alpha = (int) (scale * 255);
            binding.toolbar.setBackgroundColor(Color.argb((int) alpha,255,255,255));
            binding.toolbar.setTitleColor(Color.argb(alpha,1,24,28));
        } else {
            //滑动到banner下面设置普通颜色
            binding.toolbar.setBackgroundColor(Color.WHITE);
            binding.toolbar.setTitleColor(Color.parseColor("#333333"));
        }
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
                        //商品banner
                        useBanner(result.getData().getImageList());
                        //详情页图片
                        userGoodsDetail(result.getData().getBaseInfo().getGoodDesc());
                    }

                    @Override
                    public void onFail(Exception e) {
                        toast(e.toString());
                    }
                });


    }

    private void userGoodsDetail(String goodDesc) {
        StringBuilder sb = new StringBuilder();
        sb.append(getHtmlData(goodDesc.replaceAll("<p>", "").replaceAll("</p>", "").trim()));

        //tencent x5 加载商品详情页-------------------------------------------------------
        binding.tcWebview.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        binding.tcWebview.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        binding.tcWebview.getSettings().setDisplayZoomControls(true); //隐藏原生的缩放控件
        binding.tcWebview.getSettings().setBlockNetworkImage(false);//解决图片不显示
        binding.tcWebview.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        binding.tcWebview.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
        binding.tcWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        binding.tcWebview.getSettings().setAllowFileAccess(true);
        binding.tcWebview.getSettings().setUseWideViewPort(true);
        binding.tcWebview.getSettings().setLoadWithOverviewMode(true);
        binding.tcWebview.getSettings().setAppCacheEnabled(true);
        binding.tcWebview.getSettings().setGeolocationEnabled(true);
        binding.tcWebview.getSettings().setAppCacheMaxSize(Long.MAX_VALUE);
        binding.tcWebview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        binding.tcWebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView webView,String s) {
                super.onPageFinished(webView,s);
            }
        });


        binding.tcWebview.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8",null);
    }

    /**
     * 富文本适配
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:auto; height:auto; display:block; border:0; vertical-align: middle; }</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
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