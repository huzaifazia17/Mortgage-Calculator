package com.example.mortgage_calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText principalInput, interestRateInput, amortizationInput;
    private Spinner frequencySpinner;
    private RadioGroup lumpSumGroup;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        principalInput = findViewById(R.id.mortgage_principal);
        interestRateInput = findViewById(R.id.interest_rate);
        amortizationInput = findViewById(R.id.amortization_period);
        frequencySpinner = findViewById(R.id.payment_frequency);
        lumpSumGroup = findViewById(R.id.lump_sum_group);
        calculateButton = findViewById(R.id.calculate_button);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Capture input values
                double principal = Double.parseDouble(principalInput.getText().toString());
                double interestRate = Double.parseDouble(interestRateInput.getText().toString());
                int amortizationPeriod = Integer.parseInt(amortizationInput.getText().toString());
                String paymentFrequency = frequencySpinner.getSelectedItem().toString();
                int lumpSumOption = lumpSumGroup.getCheckedRadioButtonId();
                String lumpSum = ((RadioButton) findViewById(lumpSumOption)).getText().toString();

                // Perform the mortgage calculation (simple interest formula example)
                double monthlyRate = (interestRate / 100) / 12;
                int numberOfPayments = amortizationPeriod * 12;
                double monthlyPayment = (principal * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -numberOfPayments));

                // Pass the data to the next activity
                Intent intent = new Intent(MainActivity.this, MortgageCalculationActivity.class);
                intent.putExtra("principal", principal);
                intent.putExtra("interestRate", interestRate);
                intent.putExtra("amortizationPeriod", amortizationPeriod);
                intent.putExtra("paymentFrequency", paymentFrequency);
                intent.putExtra("lumpSum", lumpSum);
                intent.putExtra("monthlyPayment", monthlyPayment);
                startActivity(intent);
            }
        });
    }
}
