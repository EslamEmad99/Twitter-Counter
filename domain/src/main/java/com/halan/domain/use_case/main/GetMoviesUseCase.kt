package com.halan.domain.use_case.main
//
//import com.halan.domain.model.YearMovies
//import com.halan.domain.repository.local.LocalRepository
//import com.halan.domain.use_case.util.Mockable
//import kotlinx.coroutines.flow.flow
//import javax.inject.Inject
//
///**
// * A use case represents a single action or task that the application can perform.
// * It encapsulates the business logic and rules necessary to execute a specific operation
// * in the application, such as retrieving data from a repository, processing it, and returning results.
// * Use cases promote a clean separation of concerns by keeping the domain logic separate from
// * the presentation layer, making the codebase more modular, reusable, and testable.
// * **/
//@Mockable
//class GetMoviesUseCase @Inject constructor(
//    private val localRepository: LocalRepository
//) {
//
//    suspend operator fun invoke(searchQuery: String?) = flow {
//        val movies = localRepository.getMovies() // Retrieve movies from local repository
//        val filteredMovies = if (searchQuery.isNullOrBlank()) {
//            movies
//        } else {
//            movies.filter { it.title.contains(searchQuery, ignoreCase = true) }
//        } // Filter movies based on search query
//          // If search query is empty, return all movies
//
//        val groupedMovies = filteredMovies.groupBy { it.year } // Group movies by year
//        val yearGroups = groupedMovies.map { entry ->
//            val topMovies = entry.value.sortedByDescending { it.rating }.take(5) // Get top 5 movies for each year and sort by rating in descending order
//            YearMovies(entry.key, topMovies)
//        }.sortedByDescending { it.year } // Sort year groups by year in descending order
//        emit(yearGroups) // Emit the year groups
//    }
//}