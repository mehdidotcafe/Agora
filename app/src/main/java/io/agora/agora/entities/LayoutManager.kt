package io.agora.agora.entities

import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ListView

object LayoutManager {
    private fun getListViewHeight(list: ListView): Int {
        val adapter = list.adapter

        var height = 0

        list.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

        height = list.measuredHeight * adapter.count + adapter.count * list.dividerHeight

        return height
    }

    private fun getGridViewHeight(list: GridView): Int {
        val adapter = list.adapter

        var height = 0

        list.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

        height = list.measuredHeight * (adapter.count  / list.numColumns + (if (list.numColumns % 3 == 0) 1 else 0))

        return height
    }

    fun setGridViewHeight(view: GridView) {
        val listHeight = getGridViewHeight(view)

        val params: ViewGroup.LayoutParams  = view.layoutParams

        params.height = listHeight
        view.layoutParams = params
        view.requestLayout()
    }

    fun setListViewHeight(view: ListView) {
        val listHeight = getListViewHeight(view)

        val params: ViewGroup.LayoutParams  = view.layoutParams

        params.height = listHeight
        view.layoutParams = params
        view.requestLayout()
    }

}