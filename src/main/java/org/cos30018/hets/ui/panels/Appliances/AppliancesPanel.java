package org.cos30018.hets.ui.panels.Appliances;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class AppliancesPanel extends JPanel {

    private static final String TAG = "AppliancesPanel";
    public Color lightblue = new Color(187,222,251);
    JButton addBtn;

    public AppliancesPanel() {
        System.out.println(TAG + " is created");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.lightGray);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        addBtn = new JButton("Add Appliances");
        add(addBtn);
    }


    void addAppliancesPanelListener(ActionListener listener) {
        addBtn.addActionListener(listener);
    }



    public void AddApplianceDialogBox() {


        //Variables and initialization for AddAppliances Dialog Box
        String[] types = {"Dishwasher", "bulb", "Refrigerator"};
        String[] forecastMethods = {"Simple", "Moderate", "Complex"};

        /**Initialization of TextFields and Drop downs in DialogBox**/
        JTextField applianceNameField = new JTextField();
        JComboBox<String> typeCombo = new JComboBox<String>(types);
        JComboBox<String> forecastCombo = new JComboBox<String>(forecastMethods);

        Object[] dialogComponents = {
                "Name", applianceNameField,
                "Type", typeCombo,
                "Forecast Method", forecastCombo
        };

        JOptionPane.showConfirmDialog(null, dialogComponents, "Add Appliance", JOptionPane.OK_CANCEL_OPTION);


        //Validating the input check the response of dialog window

        if (!applianceNameField.getText().isEmpty()) {
            System.out.println("OK is clicked");

            addApplianceList(applianceNameField.getText(), typeCombo.getSelectedItem().toString(), forecastCombo.getSelectedItem().toString());
            updateUI();
        }
        else {
            JOptionPane.showMessageDialog(null, "Appliance Name is Empty");
            System.out.println("Appliance Name is EMPTY");
        }
    }

    private void addApplianceList(String applianceName, String applianceType, String forecastMethod) {

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(applianceName);
        name.setFont(name.getFont().deriveFont(14.0f));
        JLabel usage = new JLabel(applianceType);
        JButton btnShowDetails = new JButton("Show Details");
        JButton btnDelete = new JButton("Delete");

        container.setBorder(new EmptyBorder(5,5,5,5));

        container.add(name);
        container.add(usage);
        container.add(btnShowDetails);
        container.add(btnDelete);

        add(container);

    }

}