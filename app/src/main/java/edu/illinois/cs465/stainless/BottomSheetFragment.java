package edu.illinois.cs465.stainless;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

    private static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static BottomSheetFragment newInstance() {
        return new BottomSheetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.material_modal, container, false);

        view.setBackgroundColor(Color.TRANSPARENT);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(500)));

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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = (FrameLayout) d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                assert bottomSheet != null;
                bottomSheet.setBackgroundResource(android.R.color.transparent);
                BottomSheetBehavior behaviour = BottomSheetBehavior.from(bottomSheet);
                behaviour.setPeekHeight(dpToPx(225));

                behaviour.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        switch (newState) {
                            case STATE_COLLAPSED:
                                ((NestedScrollView)getView().findViewById(R.id.text_root)).fullScroll(View.FOCUS_UP);
                                ((NestedScrollView)getView().findViewById(R.id.text_root)).smoothScrollTo(0, 0);

                                getView().findViewById(R.id.note).setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                        getView().findViewById(R.id.note).setVisibility(View.INVISIBLE);
                        if (slideOffset > 0) {
                            // resize invisible placeholder
                            LinearLayout placeholder = ((LinearLayout)getView().findViewById(R.id.placeholder));
                            ViewGroup.LayoutParams params = placeholder.getLayoutParams();
                            int maxDp = 70;
                            params.height = (int) (dpToPx(maxDp) * (1 - slideOffset));
                            placeholder.setLayoutParams(params);

                            // resize cardview
                            CardView realThumbnail = ((CardView)getView().findViewById(R.id.realThumbnail));
                            maxDp = 200;
                            int minDp = 125;
                            params = realThumbnail.getLayoutParams();
                            int newPx = dpToPx((int) (minDp + (1 - slideOffset) * (maxDp - minDp)));
                            params.height = newPx;
                            params.width = newPx;
                            realThumbnail.setLayoutParams(params);

                            // change cardview elevation
                            maxDp = 2;
                            minDp = 0;
                            newPx = dpToPx((int) (minDp + (1 - slideOffset) * (maxDp - minDp)));
                            realThumbnail.setElevation(newPx);

                            // change corner radius
                            View modal = getView().findViewById(R.id.modal);
                            maxDp = 20;
                            minDp = 0;
                            newPx = dpToPx((int) (minDp + slideOffset * (maxDp - minDp)));
                            GradientDrawable shape =  new GradientDrawable();
                            shape.setColor(0xffffffff);
                            shape.setCornerRadii(new float[]{newPx, newPx, newPx, newPx, 0, 0, 0, 0});
                            modal.setBackground(shape);
                        }
                    }
                });
            }
        });
        return dialog;
    }
}
