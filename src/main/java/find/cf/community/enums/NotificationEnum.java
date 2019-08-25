package find.cf.community.enums;

public enum NotificationEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论");
    private Integer type;
    private String status;

    public Integer getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    NotificationEnum(Integer type, String status) {
        this.type = type;
        this.status = status;
    }

    public static String nameOfType(int type){
        for (NotificationEnum notificationEnum : NotificationEnum.values()) {
            if(notificationEnum.getType() == type){
                return notificationEnum.getStatus();
            }
        }
        return "";
    }
}
