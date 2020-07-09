package com.madnow.admob.ad;

import android.app.Activity;
import android.os.CountDownTimer;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.wogame.util.GMDebug;

import androidx.annotation.NonNull;
/**
 * 测试id
 * ca-app-pub-3940256099942544/5224354917
 */
public class AdRewardedVideo {
    private RewardedAd mRewardedVideoAd;
    private Activity mActivity;
    private int mVideoCount = 0;
    private String mVideoPlaceId = "";
    private boolean mIsShowVideo;
    boolean mIsRewarded;
    private CountDownTimer mCountDownTimer;
    private String mRewardedId;

    public AdRewardedVideo(Activity activity,final String rewardedId){
        mActivity = activity;
        mRewardedId = rewardedId;
        initRewardedVideoAd(rewardedId);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //视频广告
    private void initRewardedVideoAd(final String rewardedId) {

        mRewardedVideoAd = createAndLoadRewardedAd();
    }

    //region 奖励视频
    public RewardedAd createAndLoadRewardedAd() {
        RewardedAd rewardedAd = new RewardedAd(mActivity, mRewardedId);
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                Log.e("1","RewardedAd successfully loaded.");
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
                Log.e("1","RewardedAd failed to load. errorCode ="+errorCode);
                createTimer();
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);//.addTestDevice("5B208B272D62416A7E22D73572C51BDC")
        return rewardedAd;
    }

    private void createTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUnitFinished) {
                GMDebug.LogD(millisUnitFinished+"=====");
            }
            @Override
            public void onFinish() {
                GMDebug.LogD("onFinish: ");
//                AdService.getInstance().mRewardedAd = createAndLoadRewardedAd();
            }
        };
        mCountDownTimer.start();
    }

    public void playVideo(String placeId) {
        this.mVideoPlaceId = placeId;
        if (mRewardedVideoAd.isLoaded()) {
            mIsRewarded = false;
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                public void onRewardedAdOpened() {
                    // Ad opened.
                }
                public void onRewardedAdClosed() {
                    // Ad closed.
//                    if(mIsRewarded){
//                        JniService.onVideoAdReward(1,4, mPlaceId);
//                    }
//                    else {
//                        JniService.onVideoAdReward(2,4, mPlaceId);
//                    }
//                    AdService.getInstance().mRewardedAd = createAndLoadRewardedAd();
                }
                public void onUserEarnedReward(@NonNull com.google.android.gms.ads.rewarded.RewardItem reward) {
                    // User earned reward.
                    mIsRewarded = true;
                    Log.e("1","User earned reward.");
                }
                public void onRewardedAdFailedToShow(int errorCode) {
                    // Ad failed to display
                    Log.e("1","Ad failed to display.");
                }
            };
            mRewardedVideoAd.show(mActivity,adCallback);
        } else {
//            JniService.onVideoAdReward(2,2, mPlaceId);
            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
        }
    }

    public boolean canPlayVideo(){
        if (mRewardedVideoAd == null || !mRewardedVideoAd.isLoaded()) {
            return false;
        }else{
            return true;
        }
    }

    public void onDestroy() {
        if (mRewardedVideoAd != null) {
            mRewardedVideoAd = null;
        }
    }


}
