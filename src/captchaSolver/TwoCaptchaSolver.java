package captchaSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import com.twocaptcha.api.ProxyType;
import com.twocaptcha.http.HttpWrapper;

public class TwoCaptchaSolver {

	// Recaptcha / global
	private String apiKey;
	private String googleKey;
	private String pageUrl;
	private String proxyIp;
	private String proxyPort;
	private String proxyUser;
	private String proxyPw;
	private ProxyType proxyType;
	private HttpWrapper hw;
	private String userAgent;

	// Funcatpcha
	private String publicKey;

	public TwoCaptchaSolver(Object a, String publicKey, String apiKey, String pageUrl) {
		this.apiKey = apiKey;
		this.publicKey = publicKey;
		this.pageUrl = pageUrl;
		this.hw = new HttpWrapper();
	}

	public TwoCaptchaSolver(String apiKey, String googleKey, String pageUrl) {
		this.apiKey = apiKey;
		this.googleKey = googleKey;
		this.pageUrl = pageUrl;
		this.hw = new HttpWrapper();
	}

	public TwoCaptchaSolver(String apiKey) {
		this.apiKey = apiKey;
		this.hw = new HttpWrapper();
	}

	public TwoCaptchaSolver(String apiKey, String googleKey, String pageUrl, String proxyIp, String proxyPort,
			ProxyType proxyType) {
		this(apiKey, googleKey, pageUrl);
		this.proxyIp = proxyIp;
		this.proxyPort = proxyPort;
		this.proxyType = proxyType;
	}

	public TwoCaptchaSolver(String apiKey, String googleKey, String pageUrl, String proxyIp, String proxyPort,
			String proxyUser, String proxyPw, ProxyType proxyType) {
		this(apiKey, googleKey, pageUrl);
		this.proxyIp = proxyIp;
		this.proxyPort = proxyPort;
		this.proxyUser = proxyUser;
		this.proxyPw = proxyPw;
		this.proxyType = proxyType;
	}

	public String solveFunCaptcha() throws InterruptedException, IOException {
		System.out.println("Sending recaptcha challenge to 2captcha.com");
		String parameters = "key=" + this.apiKey + "&method=funcaptcha&publicKey=" + this.publicKey + "&pageurl="
				+ this.pageUrl+"&userAgent="+this.userAgent+"";
		if (this.proxyIp != null) {
			if (this.proxyUser != null) {
				parameters = parameters + "&proxy=" + this.proxyUser + ":" + this.proxyPw + "@" + this.proxyIp + ":"
						+ this.proxyPort;
			} else {
				parameters = parameters + "&proxy=" + this.proxyIp + ":" + this.proxyPort;
			}

			parameters = parameters + "&proxytype=" + this.proxyType;
		}

		this.hw.get("http://2captcha.com/in.php?" + parameters);
		String captchaId = this.hw.getHtml().replaceAll("\\D", "");
		int timeCounter = 0;

		do {
			this.hw.get("http://2captcha.com/res.php?key=" + this.apiKey + "&action=get" + "&id=" + captchaId);
			Thread.sleep(1000L);
			++timeCounter;
			System.out.println("Waiting for captcha to be solved");
		} while (this.hw.getHtml().contains("NOT_READY"));

		System.out.println("It took " + timeCounter + " seconds to solve the captcha");
		String gRecaptchaResponse = this.hw.getHtml().replaceAll("OK\\|", "").replaceAll("\\n", "");
		return gRecaptchaResponse;
	}

	@Override
	public String toString() {
		return "TwoCaptchaSolver [apiKey=" + apiKey + ", googleKey=" + googleKey + ", pageUrl=" + pageUrl + ", proxyIp="
				+ proxyIp + ", proxyPort=" + proxyPort + ", proxyUser=" + proxyUser + ", proxyPw=" + proxyPw
				+ ", proxyType=" + proxyType + ", hw=" + hw + ", publicKey=" + publicKey + "]";
	}

	public static ArrayList<String> ips = new ArrayList<String>(Arrays.asList("198.12.85.89:20247",
			"198.144.189.71:20247", "107.175.157.48:20247", "107.173.44.219:20247", "107.175.157.33:20247",
			"198.12.85.71:20247", "198.46.129.24:20247", "23.94.79.233:20247", "172.245.88.11:20247",
			"198.12.85.81:20247", "192.227.144.21:20247", "198.46.129.17:20247", "107.175.157.35:20247",
			"192.3.3.242:20247", "23.95.2.149:20247", "23.94.76.26:20247", "198.12.85.85:20247", "104.168.4.172:20247",
			"198.12.85.68:20247", "192.3.3.245:20247", "23.95.2.140:20247", "104.168.4.180:20247",
			"104.168.4.171:20247", "198.144.189.86:20247", "107.172.44.90:20247", "104.168.1.65:20247",
			"198.46.129.15:20247", "104.168.1.84:20247", "192.3.3.254:20247", "104.168.1.67:20247",
			"23.94.79.244:20247", "198.23.175.186:20247", "104.168.4.169:20247", "107.173.44.193:20247",
			"192.227.144.17:20247", "107.173.44.222:20247", "104.168.1.80:20247", "172.245.88.18:20247",
			"198.23.175.188:20247", "23.94.79.243:20247", "23.94.76.27:20247", "198.23.175.170:20247",
			"104.168.4.175:20247", "198.144.189.85:20247", "192.3.3.238:20247", "192.227.144.22:20247",
			"198.23.175.171:20247", "192.3.3.226:20247", "198.12.85.80:20247", "23.95.6.201:20247"));

	public String solveCaptchaRecaptchaV2Ru() throws InterruptedException, IOException {
		System.out.println("Sending recaptcha challenge to 2captcha.com");
		String parameters = "key=" + this.apiKey + "&method=userrecaptcha" + "&googlekey=" + this.googleKey
				+ "&pageurl=" + this.pageUrl + "&soft_id=2134592&invisible=1";

//		if (!getPageUrl().contains("moparscape")) {
//			if (this.proxyIp == null || this.proxyUser == null) {
//				String ip = ips.get(RandomUtil.getRandomNumberInRange(0, ips.size() - 1));
//				String[] format = ip.split(":");
//				this.proxyIp = format[0];
//				this.proxyPort = format[1];
//				this.proxyType = ProxyType.HTTP;
//
//				System.out.println("Using proxy: " + this.proxyIp + " " + this.proxyPort + " type: " + this.proxyType);
//			}
//		}

		if (this.proxyIp != null) {
			if (this.proxyUser != null) {
				parameters = parameters + "&proxy=" + this.proxyUser + ":" + this.proxyPw + "@" + this.proxyIp + ":"
						+ this.proxyPort;
			} else {
				parameters = parameters + "&proxy=" + this.proxyIp + ":" + this.proxyPort;
			}

			parameters = parameters + "&proxytype=" + this.proxyType;
		}

		this.hw.get("http://rucaptcha.com/in.php?" + parameters);
		String captchaId = this.hw.getHtml().replaceAll("\\D", "");
		int timeCounter = 0;

		do {
			this.hw.get("http://rucaptcha.com/res.php?key=" + this.apiKey + "&action=get" + "&id=" + captchaId);
			Thread.sleep(1000L);
			++timeCounter;
			System.out.println("Waiting for captcha to be solved");
		} while (this.hw.getHtml().contains("NOT_READY"));

		System.out.println("It took " + timeCounter + " seconds to solve the captcha");
		String gRecaptchaResponse = this.hw.getHtml().replaceAll("OK\\|", "").replaceAll("\\n", "");
		return gRecaptchaResponse;
	}

	public String solveCaptchaRecaptchaV3(String action, String googleKey, String pageUrl)
			throws InterruptedException, IOException {
		System.out.println("Sending recaptcha V3 challenge to 2captcha.com");
		String parameters = "key=" + this.apiKey + "&method=userrecaptcha" + "&version=3" + "&googlekey=" + googleKey
				+ "&pageurl=" + pageUrl + "&action=" + action;
		// if (this.proxyIp != null) {
		// if (this.proxyUser != null) {
		// parameters = parameters + "&proxy=" + this.proxyUser + ":" + this.proxyPw +
		// "@" + this.proxyIp + ":"
		// + this.proxyPort;
		// } else {
		// parameters = parameters + "&proxy=" + this.proxyIp + ":" + this.proxyPort;
		// }
		//
		// parameters = parameters + "&proxytype=" + this.proxyType;
		// }

		this.hw.get("http://2captcha.com/in.php?" + parameters);
		String captchaId = this.hw.getHtml().replaceAll("\\D", "");
		int timeCounter = 0;

		do {
			this.hw.get("http://2captcha.com/res.php?key=" + this.apiKey + "&action=get" + "&id=" + captchaId);
			Thread.sleep(1000L);
			++timeCounter;
			System.out.println("Waiting for captcha to be solved");
		} while (this.hw.getHtml().contains("NOT_READY"));

		System.out.println("It took " + timeCounter + " seconds to solve the captcha");
		String gRecaptchaResponse = this.hw.getHtml().replaceAll("OK\\|", "").replaceAll("\\n", "");
		return gRecaptchaResponse;
	}

	public static String doPost(String url, String postData) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// build connection
			URLConnection conn = realUrl.openConnection();
			// set request properties
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// enable output and input
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			// send POST DATA
			out.print(postData);
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "/n" + line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public String solveCaptchaRecaptchaText(String base64Image, String action)
			throws InterruptedException, IOException {
		System.out.println("Sending recaptcha normal challenge to 2captcha.com");

		String parameters = "key=" + this.apiKey + "&method=base64&body=" + URLEncoder.encode(base64Image, "UTF-8")
				+ "&textinstructions=" + action + "";

		// HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		// headers.set("Content", "application/x-www-form-urlencoded");
		// headers.set("User-Agent", USER_AGENT);
		//
		// MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		// map.add("key", this.apiKey);
		// map.add("method", "base64");
		// map.add("body", base64Image);
		// map.add("textinstructions", action);
		//
		// RestTemplate restTemplate = new RestTemplate();
		//
		// HttpEntity<MultiValueMap<String, String>> request2 = new HttpEntity<>(map,
		// headers);
		//
		// ResponseEntity<String> response2 = null;
		// try {
		// response2 = restTemplate.postForEntity("http://2captcha.com/in.php?",
		// request2, String.class);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// System.out.println(response2.getStatusCodeValue());
		// System.out.println(response2.getBody());

		// String postData = URLEncoder.encode("key", "UTF-8") + "=" +
		// URLEncoder.encode(this.apiKey, "UTF-8");
		// postData += "&" + URLEncoder.encode("method", "UTF-8") + "=" +
		// URLEncoder.encode("base64", "UTF-8");
		// postData += "&" + URLEncoder.encode("body", "UTF-8") + "=" +
		// URLEncoder.encode(base64Image, "UTF-8");

		String post = doPost("http://2captcha.com/in.php?", parameters);

		// this.hw.get("http://2captcha.com/in.php?" + parameters);
		String captchaId = post.replaceAll("\\D", ""); // response2.getBody().replaceAll("\\D", "");

		int timeCounter = 0;
		System.out.println("found id: " + captchaId);

		do {
			this.hw.get("http://2captcha.com/res.php?key=" + this.apiKey + "&action=get" + "&id=" + captchaId);
			Thread.sleep(1000L);
			++timeCounter;
			System.out.println("Waiting for captcha to be solved");
		} while (this.hw.getHtml().contains("NOT_READY"));

		System.out.println("It took " + timeCounter + " seconds to solve the captcha");
		String gRecaptchaResponse = this.hw.getHtml().replaceAll("OK\\|", "").replaceAll("\\n", "");
		return gRecaptchaResponse;
	}

	public String solveCaptchaRecaptchaV2() throws InterruptedException, IOException {
		System.out.println("Sending recaptcha challenge to 2captcha.com");
		String parameters = "key=" + this.apiKey + "&method=userrecaptcha" + "&googlekey=" + this.googleKey
				+ "&pageurl=" + this.pageUrl + "&soft_id=2134592&invisible=1";
		if (this.proxyIp != null) {
			if (this.proxyUser != null) {
				parameters = parameters + "&proxy=" + this.proxyUser + ":" + this.proxyPw + "@" + this.proxyIp + ":"
						+ this.proxyPort;
			} else {
				parameters = parameters + "&proxy=" + this.proxyIp + ":" + this.proxyPort;
			}

			parameters = parameters + "&proxytype=" + this.proxyType;
		}

		this.hw.get("http://2captcha.com/in.php?" + parameters);
		String captchaId = this.hw.getHtml().replaceAll("\\D", "");
		int timeCounter = 0;

		do {
			this.hw.get("http://2captcha.com/res.php?key=" + this.apiKey + "&action=get" + "&id=" + captchaId);
			Thread.sleep(1000L);
			++timeCounter;
			System.out.println("Waiting for captcha to be solved");
		} while (this.hw.getHtml().contains("NOT_READY"));

		System.out.println("It took " + timeCounter + " seconds to solve the captcha");
		String gRecaptchaResponse = this.hw.getHtml().replaceAll("OK\\|", "").replaceAll("\\n", "");
		return gRecaptchaResponse;
	}

	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getGoogleKey() {
		return this.googleKey;
	}

	public void setGoogleKey(String googleKey) {
		this.googleKey = googleKey;
	}

	public String getPageUrl() {
		return this.pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getProxyIp() {
		return this.proxyIp;
	}

	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	public String getProxyPort() {
		return this.proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUser() {
		return this.proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public String getProxyPw() {
		return this.proxyPw;
	}

	public void setProxyPw(String proxyPw) {
		this.proxyPw = proxyPw;
	}

	public ProxyType getProxyType() {
		return this.proxyType;
	}

	public void setProxyType(ProxyType proxyType) {
		this.proxyType = proxyType;
	}

	/**
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * @param userAgent the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}