package edu.oregonstate.cs361.battleship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import com.google.gson.Gson;
import spark.Request;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

public class Main {

    public static void main(String[] args) {
        //This will allow us to server the static pages such as index.html, app.js, etc.
        staticFiles.location("/public");

        //This will listen to GET requests to /model and return a clean new model
        get("/model", (req, res) -> newModel());
        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:row/:col", (req, res) -> fireAt(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:id/:row/:col/:orientation", (req, res) -> placeShip(req));
    }

    //This function should return a new model
    static String newModel() {

        GameModel model = new GameModel();
        Gson gson = new Gson();
        RandShips computerShips = new RandShips();
        computerShips.print();
        //Initialize computer_aircraftCarrier random coordinates.
        model.computer_aircraftCarrier.start.Across = computerShips.AircraftCordsStart[1] + 1;
        model.computer_aircraftCarrier.start.Down = computerShips.AircraftCordsStart[0] + 1;
        model.computer_aircraftCarrier.end.Across = computerShips.AircraftCordsEnd[1] + 1;
        model.computer_aircraftCarrier.end.Down = computerShips.AircraftCordsEnd[0] + 1;
        //Initialize computer_battleship random coordinates.
        model.computer_battleship.start.Across = computerShips.BattleshipCordsStart[1] + 1;
        model.computer_battleship.start.Down = computerShips.BattleshipCordsStart[0] + 1;
        model.computer_battleship.end.Across = computerShips.BattleshipCordsEnd[1] + 1;
        model.computer_battleship.end.Down = computerShips.BattleshipCordsEnd[0] + 1;
        //Initialize computer_cruiser random coordinates.
        model.computer_cruiser.start.Across = computerShips.CruiserCordsStart[1] + 1;
        model.computer_cruiser.start.Down = computerShips.CruiserCordsStart[0] + 1;
        model.computer_cruiser.end.Across = computerShips.CruiserCordsEnd[1] + 1;
        model.computer_cruiser.end.Down = computerShips.CruiserCordsEnd[0] + 1;
        //Initialize computer_destroyer random coordinates.
        model.computer_destroyer.start.Across = computerShips.DestroyerCordsStart[1] + 1;
        model.computer_destroyer.start.Down = computerShips.DestroyerCordsStart[0] + 1;
        model.computer_destroyer.end.Across = computerShips.DestroyerCordsEnd[1] + 1;
        model.computer_destroyer.end.Down = computerShips.DestroyerCordsEnd[0] + 1;
        //Initialize computer_submarine random coordinates.
        model.computer_submarine.start.Across = computerShips.SubmarineCordsStart[1] + 1;
        model.computer_submarine.start.Down = computerShips.SubmarineCordsStart[0] + 1;
        model.computer_submarine.end.Across = computerShips.SubmarineCordsEnd[1] + 1;
        model.computer_submarine.end.Down = computerShips.SubmarineCordsEnd[0] + 1;
        System.out.println(gson.toJson(model));

        return gson.toJson(model);
    }

    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    private static String placeShip(Request req) {
        return null;
    }

    //Similar to placeShip, but with firing.
    private static String fireAt(Request req) {
        Random rand = new Random(1);
        Gson gson = new Gson();
        GameModel model = gson.fromJson(req.body(),GameModel.class);
        Coordinate fire = new Coordinate(Integer.parseInt(req.params(":col")), Integer.parseInt(req.params(":row")));

        if (! checkRepeatFire(fire, model.playerHits, model.playerMisses)) {
            if (checkCollision(fire, model.computer_aircraftCarrier, model.computer_battleship, model.computer_cruiser, model.computer_destroyer, model.computer_submarine)) {
                model.playerHits.add(fire);
            } else {
                model.playerMisses.add(fire);
            }
        }


        Coordinate fireAI = new Coordinate(rand.nextInt(10) + 1, rand.nextInt(10) + 1);
        //Check for location that isn't already on board.
        while (checkRepeatFire(fireAI, model.computerHits, model.computerMisses)) {
            fireAI.Across = rand.nextInt(10) + 1;
            fireAI.Down = rand.nextInt(10) + 1;
        }

        //Check if AI hit the players ships
        if (checkCollision(fireAI, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine)) {
            model.computerHits.add(fireAI);
        } else {
            model.computerMisses.add(fireAI);
        }

        if (checkWin(model.playerHits, model.computerHits)) {
            model.ResetGame();
        }

        System.out.println(gson.toJson(model));
        return gson.toJson(model);
    }

    static boolean checkCollision(Coordinate cord, BattleshipModel ac, BattleshipModel bs, BattleshipModel c, BattleshipModel d, BattleshipModel s) {
        //Across = col, Down = row
        //Check if horizontal or vertical
        ArrayList<BattleshipModel> shipList = new ArrayList<BattleshipModel>();
        shipList.add(ac);
        shipList.add(bs);
        shipList.add(c);
        shipList.add(d);
        shipList.add(s);
        for (int i = 0; i<5; i++) {
            BattleshipModel temp = shipList.get(i);
            if (temp.start.Across == temp.end.Across && cord.Across == temp.start.Across) {
                if (cord.Down >= temp.start.Down && cord.Down <= temp.end.Down)
                    return true;
            } else if (temp.start.Down == cord.Down) { //Horizontal
                if (cord.Across >= temp.start.Across && cord.Across <= temp.end.Across)
                    return true;
            }
        }
        return false;
    }

    static boolean checkRepeatFire(Coordinate cord, List<Coordinate> hit, List<Coordinate> miss) {

        for (Coordinate aHit : hit) {
            if (cord.Across == aHit.Across && cord.Down == aHit.Down)
                return true;
        }
        for (Coordinate aMiss : miss) {
            if (cord.Across == aMiss.Across && cord.Down == aMiss.Down)
                return true;
        }
        return false;
    }
    
    //function that checks if won
    private static boolean checkWin(List<Coordinate> phits, List<Coordinate> chits){
        boolean playerWin=false;
        boolean computerWin=false;

        if(phits.size()==16)
            playerWin=true;

        if(chits.size()==16)
            computerWin=true;


        //checks if any of the two players has won and if so it resets the game
        if(playerWin || (playerWin && computerWin)) {
            System.out.println("Game won by player");
            return true;
        }
        else if(computerWin && (!playerWin)){
            System.out.println("Game won by computer");
            return true;
        }
        else
            return false;
    }
    

}
