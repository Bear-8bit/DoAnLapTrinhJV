package Model.GameObject;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Random;

public class Computer {
    private LinkedList<Coordinate> listHit;
    private Random random;
    private int hit;
    private LinkedList<String> possibility;
    private Coordinate lastHit;
    private String direction;
    private Board plBoard;
    private Coordinate firstHit; // Vị trí đánh trúng tàu đầu tiên

    public Computer(Board boardAdversary) {
        listHit = new LinkedList<Coordinate>();
        this.plBoard = boardAdversary;
        for (int i = 0; i < Board.SIZE_BOARD; i++) {
            for (int j = 0; j < Board.SIZE_BOARD; j++) {
                Coordinate c = new Coordinate(i, j);
                listHit.add(c);// Khởi tạo danh sách có thể có tàu
            }
        }
        random = new Random();
        hit = 0;
    }

    public Report myTurn() {

        Report rep = new Report();
        if (hit == 0) {
            boolean strike = shootRandom();
            rep.setC(lastHit);
            rep.setHits(strike);
            Ship sink;
            if (strike) {
                hit++;
                sink = plBoard.sunk(lastHit);
                if (sink != null) {
                    rep.setSink(true);
                    removeOutlines(sink);
                    hit = 0;
                    direction = null;
                } else {
                    firstHit = lastHit;
                    possibility = new LinkedList<String>();
                    initializeList();
                }
            }
            return rep;
        } // Bắn ngẫu nhiên
        if (hit == 1) {
            boolean strike = shootAimed1();
            Ship sink;
            rep.setC(lastHit);
            rep.setHits(strike);
            rep.setSink(false);
            if (strike) {
                hit++;
                possibility = null;
                sink = plBoard.sunk(lastHit);
                if (sink != null) {
                    rep.setSink(true);
                    removeOutlines(sink);
                    hit = 0;
                    direction = null;
                }
            }
            return rep;
        }
        if (hit >= 2) {
            boolean strike = shootAimed2();
            Ship sink;
            rep.setC(lastHit);
            rep.setHits(strike);
            rep.setSink(false);
            if (strike) {
                hit++;
                sink = plBoard.sunk(lastHit);
                if (sink != null) {
                    rep.setSink(true);
                    removeOutlines(sink);
                    hit = 0;
                    direction = null;
                }
            } else {
                invertDirection();
            }
            return rep;
        }
        return null;
    }

    private boolean shootRandom() {
        int shot = random.nextInt(listHit.size());
        Coordinate c = listHit.remove(shot);
        lastHit = c;
        boolean strike = plBoard.shoot(c);
        return strike;
    }

    private boolean shootAimed1() {
        boolean error = true;
        Coordinate c = null;
        do {
            int shot = random.nextInt(possibility.size());
            String where = possibility.remove(shot);
            c = new Coordinate(firstHit);
            c.move(where.charAt(0));
            direction = where;
            if (!plBoard.aqua(c)) {
                listHit.remove(c);
                error = false;
            }
        } while (error);
        lastHit = c;
        return plBoard.shoot(c);
    }

    private boolean shootAimed2() {
        boolean hitable = false;
        Coordinate c = new Coordinate(lastHit);
        do {
            c.move(direction.charAt(0));

            if (c.outsideBoard() || plBoard.aqua(c)) {
                invertDirection();
            } else {
                if (!plBoard.hit(c)) {
                    hitable = true;
                }

            }
        } while (!hitable);
        listHit.remove(c);
        lastHit = c;
        return plBoard.shoot(c);
    }

    //

    private void removeOutlines(Ship sink) {
        int X1 = sink.getX1();
        int X2 = sink.getX2();
        int Y1 = sink.getY1();
        int Y2 = sink.getY2();
        if (X1 == X2) {// Chiều ngang
            if (Y2 != 0) {
                Coordinate c = new Coordinate(X1, Y1 - 1);
                if (!plBoard.aqua(c)) {
                    listHit.remove(c);
                    plBoard.setAqua(c);
                }
            }
            if (Y2 != Board.SIZE_BOARD - 1) {
                Coordinate c = new Coordinate(X1, Y2 + 1);
                if (!plBoard.aqua(c)) {
                    listHit.remove(c);
                    plBoard.setAqua(c);
                }
            }
            if (X1 != 0) {
                for (int i = 0; i <= Y2 - Y1; i++) {
                    Coordinate c = new Coordinate(X1 - 1, Y1 + i);
                    if (!plBoard.aqua(c)) {
                        listHit.remove(c);
                        plBoard.setAqua(c);
                    }
                }

            }
            if (X1 != Board.SIZE_BOARD - 1) {
                for (int i = 0; i <= Y2 - Y1; i++) {
                    Coordinate c = new Coordinate(X1 + 1, Y1 + i);
                    if (!plBoard.aqua(c)) {
                        listHit.remove(c);
                        plBoard.setAqua(c);
                    }
                }
            }
        } else {// Chiều dọc
            if (X1 != 0) {
                Coordinate c = new Coordinate(X1 - 1, Y1);
                if (!plBoard.aqua(c)) {
                    listHit.remove(c);
                    plBoard.setAqua(c);
                }
            }
            if (X2 != Board.SIZE_BOARD - 1) {
                Coordinate c = new Coordinate(X2 + 1, Y1);
                if (!plBoard.aqua(c)) {
                    listHit.remove(c);
                    plBoard.setAqua(c);
                }
            }
            if (Y1 != 0) {
                for (int i = 0; i <= X2 - X1; i++) {
                    Coordinate c = new Coordinate(X1 + i, Y1 - 1);
                    if (!plBoard.aqua(c)) {
                        listHit.remove(c);
                        plBoard.setAqua(c);
                    }
                }

            }
            if (Y2 != Board.SIZE_BOARD - 1) {
                for (int i = 0; i <= X2 - X1; i++) {
                    Coordinate c = new Coordinate(X1 + i, Y1 + 1);
                    if (!plBoard.aqua(c)) {
                        listHit.remove(c);
                        plBoard.setAqua(c);
                    }
                }
            }
        }
    }

    private void initializeList() {
        if (lastHit.getCoordX() != 0) {
            possibility.add("N");
        }
        if (lastHit.getCoordX() != Board.SIZE_BOARD - 1) {
            possibility.add("S");
        }
        if (lastHit.getCoordY() != 0) {
            possibility.add("W");
        }
        if (lastHit.getCoordY() != Board.SIZE_BOARD - 1) {
            possibility.add("E");
        }
    }

    private void invertDirection() {
        if (direction.equals("N")) {
            direction = "S";
        } else if (direction.equals("S")) {
            direction = "N";
        } else if (direction.equals("E")) {
            direction = "W";
        } else if (direction.equals("W")) {
            direction = "E";
        }
    }
}
