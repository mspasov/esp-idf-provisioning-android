package com.espressif.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.espressif.AppConstants;
import com.espressif.provision.Provision;
import com.espressif.provision.R;

// TODO start in only Sec1 condition
public class ProofOfPossessionActivity extends AppCompatActivity {

    private static final String TAG = "Espressif::" + ProofOfPossessionActivity.class.getSimpleName();

    private Button btnNext;
    private EditText etDeviceKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_pop);
        setSupportActionBar(toolbar);

        btnNext = findViewById(R.id.btn_next);
        etDeviceKey = findViewById(R.id.et_pop);
        btnNext.setEnabled(false);
        btnNext.setAlpha(0.5f);
        btnNext.setOnClickListener(nextBtnClickListener);

        etDeviceKey.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String pop = s.toString();

                if (TextUtils.isEmpty(pop)) {

                    btnNext.setEnabled(false);
                    btnNext.setAlpha(0.5f);

                } else {

                    btnNext.setEnabled(true);
                    btnNext.setAlpha(1f);
                }
            }
        });
    }

    private View.OnClickListener nextBtnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            final String pop = etDeviceKey.getText().toString();
            Log.e(TAG, "POP : " + pop);

            if (BLEProvisionLanding.bleTransport.deviceCapabilities.contains("wifi_scan")) {
                goToWiFiScanListActivity();
            } else {
                goToProvisionActivity();
            }
        }
    };

    private void goToWiFiScanListActivity() {

        Intent launchWiFiScanList = new Intent(getApplicationContext(), WiFiScanActivity.class);
        launchWiFiScanList.putExtras(getIntent());
        launchWiFiScanList.putExtra(AppConstants.KEY_PROOF_OF_POSSESSION, etDeviceKey.getText().toString());
        startActivity(launchWiFiScanList);
    }

    private void goToProvisionActivity() {

        Intent launchProvisionInstructions = new Intent(getApplicationContext(), ProvisionActivity.class);
        launchProvisionInstructions.putExtras(getIntent());
        launchProvisionInstructions.putExtra(AppConstants.KEY_PROOF_OF_POSSESSION, etDeviceKey.getText().toString());
        startActivityForResult(launchProvisionInstructions, Provision.REQUEST_PROVISIONING_CODE);
    }
}