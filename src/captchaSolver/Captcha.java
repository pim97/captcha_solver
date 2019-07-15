package captchaSolver;

public class Captcha {

	private int id, attempts;
	private String api_key, google_key_on_website, page_url, proxy_user, proxy_password, proxy_ip, proxy_port, proxy_type, solved_key;
	public int getId() {
		return id;
	}
	public Captcha(int id, String api_key, String google_key_on_website, String page_url, String proxy_user,
			String proxy_password, String proxy_port, String proxy_type, String solved_key, int attempts, String proxy_ip) {
		this.id = id;
		this.api_key = api_key;
		this.google_key_on_website = google_key_on_website;
		this.page_url = page_url;
		this.proxy_user = proxy_user;
		this.proxy_password = proxy_password;
		this.proxy_port = proxy_port;
		this.proxy_type = proxy_type;
		this.proxy_ip = proxy_ip;
		this.attempts = attempts;
		this.solved_key = solved_key;
		
	}
	@Override
	public String toString() {
		return "Captcha [id=" + id + ", attempts=" + attempts + ", api_key=" + api_key + ", google_key_on_website="
				+ google_key_on_website + ", page_url=" + page_url + ", proxy_user=" + proxy_user + ", proxy_password="
				+ proxy_password + ", proxy_ip=" + proxy_ip + ", proxy_port=" + proxy_port + ", proxy_type="
				+ proxy_type + ", solved_key=" + solved_key + "]";
	}
	public String getApi_key() {
		return api_key;
	}
	public String getGoogle_key_on_website() {
		return google_key_on_website;
	}
	public String getPage_url() {
		return page_url;
	}
	public String getProxy_user() {
		return proxy_user;
	}
	public String getProxy_password() {
		return proxy_password;
	}
	public String getProxy_port() {
		return proxy_port;
	}
	public String getProxy_type() {
		return proxy_type;
	}
	public String getSolved_key() {
		return solved_key;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	public void setGoogle_key_on_website(String google_key_on_website) {
		this.google_key_on_website = google_key_on_website;
	}
	public void setPage_url(String page_url) {
		this.page_url = page_url;
	}
	public void setProxy_user(String proxy_user) {
		this.proxy_user = proxy_user;
	}
	public void setProxy_password(String proxy_password) {
		this.proxy_password = proxy_password;
	}
	public void setProxy_port(String proxy_port) {
		this.proxy_port = proxy_port;
	}
	public void setProxy_type(String proxy_type) {
		this.proxy_type = proxy_type;
	}
	public void setSolved_key(String solved_key) {
		this.solved_key = solved_key;
	}
	/**
	 * @return the attempts
	 */
	public int getAttempts() {
		return attempts;
	}
	/**
	 * @param attempts the attempts to set
	 */
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	/**
	 * @return the proxy_ip
	 */
	public String getProxy_ip() {
		return proxy_ip;
	}
	/**
	 * @param proxy_ip the proxy_ip to set
	 */
	public void setProxy_ip(String proxy_ip) {
		this.proxy_ip = proxy_ip;
	}
}
