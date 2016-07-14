package tw.orthogonal.westmarket;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QscanActicvity extends AppCompatActivity {
    Button qrScanBtn;
    ImageView gif;
    Button gomainBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qscan_acticvity);
       qrScanBtn = (Button) findViewById(R.id.button5);
         gif=(ImageView)findViewById(R.id.imageView4);
        gomainBtn = (Button) findViewById(R.id.button6);
        qrScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator integrator = new IntentIntegrator(QscanActicvity.this);
                integrator.initiateScan();

            }
        });

        gif.setVisibility(View.INVISIBLE);
        gomainBtn.setVisibility(View.INVISIBLE);

        gomainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle localBundle = new Bundle();
                localBundle.putInt("missionstatus", 10);



                Intent intent = new Intent();
                intent.putExtras(localBundle);

                intent.setClass(QscanActicvity.this,MainDrawer.class);
                startActivity(intent);


            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {



        //initiate camera
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null&&resultCode==RESULT_OK) {
            String re = scanResult.getContents();
            Log.d("code", re);
            Toast.makeText(QscanActicvity.this, re, Toast.LENGTH_SHORT).show();
           gif.setVisibility(View.VISIBLE);
            gomainBtn.setVisibility(View.VISIBLE);
            qrScanBtn.setVisibility(View.INVISIBLE);
        }


        //


        //

    }

}
