package find.cf.community.enums;

public enum CommentTypeEnum {

    QUESTION(1),
    COMMENT(2);

    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExits(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if(type == value.getType()){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
