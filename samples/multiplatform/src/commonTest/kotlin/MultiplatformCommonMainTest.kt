import kmp4free.samples.MultiplatformCommonMain

import kotlin.test.Test
import kotlin.test.assertEquals

class MultiplatformCommonMainTest {
    @Test
    fun hello() {
        assertEquals(MultiplatformCommonMain().hello(), MultiplatformCommonMain::class.simpleName)
    }
}