package captchaSolver;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import com.twocaptcha.api.ProxyType;

public class CaptchaHandler {

	private static long startTimeOfProgram;

	static {
		startTimeOfProgram = System.currentTimeMillis();
	}

	/**
	 * List with all the current captcha's that have to be solved
	 */
	public CopyOnWriteArrayList<Captcha> captchasToSolve = new CopyOnWriteArrayList<Captcha>();

	/**
	 * Seperate method for sleep
	 * 
	 * @param time
	 */
	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int getCaptchasToSolveSize() {
		return captchasToSolve.size();
	}

	private int getSleepBasedOnStartup() {
		long diff = System.currentTimeMillis() - startTimeOfProgram;

		if (diff < 60_000) {
			return 1000;
		}
		return 150;
	}

	public boolean mayStartAnotherSolvingThread() {
		int alive = ThreadSystem.getAddedThreadsToProgramAndAlive();

		if (alive > 125) {
			return false;
		}
		return true;
	}

	/**
	 * Thread for getting the solved captcha's
	 */
	public void startCaptchaSolverThread() {
		Thread captchaSolver = new Thread(() -> {
			System.out.println("Starting thread captcha solver");

			while (true) {

				for (Captcha captcha : captchasToSolve) {

					if (captcha.getAttempts() > 0) {
						continue;
					}

					if (!mayStartAnotherSolvingThread()) {
						System.out.println("Too many threads active... waiting for captcha's to be solved");
						try {
							Thread.sleep(1_000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}

					Thread solverThread = new Thread(() -> {

						if (captcha.getAttempts() > 0) {
							return;
						}

						DbUtilities.updateAttempts(captcha.getId());
						captcha.setAttempts(captcha.getAttempts() + 1);

						TwoCaptchaSolver solver = null;

						if (captcha.getProxy_type() == null) {
							solver = new TwoCaptchaSolver(captcha.getApi_key(), captcha.getGoogle_key_on_website(),
									captcha.getPage_url());
						} else {
							solver = new TwoCaptchaSolver(captcha.getApi_key(), captcha.getGoogle_key_on_website(),
									captcha.getPage_url(), captcha.getProxy_ip(), captcha.getProxy_port(),
									captcha.getProxy_user(), captcha.getProxy_password(),
									ProxyType.valueOf(captcha.getProxy_type()));
						}

						System.out.println("Starting to solve captcha: " + captcha.toString());

						try {
							String answer = solver.solveCaptchaRecaptchaV2Ru();

							DbUtilities.updateCaptchaAnswer(captcha.getId(), answer);

						} catch (InterruptedException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					});
					int random = RandomUtil.getRandomNumberInRange(1, Integer.MAX_VALUE);
					solverThread.setName("solving" + random);
					solverThread.start();
					ThreadSystem.addAddedToThread("solving" + random, solverThread);

					sleep(getSleepBasedOnStartup());
				}

				sleep(500);
			}

		});
		captchaSolver.setName("captchaSolver");
		captchaSolver.start();
	}

	/**
	 * Keeping the logs in the database clean to increase performance
	 */
	public void keepLogsClean() {
		Thread captchaCleanThread = new Thread(() -> {

			while (true) {

				DbUtilities.keepCaptchasClean();

				sleep(360_000);
			}
		});
		captchaCleanThread.setName("captchaCleanThread");
		captchaCleanThread.start();
	}

	/**
	 * Starts a new database thread to get the captcha's that have to be solved
	 */
	public void startCaptchaDatabaseThread() {
		Thread captchas = new Thread(() -> {
			System.out.println("Starting thread captchas");
			while (true) {

				captchasToSolve = DbUtilities.getCaptchasThatHaveToBeSolved();

				sleep(500);
			}
		});
		captchas.setName("captchas");
		captchas.start();
	}

}
