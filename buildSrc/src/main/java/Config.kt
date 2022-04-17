import org.gradle.api.JavaVersion

object Config {
    const val minSdk = 24
    const val compileSdk = 32
    const val targetSdk = 32
    const val versionCode = 1
    const val versionName = "1.0.0"
    val JAVA_VERSION = JavaVersion.VERSION_11
}
