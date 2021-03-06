package com.example.newsapp.util

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.newsapp.data.model.Article


fun Context.toast(message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ProgressBar.hide() {
    visibility = View.INVISIBLE
}

fun <T> startActivity(context: Context, article: Article?, cls: Class<T>) {
    val intent = Intent(context, cls)
    intent.putExtra(Constants.MODEL, article)
    context.startActivity(intent)
}


fun checkNull(content: String?, view: TextView) {
    if (content == null || content == "") {
        view.visibility = View.GONE
    } else {
        view.text = content
    }
}


fun <T> switchVisibility(
    list: List<T>,
    firstView: View,
    secondView: View,
    thirdView: View,
    fourthView: View
) {
    if (list.isEmpty()) {
        firstView.visibility = View.GONE
        secondView.visibility = View.VISIBLE
        thirdView.visibility = View.VISIBLE
        fourthView.visibility = View.VISIBLE
    } else {
        firstView.visibility = View.VISIBLE
        secondView.visibility = View.GONE
        thirdView.visibility = View.GONE
        fourthView.visibility = View.GONE
    }
}

