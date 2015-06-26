package serviceTester.core.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import serviceTester.core.services.api.VersionService;

@Service
public class VersionServiceImpl implements VersionService {

	@Value("${app.version}")
	private String version;
	

	@Override
	public String getVersionNumber() {
		return version;
	}


	@Override
	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
