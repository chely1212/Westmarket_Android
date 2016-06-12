package tw.orthogonal.westmarket;

/**
 * Created by zhonghaoli on 16/5/13.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A simple {@link Fragment} subclass.
 */
public class FinishMissionCollection extends Fragment {
    private ProgressDialog progress;
    String jsonString;
    TextView storeNameView;
    String storeName;
    ImageView imgvw;
    JSONArray jsonArray = null;
    public FinishMissionCollection() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View V8 =inflater.inflate(R.layout.fragment_finished_mission_collection, container, false);
       imgvw = (ImageView)V8.findViewById(R.id.thumbnail);
        CardView cv = (CardView)V8.findViewById(R.id.card_view);
        new PostClass2(getActivity()).execute();
        Glide.with(FinishMissionCollection.this)
                .load("http://140.116.39.153:1748/img/4")
                .into(imgvw);
        //
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnsShopInfo fragment = new AnsShopInfo();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();

            }
        });
        //



        return V8;
    }

    private class PostClass2 extends AsyncTask<String, Void, Void> {

        private Context context;
        public PostClass2(Context c){

            this.context = c;

        }

        protected void onPreExecute(){
            progress= new ProgressDialog(this.context);
            progress.setMessage("Loading");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {




            HttpURLConnection c2 = null;
            try {
                //connection2
                String url2="http://140.116.39.153:1748/store/4";

                URL u2 = new URL(url2);
                c2 = (HttpURLConnection) u2.openConnection();
                c2.setRequestMethod("GET");
                c2.setRequestProperty("Content-length", "0");
                c2.setUseCaches(false);
                c2.setAllowUserInteraction(false);

                c2.connect();
                int status2 = c2.getResponseCode();
                progress.show();
                switch (status2) {
                    case 200:
                    case 201:
                        BufferedReader br2 = new BufferedReader(new InputStreamReader(c2.getInputStream()));
                        StringBuilder sb2 = new StringBuilder();
                        String line2;
                        while ((line2 = br2.readLine()) != null) {
                            sb2.append(line2+"\n");
                        }
                        br2.close();
                        //return sb.toString();
                        jsonString = sb2.toString();


                        //final StringBuilder output=new StringBuilder(jsonString);

                        try {
                            jsonArray = new JSONArray(jsonString);
                            JSONObject rec = jsonArray.getJSONObject(0);
                            storeName=rec.getString("storeName");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // questionView = (TextView)getActivity().findViewById(R.id.missionAnswerDetail);
                        //mulAnsView = (TextView)getActivity().findViewById(R.id.mulAns);
                        // final StringBuilder output=new StringBuilder(QuseString);
                        storeNameView= (TextView)getActivity().findViewById(R.id.title);


                       FinishMissionCollection.this.getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                storeNameView.setText(storeName);

                                progress.dismiss();
                            }
                        });



                }
                //connection2

            } catch (MalformedURLException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            } finally {
                progress.dismiss();
                if (c2 != null) {
                    try {
                        c2.disconnect();
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }


            progress.dismiss();
            return null;
        }


        public void sendPostRequest(View View) {

        }

        protected void onPostExecute() {

        }



    }


}