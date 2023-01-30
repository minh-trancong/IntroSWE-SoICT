package hust.itep.quanlynhankhau.model;


import java.sql.Date;

public class NhanKhau {
    Integer id;
    String maNhanKhau;
    String hoTen;
    String bietDanh;

    Date namSinh;

    String gioiTinh;

    String noiSinh;

    String nguyenQuan;

    String danToc;

    String tonGiao;

    String quocTich;

    String noiThuongTru;

    String diaChiHienNay;

    String trinhDoHocVan;

    String trinhDoChuyenMon;

    String bietTiengDanToc;

    String trinhDoNgoaiNgu;

    String ngheNghiep;

    String noiLamViec;

    String tienAn;

    Date ngayChuyenDen;

    String lyDoChuyenDen;

    Date ngayChuyenDi;

    String lyDoChuyenDi;

    String diaChiMoi;

    Date ngayTao;

    Integer idNguoiTao;

    Date ngayXoa;

    Integer idNguoiXoa;

    String lyDoXoa;

    String ghiChu;

    public NhanKhau() {

    }

    public NhanKhau(Integer id) {
        this.id = id;
    }

    public String getNoiSinh() {
        return noiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        this.noiSinh = noiSinh;
    }

    public String getNguyenQuan() {
        return nguyenQuan;
    }

    public void setNguyenQuan(String nguyenQuan) {
        this.nguyenQuan = nguyenQuan;
    }

    public String getDanToc() {
        return danToc;
    }

    public void setDanToc(String danToc) {
        this.danToc = danToc;
    }

    public String getTonGiao() {
        return tonGiao;
    }

    public void setTonGiao(String tonGiao) {
        this.tonGiao = tonGiao;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    public String getNoiThuongTru() {
        return noiThuongTru;
    }

    public void setNoiThuongTru(String noiThuongTru) {
        this.noiThuongTru = noiThuongTru;
    }

    public String getDiaChiHienNay() {
        return diaChiHienNay;
    }

    public void setDiaChiHienNay(String diaChiHienNay) {
        this.diaChiHienNay = diaChiHienNay;
    }

    public String getTrinhDoHocVan() {
        return trinhDoHocVan;
    }

    public void setTrinhDoHocVan(String trinhDoHocVan) {
        this.trinhDoHocVan = trinhDoHocVan;
    }

    public String getTrinhDoChuyenMon() {
        return trinhDoChuyenMon;
    }

    public void setTrinhDoChuyenMon(String trinhDoChuyenMon) {
        this.trinhDoChuyenMon = trinhDoChuyenMon;
    }

    public String getBietTiengDanToc() {
        return bietTiengDanToc;
    }

    public void setBietTiengDanToc(String bietTiengDanToc) {
        this.bietTiengDanToc = bietTiengDanToc;
    }

    public String getTrinhDoNgoaiNgu() {
        return trinhDoNgoaiNgu;
    }

    public void setTrinhDoNgoaiNgu(String trinhDoNgoaiNgu) {
        this.trinhDoNgoaiNgu = trinhDoNgoaiNgu;
    }

    public String getNgheNghiep() {
        return ngheNghiep;
    }

    public void setNgheNghiep(String ngheNghiep) {
        this.ngheNghiep = ngheNghiep;
    }

    public String getNoiLamViec() {
        return noiLamViec;
    }

    public void setNoiLamViec(String noiLamViec) {
        this.noiLamViec = noiLamViec;
    }

    public String getTienAn() {
        return tienAn;
    }

    public void setTienAn(String tienAn) {
        this.tienAn = tienAn;
    }

    public Date getNgayChuyenDen() {
        return ngayChuyenDen;
    }

    public void setNgayChuyenDen(Date ngayChuyenDen) {
        this.ngayChuyenDen = ngayChuyenDen;
    }

    public String getLyDoChuyenDen() {
        return lyDoChuyenDen;
    }

    public void setLyDoChuyenDen(String lyDoChuyenDen) {
        this.lyDoChuyenDen = lyDoChuyenDen;
    }

    public Date getNgayChuyenDi() {
        return ngayChuyenDi;
    }

    public void setNgayChuyenDi(Date ngayChuyenDi) {
        this.ngayChuyenDi = ngayChuyenDi;
    }

    public String getLyDoChuyenDi() {
        return lyDoChuyenDi;
    }

    public void setLyDoChuyenDi(String lyDoChuyenDi) {
        this.lyDoChuyenDi = lyDoChuyenDi;
    }

    public String getDiaChiMoi() {
        return diaChiMoi;
    }

    public void setDiaChiMoi(String diaChiMoi) {
        this.diaChiMoi = diaChiMoi;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Integer getIdNguoiTao() {
        return idNguoiTao;
    }

    public void setIdNguoiTao(Integer idNguoiTao) {
        this.idNguoiTao = idNguoiTao;
    }

    public Date getNgayXoa() {
        return ngayXoa;
    }

    public void setNgayXoa(Date ngayXoa) {
        this.ngayXoa = ngayXoa;
    }

    public Integer getIdNguoiXoa() {
        return idNguoiXoa;
    }

    public void setIdNguoiXoa(Integer idNguoiXoa) {
        this.idNguoiXoa = idNguoiXoa;
    }

    public String getLyDoXoa() {
        return lyDoXoa;
    }

    public void setLyDoXoa(String lyDoXoa) {
        this.lyDoXoa = lyDoXoa;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Integer getId() {
        return id;
    }

    public String getMaNhanKhau() {
        return maNhanKhau;
    }

    public void setMaNhanKhau(String maNhanKhau) {
        this.maNhanKhau = maNhanKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getBietDanh() {
        return bietDanh;
    }

    public void setBietDanh(String bietDanh) {
        this.bietDanh = bietDanh;
    }

    public Date getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(Date namSinh) {
        this.namSinh = namSinh;
    }
}
