package com.team.smart.vo;

import java.io.Serializable;

public class WalletVO implements Serializable {

    String name = "";     // 지갑 이름
    String password = ""; // 지갑 비밀번호
    String address = "";  // 지갑 주소
    String path = "";     // 지갑 개인키

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
