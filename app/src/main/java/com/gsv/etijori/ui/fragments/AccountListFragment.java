package com.gsv.etijori.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gsv.etijori.AccountCredentials;
import com.gsv.etijori.R;
import com.gsv.etijori.database.AccountCredentialsDatabase;
import com.gsv.etijori.ui.AccountDetailsAdapter;
import com.gsv.etijori.ui.RecyclerViewItemClickListener;
import com.gsv.etijori.ui.RecyclerViewItemDecoration;
import com.gsv.etijori.ui.activities.BaseFragmentActivity;
import com.gsv.etijori.ui.dialogs.AccountDetailsDialogFragment;
import com.gsv.etijori.util.ETijoriUtil;

import java.util.ArrayList;

public class AccountListFragment extends Fragment {

    private RecyclerView recyclerView;
    private AccountDetailsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<AccountCredentials> detailsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseFragmentActivity) getActivity()).showMenu();

        detailsList = AccountCredentialsDatabase.getInstance(getActivity()).getAllCredentials();
        if (null != detailsList && detailsList.size() > 0) {
            for (AccountCredentials accountCredentials : detailsList) {
                Log.d(ETijoriUtil.ET_APPLICATION_TAG, "" + accountCredentials.toString());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvAccountList);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AccountDetailsAdapter(detailsList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerViewItemDecoration(getActivity(), RecyclerViewItemDecoration.VERTICAL_LIST));

        adapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(AccountCredentials details, View view, int position) {
                AccountDetailsDialogFragment dialogFragment = new AccountDetailsDialogFragment();
                dialogFragment.setAccountDetails(details);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "AccountDetailsDialogFragment");
            }

            @Override
            public void onLongClick(AccountCredentials details, View view, int position) {
                //TODO: show, hide action menu
                /*
                TODO:
                On long press show delete and edit context menu
                 */
            }
        });

        return view;
    }

    /*
    TODO:
    update this list after adding new account via dialog
     */


    /*
    TODO: use FAB to add account
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        /*
        TODO: update ui
         */
        if (100 == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                String userName = intent.getStringExtra("USER_NAME");
                String password = intent.getStringExtra("PASSWORD");
                String accType = intent.getStringExtra("ACCOUNT_TYPE");

                AccountCredentials accountCredentials = new AccountCredentials(userName, password, accType);
                adapter.addItem(accountCredentials);
                adapter.notifyItemChanged(adapter.getItemCount() - 1);
            }
        }
    }
}
