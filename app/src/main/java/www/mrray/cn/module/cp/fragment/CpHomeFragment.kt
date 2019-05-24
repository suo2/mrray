package www.mrray.cn.module.gp.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.zhouwei.mzbanner.MZBannerView
import kotlinx.android.synthetic.main.fragment_home_cp.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.http.requestbody.PageRequestBody
import www.mrray.cn.model.PageBaseModel
import www.mrray.cn.model.gp.HighCustomerModel
import www.mrray.cn.model.gp.HomeHeadModel
import www.mrray.cn.model.gp.NewsInfoModel
import www.mrray.cn.model.lp.FineSelectFundModel
import www.mrray.cn.module.gp.SpaceItemDecoration
import www.mrray.cn.module.gp.activity.HighCustomActivity
import www.mrray.cn.module.gp.adapter.HighCustomerAdapter
import www.mrray.cn.module.gp.adapter.NewsInfoAdapter
import www.mrray.cn.module.lp.FineSelectFundActivity
import www.mrray.cn.module.lp.adapter.FineSelectFundAdapter
import www.mrray.cn.repository.cp.CpHomeRepository
import www.mrray.cn.utils.NumberFormatUtil
import www.mrray.cn.view.BannerLocalViewHolder
import www.mrray.cn.view.LocalImageHolderView
import www.mrray.cn.viewmodel.cp.CpHomeViewModel
import java.util.*
import kotlin.collections.ArrayList


/**
 *@author suo
 *@date 2018/11/6
 *@desc: CP首页
 */
class CpHomeFragment : BaseViewModelFragment<CpHomeViewModel, CpHomeRepository>() {

    private lateinit var mPageRequestBody: PageRequestBody
    private lateinit var customerList: ArrayList<HighCustomerModel>
    private lateinit var fineSelectList: ArrayList<FineSelectFundModel>
    private lateinit var newsInfoList: ArrayList<NewsInfoModel>
    private lateinit var mHighCustomerAdapter: HighCustomerAdapter
    private lateinit var mFineSelectFundAdapter: FineSelectFundAdapter
    private lateinit var mNewsInfoAdapter: NewsInfoAdapter
    private lateinit var convenientBanner: ConvenientBanner<Int>//顶部广告栏控件
    private lateinit var activityBanner: MZBannerView<Int> //中间广告栏控件
    private var headImageList: List<Int> = ArrayList()
    private var marqueeData: MutableList<String> = ArrayList()
    private var marqueeViews: MutableList<View> = ArrayList()
    private val headImage = arrayOf(
            R.mipmap.head_banner1,
            R.mipmap.head_banner2,
            R.mipmap.head_banner3
    )
    private var activityLocalImageList: List<Int> = ArrayList()
    private val activityLocalImage = arrayOf(
            R.mipmap.activity_img1,
            R.mipmap.activity_img2,
            R.mipmap.activity_img3,
            R.mipmap.activity_img4
    )

    companion object {
        fun newInstance() = CpHomeFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_cp
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        convenientBanner = view.findViewById(R.id.homeBanner)
        activityBanner = view.findViewById(R.id.activityBanner)
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark)
        mSwipeRefreshLayout.setOnRefreshListener {
            requestData()
        }
        requestData()
        initBanner()
        initMarquee()
        initData()
    }

    private fun requestData(){
        mPageRequestBody= PageRequestBody("","0",1,3)
        mViewModel.getHead()
        mViewModel.getCustomerPageQuery(mPageRequestBody.getMap())
        mViewModel.getFundPageQuery(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
    }

    private fun initBanner() {
        headImageList = Arrays.asList( * headImage )
        convenientBanner.setPages(
                object : CBViewHolderCreator {
                    override fun getLayoutId(): Int {
                        return R.layout.item_localimage
                    }
                    override fun createHolder(itemView: View): LocalImageHolderView {
                        return LocalImageHolderView(itemView)
                    }
                }, headImageList).setPageIndicator(arrayOf(R.drawable.indicator_normal, R.drawable.indicator_selected).toIntArray())
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                //设置指示器是否可见
                .setPointViewVisible(true)
                .startTurning(2000)
        activityLocalImageList = Arrays.asList( * activityLocalImage)
        activityBanner.setPages( activityLocalImageList) { BannerLocalViewHolder(activity!!) }
    }

    /**
     * 初始化marquee数据
     */
    private fun initMarquee(){
        marqueeData = ArrayList()
        marqueeData.add("链讯头条以区块链技术为基础,打造资讯版权平台")
        marqueeData.add("聚焦两会|看看政协委员怎么说区块链")
        marqueeData.add("福布斯：2019年5大技术发展预测，区块链这部分你一定要看")
        var i = 0
        while (i < marqueeData.size) {
            val moreView = LayoutInflater.from(activity).inflate(R.layout.item_marquee_view, null) as LinearLayout
            val tv1 = moreView.findViewById(R.id.marquee_content) as TextView
            tv1.text = marqueeData[i]
            marqueeViews.add(moreView)
            i++
        }
        mUPMarqueeView.setViews(marqueeViews)
    }

    private fun initData() {
        high_customer_more.setOnClickListener { startActivity<HighCustomActivity>() }
        high_fund_more.setOnClickListener { startActivity<FineSelectFundActivity>() }

        highCustomerRecyclerView.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically() = false
        }
        highCustomerRecyclerView.addItemDecoration(SpaceItemDecoration(20,3))


        fineSelectfundRecyclerView.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically() = false
        }
        fineSelectfundRecyclerView.addItemDecoration(SpaceItemDecoration(20,3))

        newsInfoList = ArrayList()
        newsInfoList.add(0, NewsInfoModel("1", "专家和业内人士探讨区块链下一个爆发点", R.mipmap.home_news_1, "1551233477000", "中国新闻网", "7.1万阅读"))
        newsInfoList.add(1, NewsInfoModel("1", "BTC下一目标：增发 修改2100万上限 停止减半？", R.mipmap.home_news_2,"1549937477000", "新华社", "11.2万阅读"))
        newsInfoRecyclerView.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically() = false
        }
        newsInfoRecyclerView.addItemDecoration(SpaceItemDecoration(20))
        mNewsInfoAdapter = NewsInfoAdapter(activity!!, newsInfoList)
        newsInfoRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        newsInfoRecyclerView.adapter = mNewsInfoAdapter
    }

    override fun onPause() {
        super.onPause()
        convenientBanner.stopTurning()
        activityBanner.pause()//暂停轮播
    }

    override fun onResume() {
        super.onResume()
        convenientBanner.startTurning()
        activityBanner.start()//开始轮播
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mHomeHeadModel.observe(this,Observer {
            val mHomeHeadModel : HomeHeadModel = it as HomeHeadModel
            val investmentAmountArray : Array<String?> = NumberFormatUtil.numberFormatToArray(mHomeHeadModel.investmentAmount)
            investmentAmount.text = investmentAmountArray[0]
            investmentAmountUnit.text = investmentAmountArray[1]

            val gpCountArray : Array<String?> = NumberFormatUtil.numberFormatToArray(mHomeHeadModel.gpCount,"家",false)
            gpCount.text = gpCountArray[0]
            gpCountUnit.text = gpCountArray[1]

            val enterpriseCountArray : Array<String?> = NumberFormatUtil.numberFormatToArray(mHomeHeadModel.enterpriseCount,"家",false)
            enterpriseCount.text = enterpriseCountArray[0]
            enterpriseCountUnit.text = enterpriseCountArray[1]

            val investorsCountArray : Array<String?> = NumberFormatUtil.numberFormatToArray(mHomeHeadModel.investorsCount,"位",false)
            investorsCount.text = investorsCountArray[0]
            investorsCountUnit.text = investorsCountArray[1]
            mSwipeRefreshLayout.isRefreshing = false
        })

        mViewModel.mHighCustomerModel.observe(this, Observer {
            customerList = (it as PageBaseModel<HighCustomerModel>).content as ArrayList<HighCustomerModel>
            mHighCustomerAdapter = HighCustomerAdapter(activity!!, customerList)
            highCustomerRecyclerView.adapter = mHighCustomerAdapter
            mSwipeRefreshLayout.isRefreshing = false
        })

        mViewModel.mFineSelectFundModel.observe(this, Observer {
            fineSelectList = (it as PageBaseModel<FineSelectFundModel>).content as ArrayList<FineSelectFundModel>
            mFineSelectFundAdapter = FineSelectFundAdapter(activity!!, fineSelectList)
            fineSelectfundRecyclerView.adapter = mFineSelectFundAdapter
            mSwipeRefreshLayout.isRefreshing = false
        })
    }

    override fun onNetError(it: HttpException?) {
        super.onNetError(it)
        mSwipeRefreshLayout.isRefreshing = false
        if (it != null) {
            toast(it.httpMessage)
        }
    }
}