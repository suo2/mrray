package www.mrray.cn.customexception

class HttpException : Throwable {
    var code: Int = 0
    var httpMessage: String

    constructor(code: Int, message: String) {
        this.code = code
        this.httpMessage = message
    }
}