package www.mrray.cn.module.login.holder

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.find
import www.mrray.cn.R
import www.mrray.cn.model.login.ChoiceIdentityModel

class ChoiceIdentityHolder(view: View, val mAdapterViewModel: MutableLiveData<ChoiceIdentityModel>) : RecyclerView.ViewHolder(view) {

    fun onBind(data: ChoiceIdentityModel) {
        itemView.setBackgroundResource(if (!data.isSelect) R.drawable.select_type_bg else R.drawable.selected_type_bg)
        itemView.find<ImageView>(R.id.identity_type).setBackgroundResource(data.type)
        itemView.find<TextView>(R.id.identity_title).text = data.title
        itemView.find<TextView>(R.id.identity_des_text).text = data.des

        itemView.setOnClickListener {
            if (!data.isSelect) {
                mAdapterViewModel.value = data
                data.isSelect = true
                itemView.setBackgroundResource(if (!data.isSelect) R.drawable.select_type_bg else R.drawable.selected_type_bg)
            }
        }
    }
}