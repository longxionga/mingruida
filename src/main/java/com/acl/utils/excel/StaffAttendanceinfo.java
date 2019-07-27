package com.acl.utils.excel;

import java.io.Serializable;

public class StaffAttendanceinfo implements Serializable {
    private String id;//
    private String attendance_id;//考勤id
    private String staff_name;// 员工姓名
    private String staff_name_n;//员工名称
    private String department ;// 部门
    private String staff_code ;// 工号
    private String position  ;// 职位
    private String entry_time;// 入职时间
    private String departure_time;//离职时间
    private String state ;// 状态 01 在职 02离职
    private String attendance_days;//出勤天数
    private String rest_days;// 休息天数
    private String working_hours;//工作时长
    private String late_ness  ;//迟到次数
    private String late_arrival_time;// 迟到时长
    private String severe_lateness ;//严重迟到次数
    private String severe_arrival_time;//严重迟到时长
    private String ads_late_days;//旷工迟到天数
    private String early_time;// 早退次数
    private String early_hours;//早退时长
    private String goto_absence_num;//上班缺卡次数
    private String gooff_absence_num;//下班缺卡次数
    private String abs_days ;// 旷工天数
    private String goout;//外出(天)
    private String compassionate_leave;//事假(天)
    private String marriage_leave;//婚假(天)
    private String special_leave ;//特殊请假(天)
    private String sick_leave ;//病假(天)
    private String road_leave ;//路途假(天)
    private String h_1;//1号
    private String h_2;//2号
    private String h_3;//3号
    private String h_4;//4号
    private String h_5;//5号
    private String h_6;//6号
    private String h_7;//7号
    private String h_8;//8号
    private String h_9;//9号
    private String h_10;//10号
    private String h_11;//11号
    private String h_12;//12号
    private String h_13;//13号
    private String h_14;//14号
    private String h_15;//15号
    private String h_16;//16号
    private String h_17;//17号
    private String h_18;//18号
    private String h_19;//19号
    private String h_20;//20号
    private String h_21;//21号
    private String h_22;//22号
    private String h_23;//23号
    private String h_24;//24号
    private String h_25;//25号
    private String h_26;//26号
    private String h_27;//27号
    private String h_28;//28号
    private String h_29;//29号
    private String h_30;//30号
    private String h_31;//31号
    private String h_32;//32号
    private String h_33;//预留1
    private String h_34;//预留2
    private String h_35;//预留3

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStaff_code() {
        return staff_code;
    }

    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(String entry_time) {
        this.entry_time = entry_time;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAttendance_days() {
        return attendance_days;
    }

    public void setAttendance_days(String attendance_days) {
        this.attendance_days = attendance_days;
    }

    public String getRest_days() {
        return rest_days;
    }

    public void setRest_days(String rest_days) {
        this.rest_days = rest_days;
    }

    public String getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(String working_hours) {
        this.working_hours = working_hours;
    }

    public String getLate_ness() {
        return late_ness;
    }

    public void setLate_ness(String late_ness) {
        this.late_ness = late_ness;
    }

    public String getLate_arrival_time() {
        return late_arrival_time;
    }

    public void setLate_arrival_time(String late_arrival_time) {
        this.late_arrival_time = late_arrival_time;
    }

    public String getSevere_lateness() {
        return severe_lateness;
    }

    public void setSevere_lateness(String severe_lateness) {
        this.severe_lateness = severe_lateness;
    }

    public String getSevere_arrival_time() {
        return severe_arrival_time;
    }

    public void setSevere_arrival_time(String severe_arrival_time) {
        this.severe_arrival_time = severe_arrival_time;
    }

    public String getAds_late_days() {
        return ads_late_days;
    }

    public void setAds_late_days(String ads_late_days) {
        this.ads_late_days = ads_late_days;
    }

    public String getEarly_time() {
        return early_time;
    }

    public void setEarly_time(String early_time) {
        this.early_time = early_time;
    }

    public String getGoto_absence_num() {
        return goto_absence_num;
    }

    public void setGoto_absence_num(String goto_absence_num) {
        this.goto_absence_num = goto_absence_num;
    }

    public String getGooff_absence_num() {
        return gooff_absence_num;
    }

    public void setGooff_absence_num(String gooff_absence_num) {
        this.gooff_absence_num = gooff_absence_num;
    }

    public String getAbs_days() {
        return abs_days;
    }

    public void setAbs_days(String abs_days) {
        this.abs_days = abs_days;
    }

    public String getGoout() {
        return goout;
    }

    public void setGoout(String goout) {
        this.goout = goout;
    }

    public String getCompassionate_leave() {
        return compassionate_leave;
    }

    public void setCompassionate_leave(String compassionate_leave) {
        this.compassionate_leave = compassionate_leave;
    }

    public String getMarriage_leave() {
        return marriage_leave;
    }

    public void setMarriage_leave(String marriage_leave) {
        this.marriage_leave = marriage_leave;
    }

    public String getSpecial_leave() {
        return special_leave;
    }

    public void setSpecial_leave(String special_leave) {
        this.special_leave = special_leave;
    }

    public String getSick_leave() {
        return sick_leave;
    }

    public void setSick_leave(String sick_leave) {
        this.sick_leave = sick_leave;
    }

    public String getRoad_leave() {
        return road_leave;
    }

    public void setRoad_leave(String road_leave) {
        this.road_leave = road_leave;
    }

    public String getH_1() {
        return h_1;
    }

    public void setH_1(String h_1) {
        this.h_1 = h_1;
    }

    public String getH_2() {
        return h_2;
    }

    public void setH_2(String h_2) {
        this.h_2 = h_2;
    }

    public String getH_3() {
        return h_3;
    }

    public void setH_3(String h_3) {
        this.h_3 = h_3;
    }

    public String getH_4() {
        return h_4;
    }

    public void setH_4(String h_4) {
        this.h_4 = h_4;
    }

    public String getH_5() {
        return h_5;
    }

    public void setH_5(String h_5) {
        this.h_5 = h_5;
    }

    public String getH_6() {
        return h_6;
    }

    public void setH_6(String h_6) {
        this.h_6 = h_6;
    }

    public String getH_7() {
        return h_7;
    }

    public void setH_7(String h_7) {
        this.h_7 = h_7;
    }

    public String getH_8() {
        return h_8;
    }

    public void setH_8(String h_8) {
        this.h_8 = h_8;
    }

    public String getH_9() {
        return h_9;
    }

    public void setH_9(String h_9) {
        this.h_9 = h_9;
    }

    public String getH_10() {
        return h_10;
    }

    public void setH_10(String h_10) {
        this.h_10 = h_10;
    }

    public String getH_11() {
        return h_11;
    }

    public void setH_11(String h_11) {
        this.h_11 = h_11;
    }

    public String getH_12() {
        return h_12;
    }

    public void setH_12(String h_12) {
        this.h_12 = h_12;
    }

    public String getH_13() {
        return h_13;
    }

    public void setH_13(String h_13) {
        this.h_13 = h_13;
    }

    public String getH_14() {
        return h_14;
    }

    public void setH_14(String h_14) {
        this.h_14 = h_14;
    }

    public String getH_15() {
        return h_15;
    }

    public void setH_15(String h_15) {
        this.h_15 = h_15;
    }

    public String getH_16() {
        return h_16;
    }

    public void setH_16(String h_16) {
        this.h_16 = h_16;
    }

    public String getH_17() {
        return h_17;
    }

    public void setH_17(String h_17) {
        this.h_17 = h_17;
    }

    public String getH_18() {
        return h_18;
    }

    public void setH_18(String h_18) {
        this.h_18 = h_18;
    }

    public String getH_19() {
        return h_19;
    }

    public void setH_19(String h_19) {
        this.h_19 = h_19;
    }

    public String getH_20() {
        return h_20;
    }

    public void setH_20(String h_20) {
        this.h_20 = h_20;
    }

    public String getH_21() {
        return h_21;
    }

    public void setH_21(String h_21) {
        this.h_21 = h_21;
    }

    public String getH_22() {
        return h_22;
    }

    public void setH_22(String h_22) {
        this.h_22 = h_22;
    }

    public String getH_23() {
        return h_23;
    }

    public void setH_23(String h_23) {
        this.h_23 = h_23;
    }

    public String getH_24() {
        return h_24;
    }

    public void setH_24(String h_24) {
        this.h_24 = h_24;
    }

    public String getH_25() {
        return h_25;
    }

    public void setH_25(String h_25) {
        this.h_25 = h_25;
    }

    public String getH_26() {
        return h_26;
    }

    public void setH_26(String h_26) {
        this.h_26 = h_26;
    }

    public String getH_27() {
        return h_27;
    }

    public void setH_27(String h_27) {
        this.h_27 = h_27;
    }

    public String getH_28() {
        return h_28;
    }

    public void setH_28(String h_28) {
        this.h_28 = h_28;
    }

    public String getH_29() {
        return h_29;
    }

    public void setH_29(String h_29) {
        this.h_29 = h_29;
    }

    public String getH_30() {
        return h_30;
    }

    public void setH_30(String h_30) {
        this.h_30 = h_30;
    }

    public String getH_31() {
        return h_31;
    }

    public void setH_31(String h_31) {
        this.h_31 = h_31;
    }

    public String getH_32() {
        return h_32;
    }

    public void setH_32(String h_32) {
        this.h_32 = h_32;
    }

    public String getH_33() {
        return h_33;
    }

    public void setH_33(String h_33) {
        this.h_33 = h_33;
    }

    public String getH_34() {
        return h_34;
    }

    public void setH_34(String h_34) {
        this.h_34 = h_34;
    }

    public String getH_35() {
        return h_35;
    }

    public void setH_35(String h_35) {
        this.h_35 = h_35;
    }

    public String getEarly_hours() {
        return early_hours;
    }

    public void setEarly_hours(String early_hours) {
        this.early_hours = early_hours;
    }

    public String getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(String attendance_id) {
        this.attendance_id = attendance_id;
    }

    public String getStaff_name_n() {
        return staff_name_n;
    }

    public void setStaff_name_n(String staff_name_n) {
        this.staff_name_n = staff_name_n;
    }

    @Override
    public String toString() {
        return "StaffAttendanceinfo{" +
                "id='" + id + '\'' +
                ", attendance_id='" + attendance_id + '\'' +
                ", staff_name='" + staff_name + '\'' +
                ", staff_name_n='" + staff_name_n + '\'' +
                ", department='" + department + '\'' +
                ", staff_code='" + staff_code + '\'' +
                ", position='" + position + '\'' +
                ", entry_time='" + entry_time + '\'' +
                ", departure_time='" + departure_time + '\'' +
                ", state='" + state + '\'' +
                ", attendance_days='" + attendance_days + '\'' +
                ", rest_days='" + rest_days + '\'' +
                ", working_hours='" + working_hours + '\'' +
                ", late_ness='" + late_ness + '\'' +
                ", late_arrival_time='" + late_arrival_time + '\'' +
                ", severe_lateness='" + severe_lateness + '\'' +
                ", severe_arrival_time='" + severe_arrival_time + '\'' +
                ", ads_late_days='" + ads_late_days + '\'' +
                ", early_time='" + early_time + '\'' +
                ", early_hours='" + early_hours + '\'' +
                ", goto_absence_num='" + goto_absence_num + '\'' +
                ", gooff_absence_num='" + gooff_absence_num + '\'' +
                ", abs_days='" + abs_days + '\'' +
                ", goout='" + goout + '\'' +
                ", compassionate_leave='" + compassionate_leave + '\'' +
                ", marriage_leave='" + marriage_leave + '\'' +
                ", special_leave='" + special_leave + '\'' +
                ", sick_leave='" + sick_leave + '\'' +
                ", road_leave='" + road_leave + '\'' +
                ", h_1='" + h_1 + '\'' +
                ", h_2='" + h_2 + '\'' +
                ", h_3='" + h_3 + '\'' +
                ", h_4='" + h_4 + '\'' +
                ", h_5='" + h_5 + '\'' +
                ", h_6='" + h_6 + '\'' +
                ", h_7='" + h_7 + '\'' +
                ", h_8='" + h_8 + '\'' +
                ", h_9='" + h_9 + '\'' +
                ", h_10='" + h_10 + '\'' +
                ", h_11='" + h_11 + '\'' +
                ", h_12='" + h_12 + '\'' +
                ", h_13='" + h_13 + '\'' +
                ", h_14='" + h_14 + '\'' +
                ", h_15='" + h_15 + '\'' +
                ", h_16='" + h_16 + '\'' +
                ", h_17='" + h_17 + '\'' +
                ", h_18='" + h_18 + '\'' +
                ", h_19='" + h_19 + '\'' +
                ", h_20='" + h_20 + '\'' +
                ", h_21='" + h_21 + '\'' +
                ", h_22='" + h_22 + '\'' +
                ", h_23='" + h_23 + '\'' +
                ", h_24='" + h_24 + '\'' +
                ", h_25='" + h_25 + '\'' +
                ", h_26='" + h_26 + '\'' +
                ", h_27='" + h_27 + '\'' +
                ", h_28='" + h_28 + '\'' +
                ", h_29='" + h_29 + '\'' +
                ", h_30='" + h_30 + '\'' +
                ", h_31='" + h_31 + '\'' +
                ", h_32='" + h_32 + '\'' +
                ", h_33='" + h_33 + '\'' +
                ", h_34='" + h_34 + '\'' +
                ", h_35='" + h_35 + '\'' +
                '}';
    }
}
