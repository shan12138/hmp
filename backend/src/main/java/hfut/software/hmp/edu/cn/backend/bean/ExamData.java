package hfut.software.hmp.edu.cn.backend.bean;

public class ExamData {

    public String hour1;
    public String hour2;
    public String hour3;
    public String hour4;
    public String hour5;
    public String hour6;
    public String hour7;
    public String hour8;
    public String day;
    public String avg;

    public ExamData(String hour1, String hour2, String hour3,
                    String hour4, String hour5, String hour6, String hour7, String hour8) {
        this.hour1 = hour1;
        this.hour2 = hour2;
        this.hour3 = hour3;
        this.hour4 = hour4;
        this.hour5 = hour5;
        this.hour6 = hour6;
        this.hour7 = hour7;
        this.hour8 = hour8;
    }

    public ExamData(String day, String avg){
        this.day = day;
        this.avg = avg;
    }

}
