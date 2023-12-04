package logic

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import model.Ingredient

class AnalyzeIngredientsTest : FunSpec({
    test("finds multiple ingredients") {
        // given
        val known = listOf(
            Ingredient(
                displayName = "Foo",
                searchTerms = listOf("foo"),
            ),
            Ingredient(
                displayName = "Bar",
                searchTerms = listOf("bar"),
            ),
        )
        val ingredients = "foo, bar, baz"

        // when
        val found = analyzeIngredients(ingredients = ingredients, knownXenoestrogens = known)

        // then
        found shouldBe mapOf(
            "Foo" to true,
            "Bar" to true,
        )
    }

    test("finds one ingredient") {
        // given
        val known = listOf(
            Ingredient(
                displayName = "Foo",
                searchTerms = listOf("foo"),
            ),
            Ingredient(
                displayName = "Bar",
                searchTerms = listOf("bar"),
            ),
        )
        val ingredients = "foo, baz"

        // when
        val found = analyzeIngredients(ingredients = ingredients, knownXenoestrogens = known)

        // then
        found shouldBe mapOf(
            "Foo" to true,
            "Bar" to false,
        )
    }

    test("ignores punctuation and whitespace characters") {
        // given
        val known = listOf(
            Ingredient(
                displayName = "Foo",
                searchTerms = listOf("foo"),
            ),
            Ingredient(
                displayName = "Bar",
                searchTerms = listOf("bar"),
            ),
            Ingredient(
                displayName = "Baz",
                searchTerms = listOf("baz"),
            ),
        )
        val ingredients = """foo    , bar
            baz.
        """.trimIndent()

        // when
        val found = analyzeIngredients(ingredients = ingredients, knownXenoestrogens = known)

        // then
        found shouldBe mapOf(
            "Foo" to true,
            "Bar" to true,
            "Baz" to true,
        )
    }
})
