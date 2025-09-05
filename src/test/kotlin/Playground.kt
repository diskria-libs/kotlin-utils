import io.github.diskria.utils.kotlin.extensions.common.`kebab-case`
import io.github.diskria.utils.kotlin.extensions.common.sealedObjectsRecursive
import io.github.diskria.utils.kotlin.extensions.setCase
import io.github.diskria.utils.kotlin.words.StringCase

fun main() {
    val testData = "kotlin-utils"

    StringCase::class.sealedObjectsRecursive().forEach { case ->
        println("${case::class.simpleName}: ${testData.setCase(`kebab-case`, case)}")
    }
}
