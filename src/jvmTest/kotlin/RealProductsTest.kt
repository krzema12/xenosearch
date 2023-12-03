import io.kotest.core.config.configuration
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import logic.analyzeIngredients
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.io.path.writeText

class RealProductsTest : FunSpec({
    test("Deoproce SPF50") {
        val currentlyStored = Path("src/jvmTest/resources/products/Deoproce SPF50-snapshot.json").readText()
        val foundXenoestrogens = analyzeIngredients(ingredients = getProductIngredients("Deoproce SPF50"))
        val serialized = Json.encodeToString(MapSerializer(String.serializer(), Boolean.serializer()), foundXenoestrogens)

        currentlyStored shouldBe serialized

        Path("src/jvmTest/resources/products/Deoproce SPF50-snapshot.json").writeText(serialized)
    }
})

private fun getProductIngredients(name: String): String =
    RealProductsTest::class.java.getResource("products/$name.txt")?.readText()
        ?: error("Could not read the resource!")
