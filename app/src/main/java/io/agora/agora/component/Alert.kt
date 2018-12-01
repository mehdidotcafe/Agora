package io.agora.agora.component

import android.app.AlertDialog
import android.content.Context
import io.agora.agora.R
import io.agora.agora.entities.Callback
import android.widget.LinearLayout
import android.widget.EditText
import android.text.Editable
import android.text.TextWatcher

object Alert {
    fun yesNo(context: Context, title: String, message: String, cb: Callback) {
        val dialog = AlertDialog.Builder(context)

        dialog.setTitle(title)
                .setMessage(message)
                .setNegativeButton(context.resources.getString(R.string.no)) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    cb.fail("error")
                }
                .setPositiveButton(context.resources.getString(R.string.yes)) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    cb.success(null)
                }.show()
    }

    fun input(context: Context, title: String, message: String, cb: Callback) {
        var value = ""
        val dialog = AlertDialog.Builder(context)

        dialog.setTitle(title)
                .setMessage(message)
                .setNegativeButton(context.getString(R.string.cancel)) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    cb.fail("error")
                }
                .setPositiveButton(context.getString(R.string.send)) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    cb.success(value)
                }

        val input = EditText(context)
        val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)

        lp.setMargins(100, 0, 100, 0)
        input.layoutParams = lp

        input.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                value = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })
        dialog.setView(input).show()
    }

    fun ok(context: Context, title: String, message: List<String>, cb: Callback) {
        val dialog = AlertDialog.Builder(context)

        dialog.setTitle(title)

        for (i in message.indices) {
            dialog.setMessage(message[i])
        }
        dialog.setNeutralButton(context.resources.getString(R.string.ok)) { dialogInterface, _ ->
            dialogInterface.cancel()
            cb.success(null)
        }.show()
    }


    fun ok(context: Context, title: String, message: String, cb: Callback) {
        val dialog = AlertDialog.Builder(context)

        dialog.setTitle(title)
                .setMessage(message)
                .setNeutralButton(context.resources.getString(R.string.ok)) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    cb.success(null)
                }.show()
    }
}
