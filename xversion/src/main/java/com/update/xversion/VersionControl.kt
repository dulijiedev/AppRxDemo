package  com.update.xversion

class VersionControl(
    private val isForce: Boolean,
    private val current: VersionBean,
    private val latest: VersionBean,
    private val minimum: VersionBean,
    private val updateInfo: String?,
    private val ipaUrlString: String?
) {

    fun needUpdate(): Update? {
        if (current >= latest) {
            return null
        }
        val forceUpdate = isForce || minimum > current
        return Update(forceUpdate, updateInfo, ipaUrlString, latest)
    }


    class Update(
        private val forcely: Boolean,
        private val updateInfo: String?,
        val ipaUrl: String?,
        val to: VersionBean
    ) {

        /**
         * 更新文案
         */
        fun news(): String {
            return updateInfo ?: "".replace("\\n", "\n")
        }


        /**
         * 是否强制更新
         */
        fun byForce(): Boolean {
            return forcely
        }
    }
}

