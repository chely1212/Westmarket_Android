package tw.orthogonal.westmarket;

/**
 * Created by zhonghaoli on 16/5/13.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;

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
public class AnsShopInfo extends Fragment {
    String jsonString;
    JSONArray jsonArray = null;
    String MissionAnsDetail;
    String mulAns;
    String storeName;
    String storeAddress;
    String storePhone;
    String storeProduct;
    TextView questionView;
    TextView mulAnsView;
    TextView storeNameView;
    TextView storeAddressView;
    TextView storePhoneView;
    TextView storeProductView;
    private ProgressDialog progress;
    public AnsShopInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View V5 = inflater.inflate(R.layout.fragment_ansshopinfo, container, false);

        MainDrawer activity = (MainDrawer) getActivity();
         activity.setToolBar();


        String url="http://140.116.39.153:1748/img/4";
        ImageView imgvw = (ImageView)V5.findViewById(R.id.ShopInfoImg);
         Button goNextButton = (Button)V5.findViewById(R.id.goNextMission);
        Glide.with(AnsShopInfo.this)
                .load(url)
                .into(imgvw);
        new PostClass(getActivity()).execute();
        new PostClass2(getActivity()).execute();
        goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();


                intent.setClass(getActivity(),QscanActicvity.class);
                startActivity(intent);


            }
        });





        return V5;
    }


    private class PostClass extends AsyncTask<String, Void, Void> {

    private Context context;
    //  final TextView outputView = (TextView) findViewById(R.id.text);
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




        HttpURLConnection c = null;
        try {
            String url="http://140.116.39.153:1748/mission/4";

            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);

            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    //return sb.toString();
                    jsonString = sb.toString();


                    //final StringBuilder output=new StringBuilder(jsonString);

                    try {
                        jsonArray = new JSONArray(jsonString);
                        JSONObject rec = jsonArray.getJSONObject(0);
                        MissionAnsDetail=rec.getString("missionAnswerDetail");
                        mulAns=rec.getString("missionAnswer");



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    questionView = (TextView)getActivity().findViewById(R.id.missionAnswerDetail);
                    mulAnsView = (TextView)getActivity().findViewById(R.id.mulAns);
                    // final StringBuilder output=new StringBuilder(QuseString);

                    AnsShopInfo.this.getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            questionView.setText(MissionAnsDetail);
                            mulAnsView.setText(mulAns);
                           // progress.dismiss();
                        }
                    });



            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }



        return null;
    }


    public void sendPostRequest(View View) {

    }

    protected void onPostExecute() {

    }



}
//pstclass


    //pstclass2
    private class PostClass2 extends AsyncTask<String, Void, Void> {

        private Context context;
        //  final TextView outputView = (TextView) findViewById(R.id.text);
        public PostClass2(Context c){

            this.context = c;
//            this.error = status;
//            this.type = t;
        }

        protected void onPreExecute(){
            //progress= new ProgressDialog(this.context);
            //progress.setMessage("Loading");
            //progress.show();
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
                            storeAddress=rec.getString("stordAddress");
                            storePhone=rec.getString("storePhone");
                            storeProduct=rec.getString("storeProduct");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                       // questionView = (TextView)getActivity().findViewById(R.id.missionAnswerDetail);
                        //mulAnsView = (TextView)getActivity().findViewById(R.id.mulAns);
                        // final StringBuilder output=new StringBuilder(QuseString);
                        storeNameView= (TextView)getActivity().findViewById(R.id.storeName);
                        storeAddressView = (TextView)getActivity().findViewById(R.id.storeAddress);
                        storePhoneView = (TextView)getActivity().findViewById(R.id.storePhone);
                        storeProductView= (TextView)getActivity().findViewById(R.id.storeproductview);
                        AnsShopInfo.this.getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                storeNameView.setText(storeName);
                                storeAddressView.setText(storeAddress);
                                storePhoneView.setText( storePhone);
                                storeProductView.setText(storeProduct);
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

    //pstclass2

}