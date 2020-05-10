package hse.dm_lab.model;

import java.util.Objects;

public class ItemDTO {
    private Integer id;
    private String fio;
    private Boolean sex;
    private Integer claimCount;
    private String role;

    public ItemDTO(Integer id, String fio, Boolean sex, Integer claimCount, String role) {
        this.id = id;
        this.fio = fio;
        this.sex = sex;
        this.claimCount = claimCount;
        this.role = role;
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

    public Boolean getSex() {
        return sex;
    }

    public Integer getClaimCount() {
        return claimCount;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDTO item = (ItemDTO) o;
        if (item.id.equals(id)) {
            return true;
        }
        return Objects.equals(id, item.id) &&
                Objects.equals(fio, item.fio) &&
                Objects.equals(sex, item.sex) &&
                Objects.equals(claimCount, item.claimCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fio, sex, claimCount);
    }
}