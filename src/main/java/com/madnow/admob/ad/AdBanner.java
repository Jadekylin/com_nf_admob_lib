package com.madnow.admob.ad;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.wogame.common.AppMacros;

public class AdBanner {
    private Activity mActivity;
    private AdView mBottomAdView;
    private AdView mTopAdView;
    View mViewRoot;

    private int mShowBannerStatus = 0;//1调用show 方法了，2显示成功了，3显示视频
    private boolean mIsBannerTopLoaded = false;

    private LinearLayout mBottomContainer;
    private LinearLayout mTopContainer;

    public AdBanner(Activity activity){
        mActivity = activity;


    }

    //
    public void loadBottomAd(final String bannerId){
        mBottomAdView = new AdView(mActivity);
        mBottomAdView.setAdSize(AdSize.BANNER);
        mBottomAdView.setAdUnitId(bannerId);
        AdRequest adRequest = new AdRequest.Builder().build();
        mBottomAdView.loadAd(adRequest);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        mActivity.addContentView(mBottomAdView, params);
        mBottomAdView.setVisibility(View.GONE);
        mBottomAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mIsBannerTopLoaded = true;
//                if(!mHadBTLoaded){
//                    mHadBTLoaded = true;
//                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e("Banner", "onAdFailedToLoad: " + errorCode);
//                if(!mHadBTLoaded)
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AdRequest adRequest = new AdRequest.Builder().build();
                            mBottomAdView.loadAd(adRequest);
                        }
                    }, 35000);
                }
            }

            @Override
            public void onAdOpened() {
//                 PushJniService.onVideoAdReward(AppMacros.CALL_SUCCESS, AppMacros.AT_Banner_Top, bannerPlaceId);
                mShowBannerStatus = 2;
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdClosed() {
//                AdConfig.onAdShow(AppMacros.AT_Banner_Top);
            }
        });
    }

    public void loadTopAd(final String bannerId){
        mTopAdView = new AdView(mActivity);
        mTopAdView.setAdSize(AdSize.BANNER);
        mTopAdView.setAdUnitId(bannerId);
        AdRequest adRequest = new AdRequest.Builder().build();
        mTopAdView.loadAd(adRequest);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        //params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        mActivity.addContentView(mTopAdView, params);
        mTopAdView.setVisibility(View.GONE);
        mTopAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mIsBannerTopLoaded = true;
//                if(!mHadBTLoaded){
//                    mHadBTLoaded = true;
//                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e("Banner", "onAdFailedToLoad: " + errorCode);
//                if(!mHadBTLoaded)
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AdRequest adRequest = new AdRequest.Builder().build();
                            mTopAdView.loadAd(adRequest);
                        }
                    }, 35000);
                }
            }

            @Override
            public void onAdOpened() {
//                 PushJniService.onVideoAdReward(AppMacros.CALL_SUCCESS, AppMacros.AT_Banner_Top, bannerPlaceId);
                mShowBannerStatus = 2;
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdClosed() {
//                AdConfig.onAdShow(AppMacros.AT_Banner_Top);
            }
        });
    }

    public void loadAd(int type){
        if(type == AppMacros.AT_Banner_Top){
            mTopContainer.removeAllViews();
            mTopContainer.addView(mTopAdView);
        }
        else if(type == AppMacros.AT_Banner_Bottom) {
            mBottomContainer.removeAllViews();
            mBottomContainer.addView(mBottomAdView);
        }
    }

    public void hideBanner(int type){
        if(type == AppMacros.AT_Banner_Top){
            mTopContainer.removeAllViews();
        }
        else if(type == AppMacros.AT_Banner_Bottom) {
            mBottomContainer.removeAllViews();
        }
    }

    public void onDestroy() {
        if (mBottomAdView != null) {
            mBottomAdView.destroy();
        }

        if (mTopAdView != null) {
            mTopAdView.destroy();
        }
    }
}
