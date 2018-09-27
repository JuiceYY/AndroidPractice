package cn.istary.material;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class DrawerActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private Button button;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.drawer_toolbar);
        button = findViewById(R.id.drawer_button);
        navigationView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        button.setOnClickListener(this);

        navigationView.setCheckedItem(R.id.nav_call);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_offline:
                        Intent intent = new Intent("cn.istary.broadcastforceoffline.FORCE_OFFLINE");
                        sendBroadcast(intent);
                        break;
                    case R.id.nav_datasave_demo:
                        Intent intent1 = new Intent(DrawerActivity.this, DataSaveDemoActivity.class);
                        startActivity(intent1);
                    case R.id.nav_asynchandler:
                        Intent intent2 = new Intent(DrawerActivity.this, AsyncHandlerActivity.class);
                        startActivity(intent2);
                    case R.id.nav_myservice:
                        Intent intent3 = new Intent(DrawerActivity.this, TestMyServiceActivity.class);
                        startActivity(intent3);

                    default:
                        drawerLayout.closeDrawers();
                        break;

                }
                return true;
            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch (item.getItemId()){
//            case android.R.id.home:
//                drawerLayout.openDrawer(GravityCompat.START);
//                break;
//        }
//        return true;
//    }


    @Override
    public void onClick(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}
