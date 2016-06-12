package tw.orthogonal.westmarket;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by zhonghaoli on 5/14/16.
 */
public class Splash extends AppCompatActivity {
     ImageView iv;
   Animation an;
    Animation an2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);



          iv=(ImageView) findViewById(R.id.imageView2);
         an= AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        // an2= AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);
      //  iv.startAnimation(an);
        startHeavyProcessing();






/*
        an.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                   //  iv.startAnimation(an2);
                   //   finish();
                Intent i=new Intent(Splash.this,Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/

    }
    private void startHeavyProcessing(){
        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //some heavy processing resulting in a Data String


                try {

                    //ui
                    Splash.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // outputView.setText(output);
                            iv.startAnimation(an);
                        }
                    });
                    Thread.sleep(2000);
                    //ui

                } catch (InterruptedException e) {
                    Thread.interrupted();
                }



            return "whatever result you have";
        }

        @Override
        protected void onPostExecute(String result) {
            Intent i = new Intent(Splash.this, Login.class);
            i.putExtra("data", result);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
