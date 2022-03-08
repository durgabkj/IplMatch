package com.match.iplHub.Model;

public class TeamModel {
    public String id;
    public String team_name;
    public String short_name;
    public String team_logo;
    public String team_desc;


    public TeamModel(String id, String team_name, String short_name, String team_logo, String team_desc) {
        this.id = id;
        this.team_name = team_name;
        this.short_name = short_name;
        this.team_logo = team_logo;
        this.team_desc = team_desc;
    }

}
