package com.example.intelligentequipmentinspectionsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    Context context;
    List<String> data1;
    List<String> data2;
    List<String> data3;
    List<String> answerIds;

    public HistoryAdapter(Context context, List<String> data1, List<String> data2, List<String> data3, List<String> answerIds) {
        this.context = context;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        this.answerIds = answerIds;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.three_string_row, parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariable.backPressed = false;
                NavController navController = Navigation.findNavController(view);
                HistoryFragmentDirections.ActionHistoryFragmentToFormFragment action = HistoryFragmentDirections.actionHistoryFragmentToFormFragment(answerIds.get(holder.getAdapterPosition()));
                navController.navigate(action);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        holder.titleTV.setText(data1.get(position));
        holder.subtitleTV.setText(data2.get(position));
        holder.date.setText(data3.get(position));
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV;
        TextView subtitleTV;
        TextView date;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.historyRowTitle);
            subtitleTV = itemView.findViewById(R.id.historyRowSubtitle);
            date = itemView.findViewById(R.id.historyRowDate);
            linearLayout = itemView.findViewById(R.id.threeStringRow);
        }
    }
}
