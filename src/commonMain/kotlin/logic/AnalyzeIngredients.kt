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
            it.value.searchTerms.any { searchTerm ->
                individualIngredients.any { searchTerm.lowercase() in it }
            }
        }
}
