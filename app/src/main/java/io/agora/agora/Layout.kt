package io.agora.agora

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import io.agora.agora.entities.IntentManager

open class Layout : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun goToProjects(view: View) {

        IntentManager.replace(this, ProjectListActivity::class.java)
    }

    fun goToUsers(view: View) {
        IntentManager.replace(this, UserListActivity::class.java)

    }

    fun goToAccount(view: View) {
        IntentManager.replace(this, ProfileActivity::class.java)

    }

    fun onCreate(savedInstanceState: Bundle?, viewId: Int, isNeedingBackButton: Boolean = true)
    {
        super.onCreate(savedInstanceState)
        setContentView(viewId)

        val myToolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(myToolbar)
        if (isNeedingBackButton)
        {
            setSupportActionBar(myToolbar)
            val actionBar = supportActionBar
            actionBar!!.setDisplayHomeAsUpEnabled(true)
        }

    }
}
