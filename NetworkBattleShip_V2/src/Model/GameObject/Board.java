package Model.GameObject;

import java.util.LinkedList;
import java.util.Random;

/*
    Class này dùng để tạo Sàn chơi với kích cỡ 10x10
    chứa các method Random vị đặt tàu của người chơi,
    Đặt tàu, Chọn hướng đặt tàu và đặt tàu ngẫu nhiên cho máy
 */
public class Board {
    public static final int SIZE_BOARD = 10; //Kích cỡ của sàn
    private final char NULL = '0', SHIP = 'X', AQUA = 'A', HIT = 'H'; //Quy định cho sàn
    private char[][] board; // Sử dụng ma trận kiểu dữ liệu là char để tạo sàn
    private LinkedList<Ship> listShip; // Danh sách tàu

    public Board() {
        listShip = new LinkedList<Ship>();
        board = new char[SIZE_BOARD][SIZE_BOARD];
        for (int i = 0; i < SIZE_BOARD; i++)
            for (int j = 0; j < SIZE_BOARD; j++)
                board[i][j] = NULL;
    }

    //Method dùng để đặt tàu một cách ngẫu nhiên cho máy
    //sử dụng Method clear() và Method insertShipRandom( Random , kích cỡ tàu)
    public void fillBoardRandom() {
        clear();
        Random r = new Random();

        insertShipRandom(r, 5);
        insertShipRandom(r, 4);
        insertShipRandom(r, 3);
        insertShipRandom(r, 3);
        insertShipRandom(r, 2);
        insertShipRandom(r, 2);
        insertShipRandom(r, 2);
    }

    //Method để tạo một sàn chơi mới
    private void clear() {
        for (int i = 0; i < SIZE_BOARD; i++)
            for (int j = 0; j < SIZE_BOARD; j++)
                board[i][j] = NULL;

    }

    //Method đặt tàu với số nhận vào tọa độ x,y kích cỡ và chiều đặt
    public boolean insertShip(int x, int y, int size, int dir) {
        if (dir == 1 && x + size > SIZE_BOARD) {
            return false;
        } // Chiều dọc
        if (dir == 0 && y + size > SHIP) {
            return false;
        } // Chiều ngang
        boolean insert;

        if (dir == 0)
            insert = verifyHorizontal(x, y, size);
        else
            insert = verifyVertical(x, y, size);

        if (!insert)
            return false;
        if (dir == 0) {
            Ship s = new Ship(x, y, x, y + size - 1);
            listShip.add(s);
        } else {
            Ship s = new Ship(x, y, x + size - 1, y);
            listShip.add(s);
        }
        for (int i = 0; i < size; i++) {
            if (dir == 0) {
                board[x][y + i] = SHIP;
            } else
                board[x + i][y] = SHIP;
        }
        return true;
    }

    //Method đặt tàu một cách ngẫu nhiên
    public int[] insertShipRandom(Random random, int dimension) {
        boolean insert;
        int[] data = new int[4];
        int direction, row, column;
        do {
            insert = true;
            direction = random.nextInt(2); // 0 = Chiều ngang,  1 = Chiều dọc
            if (direction == 0) {
                column = random.nextInt(SIZE_BOARD - dimension + 1);
                row = random.nextInt(SIZE_BOARD);
            } else {
                column = random.nextInt(SIZE_BOARD);
                row = random.nextInt(SIZE_BOARD - dimension + 1);
            }
            if (direction == 0)
                insert = verifyHorizontal(row, column, dimension);
            else
                insert = verifyVertical(row, column, dimension);
        } while (!insert);
        if (direction == 0) {
            Ship s = new Ship(row, column, row, column + dimension - 1);
            listShip.add(s);
        } else {
            Ship s = new Ship(row, column, row + dimension - 1, column);
            listShip.add(s);
        }
        for (int i = 0; i < dimension; i++) {
            if (direction == 0) {
                board[row][column + i] = SHIP;
            } else
                board[row + i][column] = SHIP;
        }
        data[0] = row;
        data[1] = column;
        data[2] = dimension;
        data[3] = direction;
        return data;
    }

    //Method đặt tàu theo chiều dọc
    public boolean verifyVertical(int row, int column, int dimension) {
        if (row != 0)
            if (board[row - 1][column] == SHIP)
                return false;
        if (row != SIZE_BOARD - dimension) //Nếu tàu nắm ở ngoài bảng
            if (board[row + dimension][column] == SHIP)
                return false;
        for (int i = 0; i < dimension; i++) {
            if (column != 0)
                if (board[row + i][column - 1] == SHIP)
                    return false;
            if (column != SIZE_BOARD - 1)
                if (board[row + i][column + 1] == SHIP)
                    return false;
            if (board[row + i][column] == SHIP)
                return false;
        }
        return true;
    }

    //Method đặt tàu theo chiều ngang
    public boolean verifyHorizontal(int row, int column, int dimension) {
        if (column != 0)
            if (board[row][column - 1] == SHIP)
                return false;
        if (column != SIZE_BOARD - dimension)
            if (board[row][column + dimension] == SHIP)
                return false;
        for (int i = 0; i < dimension; i++) {
            if (row != 0)
                if (board[row - 1][column + i] == SHIP)
                    return false;
            if (row != SIZE_BOARD - 1)
                if (board[row + 1][column + i] == SHIP)
                    return false;
            if (board[row][column + i] == SHIP)
                return false;
        }
        return true;
    }

    public boolean shoot(Coordinate c) {
        int row = c.getCoordX();
        int column = c.getCoordY();
        if (board[row][column] == SHIP) {
            board[row][column] = HIT;
            return true;
        }
        board[row][column] = AQUA;
        return false;
    }

    public Ship sunk(Coordinate c) {
        int row = c.getCoordX();
        int col = c.getCoordY();
        Ship ship = null;
        for (int i = 0; i < listShip.size(); i++) {
            if (listShip.get(i).coordiante(row, col)) {
                ship = listShip.get(i);
                break;
            }
        }
        for (int i = ship.getX1(); i <= ship.getX2(); i++) {
            for (int j = ship.getY1(); j <= ship.getY2(); j++) {
                if (board[i][j] != HIT) {
                    return null;
                }
            }
        }
        listShip.remove(ship);
        return ship;
    }

    public void setAqua(Coordinate c) {
        board[c.getCoordX()][c.getCoordY()] = AQUA;
    }

    public boolean aqua(Coordinate c) {
        return board[c.getCoordX()][c.getCoordY()] == AQUA;
    }

    public boolean hit(Coordinate c) {
        return board[c.getCoordX()][c.getCoordY()] == HIT;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE_BOARD; i++) {
            for (int j = 0; j < SIZE_BOARD; j++) {
                sb.append(board[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
