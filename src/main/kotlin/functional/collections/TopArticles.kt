package functional.collections.toparticles

import junit.framework.TestCase.assertEquals
import org.junit.Test

class TopArticlesGenerator(
    private val articles: List<ArticleStatistics>,
) {
    fun topArticles(n: Int): List<ArticleStatistics> = articles
    .withIndex()
    .sortedByDescending { (_, article) ->
        article.views
    }
    .take(n)
    .sortedBy {(index, _) -> index}
    .map {(_, article) -> article}
}
data class ArticleStatistics(
    val title: String,
    val views: Long,
)

fun main() {
    val generator = TopArticlesGenerator(
        listOf(
            ArticleStatistics("Article 1", 400),
            ArticleStatistics("Article 2", 100),
            ArticleStatistics("Article 3", 200),
            ArticleStatistics("Article 4", 300),
            ArticleStatistics("Article 5", 500),
            ArticleStatistics("Article 6", 0),
        )
    )
    val topArticles = generator.topArticles(3)

    topArticles.onEach { println(it) }
    // ArticleStatistics(title=Article 1, views=400)
    // ArticleStatistics(title=Article 4, views=300)
    // ArticleStatistics(title=Article 5, views=500)
}

class TopArticlesTest {
    @Test
    fun `Top articles are returned in the correct order`() {
        val articles = listOf(
            ArticleStatistics("Article 1", 400),
            ArticleStatistics("Article 2", 100),
            ArticleStatistics("Article 3", 200),
            ArticleStatistics("Article 4", 300),
            ArticleStatistics("Article 5", 500),
            ArticleStatistics("Article 6", 0),

        )
        val generator = TopArticlesGenerator(articles)
        val topArticles = generator.topArticles(100)
        assertEquals(articles, topArticles)
    }

    @Test
    fun `Only n top articles are kept`() {
        val articles = listOf(
            ArticleStatistics("Article 1", 400),
            ArticleStatistics("Article 2", 100),
            ArticleStatistics("Article 3", 200),
            ArticleStatistics("Article 4", 300),
            ArticleStatistics("Article 5", 500),
            ArticleStatistics("Article 6", 0),

        )
        val generator = TopArticlesGenerator(articles)
        val topArticles = generator.topArticles(3)
        assertEquals(articles.slice(listOf(0, 3, 4)), topArticles)
    }
}
