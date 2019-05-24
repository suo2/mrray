package www.mrray.cn.module.register

import android.arch.lifecycle.Observer
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.KeyEvent
import android.view.View
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.layout_register_step.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.module.register.dialog.RegisterSuccessDialog
import www.mrray.cn.repository.register.RegisterRepository
import www.mrray.cn.view.RegisterStepView
import www.mrray.cn.viewmodel.register.RegisterViewModel

class RegisterFragment : BaseViewModelFragment<RegisterViewModel, RegisterRepository>() {
    private var mCurrentStep = CURRENT.ONE
    private var mCuttentType = 3
    override fun getLayoutId(): Int {
        return R.layout.fragment_register
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        initFragment()
        title_bar.setLeftImgListener {
            back()
        }
    }

    private lateinit var registerOneFragment: RegisterOneFragment

    private lateinit var registerTwoFragment: RegisterTwoFragment

    private lateinit var registerFourFragment: RegisterFourFragment

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initFragment() {
        registerOneFragment = RegisterOneFragment()
        registerTwoFragment = RegisterTwoFragment()
//        val registerThreeGPFragment = RegisterThreeGPFragment()
        registerFourFragment = RegisterFourFragment()
        val transition = fragmentManager?.beginTransaction()
        transition?.add(R.id.register_step_fragment, registerOneFragment)
        transition?.commit()

        register_next_step.setOnClickListener {
            progress_bar.visibility = View.VISIBLE
            register_next_step.isClickable = false
            val beginTransaction = fragmentManager?.beginTransaction()
            when (mCurrentStep) {
                CURRENT.ONE -> {
                    registerOneFragment.nextStep {
                        progress_bar.visibility = View.GONE
                        beginTransaction?.replace(R.id.register_step_fragment, registerTwoFragment)
                        mCurrentStep = CURRENT.TWO
                        register_next_step.isClickable = true
                        setTopStatus(register_step_two, true, true, true)
                        beginTransaction?.commit()
                    }
                }
                CURRENT.TWO -> {
                    registerTwoFragment.nextStep {
                        mCuttentType = it
                        progress_bar.visibility = View.GONE
                        beginTransaction?.replace(R.id.register_step_fragment, registerFourFragment)
                        mCurrentStep = CURRENT.FOUR
                        setTopStatus(register_step_four, true, true, false)
                        register_next_step.text = "我同意该服务协议"
                        register_next_step.isClickable = true
                        beginTransaction?.commit()
                    }
                }
//                CURRENT.THREE -> {
//                    beginTransaction?.replace(R.id.register_step_fragment, registerFourFragment)
//                    mCurrentStep = CURRENT.FOUR
//                    register_next_step.text = "我同意该服务协议"
//                    setTopStatus(register_step_four, true, true, false)
//                }
                CURRENT.FOUR -> {
                    progress_bar.visibility = View.GONE
                    mViewModel.register(mCuttentType)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setTopStatus(view: RegisterStepView, isSelected: Boolean, leftVisible: Boolean, rightVisible: Boolean) {
        if (isSelected) {
            view.leftDividerColor = context?.resources!!.getColor(R.color.register_step_done_color)!!
            view.rightDividerColor = context?.resources!!.getColor(R.color.register_step_done_color)!!
            view.stepNumColor = context?.resources!!.getColor(R.color.register_step_done_color)!!
            view.stepNameColor = context?.resources!!.getColor(R.color.register_step_done_color)!!
            view.stepNumBackGround = R.drawable.register_step_num_background_done
        } else {
            view.leftDividerColor = context?.resources!!.getColor(R.color.register_step_undone_color)!!
            view.rightDividerColor = context?.resources!!.getColor(R.color.register_step_undone_color)!!
            view.stepNumColor = context?.resources!!.getColor(R.color.register_step_undone_color)!!
            view.stepNumBackGround = R.drawable.register_step_num_background_undone
            view.stepNameColor = context?.resources!!.getColor(R.color.register_step_undone_color)!!
        }

        view.leftDividerVisible = leftVisible
        view.rightDividerVisible = rightVisible
    }

    override fun onNetError(it: HttpException?) {
        super.onNetError(it)
        register_next_step.isClickable = true
        progress_bar.visibility = View.GONE
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mRegisterModel.observe(this, Observer {
            //注册成功
            val successDialog = RegisterSuccessDialog()
            successDialog.show(fragmentManager, "successDialog")
            successDialog.setSureClickListener {
                Navigation.findNavController(view!!).popBackStack()
                successDialog.dismiss()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                back()
                true
            } else {
                false
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun back() {
        val beginTransaction = fragmentManager?.beginTransaction()

        when (mCurrentStep) {
            CURRENT.ONE -> {
                Navigation.findNavController(this.view!!).popBackStack()
            }
            CURRENT.TWO -> {
                beginTransaction?.replace(R.id.register_step_fragment, registerOneFragment)
                mCurrentStep = CURRENT.ONE
                register_next_step.isClickable = true
                setTopStatus(register_step_two, false, true, true)
                beginTransaction?.commit()
            }
//                CURRENT.THREE -> {
//                    beginTransaction?.replace(R.id.register_step_fragment, registerFourFragment)
//                    mCurrentStep = CURRENT.FOUR
//                    register_next_step.text = "我同意该服务协议"
//                    setTopStatus(register_step_four, true, true, false)
//                }
            CURRENT.FOUR -> {
                beginTransaction?.replace(R.id.register_step_fragment, registerTwoFragment)
                mCurrentStep = CURRENT.TWO
                setTopStatus(register_step_four, false, true, false)
                register_next_step.text = "下一步"
                register_next_step.isClickable = true
                beginTransaction?.commit()
            }
        }
    }
}

enum class CURRENT {
    ONE,
    TWO,
    //    THREE,
    FOUR
}