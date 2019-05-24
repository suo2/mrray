package com.hyphenate.easeui.ui.im

import android.os.Bundle
import android.widget.Toast
import com.hyphenate.easeui.EaseConstant
import com.hyphenate.easeui.R
import com.hyphenate.easeui.ui.EaseBaseActivity
import com.hyphenate.easeui.ui.EaseChatFragment


class ImChatActivity : EaseBaseActivity() {

    var toChatUserId: String? = null
    private var chatFragment: EaseChatFragment? = null

    companion object {
        const val EXTRA_USER_ID = "user_id"
        const val EXTRA_USER_NAME = "user_name"
    }

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        setContentView(R.layout.activity_im_chat)
        init()
    }

    fun init() {
        //user or group id
        toChatUserId = intent.extras?.getString(EaseConstant.EXTRA_USER_ID)

        if (toChatUserId == null) {
            //set arguments
            finish()
//            Toast.makeText(this, "用户信息出错", Toast.LENGTH_SHORT).show()
        } else {  //传入参数
            chatFragment = EaseChatFragment()
            val args = Bundle()
            args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE)
            args.putString(EaseConstant.EXTRA_USER_ID, toChatUserId)
            args.putString(EaseConstant.EXTRA_USER_NAME, intent.getStringExtra(EaseConstant.EXTRA_USER_NAME))
            chatFragment!!.arguments = args
            supportFragmentManager.beginTransaction().add(R.id.container, chatFragment!!).commit()
        }
    }
}