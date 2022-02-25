package com.example.hellokittyquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

const val EXTRA_ANSWER_SHOWN = "com.example.hellokittyquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE =
    "com.example.hellokittyquiz.answer_is_true"
private const val TAG = "CheatActivity"
private const val KEY_CHEATED = "cheated"

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    private var answerIsTrue = false

    private val cheatViewModel: CheatViewModel by lazy {
        ViewModelProviders.of(this).get(CheatViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        val answerShown = savedInstanceState?.getBoolean(KEY_CHEATED) ?: false
        cheatViewModel.answerShown = answerShown

        if(cheatViewModel.answerShown){
            presentAnswer()
        }

        showAnswerButton.setOnClickListener {
            presentAnswer()
            cheatViewModel.answerShown = true
        }
    }

    private fun presentAnswer(){
        val answerText = when{
            answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }
        answerTextView.setText(answerText)
        setAnswerShownResult(true)
        cheatViewModel.answerShown = true
        showAnswerButton.isClickable = false
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean)
    {
        val data = Intent().apply{
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putBoolean(KEY_CHEATED, cheatViewModel.answerShown)
    }

    companion object{
        fun newIntent(packageContext: Context, answerIsTrue:Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply{
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}