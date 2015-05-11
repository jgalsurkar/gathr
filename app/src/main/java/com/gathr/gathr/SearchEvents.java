package com.gathr.gathr;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



public class SearchEvents extends ActionBarActivityPlus {
    public String category, categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar("Search Events");
        hideSidebarButton();
        setContentView(R.layout.activity_search_events);
    }


    public void getTimeFilter(View v){
        TimePickerFragment newFragment = new TimePickerFragment((TextView)findViewById(R.id.vw_time));
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void openCategory(View v){
        Intent i = new Intent(this, ListViewMultipleSelectionActivity.class);
        i.putExtra("from","event");
        i.putExtra("categoryId", categoryId);
        startActivityForResult(i, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 0) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                category = data.getStringExtra("category");
                categoryId = data.getStringExtra("categoryId");
            }
        }
    }
    public void search(View v){
        EditText locEdit = (EditText)findViewById(R.id.et_location);
        TextView timeEdit = (TextView)findViewById(R.id.vw_time);
        String location = locEdit.getText().toString(),
                time = timeEdit.getText().toString();

        Intent intent;

        intent= new Intent(getApplicationContext(),MapsActivity.class );
        intent.putExtra("category", category);
        intent.putExtra("categoryId", categoryId);
        intent.putExtra("location",location);
        intent.putExtra("time", time);

        // start the ResultActivity
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}

