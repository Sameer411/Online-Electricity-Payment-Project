package com.group2.capstone.EBPaymentSystem.billing.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group2.capstone.EBPaymentSystem.authentication.models.User;
import com.group2.capstone.EBPaymentSystem.billing.models.Bill;
import com.group2.capstone.EBPaymentSystem.billing.models.Meter;
import com.group2.capstone.EBPaymentSystem.billing.models.MeterReadings;
import com.group2.capstone.EBPaymentSystem.billing.models.PaymentInfo;
import com.group2.capstone.EBPaymentSystem.billing.models.Property;
import com.group2.capstone.EBPaymentSystem.billing.models.PropertyType;
import com.group2.capstone.EBPaymentSystem.billing.repository.BillingRepo;
import com.group2.capstone.EBPaymentSystem.billing.repository.MeterReadingsRepo;
import com.group2.capstone.EBPaymentSystem.billing.repository.PropertyRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BillingService {

    @Autowired
    private BillingRepo billRepo;

    @Autowired
    private CalendarService calService;

    @Autowired
    private MeterReadingsRepo meterReadingsRepo;
    

    public List<Property> getUserProperties(User user) {
        List<Property> properties = user.getUserProfile().getProperties();
        return properties;
    }

    public List<MeterReadings> getMeterReadings(Meter meter, int month, int year){

        List<MeterReadings> readings = meterReadingsRepo.findByMeter(meter.getMeterId(), month, year);
        return readings;
    }

    public Bill calculateBill(Property property, int month, int year) {
        Meter meter = property.getMeter();
        List<MeterReadings> readings = getMeterReadings(meter, month, year);
        PropertyType pType = property.getPropertyType();

        double amount = 0;
        double units = readings.get(0).getUnitsConsumed();

        if (units > 400) {
            amount += 200 * pType.getLowRate();
            amount += 200 * pType.getMediumRate();
            amount += (units - 400) * pType.getHighRate();
        } else if (units > 200) {
            amount += 200 * pType.getLowRate();
            amount += (units - 200) * pType.getMediumRate();
        } else {
            amount += units * pType.getLowRate();
        }

        Calendar cal = calService.getDueDate();
        LocalDate date = LocalDate.now();
        Integer status = 0;
        Bill bill = new Bill();
        bill.setAmount(amount);
        bill.setDueDate(cal.getTime());
        bill.setProperty(property);
        bill.setStatus(status);
        bill.setBillingMonth(date);
        bill.setUnitsConsumed(units);
        insertBill(bill);
        
        return bill;
    }

    public void insertBill(Bill bill) {
            billRepo.save(bill);
    }

    public byte[] pdfGenerator(User user, List<Bill> bills) throws IOException {
        PDDocument document = new PDDocument();

        PDPage pdpage = new PDPage();

        document.addPage(pdpage);

        PDPageContentStream contentStream = new PDPageContentStream(document, pdpage,
                PDPageContentStream.AppendMode.APPEND, true, true);

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        float startX = 50;
        float startY = 700;
        float endX = pdpage.getMediaBox().getWidth() - 50; // Adjust the end position if needed
        float endY = startY;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX, startY);

        contentStream.showText("Name: " + user.getUserProfile().getName());
        contentStream.newLine();
        contentStream.newLineAtOffset(0, -15); // Move to the next line
        contentStream.showText("Email: " + user.getUserProfile().getEmail());
        contentStream.newLine();
        contentStream.newLineAtOffset(0, -15); // Move to the next line
        contentStream.showText("Contact No: " + user.getUserProfile().getContactNo());
        contentStream.newLine();
        contentStream.newLineAtOffset(0, -15); // Move to the next line
        contentStream.showText("Date: " + LocalDate.now());
        contentStream.newLine();
        contentStream.newLineAtOffset(0, -15); // Move to the next line
        for (Bill bill : bills) {
        	contentStream.newLine();
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("BIll ID: " + bill.getId());
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Meter ID: " + bill.getProperty().getMeter().getMeterId());
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Generated Date: " + bill.getBillingMonth());
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -15);
            String[] addressLines = splitAddressIntoLines(bill.getProperty().getAddress().toString());
            for (String addressLine : addressLines) {
                contentStream.showText(addressLine);
                contentStream.newLine();
                contentStream.newLineAtOffset(0, -15);
            }

            contentStream.showText("Units Consumed: " + bill.getUnitsConsumed());
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Amount: " + bill.getAmount());
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Due Date: " + bill.getDueDate());
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -15);
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -15);

        }
        contentStream.newLine();
        contentStream.showText("You can pay the bill at: "+"http://localhost:8081/pay");
        contentStream.endText();
        contentStream.moveTo(startX, endY);
        contentStream.lineTo(endX, endY);
        contentStream.stroke();
        contentStream.close();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();

        return outputStream.toByteArray();
    }

    private String[] splitAddressIntoLines(String address) {
        String[] lines = address.split(", ");
        for (String line : lines) {
            System.out.println(line);
        }
        return lines;
    }

    public List<Bill> getBillForUser(User user) {

        List<Bill> bills = new ArrayList<>();

        List<Property> properties = getUserProperties(user);

        for (Property property : properties) {
            List<Bill> allBills = billRepo.findByProperty(property.getId());
            bills.add(allBills.get(0));
        }

        return bills;
    }


    public List<Bill> getBillForPrevMonths(User user, int month, int year){
        List<Bill> bills = new ArrayList<>();
        List<Property> properties = getUserProperties(user);
        for (Property property : properties) {
            bills.add(billRepo.findByPropertyAndDate(property.getId(), month, year));
        }
        return bills;
    }
    
    
    public Bill getBillFromBillId(long billId) {
    	
    	Optional<Bill> bill = billRepo.findById(billId);
    	return bill.get();
    }

	public void updateStatusAsPaid(long billId) {
		
		Bill bill = getBillFromBillId(billId);
		bill.setStatus(2);
		billRepo.save(bill);
		
	}

}