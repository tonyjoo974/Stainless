package edu.illinois.cs465.stainless;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;


public class BottomSheetFragment extends BottomSheetDialogFragment {
    private View view;
    private String myString;
    private String stockStatus;
    private TextView materialName;
    private Button buttonText;
    private int thumbnail;
    private ImageView materialThumbnail;

    public static BottomSheetFragment newInstance() {
        return new BottomSheetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.material_modal, container, false);

        materialName = view.findViewById(R.id.materialName);
        materialThumbnail = view.findViewById(R.id.materialThumbnail);
        buttonText = view.findViewById(R.id.stockButton);

        Bundle data = getArguments();
        if (data != null) {
            myString = data.getString("materialName");
            thumbnail = data.getInt("thumbnail");
            stockStatus = data.getString("stockStatus");
        }
        materialName.setText(myString);
        materialThumbnail.setImageResource(thumbnail);
        buttonText.setText(stockStatus);

        Button exitButton = (Button) view.findViewById(R.id.exitModalButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }
}
