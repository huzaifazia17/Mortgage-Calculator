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

        // Initialize the views
        principalInput = findViewById(R.id.mortgage_principal);
        interestRateInput = findViewById(R.id.interest_rate);
        amortizationInput = findViewById(R.id.amortization_period);
        frequencySpinner = findViewById(R.id.payment_frequency);
        lumpSumGroup = findViewById(R.id.lump_sum_group);
        lumpSumAmountInput = findViewById(R.id.lump_sum_amount);
        calculateButton = findViewById(R.id.calculate_button);

        // Method to handle if radio button is selected for lump sump payments
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

        // On click method for the calculate mortgage button. On click the method will validate that all input fields
        // have entries in them and will pass on the values to the next page using the built in Intent functions.
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check the inputs
                if (principalInput.getText().toString().isEmpty() ||
                        interestRateInput.getText().toString().isEmpty() ||
                        amortizationInput.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check radio button selection
                int lumpSumOption = lumpSumGroup.getCheckedRadioButtonId();
                if (lumpSumOption == -1) {
                    Toast.makeText(MainActivity.this, "Please select a lump-sum option", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Capture the input values
                double principal = Double.parseDouble(principalInput.getText().toString());
                double interestRate = Double.parseDouble(interestRateInput.getText().toString());
                int amortizationPeriod = Integer.parseInt(amortizationInput.getText().toString());
                String paymentFrequency = frequencySpinner.getSelectedItem().toString();
                double lumpSumAmount = 0;

                // If lump sum is selected, validate it and capture the value
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

                // EMI calculation
                double monthlyRate = (interestRate / 100) / 12;
                int numberOfPayments = amortizationPeriod * 12;
                double monthlyPayment = (principal * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -numberOfPayments));


                // Pass info to the next activity (mortgage calculation)
                Intent intent = new Intent(MainActivity.this, MortgageCalculationActivity.class);
                intent.putExtra("principal", principal);
                intent.putExtra("interestRate", interestRate);
                intent.putExtra("amortizationPeriod", amortizationPeriod);
                intent.putExtra("paymentFrequency", paymentFrequency);
                intent.putExtra("lumpSum", lumpSumOption == R.id.lump_sum_yes ? "" : "$0");
                intent.putExtra("lumpSumAmount", lumpSumAmount);  // Pass lump-sum amount if applicable
                intent.putExtra("monthlyPayment", monthlyPayment);
                startActivity(intent);
            }
        });
    }
}
