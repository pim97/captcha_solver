package captchaSolver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class DbUtilities {

	public static boolean keepCaptchasClean() {
		try {
			String query = "DELETE FROM captcha WHERE created_at < SUBDATE(NOW(), INTERVAL 1 HOUR)";
			PreparedStatement preparedStmt = DatabaseConnection.getDatabase().getConnection().prepareStatement(query);
			preparedStmt.executeUpdate();
			System.out.println("Keeping votes clean...");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean updateAttempts(int id) {

		try {
			String query = "UPDATE captcha SET attempts = 1 WHERE id=?";
			PreparedStatement preparedStmt = DatabaseConnection.getDatabase().getConnection().prepareStatement(query);
			preparedStmt.setInt(1, id);
			preparedStmt.executeUpdate();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean updateCaptchaAnswer(int id, String answer) {

		try {
			String query = "UPDATE captcha SET solved_key=? WHERE id=?";
			PreparedStatement preparedStmt = DatabaseConnection.getDatabase().getConnection().prepareStatement(query);
			preparedStmt.setString(1, answer);
			preparedStmt.setInt(2, id);
			preparedStmt.executeUpdate();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static CopyOnWriteArrayList<Captcha> getCaptchasThatHaveToBeSolved() {
		String sql = "SELECT * FROM captcha WHERE solved_key IS NULL AND attempts = 0";
		CopyOnWriteArrayList<Captcha> captchasToSolve = new CopyOnWriteArrayList<Captcha>();

		try {
			PreparedStatement preparedStatement = DatabaseConnection.getDatabase().getConnection()
					.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery(sql);

			try {
				while (resultSet.next()) {

					int id = resultSet.getInt("id");
					String apiKey = resultSet.getString("api_key");
					String googleKeyOnWebsite = resultSet.getString("google_key_on_website");
					String pageUrl = resultSet.getString("page_url");
					String proxyUser = resultSet.getString("proxy_user");
					String proxyPassword = resultSet.getString("proxy_password");
					String proxyIp = resultSet.getString("proxy_ip");
					String proxyPort = resultSet.getString("proxy_port");
					String proxyType = resultSet.getString("proxy_type");
					String solved_key = resultSet.getString("solved_key");
					int attempts = resultSet.getInt("attempts");

					Captcha captcha = new Captcha(id, apiKey, googleKeyOnWebsite, pageUrl, proxyUser, proxyPassword,
							proxyPort, proxyType, solved_key, attempts, proxyIp);
//					System.out.println(captcha.toString());

					captchasToSolve.add(captcha);

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				resultSet.close();
				preparedStatement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return captchasToSolve;
	}

}
