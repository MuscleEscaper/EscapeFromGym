//　以下にアイテムの定義を羅列
class Items {
    public static ItemData smartPhone = new ItemData("スマートフォン", null, "assets/images/Item3D/SmartPhone.png");
    public static ItemData barbell = new ItemData("バーベル", "これだけで20kgある。", "assets/images/Item3D/Barbell.png");
    public static ItemData pin = new ItemData("ピン", "何かに刺せそうだ。", "assets/images/Item3D/Pin.PNG");
    public static ItemData ezBar = new ItemData("EZバー", "腕を鍛えるには持ってこいだ。", "assets/images/Item3D/EZBar.PNG");
}

public class Scenes {
    private MainViewPanel mainViewPanel;
    private OnClickLogics logic;

    public Scenes(MainViewPanel mainViewPanel, TextPanel textPanel) {
        this.mainViewPanel = mainViewPanel;
        this.logic = new OnClickLogics(mainViewPanel, textPanel);

        HitDetectionArea.setDefaultActionOnItemUse(() -> {
            logic.showMessage("ここでは使えないようだ。");
            InventoryManage.getInstance().resetIsHolding();
        });

        initScenes();
    }

    private void initScenes() {
        // 以下にシーンを羅列する
        // tip: ラムダ式記述時、Progressを呼び出す前にメッセージを表示することで順序が正しくなる

        // パワーラックシーン
        ScenePanel powerRackScene = new ScenePanel("assets/images/Scene/PowerRackScene.jpg", mainViewPanel);
        powerRackScene.addHitDetectionArea(
                new HitDetectionArea("パワーラック", 195, 140, 590, 460,
                        () -> {
                            logic.showMessage("[b]パワーラック[/b]がある。");
                        })
                        .setOnItemUse(() -> {
                            logic.useItem(Items.barbell, () -> {
                                if (GameProgress.getInstance().getProgress() == 12) {
                                    logic.Progress(12);
                                    PlayerStatus.getInstance().setLegVal(true);
                                } else {
                                    logic.showMessage("今はその時ではない。");
                                }
                            });
                        }));
        powerRackScene.addHitDetectionArea(
                new HitDetectionArea(Items.smartPhone, 30, 400, 90, 450,
                        () -> {
                            logic.Progress(0);
                            powerRackScene.removeHitDetectionAreaByName(Items.smartPhone.getName());
                        }));
        mainViewPanel.addScene(powerRackScene, "PowerRackScene");

        // ダンベルシーン
        ScenePanel dumbbellScene = new ScenePanel("assets/images/Scene/DumbbellScene.jpg", mainViewPanel);
        dumbbellScene.addHitDetectionArea(new HitDetectionArea("ダンベルラック", 215, 235, 530, 355, () -> {
            PlayerStatus.getInstance().setShoulderVal(true);
            logic.showMessage("ダンベルが並んでいる。\n 重さの順番は左から[b]2kg 7kg 4kg 1kg[/b]だ。");
            logic.Progress(7);

        }));
        dumbbellScene.addHitDetectionArea(new HitDetectionArea(Items.barbell, 200, 380, 550, 500, () -> {
            dumbbellScene.removeHitDetectionAreaByName(Items.barbell.getName());
            logic.acquireItem(Items.barbell);
            logic.Progress(4);
            logic.skipProgress(5);
        }));
        mainViewPanel.addScene(dumbbellScene, "DumbbellScene");

        // ベンチプレスシーン
        ScenePanel benchPressScene = new ScenePanel("assets/images/Scene/BenchPressScene.jpg", mainViewPanel);
        benchPressScene.addHitDetectionArea(
                new HitDetectionArea("ベンチプレス", 170, 300, 650, 460,
                        () -> {
                            logic.showMessage("ベンチプレスがあるが[red]バーベル[/red]がない。");
                            logic.Progress(3);
                        })
                        // バーベルを持っている場合
                        .setOnItemUse(() -> {
                            logic.useItem(Items.barbell, () -> {
                                PlayerStatus.getInstance().setChestVal(true);
                                logic.Progress(5);
                                logic.skipProgress(6);
                                benchPressScene.addHitDetectionArea(
                                        new HitDetectionArea(Items.barbell, 170, 179, 650, 299, () -> {
                                            benchPressScene.removeHitDetectionAreaByName(Items.barbell.getName());
                                            logic.acquireItem(Items.barbell);
                                        }));
                            });
                        }));
        mainViewPanel.addScene(benchPressScene, "BenchPressScene");

        // ドアシーン
        ScenePanel doorScene = new ScenePanel("assets/images/Scene/DoorScene.jpg", mainViewPanel);
        doorScene.addHitDetectionArea(
                new HitDetectionArea("ドア", 443, 40, 760, 367,
                        () -> {
                            logic.showMessage("壊せそうなドアがある。[b]全身[/b]が強くなれば壊せそうだ。");
                            logic.Progress(1);
                            if (GameProgress.getInstance().getProgress() == 13)
                                logic.showEnding();
                        }));

        doorScene.addHitDetectionArea(
                new HitDetectionArea("南京錠2", 45, 300, 420, 480,
                        () -> {
                            logic.showMessage("ダイヤルには[red]4色の色[/red]がついているようだ。");
                            logic.openLock(() -> {
                                doorScene.removeHitDetectionAreaByName("南京錠2");
                                logic.acquireItem(Items.ezBar);
                                logic.Progress(10);
                                logic.skipProgress(11);
                            }, 2);
                        }));
        mainViewPanel.addScene(doorScene, "DoorScene");

        // ロッカーシーン
        ScenePanel lockerScene = new ScenePanel("assets/images/Scene/LockerScene.PNG", mainViewPanel);
        lockerScene.addHitDetectionArea(
                new HitDetectionArea("南京錠", 30, 130, 185, 395,
                        () -> {
                            logic.showMessage("このダイヤルなんか模様があるな...。");
                            logic.openLock(() -> {
                                doorScene.removeHitDetectionAreaByName("南京錠");
                                logic.acquireItem(Items.pin);
                            }, 1);
                        }));
        lockerScene.addHitDetectionArea(new HitDetectionArea("ロッカー", 185, 50, 750, 395, () -> {
            logic.showMessage("ロッカーがある。\n [red]脆い[/red]ドアが付いていて、[b]3部位[/b]強くなった時壊せそうだ。");
            if (GameProgress.getInstance().getProgress() == 9 || (PlayerStatus.getInstance().getShoulderVal()
                    && PlayerStatus.getInstance().getBackVal() && PlayerStatus.getInstance().getChestVal())) {
                lockerScene.changeImage("assets/images/Scene/BrokenLockerScene.jpg");
                logic.Progress(9);
            }
        }));
        mainViewPanel.addScene(lockerScene, "LockerScene");

        // ランニングマシンシーン
        ScenePanel runningMachineScene = new ScenePanel("assets/images/Scene/RunningMachineScene.jpg", mainViewPanel);
        runningMachineScene.addHitDetectionArea(new HitDetectionArea("ランニングマシン", 0, 200, 730, 450, () -> {
            logic.showMessage("ランニングマシンがある。");
        }));
        mainViewPanel.addScene(runningMachineScene, "RunningMachineScene");

        // ラットプルダウンシーン
        ScenePanel latPulldownScene = new ScenePanel("assets/images/Scene/LatPulldownScene.jpg", mainViewPanel);
        latPulldownScene.addHitDetectionArea(
                new HitDetectionArea("ラットプルダウン", 75, 62, 530, 440,
                        () -> {
                            logic.showMessage("ラットプルダウンがあるが、[red]ピン[/red]がないようだ。");
                            logic.Progress(6);
                        })
                        // ピン使用時
                        .setOnItemUse(() -> {
                            logic.useItem(Items.pin, () -> {
                                logic.Progress(8);
                                logic.skipProgress(9);
                                PlayerStatus.getInstance().setBackVal(true);
                            });
                        }));
        mainViewPanel.addScene(latPulldownScene, "LatPulldownScene");

        // インクラインベンチシーン
        ScenePanel inclineBenchScene = new ScenePanel("assets/images/Scene/InclineBenchScene.jpg", mainViewPanel);
        inclineBenchScene.addHitDetectionArea(new HitDetectionArea("インクラインベンチ", 100, 100, 500, 450, () -> {
            logic.showMessage("インクラインベンチがある。");
        })
                // EZバー使用時
                .setOnItemUse(() -> {
                    logic.useItem(Items.ezBar, () -> {
                        logic.Progress(11);
                        PlayerStatus.getInstance().setArmVal(true);
                    });
                }));
        mainViewPanel.addScene(inclineBenchScene, "InclineBenchScene");
    }
}