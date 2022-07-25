package ufrn.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
	
	@PostMapping
	public void printSubscription(@RequestBody JsonNode body) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		String bodyString = body.toPrettyString();
		
		String clothingDescription, storeId;
		
		clothingDescription = new SubscribeUtil().getAttributeValue(bodyString, "\"description\"", 60);
		
		storeId = new SubscribeUtil().getAttributeValue(bodyString, "\"store\"", 54);

		System.out.println("- " + clothingDescription + " disponível na loja " + new SubscribeUtil().getStoreTitleById(storeId) + " (#id " + storeId + ") [" + formatter.format(date) + "]");
	}
	
}
