package hse.dm_lab.model;

import java.util.Objects;

public class Item {
    private Integer id;
    private String fio;
    private String sex;
    private Integer claimCount;
    private String role;

    public Item() {}

    public Item(Integer id, String fio, String sex, Integer claimCount, String role) {
        this.id = id;
        this.fio = fio;
        this.sex = sex;
        this.claimCount = claimCount;
        this.role = role;
    }

    public Item(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Integer getClaimCount() {
        return claimCount;
    }

    public void setClaimCount(Integer claimCount) {
        this.claimCount = claimCount;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        if (this.id.equals(item.getId())) return true;
        return id.equals(item.id) &&
                Objects.equals(fio, item.fio) &&
                Objects.equals(sex, item.sex) &&
                Objects.equals(claimCount, item.claimCount) &&
                Objects.equals(role, item.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fio, sex, claimCount, role);
    }
}
