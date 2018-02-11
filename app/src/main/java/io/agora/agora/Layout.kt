package io.agora.agora

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.EditText

open class Layout : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun onCreate(savedInstanceState: Bundle?, viewId: Int, isNeedingBackButton: Boolean = true)
    {
        super.onCreate(savedInstanceState)
        setContentView(viewId)

        val myToolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(myToolbar)
        if (isNeedingBackButton == true)
        {
            setSupportActionBar(myToolbar)
            val actionBar = supportActionBar
            actionBar!!.setDisplayHomeAsUpEnabled(true)
        }

    }
}
