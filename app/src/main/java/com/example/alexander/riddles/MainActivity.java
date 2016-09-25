package com.example.alexander.riddles;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    //Необходимые глобальные переменные
    //Массивы с вопросами и ответами
    //Количество загадок и номер текущей загадки
    String questions[];
    String answers[];
    int n;
    int current = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Заполняем переменные
        Resources res = getResources();
        questions = res.getStringArray(R.array.question);
        answers = res.getStringArray(R.array.answers);
        n = questions.length - 1;

        //добавляем первую загадку на экран
        TextView main_text_textview = (TextView) findViewById(R.id.main_text_textview);
        main_text_textview.setText(questions[current]);
    }

    //Обработчик кнопки "следущая"
    public void nextOne (View view) {

        TextView answer_textview = (TextView) findViewById(R.id.show_answer_textview);
        if (current < n) {
            current++;
            //Выводим следущую загадку и прячем поле со статусом ответа
            TextView main_text_textview = (TextView) findViewById(R.id.main_text_textview);
            main_text_textview.setText(questions[current]);
            answer_textview.setVisibility(View.GONE);

            //Очищаем поле с введенным ответом
            EditText answer_edittext = (EditText) findViewById(R.id.answer_edittext);
            answer_edittext.setText("");
        } else {
            //Выводим сообщение об последней загадке
            answer_textview.setVisibility(View.VISIBLE);
            answer_textview.setTextColor(Color.parseColor("#000000"));
            answer_textview.setText(getString(R.string.last_question));
        }


    }

    //Обработчик кнопки "Показать ответ"
    public void showAnswer (View view) {
        TextView show_answer_textview = (TextView) findViewById(R.id.show_answer_textview);
        show_answer_textview.setVisibility(View.VISIBLE);
        show_answer_textview.setText(answers[current]);
        show_answer_textview.setTextColor(Color.parseColor("#000000"));

    }


    //Обработчик кнопки "отправить"
    public void  answerSent (View view) {
        TextView show_answer_textview = (TextView) findViewById(R.id.show_answer_textview);
        show_answer_textview.setVisibility(View.VISIBLE);


        EditText answer_edittext = (EditText) findViewById(R.id.answer_edittext);

        String answer = answer_edittext.getText().toString();
        String rightAnswer = answers[current];

        if (answer.equals(rightAnswer)) {
            //Выводим сообщение о правильности ответа и делаем его текст зеленым
            show_answer_textview.setText(getString(R.string.right_answer));
            show_answer_textview.setTextColor(Color.parseColor("#66BB6A"));
        } else {
            //Выводим сообщение о неправильности ответа и делаем текст красным
            show_answer_textview.setText(getString(R.string.wrong_answer));
            show_answer_textview.setTextColor(Color.parseColor("#EF5350"));
        }

    }

    //Обработчик кнопки "отправить другу"
    public void sendEmail (View view) {
        CheckBox withAnswerCheckbox = (CheckBox) findViewById(R.id.with_answer_checkbox);
        String message;
        if (withAnswerCheckbox.isChecked()) {
            message = getString(R.string.email_body_with_answer, questions[current], answers[current]);
        } else {
            message = getString(R.string.email_body_without_answer, questions[current]);
        }

        final Intent intent = new Intent (Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);

        }

    }

}
