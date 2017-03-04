package com.gsv.etijori.ui.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gsv.etijori.AccountCredentials;
import com.gsv.etijori.R;

public class AccountDetailsDialogFragment extends DialogFragment {

    private TextView tvUserName;
    private TextView tvPassword;

    private AccountCredentials accountCredentials;

    public AccountDetailsDialogFragment() {
        super();
    }
    public void setAccountDetails(AccountCredentials credentials) {
        this.accountCredentials = credentials;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_details, container, false);
        tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        tvPassword = (TextView) view.findViewById(R.id.tvPassword);
        tvUserName.setText(accountCredentials.getUserName());
        tvPassword.setText(accountCredentials.getPassword());
        getDialog().setTitle(accountCredentials.getAccountName());
        return view;
    }
}
