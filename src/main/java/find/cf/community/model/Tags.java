package find.cf.community.model;

public class Tags {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TAGS.ID
     *
     * @mbg.generated Sun Aug 25 11:56:17 CST 2019
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TAGS.NAME
     *
     * @mbg.generated Sun Aug 25 11:56:17 CST 2019
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TAGS.PARENT_ID
     *
     * @mbg.generated Sun Aug 25 11:56:17 CST 2019
     */
    private Long parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TAGS.REMARIK
     *
     * @mbg.generated Sun Aug 25 11:56:17 CST 2019
     */
    private String remarik;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TAGS.ID
     *
     * @return the value of TAGS.ID
     *
     * @mbg.generated Sun Aug 25 11:56:17 CST 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TAGS.ID
     *
     * @param id the value for TAGS.ID
     *
     * @mbg.generated Sun Aug 25 11:56:17 CST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TAGS.NAME
     *
     * @return the value of TAGS.NAME
     *
     * @mbg.generated Sun Aug 25 11:56:17 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TAGS.NAME
     *
     * @param name the value for TAGS.NAME
     *
     * @mbg.generated Sun Aug 25 11:56:17 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TAGS.PARENT_ID
     *
     * @return the value of TAGS.PARENT_ID
     *
     * @mbg.generated Sun Aug 25 11:56:17 CST 2019
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TAGS.PARENT_ID
     *
     * @param parentId the value for TAGS.PARENT_ID
     *
     * @mbg.generated Sun Aug 25 11:56:17 CST 2019
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TAGS.REMARIK
     *
     * @return the value of TAGS.REMARIK
     *
     * @mbg.generated Sun Aug 25 11:56:17 CST 2019
     */
    public String getRemarik() {
        return remarik;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TAGS.REMARIK
     *
     * @param remarik the value for TAGS.REMARIK
     *
     * @mbg.generated Sun Aug 25 11:56:17 CST 2019
     */
    public void setRemarik(String remarik) {
        this.remarik = remarik == null ? null : remarik.trim();
    }
}