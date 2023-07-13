package com.ferzal.monitoring.uscis;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ferzal.monitoring.uscis.service.USCISService;
import com.google.gson.GsonBuilder;

import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {

    private EditText workPermitText;
    private EditText residenceApplicationText;
    private TextView workPermitTextView;
    private TextView residenceApplicationTextView;
    private RelativeLayout progressBar;

    private USCISService uscisService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.linkReviewButton();
        this.workPermitText = findViewById(R.id.work_permit_text);
        this.residenceApplicationText = findViewById(R.id.residence_application_text);
        this.workPermitTextView = findViewById(R.id.work_permit_text_view);
        this.residenceApplicationTextView = findViewById(R.id.residence_application_text_view);
        this.progressBar = findViewById(R.id.loading_panel);
        this.progressBar.setVisibility(View.GONE);
        this.uscisService = new USCISService();
    }

    private void linkReviewButton() {
        Button reviewButton = findViewById(R.id.review_button);
        reviewButton.setOnClickListener(this::updateUSCISInformation);
    }

    private void updateUSCISInformation(View view) {
        String workCaseNumber = workPermitText.getText().toString();
        this.updateUSCISInformation(workCaseNumber, workPermitTextView);

        String residenceApplicationCaseNumber = residenceApplicationText.getText().toString();
        this.updateUSCISInformation(residenceApplicationCaseNumber, residenceApplicationTextView);
    }



    private void updateUSCISInformation(String caseNumber, TextView textView) {
         progressBar.setVisibility(View.VISIBLE);
        CompletableFuture.supplyAsync(() -> uscisService.getCaseInformation(caseNumber))
                .thenApplyAsync(new GsonBuilder().setPrettyPrinting().create()::toJson)
                .thenAccept(result -> runOnUiThread(() -> {
                    textView.setText(result);
                    progressBar.setVisibility(View.GONE);
                }))
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                });


    }


}