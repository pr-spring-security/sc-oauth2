package org.sc.oauth2.resource.server.api;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.sc.oauth2.resource.server.configuration.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

	@PreAuthorize("#oauth2.hasScope('trust') or "
				+ "#oauth2.hasScope('public_profile') or "
				+ "#oauth2.hasScope('email') or"
				+ "#oauth2.hasScope('date_of_birth') or"
				+ "#oauth2.hasScope('place_of_birth')")
	@RequestMapping("/me")
	public Map<String, Object> profile(Principal principal) {
		Map<String, Object> response = new HashMap<>();

		OAuth2Authentication auth = (OAuth2Authentication) principal;

		for (String scope : auth.getOAuth2Request().getScope()) {
			System.out.println(scope);

			if (Scope.TRUST.toString().equalsIgnoreCase(scope)) {
				response.put("id", 1);
				response.put("username", "phearun");
				response.put("email", "rathpheaurn@gmail.com");
				response.put("date_of_birth", "11021994");
				response.put("place_of_birth", "Kandal");
				return response;
			}
			if (Scope.PUBLIC_PROFILE.toString().equalsIgnoreCase(scope)) {
				response.put("id", 1);
				response.put("username", "phearun");
				
			} else if (Scope.EMAIL.toString().equalsIgnoreCase(scope)) {
				response.put("email", "rathpheaurn@gmail.com");
				
			} else if (Scope.DATE_OF_BIRTH.toString().equalsIgnoreCase(scope)) {
				response.put("date_of_birth", "11021994");
				
			} else if (Scope.PLACE_OF_BIRTH.toString().equalsIgnoreCase(scope)) {
				response.put("place_of_birth", "Kandal");
			}
		}

		return response;
	}

}
