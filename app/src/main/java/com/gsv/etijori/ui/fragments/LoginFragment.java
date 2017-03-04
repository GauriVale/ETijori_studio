package com.gsv.etijori.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gsv.etijori.AccountCredentials;
import com.gsv.etijori.R;
import com.gsv.etijori.database.AccountCredentialsDatabase;
import com.gsv.etijori.ui.activities.BaseFragmentActivity;
import com.gsv.etijori.util.ETijoriUtil;

public class LoginFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnSubmit;

    private TextInputLayout tilConfirmPassword;

    private boolean isLockKeySet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseFragmentActivity) getActivity()).hideMenu();

        AccountCredentialsDatabase database = AccountCredentialsDatabase.getInstance(getActivity());
        AccountCredentials credentials = database.getCredentials(ETijoriUtil.ET_USER_NAME, ETijoriUtil.ET_ACCOUNT_TYPE);
        if (null != credentials) {
            isLockKeySet = true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*
        TODO: hide menu on action bar
         */
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etPassword.setFocusableInTouchMode(true);
        etPassword.addTextChangedListener(this);

        tilConfirmPassword = (TextInputLayout) view.findViewById(R.id.tilConfirmPassword);
        if (!isLockKeySet) {
            tilConfirmPassword.setVisibility(View.VISIBLE);
            etConfirmPassword = (EditText) view.findViewById(R.id.etConfirmPassword);
            etConfirmPassword.addTextChangedListener(this);
        } else {
            tilConfirmPassword.setVisibility(View.GONE);
        }

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (etPassword.hasFocus()) {
            imm.showSoftInput(etPassword, 0);
        } else {
            imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
        }

        TextView TextView1 = (TextView) view.findViewById(R.id.TextView1);
        String str = String.format("%-5s : %s", "P", "djflksdjfldsfjsld\n" );
        TextView1.setText(str);

        str = String.format("%-5s : %s", "MN", "djflksdjfldsfjsld\n" );
        TextView1.append(str);

        str = String.format("%-5s : %s", "QRT", "djflksdjfldsfjsld" );
        TextView1.append(str);


        return view;
    }

    /*
    TODO: if lock key is not set , display set key screen by default
     */

    @Override
    public void onStart() {
        super.onStart();

    }

    private void onSubmitButtonClick() {


        String password = etPassword.getText().toString();
        if (null == password || password.equals("")) {
            Toast.makeText(getActivity(), R.string.msg_invalid_password, Toast.LENGTH_SHORT).show();
            return;
        }

        AccountCredentialsDatabase database = AccountCredentialsDatabase.getInstance(getActivity());
        if (isLockKeySet) {
            AccountCredentials credentials = database.getCredentials(ETijoriUtil.ET_USER_NAME, ETijoriUtil.ET_ACCOUNT_TYPE);
            if (!password.equals(credentials.getPassword())) {
                Toast.makeText(getActivity(), R.string.msg_invalid_password, Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            String confirmPassword = etConfirmPassword.getText().toString();
            if (null == confirmPassword || confirmPassword.equals("")) {
                Toast.makeText(getActivity(), R.string.msg_invalid_confirm_password, Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(getActivity(), R.string.msg_password_mismatch, Toast.LENGTH_SHORT).show();
                return;
            }

            AccountCredentials newCred = new AccountCredentials(ETijoriUtil.ET_USER_NAME, password, ETijoriUtil.ET_ACCOUNT_TYPE);
            database.add(newCred);
            Toast.makeText(getActivity(), R.string.msg_lock_key_created, Toast.LENGTH_LONG).show();
        }

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);

            /*
            TODO: don't keep account list fragment in history to avoid password session issues
             */
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = new AccountListFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, "AccountListFragment").addToBackStack("AccountListFragmentTask").commit();

        //TODO: remove login fragment from backstack
        //fragmentManager.popBackStack();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {
            onSubmitButtonClick();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().trim().equals("")) {
            btnSubmit.setEnabled(true);
        } else {
            btnSubmit.setEnabled(false);
        }
    }
}
