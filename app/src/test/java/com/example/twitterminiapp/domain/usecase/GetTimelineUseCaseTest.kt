package com.example.twitterminiapp.domain.usecase

import com.example.twitterminiapp.domain.repository.TwitterRepository
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetTimelineUseCaseTest {

    private lateinit var getTimelineUseCase: GetTimelineUseCase
    private val repository: TwitterRepository = mock()

    @BeforeEach
    fun setUp() {
        getTimelineUseCase = GetTimelineUseCase(repository)
    }

    @Test
    fun `execute should call getTimeline`() {
        runBlockingTest {
            getTimelineUseCase.execute()
            verify(repository).getTimeline()
        }
    }
}
