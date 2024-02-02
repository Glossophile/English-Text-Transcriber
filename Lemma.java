package convert17;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.List;
public class Lemma {
    public static Lemma copyLemma (Lemma targ) {
        //System.out.println(targ.printParms());
        Lemma copy = new Lemma(targ.getSpelling(),targ.getPronUK(),targ.getPronUS(),targ.getUsage(),targ.getSel(),targ.getAlter(),targ.getType(),targ.getReg());
        copy.setUnsyncUK(targ.getUnsyncUK());
        copy.setUnsyncUS(targ.getUnsyncUS());
        copy.setAllo(targ.getAllo());
        copy.setAltSpl(targ.getAltSpl());
        copy.setAltlo(targ.getAltlo());
        copy.setAllomorph1(targ.getAllomorph1());
        copy.setAllomorph2(targ.getAllomorph2());
        copy.syncopated = targ.syncopated;
        copy.exempt = targ.exempt;
        try {
            if (targ.structured()) {
                copy.setDghtr1(copyLemma(targ.getDghtr1()));
                copy.setDghtr2(copyLemma(targ.getDghtr2()));
                copy.setMthr(copyLemma(targ.getMthr()));
                copy.setSis(copyLemma(targ.getSis()));

            }
        }
        catch (NullPointerException nul) {
            //do nothing;
        }       
        catch (StackOverflowError over) {
            //do nothing;
        }
        return copy;
    }
    private String write = "|||";
    private String speak = "|||";
    private String altspk = "|||";
    private String attach = "";
    private char type = 'b';
    private char use = ' ';
    private int alter = 0;
    private Lemma sister;
    private Lemma mother;
    private Lemma daughter1;
    private Lemma daughter2;
    private char reg = 'g';
    private int depth = 1;
    private String allo = "|||";
    private String altlo = "|||";
    private String altspl = "|||";
    private String unsync1 = "|||";
    private String unsync2 = "|||";
    private String allomorph1 = "|||";
    private String allomorph2 = "|||";
    private static List<String> irreg1 = (Morphology.getLex()).voicealt;
    private static List<String> irreg2 = (Morphology.getLex()).ablaut;
    private static List<String> irreg3 = (Morphology.getLex()).rStem;
    private static List<String> irreg4 = Util.combine(irreg1,irreg2);
    private static List<String> irreg = Util.combine(irreg3,irreg4);
    //private List<Lemma> twins = new ArrayList<>();
    private boolean syncopated = false;
    private boolean exempt = false;
    public static List<Lemma> pack (String fod) {
        Lemma pack1 = new Lemma();
        pack1.write = fod;
        List<Lemma> pack2 = new ArrayList<>();
        pack2.add(pack1);
        return pack2;
    }
    public static List<Lemma> applyAllo (List<Lemma> nallo, String lo) {
        List<Lemma> alloed = new ArrayList<>();
        ListIterator<Lemma> nallos = nallo.listIterator();
        while (nallos.hasNext()) {
            Lemma nal = nallos.next();
            if ((nal.allo).equals("|||") || (nal.allo).equals(nal.write)) {
                nal.allo = lo;
            }
            alloed.add(nal);
        }
        return alloed;
    }
    public static List<Lemma> pack (String fod, String rtstr) {
        Lemma pack1 = new Lemma();
        pack1.write = fod;
        pack1.allo = rtstr;
        List<Lemma> pack2 = new ArrayList<>();
        pack2.add(pack1);
        return pack2;
    }
    public static List<Lemma> pack (Lemma fod) {
        List<Lemma> pack2 = new ArrayList<>();
        pack2.add(fod);
        return pack2;
    }
    public static List<Lemma> trimBlank (List<Lemma> raw) {
        List<Lemma> trim = new ArrayList<>();
        ListIterator<Lemma> psblks = raw.listIterator();
        while (psblks.hasNext()) {
            Lemma psblk = psblks.next();
            if (psblk.isFull()) {
                trim.add(psblk);
            }
        }
        return trim;
    }
    public static int overlap (String one, String two) {
        int com = 0;
        for (int oc = 0; oc < one.length() && oc < two.length(); oc++) {
            char ch1 = one.charAt(oc);
            char ch2 = two.charAt(oc);
            if (ch1 == ch2) {
                com++;
            }
            else {
                break;
            }
        }
        return com;
    }
    public static List<Lemma> match (String quer, List<Lemma> raw) {
        List<Lemma> trim = new ArrayList<>();
        ListIterator<Lemma> scrits = raw.listIterator();
        while (scrits.hasNext()) {
            Lemma scrit = scrits.next();
            String written = scrit.getSpelling();
            String alternate = scrit.getAltSpl();
            if (DictReader.equalIgnoreCase(written,quer)) {
                trim.add(scrit);
            }
            else if (DictReader.equalIgnoreCase(alternate,quer)) {
                scrit.setSpelling(alternate);
                trim.add(scrit);
            }
        }
        return trim;
    }
    public static List<Lemma> trimDup (List<Lemma> dup) {
        List<Lemma> trim = new ArrayList<>();
        ListIterator<Lemma> dups = dup.listIterator();
        while (dups.hasNext()) {
            Lemma dpst = dups.next();
            boolean duplicate = false;
            ListIterator<Lemma> iter = trim.listIterator();
            while (iter.hasNext()) {
                Lemma nwst = iter.next();
                String rtn1 = dpst.getSpelling();
                String rtn2 = nwst.getSpelling();
                boolean majusc1 = Character.isUpperCase(rtn1.charAt(0));
                boolean majusc2 = Character.isUpperCase(rtn2.charAt(0));
                if (nwst.compare(dpst) || majusc1 && !majusc2) {
                    duplicate = true;
                }
            }
            if (!duplicate) {
                //System.out.println(dpst.getSpelling());
                trim.add(dpst);
            }
        }
        return trim;
    }
    public static char encode (String dic) {
        char code;
        if (dic.startsWith("noun-U")) {
            code = 'M';
        }
        else if (dic.equals("number")) {
            code = 'D';
        }
        else if (dic.equals("ordinal number")) {
            code = 'A';
        }
        else if (dic.startsWith("noun") || dic.startsWith("verb") || dic.startsWith("adj") || dic.equals("preposition") || dic.equals("conjunction") || dic.equals("determiner") || dic.equals("exclamation") || dic.length() == 1 || dic.equals("substantive")) {
            code = Character.toUpperCase(dic.charAt(0));
        }
        else if (dic.equals("auxiliary verb") || dic.equals("modal verb") || dic.equals("modal/auxiliary verb")) {
            code = 'H';
        }
        else if (dic.equals("phrasal verb")) {
            code = 'V';
        }
        else if (dic.equals("adverb") || dic.equals("adv")) {
            code = 'B';
        }
        else if (dic.equals("pronoun")) {
            code = 'R';
        }
        else if (dic.equals("predeterminer")) {
            code = 'T';
        }
        else if (dic.contains("article")) {
            code = 'D';
        }
        else if (dic.contains("prefix")) {
            code = 'F';
        }
        else {
            code = '0';
        }
        return code;
    }
    public static String decode (char code) {
        switch (code) {
            case 'H': return "modal/auxiliary verb";
            case 'T': return "predeterminer";
            case 'P': return "preposition";
            case 'C': return "conjunction";
            case 'E': return "exclamation";
            case 'S': return "substantive";
            case 'D': return "determiner";
            case 'A': return "adjective";
            case 'R': return "pronoun";
            case 'B': return "adverb";
            case 'F': return "prefix";
            case 'M': return "noun-U";
            case 'V': return "verb";
            case 'N': return "noun";
            default: return "0";
        }
    }
    public void initSel(String arg) {
        this.attach = Character.toString(encode(arg));
    }
    public void addSel (char key) {
        this.attach += Character.toString(key);
    }
    public void setSpelling (String spelling) {
        this.write = spelling;
    }
    public void setPronUK (String pronUK) {
        this.speak = pronUK;
    }
    public void setPronUS (String pronUS) {
        this.altspk = pronUS;
    }
    public void setUsage (char part) {
        this.use = part;
    }
    public void setType (char typ) {
        this.type = typ;
    }
    public void setSel (String crit) {
        this.attach = crit;
    }
    public void setAltr (int alnm) {
        this.alter = alnm;
    }
    public void setReg (char gl) {
        this.reg = gl;
    }
    /*public void setTwins (List<Lemma> gemina) {
        this.twins = gemina;
    }*/
    public void setAllo (String altern) {
        this.allo = altern;
    }
    public void setAltlo (String altern) {
        this.altlo = altern;
    }
    public void setAllomorph1 (String altern) {
        this.allomorph1 = altern;
    }
    public void setAllomorph2 (String altern) {
        this.allomorph2 = altern;
    }
    public void setMthr (Lemma mom) {
        this.mother = mom;
        this.mother.setDepth(mom.getDepth()+this.depth);
    }
    public void setDghtr1 (Lemma dautr1) {
        this.daughter1 = dautr1;
        this.depth += dautr1.getDepth();
    }
    public void setDghtr2 (Lemma dautr2) {
        this.daughter2 = dautr2;
    }
    public void setSis (Lemma sis) {
        this.sister = sis;
    }
    public void setAltSpl (String altspell) {
        this.altspl = altspell;
    }
    public void setUnsyncUK (String unsync) {
        this.unsync1 = unsync;
        this.syncopated = true;
    }
    public void setUnsyncUS (String unsync) {
        this.unsync2 = unsync;
        this.syncopated = true;
    }
    public void resetUnsyncUK (String unsync) {
        this.unsync1 = unsync;
    }
    public void resetUnsyncUS (String unsync) {
        this.unsync2 = unsync;
    }
    public void setExempt (boolean exemption) {
        this.exempt = exemption;
    }
    public boolean syncopated () {
        return this.syncopated;
    }
    private static boolean participle = false;
    public static Lemma alternate (String orthog, Lemma temp, String quirks, Dialect accent, boolean ban) {
        String lab;
        String spl;
        String spk;
        Lemma word = temp;
        try {
            lab = (pullAlter(Util.pack(quirks),"lab","")).get(0);
        }
        catch (Exception blank) {
            lab = "---";
        }
        try {
            spl = (pullAlter(Util.pack(quirks),"spell","")).get(0);
            if ((lab.contains("plural") || lab.contains("past")) && spl.length() > 1 && spl.startsWith("-")) {
                spl = orthog.substring(0,orthog.lastIndexOf(spl.charAt(1)))+spl.substring(1);
                if (lab.contains("participle")) {
                    word.setUsage('S');
                }
            }
            /*if (lab.contains("plural") && spl.endsWith("s") && (spl.substring(0,spl.length()-1)).equals(orthog)) {
                spl = "|||";
            }*/
            word.setAllo(spl);
        }
        catch (Exception blank) {
            spl = "---";
        }
        try {
            String altpros = (pullAlter(Util.pack(quirks),"pron","")).get(0);
            spk = pullPron(Util.pack(altpros),"uk").get(0);
        }
        catch (Exception blank) {
            spk = "---";
        }
        String relpro = Phonology.shave(Lemma.decReg(word,accent));
        String anlg;
        if (spk.endsWith("ɪz") && lab.equals("plural")) {
            anlg = Phonology.shave(spk.substring(spk.length()-2));
        }
        else {
            anlg = spk;
        }
        char trmnl1 = relpro.charAt(relpro.length()-1);
        char trmnl2 = anlg.charAt(anlg.length()-1);
        char catg = word.getUsage();
        boolean alt3 = lab.equals("plural") && (Util.containsEnd(orthog,irreg1) || !anlg.equals("---") && Lingua.isVoiced(trmnl1) != Lingua.isVoiced(trmnl2)) || (orthog.endsWith("fe") || orthog.endsWith("f")) && spl.endsWith("ves");
        boolean alt7 = relpro.endsWith("ɪkəl") && orthog.endsWith("ical") || spk.endsWith("əl") && spl.endsWith("le");
        boolean alt12 = relpro.endsWith("t͡ʃaɪld") && orthog.endsWith("child") || spk.endsWith("t͡ʃɪldɹən") && spl.endsWith("children");
        boolean alt16 = (relpro.endsWith("mæn") || relpro.endsWith("mən")) && orthog.endsWith("man") || (spk.endsWith("mɛn") || relpro.endsWith("mən")) && spl.endsWith("men") || relpro.endsWith("wʊmən") && orthog.endsWith("woman") || spk.endsWith("wɪmɪn") && spl.endsWith("women") || spk.endsWith("tuːθ") && spl.endsWith("tooth") || spk.endsWith("tiːθ") && spl.endsWith("teeth") || spk.endsWith("fʊt") && spl.endsWith("foot") || spk.endsWith("fiːt") && spl.endsWith("feet");
        //boolean alt18 = (orthog.endsWith("able") || orthog.endsWith("ible")) && (relpro.endsWith("əbəl") || relpro.endsWith("ɪbəl")) || (orthog.endsWith("ably") || orthog.endsWith("ibly")) && (relpro.endsWith("əbliː") || relpro.endsWith("ɪbliː") || relpro.endsWith("əbli") || relpro.endsWith("ɪbli"));
        boolean alt5 = (catg == 'N' || catg == 'A' || catg == 'V') && (relpro.endsWith("iː") || relpro.endsWith("i") && orthog.endsWith("y")) && catg == 0;
        boolean alt4 = (catg == 'A' || catg == 'B') && relpro.endsWith("ŋ") && !relpro.endsWith("ɪŋ") && !orthog.endsWith("ing");
        boolean weird = Util.containsEnd(orthog,irreg) && !irreg.contains(orthog);
        if (alt3 || alt5 || alt4 || alt12 || alt16 || weird) {
            String backrest = Morphology.restem;
            Morphology morphology = new Morphology();
            morphology.orgform = orthog;
            List<Lemma> pscmp = morphology.findHead(orthog,word,accent,ban,false);
            List<Lemma> orgwrd = pack(word);
            List<Lemma> pshd = Morphology.extrHeads(pscmp);
            List<Lemma> hdtst = trimDup(Util.append(pshd,orgwrd));
            boolean root = false;
            if (hdtst.size() <= 1) {
                Morphology.restem = backrest;
                pshd = orgwrd;
                root = true;
            }
            else if (pshd.isEmpty()) {
                pshd = pscmp;
            }
            ListIterator<Lemma> hditer = pshd.listIterator();
            while (hditer.hasNext()) {
                Lemma wr = hditer.next();
                int wrlt = wr.getUsage();
                if (!root && wrlt == word.getUsage()) {
                    int wrnm = wr.getAlter();
                    word.setAltr(wrnm);
                }
                else if (alt12) {
                    word.setAltr(12);
                }
                else if (alt3) {
                    word.setAltr(3);
                }
                else if (alt4) {
                    word.setAltr(4);
                }
                /*else if (alt18) {
                    word.setAltr(18);
                }*/
                else if (alt5) {
                    word.setAltr(5);
                }
                else if (alt7) {
                    word.setAltr(7);
                }
                else if (alt16) {
                    word.setAltr(16);
                }
            }
        }
        else if (alt7) {
            word.setAltr(7);
        }
        return word;
    }
    public static List<String> pullPron (List<String> items3, String acc) {
        List<String> items = new ArrayList<>();
        ListIterator<String> iter3 = items3.listIterator();
        while (iter3.hasNext()) {
            String curit3 = iter3.next();
            List<String> items4 = Util.pullText(curit3,acc);
            if (items4.isEmpty()) {
                items4 = Util.pullText(curit3,"cmn");
            }
            ListIterator<String> iter4 = items4.listIterator();
            while (iter4.hasNext()) {
                String curit4 = iter4.next();
                List<String> items5 = Util.pullText(curit4,"ipa");
                ListIterator<String> iter5 = items5.listIterator();
                while (iter5.hasNext()) {
                    String curit5 = iter5.next();
                    curit5 = curit5.replace("] ","]");
                    items.add(curit5);
                }
            }
        }
        return items;
    }
    public static List<String> pullAlter (List<String> items3, String altr, String acc) {
        List<String> items = new ArrayList<>();
        ListIterator<String> iter3 = items3.listIterator();
        while (iter3.hasNext()) {
            String curit3 = iter3.next();
            List<String> items4 = Util.pullText(curit3,altr);
            ListIterator<String> iter4 = items4.listIterator();
            while (iter4.hasNext()) {
                String curit5 = iter4.next();
                items.add(curit5);
            }
            /*items4 = Util.pullText(curit3,"pron");
            iter4 = items4.listIterator();
            while (iter4.hasNext()) {
                List<String> items5 = pullPron(items4,acc);
                ListIterator<String> iter5 = items5.listIterator();
                while (iter5.hasNext()) {
                    String curit6 = iter5.next();
                    items.add(curit6);
                }
            }*/
            
        }
        return items;
    }
    public static List<String> pullGram (List<String> items3) {
        List<String> items = new ArrayList<>();
        ListIterator<String> iter3 = items3.listIterator();
        while (iter3.hasNext()) {
            String curit3 = iter3.next();
            List<String> items4 = Util.pullText(curit3,"pos");
            ListIterator<String> iter4 = items4.listIterator();
            while (iter4.hasNext()) {
                String curit4 = iter4.next();
                items.add(curit4);
            }
        }
        return items;
    }
    public static List<Lemma> extract (String org, String dictraw, Dialect acc, boolean ban, boolean syncopate) {
        //System.out.println(dictraw);
        List<Lemma> mined = new ArrayList<>();
        List<String> items1 = Util.pullText(dictraw,"entry");
        ListIterator<String> iter1 = items1.listIterator();
        String grph = "";
        String phn1 = "|||";
        String phn2 = "|||";
        char categ ;
        int al = 0;
        while (iter1.hasNext()) {
            String curit1 = iter1.next();
            boolean inflect = false;
            char backuse = '|';
            int backalt = 0;
            List<String> notes = Util.pullText(curit1,"note");
            ListIterator<String> iternotes = notes.listIterator();
            while (iternotes.hasNext()) {
                String iternote = iternotes.next();
                List<String> vdts = Util.trimDup(Util.pullText(iternote,"vdt"));
                ListIterator<String> vdtitr = vdts.listIterator();
                while (vdtitr.hasNext()) {
                    String vdt = vdtitr.next();
                    if (iternote.contains("spelling")) {
                        List<Lemma> refs = extract(vdt,DictReader.lookup(vdt,vdt),acc,ban,syncopate);
                        ListIterator<Lemma> refitr = refs.listIterator();
                        List<Lemma> videts = new ArrayList<>();
                        while (refitr.hasNext()) {
                            Lemma ceci = refitr.next();
                            String cespl = ceci.getSpelling();
                            if (cespl.equals(vdt)) {
                                ceci.setAltSpl(org);
                            }
                            videts.add(ceci);
                        }
                        return videts;
                    }
                    else {
                       List<Lemma> refs = extract(vdt,DictReader.lookup(vdt,vdt),acc,ban,syncopate);
                       backuse = (refs.get(0)).getUsage();
                       backalt = (refs.get(0)).getAlter();
                       inflect = true;
                    }
                }
            }
            List<String> items2 = Util.pullText(curit1,"subentry");
            ListIterator<String> iter2 = items2.listIterator();
            while (iter2.hasNext()) {
                String curit2 = iter2.next();
                List<String> orths = Util.pullText(curit2,"spell");
                List<String> grams = Util.pullText(curit2,"gram");
                List<String> alters = Util.pullText(curit2,"altr");
                ListIterator<String> iter3 = orths.listIterator();
                grph = orths.get(0);
                List<String> phons = Util.pullText(curit2,"pron");
                List<String> pullUK = pullPron(phons,"uk");
                List<String> pullUS = pullPron(phons,"us");
                try {
                    phn1 = pullUK.get(0);
                    phn2 = pullUS.get(0);
                    //System.out.println(phn1+"  "+phn2);
                }
                catch (IndexOutOfBoundsException blank) {
                    if (pullUS.isEmpty() && !pullUK.isEmpty()) {
                        phn2 = pullUK.get(0);
                    }
                    else if (pullUK.isEmpty() && !pullUS.isEmpty()) {
                        Morphology.dotted = true;
                        if (ban) {
                            continue;
                        }
                        else {
                            phn1 = (pullUS.get(0)).replaceAll("·", ".");
                            phn2 = (pullUS.get(0)).replaceAll("·", ".");
                            phn1 = Phonology.britishize(org,phn1);
                        }
                    }
                    else if (pullUS.isEmpty() && pullUK.isEmpty()) {
                        return mined;
                    }
                }
                boolean brclps = false;
                if (pullUK.size() == 2) {
                    phn1 = Phonology.collapse(orths.get(0),pullUK.get(0),pullUK.get(1));
                    if (pullUS.isEmpty()) {
                        phn2 = Phonology.cleanRhot(Phonology.dblRhot(phn1));
                    }
                    brclps = true;
                    //phn1 = Phonology.deRhot(phn1,true);
                }
                if (pullUS.size() == 2) {
                    phn2 = Phonology.collapse(orths.get(0),pullUS.get(0),pullUS.get(1));
                    if (pullUK.isEmpty()) {
                        phn1 = Phonology.deRhot(phn2,true);
                    }
                    phn2 = Phonology.cleanRhot(Phonology.dblRhot(phn2));
                }
                phn2 = Phonology.matchSylR(phn1,phn2);                          //rhotic matching must precede de-rhotacization for words like "pasteurize"
                if (brclps) {                                                   
                    phn1 = Phonology.deRhot(phn1,true);                         //moved from above and conditionalized 1-1-2019
                }
                List<String> pullPos = pullGram(grams);
                if (inflect) {
                    categ = backuse;
                    al = backalt;
                }
                else if (pullPos.size() >= 1) {
                    categ = encode(pullPos.get(0));
                }
                else {
                    categ = backuse;
                }
                Lemma slot = Phonology.process(new Lemma(grph,phn1,phn2,categ), acc, true);
                String quirks = "---";
                if (alters.size() >= 1) {
                    quirks = alters.get(0);
                }
                try {
                    slot = alternate(grph,slot,quirks,acc,ban);
                }
                catch (Exception stup) {
                    //do nothing;
                }
                if (inflect) {
                    slot.setAltr(backalt);
                }
                //System.out.println(slot.printParms());
                mined.add(slot);
                if (grams.size() > 1) {
                    ListIterator<String> grmitr = grams.listIterator();
                    while (grmitr.hasNext()) {
                        if (grmitr.hasPrevious()) {
                            String part = grmitr.next();
                            Lemma twin = Phonology.process(new Lemma(grph,phn1,phn2,encode(part),al),acc,true);
                            mined.add(twin);
                        }
                    }
                }
            }
        }
        mined = trimDup(mined);
        //System.out.println(multiPrint(mined));
        return mined;
    }
    public Lemma (String writ) {
        this.write = writ;
    }
    public Lemma (String writ, String sound1, String sound2, char cat, String sels, int alt) {
        this.write = writ;
        this.speak = sound1;
        this.altspk = sound2;
        this.use = cat;
        this.attach = sels;
        this.alter = alt;
    }
    public Lemma (String writ, String sound, char cat, String sels) {
        this.write = writ;
        this.speak = sound;
        this.altspk = sound;
        this.use = cat;
        this.attach = sels;
    }
    public Lemma (String writ, String sound, char cat, String sels, char typ, char gl) {
        this.write = writ;
        this.speak = sound;
        this.altspk = sound;
        this.use = cat;
        this.attach = sels;
        this.type = typ;
        this.reg = gl;
    }
    public Lemma (String writ, String sound, char cat, String sels, int alt, char typ) {
        this.write = writ;
        this.speak = sound;
        this.altspk = sound;
        this.use = cat;
        this.attach = sels;
        this.alter = alt;
        this.type = typ;
    }
    public Lemma (String writ, String sound1, String sound2, char cat, String sels, int alt, char typ) {
        this.write = writ;
        this.speak = sound1;
        this.altspk = sound2;
        this.use = cat;
        this.attach = sels;
        this.alter = alt;
        this.type = typ;
    }
    public Lemma (String writ, String sound1, String sound2, char cat, String sels, int alt, char typ, char gl) {
        this.write = writ;
        this.speak = sound1;
        this.altspk = sound2;
        this.use = cat;
        this.attach = sels;
        this.alter = alt;
        this.type = typ;
        this.reg = gl;
    }
    public Lemma (String writ, String sound1, String sound2, char cat) {
        this.write = writ;
        this.speak = sound1;
        this.altspk = sound2;
        this.use = cat;
    }
    public Lemma (String writ, String sound, char cat, int alt) {
        this.write = writ;
        this.speak = sound;
        this.altspk = sound;
        this.use = cat;
        this.alter = alt;
    
    }
    public Lemma (String writ, String sound, char cat) {
        this.write = writ;
        this.speak = sound;
        this.altspk = sound;
        this.use = cat;
    
    }
    public Lemma (String writ, String sound, char cat, char gl) {
        this.write = writ;
        this.speak = sound;
        this.altspk = sound;
        this.use = cat;
        this.reg = gl;
    }
    public Lemma (String writ, String sound1, String sound2, char cat, String sels, char typ) {
        this.write = writ;
        this.speak = sound1;
        this.altspk = sound2;
        this.use = cat;
        this.attach = sels;
        this.type = typ;
    }
    public Lemma (String writ, String sound1, String sound2, char cat, String sels, char typ, char gl) {
        this.write = writ;
        this.speak = sound1;
        this.altspk = sound2;
        this.use = cat;
        this.attach = sels;
        this.type = typ;
        this.reg = gl;
    }
    public Lemma (String writ, String sound1, String sound2, char cat, int alt) {
        this.write = writ;
        this.speak = sound1;
        this.altspk = sound2;
        this.use = cat;
        this.alter = alt;
    }
    public Lemma (String writ, String sound, char cat, String sels, char typ) {
        this.write = writ;
        this.speak = sound;
        this.altspk = sound;
        this.use = cat;
        this.attach = sels;
        this.type = typ;
    }
    public Lemma () {
        //do nothing;
    }
    public void morphParms (String writ, String sound1, String sound2, char cat, String sels, int alt, char typ, char gl) {
        this.write = writ;
        this.speak = sound1;
        this.altspk = sound2;
        this.use = cat;
        this.attach = sels;
        this.alter = alt;
        this.type = typ;
        this.reg = gl;
    }
    public void morphParms (String writ, String sound1, String sound2, char cat, String sels, int alt, char typ) {
        this.write = writ;
        this.speak = sound1;
        this.altspk = sound2;
        this.use = cat;
        this.attach = sels;
        this.alter = alt;
        this.type = typ;
    }
    public void morphParms (String writ, String sound1, String sound2, char cat, String sels, int alt) {
        this.write = writ;
        this.speak = sound1;
        this.altspk = sound2;
        this.use = cat;
        this.attach = sels;
        this.alter = alt;
    }
    public void morphParms (String writ, String sound, char cat, String sels, int alt) {
        this.write = writ;
        this.speak = sound;
        this.use = cat;
        this.attach = sels;
        this.alter = alt;
    }
    public void morphParms (String writ, String sound1, String sound2, char cat, int alt) {
        this.write = writ;
        this.speak = sound1;
        this.altspk = sound2;
        this.use = cat;
        this.alter = alt;
    }
    public void morphParms (String writ, String sound, char cat, int alt) {
        this.write = writ;
        this.speak = sound;
        this.use = cat;
        this.alter = alt;
    }
    public void morphParms (String writ, String sound, char cat, String sels) {
        this.write = writ;
        this.speak = sound;
        this.use = cat;
        this.attach = sels;
    }
    public void morphParms (String writ, String sound1, String sound2, char cat) {
        this.write = writ;
        this.speak = sound1;
        this.altspk = sound2;
        this.use = cat;
    }
    public void morphParms (String writ, String sound, char cat) {
        this.write = writ;
        this.speak = sound;
        this.use = cat;
    }
    public void morphParms (String writ, String sound) {
        this.write = writ;
        this.speak = sound;
    }
    public void morphParms (String writ) {
        this.write = writ;
    }
    public String getSpelling () {
        return this.write;
    }
    public String getPronUK () {
        return this.speak;
    }
    public String getPronUS () {
        return this.altspk;
    }
    public char getUsage () {
        return this.use;
    }
    public char getType () {
        return this.type;
    }
    public String getSel () {
        return this.attach;
    }
    public int getAlter () {
        return this.alter;
    }
    public char getReg () {
        return this.reg;
    }
    public Lemma getDghtr1 () {
        return this.daughter1;
    }
    public Lemma getDghtr2 () {
        return this.daughter2;
    }
    public Lemma getMthr () {
        return this.mother;
    }
    public Lemma getSis () {
        return this.sister;
    }
    /*public List<Lemma> getTwins () {
        return this.twins;
    }*/
    public String getAltSpl () {
        return this.altspl;
    }
    public String getUnsyncUK () {
        return this.unsync1;
    }
    public String getUnsyncUS () {
        return this.unsync2;
    }
    public boolean getSyncope() {
        return this.syncopated;
    }
    public String getAllo() {
        /*String allog = "";
        if (this.structured()) {
            if (isSuffix(this.daughter2)) {
                if ((this.allo).equals("")) {
                    allog = this.write+(this.daughter1).write;
                }
                else {
                    allog = this.allo+(this.daughter1).write;
                }
            }
            else if (isPrefix(this.daughter2)) {
                if ((this.allo).equals("")) {
                    allog = (this.daughter1).write+this.write;
                }
                else {
                    allog = (this.daughter1).write+this.allo;
                }
            }
        }
        else if ((this.allo).equals("")) {
            allog = this.write;
        }
        else {
            allog = this.allo;
        }
        return allog;*/
        return this.allo;
    }
    public String getAltlo () {
        return this.altlo;
    }
    public String getAllomorph1() {
        return this.allomorph1;
    }
    public String getAllomorph2() {
        if ((this.allomorph2).equals("|||")) {
            return this.allomorph1;
        }
        else {
            return this.allomorph2;
        }
    }
    public boolean getExempt() {
        return this.exempt;
    }
    public boolean hasAllo () {
        return !(this.allo).equals("|||");
    }
    public boolean hasAltlo () {
        return !(this.altlo).equals("|||");
    }
    public boolean allomorphic () {
        return this.hasAllo() || this.hasAltlo();
    }
    public String printParms () {
        String info = "\nID: "+this.write+"\nIPA(UK): "+this.speak+"\nIPA(US): "+this.altspk+"\nCAT: "+decode(this.use);
        if (!(this.attach).equals("")) {
            info += this.use+"\nSel: "+this.attach;
        }
        if (this.alter != 0) {
            info += "\nALTER: "+this.alter;
        }
        if (this.structured()) {
            info += "\n"+this.printTree();
        }
        if (!allo.equals("|||")) {
            info += "\n("+allo+")";
        }
        return info;
    }
    public boolean isFull () {
        if (!speak.equals("|||") && !speak.equals("") && !speak.contains("-")) {// && !altspk.equals("|||") && !altspk.equals("") && !altspk.contains("-")) {// && use != ' ' && use != '0') {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean compareNoSpell (Lemma juxt) {
        if ((this.speak).equals(juxt.speak) && (this.altspk).equals(juxt.altspk) && this.use == juxt.use && this.attach.equals(juxt.attach) && this.alter == juxt.alter && this.type == juxt.type) {
            return true;
        }
        else {
            return false;
        }
    }
    /*public boolean equals (Lemma juxt) {
        boolean unsync1 = (this.unsync1).equals(juxt.unsync1);
        boolean unsync2 = (this.unsync2).equals(juxt.unsync2);
        boolean allomorph1 = (this.allomorph1).equals(juxt.allomorph1);
        boolean allomorph2 = (this.allomorph2).equals(juxt.allomorph2);
        if ((this.write).equals(juxt.write) && (this.speak).equals(juxt.speak) && (this.altspk).equals(juxt.altspk) && unsync1 && unsync2 && this.use == juxt.use && this.attach.equals(juxt.attach) && this.alter == juxt.alter && this.type == juxt.type && (this.allo).equals(juxt.allo) && (this.altlo).equals(juxt.altlo) && (this.altspl).equals(juxt.altspl) && this.reg == juxt.reg && this.syncopated == juxt.syncopated && this.participle == juxt.participle) {
            if (this.structured() && juxt.structured()) {
                if ((this.daughter1).equals(juxt.daughter1) && (this.daughter2).equals(juxt.daughter2)) {
                    return true;
                }
            }
            else if (!this.structured() && !juxt.structured()) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }*/
    public boolean compare (Lemma juxt) {
        boolean unsync1 = (this.unsync1).equals(juxt.unsync1) && !(this.unsync1).equals("|||") && !(juxt.unsync1).equals("|||");
        boolean unsync2 = (this.unsync2).equals(juxt.unsync2) && !(this.unsync2).equals("|||") && !(juxt.unsync2).equals("|||");
        if ((this.write).equals(juxt.write) && this.use == juxt.use && this.attach.equals(juxt.attach) && this.alter == juxt.alter && this.type == juxt.type) {
            if ((this.speak).equals(juxt.speak) && (this.altspk).equals(juxt.altspk) || unsync1 && unsync2) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    public static boolean isPrefix (Lemma test) {
        List<Lemma> prefixes = (Morphology.getLex()).prefixes;
        boolean isPrefix = false;
        for (int ur = 0; ur < prefixes.size(); ur++) {
            Lemma pf = prefixes.get(ur);
            if (pf.compare(test)) {
                isPrefix = true;
            }
        }
        return isPrefix;
    }
    public static boolean isPrefix (List<Lemma> fixes) {
        boolean isPrefix = false;
        for (int ir = 0; ir < fixes.size(); ir++) {
            if (isSuffix(fixes.get(ir))) {
                isPrefix = true;
                break;
            }
        }
        return isPrefix;
    }
    public static boolean isSuffix (Lemma test) {
        List<Lemma> prefixes = Util.append((Morphology.getLex()).suffixes,(Morphology.getLex()).backsuff);
        boolean isPrefix = false;
        for (int ur = 0; ur < prefixes.size(); ur++) {
            Lemma pf = prefixes.get(ur);
            if (pf.compare(test)) {
                isPrefix = true;
            }
        }
        return isPrefix;
    }
    public static boolean isSuffix (List<Lemma> fixes) {
        boolean isSuffix = false;
        for (int ir = 0; ir < fixes.size(); ir++) {
            if (isSuffix(fixes.get(ir))) {
                isSuffix = true;
                break;
            }
        }
        return isSuffix;
    }
    public static boolean isRoot (Lemma test) {
        return !isPrefix(test) && !isSuffix(test);
    }
    public static boolean isRoot (List<Lemma> test) {
        return !isPrefix(test) && !isSuffix(test);
    }
    public String printTree() {
        String tree = "";
        if (this.daughter1 == null && this.daughter2 == null) {
            tree = this.write;
        }
        else {
            if (isPrefix(this.daughter2) || isRoot(this.daughter2)) {
                tree = "["+(this.daughter2).printTree()+"+"+(this.daughter1).printTree()+"]";
            }
            else {
                tree = "["+(this.daughter1).printTree()+"+"+(this.daughter2).printTree()+"]";
            }
        }
        return tree;
    }
    public String printTree(Dialect acnt) {
        String tree = "";
        if (this.daughter1 == null && this.daughter2 == null) {
            tree = decReg(this,acnt);
        }
        else {
            if (isPrefix(this.daughter2) || isRoot(this.daughter2)) {
                tree = (this.daughter2).printTree(acnt)+"+"+(this.daughter1).printTree(acnt);
            }
            else {
                tree = (this.daughter1).printTree(acnt)+"+"+(this.daughter2).printTree(acnt);
            }
        }
        return tree;
    }
    public static String multiPrint (List<Lemma> mlist) {
        String roster = "";
        for (int ls = 0; ls < mlist.size(); ls++) {
            Lemma ros = mlist.get(ls);
            roster += ros.printParms();
        }
        return roster;
    }
    public static String decReg (Lemma chal, Dialect dreg) {
        if ((dreg.getName()).equals("HB") && (chal.altspk).equals("|||")) {
            return chal.speak;
        }
        else if ((dreg.getName()).equals("HB")) {
            return Phonology.hybridize(chal,dreg);
        }
        else if ((dreg.getName()).equals("GA")) {
            return chal.altspk;
        }
        else {
            return chal.speak;
        }
    }
    public static List<Lemma> findWord (String qu, List<Lemma> src) {
        List<Lemma> finds = new ArrayList<>();
        for (int l = 0; l < src.size(); l++) {
            Lemma dum = src.get(l);
            if (dum.getSpelling().equals(qu)) {
                finds.add(dum);
            }
        }
        return finds;
    }
    public static List<Lemma> findPart (char spc, List<Lemma> src) {
        List<Lemma> finds = new ArrayList<>();
        for (int l = 0; l < src.size(); l++) {
            Lemma dum = src.get(l);
            if (dum.getUsage() == spc) {
                finds.add(dum);
            }
        }
        return finds;
    }
    
    public static Lemma findBase(Lemma whole) {
        if (whole.daughter1 == null) {
            return whole;
        }
        else {
            return findBase(whole.daughter1);
        }
    }
    public int getDepth() {
        return this.depth;
    }
    public void setDepth(int dep) {
        this.depth = dep;
    }
    public static Lemma structure (Lemma mater, Lemma filia1, Lemma filia2) {
        filia1.mother = mater;
        filia2.mother = mater;
        filia1.sister = filia2;
        filia2.sister = filia1;
        mater.daughter1 = filia1;
        mater.daughter2 = filia2;
        mater.setDepth(mater.getDepth()+filia1.getDepth());
        if (isPrefix(filia2) || isRoot(filia2)) {
            mater.setAltr(filia1.getAlter());
        }
        return mater;
    }
    public boolean structured () {
        if (this.daughter1 == null || this.daughter2 == null) {
            return false;
        }
        else {
            return true;
        }
    }
    public static boolean structured (List<Lemma> prob) {
        boolean interstr = false;
        for (int sr = 0; sr < prob.size(); sr++) {
            if ((prob.get(sr)).structured()) {
                interstr = true;
                break;
            }
        }
        return interstr;
    }
}
