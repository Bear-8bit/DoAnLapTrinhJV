package Model.GameObject;

public class Coordinate {
    private int coordX,coordY;

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public Coordinate(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public Coordinate(Coordinate c){
        this.coordX = c.coordX;
        this.coordY = c.coordY;
    }

    public void move(char where){
        switch(where){
            case 'N':
                coordX--;
                break;
            case 'S':
                coordX++;
                break;
            case 'W':
                coordY--;
                break;
            case 'E':
                coordY++;
                break;
        }
    }

    public String toString(){
        char Y=(char)(coordY + 65);
        return " " + (coordX+1) + " " + Y;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Coordinate other = (Coordinate) obj;
        if (coordX != other.coordX)
            return false;
        if (coordY != other.coordY)
            return false;
        return true;
    }

    public boolean outsideBoard(){
        if(coordX >= Board.SIZE_BOARD || coordY >= Board.SIZE_BOARD || coordX < 0 || coordY < 0)
            return true;
        return false;
    }
}
