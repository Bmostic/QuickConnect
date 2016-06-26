package com.brankomostic.quickconnect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    ListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listFragment = new ListFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, listFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_share) {
            listFragment.dialog(this);
            return true;
        } else if(id == R.id.action_report_problem) {
            Intent report = new Intent(Intent.ACTION_SENDTO);
            report.setData(Uri.parse("mailto:"));
            report.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.email)});
            report.putExtra(Intent.EXTRA_SUBJECT, "Quick Connect Feedback/Bug Report");
            startActivity(report);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
