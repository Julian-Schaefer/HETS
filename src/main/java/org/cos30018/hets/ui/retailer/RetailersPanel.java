package org.cos30018.hets.ui.retailer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class RetailersPanel extends JPanel {

    private static final String TAG = "RetailerPanel";
    public Color lightblue = new Color(187,222,251);
    JButton addRetailerBtn;

    public RetailersPanel() {

        System.out.println(TAG + " is created");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.lightGray);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        addRetailerBtn = new JButton("Add Retailers");
        add(addRetailerBtn);

    }

    void addRetailersPanelListener(ActionListener listener) {
        addRetailerBtn.addActionListener(listener);
    }



    public void AddRetailersDialogBox() {

        //Selective data for two JComboBox implemented
        String[] negotiation = {"Fixed", "Complex"};
        String[] pricing = {"High", "Low", "Efficient"};

        /**Initialization of TextFields and Drop downs in DialogBox**/
        JTextField retailerNameField = new JTextField();
        JComboBox<String> negotitationStrategy = new JComboBox<String>(negotiation);
        JComboBox<String> pricingStrategy = new JComboBox<String>(pricing);

        Object[] retailerDialogComponents = {
                "Name", retailerNameField,
                "Negotiation Strategy", negotitationStrategy,
                "Pricing Strategy", pricingStrategy
        };

        JOptionPane.showConfirmDialog(null, retailerDialogComponents,
                "Add Retailer", JOptionPane.OK_CANCEL_OPTION);

        /**
         * Validating the input check the response of dialog window
         *
         */
        if (!retailerNameField.getText().isEmpty()) {

            addRetailerList(retailerNameField.getText(), negotitationStrategy.getSelectedItem().toString(),
                    pricingStrategy.getSelectedItem().toString());
            updateUI();

        } else {

            JOptionPane.showMessageDialog(null, "Retailer Name is Empty");
            System.out.println("Retailer Name is EMPTY");

        }


    }

    private void addRetailerList(String name, String negotiation, String pricing) {

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JLabel retailerName = new JLabel(name);
        retailerName.setFont(getFont().deriveFont(14.0f));
        JLabel retailerDesc = new JLabel(negotiation);

        JButton showDetailsBtn = new JButton("Show Details");
        JButton deleteBtn = new JButton("Delete");

        container.setBorder(new EmptyBorder(5,5,5,5));

        container.add(retailerName);
        container.add(retailerDesc);
        container.add(showDetailsBtn);
        container.add(deleteBtn);

        add(container);

    }

}