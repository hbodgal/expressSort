package com.example.myfirstproject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private EditText myEditText;
    private TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myEditText = findViewById(R.id.editTextTextMultiLine2);
        myTextView = findViewById(R.id.textView);
    }

    public void mySort(View view) {
        String finalString = "";
        try {
            String mystr = myEditText.getText().toString();
            // Clear the text in the EditText
            myEditText.setText("");
            String[] strArray = mystr.split(",");
            if (strArray.length >= 2 && strArray.length <= 8) {
                int[] arr = new int[strArray.length];
                finalString = "";

                // Create a list of different text colors
                int[] textColors = generateRedToGreenGradient(strArray.length);

                boolean validInput = true; // Flag to check if all inputs are valid

                for (int i = 0; i < strArray.length; i++) {
                    try {
                        int value = Integer.parseInt(strArray[i]);
                        if (value < 0 || value > 9) {
                            validInput = false;
                            break; // Exit the loop if an invalid value is encountered
                        }
                        arr[i] = value;
                    } catch (NumberFormatException e) {
                        validInput = false;
                        break; // Exit the loop if a non-integer value is encountered
                    }
                }

                if (validInput) {
                    int size = arr.length;

                    // Create a SpannableStringBuilder to build the colored text
                    SpannableStringBuilder builder = new SpannableStringBuilder();

                    for (int i = 1; i < size; ++i) {
                        int keyValue = arr[i];
                        int j = i - 1;
                        while (j >= 0 && arr[j] > keyValue) {
                            arr[j + 1] = arr[j];
                            j = j - 1;
                        }
                        arr[j + 1] = keyValue;

                        // Create a SpannableString for the current line with the specified text color
                        SpannableString spannableString = new SpannableString(Arrays.toString(arr).replaceAll(",", ","));

                        // Set the text color for the current line
                        spannableString.setSpan(new ForegroundColorSpan(textColors[i % textColors.length]), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        // Append the SpannableString to the builder
                        builder.append(spannableString);

                        // Append a newline character to separate lines
                        builder.append("\n");
                    }

                    // Set the colored text to the TextView
                    myTextView.setText(builder);

                    // Hide the keyboard
                    hideKeyboard();

                } else {
                    finalString = "Error: Please enter values between 0 to 9.";
                    myTextView.setText(finalString);
                }
            } else {
                finalString = "Error: Input size must be greater than or equal to 2 and less than or equal to 8.";
                myTextView.setText(finalString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to hide the keyboard
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
        }
    }

    // Generate an array of colors transitioning from red to green
    private int[] generateRedToGreenGradient(int count) {
        int[] colors = new int[count];
        for (int i = 0; i < count; i++) {
            // Interpolate between red (#FF0000) and green (#00FF00) in RGB color space
            int red = 255 - (i * 255 / (count - 1));
            int green = i * 255 / (count - 1);
            int color = Color.rgb(red, green, 0); // Create the color
            colors[i] = color;
        }
        return colors;
    }
}
