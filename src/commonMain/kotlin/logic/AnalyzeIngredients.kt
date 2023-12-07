package logic

import data.xenoestrogens
import model.Ingredient

fun analyzeIngredients(
    ingredients: String,
    knownXenoestrogens: List<Ingredient> = xenoestrogens,
): Map<String, Boolean> {
    val individualIngredients = ingredients.split(",", ".", "\n").map {
        it.trim().lowercase()
    }
    return knownXenoestrogens
        .associateBy { it.displayName }
        .mapValues {
            println("== ${it.key}")
            it.value.searchTerms.any { searchTerm ->
                print("  - $searchTerm: ")
                val result = individualIngredients.any { searchTerm.lowercase() in it }
                println(result)
                result
            }
        }
}
