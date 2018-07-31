package com.chintansoni.android.repositorypattern.util.smartrecyclerview

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class InfiniteScrollListener
/**
 * Initializes InfiniteScrollListener, which can be added
 * to RecyclerView with addOnScrollListener method
 *
 * @param maxItemsPerRequest Max items to be loaded in a single request.
 * @param layoutManager LinearLayoutManager created in the Activity.
 */
(private val maxItemsPerRequest: Int, private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    init {
        Preconditions.checkIfPositive(maxItemsPerRequest, "maxItemsPerRequest <= 0")
        Preconditions.checkNotNull(layoutManager, "layoutManager == null")
    }

    /**
     * Callback method to be invoked when the RecyclerView has been scrolled
     *
     * @param recyclerView The RecyclerView which scrolled.
     * @param dx The amount of horizontal scroll.
     * @param dy The amount of vertical scroll.
     */
    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (canLoadMoreItems()) {
            onScrolledToEnd(layoutManager.findFirstVisibleItemPosition())
        }
    }

    /**
     * Refreshes RecyclerView by setting new adapter,
     * calling invalidate method and scrolling to given position
     *
     * @param view RecyclerView to be refreshed
     * @param adapter adapter with new list of items to be loaded
     * @param position position to which RecyclerView will be scrolled
     */
    protected fun refreshView(view: RecyclerView, adapter: RecyclerView.Adapter<*>, position: Int) {
        view.adapter = adapter
        view.invalidate()
        view.scrollToPosition(position)
    }

    /**
     * Checks if more items can be loaded to the RecyclerView
     *
     * @return boolean Returns true if can load more items or false if not.
     */
    private fun canLoadMoreItems(): Boolean {
        val visibleItemsCount = layoutManager.childCount
        val totalItemsCount = layoutManager.itemCount
        val pastVisibleItemsCount = layoutManager.findFirstVisibleItemPosition()
        val lastItemShown = visibleItemsCount + pastVisibleItemsCount >= totalItemsCount
        return lastItemShown && totalItemsCount >= maxItemsPerRequest
    }

    /**
     * Callback method to be invoked when the RecyclerView has been scrolled to the end
     *
     * @param firstVisibleItemPosition Id of the first visible item on the list.
     */
    abstract fun onScrolledToEnd(firstVisibleItemPosition: Int)
}