package eu.komanda30.kupra.controllers.unit;

import java.util.ArrayList;

/**
 * Created by Ignas on 12/10/2014.
 */
public class UnitsListForm{
    private ArrayList<UnitListItem> unitListItemArrayList = new ArrayList<UnitListItem>();

    public ArrayList<UnitListItem> getUnitListItemArrayList() {
        return unitListItemArrayList;
    }

    public void setUnitListItemArrayList(ArrayList<UnitListItem> unitListItemArrayList) {
        this.unitListItemArrayList = unitListItemArrayList;
    }

    public void addUnitListItem(UnitListItem unitListItem){
        this.unitListItemArrayList.add(unitListItem);
    }
}
