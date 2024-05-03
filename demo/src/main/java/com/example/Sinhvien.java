package com.example;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "sinhvien")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sinhvien {

    @XmlAttribute(name = "id")
    private String id;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "address")
    private String address;


    public Sinhvien() {
    }
 
    public Sinhvien(String id, String name, String address) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        
    }
 
    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getAddress() {
        return address;
    }
 
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Sinhvien [id=" + id + ", name=" + name + ", address=" + address + "]";
    }


}

