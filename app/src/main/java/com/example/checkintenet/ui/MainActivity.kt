package com.example.checkintenet.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.checkintenet.ConnectionType
import com.example.checkintenet.NetworkMonitorUtil
import com.example.checkintenet.R
import com.example.checkintenet.databinding.ActivityMainBinding
import com.example.checkintenet.ui.viewModel.mainViewModel


class MainActivity : AppCompatActivity() {
    private val networkMonitor = NetworkMonitorUtil(this)
    private lateinit var binding: ActivityMainBinding
    private val viewModel: mainViewModel by lazy { ViewModelProvider(this)[mainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkWifiStatus()
        initLoading()

    }

    private fun initLoading() {
        viewModel.isLoading.observe(this) {
            if (it) {
                with(binding) {
                    gitLoading.visibility = View.VISIBLE
                    containerId.visibility = View.INVISIBLE
                }
            } else {
                with(binding) {
                    gitLoading.visibility = View.INVISIBLE
                    containerId.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun setupMainViewModel() {
        viewModel.isResponseSuccessful.observe(this) {
            if (it) {
                val connectionWifi = ParamByUI(
                    R.string.status_wifi,
                    R.string.message_description_successufull,
                    R.drawable.wifi_successful,
                    Color.GREEN
                )
                showUIMsg(connectionWifi)
            } else {
                val intenetNotAvail = ParamByUI(
                    R.string.status_ERROR,
                    R.string.message_description_error,
                    R.drawable.wifi_question,
                    Color.YELLOW
                )
                showUIMsg(intenetNotAvail)
            }
        }
    }


    private fun checkWifiStatus() {
        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi, ConnectionType.Cellular -> {
                                setupMainViewModel()
                            }
                            else -> {}
                        }
                    }
                    false -> {

                        val connectionNotWifi = ParamByUI(
                            R.string.status_ERROR,
                            R.string.message_description_error,
                            R.drawable.wifi_error,
                            Color.RED
                        )
                        showUIMsg(connectionNotWifi)
                    }
                }
            }
        }
    }

    private fun showUIMsg(param: ParamByUI) {
        with(binding) {
            tvStatus.text = getString(param.status)
            tvDescri.text = getString(param.descrip)
            imgStatusWifi.setImageResource(param.img)
            tvStatus.setTextColor(param.color)
        }
    }


    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }
}


/*

        val connectivityManager:ConnectivityManager =  getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

*//*


val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
val networkInfo = connectivityManager.activeNetworkInfo

if (networkInfo != null && networkInfo.isConnected) {
    displaySuccessful()
} else {
    displayError()
}*/
