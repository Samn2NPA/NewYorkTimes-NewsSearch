package samn.com.nytimessearch.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import samn.com.nytimessearch.R;

/**
 * Created by Samn on 27-Jun-17.
 */

public class SettingDialogFragment extends DialogFragment {

    private static final String TAG = SettingDialogListener.class.getSimpleName();
    private static final String DIALOG_TITLE = "title";

    private String dateBegin;
    private String sortType;
    private boolean isArt;
    private boolean isFashionStyle;
    private boolean isSports;

    @BindView(R.id.etDate)
    EditText etDate;
    @BindView(R.id.checkTv_art)
    CheckBox checkTv_art;
    @BindView(R.id.checkTv_FashionAndStyle)
    CheckBox checkTv_FashionAndStyle;
    @BindView(R.id.checkTv_sports)
    CheckBox checkTv_sports;
    @BindView(R.id.spinner_sort)
    Spinner spinner_sort;
    @BindView(R.id.btnSave)
    Button btnSave;

    public SettingDialogFragment(){
    }

    public static SettingDialogFragment newInstance(String title){
        SettingDialogFragment frag = new SettingDialogFragment();
        Bundle args = new Bundle();
        args.putString(DIALOG_TITLE, title);
        frag.setArguments(args);
        return frag;
    }

    public interface SettingDialogListener{
        void onFinishSettingDialog(String dateBegin, String sortType, boolean check_art,
                                   boolean check_fashionandstyle, boolean check_sport);
    }

    public void openDatePicker() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateListener =  new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateEtDate(calendar);
            }
        };

        new DatePickerDialog(getContext(), dateListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateEtDate(Calendar calendar) {
        String myFormat = "yyyyMMdd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dateBegin = dateFormat.format(calendar.getTime());
        String dateToShow = calendar.get(Calendar.YEAR) + "/" +
                (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
        etDate.setText(dateToShow);
    }

    private void setUpCheckBox(){
        CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean checked) {
                switch (view.getId()){
                    case R.id.checkTv_art:
                        isArt = checked;
                        Log.d(TAG, "isArt: " + checked);
                        break;
                    case R.id.checkTv_FashionAndStyle:
                        isFashionStyle = checked;
                        Log.d(TAG, "isFashionStyle: " + checked);
                        break;
                    case R.id.checkTv_sports:
                        isSports = checked;
                        Log.d(TAG, "checkTv_sports: " + checked);
                        break;
                }
            }
        };

        checkTv_art.setOnCheckedChangeListener(checkListener);
        checkTv_FashionAndStyle.setOnCheckedChangeListener(checkListener);
        checkTv_sports.setOnCheckedChangeListener(checkListener);
    }

    private void setUpSpinner(){
        spinner_sort.setAdapter(ArrayAdapter.createFromResource(getContext(),R.array.sort_array, R.layout.spinner_item));
        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortType = parent.getSelectedItem().toString();
                Log.d(TAG, "DIALOG :: spinner = " + sortType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });

        setUpSpinner();
        setUpCheckBox();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DIALOG", "btnSave CLICK");
                SettingDialogListener listener = (SettingDialogListener) getActivity();
                listener.onFinishSettingDialog(dateBegin, sortType, isArt, isFashionStyle, isSports);

                Log.d(TAG, "DIALOG:: " +sortType + " || " + dateBegin + " || " + isArt + " || " + isFashionStyle + " || " + isSports);
                dismiss();
            }
        });
        //set title to dialog
        String title = getArguments().getString(DIALOG_TITLE, "Setting search");
        getDialog().setTitle(title);
    }



}
