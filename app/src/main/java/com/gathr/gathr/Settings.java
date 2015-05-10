package com.gathr.gathr;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.gathr.gathr.classes.MyGlobals;
import com.gathr.gathr.classes.SidebarGenerator;

public class Settings extends ActionBarActivity {

    MyGlobals global = new MyGlobals(this);
    Context context = this;

    //  private PendingIntent pendingIntent;
    // private AlarmManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setActionBar();
        ProgressBar spinner;
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        // spinner.setVisibility(View.VISIBLE);

        LoginButton authButton = (LoginButton)findViewById(R.id.authButton);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        new SidebarGenerator((DrawerLayout)findViewById(R.id.drawer_layout), (ListView)findViewById(R.id.left_drawer),android.R.layout.simple_list_item_1,this);

        //Intent notificationIntent = new Intent(this, NotificationReceiver.class);
        //pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent,0);

        //getNotifications();

    }
    public void setActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        //displaying custom ActionBar
        View mActionBarView = getLayoutInflater().inflate(R.layout.my_action_bar, null);
        actionBar.setCustomView(mActionBarView);
        TextView title= (TextView)mActionBarView.findViewById(R.id.title);
        title.setText(R.string.title_action_settings);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    }
    public void openSideBar(View view)
    {
        DrawerLayout sidebar = (DrawerLayout) findViewById(R.id.drawer_layout);
        sidebar.openDrawer(Gravity.LEFT);
    }
    /*public void getNotifications(){
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int interval = 10000;

        manager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),interval,pendingIntent);
        Toast.makeText(this, "WE GOT THIS SON", Toast.LENGTH_SHORT).show();
    }*/

    public void onToggleClicked(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();
        SharedPreferences settings = this.getSharedPreferences("AuthUser", 0);
        SharedPreferences.Editor editor = settings.edit();
        if (on) {
            editor.putBoolean("notifications", true);
            editor.apply();
        } else {
            editor.putBoolean("notifications", false);
            editor.apply();
        }
    }


    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override

        public void call(Session session, SessionState state, Exception exception) {
            if (session != null && (session.isClosed()) ) {

                SharedPreferences settings = context.getSharedPreferences("AuthUser", Context.MODE_PRIVATE);
                settings.edit().clear().apply();

                final Intent intent = getIntent();
                PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("stopNotification");
                AlarmManager manager= (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                manager.cancel(pendingIntent);

                global.tip("You are now logged out!");

                Intent i = new Intent(context, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }
            uiHelper.onResume();
        }
    };

    private UiLifecycleHelper uiHelper;

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }


}