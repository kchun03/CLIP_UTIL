package playKorean;

/**
 * @(#) CombineHangulJaMo.java
 * 
 * @date 2001/04/20
 * @author Pilho Kim [phkim@cluecom.co.kr]
 */

// ///////////////////////////////////////////////
// 0xAC00 ~ 0xD7A3 (11172 자)
//
// 초성: ㄱ ㄲ ㄴ ㄷ ㄸ ㄹ ㅁ ㅂ ㅃ ㅅ ㅆ ㅇ ㅈ ㅉ ㅊ ㅋ ㅌ ㅍ ㅎ
// 중성: ㅏ ㅐ ㅑ ㅒ ㅓ ㅔ ㅕ ㅖ ㅗ ㅘ ㅙ ㅚ ㅛ ㅜ ㅝ ㅞ ㅟ ㅠ ㅡ ㅢ ㅣ
// 종성: fill ㄱ ㄲ ㄳ ㄴ ㄵ ㄶ ㄷ ㄹ ㄺ ㄻ ㄼ ㄽ ㄾ ㄿ ㅀ ㅁ ㅂ ㅄ ㅅ ㅆ ㅇ ㅈ ㅊ ㅋ ㅌ ㅍ ㅎ
// ///////////////////////////////////////////////
public class CombineHangulJaMo {

	public static char combine(int x1, int x2, int x3) {
		int x = (x1 * 21 * 28) + (x2 * 28) + x3;
		return (char) (x + 0xAC00);
	}

	public static void main(String[] args) {
		System.out.println("" + combine(0, 0, 0));
		System.out.println("" + combine(0, 0, 1));
		System.out.println("" + combine(0, 0, 2));
		System.out.println("" + combine(0, 0, 3));
		System.out.println("" + combine(0, 0, 4));
	}
}
