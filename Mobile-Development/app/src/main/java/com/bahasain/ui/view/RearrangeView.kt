package com.bahasain.ui.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.dicoding.bahasain.R

class RearrangeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val words = mutableListOf<String>()
    private var onOrderChangedListener: ((List<String>) -> Unit)? = null

    init {
        orientation = HORIZONTAL
    }

    fun getUserOrder(): List<String> {
        return words
    }

    // Set the words to display in the view
    @RequiresApi(Build.VERSION_CODES.N)
    fun setWords(wordList: List<String>) {
        words.clear()
        words.addAll(wordList)
        refreshView()
    }

    // Refresh the view by adding all the word views to the layout
    @RequiresApi(Build.VERSION_CODES.N)
    private fun refreshView() {
        removeAllViews()
        words.forEach { word ->
            val wordView = createWordView(word)
            addView(wordView)
        }
    }

    // Set listener for when the order of words changes
    fun setOnOrderChangedListener(listener: (List<String>) -> Unit) {
        onOrderChangedListener = listener
    }

    // Create a TextView for each word and set up drag listeners
    @RequiresApi(Build.VERSION_CODES.N)
    private fun createWordView(word: String): TextView {
        val wordView = LayoutInflater.from(context)
            .inflate(R.layout.item_rearrange_word, this, false) as TextView
        wordView.text = word

        wordView.setOnLongClickListener {
            val shadow = DragShadowBuilder(it)
            it.startDragAndDrop(null, shadow, word, 0)
            true
        }

        wordView.setOnDragListener { _, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> true

                DragEvent.ACTION_DRAG_ENTERED -> {
                    true
                }

                DragEvent.ACTION_DRAG_EXITED -> {
                    true
                }

                DragEvent.ACTION_DROP -> {
                    handleDrop(event, wordView)
                    true
                }

                DragEvent.ACTION_DRAG_ENDED -> {
                    wordView.visibility = View.VISIBLE
                    true
                }

                else -> false
            }
        }


        return wordView
    }

    // Handle the drop event, update the word order and refresh the view
    @RequiresApi(Build.VERSION_CODES.N)
    private fun handleDrop(event: DragEvent, wordView: TextView) {
        val draggedWord = event.localState as? String
        val currentIndex = words.indexOf(wordView.text.toString())

        // If the word is dragged, update the order
        draggedWord?.let {
            words.remove(it)
            words.add(currentIndex, it) // Insert the dragged word at the correct position
        }

        // Refresh the view and notify listener about the order change
        refreshView()
        onOrderChangedListener?.invoke(words)
    }
}