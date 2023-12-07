package data

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class XenoestrogensTest : FunSpec({
    test("check if display names are unique") {
        val repeatedEntries = xenoestrogens
            .groupingBy { it.displayName }
            .eachCount()
            .filter { it.value > 1 }
        repeatedEntries shouldBe emptyMap()
    }
})
