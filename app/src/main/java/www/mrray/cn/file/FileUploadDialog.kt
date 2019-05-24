package www.mrray.cn.file

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import www.mrray.cn.R

class FileUploadDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.activity!!)
        // Get the layout inflater
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.dialog_file_up_load,null)
        builder.setView(view)
        return builder.create()
    }
}