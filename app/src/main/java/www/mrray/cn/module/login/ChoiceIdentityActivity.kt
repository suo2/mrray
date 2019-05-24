package www.mrray.cn.module.login

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import kotlinx.android.synthetic.main.activity_choice_identity.*
import org.jetbrains.anko.toast
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelActivity
import www.mrray.cn.model.login.ChoiceIdentityModel
import www.mrray.cn.model.login.LoginModel
import www.mrray.cn.module.cp.CPMainActivity
import www.mrray.cn.module.gp.GPMainActivity
import www.mrray.cn.module.login.adapter.ChoiceIdentityAdapter
import www.mrray.cn.module.lp.LPMainActivity
import www.mrray.cn.repository.login.LoginRepository
import www.mrray.cn.utils.DataCatchInfoUtils
import www.mrray.cn.utils.ImManager
import www.mrray.cn.utils.LogUtil
import www.mrray.cn.viewmodel.login.LoginViewModel

@SuppressLint("Registered")
/**
 * 选择身份
 */
class ChoiceIdentityActivity : BaseViewModelActivity<LoginViewModel, LoginRepository>() {
    private val mDataList: ArrayList<ChoiceIdentityModel> = ArrayList()
    private val mAdapterViewModel: MutableLiveData<ChoiceIdentityModel> = MutableLiveData()
    private var mCurrentBean: ChoiceIdentityModel? = null

    companion object {
        const val EXTRA_DATA = "data"
    }

    private lateinit var mAdapter: ChoiceIdentityAdapter
    private lateinit var mLoginDataList: ArrayList<LoginModel>

    override fun getLayoutId(): Int {
        return R.layout.activity_choice_identity
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        mLoginDataList = intent.getParcelableArrayListExtra(EXTRA_DATA)

        initRecyclerView()
        complete.setOnClickListener {
            mViewModel.completeChoiceIdentity(mCurrentBean)
        }
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mChoiceCompleteModel.observe(this, Observer {
            if (mCurrentBean == null) {
                toast("请选择角色")
                return@Observer
            }
            if (checkUserType(mCurrentBean!!.id)) {
                DataCatchInfoUtils(this.applicationContext).setUserIdentity(mCurrentBean!!.id)
                var intent = Intent()
                mDataCatch.switchUserIdentity({
                    intent = Intent(this, GPMainActivity::class.java)
                }, {
                    intent = Intent(this, LPMainActivity::class.java)
                }, {
                    intent = Intent(this, CPMainActivity::class.java)
                }, {}, {})
                startActivity(intent)
                this.finish()
            } else {
                toast("您暂无此身份")
            }
        })

        mAdapterViewModel.observe(this, Observer {
            if (mCurrentBean == null) {
                mCurrentBean = it
            } else {
                mCurrentBean?.isSelect = false
                mCurrentBean = it
            }
            mAdapter.notifyDataSetChanged()
        })
    }

    private fun checkUserType(id: Int): Boolean {
        for (i in 0 until mLoginDataList.size) {
            LogUtil.e(mLoginDataList[0].toString())
            if (mLoginDataList[i].typeId == id) {
                DataCatchInfoUtils(this.applicationContext).setLoginModel(mLoginDataList[i])
                return true
            }
        }
        return false
    }

    private fun initRecyclerView() {
        mDataList.add(ChoiceIdentityModel(DataCatchInfoUtils.GP, false, R.mipmap.g, "我是GP", "发布基金、管理基金、投资项目"))
        mDataList.add(ChoiceIdentityModel(DataCatchInfoUtils.LP, false, R.mipmap.l, "我是LP", "投资项目、购买基金"))
        mDataList.add(ChoiceIdentityModel(DataCatchInfoUtils.CP, false, R.mipmap.c, "我是CP", "发布项目、申请融资"))
//        mDataList.add(ChoiceIdentityModel(DataCatchInfoUtils.FA, false, R.mipmap.f, "我是FA", "给平台提供认证服务"))
//        mDataList.add(ChoiceIdentityModel(DataCatchInfoUtils.FA, false, R.mipmap.jian, "我是监管方", "监管平台的合法合规运作"))

        mAdapter = ChoiceIdentityAdapter(mDataList, mAdapterViewModel)

        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
                super.getItemOffsets(outRect, itemPosition, parent)
                if (itemPosition == 0) {
                    outRect.top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27F, this@ChoiceIdentityActivity.resources.displayMetrics).toInt()
                }
                outRect.bottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16F, this@ChoiceIdentityActivity.resources.displayMetrics).toInt()
            }
        })
        recycler.adapter = mAdapter
    }

    override fun onBackPressed() {
    }
}