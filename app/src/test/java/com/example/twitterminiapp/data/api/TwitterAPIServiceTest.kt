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

class TwitterAPIServiceTest {

    private lateinit var service: TwitterAPIService
    private lateinit var server: MockWebServer

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TwitterAPIService::class.java)
    }

    @AfterEach
    internal fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getTimeline should receive expected contents`() {
        runBlocking {
            enqueueMockResponse("timeline_response.json")
            val responseBody = service.getTimeline().body()
            val topTweet = responseBody!![0]
            assertEquals(1374278834393026561, topTweet.id)
            assertEquals("Tue Mar 23 08:36:21 +0000 2021", topTweet.createdAt)
            assertEquals(
                "本日のヒカキンTVはこちら！→【1枚110万円のマスクを購入しました。】 https://t.co/ClW7a48ldI @YouTubeより https://t.co/2ytlnxVxAT",
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
