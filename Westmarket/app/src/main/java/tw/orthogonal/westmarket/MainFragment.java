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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    private RoundCornerProgressBar progressTwo;
    private TextRoundCornerProgressBar progressThree;



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
       // final Button button = (Button)V.findViewById(R.id.main_button);
       // final Button inbtn = (Button)V.findViewById(R.id.button_progress_two_increase);
      //  final Button decbtn = (Button)V.findViewById(R.id.button_progress_two_decrease);

        MainDrawer activity = (MainDrawer) getActivity();
        int myDataFromActivity = activity.getMyData();
     //   activity.setToolBar2();

        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();


        FloatingActionButton fab = (FloatingActionButton)V.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "請搖動手機接受任務", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

                    //@Override
                    public void onShake(int count) {
                        //Toast.makeText(this, "Please enter name in correct format.", Toast.LENGTH_SHORT);
                        //button.setText("Hello");
                        //Toast.makeText(getActivity(), "手機已搖動", Toast.LENGTH_SHORT).show();


                        //  new PostClass(getActivity()).execute();

                        MutiChoice fragment = new  MutiChoice();
                        android.support.v4.app.FragmentTransaction fragmentTransaction =
                                getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.commit();
                    }

                });

            }
        });

        //AsynHTTp


/*
        //toast on UI thread
       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // button.setText("Hello");
               // new PostClass(MainFragment.this.getActivity()).execute();
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
*/

        //progressbar
        progressTwo = (RoundCornerProgressBar)V.findViewById(R.id.progress_two);
        progressTwo.setProgressBackgroundColor(getResources().getColor(R.color.custom_progress_background));
        updateProgressTwoColor();



        tvProgressTwo = (TextView)V.findViewById(R.id.tv_progress_two);
        updateTextProgressTwo();

        //progressbar

   if(myDataFromActivity==0) {
       decreaseProgressTwo();
       decreaseProgressTwo();
       decreaseProgressTwo();
       decreaseProgressTwo();
       decreaseProgressTwo();
       //asydecreaseProgressTwo();
   }
        else
   {
       for(int i=0;i<myDataFromActivity;i++)
       {
           increaseProgressTwo();
       }
   }
        return V;




    }





    //get json

    //get json

/*
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
*/
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
        tvProgressTwo.setText(String.valueOf((int) progressTwo.getProgress()*10)+"%任務完成度");
    }

}