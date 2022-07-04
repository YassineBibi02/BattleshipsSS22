package model;



public enum ShipType {
    MOUDAMIR(1),
    EMLEK(4),
    KANAS(3),
    EDIA(3),
    KABIRA(2),
    GUEZ(4);

    private final Integer label;

    ShipType(int l){
        this.label=l;
    }

    public int Getlabel(){
        return label;
    }
}
