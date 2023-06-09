package model;


public class ChessPiece {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        if(this.rank==1&&target.rank==8&&this.getOwner()!=target.getOwner()){
            return true;
        }
        if((this.rank>=target.rank)&&this.getOwner()!=target.getOwner()&&!(this.rank==8&&target.rank==1)){
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public int getRank() {return this.rank;}
}
