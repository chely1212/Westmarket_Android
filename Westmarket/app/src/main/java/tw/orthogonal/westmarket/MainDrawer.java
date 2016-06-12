package tw.orthogonal.westmarket;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

public  class MainDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView profileImgView2;
    public static int tapTrick = 0;
    int missionstatus=0;
            int rtnValue=0;
    DrawerLayout drawer;
    int qscanflag=0;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    View relativeLayout;
  //  private ShakeDetector mShakeDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //relativeLayout = (View)findViewById(R.id.mdLayout);
        //relativeLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.bb1));

        //chg status bar
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.maindrawer));
        //chg status bar
        //chg toolbar
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.maindrawer)));
        }
        //chg toobar

        //setbackgroundimage



       // RelativeLayout rLayout = (RelativeLayout)findViewById(R.id.mdLayout);
       // SVG svg = SVGParser.getSVGFromResource(getResources(), R.raw.android);
       // rLayout.setBackground(svg.createPictureDrawable());



        // RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.mdLayout);
       // Resources res = getResources(); //resource handle
       // Drawable drawable = res.getDrawable(R.drawable.bb1); //new Image that was added to the res folder
       // rLayout.setBackground(drawable);
        //setbackgroundimage



        FacebookSdk.sdkInitialize(getApplicationContext());
        //
        profileImgView2 = (ImageView) findViewById(R.id.idImg);


        String usrPic_src = getIntent().getStringExtra("usrPic_src");
        String usr_welcome = getIntent().getStringExtra("userName");
        missionstatus = getIntent().getIntExtra("missionstatus",0);


        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(qscanflag);
        String strI = sb.toString();
        Toast.makeText(MainDrawer.this, strI, Toast.LENGTH_SHORT).show();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        ImageView imgvw = (ImageView)hView.findViewById(R.id.idImg);
        TextView nav_user = (TextView)hView.findViewById(R.id.userName);
        nav_user.setText(usr_welcome);
        //imgvw.setImageResource(Uri.parse(string2));
        Glide.with(MainDrawer.this)
            .load(usrPic_src)
           .into(imgvw);

        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "請搖動手機接受任務", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();



            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

       // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//

//
    }



    public int getMyData() {
        return missionstatus;
    }

    public int getQrcodeResult() {


        return qscanflag;
    }

    public int setToolBar()
    {
        //chg status bar
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.shopinfo));
        //chg status bar
        //chg toolbar
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.shopinfo)));
        }
        //chg toobar
        return 1;
    }

    public int setToolBar2()
    {
        //chg status bar
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.maindrawer));
        //chg status bar
        //chg toolbar
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.maindrawer)));
        }
        //chg toobar
        return 1;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {



        //initiate camera
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String re = scanResult.getContents();


            qscanflag=1;
            Toast.makeText(MainDrawer.this, re, Toast.LENGTH_SHORT).show();
        }


        //


        //

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        drawer.openDrawer(GravityCompat.START);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            MainFragment fragment = new MainFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_gallery) {
            FinishMissionCollection fragment = new FinishMissionCollection();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_slideshow) {

            Shoptest fragment = new Shoptest();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

            QrcodeScanner fragment = new QrcodeScanner();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_send) {


            Intent intent = new Intent();

            intent.setClass(MainDrawer.this,Login.class);
            startActivity(intent);
            SharedPreferences mPreferences= getSharedPreferences("User", MODE_PRIVATE);
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.clear();   // This will delete all your preferences, check how to delete just one
            editor.commit();
           LoginManager.getInstance().logOut();
            finish();
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
