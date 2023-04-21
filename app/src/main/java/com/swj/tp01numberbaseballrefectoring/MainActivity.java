package com.swj.tp01numberbaseballrefectoring;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;

import com.swj.tp01numberbaseballrefectoring.databinding.ActivityMainBinding;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    int com100, com10, com1;
    int tryNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Random rnd = new Random();
        com100 = rnd.nextInt(9) + 1;
        com10 = rnd.nextInt(9) + 1;
        com1 = rnd.nextInt(9) + 1;

        while(com100 == com10) {
            com10 = rnd.nextInt(9) + 1;
        }

        while(com100 == com1 || com10 == com1) {
            com1 = rnd.nextInt(9) + 1;
        }

        binding.su100.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 1) binding.su10.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        binding.su10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 1) binding.su1.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });


        binding.btnRetry.setOnClickListener(view -> {
            binding.result.setText("");
            binding.btn.setVisibility(View.VISIBLE);
            binding.btnRetry.setVisibility(View.GONE);
        });


        binding.btn.setOnClickListener(view -> {
            if(binding.su100.getText().toString().equals("") ||
               binding.su10.getText().toString().equals("")  ||
               binding.su1.getText().toString().equals("")) {
                new AlertDialog.Builder(this).setMessage("숫자를 입력하세요")
                        .setPositiveButton("OK", null).show();
                return;
            }

            int num100 = Integer.parseInt(binding.su100.getText().toString());
            int num10 = Integer.parseInt(binding.su10.getText().toString());
            int num1 = Integer.parseInt(binding.su1.getText().toString());
            int strike = 0, ball = 0;

            if(num100==com100) strike++;
            if(num10==com10) strike++;
            if(num1==com1) strike++;

            if((num100 == com10) || (num100 == com1)) ball++;
            if((num10 == com100) || (num10 == com1)) ball++;
            if((num1 == com100)  || (num1 == com10)) ball++;

            if(strike != 3) {
                binding.result.append("" + num100);
                binding.result.append("" + num10);
                binding.result.append("" + num1 + " : ");
                binding.result.append("" + strike + " strike, " );
                binding.result.append("" + ball + " ball \n" );
                tryNum++;
            } else {
                String s = tryNum +"회 만에 정답!\n축하합니다.\n정답입니다 ~";
                binding.result.setText(s);
                binding.btn.setVisibility(View.GONE);
                binding.btnRetry.setVisibility(View.VISIBLE);
                tryNum = 0;
            }

            binding.su100.setText("");
            binding.su10.setText("");
            binding.su1.setText("");
            binding.su100.requestFocus();
        });
    }
}