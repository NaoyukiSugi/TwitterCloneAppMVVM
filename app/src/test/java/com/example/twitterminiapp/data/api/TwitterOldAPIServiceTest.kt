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

class TwitterOldAPIServiceTest {

    private lateinit var aplService: TwitterOldAPIService
    private lateinit var server: MockWebServer

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        aplService = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TwitterOldAPIService::class.java)
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getTimeline should return Response of Tweet list`() {
        runBlocking {
            enqueueMockResponse("timeline_response.json")
            val responseBody = aplService.getTimeline().body()

            val topTweet = responseBody!![0]

            assertEquals(1374278834393026561, topTweet.id)
            assertEquals("Tue Mar 23 08:36:21 +0000 2021", topTweet.createdAt)
            assertEquals(
                "本日のヒカキンTVはこちら！→【1枚110万円のマスクを購入しました。】 https://t.co/ClW7a48ldI @YouTubeより https://t.co/2ytlnxVxAT",
                topTweet.text
            )
        }
    }

    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }
}
