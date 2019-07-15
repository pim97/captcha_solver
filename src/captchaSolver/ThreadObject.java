package captchaSolver;

public class ThreadObject {

	private int maxThreads;

	/**
	 * @return the maxThreads
	 */
	public int getMaxThreads() {
		return maxThreads;
	}

	public ThreadObject(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	/**
	 * @param maxThreads the maxThreads to set
	 */
	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}
}
