package pl.kitek.rvwithcardview

import android.annotation.SuppressLint
import android.graphics.Outline
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import kotlinx.android.synthetic.main.item.view.*


class ItemsAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item)
        val elevation = view.resources.getDimension(R.dimen.cardElevation)
        ViewCompat.setElevation(view, elevation)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position, items.size)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val contentPadding: Int = dp2px(16).toInt()
        private val defaultOutline: ViewOutlineProvider? by lazy {
            if (isLollipop()) ViewOutlineProvider.BACKGROUND else null
        }
        private val fixedOutline: ViewOutlineProvider? by lazy {
            if (isLollipop()) {
                object : ViewOutlineProvider() {
                    @SuppressLint("NewApi")
                    override fun getOutline(view: View, outline: Outline) {
                        outline.setRect(
                                0,
                                view.resources.getDimensionPixelSize(R.dimen.cardElevation),
                                view.width,
                                view.height
                        )
                    }
                }
            } else null
        }

        fun bind(item: Item, position: Int, size: Int) = with(itemView) {
            bindBackground(itemView, position, size)
            bindOutlineProvider(itemView, position, size)

            avatarImage.loadImage(item.avatarUrl, CircleTransform())
            userNameText.text = item.username
            userCommentText.text = item.comment
        }

        private fun bindBackground(itemView: View, position: Int, size: Int) {
            val drawableRes: Int = when {
                size == 1 -> R.drawable.item_top_bottom
                position == 0 -> R.drawable.item_top
                position == size - 1 -> R.drawable.item_bottom
                else -> R.drawable.item_middle
            }
            itemView.background = ContextCompat
                    .getDrawable(itemView.context, drawableRes)
            itemView.setPadding(contentPadding, contentPadding,
                    contentPadding, contentPadding)
        }

        private fun bindOutlineProvider(itemView: View, position: Int, size: Int) {
            if (!isLollipop()) return
            itemView.outlineProvider = if (size == 1 || position == 0) defaultOutline else fixedOutline
        }
    }
}
