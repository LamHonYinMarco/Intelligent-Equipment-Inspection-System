package com.example.intelligentequipmentinspectionsystem;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView formName, equipmentNameAndCode, roomNameAndLocation, inspector;
    private Button sendForm;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormFragment newInstance(String param1, String param2) {
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.formRecyclerView);
        formName = (TextView) view.findViewById(R.id.formName);
        equipmentNameAndCode = (TextView) view.findViewById(R.id.equipmentNameAndCode);
        roomNameAndLocation = (TextView) view.findViewById(R.id.roomNameAndLocation);
        inspector = (TextView) view.findViewById(R.id.inspector);
        sendForm = (Button) view.findViewById(R.id.sendForm);

        if (getArguments() != null) {
            FormFragmentArgs args = FormFragmentArgs.fromBundle(getArguments());




            DataService dataService = new DataService();
            dataService.getRoomById(args.getRoomId(), new DataService.VolleyResponseListenerForSingle() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse(String data1, String data2) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            roomNameAndLocation.setText(data1 + " (" + data2 + ")");
                        }
                    });
                }
            });

            dataService.getEquipmentById(args.getEquipmentId(), new DataService.VolleyResponseListenerForSingle() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse(String data1, String data2) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            equipmentNameAndCode.setText(data1 + " (" + data2 + ")");
                            //TODO remove this
                            formName.setText(data1 + " Form");
                        }
                    });

                }
            });

            inspector.setText(getUsername());
//            dataService.getQuestionsByEquipmentId(args.getEquipmentId, new DataService.)
            List<String> listOfQuestionTitles = new ArrayList<>();
            listOfQuestionTitles.add("How is the handrail?");
            listOfQuestionTitles.add("How is the heart rate sensor grip?");
            listOfQuestionTitles.add("How is the emergency stop?");
            listOfQuestionTitles.add("How is the display screen?");
            listOfQuestionTitles.add("How are the touch controls?");
            listOfQuestionTitles.add("How is the belt?");
            listOfQuestionTitles.add("How is the running deck?");
            listOfQuestionTitles.add("How is the motor?");
            listOfQuestionTitles.add("How is the cushioning system?");
            listOfQuestionTitles.add("How is it's overall condition?");

            List<String> listOfQuestionId = new ArrayList<>();
            listOfQuestionId.add("1");
            listOfQuestionId.add("2");
            listOfQuestionId.add("3");
            listOfQuestionId.add("4");
            listOfQuestionId.add("5");
            listOfQuestionId.add("6");
            listOfQuestionId.add("7");
            listOfQuestionId.add("8");
            listOfQuestionId.add("9");
            listOfQuestionId.add("10");

            FormAdapter formAdapter = new FormAdapter(getContext(), listOfQuestionTitles, listOfQuestionId);
            recyclerView.setAdapter(formAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            sendForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(formAdapter.validation() == "pass") {
                        Toast toast = Toast.makeText(getContext(), "Form Sent", Toast.LENGTH_SHORT);
                        toast.show();
                        NavController navController = Navigation.findNavController(view);
                        navController.navigateUp();
                    } else if (formAdapter.validation() == "missing"){
                        Toast toast = Toast.makeText(getContext(), "Please Fill All Questions", Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (formAdapter.validation() == "pic") {
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_formFragment_to_cameraFragment);
                    }
                }
            });
        }
    }

    private String getUsername() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UsernamePref", 0);
        String username = sharedPreferences.getString("username", "");
        return username;
    }
}