package www.mrray.cn.model.http

data class BaseHttpModel<T>(val code: Int, var data: T, val message: String)