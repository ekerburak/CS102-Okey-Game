import java.util.Random;

public class OkeyGame {

    Player[] players;
    Tile[] tiles;
    private static final Random RNG = new Random();

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[112];
        int currentTile = 0;

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
    }

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {

    }

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        return null;
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        return null;
    }

    public void shuffleTiles() {
        for(int i = 0; i < tiles.length; i++) {
            Tile tmp = tiles[i];
            int j = RNG.nextInt(tiles.length);
            tiles[i] = tiles[j];
            tiles[j] = tmp;    
        }
    }

    public boolean didGameFinish() {
        if (players[currentPlayerIndex].isWinningHand()) {
            return true;
        }
        return false;
    }

    /*
     * Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() {
        int count = 0; // number of tiles that can form a chain with the last discarded tile
        boolean hasSame = false;

        for (Tile t : players[currentPlayerIndex].getTiles()) {
            if(t.canFormChainWith(lastDiscardedTile)) {
                count++; 
            }
            if(t.compareTo(lastDiscardedTile) == 0) {
                hasSame = true;
            }
        }
        
        String pickedTileStr = "";
        if(!hasSame && 0 < count) {
            pickedTileStr = getTopTile();
        } else {
            pickedTileStr = getLastDiscardedTile();
        }
        System.out.println("User " + currentPlayerIndex + " picked the tile " + pickedTileStr);
    }

    /*
     * Current computer player will discard the least useful tile.
     * this method should print what tile is discarded since it should be
     * known by other players. You may first discard duplicates and then
     * the single tiles and tiles that contribute to the smallest chains.
     */
    public void discardTileForComputer() {
        int idx = -1, worstChain = 4;

        for(int i = 0; i < players[currentPlayerIndex].getTiles().length; i++) {
            int chain = 0;
            for(int j = i+1; j < players[currentPlayerIndex].getTiles().length; j++) {
                // duplicate tiles are discarded first
                if(players[currentPlayerIndex].getTiles()[i].compareTo(players[currentPlayerIndex].getTiles()[j]) == 0) {
                    discardTile(i);
                    return;
                }
                // can form a chain
                if(players[currentPlayerIndex].getTiles()[i].canFormChainWith(players[currentPlayerIndex].getTiles()[j])) {
                    chain++;
                }
            }
            if(chain < worstChain) {
                worstChain = chain;
                idx = i;
            }
        }
        discardTile(idx);
    }

    /*
     * discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) { 
        lastDiscardedTile = players[currentPlayerIndex].getAndRemoveTile(tileIndex);
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
