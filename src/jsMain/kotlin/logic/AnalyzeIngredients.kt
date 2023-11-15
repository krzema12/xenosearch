package logic

import data.xenoestrogens

fun analyzeIngredients(ingredients: String): Map<String, Boolean> {
    val individualIngredients = ingredients.split(",").map {
        it.trim().lowercase()
    }
    return xenoestrogens
        .associateBy { it.displayName }
        .mapValues {
            it.value.searchTerms.any {
                it.lowercase() in individualIngredients
            }
        }
}
