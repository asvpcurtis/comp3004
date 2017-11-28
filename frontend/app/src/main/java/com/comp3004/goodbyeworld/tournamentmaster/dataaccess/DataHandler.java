package com.comp3004.goodbyeworld.tournamentmaster.dataaccess;

import android.content.Context;
import android.util.Log;

import com.comp3004.goodbyeworld.tournamentmaster.auth.AppHelper;
import com.comp3004.goodbyeworld.tournamentmaster.dataoperations.FieldTranslate;
import com.comp3004.goodbyeworld.tournamentmaster.dataoperations.TMTemplates;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Michael Souter on 2017-10-28.
 * Interface for the view to access data.
 * Directs by delegating calls for data to the appropriate
 * function in the appropriate data type.
 */

public class DataHandler {
    // Function to be called when data is updated
    private static UpdateCallback updateCallback = null;

    // Competitor array, used to give nice pairing names
    private static ArrayList<TMDataSet> competitorsStore = null;

    /**
     * updateActivity calls the current update method of an activity when
     * the data is ready for it to be updated
     */
    private static void updateActivity(ArrayList<TMDataSet> data) {
        updateCallback.updateData(data);
    }

    /**
     * getMyID() returns the ID of a logged in user as a string
     */
    public static void getMyID(Context c, UpdateCallback u) {
        updateCallback = u;
        DataRetriever.getID(c, new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                ArrayList<TMDataSet> info = new ArrayList<>();
                info.add(new TMDataSet((String)result, "Account", (String)result));
                updateActivity(info);
            }

            @Override
            public void onFailure(Object result) {

            }
        });
    }

    /**
     * getData retrieves an ArrayList of TMDataSets
     * The first element of the array will always be the item itself
     * which was used to make the request. The following items will be
     * the associated children of that item, including its name, id,
     * and related types.
     */
    public static void getData(Context c, String type, String iD, UpdateCallback u) {
        updateCallback = u;
        final Context myCtx = c;
        final String fieldType = type;
        final String myID = iD;
        if (type.equals("Account")) {
            String urlAdd = "/organizations";
            DataRetriever.getDataArray(c, urlAdd, new VolleyCallback() {
                @Override
                public void onSuccess(Object result) {
                    ArrayList<TMDataSet> info = new ArrayList<>();
                    info.add(new TMDataSet(AppHelper.getCurrUser(), "Account", myID));
                    info.addAll(FieldTranslate.convert((JSONArray) result, "Organization"));
                    updateActivity(info);
                }

                @Override
                public void onFailure(Object result) {
                    updateActivity(null);
                }
            });
        } else {
            String urlAdd = FieldTranslate.convertType(type, iD);
            DataRetriever.getData(c, urlAdd, new VolleyCallback() {
                @Override
                public void onSuccess(Object result) {
                    final ArrayList<TMDataSet> info = new ArrayList<>();
                    info.addAll(FieldTranslate.convertFull((JSONObject) result, fieldType));
                    // Switch case to get supplementary data (IE: an organizations tournament list)
                    switch (fieldType) {
                        case "Organization":
                            DataRetriever.getDataArray(myCtx, "/tournaments?organization=" + myID, new VolleyCallback() {
                                @Override
                                public void onSuccess(Object result) {
                                    info.addAll(FieldTranslate.convert((JSONArray) result, "Tournament"));
                                    DataRetriever.getDataArray(myCtx, "/competitors?organization=" + myID, new VolleyCallback() {
                                        @Override
                                        public void onSuccess(Object result) {
                                            info.addAll(FieldTranslate.convert((JSONArray) result, "Competitor"));
                                            competitorsStore = new ArrayList<>();
                                            competitorsStore.addAll(FieldTranslate.convert((JSONArray) result, "Competitor"));
                                            updateActivity(info);
                                        }

                                        @Override
                                        public void onFailure(Object result) {

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Object result) {

                                }
                            });
                            break;
                        case "Tournament":
                            DataRetriever.getDataArray(myCtx, "/rounds?tournament=" + myID, new VolleyCallback() {
                                @Override
                                public void onSuccess(Object result) {
                                    info.addAll(FieldTranslate.convert((JSONArray) result, "Round"));
                                    updateActivity(info);
                                }

                                @Override
                                public void onFailure(Object result) {

                                }
                            });
                            break;
                        case "Round":
                            DataRetriever.getDataArray(myCtx, "/pairings?round=" + myID, new VolleyCallback() {
                                @Override
                                public void onSuccess(Object result) {
                                    for (int i=0; i<((JSONArray)result).length(); i++) {
                                        try {
                                            JSONObject obj = ((JSONArray)result).getJSONObject(i);
                                            String idOne = obj.getString("competitorId1");
                                            String idTwo = obj.getString("competitorId2");
                                            String winner = obj.getString("result");
                                            JSONObject pair = new JSONObject();
                                            if (idOne!=null) {
                                                for (TMDataSet t : competitorsStore) {
                                                    if (idOne.equals(t.getID())) {
                                                        pair.put("competitorOne", t.getData());
                                                        break;
                                                    }
                                                }
                                            }
                                            if (idTwo!=null) {
                                                for (TMDataSet t : competitorsStore) {
                                                    if (idTwo.equals(t.getID())){
                                                        pair.put("competitorTwo", t.getData());
                                                        break;
                                                    }
                                                }
                                            }
                                            if (winner.equals(idOne)) {
                                                pair.put("result", "Defeats");
                                            } else if (winner.equals(idTwo)) {
                                                pair.put("result", "Is Defeated By");
                                            } else {
                                                pair.put("result", "--Versus--");
                                            }
                                            info.add(new TMDataSet(pair.toString(), "Pairing", obj.getString("id")));
                                        } catch (JSONException e) {
                                            Log.e("ERROR:", "JSON not readable");
                                        }
                                    }

                                    updateActivity(info);
                                }

                                @Override
                                public void onFailure(Object result) {

                                }
                            });
                            break;
                        case "Pairing":
                            final JSONObject myPair = (JSONObject) result;
                            try {
                                DataRetriever.getData(myCtx, "/competitors/" + myPair.get("competitorId1").toString(), new VolleyCallback() {
                                    @Override
                                    public void onSuccess(Object result) {
                                        info.addAll(FieldTranslate.convert((JSONObject) result, "Competitor"));
                                        try {
                                            DataRetriever.getData(myCtx, "/competitors/" + myPair.get("competitorId2").toString(), new VolleyCallback() {
                                                @Override
                                                public void onSuccess(Object result) {
                                                    info.addAll(FieldTranslate.convert((JSONObject) result, "Competitor"));
                                                    updateActivity(info);
                                                }

                                                @Override
                                                public void onFailure(Object result) {

                                                }
                                            });
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Object result) {

                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Competitor":
                            updateActivity(info);
                            break;
                    }
                }

                @Override
                public void onFailure(Object result)
                {}
            });
        }
    }

    /**
     * getCompetitors gets a competitor list to seed tournaments with
     * The dataType of all competitors is the actual competitor JSON for sending
     * back to the server
     */
    public static void getCompetitors(Context c, String iD, UpdateCallback u) {
        updateCallback = u;
        final ArrayList<TMDataSet> info = new ArrayList<>();
        DataRetriever.getDataArray(c, "/competitors?organization=" + iD, new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                info.addAll(FieldTranslate.convertCompetitors((JSONArray) result));
                updateActivity(info);
            }

            @Override
            public void onFailure(Object result) {

            }
        });
    }

    /**
     * makeCompetitorList makes a JSONArray String from a competitor
     * list to seed tournaments
     */
    public static String makeCompetitorList(ArrayList<TMDataSet> a) {
        JSONArray list = new JSONArray();
        for (TMDataSet i : a) {
            JSONObject obj = null;
            try {
                obj = new JSONObject(i.getDataType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.put(obj);
        }
        return list.toString();
    }



    /**
     * getCreate retrieves the fields required to create a new entity
     */
    public static ArrayList<TMDataSet> getCreate(String type) {
        return TMTemplates.get(type);
    }

    /**
     * setCreate accepts data to be posted to backend
     */
    public static void setCreate(Context c, String type, ArrayList<TMDataSet> data, UpdateCallback u) {
        updateCallback = u;
        String urlAdd = FieldTranslate.convertType(type);
        JSONObject newData;
        if (type.equals("Tournament")) {
            urlAdd += "?seed=manual";
            newData = FieldTranslate.convertTournament(data);
        } else {
            newData = FieldTranslate.convert(data);
        }
        DataRetriever.postData(c, urlAdd, newData, new VolleyCallback() {
           @Override
            public void onSuccess(Object result) {
               updateActivity(null);
           }

            @Override
            public void onFailure(Object result)
            {}
        });
    }

    /**
     * setAddAccount accepts data to be posted to backend
     */
    public static void addAccount(Context c, TMDataSet d, UpdateCallback u) {
        updateCallback = u;
        String urlAdd = "/organizationaccounts";
        JSONObject newData = new JSONObject();
        try {
            newData.put("accountId", d.getData());
            newData.put("organizationId", d.getID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DataRetriever.postData(c, urlAdd, newData, new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                updateActivity(null);
            }

            @Override
            public void onFailure(Object result)
            {}
        });
    }

    /**
     * getEdit is currently a masked call to getData that will
     * respond with only the needed info.
     */
    public static void getEdit(Context c, String type, String iD, UpdateCallback u) {
        updateCallback = u;
        final String fieldType = type;
        String urlAdd = FieldTranslate.convertType(type, iD);
        DataRetriever.getData(c, urlAdd, new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                ArrayList<TMDataSet> info = new ArrayList<>();
                info.addAll(TMTemplates.editFields((JSONObject)result, fieldType));
                if (fieldType.equals("Pairing")) {
                    JSONObject obj = (JSONObject)result;
                    try {
                        for (TMDataSet t : competitorsStore) {
                            if (obj.getString("competitorId1").equals(t.getID()) || obj.getString("competitorId2").equals(t.getID())) {
                                info.add(t);
                            }
                        }
                        info.add(new TMDataSet(obj.getString("result"), "currResult", null));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                updateActivity(info);
            }

            @Override
            public void onFailure(Object result)
            {}
        });
    }

    /**
     * setEdit accepts data to be modified on backend
     */
    public static void setEdit(Context c, String type, String iD, ArrayList<TMDataSet> data, UpdateCallback u) {
        updateCallback = u;
        String urlAdd = FieldTranslate.convertType(type, iD);
        JSONObject newData = FieldTranslate.convert(data);
        DataRetriever.putData(c, urlAdd, newData, new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                updateActivity(null);
            }

            @Override
            public void onFailure(Object result)
            {}
        });
    }

    public static void setDelete(Context c, String type, String iD, UpdateCallback u) {
        updateCallback = u;
        String urlAdd = FieldTranslate.convertType(type, iD);
        DataRetriever.deleteData(c, urlAdd, new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                updateActivity(null);
            }

            @Override
            public void onFailure(Object result)
            {}
        });
    }
}
