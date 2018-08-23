package instigatemobile.com.cookmaster.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import instigatemobile.com.cookmaster.R;

public class PaymentFragment extends Fragment {

    public PaymentFragment() {
    }

    @SuppressLint("ValidFragment")
    private TextView mLastPaidMoney;
    private EditText mMoneyET;
    private Button mPayBtn;
    private ImageView pepperImage;
    private String mStatus;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.payment_page_title));
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        findVariables(view);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference statusRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference moneyRef = database.getReference(getString(R.string.money_paid));
        onPayBtnClicked(moneyRef, statusRef);
        getStatus(statusRef);
        return view;
    }

    private void getStatus(DatabaseReference statusRef) {
        statusRef.child(getString(R.string.users)).
                child(String.valueOf(Objects.requireNonNull(FirebaseAuth.getInstance().
                        getCurrentUser()).getUid())).child(getString(R.string.payment_PF)).
                child(getString(R.string.payment_status_PF)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mStatus = String.valueOf(dataSnapshot.getValue());
                whichImage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void onPayBtnClicked(final DatabaseReference moneyRef, final DatabaseReference statusRef) {
        mPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String amountofMoney = mMoneyET.getText().toString();
                if (TextUtils.isEmpty(amountofMoney) || amountofMoney.startsWith(getString(R.string.zero))) {
                    Toast.makeText(getContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
                    return;
                }
                statusRef.child(getString(R.string.users)).
                        child(String.valueOf(Objects.requireNonNull(FirebaseAuth.getInstance().
                                getCurrentUser()).getUid())).child(getString(R.string.payment_PF)).
                        child(getString(R.string.payment_status_PF)).setValue(getString(R.string.pending_PF));

                moneyRef.setValue(amountofMoney).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), R.string.success, Toast.LENGTH_SHORT).show();
                        mLastPaidMoney.setText(amountofMoney);
                        mMoneyET.setText("");
                        whichImage();
                    }
                });
            }
        });
    }

    public void whichImage() {
        switch (mStatus) {
            case "pending":
                pepperImage.setImageResource(R.drawable.orange_pepper);
                break;
            case "confirmed":
                pepperImage.setImageResource(R.drawable.green_pepper);
                break;
            default:
                pepperImage.setImageResource(R.drawable.pepper_red);
                break;
        }
    }

    private void findVariables(View view) {
        mLastPaidMoney = view.findViewById(R.id.last_paid_money);
        mMoneyET = view.findViewById(R.id.money_ET);
        mPayBtn = view.findViewById(R.id.pay_Btn);
        pepperImage = view.findViewById(R.id.pepperImg);
    }
}