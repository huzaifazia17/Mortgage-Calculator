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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText principalInput, interestRateInput, amortizationInput, lumpSumAmountInput;
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
        lumpSumAmountInput = findViewById(R.id.lump_sum_amount);
        calculateButton = findViewById(R.id.calculate_button);

        // Handle lump-sum radio button selection
        lumpSumGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.lump_sum_yes) {
                    lumpSumAmountInput.setVisibility(View.VISIBLE); // Show lump sum input
                } else {
                    lumpSumAmountInput.setVisibility(View.GONE); // Hide lump sum input
                }
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate inputs
                if (principalInput.getText().toString().isEmpty() ||
                        interestRateInput.getText().toString().isEmpty() ||
                        amortizationInput.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate radio button selection
                int lumpSumOption = lumpSumGroup.getCheckedRadioButtonId();
                if (lumpSumOption == -1) {
                    Toast.makeText(MainActivity.this, "Please select a lump-sum option", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Capture input values
                double principal = Double.parseDouble(principalInput.getText().toString());
                double interestRate = Double.parseDouble(interestRateInput.getText().toString());
                int amortizationPeriod = Integer.parseInt(amortizationInput.getText().toString());
                String paymentFrequency = frequencySpinner.getSelectedItem().toString();
                double lumpSumAmount = 0;

                // If lump sum is selected, validate and capture its value
                if (lumpSumOption == R.id.lump_sum_yes) {
                    if (lumpSumAmountInput.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter lump sum amount", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    lumpSumAmount = Double.parseDouble(lumpSumAmountInput.getText().toString());
                    if (lumpSumAmount >= principal) {
                        Toast.makeText(MainActivity.this, "Lump sum must be less than principal", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                double monthlyRate = (interestRate / 100) / 12;
                int numberOfPayments = amortizationPeriod * 12;
                double monthlyPayment = (principal * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -numberOfPayments));


                // Pass the data to the next activity
                Intent intent = new Intent(MainActivity.this, MortgageCalculationActivity.class);
                intent.putExtra("principal", principal);
                intent.putExtra("interestRate", interestRate);
                intent.putExtra("amortizationPeriod", amortizationPeriod);
                intent.putExtra("paymentFrequency", paymentFrequency);
                intent.putExtra("lumpSum", lumpSumOption == R.id.lump_sum_yes ? "Yes" : "No");
                intent.putExtra("lumpSumAmount", lumpSumAmount);  // Pass lump-sum amount if applicable
                intent.putExtra("monthlyPayment", monthlyPayment);
                startActivity(intent);
            }
        });
    }
}
