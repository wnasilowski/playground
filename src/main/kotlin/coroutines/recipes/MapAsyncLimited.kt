package coroutines.recipes.mapasynclimited

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.coroutines.CoroutineContext
import kotlin.test.assertEquals

suspend fun <T, R> Iterable<T>.mapAsync(
    concurrency: Int,
    transformation: suspend (T) -> R
): List<R> = TODO()

class MapAsyncLimitedTest {
    private val anyConcurrency = 3

    @Test
    fun should_behave_like_a_regular_map_for_a_list_and_a_set() = runTest {
        val list = ('a'..'z').toList()
        assertEquals(list.map { c -> c.inc() }, list.mapAsync(anyConcurrency) { c -> c.inc() })
        assertEquals(list.map { c -> c.code }, list.mapAsync(anyConcurrency) { c -> c.code })
        assertEquals(list.map { c -> c.uppercaseChar() }, list.mapAsync(anyConcurrency) { c -> c.uppercaseChar() })

        val set = (1..10).toSet()
        assertEquals(set.map { i -> i * i }, set.mapAsync(anyConcurrency) { i -> i * i })
        assertEquals(set.map { i -> "A$i" }, set.mapAsync(anyConcurrency) { i -> "A$i" })
        assertEquals(set.map { i -> i.toChar() }, set.mapAsync(anyConcurrency) { i -> i.toChar() })
    }

    @Test
    fun should_map_async_and_keep_elements_order() = runTest {
        val transforms = listOf(
            suspend { delay(3000); "A" },
            suspend { delay(2000); "B" },
            suspend { delay(4000); "C" },
            suspend { delay(1000); "D" },
        )

        val res = transforms.mapAsync(concurrency = 4) { it() }
        assertEquals(listOf("A", "B", "C", "D"), res)
        assertEquals(4000, currentTime)
    }

    @Test
    fun should_limit_concurrency_for_single_delay() = runTest {
        val process: suspend (Int) -> Int = { i: Int ->
            delay(1000)
            i * i
        }

        List(1000) { it }.mapAsync(concurrency = 10, transformation = process)
        assertEquals(1000 * 1000 / 10, currentTime)
    }

    @Test
    fun should_limit_concurrency_for_different_delays() = testFor(
        1 to 3000L + 2000L + 4000L + 1000L + 2000L,
        2 to 6000L,
        3 to 5000L,
        4 to 4000L,
        5 to 4000L,
    ) { concurrency, expectedTime ->
        val transforms = listOf(
            suspend { delay(3000); "A" },
            suspend { delay(2000); "B" },
            suspend { delay(4000); "C" },
            suspend { delay(1000); "D" },
            suspend { delay(2000); "E" },
        )

        val res = transforms.mapAsync(concurrency = concurrency) { it() }
        assertEquals(listOf("A", "B", "C", "D", "E"), res)
        assertEquals(expectedTime, currentTime)
    }

    @Test
    fun should_support_context_propagation() = runTest {
        var ctx: CoroutineContext? = null

        val name1 = CoroutineName("Name 1")
        withContext(name1) {
            listOf("A").mapAsync(concurrency = anyConcurrency) {
                ctx = currentCoroutineContext()
                it
            }
            assertEquals(name1, ctx?.get(CoroutineName))
        }

        val name2 = CoroutineName("Some name 2")
        withContext(name2) {
            listOf("B").mapAsync(concurrency = anyConcurrency) {
                ctx = currentCoroutineContext()
                it
            }
            assertEquals(name2, ctx?.get(CoroutineName))
        }
    }

    @Test
    fun should_support_cancellation() = runTest {
        var job: Job? = null

        val parentJob = launch {
            listOf("A").mapAsync(concurrency = anyConcurrency) {
                job = currentCoroutineContext().job
                delay(Long.MAX_VALUE)
            }
        }

        delay(1000)
        parentJob.cancel()
        assertEquals(true, job?.isCancelled)
    }
}

private fun <T1, T2> testFor(vararg data: Pair<T1, T2>, body: suspend TestScope.(T1, T2) -> Unit) {
    for ((input, expected) in data) {
        runTest {
            body(input, expected)
        }
    }
}
