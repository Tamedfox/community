package find.cf.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(1001,"你找的问题坠落沙漠了，快去找你的玫瑰吧~"),
    TARGET_NOT_FOUND(1002,"评论的帖子不见了~"),
    NO_LOGIN(1003,"未登录~"),
    SYS_ERROR(1004,"服务器长满了猴面包树，正在清理~" ),
    TYPE_WRONG(1005,"评论类型错误"),
    NO_COMMENT(1006,"评论不见了"),
    NO_QUESTION(1007,"问题不见了" ),
    NO_CONTENT(1008,"没有内容" );

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return null;
    }


}
