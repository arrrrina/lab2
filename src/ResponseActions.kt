class ResponseActions(val code : Int, val body: String?) {
    val response = Response(code, body)

    fun andExpect(check: ResponseMatchers.() -> Unit) : ResponseActions {
        val matchers = ResponseMatchers()
        matchers.check()
        return this
    }
    fun andDo(check: (Response) -> Unit) : ResponseActions {
        check(response)
        return this
    }


    inner class ResponseMatchers(){
        fun status(check : StatusResponseMatchers.() -> Unit) {
            val status = StatusResponseMatchers()
            status.check()
        }
        fun body(check : BodyResponseMatchers.() -> Unit) {
            val bodycheck = BodyResponseMatchers()
            bodycheck.check()
        }
    }

    inner class StatusResponseMatchers(){
        fun isOk() {
            if (code!=200){
                throw StatusResponseMatchersException("Status isn't 200, it is $code")
            }
        }
        fun isBadRequest() {
            if (code!=400){
                throw StatusResponseMatchersException("Status isn't 200, it is $code")
            }
        }
        fun isInternalServerError(){
            if (code!=500){
                throw StatusResponseMatchersException("Status isn't 200, it is $code")
            }
        }
    }
    inner class BodyResponseMatchers(){
        fun isNull() {
            if(body!=null){
                throw BodyResponseMatchersException("Body = $body")
            }
        }
        fun isNotNull() {
            if (body==null){
                throw BodyResponseMatchersException("Body is empty")
            }
        }

    }
}
