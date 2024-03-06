package model;

import java.util.List;

public class MenuWeightResultDto {
    private List<Menu> menus;
    private boolean lessThenOneKg;
    private Double commonWeight;

    @Override
    public String toString() {
        return "MenuWeightResultDto{" +
                "menus=" + menus +
                ", lessThenOneKg=" + lessThenOneKg +
                ", commonWeight=" + commonWeight +
                '}';
    }

    public MenuWeightResultDto(List<Menu> menus, boolean lessThenOneKg, Double commonWeight) {
        this.menus = menus;
        this.lessThenOneKg = lessThenOneKg;
        this.commonWeight = commonWeight;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public boolean isLessThenOneKg() {
        return lessThenOneKg;
    }

    public void setLessThenOneKg(boolean lessThenOneKg) {
        this.lessThenOneKg = lessThenOneKg;
    }

    public Double getCommonWeight() {
        return commonWeight;
    }

    public void setCommonWeight(Double commonWeight) {
        this.commonWeight = commonWeight;
    }


}
