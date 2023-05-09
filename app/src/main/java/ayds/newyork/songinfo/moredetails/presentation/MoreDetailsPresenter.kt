package ayds.newyork.songinfo.moredetails.presentation

interface MoreDetailsPresenter {
    fun onViewAttached(view: MoreDetailsView)
    fun onViewDetached()
}

class MoreDetailsPresenterImpl: MoreDetailsPresenter {
    private var view: MoreDetailsView? = null

    override fun onViewAttached(view: MoreDetailsView) {
        this.view = view
    }

    override fun onViewDetached() {
        this.view = null
    }
}