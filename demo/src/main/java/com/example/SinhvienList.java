package com.example;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sinhviens")
public class SinhvienList {

    private List<Sinhvien> sinhviens;

    @XmlElement(name = "sinhvien")
    public List<Sinhvien> getSinhviens() {
        return sinhviens;
    }

    public void setSinhviens(List<Sinhvien> sinhviens) {
        this.sinhviens = sinhviens;
    }
}