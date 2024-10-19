// TeacherCreateQuiz.kt
package com.example.erp_system_

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TeacherCreateQuiz : AppCompatActivity() {
    private lateinit var quizTitleEditText: EditText
    private lateinit var numberOfQuestionsEditText: EditText
    private lateinit var marksPerQuestionEditText: EditText
    private lateinit var questionEditText: EditText
    private lateinit var optionAEditText: EditText
    private lateinit var optionBEditText: EditText
    private lateinit var optionCEditText: EditText
    private lateinit var optionDEditText: EditText
    private lateinit var correctOptionEditText: EditText
    private lateinit var nextButton: Button
    private lateinit var database: DatabaseReference
    private var questionCounter = 0
    private var quizTitle = ""
    private var quizId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_create_quiz)

        quizTitleEditText = findViewById(R.id.quizTitleEditText)
        numberOfQuestionsEditText = findViewById(R.id.numberOfQuestionsEditText)
        marksPerQuestionEditText = findViewById(R.id.marksPerQuestionEditText)
        questionEditText = findViewById(R.id.questionEditText)
        optionAEditText = findViewById(R.id.optionAEditText)
        optionBEditText = findViewById(R.id.optionBEditText)
        optionCEditText = findViewById(R.id.optionCEditText)
        optionDEditText = findViewById(R.id.optionDEditText)
        correctOptionEditText = findViewById(R.id.correctOptionEditText)
        nextButton = findViewById(R.id.nextButton)

        database = FirebaseDatabase.getInstance().reference

        nextButton.setOnClickListener {
            val numberOfQuestions = numberOfQuestionsEditText.text.toString().toIntOrNull()
            val marksPerQuestion = marksPerQuestionEditText.text.toString().toIntOrNull()

            if (numberOfQuestions == null || marksPerQuestion == null) {
                Toast.makeText(this, "Ingrese valores válidos para el número de preguntas y puntos por pregunta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (quizTitle.isEmpty()) {
                quizTitle = quizTitleEditText.text.toString().trim()

                if (quizTitle.isEmpty()) {
                    quizTitleEditText.error = "Por favor ingrese el título del cuestionario"
                    return@setOnClickListener
                }

                quizId = database.child("quizzes").push().key ?: ""
            }

            val question = questionEditText.text.toString().trim()
            val optionA = optionAEditText.text.toString().trim()
            val optionB = optionBEditText.text.toString().trim()
            val optionC = optionCEditText.text.toString().trim()
            val optionD = optionDEditText.text.toString().trim()
            val correctOption = correctOptionEditText.text.toString().trim()

            if (question.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty() || correctOption.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            questionCounter++

            val questionData = Question(question, optionA, optionB, optionC, optionD, correctOption, marksPerQuestion)

            if (quizId.isNotEmpty()) {
                val questionKey = database.child("quizzes").child(quizId).child("questions").push().key
                if (questionKey != null) {
                    database.child("quizzes").child(quizId).child("questions").child(questionKey).setValue(questionData)
                        .addOnSuccessListener {
                            questionEditText.text.clear()
                            optionAEditText.text.clear()
                            optionBEditText.text.clear()
                            optionCEditText.text.clear()
                            optionDEditText.text.clear()
                            correctOptionEditText.text.clear()

                            if (questionCounter >= numberOfQuestions) {
                                questionCounter = 0
                                quizTitle = ""
                                quizId = ""
                                Toast.makeText(this, "Creación de cuestionario completa", Toast.LENGTH_SHORT).show()

                                numberOfQuestionsEditText.text.clear()
                                marksPerQuestionEditText.text.clear()
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "No se pudo guardar la pregunta: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }
}
