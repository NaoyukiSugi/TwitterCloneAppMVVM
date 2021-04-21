package com.example.twitterminiapp.presentation.adapter

import android.view.ViewGroup
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.databinding.ViewTweetBinding
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.*

internal class HomeAdapterTest {

    private lateinit var adapter: HomeAdapter

    @BeforeEach
    fun setUp() {
        adapter = spy(HomeAdapter())
    }

    @Test
    fun `onCreateViewHolder should return TwitterViewHolder`() {
        val viewTweetBinding: ViewTweetBinding = mock()
        val viewHolder: TwitterViewHolder = mock()
        val parent: ViewGroup = mock()
        val viewType = 0
        doReturn(viewTweetBinding).whenever(adapter).createViewTweetBinding(parent)
        doReturn(viewHolder).whenever(adapter).createTwitterViewHolder(viewTweetBinding)

        val result = adapter.onCreateViewHolder(parent, viewType)

        assertEquals(viewHolder, result)
    }

    @Test
    fun `onBindViewHolder should call bind`() {
        val viewHolder: TwitterViewHolder = mock()
        val position = 0
        val tweet: Tweet = mock()
        doReturn(tweet).whenever(adapter).getTweet(position)

        adapter.onBindViewHolder(viewHolder, position)

        verify(viewHolder).bind(tweet)
    }

    @Test
    fun `setOnUserIconClickListener should set listener`() {
        val listener: ((Tweet) -> Unit) = mock()

        adapter.setOnUserIconClickListener(listener)

        assertEquals(listener, adapter.onUserIconClickListener)
    }

    // region areItemsTheSame
    @Test
    fun `DIFF_CALLBACK areItemsTheSame should return true when item ids are same`() {
        val oldItem: Tweet = mock { on { id } doReturn 1 }
        val newItem: Tweet = mock { on { id } doReturn 1 }

        val result = HomeAdapter.DIFF_CALLBACK.areItemsTheSame(oldItem, newItem)

        assertTrue(result)
    }

    @Test
    fun `DIFF_CALLBACK areItemsTheSame should not return false when item ids are not same`() {
        val oldItem: Tweet = mock { on { id } doReturn 1 }
        val newItem: Tweet = mock { on { id } doReturn 2 }

        val result = HomeAdapter.DIFF_CALLBACK.areItemsTheSame(oldItem, newItem)

        assertFalse(result)
    }
    // endregion

    // region areContentsTheSame
    @Test
    fun `DIFF_CALLBACK areContentsTheSame should return true when item instances are same`() {
        val oldItem: Tweet = mock()
        val newItem: Tweet = oldItem

        val result = HomeAdapter.DIFF_CALLBACK.areContentsTheSame(oldItem, newItem)

        assertTrue(result)
    }

    @Test
    fun `DIFF_CALLBACK areContentsTheSame should return false when item instances are not same`() {
        val oldItem: Tweet = mock()
        val newItem: Tweet = mock()

        val result = HomeAdapter.DIFF_CALLBACK.areContentsTheSame(oldItem, newItem)

        assertFalse(result)
    }
    // endregion
}
