package com.acl.utils.excel;

import java.io.Serializable;
import java.util.Map;

public class BasicInfo implements Serializable {
        private String id ;
        private String mobile;
        private String name;
        private String idCard;
        //岗位
        private String job;

        //入职时间
        private String jobDay;

        //上班工作日
        private String wordDays;

        //请假天数
        private String leaveDays;

        //本月业绩达标
        private String monthCheck ;

        //本月未达标
        private String monthUnCheck ;

        //薪资及绩效提成 ** 项目明细
        private Map totalMap ;

        //应扣工资
        private Map deductMap ;

        //应发工资
        private String totalAmount ;
        //预支工资  ** 抵扣所得分润
        private String deductAmount ;
        //实发工资
        private String realAmount ;

        //备注
        private String notes;

        //后台名称
        private String backName;

        //代理名称
        private String agentName;

        //所属渠道
        private String chName;

        //品牌
        private String brand;


        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getJobDay() {
            return jobDay;
        }

        public void setJobDay(String jobDay) {
            this.jobDay = jobDay;
        }

        public String getWordDays() {
            return wordDays;
        }

        public void setWordDays(String wordDays) {
            this.wordDays = wordDays;
        }

        public String getLeaveDays() {
            return leaveDays;
        }

        public void setLeaveDays(String leaveDays) {
            this.leaveDays = leaveDays;
        }

        public String getMonthCheck() {
            return monthCheck;
        }

        public void setMonthCheck(String monthCheck) {
            this.monthCheck = monthCheck;
        }

        public String getMonthUnCheck() {
            return monthUnCheck;
        }

        public void setMonthUnCheck(String monthUnCheck) {
            this.monthUnCheck = monthUnCheck;
        }

        public Map getTotalMap() {
            return totalMap;
        }

        public void setTotalMap(Map totalMap) {
            this.totalMap = totalMap;
        }

        public Map getDeductMap() {
            return deductMap;
        }

        public void setDeductMap(Map deductMap) {
            this.deductMap = deductMap;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getDeductAmount() {
            return deductAmount;
        }

        public void setDeductAmount(String deductAmount) {
            this.deductAmount = deductAmount;
        }

        public String getRealAmount() {
            return realAmount;
        }

        public void setRealAmount(String realAmount) {
            this.realAmount = realAmount;
        }

        public String getBackName() {
            return backName;
        }

        public void setBackName(String backName) {
            this.backName = backName;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public String getChName() {
            return chName;
        }

        public void setChName(String chName) {
            this.chName = chName;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        @Override
        public String toString() {
            return "BasicInfo{" +
                    "id='" + id + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", name='" + name + '\'' +
                    ", idCard='" + idCard + '\'' +
                    ", job='" + job + '\'' +
                    ", jobDay='" + jobDay + '\'' +
                    ", wordDays='" + wordDays + '\'' +
                    ", leaveDays='" + leaveDays + '\'' +
                    ", monthCheck='" + monthCheck + '\'' +
                    ", monthUnCheck='" + monthUnCheck + '\'' +
                    ", totalMap=" + totalMap +
                    ", deductMap=" + deductMap +
                    ", totalAmount='" + totalAmount + '\'' +
                    ", deductAmount='" + deductAmount + '\'' +
                    ", realAmount='" + realAmount + '\'' +
                    ", notes='" + notes + '\'' +
                    ", backName='" + backName + '\'' +
                    ", agentName='" + agentName + '\'' +
                    ", chName='" + chName + '\'' +
                    ", brand='" + brand + '\'' +
                    '}';
        }
    }
