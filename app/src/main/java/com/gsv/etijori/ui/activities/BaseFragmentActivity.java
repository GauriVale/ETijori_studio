package com.gsv.etijori.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gsv.etijori.R;
import com.gsv.etijori.ui.dialogs.AccountDetailsDialogFragment;
import com.gsv.etijori.ui.dialogs.AddDialogFragment;
import com.gsv.etijori.ui.fragments.LoginFragment;

public class BaseFragmentActivity extends AppCompatActivity {

    private boolean hideMenu = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.disallowAddToBackStack();
        Fragment fragment = new LoginFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment, "LoginFragment");
        //fragmentTransaction.addToBackStack("AFragmentTask");
        fragmentTransaction.commit();
    }

    /*
    TODO: rename this activity
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(!hideMenu);
        }
        return true;
    }

    public void hideMenu() {
        hideMenu = true;
        invalidateOptionsMenu();

    }

    public void showMenu() {
        hideMenu = false;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /*if (R.id.menu1 == item.getItemId()) {
            return true;
        }*/

        if (R.id.action_add == item.getItemId()) {
            onAddMenuClick();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onAddMenuClick() {
        AddDialogFragment dialogFragment = new AddDialogFragment();
        dialogFragment.setTargetFragment(getSupportFragmentManager().findFragmentByTag("AccountListFragment"), 100);
        dialogFragment.show(this.getSupportFragmentManager(), "AddDialogFragment");
    }

}
