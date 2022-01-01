package com.example.intelligentequipmentinspectionsystem;

public class Room {
    private int roomId;
    private String roomName;

    public Room() {
    }

    public Room(int roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                '}';
    }
}
