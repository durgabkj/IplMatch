package com.match.iplHub.Model;

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
            if (p.team_id.equalsIgnoreCase(teamid1)) {
                t1.add(p);
            }
        }

        TeamItemModel ta = new TeamItemModel(teamid1, t1.get(0).team_name, t1);
        return ta;
    }

    public TeamItemModel getTeam2() {
        List<PlayerModel> t2 = new ArrayList<>();
        for (PlayerModel p : player) {
            if (p.team_id.equalsIgnoreCase(teamid2)) {
                t2.add(p);
            }
        }
        TeamItemModel tb = new TeamItemModel(teamid2, t2.get(0).team_name, t2);
        return tb;
    }

    public List<TeamItemModel> getTeamList() {
        List<TeamItemModel> list = new ArrayList<>();

        list.add(getTeam1());
        list.add(getTeam2());

        Log.e("parsedResponse", new Gson().toJson(list));
        return list;


    }


}
