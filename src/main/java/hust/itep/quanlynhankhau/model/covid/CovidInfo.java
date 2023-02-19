package hust.itep.quanlynhankhau.model.covid;

import java.util.Date;

public class CovidInfo {
    private Long id;
    private String name;
    private String tag;
    private Date date;

    public CovidInfo(Long id, String name, String tag, Date date) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
