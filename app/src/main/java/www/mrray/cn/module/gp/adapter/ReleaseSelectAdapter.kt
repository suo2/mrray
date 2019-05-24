package www.mrray.cn.module.gp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView

import www.mrray.cn.R
import www.mrray.cn.model.gp.ReleaseFundSelectModel

/**
 * @author suo
 * @date 2018/10/20
 * @desc:
 */
class ReleaseSelectAdapter(private val data: List<ReleaseFundSelectModel>, private val context: Context) : BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(i: Int): Any {
        return data[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        var hd: ViewHoder
        if (view == null) {
            hd = ViewHoder()
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.item_release_fund_select, null)
            hd.textView = view!!.findViewById<View>(R.id.text_title) as TextView
            hd.checkBox = view.findViewById<View>(R.id.ckb) as CheckBox
            view.tag = hd
        }
        val (id, ischeck) = data[i]
        hd = view.tag as ViewHoder

//        hd.checkBox.setChecked(mModel.ischeck());
        view.setOnClickListener {
            val checkBox = hd.checkBox
            if (checkBox!!.isChecked) {
                checkBox.isChecked = false
                //                    data.get(i).setIscheck(false);
            } else {
                checkBox.isChecked = true
                //                    data.get(i).setIscheck(true);
            }
            //监听每个item，若所有checkbox都为选中状态则更改main的全选checkbox状态
            for ((id1, ischeck1) in data) {
//                                    if (!model.ischeck()) {
//                                        return;
//                                    }
            }
        }


        return view
    }

    internal inner class ViewHoder {
        var textView: TextView? = null
        var checkBox: CheckBox? = null
    }

}