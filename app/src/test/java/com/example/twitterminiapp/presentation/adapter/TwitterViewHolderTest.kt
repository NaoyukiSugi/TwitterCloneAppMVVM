package com.example.twitterminiapp.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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

    private val userIconMock: ImageView = mock()
    private val userNameMock: TextView = mock()
    private val userScreenNameMock: TextView = mock()
    private val userDescriptionMock: TextView = mock()

//    private val rootView: ConstraintLayout = mock {
//        on { findViewById<ImageView>(R.id.user_icon) } doReturn userIcon
//        on { findViewById<TextView>(R.id.user_name) } doReturn userName
//        on { findViewById<TextView>(R.id.user_screen_name) } doReturn userScreenName
//        on { findViewById<TextView>(R.id.user_description) } doReturn userDescription
//    }

    private val viewTweetBinding: ViewTweetBinding = mock()

    @BeforeEach
    fun setUp() {
        viewTweetBinding.apply {
            doReturn(userIconMock).whenever(userIcon)
            doReturn(userNameMock).whenever(userName)
            doReturn(userScreenNameMock).whenever(userScreenName)
            doReturn(userDescriptionMock).whenever(userDescription)
        }

        viewHolder = spy(TwitterViewHolder(viewTweetBinding, requestManager, listener))
    }

    @Test
    fun `bind should load image into userIcon`() {
//        viewHolder.bind(tweet)

//        verify(requestManager).load(tweet.user.profileImageUrlHttps).into(viewTweetBinding.userIcon)
    }
}
