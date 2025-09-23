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
}
