/*
© Budkov Sergey, Maksakova Maria, Markovnikov Nikita 2014
All Rights Reserved
 */
package translator.BudkovMaksakovaMarkovnikov.ru.translator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;


public class DrawItActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (InputActivity.imagePack == null || InputActivity.ruWord == null) {
            finish();
            Toast.makeText(DrawItActivity.this,
                    getString(R.string.FAIL_MSG), Toast.LENGTH_SHORT).show();
            return;
        }
        setContentView(R.layout.draw_layout);
        final Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(this, InputActivity.imagePack));
        final TextView label = (TextView) findViewById(R.id.textView1);
        String translation = "Translation: " + InputActivity.ruWord;
        label.setText(translation);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(DrawItActivity.this,
                        getString(R.string.CLICK_ON_IMAGE), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void authors(View v) {
        Toast.makeText(DrawItActivity.this,
                getString(R.string.AUTHORS), Toast.LENGTH_SHORT).show();
        finish();
    }



}