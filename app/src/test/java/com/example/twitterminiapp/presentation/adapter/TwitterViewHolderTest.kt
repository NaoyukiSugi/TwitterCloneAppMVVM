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

    private lateinit var viewHolder: TwitterViewHolder
    private val requestManager: RequestManager = mock()
    private val listener: ((Tweet) -> Unit) = mock()

    private val homeUserIconMock: ImageView = mock()
    private val homeUserNameMock: TextView = mock()
    private val homeUserScreenNameMock: TextView = mock()
    private val homeUserDescriptionMock: TextView = mock()

    private val viewTweetBinding: ViewTweetBinding = mock {
        on { root } doReturn mock()
        on { homeUserIcon } doReturn homeUserIconMock
        on { homeUserName } doReturn homeUserNameMock
        on { homeUserScreenName } doReturn homeUserScreenNameMock
        on { homeUserDescription } doReturn homeUserDescriptionMock
    }

//    val rootView: ConstraintLayout = mock {
//        on { findViewById<ImageView>(R.id.home_user_icon) } doReturn userIconMock
//        on { findViewById<TextView>(R.id.home_user_name) } doReturn userNameMock
//        on { findViewById<TextView>(R.id.home_user_screen_name) } doReturn userScreenNameMock
//        on { findViewById<TextView>(R.id.home_user_description) } doReturn userDescriptionMock
//    }

    private val tweet: Tweet = Tweet(
        id = 1,
        createdAt = "createdAt",
        text = "text",
        user = User(
            id = 1L,
            name = "name",
            screenName = "screenName",
            description = "description",
            profileImageUrlHttps = "profileImageUrlHttps"
        )
    )

    @BeforeEach
    fun setUp() {
//        doReturn(viewTweetBinding).whenever(viewTweetBinding).apply(any())
//        doReturn(rootView).whenever(viewTweetBinding).root
//        doReturn(userNameMock).whenever(viewTweetBinding).homeUserName
        viewTweetBinding.homeUserName

//        viewTweetBinding.apply {
//            doReturn(userIconMock).whenever(userIcon)
//            doReturn(userNameMock).whenever(userName)
//            doReturn(userScreenNameMock).whenever(userScreenName)
//            doReturn(userDescriptionMock).whenever(userDescription)
//        }

        viewHolder = spy(TwitterViewHolder(viewTweetBinding, requestManager, listener))
    }

    @Test
    fun `bind should load image into userIcon`() {
        viewHolder.bind(tweet)

//        verify(requestManager).load(tweet.user.profileImageUrlHttps).into(viewTweetBinding.userIcon)
    }
}
