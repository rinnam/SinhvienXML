package com.source;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.net.Socket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class SinhvienManager {
    private JFrame frame;
    private JTextField idTextField, nameTextField, addressTextField;
    private JButton searchButton, addButton, editButton, deleteButton, clearButton, exitButton, saveButton, loadButton;
    private JTable table;

    Socket s;
    DataInputStream in;
    DataOutputStream out;
    
    public SinhvienManager() {
        frame = new JFrame("QL lop hoc");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(620, 300);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel titlePanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("QUẢN LÝ LỚP HỌC");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setForeground(Color.BLUE);
        titlePanel.add(titleLabel);

        JPanel SLPanel = new JPanel(new FlowLayout());
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        SLPanel.add(saveButton);
        SLPanel.add(loadButton);


        JPanel inputbuttonPanel = new JPanel(new GridLayout(7, 2));
        inputbuttonPanel.add(new JLabel("ID:"));
        idTextField = new JTextField();
        inputbuttonPanel.add(idTextField);
        inputbuttonPanel.add(new JLabel("Name:"));
        nameTextField = new JTextField();
        inputbuttonPanel.add(nameTextField);
        inputbuttonPanel.add(new JLabel("Address:"));
        addressTextField = new JTextField();
        inputbuttonPanel.add(addressTextField);

        

        searchButton = new JButton("Search");
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");
        inputbuttonPanel.add(searchButton);
        inputbuttonPanel.add(addButton);
        inputbuttonPanel.add(editButton);
        inputbuttonPanel.add(deleteButton);
        inputbuttonPanel.add(clearButton);
        inputbuttonPanel.add(exitButton);
        

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are uneditable
                return false;
            }
        };
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Address");
        table = new JTable(tableModel);
        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(inputbuttonPanel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.EAST);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(SLPanel, BorderLayout.SOUTH);

        frame.setVisible(true);



        try {
            s = new Socket("192.168.0.8", 1234);
            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    
        

        // Add action listeners for buttons
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTextField.getText();
                String name = nameTextField.getText();
                String address = addressTextField.getText();
                
                // Loop through each row in the table
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String tableId = tableModel.getValueAt(i, 0).toString();
                    String tableName = tableModel.getValueAt(i, 1).toString();
                    String tableAddress = tableModel.getValueAt(i, 2).toString();
                    
                    // Check if the row matches the search criteria
                    if (id.equals(tableId) && name.equals(tableName) && address.equals(tableAddress)) {
                        // Select the matching row in the table
                        table.setRowSelectionInterval(i, i);
                        table.scrollRectToVisible(table.getCellRect(i, 0, true));
                        break;
                    }
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTextField.getText();
                String name = nameTextField.getText();
                String address = addressTextField.getText();
                
                // Add the data to the table
                Object[] rowData = {id, name, address};
                tableModel.addRow(rowData);
                
                // Clear the text fields
                idTextField.setText("");
                nameTextField.setText("");
                addressTextField.setText("");
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index
                int selectedRowIndex = table.getSelectedRow();
                
                // Check if a row is selected
                if (selectedRowIndex != -1) {
                    // Get the data from the selected row
                    String id = idTextField.getText();
                    String name = nameTextField.getText();
                    String address = addressTextField.getText();
        
                    // Update the data in the table
                    tableModel.setValueAt(id, selectedRowIndex, 0);
                    tableModel.setValueAt(name, selectedRowIndex, 1);
                    tableModel.setValueAt(address, selectedRowIndex, 2);
                    
                    // Clear the text fields
                    idTextField.setText("");
                    nameTextField.setText("");
                    addressTextField.setText("");
                    
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Get the selected row index
            int selectedRowIndex = table.getSelectedRow();
            
            // Check if a row is selected
            if (selectedRowIndex != -1) {
                // Remove the selected row from the table
                tableModel.removeRow(selectedRowIndex);
                
                // Clear the text fields
                idTextField.setText("");
                nameTextField.setText("");
                addressTextField.setText("");
            }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the table data
                tableModel.setRowCount(0);
                
                // Clear the text fields
                idTextField.setText("");
                nameTextField.setText("");
                addressTextField.setText("");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String command = "save";
                    out.writeUTF(command);
                    int n = tableModel.getRowCount();
                    out.writeInt(n);
        
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        String id = tableModel.getValueAt(i, 0).toString();
                        String name = tableModel.getValueAt(i, 1).toString();
                        String address = tableModel.getValueAt(i, 2).toString();


                        out.writeUTF(id);
                        out.writeUTF(name);
                        out.writeUTF(address);
    
                    }

                    JOptionPane.showMessageDialog(frame, "Data saved successfully!", "Save", JOptionPane.INFORMATION_MESSAGE);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String command = "load";
                    out.writeUTF(command);
                    tableModel.setRowCount(0);
            
                    // Read the number of students first
                    int numStudents = in.readInt();
            
                    for (int i=0; i<numStudents; i++){
                        String id = in.readUTF();
                        String name = in.readUTF();
                        String address = in.readUTF();
                        Object[] rowData = {id, name, address};
                        tableModel.addRow(rowData);
                    }
            
                    JOptionPane.showMessageDialog(frame, "Data loaded successfully!", "Load", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                    
        }
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SinhvienManager();
            }
        });
    }
}


