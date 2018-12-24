package us.myles_selim.starota;

public class MiscUtils {

	public static String getTrainerCodeString(long trainerCode) {
		String codeS = String.format("%012d", trainerCode);
		return codeS.substring(0, 4) + " " + codeS.substring(4, 8) + " " + codeS.substring(8, 12);
	}

}
