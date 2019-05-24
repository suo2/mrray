package www.mrray.cn.module.login.adapter

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import www.mrray.cn.R
import www.mrray.cn.model.login.ChoiceIdentityModel
import www.mrray.cn.module.login.holder.ChoiceIdentityHolder

class ChoiceIdentityAdapter(private val mDataList: ArrayList<ChoiceIdentityModel>, val  mAdapterViewModel: MutableLiveData<ChoiceIdentityModel>) : RecyclerView.Adapter<ChoiceIdentityHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ChoiceIdentityHolder = ChoiceIdentityHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_choice_identity, p0, false),mAdapterViewModel)

    override fun getItemCount(): Int = mDataList.size

    override fun onBindViewHolder(p0: ChoiceIdentityHolder, p1: Int) {
        p0.onBind(mDataList[p1])
    }
}