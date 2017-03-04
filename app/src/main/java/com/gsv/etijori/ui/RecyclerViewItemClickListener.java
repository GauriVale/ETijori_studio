package com.gsv.etijori.ui;

import android.view.View;

import com.gsv.etijori.AccountCredentials;

/**
 * Created by Gauri Vale on 5/17/2015.
 * Listener interface for click events of items present in recycler view.
 */
public interface RecyclerViewItemClickListener {

    /**
     * This callback will be called when item present in recycler view is clicked.
     * @param details instance of {@link AccountCredentials} storing details linked with clicked item.
     * @param view view of clicked item.
     * @param position position of clicked item.
     */
    public void onItemClick(AccountCredentials details, View view, int position);

    public void onLongClick(AccountCredentials details, View view, int position);
}
