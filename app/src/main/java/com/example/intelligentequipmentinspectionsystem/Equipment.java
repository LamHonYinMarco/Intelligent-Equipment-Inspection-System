//package com.example.intelligentequipmentinspectionsystem;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Equipment {
//    private int equipmentId;
//    private EquipmentType equipmentType;
//    private List<Form> forms;
//    private Room room;
//
//    public Equipment() {
//        forms = new ArrayList<>();
//    }
//
//    public Equipment(int equipmentId, EquipmentType equipmentType, List<Form> forms, Room room) {
//        this.equipmentId = equipmentId;
//        this.equipmentType = equipmentType;
//        this.forms = forms;
//        this.room = room;
//    }
//
//    public int getEquipmentId() {
//        return equipmentId;
//    }
//
//    public void setEquipmentId(int equipmentId) {
//        this.equipmentId = equipmentId;
//    }
//
//    public EquipmentType getEquipmentType() {
//        return equipmentType;
//    }
//
//    public void setEquipmentType(EquipmentType equipmentType) {
//        this.equipmentType = equipmentType;
//    }
//
//    public List<Form> getForms() {
//        return forms;
//    }
//
//    public void setForms(List<Form> forms) {
//        this.forms = forms;
//    }
//
//    public void addForm(Form form){
//        this.forms.add(form);
//    }
//
//    public void removeForm(Form form){
//        this.forms.remove(form);
//    }
//
//    public Room getRoom() {
//        return room;
//    }
//
//    public void setRoom(Room room) {
//        this.room = room;
//    }
//
//    @Override
//    public String toString() {
//        return "Equipment{" +
//                "equipmentId=" + equipmentId +
//                ", equipmentType=" + equipmentType +
//                //", forms=" + forms +
//                ", room=" + room +
//                '}';
//    }
//}
