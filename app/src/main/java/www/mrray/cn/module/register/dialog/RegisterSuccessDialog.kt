package www.mrray.cn.module.register.dialog

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.find
import www.mrray.cn.R
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics



class RegisterSuccessDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        dialog.window!!.setLayout(dm.widthPixels, dialog.window!!.attributes.height)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.dialog_register_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.find<TextView>(R.id.sure).setOnClickListener {
            callback(it)
        }
    }

    lateinit var callback: (View) -> Unit

    fun setSureClickListener(callback: (View) -> Unit) {
        this.callback = callback
    }
}