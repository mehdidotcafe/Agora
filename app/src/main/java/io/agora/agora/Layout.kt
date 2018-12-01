package io.agora.agora

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import io.agora.agora.entities.IntentManager

open class Layout : AppCompatActivity() {

    companion object {
        var currentInstance: Class<*> = MyProjectsActivity::class.java
    }


    fun getInputString(input: Int): String
    {
        val editText: EditText = findViewById(input)

        return editText.text.toString()
    }

    private fun goToIfNotCurrent(packageContext: Context, cls: Class<*>) {
        if (cls != currentInstance) {
            currentInstance = cls
            IntentManager.goTo(packageContext, cls)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun goToMyProjects(view: View) {
        goToIfNotCurrent(this, MyProjectsActivity::class.java)
    }

    fun goToSearchUsers(view: View) {
        goToIfNotCurrent(this, UserSearchActivity::class.java)
    }

    fun goToSearchProjects(view: View) {
        goToIfNotCurrent(this, ProjectSearchActivity::class.java)
    }

    fun goToConfiguration(view: View) {
        goToIfNotCurrent(this, ConfigurationActivity::class.java)

    }

    fun onCreate(savedInstanceState: Bundle?, viewId: Int, isNeedingBackButton: Boolean = true)
    {
        super.onCreate(savedInstanceState)
        setContentView(viewId)
    }
}
