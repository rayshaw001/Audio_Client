package ray.shaw.Bean;

import java.util.GregorianCalendar;

public class InfoBean {
	private String FilePath = null;
	private GregorianCalendar TimeLine = null;
	public String getFilePath() {
		return FilePath;
	}
	public GregorianCalendar getTimeLine() {
		return TimeLine;
	}
	public void setFilePath(String filePath) {
		FilePath = filePath;
	}
	public void setTimeLine(GregorianCalendar timeLine) {
		TimeLine = timeLine;
	}
}
