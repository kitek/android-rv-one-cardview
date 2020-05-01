package pl.kitek.rvwithcardview

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ItemDecoration
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemsRv.layoutManager = LinearLayoutManager(this)
        itemsRv.adapter = ItemsAdapter((1..10).map { createItem(it) })

        //https://stackoverflow.com/questions/31242812/how-can-a-divider-line-be-added-in-an-android-recyclerview
        //https://stackoverflow.com/questions/31242812/how-can-a-divider-line-be-added-in-an-android-recyclerview
        val dividerItemDecoration = DividerItemDecorator(ContextCompat.getDrawable(this, R.drawable.divider))
        itemsRv.addItemDecoration(dividerItemDecoration)
    }

    private fun createItem(position: Int): Item {
        return Item(
                "https://picsum.photos/300/300/?image=$position",
                "Item $position",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
        )
    }


    // Source: https://stackoverflow.com/a/49722959/2848021
    class DividerItemDecorator(private val mDivider: Drawable) : ItemDecoration() {
        private val bounds: Rect = Rect()

        override fun onDraw(canvas: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
            if (canvas == null || parent == null) return
            canvas.save()
            val left: Int
            val right: Int
            if (parent.clipToPadding) {
                left = parent.paddingLeft
                right = parent.width - parent.paddingRight
                canvas.clipRect(left, parent.paddingTop, right,
                        parent.height - parent.paddingBottom)
            } else {
                left = 0
                right = parent.width
            }

            val childCount = parent.childCount
            for (i in 0 until childCount - 1) {
                val child: View = parent.getChildAt(i)
                parent.getDecoratedBoundsWithMargins(child, bounds)
                val bottom = (bounds.bottom + child.translationY.roundToInt())
                val top = bottom - mDivider.intrinsicHeight
                mDivider.setBounds(left, top, right, bottom)
                mDivider.draw(canvas)
            }
            canvas.restore()
        }

        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            if (parent == null || outRect == null || state == null) return
            if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
                outRect.setEmpty();
            } else {
                outRect.set(0, 0, 0, mDivider.intrinsicHeight)
            }
        }
    }
}
