package com.assignment.mapdevDigital

import android.app.AlertDialog
import android.app.Dialog
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.assignment.mapdevDigital.databinding.LoaderAnimationBinding


open class BaseFragment : Fragment() {
    private val internetDialog: Dialog by lazy { Dialog(requireActivity()) }
    lateinit var loaderFragment: LoaderAnimationBinding
    private val dialog: Dialog by lazy { Dialog(requireActivity()) }


    /**
     * show toast
     * @param
     */
    private fun showToast(message: String?) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    /**
     * To show loader
     */
    fun showLoader() {
        loaderFragment = LoaderAnimationBinding.inflate(layoutInflater)

        dialog.setContentView(loaderFragment.root)
        val wlmp: WindowManager.LayoutParams = dialog.window!!.attributes
        dialog.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        wlmp.gravity = Gravity.CENTER_HORIZONTAL
        dialog.window!!.attributes = wlmp
        dialog.setTitle(null)
        dialog.setCancelable(false)
        dialog.setOnCancelListener(null)
        dialog.show()
    }

    fun hideLoader() {
        dialog.dismiss()
    }

    /**
     * show cancelable progress dialog
     */


    /**
     * hide internet lost dialog
     */
    open fun hideInternetLostDialog() {
        internetDialog.dismiss()
    }


    fun showAlertDialog(msg: String?) {
        val builder1 = AlertDialog.Builder(requireActivity())
        builder1.setMessage(msg)
        builder1.setTitle("Alert !!")
        builder1.setCancelable(false)
        builder1.setPositiveButton(
            "OK"
        ) { dialog3, id -> dialog3.cancel() }
        builder1.show()
    }


}