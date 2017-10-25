package com.comp3004.goodbyeworld.tournamentmaster.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.comp3004.goodbyeworld.tournamentmaster.R;
import com.comp3004.goodbyeworld.tournamentmaster.data.DataSource;

public class EditOrgActivity extends AppCompatActivity {

    DataSource source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_org);

        source = new DataSource(this);
        source.open();

        Button button = (Button)findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameText = (EditText) findViewById(R.id.org_name);
                String name = nameText.getText().toString();
                if (name.trim().length() > 0) {
                    source.createOrg(name, 0);
                    Intent intent = new Intent(EditOrgActivity.this, OrgActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
