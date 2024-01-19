package vip.yeee.app.common.constant;

/**
 * description......
 * @author yeeee
 */
public enum SysMenuTypeEnum {

    menu(0, "菜单"),
    func(1, "功能"),
    pseudoMenu(2, "伪菜单");

    private final int code;

    private final String name;

    SysMenuTypeEnum(int cd, String nm) {
        this.code = cd;
        this.name = nm;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
