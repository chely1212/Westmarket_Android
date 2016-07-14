package tw.orthogonal.westmarket;

/**
 * Created by zhonghaoli on 16/5/13.
 */
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


/**
 * A simple {@link Fragment} subclass.
 */
public class QrcodeScanner extends Fragment {
    Button qrBtn;
    Button qrBtn2;
    int myDataFromActivity=0;
    public  QrcodeScanner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View V6= inflater.inflate(R.layout.fragment_qrscanner, container, false);




   qrBtn = (Button)V6.findViewById(R.id.qrBtn);
        qrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // IntentIntegrator integrator = new IntentIntegrator(getActivity());
               // integrator.initiateScan();
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setOrientationLocked(false);
                integrator.setCaptureActivity(SmallCaptureActivity.class);
                integrator.initiateScan();


            }
        });

        qrBtn2 = (Button)V6.findViewById(R.id.sendBack);
        qrBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainDrawer activity = (MainDrawer) getActivity();
                myDataFromActivity = activity.getQrcodeResult();
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(myDataFromActivity);
                String strI = sb.toString();
                Toast.makeText(getActivity(), strI, Toast.LENGTH_SHORT).show();


            }
        });


        return V6;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

/*
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String re = scanResult.getContents();
            Log.d("code", re);
            Toast.makeText(getActivity(), "OAS", Toast.LENGTH_SHORT).show();

        }
*/



        Fragment uploadType = getChildFragmentManager().findFragmentById(R.id.fragment_container);

        if (uploadType != null) {
            uploadType.onActivityResult(requestCode, resultCode, intent);
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }








}