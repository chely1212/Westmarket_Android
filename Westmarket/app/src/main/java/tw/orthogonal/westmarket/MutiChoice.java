package tw.orthogonal.westmarket;

/**
 * Created by zhonghaoli on 16/5/13.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
public class MutiChoice extends Fragment {
    private ProgressDialog progress;
    String jsonString;
    JSONArray jsonArray = null;
    String QuseString;
    String MissionAnsDetail;
    int MulAnsString;
    String Ans1String;
    String Ans2String;
    String Ans3String;
    String Ans4String;
    RadioButton AnsR1;
    RadioButton AnsR2;
    RadioButton AnsR3;
    RadioButton AnsR4;
    int text;
   Button mulSubmit;
   // Button b1;
   // Button b2;
    int rannum;
    TextView questionView;
    public MutiChoice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View V2 = inflater.inflate(R.layout.fragment_muti_choice, container, false);
        //final TextView outputView =(TextView )V2.findViewById(R.id.text);

        RadioGroup myRadioGroup = (RadioGroup)V2.findViewById(R.id.rgroup);
        Button submitBtn = (Button) V2.findViewById(R.id.mulSubmit);
        myRadioGroup.setVisibility(View.INVISIBLE);
        submitBtn.setVisibility(View.INVISIBLE);
        new PostClass(getActivity()).execute();
        final Button mulSubmit = (Button)V2.findViewById(R.id.mulSubmit);
      //  final Button b1 = (Button)V2.findViewById(R.id.b1);
      //  final Button b2 = (Button)V2.findViewById(R.id.b2);
       final RadioGroup radioGroup = (RadioGroup)V2.findViewById(R.id.rgroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                 text=radioGroup.getCheckedRadioButtonId();
               // RadioButton checkedRadioButton = (RadioButton)V2.findViewById(checkedId);
               // String text = checkedRadioButton.getText().toString();
               // Toast.makeText(getActivity(), ""+text, Toast.LENGTH_SHORT).show();


            }
        });




        mulSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text==(MulAnsString+2131493040))
                {
                    Toast.makeText(getActivity(), "答案正確", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getActivity(), "答錯了ＱＱ", Toast.LENGTH_SHORT).show();

                }
                AnsShopInfo fragment = new AnsShopInfo();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                       getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();

            }
        });

        /*
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),""+(MulAnsString+2131493040) , Toast.LENGTH_SHORT).show();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text==(MulAnsString+2131439040))
                {
                    Toast.makeText(getActivity(), "答案正確", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getActivity(), "答錯了ＱＱ", Toast.LENGTH_SHORT).show();

                }
                AnsShopInfo fragment = new AnsShopInfo();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();

            }
        });
        //
*/
        return V2;
    }






//pstclass
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



       // int rannum=(int)(Math.random()* 9 + 1);
        rannum=4;
        HttpURLConnection c = null;
        try {
            String url="http://140.116.39.153:1748/mission/4";
           // String url="http://140.116.39.153:1748/mission/"+rannum;
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
                    final RadioGroup radioGroup = (RadioGroup)getActivity().findViewById(R.id.rgroup);
                    final Button submitBtn = (Button) getActivity().findViewById(R.id.mulSubmit);
                    MutiChoice.this.getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            radioGroup.setVisibility(View.VISIBLE);
                            submitBtn.setVisibility(View.VISIBLE);
                        }
                    });
                    //final StringBuilder output=new StringBuilder(jsonString);

                    try {
                        jsonArray = new JSONArray(jsonString);
                        JSONObject rec = jsonArray.getJSONObject(0);
                       QuseString=rec.getString("missionQuestion");

                       // jsonArray = new JSONArray(jsonString);
                       // JSONObject rec2 = jsonArray.getJSONObject(0);
                        MulAnsString=rec.getInt("missionAnswer");
                        MissionAnsDetail=rec.getString("missionAnswerDetail");
                       // jsonArray = new JSONArray(jsonString);
                       // JSONObject rec1 = jsonArray.getJSONObject(0);
                        jsonString=rec.getString("missionOption");
                        JSONObject object = new JSONObject(jsonString);
                        Ans1String = object.getString("option1");
                        Ans2String = object.getString("option2");
                        Ans3String = object.getString("option3");
                        Ans4String = object.getString("option4");

                        AnsR1 = (RadioButton)getActivity().findViewById(R.id.Ans1);
                        AnsR2 = (RadioButton)getActivity().findViewById(R.id.Ans2);
                        AnsR3 = (RadioButton)getActivity().findViewById(R.id.Ans3);
                        AnsR4 = (RadioButton)getActivity().findViewById(R.id.Ans4);
                        questionView = (TextView)getActivity().findViewById(R.id.Qusetion);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    final StringBuilder output=new StringBuilder(QuseString);

                    MutiChoice.this.getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                             //outputView.setText(output);

                            AnsR1.setText(Ans1String);
                            AnsR2.setText(Ans2String);
                            AnsR3.setText(Ans3String);
                            AnsR4.setText(Ans4String);
                            questionView.setText(output);
                            progress.dismiss();
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

}

