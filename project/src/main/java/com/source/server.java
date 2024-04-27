package com.source;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(1234);
            System.out.println("Server is running on port 1234");
            while (true) {
                Socket s = server.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                while (true) {
                    String command = in.readUTF();
                    if (command.equals("save")){

                        try {

                            JAXBContext jaxbContext = JAXBContext.newInstance(SinhvienList.class);
                            Marshaller marshaller = jaxbContext.createMarshaller();
                            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
                            List<Sinhvien> sinhviens = new ArrayList<>();
        
                                int n = in.readInt();
        
                                for (int i = 0; i < n; i++) {
                                    String id = in.readUTF();
                                    String name = in.readUTF();
                                    String address = in.readUTF();
        
                                    Sinhvien currentSinhvien = new Sinhvien(id, name, address);
                                    sinhviens.add(currentSinhvien);
        
                                }
        
                                SinhvienList wrapper = new SinhvienList();
                                wrapper.setSinhviens(sinhviens);
                
                                marshaller.marshal(wrapper, System.out);
                                marshaller.marshal(wrapper, new FileOutputStream("sinhvien.xml"));
        
                                
                            } catch (Exception e) {
                                in.close();
                                out.close();
                                s.close();
                                break;
                            }


                            
                        } else if (command.equals("load")){
                            try {
                                JAXBContext jaxbContext = JAXBContext.newInstance(SinhvienList.class);
                                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                            
                                SinhvienList wrapper = (SinhvienList) unmarshaller.unmarshal(new File("sinhvien.xml"));
                                List<Sinhvien> sinhviens = wrapper.getSinhviens();
                            
                                // Send the number of students first
                                out.writeInt(sinhviens.size());
                            
                                for (Sinhvien currentSinhvien : sinhviens) {
                                    out.writeUTF(currentSinhvien.getId());
                                    out.writeUTF(currentSinhvien.getName());
                                    out.writeUTF(currentSinhvien.getAddress());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                    }
                    
                }
            }
        }
            
         catch (IOException e) {
            e.printStackTrace();
        }
    }
}


    

