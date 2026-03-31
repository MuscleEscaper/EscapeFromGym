/**
 * ヒントのテキストと背景画像パスを管理するデータクラス
 */
public class HintData {
    // ヒントメッセージを以下に羅列
    // シナリオの順序はこの配列のインデックスに対応
    private static final String[] HINT_MESSAGES = {
            "", // 0
            "", // 1
            "有効な筋トレ方法は分割法です。胸、肩、背中、腕、脚に分けて鍛えてください。", // 2
            "胸を鍛えるにはベンチプレスが有効です。", // 3
            "", // 4
            "", // 5
            "背中を鍛えるにはラットプルダウンが有効です。", // 6
            "腕を鍛えるにはダンベルにてサイドレイズです。", // 7
            "", // 8
            "腕を鍛えるにはEZバーを使うのが良いとされています。", // 9
            "", // 10
            "ライイングエクステンションはインクラインベンチで行います。", // 11
            "脚トレはバーベルを使います。", // 12
            "電通大ごとぶっ壊しましょう。" // 13
    };

    // ヒント画面の背景画像パス
    private static final String HINT_IMAGE_PATH = "assets/images/HintBackGround.png";

    public static String getHintMessage(int progress) {
        if (progress >= 0 && progress < HINT_MESSAGES.length) {
            if (HINT_MESSAGES[progress] == "")
                return "筋トレ以外のことはお答えできません。";
            return HINT_MESSAGES[progress];
        }
        return "今はその時ではない。";
    }

    public static String getHintImagePath() {
        return HINT_IMAGE_PATH;
    }
}