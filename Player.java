public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * TODO: removes and returns the tile in given index
     */
    public Tile getAndRemoveTile(int index) {
        Tile removedTile = playerTiles[index];
        // Shifts the rest of the tiles to fill the gap
        for (int i = index; i < numberOfTiles - 1; i++) {
            playerTiles[i] = playerTiles[i + 1];
        }
        playerTiles[numberOfTiles - 1] = null; // Remove the last tile (now duplicate)
        numberOfTiles--; // Decrease tile count
        return removedTile;
    }

    /*
     * adds the given tile to the playerTiles in order
     * should also update numberOfTiles accordingly.
     * make sure playerTiles are not more than 15 at any time
     */
    public void addTile(Tile t) {
        if (numberOfTiles >= 15) {
            System.out.println("Cannot add more tiles. Maximum limit reached.");
            return;
        }
        // Find the correct position to insert the new tile while keeping the array sorted
        int indexNewTile = numberOfTiles;
        for (int i = 0; i < numberOfTiles; i++) {
            if (t.compareTo(playerTiles[i]) < 0) {
                indexNewTile = i;
                break;
            }
        }
    
        // Shift elements to the right to make a room for the new tile
        for (int i = numberOfTiles; i > indexNewTile; i--) {
            playerTiles[i] = playerTiles[i - 1];
        }
    
        playerTiles[indexNewTile] = t;
        numberOfTiles++;

    }

    /*
     * checks if this player's hand satisfies the winning condition
     * to win this player should have 3 chains of length 4, extra tiles
     * does not disturb the winning condition
     */
    public boolean isWinningHand() {
        int chainsOfFour = 0;
        int chainMember = 1; // length of the chain
    
        for (int i = 1; i < numberOfTiles; i++) {
            // Check if the current tile can form a chain with the previous one
            if (playerTiles[i].canFormChainWith(playerTiles[i - 1])) {
                chainMember++;
            } else {
                chainMember = 1; // Reset chain count
            }
    
            // If we have a chain of 4, count it
            if (chainMember == 4) {
                chainsOfFour++;
                chainMember = 1; // Reset to search for the next chain
            }
        }
    
        return chainsOfFour >= 3;
    }

    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].compareTo(t) == 0) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
