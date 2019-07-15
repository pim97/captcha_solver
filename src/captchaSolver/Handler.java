package captchaSolver;

public class Handler {

	public static CaptchaHandler captchaHandler = new CaptchaHandler();
	
	public static void main(String args[]) {
		captchaHandler.startCaptchaDatabaseThread();
		captchaHandler.startCaptchaSolverThread();
		captchaHandler.keepLogsClean();
	}
	
}
