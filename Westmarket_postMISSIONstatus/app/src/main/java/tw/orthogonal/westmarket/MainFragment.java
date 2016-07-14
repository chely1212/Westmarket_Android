package tw.orthogonal.westmarket;

/**
 * Created by zhonghaoli on 16/5/13.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import com.loopj.android.http.*;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    String jsonString;
    JSONArray jsonArray = null;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private ProgressDialog progress;
    private RoundCornerProgressBar progressTwo;
    private TextRoundCornerProgressBar progressThree;
    String hash;
    String name;
    private TextView tvProgressTwo;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View V = inflater.inflate(R.layout.fragment_unfinishedtask, container, false);
        // Inflate the layout for this fragment
       // final TextView outputView =(TextView )V.findViewById(R.id.showOutput);
        final Button button = (Button)V.findViewById(R.id.main_button);
        final Button inbtn = (Button)V.findViewById(R.id.button_progress_two_increase);
        final Button decbtn = (Button)V.findViewById(R.id.button_progress_two_decrease);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                //Toast.makeText(this, "Please enter name in correct format.", Toast.LENGTH_SHORT);
                //button.setText("Hello");
                Toast.makeText(getActivity(), "手機已搖動", Toast.LENGTH_SHORT).show();

            }

        });


        //AsynHTTp



        //toast on UI thread
       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // button.setText("Hello");
                new PostClass(MainFragment.this.getActivity()).execute();
            }
        });


        inbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.button_progress_two_decrease) {
                    decreaseProgressTwo();
                } else if (id == R.id.button_progress_two_increase) {
                    increaseProgressTwo();
                }

            }
        });

        decbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.button_progress_two_decrease) {
                    decreaseProgressTwo();
                } else if (id == R.id.button_progress_two_increase) {
                    increaseProgressTwo();
                }

            }
        });
        //toast on UI thread


        //progressbar
        progressTwo = (RoundCornerProgressBar)V.findViewById(R.id.progress_two);
        progressTwo.setProgressBackgroundColor(getResources().getColor(R.color.custom_progress_background));
        updateProgressTwoColor();



        tvProgressTwo = (TextView)V.findViewById(R.id.tv_progress_two);
        updateTextProgressTwo();

        //progressbar


        //asy

        //asy

        return V;




    }


    //http class
    private class PostClass extends AsyncTask<String, Void, Void> {

        private Context context;

        public PostClass(Context c){

            this.context = c;
//            this.error = status;
//            this.type = t;
        }

        protected void onPreExecute(){
            progress= new ProgressDialog(this.context);
            progress.setMessage("Loading");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                final TextView outputView = (TextView)getActivity().findViewById(R.id.showOutput);
                URL url = new URL("http://140.116.39.153:1750/missionstate");

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                String urlParameters = "username="+"snoopy"+"&missionid="+"0"+"&missionstate="+"1";
              //  String urlParameters = "username="+"snoopy";
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters);
                dStream.flush();
                dStream.close();

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                while((line = br.readLine()) != null ) {
                    responseOutput.append(line);
                }
                br.close();
               // final StringBuilder output = new StringBuilder(responseOutput.toString());

                jsonString = responseOutput.toString();
                //read json

                //final StringBuilder output=new StringBuilder(jsonString);
                //
               // JSONArray jsonArray = new JSONArray(yourStringData);

                //

                try {
                   // jsonArray = new JSONArray(jsonString);


                    ////////////////讀取任務進度陣列/////////////////////////////
                    jsonArray = new JSONArray(jsonString);
                    String[] resultingArray = jsonArray.join(",").split(",");
                    name=resultingArray[0];
                    ////////////////讀取任務進度陣列/////////////////////////////

                } catch (JSONException e) {
                    e.printStackTrace();
                }

              //  final StringBuilder output=new StringBuilder(jsonArray.getString(0).toString(););
                //read json

                MainFragment.this.getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        outputView.setText(jsonString);
                        progress.dismiss();
                    }
                });


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }


        public void sendPostRequest(View View) {
            //new PostClass(MainFragment.this.getActivity()).execute();
            // Intent getNameScreenIntent=new Intent(this,MainDrawer.class);
            //final int result=1;
            //startActivity(getNameScreenIntent);
            //System.out.println("ssss");

            Toast.makeText(getActivity(), "手機已搖動", Toast.LENGTH_SHORT).show();
        }

        protected void onPostExecute() {
            progress.dismiss();
        }

    }
    //http class


    // http post

    public void toast(View v)
    {
      //  Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();

        int id = v.getId();
        if (id == R.id.button_progress_two_decrease) {
            decreaseProgressTwo();
        } else if (id == R.id.button_progress_two_increase) {
            increaseProgressTwo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

/*
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_progress_two_decrease) {
            decreaseProgressTwo();
        } else if (id == R.id.button_progress_two_increase) {
            increaseProgressTwo();
        }
    }
*/


    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_progress_two_decrease) {
            decreaseProgressTwo();
        } else if (id == R.id.button_progress_two_increase) {
            increaseProgressTwo();
        }
    }



    private void increaseProgressTwo() {
        progressTwo.setProgress(progressTwo.getProgress() + 1);
        updateProgressTwoColor();
        updateTextProgressTwo();
    }

    private void decreaseProgressTwo() {
        progressTwo.setProgress(progressTwo.getProgress() - 1);
        updateProgressTwoColor();
        updateTextProgressTwo();
    }


    private void updateProgressTwoColor() {
        float progress = progressTwo.getProgress();
        if(progress <= 3) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_red_progress));
        } else if(progress > 3 && progress <= 6) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_orange_progress));
        } else if(progress > 6) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_green_progress));
        }
    }



    private void updateTextProgressTwo() {
        tvProgressTwo.setText(String.valueOf((int) progressTwo.getProgress()));
    }

}