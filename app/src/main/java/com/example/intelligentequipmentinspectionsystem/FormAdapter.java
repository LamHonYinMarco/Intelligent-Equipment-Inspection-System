package com.example.intelligentequipmentinspectionsystem;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.ViewHolder> {
    private Context context;
    private List<String> questionTitles;
    private List<String> questionIds;
//    private List<String> toDoList;
    HashMap<String, String> toDoList = new HashMap<>();
//    private boolean warningMode = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public FormAdapter(Context context, List<String> questionTitles, List<String> questionIds) {
        this.context = context;
        this.questionTitles = questionTitles;
        this.questionIds = questionIds;
        questionIds.forEach(questionId -> {
            toDoList.put(questionId,"");
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_row, parent, false);

        final ViewHolder holder = new ViewHolder(view);

        holder.goodOrBad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.bad) {
                    // if bad is selected make sure it isn't removed from toDoList yet
                    if (!toDoList.containsKey(questionIds.get(holder.getAdapterPosition()))) {
                        toDoList.put(questionIds.get(holder.getAdapterPosition()),"pic");
                    } else {
                        toDoList.replace(questionIds.get(holder.getAdapterPosition()),"pic");
                    }
                    // show reason editText
                    holder.reason.setVisibility(View.VISIBLE);
                } else {
                    // if selected good remove that question from toDoList
                    holder.reason.setVisibility(View.GONE);
                    try {
                        toDoList.remove(questionIds.get(holder.getAdapterPosition()));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                System.out.println("toDoList" + toDoList);
                System.out.println("questionIds" + questionIds);
            }
        });

        // if user have added something to the reason then it can be removed from the to do list
//        holder.reason.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (!editable.toString().equals("")) {
//                    toDoList.remove(questionIds.get(holder.getAdapterPosition()));
//                } else {
//                    if (!toDoList.containsKey(questionIds.get(holder.getAdapterPosition()))) {
//                        toDoList.put(questionIds.get(holder.getAdapterPosition()),"pic");
//                    }
//                }
//                System.out.println("toDoList" + toDoList);
//                System.out.println("questionIds" + questionIds);
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.questionTitleTV.setText(questionTitles.get(position));
//        if(warningMode){
//            System.out.println("it ran");
//            if(questionIds.get(holder.getAdapterPosition()).contains(toDoList.toString())){
//                holder.questionTitleTV.setTextColor(Color.RED);
//            }
//        }
//        if(holder.goodOrBad.getCheckedRadioButtonId() == R.id.bad){
//            holder.reason.setVisibility(View.VISIBLE);
//        } else {
//            holder.reason.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return questionTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionTitleTV;
        RadioGroup goodOrBad;
        RadioButton good;
        RadioButton bad;
        EditText reason;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTitleTV = (TextView) itemView.findViewById(R.id.questionTitle);
            goodOrBad = (RadioGroup) itemView.findViewById(R.id.goodOrBad);
            good = (RadioButton) itemView.findViewById(R.id.good);
            bad = (RadioButton) itemView.findViewById(R.id.bad);
            reason = (EditText) itemView.findViewById(R.id.reasonForBad);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.questionRow);
        }
    }

    public String validation() {
        if (toDoList.containsValue("")){
            return "missing";
        } else if (toDoList.containsValue("pic")){
            return "pic";
        }
        return "pass";
    }

}
