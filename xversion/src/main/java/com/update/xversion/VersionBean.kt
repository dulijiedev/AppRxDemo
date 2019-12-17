package  com.update.xversion

data class VersionBean constructor(
    private val major: Int?,
    private val minor: Int?,
    private val point: Int?,
    private val value: Any?
) : Comparable<VersionBean> {

    constructor(_value: String) : this(
        _value.compBy(".").index(0).toIntValue(),
        _value.compBy(".").index(1).toIntValue(),
        _value.compBy(".").index(2).toIntValue(),
        _value.compBy(".")
    )

    constructor(
        major: Int?,
        minor: Int?,
        point: Int?
    ) : this(
        major.nilValue(),
        minor.nilValue(),
        point.nilValue(),
        arrayOf(major, minor, point)
    )


    override fun compareTo(other: VersionBean): Int {
        return when {
            major.nilValue() > other.major.nilValue() -> return 1
            major.nilValue() < other.major.nilValue() -> return -1
            major == other.major -> {
                when {
                    minor.nilValue() > other.minor.nilValue() -> 1
                    minor.nilValue() < other.minor.nilValue() -> -1
                    minor == other.minor.nilValue() -> {
                        when {
                            point.nilValue() > other.point.nilValue() -> 1
                            point.nilValue() < other.point.nilValue() -> -1
                            point == other.point -> 0
                            else -> {
                                0
                            }
                        }
                    }
                    else -> {
                        0
                    }
                }
            }
            else -> {
                0
            }
        }
    }

    override fun toString(): String {
        return "$major.$minor.$point"
    }
}

internal fun String.compBy(tag: String): Array<String> {
    return this.split(tag).toTypedArray()
}

internal fun <T> Array<T>.index(index: Int): T? {
    return if (this.size > index) {
        this[index]
    } else {
        null
    }
}

internal fun Int?.nilValue(): Int {
    return this ?: 0
}

internal fun String?.nilValue(): String {
    return this ?: "0"
}

internal fun String?.toIntValue(): Int {
    val value = this?.toIntOrNull()
    return value.nilValue()
}




