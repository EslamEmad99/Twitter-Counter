package com.halan.domain.use_case.main
//
//import com.halan.domain.model.Movie
//import com.halan.domain.model.YearMovies
//import com.halan.domain.repository.local.LocalRepository
//import kotlinx.coroutines.flow.toList
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.MockitoAnnotations
//
//class GetMoviesUseCaseTest {
//
//    @Mock
//    private lateinit var localRepository: LocalRepository
//
//    private lateinit var getMoviesUseCase: GetMoviesUseCase
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.openMocks(this)
//        getMoviesUseCase = GetMoviesUseCase(localRepository)
//    }
//
//    @Test
//    fun `test invoke without search query returns all movies grouped by year and sorted`() =
//        runTest {
//            // Given
//            val movies = listOf(
//                Movie(listOf("Actor 1"), 2021, listOf("Genre 1"), 5.0f, "Movie A"),
//                Movie(listOf("Actor 2"), 2022, listOf("Genre 2"), 4.0f, "Movie B"),
//                Movie(listOf("Actor 3"), 2021, listOf("Genre 3"), 4.5f, "Movie C"),
//                Movie(listOf("Actor 4"), 2022, listOf("Genre 4"), 3.5f, "Movie D"),
//                Movie(listOf("Actor 5"), 2021, listOf("Genre 5"), 4.2f, "Movie E")
//            )
//            Mockito.`when`(localRepository.getMovies()).thenReturn(movies)
//
//            // When
//            val result = getMoviesUseCase(null).toList()
//
//            // Then
//            val expected = listOf(
//                YearMovies(
//                    2022, listOf(
//                        Movie(listOf("Actor 2"), 2022, listOf("Genre 2"), 4.0f, "Movie B"),
//                        Movie(listOf("Actor 4"), 2022, listOf("Genre 4"), 3.5f, "Movie D")
//                    )
//                ),
//                YearMovies(
//                    2021, listOf(
//                        Movie(listOf("Actor 1"), 2021, listOf("Genre 1"), 5.0f, "Movie A"),
//                        Movie(listOf("Actor 3"), 2021, listOf("Genre 3"), 4.5f, "Movie C"),
//                        Movie(listOf("Actor 5"), 2021, listOf("Genre 5"), 4.2f, "Movie E")
//                    )
//                )
//            )
//
//            assertEquals(1, result.size)
//            assertEquals(expected, result[0])
//        }
//
//    @Test
//    fun `test invoke with search query filters and returns movies`() = runTest {
//        // Given
//        val movies = listOf(
//            Movie(listOf("Actor 1"), 2021, listOf("Genre 1"), 5.0f, "Movie A"),
//            Movie(listOf("Actor 2"), 2022, listOf("Genre 2"), 4.0f, "Movie B"),
//            Movie(listOf("Actor 3"), 2021, listOf("Genre 3"), 4.5f, "Movie C"),
//            Movie(listOf("Actor 4"), 2022, listOf("Genre 4"), 3.5f, "Movie D"),
//            Movie(listOf("Actor 5"), 2021, listOf("Genre 5"), 4.2f, "Movie E")
//        )
//        Mockito.`when`(localRepository.getMovies()).thenReturn(movies)
//
//        // When
//        val result = getMoviesUseCase("Movie A").toList()
//
//        // Then
//        val expected = listOf(
//            YearMovies(
//                2021, listOf(
//                    Movie(listOf("Actor 1"), 2021, listOf("Genre 1"), 5.0f, "Movie A")
//                )
//            )
//        )
//
//        assertEquals(1, result.size)
//        assertEquals(expected, result[0])
//    }
//}