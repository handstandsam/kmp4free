package kmp4free.samples

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class MultiplatformJvmTest {
    @Test
    fun hello() {
        assertThat(MultiplatformCommonMain().hello())
            .isEqualTo(MultiplatformCommonMain::class.java.simpleName)
    }
}