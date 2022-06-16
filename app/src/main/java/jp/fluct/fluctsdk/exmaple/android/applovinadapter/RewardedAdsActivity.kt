package jp.fluct.fluctsdk.exmaple.android.applovinadapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxRewardedAdListener
import com.applovin.mediation.ads.MaxRewardedAd
import jp.fluct.fluctsdk.exmaple.android.applovinadapter.databinding.RewardedAdsActivityBinding

class RewardedAdsActivity : AppCompatActivity() {

    companion object {

        fun launch(context: Context) {
            context.startActivity(
                Intent(context, RewardedAdsActivity::class.java)
            )
        }

    }

    private val adUnitId: String by lazy { getString(R.string.appLovinRewardedAdsAdUnitId) }

    private lateinit var binding: RewardedAdsActivityBinding

    private var rewardedAd: MaxRewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = RewardedAdsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.adUnitId
            .setText(adUnitId)

        binding.load.setOnClickListener { load() }
        binding.show.setOnClickListener { show() }

        refreshUi()
    }

    private fun load() {
        rewardedAd = MaxRewardedAd.getInstance(adUnitId, this).apply {
            setListener(listener)
            loadAd()
        }
        refreshUi()
    }

    private fun show() {
        rewardedAd!!.showAd()
        refreshUi()
    }

    private val listener: MaxRewardedAdListener = object : MaxRewardedAdListener {

        override fun onAdLoaded(ad: MaxAd?) {
            toast("onAdLoaded")
            refreshUi()
        }

        override fun onAdDisplayed(ad: MaxAd?) {
            toast("onAdDisplayed")
            refreshUi()
        }

        override fun onAdHidden(ad: MaxAd?) {
            toast("onAdHidden")
            refreshUi()
        }

        override fun onAdClicked(ad: MaxAd?) {
            toast("onAdClicked")
            refreshUi()
        }

        override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
            toast("onAdLoadFailed")
            refreshUi()
        }

        override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
            toast("onAdDisplayFailed")
            refreshUi()
        }

        override fun onRewardedVideoStarted(ad: MaxAd?) {
            toast("onRewardedVideoStarted")
            refreshUi()
        }

        override fun onRewardedVideoCompleted(ad: MaxAd?) {
            toast("onRewardedVideoCompleted")
            refreshUi()
        }

        override fun onUserRewarded(ad: MaxAd?, reward: MaxReward?) {
            toast("Rewarded user: ${reward?.amount} ${reward?.label}")
            refreshUi()
        }

    }

    private fun toast(msg: String) {
        Toast.makeText(
            this,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun refreshUi() {
        binding.load.isEnabled = rewardedAd == null || !(rewardedAd!!.isReady)
        binding.show.isEnabled = rewardedAd?.isReady ?: false
    }

}
