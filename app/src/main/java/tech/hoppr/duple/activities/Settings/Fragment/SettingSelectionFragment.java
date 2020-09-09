package tech.hoppr.duple.activities.Settings.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import tech.hoppr.duple.R;

public class SettingSelectionFragment extends DialogFragment {

    private EditText mZipcodeField;
    public OnClickListener mOnClickListener;

    public interface OnClickListener {
        void getInput(String input);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_zipcode, container, false);
        mZipcodeField = view.findViewById(R.id.zipcode_field);
        TextView mOkayButton = view.findViewById(R.id.dialog_okay_button);
        TextView mCancelButton = view.findViewById(R.id.dialog_cancel_button);

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        mOkayButton.setOnClickListener(new View.OnClickListener() {
            String input;

            @Override
            public void onClick(View v) {
                // Prevent OK click if dialog field is empty
                if (!mZipcodeField.getText().toString().equals("")) {
                    input = mZipcodeField.getText().toString();
                    mOnClickListener.getInput(input);
                    Objects.requireNonNull(getDialog()).dismiss();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mOnClickListener = (OnClickListener) getActivity();
        } catch (ClassCastException e) {
            Log.e("Error", Objects.requireNonNull(e.getMessage()));
        }
    }
}
