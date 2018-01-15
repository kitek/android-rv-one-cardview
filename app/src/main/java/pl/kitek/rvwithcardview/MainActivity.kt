package pl.kitek.rvwithcardview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemsRv.layoutManager = LinearLayoutManager(this)
        itemsRv.adapter = ItemsAdapter((1..10).map { createItem(it) })
    }

    private fun createItem(position: Int): Item {
        return Item(
                "https://picsum.photos/300/300/?image=$position",
                "Item $position",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
        )
    }
}
