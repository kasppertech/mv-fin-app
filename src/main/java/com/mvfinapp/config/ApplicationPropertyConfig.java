package com.mvfinapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
 

@ConfigurationProperties("mv-fin-app")
public class ApplicationPropertyConfig {

	private final Security security = new Security();

	public Security getSecurity() {
		return security;
	} 

	public static class Security {
		private boolean enableHttps;

		private String allowedOrigin = "";

		public String getAllowedOrigin() {
			return allowedOrigin;
		}

		public void setAllowedOrigin(String allowedOrigin) {
			this.allowedOrigin = allowedOrigin;
		}

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
	}
}
