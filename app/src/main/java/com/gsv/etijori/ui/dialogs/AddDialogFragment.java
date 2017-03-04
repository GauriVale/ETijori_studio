package com.gsv.etijori.ui.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gsv.etijori.AccountCredentials;
import com.gsv.etijori.R;
import com.gsv.etijori.database.AccountCredentialsDatabase;
import com.gsv.etijori.util.ETijoriUtil;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Gauri on 3/13/2016.
 */
public class AddDialogFragment extends DialogFragment implements View.OnClickListener {

    private EditText etAccType;
    private EditText etUserName;
    private EditText etPassword;
    private Button btnCancel;
    private Button btnSave;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_account, container, false);

        etAccType = (EditText) view.findViewById(R.id.etAccType);
        etUserName = (EditText) view.findViewById(R.id.etUserName);
        etPassword = (EditText) view.findViewById(R.id.etPassword);

        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnSave = (Button) view.findViewById(R.id.btnSave);

        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_CANCELED, getActivity().getIntent());
                dismiss();
                break;

            case R.id.btnSave:
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                String accType = etAccType.getText().toString();
                Log.d(ETijoriUtil.ET_APPLICATION_TAG, "user name: " + userName + ", password: " + password + ", account type: " + accType);
                AccountCredentials accountCredentials = new AccountCredentials(userName, password, accType);
                onSaveAccount(accountCredentials);

                Intent intent = new Intent();
                intent.putExtra("USER_NAME", userName);
                intent.putExtra("PASSWORD", password);
                intent.putExtra("ACCOUNT_TYPE", accType);

                getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
                dismiss();
                break;

            default:
                break;
        }
    }

    private void onSaveAccount(AccountCredentials accountCredentials) {
        AccountCredentialsDatabase database = AccountCredentialsDatabase.getInstance(getActivity());
        boolean isAdded = database.add(accountCredentials);
        Log.d(ETijoriUtil.ET_APPLICATION_TAG, "is account added: " + isAdded);
        if (!isAdded) {
            Toast.makeText(getActivity(), R.string.msg_add_acc_fail, Toast.LENGTH_SHORT).show();
        }
    }

}
