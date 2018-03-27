package jonander.calculatusueldo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;


public class AnswersFragment extends Fragment {

    private EditText eT_money;
    private Spinner s_n_payments;
    private CheckBox checkBox;
    private EditText eT_irpf;
    private Button calculate ;

    // var for calculate
    private int payments = 0;
    private int money = 0;
    private double irpf;

    public AnswersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_answers, container, false);

        s_n_payments = view.findViewById(R.id.n_payments);
        eT_money = view.findViewById(R.id.money);
        checkBox = view.findViewById(R.id.checkBox);
        eT_irpf = view.findViewById(R.id.irpf);

        events(view);


        return view;
    }

    private void events(View view) {


        s_n_payments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                payments = Integer.parseInt(s_n_payments.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        eT_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ( !editable.toString().isEmpty() ){
                    try {
                        money = Integer.parseInt(editable.toString());
                    }catch (Exception ex) {
                        money = 0;
                    }
                }

            }
        });
        eT_irpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (  !editable.toString().isEmpty() ){
                    try {
                        irpf = Double.parseDouble(editable.toString());
                    }catch (Exception ex) {
                        irpf = 0;
                    }
                }

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                calculate.setVisibility(View.VISIBLE);
            }
        });

        calculate = (Button) view.findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( checkBox.isChecked() && money != 0 && irpf != 0 )
                    checkButton(view);
            }
        });
    }

    private void checkButton (View view) {
        Budget budget = new Budget();
        budget.monthly = money / payments;
        budget.annual = money;
        budget.quotation = budget.monthly +( budget.monthly *2)/12;


        budget.contingencies = ( budget.quotation * 4.7 ) / 100;
        budget.unemployment = (budget.quotation * 1.55 ) / 100;
        budget.fp = ( budget.quotation * 0.1  ) / 100;
        budget.taxes = budget.contingencies + budget.unemployment + budget.fp;

        // IRPF
        budget.irpf = ( irpf * budget.monthly ) / 100 ;

        if ( payments == 14 ) {
            budget.extra = budget.monthly - ( budget.monthly * budget.irpf ) / 100 ;
            budget.add_mouth = ( budget.monthly * 14 ) / 12;
        }


        Gson gson = new Gson();
        String budgetJSON = gson.toJson(budget);

        Intent intent = new Intent(view.getContext(), ResultsActivity.class);
        intent.putExtra("budget", budgetJSON);
        startActivity(intent);

    }




}
