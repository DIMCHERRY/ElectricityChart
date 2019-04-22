package com.codercoral.electricitychart.entity;

import cn.bmob.v3.BmobObject;

public class TransformerLoadRate extends BmobObject {
    /**
     * 用户名称
     */
    private String G;
    /**
     * 最大负载率
     */
    private String K;
    /**
     * 最小负载率
     */
    private String M;
    /**
     * 平均负载率
     */
    private String J;
    public String getJ() {
        return J;
    }

    public void setJ(String j) {
        J = j;
    }


    public String getG() {
        return G;
    }

    public void setG(String g) {
        G = g;
    }

    public String getK() {
        return K;
    }

    public void setK(String k) {
        K = k;
    }

    public String getM() {
        return M;
    }

    public void setM(String m) {
        M = m;
    }


}
