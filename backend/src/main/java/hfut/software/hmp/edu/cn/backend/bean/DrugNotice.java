package hfut.software.hmp.edu.cn.backend.bean;

public class DrugNotice {
    public int id;
    public String robot;
    public String room;
    public String bed;
    public String drugName;
    public String morningTime;
    public String morningAmount;
    public String noonTime;
    public String noonAmount;

    public DrugNotice(String robot, String room, String bed, String drugName, String morningTime, String morningAmount, String noonTime, String noonAmount, String eveningTime, String eveningAmount, String attention, String releaseMen) {
        this.robot = robot;
        this.room = room;
        this.bed = bed;
        this.drugName = drugName;
        this.morningTime = morningTime;
        this.morningAmount = morningAmount;
        this.noonTime = noonTime;
        this.noonAmount = noonAmount;
        this.eveningTime = eveningTime;
        this.eveningAmount = eveningAmount;
        this.attention = attention;
        this.releaseMen = releaseMen;
        this.releaseTime = releaseTime;
    }

    public String eveningTime;
    public String eveningAmount;
    public String attention;
    public String releaseMen;
    public String releaseTime;
    public String state;
    public String real_name;

    public DrugNotice(){

    }

    public DrugNotice(int id, String robot, String room, String bed, String drugName,
                      String morningTime, String morningAmount,
                      String noonTime, String noonAmount,
                      String eveningTime, String eveningAmount,
                      String attention, String releaseMen, String realseTime,
                      String realName) {
        this.id = id;
        this.robot = robot;
        this.room = room;
        this.bed = bed;
        this.drugName = drugName;
        this.morningTime = morningTime;
        this.morningAmount = morningAmount;
        this.noonTime = noonTime;
        this.noonAmount = noonAmount;
        this.eveningTime = eveningTime;
        this.eveningAmount = eveningAmount;
        this.attention = attention;
        this.releaseMen = releaseMen;
        this.releaseTime = realseTime;
        this.real_name = realName;
    }

    public DrugNotice(int id, String robot, String room, String bed, String drugName,
                      String morningTime, String morningAmount,
                      String noonTime, String noonAmount,
                      String eveningTime, String eveningAmount,
                      String attention, String releaseMen, String realseTime, String state,
                      String realName) {
        this.id = id;
        this.robot = robot;
        this.room = room;
        this.bed = bed;
        this.drugName = drugName;
        this.morningTime = morningTime;
        this.morningAmount = morningAmount;
        this.noonTime = noonTime;
        this.noonAmount = noonAmount;
        this.eveningTime = eveningTime;
        this.eveningAmount = eveningAmount;
        this.attention = attention;
        this.releaseMen = releaseMen;
        this.releaseTime = realseTime;
        this.state = state;
        this.real_name = realName;
    }
}
