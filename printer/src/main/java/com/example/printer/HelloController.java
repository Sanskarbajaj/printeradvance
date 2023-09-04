package com.example.printer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button closeButton;

    @FXML
    private ComboBox comboBox;

    @FXML
    void onComboBoxSelect(ActionEvent event) {

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(getPrinterNames());
        print();
    }

    @FXML
    void onCloseButtonClick(ActionEvent event) {
        System.exit(0);
    }


    private static String getPrinterNames() {
        String content = "";
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

        content += "Number of print services: " + services.length;
        content += "\n";

        if (services.length != 0 || services != null) {
            int i = 1;
            for (PrintService service : services) {
                String name = service.getName();

                content += "Printer " + i + " name: " + name;
                content += "\n";
                i++;
            }
        }

        System.out.println(content);

        return content;
    }

    public void print() {
        String printerName = "Microsoft XPS Document Writer";

        // Get the specified printer
        PrintService printerService = getPrintServiceByName(printerName);

        if (printerService != null) {
            // Create a simple document with your content
            String content = "This is the content that will be printed.";
            byte[] contentBytes = content.getBytes();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            SimpleDoc doc = new SimpleDoc(contentBytes, flavor, null);

            // Create a print request attribute set
            PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

            // Print the document to the specified printer
            try {
                System.out.println(printerName);
                printerService.createPrintJob().print(doc, attributeSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Printer not found: " + printerName);
        }
    }

    private static PrintService getPrintServiceByName(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService service : printServices) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }
        return null;
    }
}