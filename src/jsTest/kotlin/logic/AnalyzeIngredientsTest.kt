package logic

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class AnalyzeIngredientsTest : FunSpec({
    test("finds multiple ingredients") {
        // given
        val ingredients = "water, BPA, glycerol, parabens"

        // when
        val found = analyzeIngredients(ingredients = ingredients)

        // then
        found shouldBe mapOf(
            "Bisfenol A" to true,
            "Parabeny" to true,
        )
    }

    test("finds one ingredient") {
        // given
        val ingredients = "water, BPA"

        // when
        val found = analyzeIngredients(ingredients = ingredients)

        // then
        found shouldBe mapOf(
            "Bisfenol A" to true,
            "Parabeny" to false,
        )
    }
})
