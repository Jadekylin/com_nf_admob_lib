package com.madnow.admob.ad;

import android.app.Activity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.wogame.util.GMDebug;

public class AdInterstitial {
    private InterstitialAd mInterstitialAd;
    private Activity mActivity;
    private int mInterstitialPlaceCount = 0;
    private String mVideoPlaceId = "";
    public boolean mIsInterstitial = false;
    private String mInterstitialPlaceId = "";


    public AdInterstitial(Activity activity,final String interstitialId){
        mActivity = activity;
        initInterstitialAd(interstitialId);
    }

    //插屏广告
    private void initInterstitialAd(final String interstitialId){
        // Instantiate an InterstitialAd object
        mInterstitialAd = new InterstitialAd(mActivity);
        mInterstitialAd.setAdUnitId(interstitialId);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                GMDebug.LogD("插页广告加载成功============");
            }
            public void onAdFailedToLoad(int errorCode) {
                GMDebug.LogD("插页广告加载失败============"+errorCode);
            }
            public void onAdClosed() {
//                JniService.onVideoAdReward(1,3, mPlaceId);
                loadInterstitialAd();
            }
        });

        loadInterstitialAd();
    }

    private void loadInterstitialAd(){
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();//.addTestDevice("5B208B272D62416A7E22D73572C51BDC")
            mInterstitialAd.loadAd(adRequest);
        }
    }

    public void playInterstitialAd(String placeId) {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            GMDebug.LogD("The rewarded ad wasn't loaded yet.");
//            JniService.onVideoAdReward(2,3, mPlaceId);
//            InterstitialAdRequest();
        }
    }


    public void onDestroy() {

    }
}
