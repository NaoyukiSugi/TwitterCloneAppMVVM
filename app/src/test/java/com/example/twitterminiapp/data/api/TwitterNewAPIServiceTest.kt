package com.example.twitterminiapp.data.api

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals

class TwitterNewAPIServiceTest {

    private lateinit var apiService: TwitterNewAPIService
    private lateinit var server: MockWebServer

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TwitterNewAPIService::class.java)
    }

    @AfterEach
    internal fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getSearchedTimeline should return Response of GetSearchedTweetsResponse`() {
        runBlocking {
            enqueueMockResponse("searched_tweet.json")
            val responseBody = apiService.getSearchedTimeline(searchQuery = "コロナ").body()
            val topTweet = responseBody!!.tweets[0]
            assertEquals(1386610429254766595, topTweet.id)
            assertEquals("2021-04-26T09:17:42.000Z", topTweet.createdAt)
            assertEquals(
                "RT @kyototower_1228: 【臨時休業のお知らせ】\n新型コロナウィルス感染拡大防止のため、京都タワー展望室および“ゴジラvs京都”コラボイベントは、2021年4月26日(月)〜当面の間、臨時休業をいたします。",
                topTweet.text
            )
        }
    }

    private fun enqueueMockResponse(
        fileName: String
    ) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }
}
