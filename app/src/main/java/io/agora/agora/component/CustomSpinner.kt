package io.agora.agora.component

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner

object CustomSpinner {
    fun setCategories(ctx: Context, spinner: Spinner) {
        val items = arrayOf("Art", "BD", "Artisanat", "Danse", "Design", "Mode", "Cinéma et vidéo", "Gastronomie", "Jeux", "Journalisme", "Musique",
                "Photographie", "Edition", "Technologie", "Theatre")

        val adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, items)

        spinner.adapter = adapter
    }
}