package samn.com.nytimessearch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import samn.com.nytimessearch.R;

/**
 * Created by Samn on 27-Jun-17.
 */

public class SettingDialogFragment extends DialogFragment {

    private static final String DIALOG_TITLE = "title";

    @BindView(R.id.datePicker) DatePicker datePicker;
    @BindView(R.id.checkTv_art) CheckBox checkTv_art;
    @BindView(R.id.checkTv_FashionAndStyle) CheckBox checkTv_FashionAndStyle;
    @BindView(R.id.checkTv_sports) CheckBox checkTv_sports;
    @BindView(R.id.spinner_sort) Spinner spinner_sort;
    @BindView(R.id.btnSave) Button btnSave;

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
        void onFinishSettingDialog(String spinner_value, String datetime, boolean check_art,
                                   boolean check_fashionandstyle, boolean check_sport);
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

        ButterKnife.bind(view);
        spinner_sort.setAdapter(ArrayAdapter.createFromResource(getContext(),R.array.sort_array, R.layout.spinner_item));
        final String spinner_value = spinner_sort.getSelectedItem().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();

        final String datetime = String.format("%d%d%d", year, month, day);
        Log.d("DIALOG PARSE DATA:: ", datetime);

        final boolean isArt = checkTv_art.isChecked();
        final boolean isFashionStyle = checkTv_FashionAndStyle.isChecked();
        final boolean isSports = checkTv_sports.isChecked();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DIALOG", "btnSave CLICK");
                SettingDialogListener listener = (SettingDialogListener) getActivity();
                listener.onFinishSettingDialog(spinner_value,datetime, isArt, isFashionStyle, isSports);
            }
        });
        //set title to dialog
        String title = getArguments().getString(DIALOG_TITLE, "Setting search");
        getDialog().setTitle(title);
    }



}
