package com.vecika.plin.main;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vecika.plin.R;
import com.vecika.plin.models.Workorder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vecika on 04.11.2017..
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<Workorder> mData;
    private IOnListClicked callback;

    public MainAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItems(ArrayList<Workorder> data){
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.textViewAddress)         TextView mTextViewAddress;
        @BindView(R.id.textViewLastState)       TextView mTextViewLastState;
        @BindView(R.id.textViewSerialNumber)    TextView mTextViewSerialNumber;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindData(Workorder workorder) {
            mTextViewAddress.setText(workorder.getAddress());
            mTextViewLastState.setText(workorder.getLastRead());
            mTextViewSerialNumber.setText(workorder.getSerialNumber());
        }

        @Override
        public void onClick(View v) {
            callback.onItemClicked(mData.get(getAdapterPosition()));
        }
    }

    public interface IOnListClicked{

        void onItemClicked(Workorder workorder);

    }

    public void setCallback(IOnListClicked callback) {
        this.callback = callback;
    }
}
