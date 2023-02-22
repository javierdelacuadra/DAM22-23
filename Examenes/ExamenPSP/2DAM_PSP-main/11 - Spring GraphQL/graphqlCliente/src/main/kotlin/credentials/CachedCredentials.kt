package credentials



object CachedCredentials{
    fun invalidate() {
        username = null
        password = null
        role = null
        token = null
    }

    var role: String? = null
    var username: String? = null
    var password: String? = null
    var token: String? = null
    init {
        role = null
        username = null
        password = null
        token = null
    }
}
