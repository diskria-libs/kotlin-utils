import io.github.diskria.kotlin.utils.extensions.common.SCREAMING_SNAKE_CASE
import io.github.diskria.kotlin.utils.extensions.common.camelCase
import io.github.diskria.kotlin.utils.extensions.common.`kebab-case`
import io.github.diskria.kotlin.utils.extensions.setCase
import kotlin.test.Test
import kotlin.test.assertEquals

class StringCaseTest {

    @Test
    fun `should convert kebab-case to camelCase`() {
        assertEquals("kotlinUtils", "kotlin-utils".setCase(`kebab-case`, camelCase))
    }

    @Test
    fun `should convert camelCase to kebab-case`() {
        assertEquals("kotlin-utils", "kotlinUtils".setCase(camelCase, `kebab-case`))
    }

    @Test
    fun `should convert SCREAMING_SNAKE_CASE to camelCase`() {
        assertEquals("kotlin-utils", "KOTLIN_UTILS".setCase(SCREAMING_SNAKE_CASE, `kebab-case`))
    }
}
