package za.co.codetribe.shoptodrink;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import butterknife.ButterKnife;

public class EnterProfileActivity extends AppCompatActivity {

    private EditText etName, etSurname,etIDNo,etCell;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_profile);

        etName = ButterKnife.findById(this,R.id.etName);
        etSurname = ButterKnife.findById(this,R.id.etSurname);
        etIDNo = ButterKnife.findById(this,R.id.etIDNo);
        etCell = ButterKnife.findById(this,R.id.etCell);
    }




    public void onSubmit(View view)
    {


        if("".equals(etName.getText().toString()))
        {
            Toast.makeText(context,"Enter Name",Toast.LENGTH_SHORT).show();
        }else if("".equals(etSurname.getText().toString()))
        {
            Toast.makeText(context,"Enter Surname",Toast.LENGTH_SHORT).show();
        }else if("".equals(etIDNo.getText().toString()))
        {

            Toast.makeText(context,"Enter IDNo",Toast.LENGTH_SHORT).show();
        }else if("".equals(etCell.getText().toString()))
        {
            Toast.makeText(context,"Enter Cellphone Number",Toast.LENGTH_SHORT).show();
        }else  if(Long.parseLong(etIDNo.getText().toString()) != 13)
        {

            Toast.makeText(context,"Incorrect IDNo",Toast.LENGTH_SHORT).show();

        }else
        {



        }


    }
}
