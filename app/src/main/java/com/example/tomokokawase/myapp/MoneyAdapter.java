package com.example.tomokokawase.myapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tomokokawase on 2018/10/7.
 * 一个用于连接view和Recycle的适配器
 */

public class MoneyAdapter extends RecyclerView.Adapter<MoneyAdapter.ViewHolder> {

    private List<ExchangeRateActivity.Money> _moneyList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView excbuy;
        TextView excsell;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            excbuy = (TextView) view.findViewById(R.id.excbuy);
            excsell = (TextView) view.findViewById(R.id.excsell);
        }
    }

    public MoneyAdapter(List<ExchangeRateActivity.Money> moneyList) {
        _moneyList = moneyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.money_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ExchangeRateActivity.Money money = _moneyList.get(position);
        holder.name.setText(money.getName());
        holder.excbuy.setText(String.valueOf(money.getExcBuyPrice()));
        holder.excsell.setText(String.valueOf(money.getExcSellPrice()));
    }

    @Override
    public int getItemCount() {
        return _moneyList.size();
    }
}

