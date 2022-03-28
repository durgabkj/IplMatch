package com.ottego.iplHub.Model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DataModelPlayer {

    public List<PlayerModel> player;
    public String teamid1;
    public String teamid2;

    public TeamItemModel getTeam1() {
        List<PlayerModel> t1 = new ArrayList<>();
        for (PlayerModel p : player) {

            if (p.team_id.equalsIgnoreCase(teamid1) && p.status.equals("1")) {
                t1.add(p);
            }
        }

        if (t1.size()> 0){
            return new TeamItemModel(teamid1, t1.get(0).team_name, t1);
        }else{
            return new TeamItemModel(teamid1, "Players not decided yet", t1);
        }
    }

    public TeamItemModel getTeam2() {
        List<PlayerModel> t2 = new ArrayList<>();
        for (PlayerModel p : player) {
            if (p.team_id.equalsIgnoreCase(teamid2) && p.status.equals("1")) {
                t2.add(p);
            }
        }

        if (t2.size()> 0){
            return new TeamItemModel(teamid2, t2.get(0).team_name, t2);
        }else{
            return new TeamItemModel(teamid2, "Players not decided yet", t2);
        }

    }

    public List<TeamItemModel> getTeamList() {
        List<TeamItemModel> list = new ArrayList<>();

        list.add(getTeam1());
        list.add(getTeam2());

        Log.e("parsedResponse", new Gson().toJson(list));
        return list;
    }
}
