package com.example.la2

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
// Явный импорт вашего класса R, чтобы не было ошибок "Unresolved reference"
import com.example.la2.R

class MainActivity : AppCompatActivity() {

    // Константа для сохранения состояния (исправлено именование согласно советам студии)
    private val stateIndexKey = "current_artwork_index"
    private var currentIndex = 0

    // Список картин. Убедитесь, что файлы art1, art2, art3 лежат в res/drawable
    private val artworks = listOf(
        Artwork(R.drawable.art1, R.string.title1, R.string.author1, R.string.year1, R.string.artwork_image_desc),
        Artwork(R.drawable.art2, R.string.title2, R.string.author2, R.string.year2, R.string.artwork_image_desc),
        Artwork(R.drawable.art3, R.string.title3, R.string.author3, R.string.year3, R.string.artwork_image_desc)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Восстановление индекса при повороте экрана
        currentIndex = savedInstanceState?.getInt(stateIndexKey, 0) ?: 0

        // Инициализация интерфейса
        updateUI()

        // Кнопка "Назад"
        findViewById<Button>(R.id.prev_button).setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateUI()
            }
        }

        // Кнопка "Вперед"
        findViewById<Button>(R.id.next_button).setOnClickListener {
            if (currentIndex < artworks.size - 1) {
                currentIndex++
                updateUI()
            }
        }
    }

    // Сохранение текущего индекса перед уничтожением активности (например, при повороте)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(stateIndexKey, currentIndex)
    }

    /**
     * Метод для обновления всех элементов экрана на основе currentIndex
     */
    private fun updateUI() {
        val current = artworks[currentIndex]

        // Обновление картинки
        findViewById<ImageView>(R.id.image_view).apply {
            setImageResource(current.imageResId)
            // Установка описания для людей с ограниченными возможностями (Accessibility)
            contentDescription = getString(current.accessibilityDescId)
        }

        // Обновление заголовка (название картины)
        findViewById<TextView>(R.id.title_text).setText(current.titleResId)

        // Обновление подписи (Автор и Год)
        val authorYearString = "${getString(current.authorResId)} (${getString(current.yearResId)})"
        findViewById<TextView>(R.id.author_year_text).text = authorYearString

        // Управление доступностью кнопок (чтобы нельзя было выйти за границы списка)
        findViewById<Button>(R.id.prev_button).isEnabled = currentIndex > 0
        findViewById<Button>(R.id.next_button).isEnabled = currentIndex < artworks.size - 1
    }
}