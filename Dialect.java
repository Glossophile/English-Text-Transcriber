package convert17;
public class Dialect {
    private String name = "";
    private boolean rhotic = false;
    private boolean syncope = false;
    private boolean clip = false;
    private boolean mergeFB = false;
    private boolean mergeCC = false;
    private boolean mergeHF = false;
    private boolean mergeMM = false;
    private boolean raiseAsh = false;
    private boolean wHine = false;
    private boolean wunt = false;
    private boolean dropYod = false;
    private boolean dropYur = false;
    private boolean glide = false;
    private boolean backA = false;
    private boolean flap = false;
    public Dialect (String label, boolean parm1, boolean parm2, boolean parm3, boolean parm4, boolean parm5, boolean parm6, boolean parm7, boolean parm8, boolean parm9, boolean parm10, boolean parm11, boolean parm12, boolean parm13, boolean parm14, boolean parm15) {
        name = label;
        rhotic = parm1;
        syncope = parm2;
        clip = parm3;
        mergeFB = parm4;
        mergeCC = parm5;
        mergeHF = parm6;
        mergeMM = parm7;
        raiseAsh = parm8;
        wHine = parm9;
        wunt = parm10;
        dropYod = parm11;
        dropYur = parm12;
        glide = parm13;
        backA = parm14;
        flap = parm15;
    }
    public String printFtrs () {
        return "Rhotic: "+rhotic+"\nSyncope: "+syncope+"\nClipping: "+clip+"\nFather-Bother Merger: "+mergeFB+"\nCot-Caught Merger: "+mergeCC+"\nHurry-Furry Merger: "+mergeHF+"\nMary-Merry Merger: "+mergeMM+"\nAsh-Raising: "+raiseAsh+"\nWine-Whine Meger: "+wHine+"\nPost-W Unrounding: "+wunt+"\n"+"Yod-Dropping: "+dropYod+"\nFigure-Digger Merger: "+"\nGliding: "+glide+"\nKeep Back A: "+backA;
    }
    public boolean[] getParms() {
        boolean[] parms = {this.rhotic,this.syncope,this.mergeFB,this.mergeCC,this.mergeHF,this.wHine,this.dropYod,this.wunt,this.backA,this.mergeMM,this.raiseAsh};
        return parms;
    }
    public String getName() {
        return this.name;
    }
    public boolean getRhotic() {
        return rhotic;
    }
    public boolean getSyncope() {
        return this.syncope;
    }
    public boolean getClip() {
        return this.clip;
    }
    public boolean getMergeFB() {
        return this.mergeFB;
    }
    public boolean getMergeCC() {
        return this.mergeCC;
    }
    public boolean getmergeHF() {
        return this.mergeHF;
    }
    public boolean getMergeMM() {
        return this.mergeMM;
    }
    public boolean getRaiseAsh() {
        return this.raiseAsh;
    }
    public boolean getWhine() {
        return this.wHine;
    }
    public boolean getWunt() {
        return this.wunt;
    }
    public boolean getDropYod() {
        return this.dropYod;
    }
    public boolean getDropYur() {
        return this.dropYur;
    }
    public boolean getGlide() {
        return this.glide;
    }
    public boolean getBackA() {
        return this.backA;
    }
    public boolean getFlap() {
        return this.flap;
    }
    public void setName (String label) {
        this.name = label;
    }
    public void setRhotic (boolean setting) {
        this.rhotic = setting;
    }
    public void setSyncope (boolean setting) {
        this.syncope = setting;
    }
    public void setClip (boolean setting) {
        this.clip = setting;
    }
    public void setMergeFB (boolean setting) {
        this.mergeFB = setting;
    }
    public void setMergeCC (boolean setting) {
        this.mergeCC = setting;
    }
    public void setMergeHF (boolean setting) {
        this.mergeHF = setting;
    }
    public void setMergeMM (boolean setting) {
        this.mergeMM = setting;
    }
    public void setWhine (boolean setting) {
        this.wHine = setting;
    }
    public void setWunt (boolean setting) {
        this.wunt = setting;
    }
    public void setDropYod (boolean setting) {
        this.dropYod = setting;
    }
    public void setDropYur (boolean setting) {
        this.dropYur = setting;
    }
    public void setGlide (boolean setting) {
        this.glide = setting;
    }
    public void setBackA (boolean setting) {
        this.backA = setting;
    }
    public void setRaiseAsh (boolean setting) {
        this.raiseAsh = setting;
    }
    public void setFlap (boolean setting) {
        this.flap = setting;
    }
}
