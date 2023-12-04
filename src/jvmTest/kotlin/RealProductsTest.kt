import io.kotest.core.spec.style.FunSpec
import logic.analyzeIngredients
import kotlin.io.path.Path

class RealProductsTest : FunSpec({
    Path("src/jvmTest/resources/products").toFile()
        .list { _, name -> name.endsWith(".txt") }
        ?.map { it.removeSuffix(".txt") }
        ?.forEach { testFileName ->
            test(testFileName) {
                val foundXenoestrogens = analyzeIngredients(ingredients = getProductIngredients(this.testCase.name.testName))
                foundXenoestrogens.shouldMatchSnapshot(this)
            }
        } ?: error("No test files found!")
})

private fun getProductIngredients(name: String): String =
    RealProductsTest::class.java.getResource("products/$name.txt")?.readText()
        ?: error("Could not read the resource!")
