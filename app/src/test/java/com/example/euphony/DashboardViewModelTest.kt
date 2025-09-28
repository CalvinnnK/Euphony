package com.example.euphony

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.media3.common.Player
import com.example.euphony.app.data.model.MusicResponse
import com.example.euphony.app.domain.repository.MusicPlayerRepository
import com.example.euphony.app.presentation.model.MusicItem
import com.example.euphony.app.presentation.ui.dashboard.DashboardViewModel
import com.example.euphony.app.service.MusicPlayerManagerService
import com.example.euphony.core.utils.Event
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class DashboardViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Mocks for the ViewModel's dependencies
    @MockK
    private lateinit var repository: MusicPlayerRepository

    @MockK
    private lateinit var musicPlayerService: MusicPlayerManagerService

    @MockK(relaxed = true)
    private lateinit var musicItemsObserver: Observer<Event<List<MusicItem>>>

    @MockK(relaxed = true)
    private lateinit var loadingObserver: Observer<Event<Boolean>>


    private lateinit var viewModel: DashboardViewModel


    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(repository, musicPlayerService)
        viewModel.musicItems.observeForever(musicItemsObserver)
        viewModel.loading.observeForever(loadingObserver)
    }

    @After
    fun tearDown() {
        viewModel.musicItems.removeObserver(musicItemsObserver)
        viewModel.loading.removeObserver(loadingObserver)
        clearAllMocks()
        unmockkAll()
        Dispatchers.resetMain()
    }

    fun createJackJohnsonMusicResponse(): MusicResponse {
        return MusicResponse(
            wrapperType = "track",
            kind = "song",
            artistId = 909253L,
            trackId = 120954025L,
            artistName = "Jack Johnson",
            trackName = "Upside Down",
            trackCensoredName = "Upside Down",
            artistViewUrl = "https://music.apple.com/us/artist/jack-johnson/909253?uo=4",
            trackViewUrl = "https://music.apple.com/us/album/upside-down/120954021?i=120954025&uo=4",
            previewUrl = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview115/v4/6d/83/79/6d837910-4c45-a7b2-658b-b1d977727375/mzaf_13997623871101967817.plus.aac.p.m4a",
            artworkUrl30 = null, // Not in the provided JSON
            artworkUrl60 = "https://is1-ssl.mzstatic.com/image/thumb/Music/v4/ec/3b/68/ec3b6851-5184-33f9-86aa-3d32575c5717/source/60x60bb.jpg",
            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music/v4/ec/3b/68/ec3b6851-5184-33f9-86aa-3d32575c5717/source/100x100bb.jpg",
            collectionPrice = 10.99,
            trackPrice = 0.99,
            trackRentalPrice = null,
            collectionHdPrice = null,
            trackHdPrice = null,
            trackHdRentalPrice = null,
            releaseDate = "2006-02-28T08:00:00Z", // Example value, can be adjusted
            collectionExplicitness = "notExplicit",
            trackExplicitness = "notExplicit",
            trackTimeMillis = 210743L,
            country = "USA",
            currency = "USD",
            primaryGenreName = "Rock",
            contentAdvisoryRating = null,
            shortDescription = null,
            longDescription = null
        )
    }

    fun createJackJohnsonMusicList(): List<MusicResponse> {
        return listOf(
            MusicResponse(
                wrapperType = "track",
                kind = "feature-movie",
                artistId = 909253,
                trackId = 1467927519,
                artistName = "Jack Johnson",
                trackName = "En Concert",
                trackCensoredName = "En Concert",
                artistViewUrl = "https://music.apple.com/us/artist/jack-johnson/909253?uo=4",
                trackViewUrl = "https://itunes.apple.com/us/movie/en-concert/id1467927519?uo=4",
                previewUrl = "https://video-ssl.itunes.apple.com/itunes-assets/Video128/v4/be/ef/af/beefaffe-6278-4336-07a1-de180ddaeaf8/mzvf_1177979557611386379.640x468.h264lc.U.p.m4v",
                artworkUrl30 = "https://is1-ssl.mzstatic.com/image/thumb/Video/8b/ea/6c/mzi.gnjahvkg.jpg/30x30bb.jpg",
                artworkUrl60 = "https://is1-ssl.mzstatic.com/image/thumb/Video/8b/ea/6c/mzi.gnjahvkg.jpg/60x60bb.jpg",
                artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Video/8b/ea/6c/mzi.gnjahvkg.jpg/100x100bb.jpg",
                collectionPrice = 12.99,
                trackPrice = 12.99,
                trackRentalPrice = null,
                collectionHdPrice = null,
                trackHdPrice = null,
                trackHdRentalPrice = null,
                releaseDate = "2009-01-01T08:00:00Z",
                collectionExplicitness = "notExplicit",
                trackExplicitness = "notExplicit",
                trackTimeMillis = 4773648,
                country = "USA",
                currency = "USD",
                primaryGenreName = "Concert Films",
                contentAdvisoryRating = "NR",
                shortDescription = "Jack Johnson:: En Concert",
                longDescription = "Jack Johnson:: En ConcertA Live Film and Album.Words by Emmett Malloy..."
            ),
            MusicResponse(
                wrapperType = "track",
                kind = "feature-movie",
                artistId = null,
                trackId = 1020340181,
                artistName = "Justin Krumb",
                trackName = "Minds In the Water",
                trackCensoredName = "Minds In the Water",
                artistViewUrl = null,
                trackViewUrl = "https://itunes.apple.com/us/movie/minds-in-the-water/id1020340181?uo=4",
                previewUrl = "https://video-ssl.itunes.apple.com/itunes-assets/Video122/v4/56/ec/5e/56ec5e69-d3a2-1724-efbc-ba2f4ce41397/mzvf_7061884105406007548.640x478.h264lc.U.p.m4v",
                artworkUrl30 = "https://is1-ssl.mzstatic.com/image/thumb/Video7/v4/3c/b8/ee/3cb8ee50-57da-7729-a685-57aa0985fb95/MindsintheWater_iTunes.jpg/30x30bb.jpg",
                artworkUrl60 = "https://is1-ssl.mzstatic.com/image/thumb/Video7/v4/3c/b8/ee/3cb8ee50-57da-7729-a685-57aa0985fb95/MindsintheWater_iTunes.jpg/60x60bb.jpg",
                artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Video7/v4/3c/b8/ee/3cb8ee50-57da-7729-a685-57aa0985fb95/MindsintheWater_iTunes.jpg/100x100bb.jpg",
                collectionPrice = 9.99,
                trackPrice = 9.99,
                trackRentalPrice = 3.99,
                collectionHdPrice = 12.99,
                trackHdPrice = 12.99,
                trackHdRentalPrice = 3.99,
                releaseDate = "2012-11-13T08:00:00Z",
                collectionExplicitness = "notExplicit",
                trackExplicitness = "notExplicit",
                trackTimeMillis = 5106472,
                country = "USA",
                currency = "USD",
                primaryGenreName = "Action & Adventure",
                contentAdvisoryRating = "NR",
                shortDescription = "Pro soul surfer Dave \"Rasta\" Rastovich went from observer to activist...",
                longDescription = "Pro soul surfer Dave \"Rasta\" Rastovich went from observer to activist..."
            ),
            MusicResponse(
                wrapperType = "track",
                kind = "tv-episode",
                artistId = 129458982,
                trackId = 712018772,
                artistName = "The Colbert Report",
                trackName = "The Colbert Report 9/19/2013",
                trackCensoredName = "The Colbert Report 9/19/2013",
                artistViewUrl = "https://itunes.apple.com/us/tv-show/the-colbert-report/id129458982?uo=4",
                trackViewUrl = "https://itunes.apple.com/us/tv-season/the-colbert-report-9-19-2013/id129458911?i=712018772&uo=4",
                previewUrl = "https://video-ssl.itunes.apple.com/itunes-assets/Video117/v4/10/40/bf/1040bf9b-e67a-0536-083c-39fc821d7a1f/mzvf_9101589954578638652.640x476.h264lc.U.p.m4v",
                artworkUrl30 = "https://is1-ssl.mzstatic.com/image/thumb/Features/92/40/07/dj.jkhlqpok.jpg/30x30bb.jpg",
                artworkUrl60 = "https://is1-ssl.mzstatic.com/image/thumb/Features/92/40/07/dj.jkhlqpok.jpg/60x60bb.jpg",
                artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Features/92/40/07/dj.jkhlqpok.jpg/100x100bb.jpg",
                collectionPrice = -1.00,
                trackPrice = 1.99,
                trackRentalPrice = null,
                collectionHdPrice = null,
                trackHdPrice = null,
                trackHdRentalPrice = null,
                releaseDate = "2013-09-19T07:00:00Z",
                collectionExplicitness = "notExplicit",
                trackExplicitness = "notExplicit",
                trackTimeMillis = 1302368,
                country = "USA",
                currency = "USD",
                primaryGenreName = "Comedy",
                contentAdvisoryRating = "TV-14",
                shortDescription = "Jack Johnson visits the show...",
                longDescription = "Jack Johnson visits the show and performs songs..."
            )
        )
    }

    @Test
    fun `searchMusic with non-empty query, should update musicItems`() {
        // Arrange
        val query = "Jack Johnson"
        val fakeResponse = listOf(createJackJohnsonMusicResponse())
        val expectedMusicItems = fakeResponse.map { MusicItem.parseToMusicItem(it) }

        every { repository.getMusicItems(any()) } returns flowOf(fakeResponse)

        // Act
        viewModel.searchMusic(query)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        verify { repository.getMusicItems(query) }

        // Assertion for the music list
        assertThat(viewModel.currentMusicList).isEqualTo(expectedMusicItems)
    }

    @Test
    fun `searchMusic with empty query, should use default keyword 'Jack Johnson'`() = runTest {
        // Arrange
        val defaultKeyword = "Jack Johnson"
        val defaultResponse = listOf(createJackJohnsonMusicResponse())
        val expectedMusicItems = listOf(createJackJohnsonMusicResponse()).map { MusicItem.parseToMusicItem(it) }

        every { repository.getMusicItems(defaultKeyword) } returns flowOf(defaultResponse)

        // Act
        viewModel.searchMusic("")
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        verify { repository.getMusicItems(defaultKeyword) }

        assertThat(viewModel.currentMusicList).isEqualTo(expectedMusicItems)
    }

    @Test
    fun `set new music list, should call service setMusicList`() {
        // Arrange
        val musicList = createJackJohnsonMusicList().map { MusicItem.parseToMusicItem(it) }
        every{musicPlayerService.setMusicList(musicList, false)} returns Unit

        //Act
        viewModel.setListMusic(musicList, false)

        // Arrange
        verify { musicPlayerService.setMusicList(musicList, false) }
    }

    @Test
    fun `onSelectedMusic, should seek to correct index and play`() {
        // Arrange
        val expectedPosition = 1
        val musicList = createJackJohnsonMusicList().map { MusicItem.parseToMusicItem(it) }
        viewModel.currentMusicList = musicList
        val selectedItem = musicList[expectedPosition]

        every { musicPlayerService.setToDefaultPosition(expectedPosition) } returns Unit
        every { musicPlayerService.getCurrentMediaItemIndex() } returns expectedPosition
        every { musicPlayerService.playMusic() } returns Unit

        // Act
        viewModel.onSelectedMusic(selectedItem)
        val currentIndex = viewModel.getCurrentMediaItemIndex()

        // Assert
        verify { musicPlayerService.setToDefaultPosition(expectedPosition) }
        verify { musicPlayerService.playMusic() }
        assertThat(currentIndex).isEqualTo(expectedPosition)
    }

    @Test
    fun `playPlayer, should call service playMusic`() {
        // Arrange
        every { musicPlayerService.playMusic() } returns Unit

        // Act
        viewModel.playPlayer()

        // Assert
        verify { musicPlayerService.playMusic() }
    }

    @Test
    fun `pausePlayer, should call service pauseMusic`() {
        // Arrange
        every {musicPlayerService.pauseMusic()} returns Unit

        // Act
        viewModel.pausePlayer()

        // Arrange
        verify { musicPlayerService.pauseMusic() }
    }

    @Test
    fun `skipToNextPlayer, should call service skipToNext`() {
        // Arrange
        every {musicPlayerService.skipToNext()} returns Unit

        // Act
        viewModel.skipToNextPlayer()

        // Arrange
        verify { musicPlayerService.skipToNext() }
    }

    @Test
    fun `skipToPreviousPlayer, should call service skipToPrevious`() {
        // Arrange
        every {musicPlayerService.skipToPrevious()} returns Unit

        // Act
        viewModel.skipToPreviousPlayer()

        // Arrange
        verify { musicPlayerService.skipToPrevious() }
    }

    @Test
    fun `isPlayerPlaying, should return service isPlaying`() {
        // Arrange
        val isPlaying = true
        every { musicPlayerService.isPlaying() } returns isPlaying

        // Act
        val actualIsPlaying = viewModel.isPlayerPlaying()

        // Assert
        assertThat(actualIsPlaying).isEqualTo(isPlaying)
    }

    @Test
    fun `getPlayerState, should return service getPlayerState`() {
        // Arrange
        val playerState = Player.STATE_READY
        every { musicPlayerService.getPlayerState() } returns playerState

        // Act
        val actualState = viewModel.getPlayerState()

        // Assert
        assertThat(actualState).isEqualTo(playerState)
    }

    @Test
    fun `getPlayerPosition, should return service getPlayerPosition`() {
        // Arrange
        val playerPosition = 1000L
        every { musicPlayerService.getPlayerPositionTime() } returns playerPosition

        // Act
        val actualPosition = viewModel.getPlayerPosition()

        // Assert
        assertThat(actualPosition).isEqualTo(musicPlayerService.getPlayerPositionTime())
    }

    @Test
    fun `getPlayerDuration, should return service getPlayerDuration`() {
        // Arrage
        val songDuration = 2000L
        every { musicPlayerService.getPlayerDuration() } returns songDuration

        // Act
        val actualDuration = viewModel.getPlayerDuration()

        // Assert
        assertThat(actualDuration).isEqualTo(musicPlayerService.getPlayerDuration())
    }

    // --- Tests for pure functions ---

    @Test
    fun `formatTime with positive value, should return correct MM_SS string`() {
        val formattedTime = viewModel.formatTime(95000L) // 1 minute 35 seconds
        assertThat("01:35").isEqualTo(formattedTime)
    }

    @Test
    fun `formatTime with zero, should return '00_00'`() {
        val formattedTime = viewModel.formatTime(0L)
        assertThat("00:00").isEqualTo(formattedTime)
    }

    @Test
    fun `formatTime with negative value, should return '00_00'`() {
        val formattedTime = viewModel.formatTime(-100L)
        assertThat("00:00").isEqualTo(formattedTime)
    }
}