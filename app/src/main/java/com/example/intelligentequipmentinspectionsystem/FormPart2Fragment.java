package com.example.intelligentequipmentinspectionsystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormPart2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormPart2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final int CAMERA_REQUEST = 1;
    private LinearLayout takePicture;
    private ImageView imageView;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Button send, clear;
    private SignatureView signatureView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormPart2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormPart2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormPart2Fragment newInstance(String param1, String param2) {
        FormPart2Fragment fragment = new FormPart2Fragment();
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

        // camera stuff
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getData() != null){
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imageView.setImageBitmap(bitmap);
                }
            }
        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_part2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // camera stuff
        takePicture = (LinearLayout) view.findViewById(R.id.part2_take_picture_button);
        imageView = (ImageView) view.findViewById(R.id.part2_picture_preview);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST);
                } else {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    activityResultLauncher.launch(takePictureIntent);
                }
            }
        });

        // signature stuff
        signatureView = (SignatureView) view.findViewById(R.id.signature);
        clear = (Button) view.findViewById(R.id.clearSignature);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signatureView.clearSignature();
            }
        });
        // send button
        send = (Button) view.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap emptyBitmap = Bitmap.createBitmap(signatureView.getImage().getWidth(), signatureView.getImage().getHeight(), signatureView.getImage().getConfig());
                if (signatureView.getImage().sameAs(emptyBitmap)) {
                    Toast toast = Toast.makeText(getContext(), "Please Sign", Toast.LENGTH_SHORT);
                        toast.show();
                } else {
                    saveBitmap(signatureView.getImage());
                    Toast toast = Toast.makeText(getContext(), "Form Sent", Toast.LENGTH_SHORT);
                    toast.show();
                    // get data from last fragment
                    FormPart2FragmentArgs args = FormPart2FragmentArgs.fromBundle(getArguments());

                    // prepare bundle for next fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("roomId", args.getRoomId());

                    // navigate to equipmentFragment
                    Navigation.findNavController(view).navigate(R.id.equipmentFragment, bundle);
                }
            }
        });
    }

    public void saveBitmap(Bitmap bmp) {
        try {
            String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
            String filepath = root + "signature.jpg";

            FileOutputStream fos = new FileOutputStream(filepath);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.e("Could not save", e.getMessage());
            e.printStackTrace();
        }
    }

}