package logic

fun analyzeIngredients(ingredients: String): Map<String, Boolean> {
    if (ingredients.isBlank()) {
        return emptyMap()
    }

    return ingredients.split(",")
        .map { it.trim() }
        .associateWith { isXenoestrogen(it) }
}

private val xenoestrogens = listOf(
    "BPA",
    "parabeny",
)

private fun isXenoestrogen(name: String): Boolean =
    xenoestrogens.any { it.lowercase() == name.lowercase() }
