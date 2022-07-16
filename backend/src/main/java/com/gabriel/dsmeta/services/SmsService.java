package com.gabriel.dsmeta.services;

import com.gabriel.dsmeta.entities.Sale;
import com.gabriel.dsmeta.repositories.SaleRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class SmsService {

    @Value("${twilio.sid}")
    private String twilioSid;

    @Value("${twilio.key}")
    private String twilioKey;

    @Value("${twilio.phone.from}")
    private String twilioPhoneFrom;

    @Value("${twilio.phone.to}")
    private String twilioPhoneTo;
    @Autowired
    private SaleRepository saleRepository;

    public void sendSms(Long saleId) {


        var text = getFormaterMessage(saleId);

        Twilio.init(twilioSid, twilioKey);

        PhoneNumber to = new PhoneNumber(twilioPhoneTo);
        PhoneNumber from = new PhoneNumber(twilioPhoneFrom);

        Message message = Message.creator(to, from, text ).create();

        System.out.println(message.getSid());
    }

    private String getFormaterMessage(Long saleId) {
        var sale = getSale(saleId);
        if (sale.isPresent()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            var dateParse = sale.get().getDate().format(formatter);
        return "O vendedor " + sale.get().getSellerName() + " foi destaque em " + dateParse.toString()
                + "com um total de R$" + String.format("%.2f", sale.get().getAmount()) ;
        } else {
            return "";
        }
    }

    private Optional<Sale> getSale(Long saleId) {
        return  saleRepository.findById(saleId);
    }
}
