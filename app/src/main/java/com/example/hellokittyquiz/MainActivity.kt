/***
 * MCS 270
 * Hello Kitty Quiz
 * Now linked to Github :)
 *
 * @author Austin Legatt
 */
package com.example.hellokittyquiz

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView
    private var score: Int = 0
    private var totalAnswered: Int = 0

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.index = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)
        /* Treat question text as a "next" button */
        questionTextView.setOnClickListener{
            quizViewModel.index = (quizViewModel.index+1) % quizViewModel.questionBank.size
            updateQuestion()
        }
        /* True button listener to check answer */
        trueButton.setOnClickListener {
            //Do something on true button click
            toastCheck(true)
            changeButtonState(trueButton, falseButton, false)
        }
        /* False button listener to check user answer */
        falseButton.setOnClickListener {
            //Do something on false button click
            toastCheck(false)
            changeButtonState(trueButton, falseButton, false)
        }
        //Initializes the first question text
        updateQuestion()
        //Operating next button
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            if(!quizViewModel.questionBank[quizViewModel.index].answered){
                changeButtonState(trueButton, falseButton, true)
            }
        }
        //Operating previous button
        previousButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            updateQuestion()
            if(!quizViewModel.questionBank[quizViewModel.index].answered){
                changeButtonState(trueButton, falseButton, true)
            }
        }

    }


    /***
     * Updates the currently visible question text by replacing the current question with one at the current index value
     * in list of questions
     */
    private fun updateQuestion(){
        val questId = quizViewModel.currentQuestionText
        questionTextView.setText(questId)
    }

    /***
     * Checks whether the answer given by a user is correct based off the stored value in the
     * list of questions. Then presents the appropriate toast based on correctness.
     *
     * @param answer Boolean of user input for true/false answers
     */
    private fun toastCheck(answer:Boolean) {
        //First if-else presents toast based on user answer correctness
        if (quizViewModel.questionBank[quizViewModel.index].questAnswer == answer) {
            quizViewModel.questionBank[quizViewModel.index].answered = true
            val correctToast = Toast.makeText(
                this,
                R.string.correct_toast,
                Toast.LENGTH_LONG
            )//.setGravity(Gravity.TOP,0,0)
            correctToast.setGravity(Gravity.TOP, 0, 0)
            correctToast.show()
            score++
            totalAnswered++
        } else {
            quizViewModel.questionBank[quizViewModel.index].answered = true
            val falseToast = Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_LONG)
            falseToast.setGravity(Gravity.TOP, 0, 0)
            falseToast.show()
            totalAnswered++
        }
        //Second 'if' presents a score if all questions are answered
        if (totalAnswered == quizViewModel.questionBank.size) {
            val scoreToast = Toast.makeText(
                this,
                "You scored " + score * 100.0 / quizViewModel.questionBank.size + "%",
                Toast.LENGTH_LONG
            )
            scoreToast.setGravity(Gravity.TOP, 0, 0)
            scoreToast.show()
        }
    }
    private fun changeButtonState(trueBut:Button, falseBut: Button, state: Boolean){
        trueBut.isClickable = state
        falseBut.isClickable = state
    }

    override fun onStart(){
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume(){
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause(){
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.index)
    }
    override fun onStop(){
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}