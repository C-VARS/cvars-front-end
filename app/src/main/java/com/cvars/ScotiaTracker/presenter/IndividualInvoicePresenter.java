package com.cvars.ScotiaTracker.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.LocationTime;
import com.cvars.ScotiaTracker.responseListeners.InvoiceResponseListener;
import com.cvars.ScotiaTracker.view.IndividualInvoiceView;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IndividualInvoicePresenter extends FragmentPresenter implements InvoiceResponseListener, ValueEventListener {

    private DataModelFacade modelFacade;
    private IndividualInvoiceView view;

    private DatabaseReference mBaseRef;
    private DatabaseReference mCurrRef;

    public IndividualInvoicePresenter(DataModelFacade modelFacade, IndividualInvoiceView view) {
        this.modelFacade = modelFacade;
        this.view = view;
        modelFacade.addInvoiceResponseListener(this);

        mBaseRef = FirebaseDatabase.getInstance().getReference().child("Location");
    }

    public void updateStatus(int invoiceID, String status){
        modelFacade.updateStatus(invoiceID, status);
    }

    @Override
    public void onDestroy() {
        modelFacade = null;
    }

    @Override
    public void notifyInvoiceResponse() {
        int id = view.getCurrentInvoiceNum();

        if (id != -1){
            Invoice inv = modelFacade.getInvoice(id);
            view.updateFields(inv);
        }
    }

    public void updateInvoice(int invoiceID){
        Invoice inv = modelFacade.getInvoice(invoiceID);
        view.updateFields(inv);

        if (mCurrRef != null){
            mCurrRef.removeEventListener(this);
        }

        if (!inv.getDriverUsername().equals("N/A")){
            mCurrRef = mBaseRef.child(inv.getDriverUsername());
            mCurrRef.addValueEventListener(this);
        }
    }

    public UserType getUserType(){return modelFacade.getUserType();}

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        LocationTime lt = dataSnapshot.getValue(LocationTime.class);
        view.updateMap(lt);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.e("IndividualInvoicePresenter", "Database Error: " + databaseError.getDetails());
    }
}

