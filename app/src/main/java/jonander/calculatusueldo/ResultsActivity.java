package jonander.calculatusueldo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.google.gson.Gson;

public class ResultsActivity extends AppCompatActivity {


    private TextView et_sueldo_anual;
    private TextView et_irpf;
    private TextView et_impuestos_sociales;
    private TextView et_ingresos_mensuales;
    private TextView et_paga_extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Gson gson = new Gson();
        String studentDataObjectAsAString = getIntent().getStringExtra("budget");
        Budget studentDataObject = gson.fromJson(studentDataObjectAsAString, Budget.class);


        // Set properties
        et_sueldo_anual = findViewById(R.id.sueldo_anual);
        et_impuestos_sociales = findViewById(R.id.impuestos_sociales);
        et_ingresos_mensuales = findViewById(R.id.ingresos_mensuales);
        et_paga_extra = findViewById(R.id.paga_extra);
        et_irpf = findViewById(R.id.irpf);

        setObjects( studentDataObject);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    private void setObjects ( Budget student) {

        et_sueldo_anual.setText(String.valueOf(student.getAnnual()));
        et_irpf.setText(String.valueOf(student.getIrpf()));
        et_impuestos_sociales.setText(String.valueOf(student.getTaxes()));
        et_ingresos_mensuales.setText(String.valueOf(student.getMonthly()));
        et_paga_extra.setText(String.valueOf(student.getAdd_mouth()));

    }

}
