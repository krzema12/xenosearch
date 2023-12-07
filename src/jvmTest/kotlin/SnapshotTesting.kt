import io.kotest.core.test.TestScope
import io.kotest.matchers.shouldBe
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.io.path.writeText

internal fun Map<String, Boolean>.shouldMatchSnapshot(testScope: TestScope) {
    val onlyFound = this.filter { it.value }
    val sortedKeys = onlyFound.toSortedMap()
    val serialized = json.encodeToString<Map<String, Boolean>>(sortedKeys)
    val pathToSnapshot = Path("src/jvmTest/resources/products/${testScope.testCase.name.testName}-snapshot.json")

    if (System.getenv("UPDATE_SNAPSHOTS") == null) {
        val currentlyStored = pathToSnapshot.readText()
        currentlyStored shouldBe serialized
    } else {
        pathToSnapshot.writeText(serialized)
    }
}

private val json = Json {
    prettyPrint = true
}
