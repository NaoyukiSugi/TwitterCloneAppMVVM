package com.example.twitterminiapp.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestManager
import com.example.twitterminiapp.R
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.data.model.User
import com.example.twitterminiapp.databinding.ViewTweetBinding
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.mockito.kotlin.*

internal class TwitterViewHolderTest {

    lateinit var viewHolder: TwitterViewHolder
    private val requestManager: RequestManager = mock()
    private val listener: (Tweet) -> Unit = mock()

    private val userIcon: ImageView = mock()
    private val userName: TextView = mock()
    private val userScreenName: TextView = mock()
    private val userDescription: TextView = mock()

    private val rootView: View = mock {
        on { findViewById<ImageView>(R.id.user_icon) } doReturn userIcon
        on { findViewById<TextView>(R.id.user_name) } doReturn userName
        on { findViewById<TextView>(R.id.user_screen_name) } doReturn userScreenName
        on { findViewById<TextView>(R.id.user_description) } doReturn userDescription
    }

    private val viewTweetBinding: ViewTweetBinding = mock { on { root } doReturn rootView }

    private val user: User = mock { on { profileImageUrlHttps } doReturn "profileImageUrlHttps" }
    private val tweet: Tweet = mock { on { user } doReturn user }

    @BeforeEach
    fun setUp() {
        viewHolder = spy(TwitterViewHolder(viewTweetBinding, requestManager, listener))
    }

    @Test
    fun `bind should load image into userIcon`() {
        viewHolder.bind(tweet)

        verify(requestManager).load(tweet.user.profileImageUrlHttps).into(viewTweetBinding.userIcon)
    }
}
