package www.mrray.cn.view

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.find
import www.mrray.cn.R
import www.mrray.cn.model.SortModel

/**
 *@author suo
 *@date 2018/10/30
 *@desc: 条件筛选
 */
class ConditionFilterView : FrameLayout {

    private val DESC : String = "DESC" //降序
    private val ASC :String = "ASC" //升序
    private lateinit var descDrawable : Drawable
    private lateinit var ascDrawable : Drawable

    private lateinit var conditionOne: TextView
    private lateinit var conditionTwo: TextView
    private lateinit var conditionThree: TextView
    private lateinit var llConditionOne: LinearLayout
    private lateinit var llConditionTwo: LinearLayout
    private lateinit var llConditionThree: LinearLayout

    private var mConditionOne : String = ""
    private var mConditionTwo : String = ""
    private var mConditionThree : String = ""

    //排序方式
    private var mConditionOneSort :String = DESC
    private var mConditionTwoSort :String = ""
    private var mConditionThreeSort :String = ""

    //默认现实三个tab
    private var mConditionCount : Int = 3

    var currentPosition : Int = 0
    var currentSort : String = DESC

    //默认选中项（0表示第一项）
    private var mDefaultSelected : Int = 0

    private lateinit var mContext : Context

    constructor(context: Context) : super(context)

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        LayoutInflater.from(context).inflate(R.layout.include_filter_condition, this)
        mContext = context

        conditionOne = find(R.id.conditionOne)
        conditionTwo = find(R.id.conditionTwo)
        conditionThree = find(R.id.conditionThree)
        llConditionOne = find(R.id.llConditionOne)
        llConditionTwo = find(R.id.llConditionTwo)
        llConditionThree = find(R.id.llConditionThree)

        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.ConditionFilter)
        mConditionOne = typedArray.getString(R.styleable.ConditionFilter_condition_one) ?: ""
        mConditionTwo = typedArray.getString(R.styleable.ConditionFilter_condition_two) ?: ""
        mConditionThree = typedArray.getString(R.styleable.ConditionFilter_condition_three) ?: ""
        mDefaultSelected = typedArray.getInt(R.styleable.ConditionFilter_default_selected,0)
        mConditionCount = typedArray.getInt(R.styleable.ConditionFilter_show_tab_count,3)

        descDrawable = ContextCompat.getDrawable(context,R.mipmap.sort_desc)!!
        descDrawable.setBounds(0, 0, descDrawable.minimumWidth, descDrawable.minimumHeight)
        ascDrawable = ContextCompat.getDrawable(context,R.mipmap.sort_asc)!!
        ascDrawable.setBounds(0, 0, descDrawable.minimumWidth, descDrawable.minimumHeight)
        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }
    private fun initView() {
        conditionOne.text = mConditionOne
        conditionTwo.text = mConditionTwo
        conditionThree.text = mConditionThree

        //设置默认选中状态
        when(mDefaultSelected){
            0 -> mConditionOneSort = setTabStatus(conditionOne,mConditionOneSort)
            1 -> mConditionTwoSort = setTabStatus(conditionTwo,mConditionTwoSort)
            2 -> mConditionThreeSort = setTabStatus(conditionThree,mConditionThreeSort)
        }
        if(mConditionCount >=3){

        }else if(mConditionCount ==2){
            llConditionThree.visibility = View.GONE
        }else{
            llConditionThree.visibility = View.GONE
            llConditionTwo.visibility = View.GONE
        }
    }

    /**
     * 设置监听
     */
    fun setFilterListener(event : MotionEvent ,callbackPos: (Int) -> Unit , callbackSort: (String) -> Unit) : Boolean{
        if(isInTextView(llConditionOne,event)){
            mConditionOneSort = setTabStatus(conditionOne,mConditionOneSort)
            currentPosition = 0
            currentSort = mConditionOneSort
        }
        if(isInTextView(llConditionTwo,event)){
            mConditionTwoSort = setTabStatus(conditionTwo,mConditionTwoSort)
            currentPosition = 1
            currentSort = mConditionTwoSort
        }
        if(isInTextView(llConditionThree,event)){
            mConditionThreeSort = setTabStatus(conditionThree,mConditionThreeSort)
            currentPosition = 2
            currentSort = mConditionThreeSort
        }
        callbackPos(currentPosition )
        callbackSort(currentSort)
        return false
    }

    /**
     * 设置监听
     */
    fun setFilterListener(event : MotionEvent ,callback: (SortModel) -> Unit) : Boolean{
        var mSortModel  = SortModel(0,DESC)
        if(isInTextView(llConditionOne,event)){
            mConditionOneSort = setTabStatus(conditionOne,mConditionOneSort)
            mSortModel.position = 0
            mSortModel.sort = mConditionOneSort
        }
        if(isInTextView(llConditionTwo,event)){
            mConditionTwoSort = setTabStatus(conditionTwo,mConditionTwoSort)
            mSortModel.position = 1
            mSortModel.sort = mConditionTwoSort
        }
        if(isInTextView(llConditionThree,event)){
            mConditionThreeSort = setTabStatus(conditionThree,mConditionThreeSort)
            mSortModel.position = 2
            mSortModel.sort = mConditionThreeSort
        }
        callback(mSortModel)
        return false
    }

    /**
     * 设置点击状态
     */
    private fun setTabStatus(tv : TextView, sort : String) : String{
        conditionOne.setTextColor(ContextCompat.getColor(mContext,R.color.tab_sort_unselect))
        conditionTwo.setTextColor(ContextCompat.getColor(mContext,R.color.tab_sort_unselect))
        conditionThree.setTextColor(ContextCompat.getColor(mContext,R.color.tab_sort_unselect))
        tv.setTextColor(ContextCompat.getColor(mContext,R.color.tab_sort_select))
        return if(sort == DESC) {
            tv.setCompoundDrawables(null, null, ascDrawable, null)
            ASC
        }else{
            tv.setCompoundDrawables(null, null, descDrawable, null)
            DESC
        }
    }

    /**
     * 判断触摸的点是否在TextView范围内
     */
    private fun isInTextView(v: View, event: MotionEvent): Boolean {
        val frame = Rect()
        v.getHitRect(frame)
        val eventX = event.x
        val eventY = event.y
        return frame.contains(eventX.toInt(), eventY.toInt())
    }
}