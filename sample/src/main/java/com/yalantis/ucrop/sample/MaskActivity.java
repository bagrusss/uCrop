package com.yalantis.ucrop.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.yalantis.ucrop.view.CropImageView;
import com.yalantis.ucrop.view.OverlayView;
import com.yalantis.ucrop.view.UCropView;

import java.util.ArrayList;
import java.util.List;

public class MaskActivity extends AppCompatActivity {

    private static final String KEY_PATH = "PATH";

    private UCropView cropView;

    private List<OverlayView.PreviewForm> forms = new ArrayList<>(6);
    private int current = 0;

    {
        forms.add(OverlayView.PreviewForm.CIRCLE);
        forms.add(OverlayView.PreviewForm.SQUARE);
        forms.add(OverlayView.PreviewForm.HEXAGON);
        forms.add(OverlayView.PreviewForm.ROUND_SQUARE);
        forms.add(OverlayView.PreviewForm.SQUIRCLE_ROMB);
        forms.add(OverlayView.PreviewForm.SQUIRCLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        cropView = (UCropView) findViewById(R.id.crop_view);

        initCrop();

        findViewById(R.id.next_form).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current = ++current % forms.size();
                cropView.getOverlayView().setPreviewForm(forms.get(current));
            }
        });
    }

    private void initCrop() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int res = Math.round(70 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

        OverlayView overlayView = cropView.getOverlayView();
        overlayView.setShowCropGrid(false);
        overlayView.setDimmedColor(Color.parseColor("#CCFAFAFA"));
        overlayView.setShowCropFrame(false);
        current = 0;
        overlayView.setPreviewForm(forms.get(current));
        overlayView.setPadding(res, res, res, res);


        CropImageView cropImageView = cropView.getCropImageView();
        String path = getIntent().getStringExtra(KEY_PATH);
        cropImageView.setTargetAspectRatio(1f);

        try {
            cropImageView.setImageUri(Uri.parse(path), null);
        } catch (Exception ignored) {

        }
    }

    public static void start(Activity activity, String path) {
        Intent intent = new Intent(activity, MaskActivity.class);
        intent.putExtra(KEY_PATH, path);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.show_up, R.anim.show_down);
    }
}
