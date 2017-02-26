package com.helluva.telephone_pictionary_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//<<<<<<< Updated upstream
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.agsw.FabricView.FabricView;
//=======
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//>>>>>>> Stashed changes

public class SketchActivity extends AppCompatActivity {


    private Spinner colorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        String description = getIntent().getStringExtra("text_description");

        TextView textDescription = (TextView) this.findViewById(R.id.description_text_view);
        textDescription.setText(description);

        Button nextButton = (Button) this.findViewById(R.id.next_button_sketch);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(SketchActivity.this, TextWithSketchActivity.class);
                SketchActivity.this.startActivity(i);
            }
        });
/*
        Button redButton = (Button) this.findViewById(R.id.red_button);
        redButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        redButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FabricView canvas = (FabricView) findViewById(R.id.faricView);
                canvas.setColor(Color.RED);
            }

        });
*/
        Spinner colorSpinner = (Spinner) findViewById(R.id.color_spinner);

        //for the new spinner
        ArrayAdapter<Colors> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Colors.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);



        /* colorSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Colors newColor = Colors.values()[position];
                FabricView canvas = (FabricView) findViewById(R.id.faricView);
                canvas.setColor(newColor.getColor());
                //colorSpinner.setSelection(canvas.setColor(newColor);
            }
        }); */
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
