/*
© Budkov Sergey, Maksakova Maria, Markovnikov Nikita 2014
All Rights Reserved
 */
package translator.BudkovMaksakovaMarkovnikov.ru.translator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class InputActivity extends Activity {

    EditText editText1;
    String word;
    private static final int COUNT = 10;
    static String ruWord;
    static Drawable[] imagePack = new Drawable[COUNT];
    ImageButton imageButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_layout);
        editText1 = (EditText) findViewById(R.id.editText);
        findViewById(R.id.my);
        imageButton1 = (ImageButton) findViewById(R.id.imageButton);
    }

    public void start_translate(View v) {
        if (editText1.getText().length() == 0) {
            Toast.makeText(this, getString(R.string.INVITATION),
                    Toast.LENGTH_LONG).show();
            return;
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(imageButton1.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        word = editText1.getText().toString().trim();
        Intent intent = new Intent(this, TranslationActivity.class);
        intent.putExtra("Word", word);
        startActivity(intent);
    }

}
