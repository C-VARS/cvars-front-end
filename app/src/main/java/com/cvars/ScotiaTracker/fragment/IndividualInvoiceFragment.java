package com.cvars.ScotiaTracker.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Invoice", "You created new invoice page");
        this.view = inflater.inflate(R.layout.single_invoice, container, false);
        invoiceContainer = view.findViewById(R.id.invoiceContainer);

        // Set up individual layouts for each tab
        basicInfoView = inflater.inflate(R.layout.component_basic_invoice_info, invoiceContainer, false);
        fullInvoiceView = inflater.inflate(R.layout.full_invoice, invoiceContainer,false);

        // Set up initial tab
        currentView = basicInfoView;
        invoiceContainer.addView(basicInfoView);

        initializeTab();
        initializeActionButton();
        initializeMap();

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

    @Override
    public void updateFields(Invoice invoice){
        // Save this invoice
        this.invoice = invoice;

        if (currentView == basicInfoView) {
            // Fill in invoiceID
            ((TextView) basicInfoView.findViewById(R.id.invoiceNum)).setText(Integer.toString(invoice.getInvoiceId()));
            ((TextView) basicInfoView.findViewById(R.id.totalPrice)).setText(Double.toString(invoice.getTotalCost()));

            updateActionButton();
        }

        else{
            TableLayout table = currentView.findViewById(R.id.invoiceTable);
            table.removeAllViews();

            // Populate table with rows of order information
            List<Order> orders = invoice.getOrders();
            for (Order order: orders){
                TableRow row = new TableRow(view.getContext());
                row.setGravity(Gravity.CENTER);
                row.setPadding(0, 50, 0, 50);

                // Set up new cells
                TextView item = new TextView(view.getContext());
                TextView amount = new TextView(view.getContext());
                TextView price = new TextView(view.getContext());
                TextView subtotal = new TextView(view.getContext());

                item.setPadding(0, 0, 0, 5);
                item.setGravity(Gravity.START);
                amount.setPadding(30, 5, 30, 5);
                amount.setGravity(Gravity.CENTER);
                price.setPadding(5, 5, 5, 5);
                price.setGravity(Gravity.CENTER);
                subtotal.setPadding(5, 5, 5, 5);
                subtotal.setGravity(Gravity.RIGHT);

                // Fill in the cells
                item.setText(order.getName());
                amount.setText(Integer.toString(order.getQuantity()));
                price.setText(Double.toString(order.singleItemPrice()));
                subtotal.setText(Double.toString(order.getTotalPrice()));

                // add the cells on to the row
                row.addView(item);
                row.addView(amount);
                row.addView(price);
                row.addView(subtotal);

                table.addView(row);
            }

            // Fill in invoiceID
            ((TextView) currentView.findViewById(R.id.invoiceID)).setText(Integer.toString(invoice.getInvoiceId()));
            ((TextView) currentView.findViewById(R.id.issuedDate)).setText(invoice.getIssuedDate());
            ((TextView) currentView.findViewById(R.id.customerName)).setText(invoice.getCustomerName());
            ((TextView) currentView.findViewById(R.id.customerAddress)).setText(invoice.getCustomerAddress());
            ((TextView) currentView.findViewById(R.id.customerContact)).setText(invoice.getCustomerContact());
            ((TextView) currentView.findViewById(R.id.supplierName)).setText(invoice.getSupplierName());
            ((TextView) currentView.findViewById(R.id.supplierContact)).setText(invoice.getSupplierContact());

            // Calculate Total
            double subtotal = invoice.getTotalCost();
            ((TextView) currentView.findViewById(R.id.subtotalText)).setText(Double.toString(subtotal));
            ((TextView) currentView.findViewById(R.id.totalText)).setText(Double.toString(subtotal * 1.13));
        }
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
            actionButton.setVisibility(View.GONE);
        }
    }

    public int getCurrentInvoiceNum(){
        String invoiceID = ((TextView) view.findViewById(R.id.invoiceNum)).getText().toString();

        if (invoiceID.equals("Invoice ID")){
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
