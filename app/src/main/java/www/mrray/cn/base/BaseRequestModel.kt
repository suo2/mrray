package www.mrray.cn.base

import www.mrray.cn.customexception.HttpException

data class BaseRequestModel<T>(var success: (T) ->Unit,
                               var error: (HttpException) ->Unit
)