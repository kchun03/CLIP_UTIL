package playKorean;

/////////////////////////////////////////////////
//0xAC00 ~ 0xD7A3 (11172 자)
//
//초성: ㄱ ㄲ ㄴ ㄷ ㄸ ㄹ ㅁ ㅂ ㅃ ㅅ ㅆ ㅇ ㅈ ㅉ ㅊ ㅋ ㅌ ㅍ ㅎ
//중성: ㅏ ㅐ ㅑ ㅒ ㅓ ㅔ ㅕ ㅖ ㅗ ㅘ ㅙ ㅚ ㅛ ㅜ ㅝ ㅞ ㅟ ㅠ ㅡ ㅢ ㅣ
//종성: fill ㄱ ㄲ ㄳ ㄴ ㄵ ㄶ ㄷ ㄹ ㄺ ㄻ ㄼ ㄽ ㄾ ㄿ ㅀ ㅁ ㅂ ㅄ ㅅ ㅆ ㅇ ㅈ ㅊ ㅋ ㅌ ㅍ ㅎ
/////////////////////////////////////////////////

public class SplitHangulJaMo {

	private static char[] cho = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ',
			'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };
	private static char[] jung = { 'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ',
			'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ' };
	private static char[] jong = { ' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ',
			'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ',
			'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };

	public static String split(char a) {
		int x = (int) (a & 0xFFFF);
		int y, z, x1, x2, x3;
		if (x >= 0xAC00 && x <= 0xD7A3) {
			y = x - 0xAC00;
			x1 = y / (21 * 28);
			z = y % (21 * 28);
			x2 = z / 28;
			x3 = z % 28;
			if (x3 == 0)
				return "(" + cho[x1] + "," + jung[x2] + ")";
			else
				return "(" + cho[x1] + "," + jung[x2] + "," + jong[x3] + ")";
		} else
			return "" + a;
	}

	public static String split(String s) {
		if (s == null)
			return null;

		char a;
		String t = "";
		for (int i = 0; i < s.length(); i++) {
			a = s.charAt(i);
			t += split(a);
		}
		return t;
	}

	public static void main(String[] args) {
		System.out.println(split('한'));
		System.out.println(split("안녕하세요?"));
	}
}