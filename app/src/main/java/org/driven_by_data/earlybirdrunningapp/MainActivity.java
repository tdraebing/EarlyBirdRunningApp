package org.driven_by_data.earlybirdrunningapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String[] PARAGRAPHS = {
            "You are an early riser like us?",
            "You like to start your day with some sports before work?",
            "You live in the Heidelberg area?",
            "Why not join us! We meet regularly at 6:30 AM for a short wake up run. Just drop us a short note!"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout textContainer = (LinearLayout) findViewById(R.id.textContainer);
        for (int i = 0; i < PARAGRAPHS.length; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0,10,0,10);
            TextView tx = new TextView(this);
            tx.setText(PARAGRAPHS[i]);
            tx.setTextColor(Color.WHITE);
            tx.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tx.setLayoutParams(layoutParams);
            textContainer.addView(tx);
        }

        Button startBtn = (Button) findViewById(R.id.sendEmail);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });

        new LoadDrawable().execute();
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String TO = "earlybirdrunning@example.com";
        String SUBJECT = "Joining the early run";
        String BODY = "Hi there, I want to join your running group.";

        Uri uri = Uri.parse("mailto:" + TO +
                "?subject=" + Uri.encode(SUBJECT) +
                "&body=" + Uri.encode(BODY));

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.putExtra("exit_on_sent", true);
        emailIntent.setData(uri);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private class LoadDrawable extends AsyncTask<Drawable, Void, Drawable> {
        @Override
        protected Drawable doInBackground(Drawable... params) {
            return ResourcesCompat.getDrawable(getResources(),
                    R.drawable.runners, null);
        }

        @Override
        protected void onPostExecute(Drawable loaded) {
            ImageView image = (ImageView) findViewById(R.id.header_image);
            image.setImageDrawable(loaded);
        }
    }

}
