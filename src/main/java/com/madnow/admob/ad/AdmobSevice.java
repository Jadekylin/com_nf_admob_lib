package com.madnow.admob.ad;

import android.app.Activity;
import com.wogame.cinterface.AdCallBack;
import com.wogame.common.AppMacros;
import com.wogame.common.Common;
import com.wogame.util.DeviceUtil;

/**
 * Created by Administrator on 2018/3/10 0010.
 */

public class AdmobSevice {

    public static AdmobSevice mInstance;
    private Activity mActivity;

    private AdCallBack mAdCallback = null;

    private AdBanner mAdBanner;
    private AdInterstitial mInterstitial;
    private AdRewardedVideo mRewardedVideo;


    public static AdmobSevice getInstance() {
        if (mInstance == null) {
            mInstance = new AdmobSevice();
        }
        return mInstance;
    }

    public void initActivity(Activity activity,AdCallBack callback) {
        mActivity = activity;
        mAdCallback = callback;
    }

    public void setAdId(final String bannerTopId,final String bannerBottomId,final String interstitialId,final String rewardedId){

        if(!bannerTopId.isEmpty() || !bannerBottomId.isEmpty()){
            mAdBanner = new AdBanner(mActivity);
            if(!bannerTopId.isEmpty()){
                mAdBanner.loadTopAd(bannerTopId);
            }
            if(!bannerBottomId.isEmpty()){
                mAdBanner.loadBottomAd(bannerBottomId);
            }
        }

        if(!interstitialId.isEmpty()){
            mInterstitial = new AdInterstitial(mActivity,interstitialId);
        }

        if(!rewardedId.isEmpty()){
            mRewardedVideo = new AdRewardedVideo(mActivity,rewardedId);
        }
    }

    public boolean loadedAd(final int type){
        if(type == AppMacros.AT_RewardVideo){
            if(mRewardedVideo!=null){
               return mRewardedVideo.canPlayVideo();
            }
        }
        else  if(type == AppMacros.AT_FullScreenVideo){

        }
        return false;
    }

    /**
     * 显示广告
     * @param type
     * @param cpPlaceId
     * @param px
     * @param py
     */
    public void showAd(final int type, final String cpPlaceId, final int px, final int py){
        if(type == AppMacros.AT_RewardVideo){
            if(mRewardedVideo!=null){
                mRewardedVideo.playVideo(cpPlaceId);
            }
        }
        else  if(type == AppMacros.AT_FullScreenVideo){
            if(mInterstitial!= null){
                mInterstitial.playInterstitialAd(cpPlaceId);
            }
        }
    }

    /**
     * 关闭广告
     * @param type
     */
    public void closeAd(final int type) {

    }


    public void onDestroy() {
        if (mRewardedVideo!=null) {
            mRewardedVideo.onDestroy();
        }

        if(mAdBanner!= null){
            mAdBanner.onDestroy();
        }
    }

}
