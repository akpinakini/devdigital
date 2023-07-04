package com.assignment.mapdevDigital.ui.webView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.assignment.mapdevDigital.databinding.FragmentNotificationsBinding

class WebviewFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[NotificationsViewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        loadWebView()
        return root
    }

    private fun loadWebView() {

        binding.webview.visibility = View.VISIBLE

        binding.webview.loadUrl("https://support.google.com/?hl=en-GB")


        binding.webview.settings.javaScriptEnabled = true

//        binding.webview.webViewClient = WebViewClient()
        binding.webview!!.settings.loadWithOverviewMode = true
        binding.webview!!.settings.useWideViewPort = true
        binding.webview!!.settings.builtInZoomControls = false
        val settings = binding.webview!!.settings
        settings.javaScriptEnabled = true
        binding.webview!!.settings.setSupportMultipleWindows(true)
        binding.webview!!.settings.javaScriptCanOpenWindowsAutomatically = true

//        binding.webview!!.settings.setAppCachePath(applicationContext.cacheDir.absolutePath)
        binding.webview!!.settings.cacheMode = WebSettings.LOAD_DEFAULT
        binding.webview!!.settings.databaseEnabled = true
        binding.webview!!.settings.domStorageEnabled = true
        binding.webview!!.settings.useWideViewPort = true
        binding.webview!!.settings.loadWithOverviewMode = true
        binding.webview!!.settings.pluginState = WebSettings.PluginState.ON
        binding.webview!!.loadUrl("https://support.google.com/?hl=en-GB")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}