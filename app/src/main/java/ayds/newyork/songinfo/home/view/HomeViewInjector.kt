package ayds.newyork.songinfo.home.view

import ayds.newyork.songinfo.home.controller.HomeControllerInjector
import ayds.newyork.songinfo.home.model.HomeModelInjector

object HomeViewInjector {

    private val songReleaseDateStrategyFactory: SpotifySongReleaseDateStrategyFactory = SpotifySongReleaseDateStrategyFactory()
    val songDescriptionHelper: SongDescriptionHelper = SongDescriptionHelperImpl(songReleaseDateStrategyFactory)

    fun init(homeView: HomeView) {
        HomeModelInjector.initHomeModel(homeView)
        HomeControllerInjector.onViewStarted(homeView)
    }
}