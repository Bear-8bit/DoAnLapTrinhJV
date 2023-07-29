package Model.GameObject;

public class Report {
    private Coordinate c;
    private boolean hits;
    private boolean sink;

    public Report(){
    }

    public Report(Coordinate c, boolean hits, boolean sink) {
        this.c = c;
        this.hits = hits;
        this.sink = sink;
    }
    public Coordinate getC() {
        return c;
    }
    public void setC(Coordinate c) {
        this.c = c;
    }
    public boolean isHits() {
        return hits;
    }
    public void setHits(boolean hits) {
        this.hits = hits;
    }
    public boolean isSink() {
        return sink;
    }
    public void setSink(boolean sink) {
        this.sink = sink;
    }
    public String toString(){
        return "coordinate:" + c + " hits:" + hits + " sink:" + sink;
    }
}
