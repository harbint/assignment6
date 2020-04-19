package com.example.assignment5_2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    SQLDatabase database;
    TextView currentBalance;
    //EditText date;
    EditText editDay;
    EditText editMonth;
    EditText editYear;
    EditText price;
    EditText dayFloor;
    EditText monthFloor;
    EditText yearFloor;
    EditText dayCeil;
    EditText monthCeil;
    EditText yearCeil;
    EditText priceFloor;
    EditText priceCeil;
    EditText category;
    Button button;
    Button button2;
    TableLayout history;
    DecimalFormat format = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new SQLDatabase(this);
        currentBalance = findViewById(R.id.currentBalance);
        //date = findViewById(R.id.date);
        editDay = findViewById(R.id.editDay);
        editMonth = findViewById(R.id.editMonth);
        editYear = findViewById(R.id.editYear);
        dayFloor = findViewById(R.id.editDayFloor);
        monthFloor = findViewById(R.id.editMonthFloor);
        yearFloor = findViewById(R.id.editYearFloor);
        dayCeil = findViewById(R.id.editDayCeil);
        monthCeil = findViewById(R.id.editMonthCeil);
        yearCeil = findViewById(R.id.editYearCeil);
        priceFloor = findViewById(R.id.editPriceFloor);
        priceCeil = findViewById(R.id.editPriceCeil);

        price = findViewById(R.id.price);

        category = findViewById(R.id.item);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        history = findViewById(R.id.history);
        Cursor result = database.retrieveAll();
        accessTable(result, false);
        onClick();
        applyFilter();
    }

    public void onClick(){
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = Double.parseDouble(MainActivity.this.price.getText().toString());
                        SQLDatabase.rowData model = new SQLDatabase.rowData();
                        String d = editDay.getText().toString();
                        String m = editMonth.getText().toString();
                        String y = editYear.getText().toString();
                        model.mDate =  dateGen(d, m, y);
                        model.mItem = category.getText().toString();
                        model.mPrice = price;
                        database.addRow(model);
                        Cursor result = database.retrieveAll();
                        accessTable(result, false);
                    }
                }
        );
    }

    public void accessTable(Cursor result, boolean filtered){
        if (result == null){
            return;
        }

        int counter = history.getChildCount();
        for (int i = 1; i < counter; i++) {
            history.removeViewAt(1);
        }
        Double balance = 0.0;
        while(result.moveToNext()){
            TableRow row = new TableRow(this);
            TableRow.LayoutParams columnLayout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            columnLayout.weight = 1;
            TextView date = new TextView(this);
            date.setLayoutParams(columnLayout);
            date.setText(result.getString(2));
            row.addView(date);
            TextView price = new TextView(this);
            price.setLayoutParams(columnLayout);
            price.setText(result.getString(3));
            row.addView(price);
            TextView category = new TextView(this);
            category.setLayoutParams(columnLayout);
            category.setText(result.getString(1));
            row.addView(category);
            history.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            Double price2 = Double.parseDouble(result.getString(3));
            balance += price2;
        }
        if (!filtered) {
            MainActivity.this.currentBalance.setText("Balance = $" + format.format(balance));
        }
    }

    public String dateGen(String d, String m, String y){

            int intD = Integer.parseInt(d);
            int intM = Integer.parseInt(m);

            if (intD < 10 && intM >= 10) {
                return y + "-" + m + "-0" + d;
            }

            else if (intD >= 10 && intM < 10){
                return y + "-0" + m + "-" + d;
            }

            else if (intD < 10 && intM < 10){
                return y + "-0" + m + "-0" + d;
            }

            else {
                return y + "-" + m + "-" + d;
            }
    }

    public void applyFilter(){
        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String priceFloorS = priceFloor.getText().toString();
                        String priceCeilS = priceCeil.getText().toString();
                        String d = dayFloor.getText().toString();
                        String m = monthFloor.getText().toString();
                        String y = yearFloor.getText().toString();
                        String dateFloor = dateGen(d, m, y);
                        d = dayCeil.getText().toString();
                        m = monthCeil.getText().toString();
                        y = yearCeil.getText().toString();
                        String dateCeil = dateGen(d, m, y);
                        Cursor result = database.filterDB(priceFloorS, priceCeilS, dateFloor, dateCeil);
                        accessTable(result, true);
                    }
                }
        );
    }

}
