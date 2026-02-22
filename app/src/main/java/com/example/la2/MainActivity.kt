package com.example.la2

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    private val stateIndexKey = "current_artwork_index"
    private var currentIndex = 0


    private val artworks = listOf(
        Artwork(R.drawable.art1, R.string.title1, R.string.author1, R.string.year1, R.string.artwork_image_desc),
        Artwork(R.drawable.art2, R.string.title2, R.string.author2, R.string.year2, R.string.artwork_image_desc),
        Artwork(R.drawable.art3, R.string.title3, R.string.author3, R.string.year3, R.string.artwork_image_desc)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        currentIndex = savedInstanceState?.getInt(stateIndexKey, 0) ?: 0


        updateUI()


        findViewById<Button>(R.id.prev_button).setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateUI()
            }
        }


        findViewById<Button>(R.id.next_button).setOnClickListener {
            if (currentIndex < artworks.size - 1) {
                currentIndex++
                updateUI()
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(stateIndexKey, currentIndex)
    }


    private fun updateUI() {
        val current = artworks[currentIndex]


        findViewById<ImageView>(R.id.image_view).apply {
            setImageResource(current.imageResId)

            contentDescription = getString(current.accessibilityDescId)
        }


        findViewById<TextView>(R.id.title_text).setText(current.titleResId)


        val authorYearString = "${getString(current.authorResId)} (${getString(current.yearResId)})"
        findViewById<TextView>(R.id.author_year_text).text = authorYearString


        findViewById<Button>(R.id.prev_button).isEnabled = currentIndex > 0
        findViewById<Button>(R.id.next_button).isEnabled = currentIndex < artworks.size - 1
    }
}