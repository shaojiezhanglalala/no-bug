package com.smartfactory.model.entity;

public class Equipmentinfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column equipmentinfo.eqpid
     *
     * @mbg.generated Thu Jul 23 22:11:57 CST 2020
     */
    private Integer eqpid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column equipmentinfo.eqpname
     *
     * @mbg.generated Thu Jul 23 22:11:57 CST 2020
     */
    private String eqpname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column equipmentinfo.eqpcond
     *
     * @mbg.generated Thu Jul 23 22:11:57 CST 2020
     */
    private String eqpcond;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column equipmentinfo.eqppic
     *
     * @mbg.generated Thu Jul 23 22:11:57 CST 2020
     */
    private String eqppic;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column equipmentinfo.eqpid
     *
     * @return the value of equipmentinfo.eqpid
     *
     * @mbg.generated Thu Jul 23 22:11:57 CST 2020
     */
    public Integer getEqpid() {
        return eqpid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column equipmentinfo.eqpid
     *
     * @param eqpid the value for equipmentinfo.eqpid
     *
     * @mbg.generated Thu Jul 23 22:11:57 CST 2020
     */
    public void setEqpid(Integer eqpid) {
        this.eqpid = eqpid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column equipmentinfo.eqpname
     *
     * @return the value of equipmentinfo.eqpname
     *
     * @mbg.generated Thu Jul 23 22:11:57 CST 2020
     */
    public String getEqpname() {
        return eqpname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column equipmentinfo.eqpname
     *
     * @param eqpname the value for equipmentinfo.eqpname
     *
     * @mbg.generated Thu Jul 23 22:11:57 CST 2020
     */
    public void setEqpname(String eqpname) {
        this.eqpname = eqpname == null ? null : eqpname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column equipmentinfo.eqpcond
     *
     * @return the value of equipmentinfo.eqpcond
     *
     * @mbg.generated Thu Jul 23 22:11:57 CST 2020
     */
    public String getEqpcond() {
        return eqpcond;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column equipmentinfo.eqpcond
     *
     * @param eqpcond the value for equipmentinfo.eqpcond
     *
     * @mbg.generated Thu Jul 23 22:11:57 CST 2020
     */
    public void setEqpcond(String eqpcond) {
        this.eqpcond = eqpcond == null ? null : eqpcond.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column equipmentinfo.eqppic
     *
     * @return the value of equipmentinfo.eqppic
     *
     * @mbg.generated Thu Jul 23 22:11:57 CST 2020
     */
    public String getEqppic() {
        return eqppic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column equipmentinfo.eqppic
     *
     * @param eqppic the value for equipmentinfo.eqppic
     *
     * @mbg.generated Thu Jul 23 22:11:57 CST 2020
     */
    public void setEqppic(String eqppic) {
        this.eqppic = eqppic == null ? null : eqppic.trim();
    }

	@Override
	public String toString() {
		return "Equipmentinfo [eqpid=" + eqpid + ", eqpname=" + eqpname + ", eqpcond=" + eqpcond + ", eqppic=" + eqppic
				+ "]";
	}

	public Equipmentinfo(Integer eqpid, String eqpname, String eqpcond, String eqppic) {
		super();
		this.eqpid = eqpid;
		this.eqpname = eqpname;
		this.eqpcond = eqpcond;
		this.eqppic = eqppic;
	}

	public Equipmentinfo() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}