package com.example.hellokittyquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    //Buttons declared
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var cheatButton: Button
    private lateinit var mcButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var trueFalseLayout: LinearLayout
    private lateinit var mcRecyclerView: RecyclerView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.index = currentIndex

        //Buttons vars assigned
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        cheatButton = findViewById(R.id.cheat_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        mcButton = findViewById(R.id.mc_button)
        //Assign question TextView
        questionTextView = findViewById(R.id.question_text_view)
        trueFalseLayout = findViewById(R.id.true_false_layout)
        mcRecyclerView = findViewById(R.id.mc_recycler_view)
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
        //During a state change, check if current question is answerable and, if not, disable answer buttons
        if(quizViewModel.questionBank[quizViewModel.index].answered){
            changeButtonState(trueButton, falseButton, false)
        }

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

        cheatButton.setOnClickListener {
            //Start Cheat Activity
            val answerIsTrue = quizViewModel.questionBank[quizViewModel.index].questAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
        mcButton.setOnClickListener {
          trueFalseLayout.visibility = View.GONE
            mcRecyclerView.visibility = View.VISIBLE
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK){
            return
        }

        if(requestCode == REQUEST_CODE_CHEAT){
            quizViewModel.questionBank[quizViewModel.index].hasCheated=
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
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
        //Create a reference to the current question to ease readability and compactness
        val currentQuest: Question = quizViewModel.questionBank[quizViewModel.index]
        //Change answered state to prevent repeat answers
        currentQuest.answered = true
        val messageResId = when{
            currentQuest.hasCheated -> R.string.judgement_toast
            answer == currentQuest.questAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        if(messageResId == R.string.correct_toast) quizViewModel.score++
        quizViewModel.totalAnswered++
        val toast = Toast.makeText(this, messageResId, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()

        //Presents a score if all questions are answered
        /* Known "error": Does override previous correctness Toast*/
        if (quizViewModel.totalAnswered == quizViewModel.questionBank.size) {
            val scoreToast = Toast.makeText(
                this,
                "You scored " + quizViewModel.score * 100.0 / quizViewModel.questionBank.size + "%",
                Toast.LENGTH_LONG
            )
            scoreToast.setGravity(Gravity.TOP, 0, 0)
            scoreToast.show()
        }
    }

    /***
     * Change the clickable state of the answer buttons
     */
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