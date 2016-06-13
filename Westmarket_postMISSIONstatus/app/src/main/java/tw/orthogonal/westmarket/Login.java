package tw.orthogonal.westmarket;

import android.content.Intent;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.util.EntityUtils;

public class Login extends AppCompatActivity {

    //private CallbackManager callbackManager;
    private TextView info;
    private ImageView profileImgView;
    private LoginButton loginButton;
    JSONArray jsonArray = null;
    String jsonString;
    private PrefUtil prefUtil;
    private IntentUtil intentUtil;
    private ProgressDialog progress;
    String hash;
    String url="http://140.116.39.153:1748/mission/4";
    CallbackManager callbackManager;
    private AccessToken accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //fb ini
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        //fb ini


        setContentView(R.layout.activity_login);

       //fb
        prefUtil = new PrefUtil(this);
        intentUtil = new IntentUtil(this);

        info = (TextView) findViewById(R.id.info);
        profileImgView = (ImageView) findViewById(R.id.profile_img);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                info.setText(message(profile));

                String userId = loginResult.getAccessToken().getUserId();
                String accessToken = loginResult.getAccessToken().getToken();

                // save accessToken to SharedPreference
                prefUtil.saveAccessToken(accessToken);

                String profileImgUrl = "https://graph.facebook.com/" + userId + "/picture?type=large";

                Bundle localBundle = new Bundle();
                localBundle.putString("usrPic_src", profileImgUrl);
                localBundle.putString("userName",message(profile));
                Intent intent = new Intent();
                intent.putExtras(localBundle);

                intent.setClass(Login.this,MainDrawer.class);
                startActivity(intent);

               // Intent intent = new Intent();
               // intent.setClass(Login.this,MainDrawer.class);
               // startActivity(intent);


               // Glide.with(Login.this)
                   //     .load(profileImgUrl)
                     //   .into(profileImgView);

            }

            @Override
            public void onCancel() {
                info.setText("Login attempt cancelled.");
            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
                info.setText("Login attempt failed.");
            }
        });
        //fb


    }

    public void toast(View v)
    {
        Toast.makeText(Login.this, "hello", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        //intent.putExtras(localBundle);

        intent.setClass(Login.this,MainDrawer.class);
        startActivity(intent);
    }



    public void sendPostRequest(View View) {
        new PostClass(this).execute();
       // Intent getNameScreenIntent=new Intent(this,MainDrawer.class);
        //final int result=1;
        //startActivity(getNameScreenIntent);
        //System.out.println("ssss");


    }

    public class AuthMsg {
        private int code;
        private String message;

        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    }

    private class PostClass extends AsyncTask<String, String, String> {

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
        protected String doInBackground(String... params) {
            try {

                final TextView outputView = (TextView) findViewById(R.id.showOutput);
                URL url = new URL("http://140.116.39.153:1748/mission/4");

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
                connection.setInstanceFollowRedirects(false);
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                while((line = br.readLine()) != null ) {
                    responseOutput.append(line);
                }

                jsonString = responseOutput.toString();
                br.close();
                final StringBuilder output = new StringBuilder(jsonString);


                try {
                    jsonArray = new JSONArray(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Login.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                       outputView.setText(output);
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



        protected void onPostExecute(String str) {

          //  super.onPostExecute(responseOutput);
            progress.dismiss();
        }

    }





    @Override
    public void onResume() {
        super.onResume();
        deleteAccessToken();
        Profile profile = Profile.getCurrentProfile();
        info.setText(message(profile));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private String message(Profile profile) {
        StringBuilder stringBuffer = new StringBuilder();
        if (profile != null) {
            stringBuffer.append("Welcome ").append(profile.getName());
        }
        return stringBuffer.toString();
    }

    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    //User logged out
                    prefUtil.clearToken();
                    clearUserArea();
                }
            }
        };
    }

    private void clearUserArea() {
        info.setText("");
        profileImgView.setImageDrawable(null);
    }

}



