package ufrn.Subscribe;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SubscribeUtil {
	public String getAttributeValue(String text, String regExp, Integer charsAmt) {
		Integer valueInit = text.indexOf(regExp) + charsAmt;
		String valueToEnd = text.substring(text.indexOf(regExp) + charsAmt);
		Integer valueEnd = valueToEnd.indexOf(",");
		return text.substring(valueInit, valueInit + valueEnd - 1);
	}
	
	public String getStoreTitleById(String id) {

		String uri = "http://localhost:1026/v2/entities";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		
		if(result.getStatusCode() == HttpStatus.OK) {
			
			String[] stores = result.getBody().substring(1, result.getBody().length() - 2).split("id\":");

			
			for(int i = 1; i < stores.length; i++) {
				String store = stores[i];
				if (store.substring(store.indexOf(",\"type\":") + 9,store.indexOf(",\"type\":") + 10).equals("S")) {
					if (this.getAttributeValue(store, "\"id:\"", 2).equals(id)) {
						return this.getAttributeValue(store, "\"title\":", 34);						
						
					}
				}
			}
		}
		return "";
	}
	
	public Integer getSubscriptionTypeByIpAndPort(String ip, String port) {

		String uri = "http://localhost:1026/v2/subscriptions";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		
		if(result.getStatusCode() == HttpStatus.OK) {
			if (result.getBody().length() < 4) return -1;
			String[] subscriptions = result.getBody().substring(1, result.getBody().length() - 2).split("id\":");
			for (String sub : subscriptions) {
				if (sub.indexOf(ip + ":" + port) != - 1) {
					return Integer.parseInt(sub.substring(sub.indexOf("style==") + 7, sub.indexOf("style==") + 8));
				}
			}
			
		}
		
		return -1;
		
	}
	
	public String subscribe(String ip, String port, Integer favoriteStyle) {
		JSONObject subscription = new JSONObject();

		JSONObject subject = new JSONObject();
		List<JSONObject> entities = new ArrayList<>();

		JSONObject entity = new JSONObject();
		entity.put("idPattern", "C*");
		entity.put("type", "Clothing");

		entities.add(entity);

		subject.put("entities", entities);

		JSONObject condition = new JSONObject();
		List<String> attrs = new ArrayList<>();
		attrs.add("available");
		attrs.add("style");
		condition.put("attrs", attrs);

		JSONObject expression = new JSONObject();
		expression.put("q", "available==true;style==" + favoriteStyle);
		condition.put("expression", expression);

		subject.put("condition", condition);
		subscription.put("subject", subject);

		JSONObject notification = new JSONObject();
		JSONObject http = new JSONObject();
		http.put("url", "http://" + ip + ":" + port + "/Subscribe/subscribe");
		notification.put("http", http);

		subscription.put("notification", notification);	
		
		String url = "http://localhost:1026/v2/subscriptions";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(subscription.toString(), httpHeaders);
		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.postForObject(url, httpEntity, String.class);
	}

}
