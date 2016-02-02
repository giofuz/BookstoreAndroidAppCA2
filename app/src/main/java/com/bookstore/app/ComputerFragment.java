package com.bookstore.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class ComputerFragment extends Fragment {

    private static final String FRAGMENT_COMPUTER_ID = "book_id";

    private int mComputerId;
    private EditText mMakeField;
    private EditText mModelField;
    private EditText mOsField;
    private EditText mQuantity;
    private EditText mPriceField;
    private EditText mStudentIDField;
    private Computer mComputer;
    private boolean mEditMode;

    public static ComputerFragment newInstance(int bookId) {
        ComputerFragment fragment = new ComputerFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_COMPUTER_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mComputerId = getArguments().getInt(FRAGMENT_COMPUTER_ID);
        }
        CompterStore store = CompterStore.getInstance();
        List<Computer> computers = store.getBooks();

        if (mComputerId != -1) {
            mComputer = null;
            for (int i = 0; i != computers.size(); i++) {
                Computer b = computers.get(i);
                if (b.getId() == mComputerId) {
                    mComputer = b;
                    break;
                }
            }

        }
        else {
            mComputer = null;
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_book, container, false);

        mMakeField = (EditText) fragment.findViewById(R.id.computer_make_editText);
        mModelField = (EditText) fragment.findViewById(R.id.computer_model_editText);
        mOsField = (EditText) fragment.findViewById(R.id.computer_os_editText);
        mQuantity = (EditText) fragment.findViewById(R.id.computer_quantity_editText);
        mPriceField = (EditText) fragment.findViewById(R.id.computer_price_editText);

        populateForm();

        if (mComputer == null) {
            setEditMode(true);
        }
        else {
            setEditMode(false);
        }

        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.book, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem edit = menu.findItem(R.id.action_edit);

        if (mEditMode) {
            edit.setIcon(R.drawable.ic_action_accept);
            edit.setTitle(R.string.action_save);
        }
        else {
            edit.setIcon(R.drawable.ic_action_edit);
            edit.setTitle(R.string.action_edit);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit: {
                if (mEditMode) {
                    Toast.makeText(getActivity(), "Save selected", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(), "Edit selected", Toast.LENGTH_LONG).show();
                }
                setEditMode(!mEditMode);

                getActivity().invalidateOptionsMenu();
                return false;
            }
            case R.id.action_delete: {
                Toast.makeText(getActivity(), "Delete selected", Toast.LENGTH_LONG).show();

                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEditMode(boolean editMode) {
        mEditMode = editMode;
        mMakeField.setEnabled(editMode);
        mModelField.setEnabled(editMode);
        mOsField.setEnabled(editMode);
        mQuantity.setEnabled(editMode);
        mPriceField.setEnabled(editMode);
    }

    private void populateForm() {
        if (mComputer != null) {
            mMakeField.setText(mComputer.getMake());
            mModelField.setText(mComputer.getModel());
            mOsField.setText(mComputer.getOs());
            mQuantity.setText(mComputer.getQuantity());
            mPriceField.setText(""+ mComputer.getPrice());
        }
    }
}
