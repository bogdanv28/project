package wantsome.presentation.client_operations;

import wantsome.business.FlowerService;
import wantsome.business.mail.SendMailBySite;
import wantsome.database.dto.Flower;

import javax.mail.MessagingException;
import java.util.List;

public class CheckFlowerStock {
    public static void check() throws MessagingException {
        FlowerService flowerService = FlowerService.getInstance();

        List<Flower> flowerList  = flowerService.seeFlowerList();

        for (Flower f : flowerList){
            if (f.getAvailableStock()<15){
                SendMailBySite.sendPlainTextEmail("LOW FLOWER STOCK!",
                        "Only " + f.getAvailableStock() + " units of " + f.getName() + " available!");
            }
        }

    }
}
