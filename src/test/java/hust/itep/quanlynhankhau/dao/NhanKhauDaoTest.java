package hust.itep.quanlynhankhau.dao;

import hust.itep.quanlynhankhau.model.NhanKhau;

import java.util.ArrayList;

public class NhanKhauDaoTest {
    public static void main(String[] args) {
        NhanKhauDAO dao = new NhanKhauDAO();

        for (NhanKhau nhanKhau : dao.getAllNhanKhau()) {
            System.out.println(nhanKhau.getHoTen());
        }
        System.out.println();

        NhanKhau lam = new NhanKhau();
        lam.setHoTen("Lam");

        dao.addNhanKhau(lam);

        for (NhanKhau nhanKhau : dao.getAllNhanKhau()) {
            System.out.println(nhanKhau.getHoTen());
        }
        System.out.println();

        for (NhanKhau nhanKhau : dao.getAllNhanKhau()) {
            if (nhanKhau.getHoTen().equals("Lam")) {
                lam = nhanKhau;
            }
        }

        dao.deleteNhanKhau(lam);

        for (NhanKhau nhanKhau : dao.getAllNhanKhau()) {
            System.out.println(nhanKhau.getHoTen());
        }
        System.out.println();
    }
}
