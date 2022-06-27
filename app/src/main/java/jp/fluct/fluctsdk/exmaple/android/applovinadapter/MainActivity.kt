package jp.fluct.fluctsdk.exmaple.android.applovinadapter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkConfiguration
import com.applovin.sdk.AppLovinSdkSettings
import jp.fluct.fluctsdk.exmaple.android.applovinadapter.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.appLovinSdkKey
            .setText("SDK Key: ${getString(R.string.appLovinSdkKey)}")

        binding.rewardedAds.setOnClickListener {
            RewardedAdsActivity.launch(this@MainActivity)
        }

        binding.viewFlipper
            .displayedChild = binding.root.indexOfChild(binding.progressBar)

        val settings: AppLovinSdkSettings = AppLovinSdkSettings(this).apply {
            setVerboseLogging(true)
        }

        AppLovinSdk
            .getInstance(settings, this)
            .apply {
                mediationProvider = "max"
            }
            .initializeSdk {
                onAppLovinInitialized(it)
            }
    }

    private fun onAppLovinInitialized(config: AppLovinSdkConfiguration) {
        binding.viewFlipper
            .displayedChild = binding.root.indexOfChild(binding.buttons)
    }

}
