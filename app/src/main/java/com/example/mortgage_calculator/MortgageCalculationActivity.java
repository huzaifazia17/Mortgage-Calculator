package com.example.mortgage_calculator;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MortgageCalculationActivity extends AppCompatActivity {

    private TextView principalText, interestRateText, amortizationText, frequencyText, lumpSumText, monthlyPaymentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mortgage_calculation);

        // Initialize views
        principalText = findViewById(R.id.display_principal);
        interestRateText = findViewById(R.id.display_interest_rate);
        amortizationText = findViewById(R.id.display_amortization);
        frequencyText = findViewById(R.id.display_frequency);
        lumpSumText = findViewById(R.id.display_lump_sum);
        monthlyPaymentText = findViewById(R.id.monthly_payment);

        // Get intent data from the MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double principal = extras.getDouble("principal");
            double interestRate = extras.getDouble("interestRate");
            int amortizationPeriod = extras.getInt("amortizationPeriod");
            String paymentFrequency = extras.getString("paymentFrequency");
            String lumpSum = extras.getString("lumpSum");
            double monthlyPayment = extras.getDouble("monthlyPayment");

            // Display the values
            principalText.setText("Principal Amount: $" + principal);
            interestRateText.setText("Interest Rate: " + interestRate + "%");
            amortizationText.setText("Amortization Period: " + amortizationPeriod + " years");
            frequencyText.setText("Payment Frequency: " + paymentFrequency);
            lumpSumText.setText("Lump Sum Option: " + lumpSum);
            monthlyPaymentText.setText("Estimated Monthly Payment: $" + String.format("%.2f", monthlyPayment));
        }
    }
}
