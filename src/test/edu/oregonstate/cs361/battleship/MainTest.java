package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.io.OutputStream;


import static org.junit.jupiter.api.Assertions.*;
import static spark.Spark.awaitInitialization;

/**
 * Created by michaelhilton on 1/26/17.
 */
class MainTest {
    String getModelTest = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":6}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":5,\"Down\":8}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":4,\"Down\":1},\"end\":{\"Across\":4,\"Down\":3}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":7,\"Down\":3},\"end\":{\"Across\":7,\"Down\":4}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":9,\"Down\":6},\"end\":{\"Across\":9,\"Down\":7}},\"playerHits\":[],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[]}";
    String testFire1 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":6}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":5,\"Down\":8}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":4,\"Down\":1},\"end\":{\"Across\":4,\"Down\":3}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":7,\"Down\":3},\"end\":{\"Across\":7,\"Down\":4}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":9,\"Down\":6},\"end\":{\"Across\":9,\"Down\":7}},\"playerHits\":[],\"playerMisses\":[{\"Across\":1,\"Down\":1}],\"computerHits\":[],\"computerMisses\":[{\"Across\":6,\"Down\":9}]}";
    String testFire2 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":6}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":5,\"Down\":8}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":4,\"Down\":1},\"end\":{\"Across\":4,\"Down\":3}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":7,\"Down\":3},\"end\":{\"Across\":7,\"Down\":4}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":9,\"Down\":6},\"end\":{\"Across\":9,\"Down\":7}},\"playerHits\":[{\"Across\":2,\"Down\":2}],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[{\"Across\":6,\"Down\":9}]}";
    String testRepeat1 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":6}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":5,\"Down\":8}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":4,\"Down\":1},\"end\":{\"Across\":4,\"Down\":3}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":7,\"Down\":3},\"end\":{\"Across\":7,\"Down\":4}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":9,\"Down\":6},\"end\":{\"Across\":9,\"Down\":7}},\"playerHits\":[],\"playerMisses\":[{\"Across\":1,\"Down\":1}],\"computerHits\":[],\"computerMisses\":[{\"Across\":6,\"Down\":9}]}";
    String testRepeat2 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":6}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":5,\"Down\":8}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":4,\"Down\":1},\"end\":{\"Across\":4,\"Down\":3}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":7,\"Down\":3},\"end\":{\"Across\":7,\"Down\":4}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":9,\"Down\":6},\"end\":{\"Across\":9,\"Down\":7}},\"playerHits\":[],\"playerMisses\":[{\"Across\":1,\"Down\":1}],\"computerHits\":[],\"computerMisses\":[{\"Across\":6,\"Down\":9},{\"Across\":8,\"Down\":4}]}";
    String testRepeat3 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":6}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":5,\"Down\":8}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":4,\"Down\":1},\"end\":{\"Across\":4,\"Down\":3}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":7,\"Down\":3},\"end\":{\"Across\":7,\"Down\":4}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":9,\"Down\":6},\"end\":{\"Across\":9,\"Down\":7}},\"playerHits\":[{\"Across\":2,\"Down\":2}],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[{\"Across\":6,\"Down\":9}]}";
    String testRepeat4 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":6}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":5,\"Down\":8}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":4,\"Down\":1},\"end\":{\"Across\":4,\"Down\":3}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":7,\"Down\":3},\"end\":{\"Across\":7,\"Down\":4}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":9,\"Down\":6},\"end\":{\"Across\":9,\"Down\":7}},\"playerHits\":[{\"Across\":2,\"Down\":2}],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[{\"Across\":6,\"Down\":9},{\"Across\":8,\"Down\":4}]}";
    @BeforeAll
    public static void beforeClass() {
        Main.main(null);
        awaitInitialization();
    }

    @AfterAll
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void testGetModel() {
        TestResponse res = request("GET", "/model", null);
        assertEquals(200, res.status);
        assertEquals(getModelTest,res.body);
    }

    @Test
    public void testPlaceShip() {

        TestResponse res = request("POST", "/placeShip/aircraftCarrier/1/1/horizontal", getModelTest);
        assertEquals(200, res.status);
     //   assertEquals("SHIP",res.body);
    }

    @Test
    public void testFireAtMiss() {
        GameModel model = new GameModel();
        Gson gson = new Gson();
        TestResponse res = request("POST", "/fire/1/1", gson.toJson(model));
        assertEquals(200, res.status);
        assertEquals(testFire1,res.body);
    }

    @Test
    public void testFireAtHit() {
        GameModel model = new GameModel();
        Gson gson = new Gson();
        TestResponse res = request("POST", "/fire/2/2", gson.toJson(model));
        assertEquals(200, res.status);
        assertEquals(testFire2,res.body);
    }

    @Test
    public void testRepeatFireMiss() {
        TestResponse res = request("POST", "/fire/1/1", testRepeat1);
        assertEquals(200, res.status);
        assertEquals(testRepeat2,res.body);
    }

    @Test
    public void testRepeatFireHit() {
        TestResponse res = request("POST", "/fire/2/2", testRepeat3);
        assertEquals(200, res.status);
        assertEquals(testRepeat4,res.body);
    }

    private TestResponse request(String method, String path, String body) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            if(body != null) {
                connection.setDoInput(true);
                OutputStream os = connection.getOutputStream();
                byte[] outputInBytes = body.getBytes("UTF-8");
                os.write(outputInBytes);
            }
            connection.connect();
            body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String,String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }


}