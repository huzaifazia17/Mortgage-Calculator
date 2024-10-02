package com.example.mortgage_calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MortgageCalculationActivity extends AppCompatActivity {

    private TextView principalText, interestRateText, amortizationText, frequencyText, lumpSumOptionText, lumpSumAmountText, monthlyPaymentText;
    private Button calculateAgainButton;

    // When the activity is initialized, this method will initialize all the views, will get the intent data from the main page, display the values in the xml file
    // and setup the return button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mortgage_calculation);

        // Views
        principalText = findViewById(R.id.display_principal);
        interestRateText = findViewById(R.id.display_interest_rate);
        amortizationText = findViewById(R.id.display_amortization);
        frequencyText = findViewById(R.id.display_frequency);
        lumpSumOptionText = findViewById(R.id.display_lump_sum_option);
        lumpSumAmountText = findViewById(R.id.display_lump_sum_amount);
        monthlyPaymentText = findViewById(R.id.display_monthly_payment);
        calculateAgainButton = findViewById(R.id.calculate_again_button);

        // Get the intent data from MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double principal = extras.getDouble("principal");
            double interestRate = extras.getDouble("interestRate");
            int amortizationPeriod = extras.getInt("amortizationPeriod");
            String paymentFrequency = extras.getString("paymentFrequency");
            String lumpSumOption = extras.getString("lumpSum");
            double lumpSumAmount = extras.getDouble("lumpSumAmount", 0.0);
            double monthlyPayment = extras.getDouble("monthlyPayment");

            // Display the values
            principalText.setText("" + principal);
            interestRateText.setText(interestRate + "%");
            amortizationText.setText(amortizationPeriod + " years");
            frequencyText.setText(paymentFrequency);
            lumpSumOptionText.setText(lumpSumOption);

            // Display lump sum amount if applicable
            if (lumpSumOption.equals("")) {
                lumpSumAmountText.setText("" + "    $" + lumpSumAmount);
                lumpSumAmountText.setVisibility(View.VISIBLE);  // Make the text view visible
            }

            monthlyPaymentText.setText("$" + String.format("%.2f", monthlyPayment));
        }

        // button to go back to the main activity
        calculateAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MortgageCalculationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
