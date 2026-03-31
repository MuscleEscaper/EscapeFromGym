import java.awt.Rectangle;

/**
 * クリック判定領域クラス
 * 当たり判定はRectangleで表現している
 */
public class HitDetectionArea {
    private String name;
    private ItemData itemData;
    private Rectangle bounds;

    private IActionOnClick gameActionOnClick; // 通常時
    private IActionOnClick actionOnItemUse; // 特定のアイテム使用時
    private static IActionOnClick defaultActionOnItemUse; // 異なる場所を押した場合

    public static void setDefaultActionOnItemUse(IActionOnClick action) {
        defaultActionOnItemUse = action;
    }

    /**
     * itemの当たり判定生成コンストラクタ
     * nameはitemDataから直接取得
     * 
     * @param itemData          ItemData
     * @param upperLeftX        上左のx座標
     * @param upperLeftY        上左のy座標
     * @param lowerRightX       下右のx座標
     * @param lowerRightY       下右のy座標
     * @param gameActionOnClick 通常時
     */
    public HitDetectionArea(ItemData itemData, int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY,
            IActionOnClick gameActionOnClick) {
        this.itemData = itemData;
        this.bounds = new Rectangle(upperLeftX, upperLeftY, lowerRightX - upperLeftX, lowerRightY - upperLeftY);
        this.name = itemData.getName();
        this.gameActionOnClick = gameActionOnClick;
    }

    /**
     * 通常の当たり判定生成コンストラクタ
     * 
     * @param name              名前
     * @param upperLeftX        上左のx座標
     * @param upperLeftY        上左のy座標
     * @param lowerRightX       下右のx座標
     * @param lowerRightY       下右のy座標
     * @param gameActionOnClick 通常時
     */
    public HitDetectionArea(String name, int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY,
            IActionOnClick gameActionOnClick) {
        this.name = name;
        this.itemData = null;
        this.bounds = new Rectangle(upperLeftX, upperLeftY, lowerRightX - upperLeftX, lowerRightY - upperLeftY);
        this.gameActionOnClick = gameActionOnClick;
    }

    /**
     * アイテム使用時のアクション設定
     * メソッドチェーンに対応
     */
    public HitDetectionArea setOnItemUse(IActionOnClick action) {
        this.actionOnItemUse = action;
        return this;
    }

    /**
     * 座標が当たり判定を含んでいるか
     */
    public boolean contains(int x, int y) {
        return bounds.contains(x, y);
    }

    /**
     * クリック時の処理実行
     */
    public void onClick() {
        // アイテムを持っている場合
        if (InventoryManage.getInstance().haveHoldingItem()) {
            if (actionOnItemUse != null) // アイテム使用時かつ正しい場合
                actionOnItemUse.onClick();
            else if (defaultActionOnItemUse != null) // アイテム使用時だが、正しくない場合
                defaultActionOnItemUse.onClick();
            return;
        }
        if (gameActionOnClick != null) // 通常時
            gameActionOnClick.onClick();
    }

    public String getName() {
        return name;
    }

    public ItemData getItemData() {
        return itemData;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}