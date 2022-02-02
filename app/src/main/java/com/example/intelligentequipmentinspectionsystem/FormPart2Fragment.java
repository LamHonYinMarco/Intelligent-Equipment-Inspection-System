package com.example.intelligentequipmentinspectionsystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
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
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
                if (result.getData() != null) {
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
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
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

//                    generatePDF();


                    // navigate to equipmentFragment
                    GlobalVariable.backPressed = false;
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.navigateUp();
                    navController.navigateUp();
//                    Navigation.findNavController(view).navigate(R.id.equipmentFragment, bundle);
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
//    the canvas way
//    private void generatePDF() {
//        // declaring width and height for the PDF file.
//        int pageHeight = 1188;
//        int pageWidth = 842;
//
//        // creating an object variable for the PDF document.
//        PdfDocument pdfDocument = new PdfDocument();
//
//
//        Paint paint = new Paint();
//        Paint title = new Paint();
//
//        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
//
//        // setting start page for the PDF file.
//        PdfDocument.Page myPage = pdfDocument.startPage(myPageInfo);
//
//        // creating a variable for canvas from the page of PDF.
//        Canvas canvas = myPage.getCanvas();
//
//        // below line is used to draw our image on our PDF file.
//        // the first parameter of our drawbitmap method is
//        // our bitmap
//        // second parameter is position from left
//        // third parameter is position from top and last
//        // one is our variable for paint.
//        canvas.drawBitmap(signatureView.getImage(), 56, 40, paint);
//
//        // below line is used for adding typeface for
//        // our text which we will be adding in our PDF file.
//        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//
//        // below line is used for setting text size
//        // which we will be displaying in our PDF file.
//        title.setTextSize(15);
//
//        // below line is sued for setting color
//        // of our text inside our PDF file.
//        title.setColor(ContextCompat.getColor(getContext(), R.color.purple_200));
//
//        // below line is used to draw text in our PDF file.
//        // the first parameter is our text, second parameter
//        // is position from start, third parameter is position from top
//        // and then we are passing our variable of paint which is title.
//        canvas.drawText("A portal for IT professionals.", 209, 100, title);
//        canvas.drawText("Geeks for Geeks", 209, 80, title);
//
//        // similarly we are creating another text and in this
//        // we are aligning this text to center of our PDF file.
//        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//        title.setColor(ContextCompat.getColor(this, R.color.purple_200));
//        title.setTextSize(15);
//
//        // below line is used for setting
//        // our text to center of PDF.
//        title.setTextAlign(Paint.Align.CENTER);
//        canvas.drawText("This is sample document which we have created.", 396, 560, title);
//
//        // after adding all attributes to our
//        // PDF file we will be finishing our page.
//        pdfDocument.finishPage(myPage);
//
//        // below line is used to set the name of
//        // our PDF file and its path.
//        File file = new File(Environment.getExternalStorageDirectory(), "Test.pdf");
//
//        try {
//            // after creating a file name we will
//            // write our PDF file to that location.
//            pdfDocument.writeTo(new FileOutputStream(file));
//
//            // below line is to print toast message
//            // on completion of PDF generation.
//            Toast.makeText(getContext(), "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            // below line is used
//            // to handle error
//            e.printStackTrace();
//        }
//        // after storing our pdf to that
//        // location we are closing our PDF file.
//        pdfDocument.close();
//    }

    private void generatePDF() {
        System.out.println("generatePDF");
        PdfGenerator.getBuilder()
                .setContext(getContext())
                .fromLayoutXMLSource()
                .fromLayoutXML(R.layout.fragment_form_part2, R.layout.fragment_form_part2)
                /* "fromLayoutXML()" takes array of layout resources.
                 * You can also invoke "fromLayoutXMLList()" method here which takes list of layout resources instead of array. */
                .setFileName("Test-PDF")
                /* It is file name */
                .setFolderName("FolderA/FolderB/FolderC")
                /* It is folder name. If you set the folder name like this pattern (FolderA/FolderB/FolderC), then
                 * FolderA creates first.Then FolderB inside FolderB and also FolderC inside the FolderB and finally
                 * the pdf file named "Test-PDF.pdf" will be store inside the FolderB. */
                .openPDFafterGeneration(true)
                /* It true then the generated pdf will be shown after generated. */
                .build(new PdfGeneratorListener() {
                    @Override
                    public void onFailure(FailureResponse failureResponse) {
                        super.onFailure(failureResponse);
                        System.out.println("It failed: " + failureResponse.getErrorMessage());
                        /* If pdf is not generated by an error then you will findout the reason behind it
                         * from this FailureResponse. */
                    }

                    @Override
                    public void onStartPDFGeneration() {
                        /*When PDF generation begins to start*/
                    }

                    @Override
                    public void onFinishPDFGeneration() {
                        /*When PDF generation is finished*/
                    }

                    @Override
                    public void showLog(String log) {
                        super.showLog(log);
                        /*It shows logs of events inside the pdf generation process*/
                    }

                    @Override
                    public void onSuccess(SuccessResponse response) {
                        super.onSuccess(response);
                        /* If PDF is generated successfully then you will find SuccessResponse
                         * which holds the PdfDocument,File and path (where generated pdf is stored)*/

                    }
                });
    }
}