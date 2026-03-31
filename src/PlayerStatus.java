/**
 * playerのstatusを管理する。
 * それぞれの部位の変数は2値で表され、0が弱い、1が強いとする。
 */

public class PlayerStatus {
    private static PlayerStatus INSTANCE = new PlayerStatus();
    private boolean shoulder;
    private boolean back;
    private boolean chest;
    private boolean arm;
    private boolean leg;

    public PlayerStatus() {
        this.shoulder = false;
        this.back = false;
        this.chest = false;
        this.arm = false;
        this.leg = false;
    }

    public static PlayerStatus getInstance() {
        return INSTANCE;
    }

    public void setShoulderVal(boolean shoulder) {
        this.shoulder = shoulder;
    }

    public void setBackVal(boolean back) {
        this.back = back;
    }

    public void setChestVal(boolean chest) {
        this.chest = chest;
    }

    public void setArmVal(boolean arm) {
        this.arm = arm;
    }

    public void setLegVal(boolean leg) {
        this.leg = leg;
    }

    public boolean getShoulderVal() {
        return this.shoulder;
    }

    public boolean getBackVal() {
        return this.back;
    }

    public boolean getChestVal() {
        return this.chest;
    }

    public boolean getArmVal() {
        return this.arm;
    }

    public boolean getLegVal() {
        return this.leg;
    }
}
