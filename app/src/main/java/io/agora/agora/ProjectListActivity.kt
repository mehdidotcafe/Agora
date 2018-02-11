package io.agora.agora

import android.os.Bundle
import android.widget.*
import io.agora.agora.entities.ProjectArrayAdapter



class ProjectListActivity : Layout() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_project_list, true)

        val projects = arrayOf("Android", "PHP", "Android Studio", "PhpMyAdmin", "Android", "PHP", "Android Studio", "PhpMyAdmin")

        val adapter = ProjectArrayAdapter(this,
                R.layout.project_item, projects)

        val gridView = findViewById<GridView>(R.id.project_list)

        gridView.adapter = adapter

    }
}
