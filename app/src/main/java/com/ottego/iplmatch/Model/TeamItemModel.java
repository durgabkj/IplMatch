package com.ottego.iplmatch.Model;

import java.util.List;

public class TeamItemModel {

    public String id;
    public String name;
    public List<PlayerModel> members;

    public TeamItemModel(String id, String name, List<PlayerModel> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }
}
