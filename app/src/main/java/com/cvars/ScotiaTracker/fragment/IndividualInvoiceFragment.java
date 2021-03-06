package com.cvars.ScotiaTracker.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.LocationTime;
import com.cvars.ScotiaTracker.model.pojo.Order;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.presenter.FragmentPresenter;
import com.cvars.ScotiaTracker.presenter.IndividualInvoicePresenter;
import com.cvars.ScotiaTracker.view.IndividualInvoiceView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;

import java.text.DecimalFormat;
import java.util.List;

public class IndividualInvoiceFragment extends Fragment implements IndividualInvoiceView, OnMapReadyCallback {

    private View view;
    private FrameLayout invoiceContainer;
    private View basicInfoView;

    private MapView mapView;
    private GoogleMap googleMap;
    private Marker marker;

    private View fullInvoiceView;
    private View currentView;
    private Invoice invoice;
    private IndividualInvoicePresenter individualInvoicePresenter;
    private UserType userType;

    private Button actionButton;

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Invoice", "You created new invoice page");
        this.view = inflater.inflate(R.layout.fragment_single_invoice, container, false);
        invoiceContainer = view.findViewById(R.id.invoiceContainer);

        // Set up individual layouts for each tab
        basicInfoView = inflater.inflate(R.layout.component_basic_invoice_info, invoiceContainer, false);
        fullInvoiceView = inflater.inflate(R.layout.component_full_invoice, invoiceContainer,false);

        // Set up initial tab
        currentView = basicInfoView;
        invoiceContainer.addView(basicInfoView);

        initializeTab();
        initializeActionButton();
        initializeMap();

        this.progressBar = basicInfoView.findViewById(R.id.progressBar);

        return view;
    }

    private void initializeTab(){
        TabLayout tab = view.findViewById(R.id.invoiceTab);
        tab.addOnTabSelectedListener(new InvoiceTabSwitchListener());
    }

    private void initializeActionButton(){
        actionButton = basicInfoView.findViewById(R.id.payNow);
        actionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (actionButton.getText().toString().equals("Notify Delivery")){
                    updateOnTheWay();
                } else if (actionButton.getText().toString().equals("Confirm Arrival")){
                    updateArrived();
                } else if (actionButton.getText().toString().equals("Pay Now")){
                    updatePay();
                }
            }
        });
    }

    private void updateProgressBar() {
        progressBar.setProgress(getProgress());

        // change color depending on order status
        if (getProgress() == 4) {
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }
        else {
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }

    }

    private int getProgress() {
        // returns 1-4 representing stage of delivery. 1 = pending, 2 = on the way, 3 = arrived,
        // 4 = payment processed
        if (invoice.getOrderStatus().toString().equals("Pending")) {
            return 1;
        }
        else if (invoice.getOrderStatus().toString().equals("On The Way")) {
            return 2;
        }
        else if (invoice.getOrderStatus().toString().equals("Arrived")) {
            return 3;
        }
        else if (invoice.getOrderStatus().toString().equals("Payment Processed")) {
            return 4;
        }
        else {
            System.out.println("Incorrect statusText");
            return 0;
        }
    }

    private void initializeMap(){
        mapView = basicInfoView.findViewById(R.id.mapView);
        mapView.onCreate(null);
        mapView.getMapAsync(this);
    }

    @Override
    public void setPresenter(FragmentPresenter presenter){
        individualInvoicePresenter = (IndividualInvoicePresenter) presenter;
        userType = individualInvoicePresenter.getUserType();
    }

    @Override
    public void onDestroy() {
        view = null;
        mapView.onDestroy();
        super.onDestroy();
    }
    public void updateFullInvoice() {
        LinearLayout itemRow = fullInvoiceView.findViewById(R.id.itemRow);
        itemRow.removeAllViews();
        LinearLayout amountRow = fullInvoiceView.findViewById(R.id.amountRow);
        amountRow.removeAllViews();
        LinearLayout priceRow = fullInvoiceView.findViewById(R.id.priceRow);
        priceRow.removeAllViews();
        LinearLayout subtotalRow = fullInvoiceView.findViewById(R.id.subtotalRow);
        subtotalRow.removeAllViews();

        // Populate table with rows of order information
        List<Order> orders = invoice.getOrders();
        for (Order order : orders) {
            // Set up new cells
            TextView item = new TextView(view.getContext());
            TextView amount = new TextView(view.getContext());
            TextView price = new TextView(view.getContext());
            TextView subtotal = new TextView(view.getContext());

            // Fill in the cells
            item.setText(order.getName());
            item.setGravity(Gravity.CENTER);
            item.setPadding(0, 20, 0, 20);

            amount.setText(Integer.toString(order.getQuantity()));
            amount.setGravity(Gravity.CENTER);
            amount.setPadding(0, 20, 0, 20);

            price.setText(Double.toString(order.singleItemPrice()));
            price.setGravity(Gravity.CENTER);
            price.setPadding(0, 20, 0, 20);

            subtotal.setText(Double.toString(order.getTotalPrice()));
            subtotal.setGravity(Gravity.CENTER);
            subtotal.setPadding(0, 20, 0, 20);

            // add the cells on to the row
            itemRow.addView(item);
            amountRow.addView(amount);
            priceRow.addView(price);
            subtotalRow.addView(subtotal);
        }

        // Fill in invoiceID
        ((TextView) fullInvoiceView.findViewById(R.id.invoiceID)).setText(Integer.toString(invoice.getInvoiceId()));
        ((TextView) fullInvoiceView.findViewById(R.id.issuedDate)).setText(invoice.getIssuedDate());
        ((TextView) fullInvoiceView.findViewById(R.id.customerName)).setText(invoice.getCustomerName());
        ((TextView) fullInvoiceView.findViewById(R.id.customerAddress)).setText(invoice.getCustomerAddress());
        ((TextView) fullInvoiceView.findViewById(R.id.customerContact)).setText(invoice.getCustomerContact());
        ((TextView) fullInvoiceView.findViewById(R.id.supplierName)).setText(invoice.getSupplierName());
        ((TextView) fullInvoiceView.findViewById(R.id.supplierContact)).setText(invoice.getSupplierContact());

        // Calculate Total
        double subtotal = invoice.getTotalCost();
        ((TextView) fullInvoiceView.findViewById(R.id.subtotalText)).setText(Double.toString(Math.round(subtotal)));
        double total = subtotal * 1.13;
        ((TextView) fullInvoiceView.findViewById(R.id.totalText)).setText(Double.toString(Math.round(total)));

    }

    @Override
    public void updateFields(Invoice invoice){
        this.invoice = invoice;
        updateFullInvoice();
        updateBasicInvoice();

    }

    private void updateBasicInvoice() {
        // Fill in invoiceID
        ((TextView) basicInfoView.findViewById(R.id.invoiceNum)).setText(Integer.toString(invoice.getInvoiceId()));
        DecimalFormat df2 = new DecimalFormat("#.##");
        ((TextView) basicInfoView.findViewById(R.id.totalPrice)).setText(df2.format(invoice.getTotalCost()));
        updateActionButton();


        // Fill in progress status
        ((TextView) basicInfoView.findViewById(R.id.status)).setText(invoice.getOrderStatus().toString());
        updateProgressBar();
    }


    private void updateActionButton(){
        actionButton.setVisibility(View.VISIBLE);

        if(userType == UserType.DRIVER && invoice.getOrderStatus().toString().equals("Pending")){
            actionButton.setText("Notify Delivery");
        }
        else if (userType == UserType.DRIVER && invoice.getOrderStatus().toString().equals("On The Way")){
            actionButton.setText("Confirm Arrival");
        }

        else if (userType == UserType.CUSTOMER && !invoice.getOrderStatus().paymentProcessed){
            actionButton.setText("Pay Now");
        }
        else{
            actionButton.setVisibility(View.INVISIBLE);
        }
    }

    public int getCurrentInvoiceNum(){
        String invoiceID = ((TextView) basicInfoView.findViewById(R.id.invoiceNum)).getText().toString();

        if (invoiceID.equals("InvoiceID")){
            return -1;
        }

        return Integer.parseInt(invoiceID);
    }

    @Override
    public void updateMap(LocationTime lt) {

        if (marker != null){
            marker.remove();
        }

        LatLng loc = new LatLng(lt.getLatitude(), lt.getLongitude());
        marker = googleMap.addMarker(new MarkerOptions().position(loc).title(lt.getTime()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }

    @Override
    public void updateOnTheWay() {
        int invoiceID = this.invoice.getInvoiceId();
        individualInvoicePresenter.updateStatus(invoiceID, "onTheWay");
    }

    @Override
    public void updateArrived() {
        int invoiceID = this.invoice.getInvoiceId();

        individualInvoicePresenter.updateStatus(invoiceID, "arrived");
    }

    @Override
    public void updatePay() {
        int invoiceID = this.invoice.getInvoiceId();
        individualInvoicePresenter.updateStatus(invoiceID, "payment");
    }

    /**
     * Allow to switch between tabs
     */
    private void switchComponent(){
        if (currentView == basicInfoView){
            currentView = fullInvoiceView;
            invoiceContainer.removeView(basicInfoView);
            invoiceContainer.addView(fullInvoiceView);
        } else{
            currentView = basicInfoView;
            invoiceContainer.removeView(fullInvoiceView);
            invoiceContainer.addView((basicInfoView));
        }
    }

    private class InvoiceTabSwitchListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switchComponent();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            //unimplemented
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            //unimplemented
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng loc = new LatLng(50, 50);
        googleMap.addMarker(new MarkerOptions().position(loc).title("haha"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));

        googleMap.setMinZoomPreference(12f);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
