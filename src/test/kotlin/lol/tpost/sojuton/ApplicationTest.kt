@file:Suppress("LeakingThis")

package lol.tpost.sojuton

import io.kotest.matchers.shouldBe
import java.util.*

class ApplicationTest : IntegrationTestBase() {
    init {
        context("startup") {
            test("애플리케이션이 시작되면 timezone은 Asia/Seoul이다") {
                // Given
                val timezone = TimeZone.getDefault()

                // When
                val actual = timezone.id

                // Then
                actual shouldBe "Asia/Seoul"
            }
        }
    }
}
