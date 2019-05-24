package www.mrray.cn.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import www.mrray.cn.utils.DataCatchInfoUtils

open class BaseFragment : Fragment() {
    val mDataCatch by lazy {
        DataCatchInfoUtils(this.requireContext().applicationContext)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view, savedInstanceState)
    }

    open fun init(view: View, savedInstanceState: Bundle?) {
    }

    open fun getLayoutId(): Int {
        return 0
    }
}