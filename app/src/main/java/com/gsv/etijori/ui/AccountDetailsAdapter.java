package com.gsv.etijori.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gsv.etijori.AccountCredentials;
import com.gsv.etijori.R;

import java.util.ArrayList;

public class AccountDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<AccountCredentials> mDetailsList;
    private RecyclerViewItemClickListener mListener;

    public AccountDetailsAdapter(ArrayList<AccountCredentials> detailsList) {
        mDetailsList = detailsList;
    }

    public void addItem(AccountCredentials accountCredentials) {
        mDetailsList.add(accountCredentials);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_account_list_item, parent, false);
        return new AccountViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (null == mDetailsList || mDetailsList.size() <= 0) {
            return;
        }
        AccountViewHolder viewHolder = (AccountViewHolder) holder;
        AccountCredentials details = mDetailsList.get(position);
        viewHolder.getProductNameTextView().setText(details.getAccountName());

    }

    @Override
    public int getItemCount() {
        if (null == mDetailsList) {
            return 0;
        }

        return mDetailsList.size();
    }

    public void setOnItemClickListener(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    /**
     *
     */
    public class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView tvAccountName;

        public AccountViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            tvAccountName = (TextView) v.findViewById(R.id.tvAccountName);
        }

        public TextView getProductNameTextView() {
            return tvAccountName;
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(AccountDetailsAdapter.this.mDetailsList.get(getPosition()), v, getPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            mListener.onLongClick(AccountDetailsAdapter.this.mDetailsList.get(getPosition()), v, getPosition());
            return false;
        }
    }

}
