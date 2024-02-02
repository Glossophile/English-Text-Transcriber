package convert17;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Morphology {
    public String orgform;
    public static Lexicon dict;
    public static List<Lemma> dictionary;
    public static List<Lemma> exports = new ArrayList<>();
    public static boolean natLex = false;
    public List<Lemma> adjMorph (String tradit, List<Lemma> compl, Dialect acnt, boolean rej) {
        List<Lemma> dcmps = new ArrayList<>();
        String stem = tradit;
        for (int er = 0; er < compl.size(); er++) {
            Lemma crcmp = compl.get(er);
            if (!crcmp.structured()) {
                List<Lemma> treed = findStem(stem,crcmp,acnt,rej);
                for (int hr = 0; hr < treed.size(); hr++) {
                    Lemma crtrd = treed.get(hr);
                    dcmps.add(crtrd);
                }
                dict.store(dcmps);
                /*exports = Util.append(exports,dcmps);
                if (!spares.isEmpty()) {
                    exports = Util.append(exports,spares);
                    //spares.clear();
                }
                dict.store(exports);
                //dict.exportEntries(exports);*/
            }
            else {
                dcmps.add(crcmp);
            }
        }
        return dcmps;
    }
    public static Lexicon getLex () {
        return dict;
    }
    public static List<Lemma> getDict () {
        return dictionary;
    }
    public static int findMorph (String prompt) {
        ListIterator<Lemma> diction = dictionary.listIterator();
        int c = 0;
        while (diction.hasNext()) {
            Lemma cur = diction.next();
            String mat = cur.getSpelling();
            String alm = cur.getAltSpl();
            //System.out.println(prompt+" "+mat+" "+alm);
            if (prompt.equals(mat) || prompt.equals(alm)) {
                break;
            }
            c++;
        }
        return c;
    }
    public static List<Lemma[]> findAgr (List<Lemma> main, String aff, char pos)  throws IndexOutOfBoundsException {
        List<Lemma> affixes;
        if (pos == 'p') {
            affixes = Lexicon.prefixes;
        }
        else if (pos == 'b') {
            affixes = Lexicon.backsuff;
        }
        else {
            affixes = Lexicon.suffixes;
        }
        Lemma mnrt = new Lemma();
        mnrt.setSpelling((main.get(0)).getSpelling());
        mnrt.setPronUK("|||");
        Lemma afx = new Lemma();
        afx.setSpelling(aff);
        afx.setPronUK("|||");
        List<Lemma[]> comps = new ArrayList<>();
        for (int z = 0; z < main.size(); z++) {
            Lemma attach = main.get(z);
            for (int w = 0; w < affixes.size(); w++) {
                Lemma mrf = affixes.get(w);
                String splg = mrf.getSpelling();
                if (splg.equals(aff) && Lingua.select(attach,mrf)) {
                    mnrt = attach;
                    afx = mrf;
                    Lemma[] pair = {mnrt,afx};
                    comps.add(pair);
                }
            }
        }
        return comps;
    }
    public List<Lemma> matchMorph (String org, List<Lemma> bases, List<Lemma> look, Dialect dial, String pfix, char pos, boolean ign) {
        List<Lemma[]> pairs;
        String allo;
        try {
            pairs = findAgr(bases,pfix,pos);
        }
        catch (IndexOutOfBoundsException nope) {
            pairs = new ArrayList<Lemma[]>();
        }
        if(pairs.isEmpty()) {
            List <Lemma> bases3 = parseLemma(org,bases,dial,true,true,true,ign);
            limit++;
            try {
                pairs = findAgr(bases3,pfix,pos);
            }
            catch (IndexOutOfBoundsException nope) {
                //return look;
            }
        }
        if(pairs.isEmpty() && pos == 's') {
            try {
                pairs = findAgr(bases,pfix,'b');
            }
            catch (IndexOutOfBoundsException nope) {
                //return look;
            }
        }
        List<Lemma> yields = new ArrayList<>();
        ListIterator<Lemma[]> prstr = pairs.listIterator();
        while (prstr.hasNext()) {
            Lemma[] pair = prstr.next();
            Lemma part1 = pair[0];
            Lemma part2 = pair[1];
            //System.out.println(org+": "+part1.getSpelling()+"+"+part2.getSpelling());
            Lemma conc = concat(org,part1,part2,dial,pos);
            rad = false;
            boolean deriv = lat && (part1.getSpelling()).equals(latbase);
            if (!deriv && !(conc.getPronUK()).equals("|||")) {
                yields.add(conc);
                limit = 0;
            }
        }
        //System.out.println(Lemma.multiPrint(yields));
        return Lemma.trimBlank(yields);
    }
    public static Lemma findFix (Lemma main, String af, char ch) {
        Lemma seld = new Lemma();
        List<Lemma> affixes;
        if (ch == 'p') {
            affixes = Lexicon.prefixes;
        }
        else {
            affixes = Lexicon.suffixes;
        }
        for (int y = 0; y < affixes.size(); y++) {
            Lemma mrf = affixes.get(y);
            String spl = mrf.getSpelling();
            if (spl.equals(af) && Lingua.select(main,mrf)) {
                seld = mrf;
                break;
            }
        }
        return seld;
    }
    public static int findMB (String cmp, char pos) {
        List<Lemma> affixes;
        if (pos == 'p') {
            affixes = Lexicon.prefixes;
        }
        else if (pos == 'l') {
            affixes = Lexicon.latinate;
        }
        else {
            affixes = Lexicon.suffixes;
        }
        Lemma morph;
        String afx;
        int inx = 0;
        int bnd = 0;
        while (inx < affixes.size()) {
            morph = affixes.get(inx);
            afx = morph.getSpelling();
            if (pos == 'p' && cmp.startsWith(afx)) {
                bnd = afx.length();
                break;
            }
            else if (cmp.endsWith(afx)) {
                bnd = cmp.lastIndexOf(afx);
                break;
            }
            inx++;
        }
        return bnd;
    }
    private static boolean gem = false;
    private static boolean magicE = false;
    private static boolean endY = false;
    private static boolean delY = false;
    public static List<Lemma> ablauted = new ArrayList<>();
    public String adjAblaut (String possabl) {
        String mal = possabl;
        String rev = mal;
        if (mal.endsWith("women")) {
            rev = mal.substring(0,mal.length()-5)+"woman";
        }
        else if (mal.endsWith("men")) {
            rev = mal.substring(0,mal.length()-2)+"an";
        }
        else if (mal.endsWith("teeth")) {
            rev = mal.substring(0,mal.length()-4)+"ooth";
        }
        else if (mal.endsWith("feet")) {
            rev = mal.substring(0,mal.length()-3)+"oot";
        }
        return rev;
    }
    public List<Lemma> internalInfl (List<Lemma> ablauted, Dialect dial, boolean trypfx, boolean trysfx, boolean record, boolean bar) {
        List<Lemma> intInfl = new ArrayList<>();
        ListIterator<Lemma> abltitr = ablauted.listIterator();
        while (abltitr.hasNext()) {
            Lemma curablt = abltitr.next();
            String spel = curablt.getSpelling();
            String tmpUK = curablt.getPronUK();
            String tmpUS = curablt.getPronUS();
            String intl1 = "";
            String repl1 = "";
            String intl2 = "";
            String repl2 = "";
            String splr1 = "";
            String splr2 = "";
            //String rev = "";
            if (spel.endsWith("woman")) {
                intl1 = "wʊ.mən";
                repl1 = "wɪ.mɪn";
                intl2 = "wʊ.mən";
                repl2 = "wɪ.mɪn";
                splr1 = "woman";
                splr2 = "women";
            }
            else if (spel.endsWith("man")) {
                intl1 = "mæn";
                repl1 = "mɛn";
                intl2 = "mæn";
                repl2 = "mɛn";
                splr1 = "man";
                splr2 = "men";
            }
            else if (spel.endsWith("tooth")) {
                intl1 = "tuːθ";
                repl1 = "tiːθ";
                intl2 = "tuːθ";
                repl2 = "tiːθ";
                splr1 = "tooth";
                splr2 = "teeth";
            }
            else if (spel.endsWith("foot")) {
                intl1 = "fʊt";
                repl1 = "fiːt";
                intl2 = "fʊt";
                repl2 = "fiːt";
                splr1 = "foot";
                splr2 = "feet";
            }
            if (tmpUS.equals("|||") || tmpUS.length() == 0) {
                tmpUS = tmpUK;
            }
            if (tmpUK.endsWith("mən")) {
                intl1 = "mən";
                repl1 = "mən";
            }
            if (tmpUS.endsWith("mən")) {
                intl2 = "mən";
                repl2 = "mən";
            }
            int cut1 = tmpUK.lastIndexOf(intl1);
            int cut2 = tmpUS.lastIndexOf(intl2);
            int cut3 = spel.lastIndexOf(splr1);
            curablt.setAllomorph1(tmpUK);
            curablt.setAllomorph2(tmpUS);
            tmpUK = tmpUK.substring(0,cut1)+repl1;
            tmpUS = tmpUS.substring(0,cut2)+repl2;
            spel = spel.substring(0,cut3)+splr2;
            curablt.setUnsyncUK(tmpUK);
            curablt.setUnsyncUS(tmpUS);
            curablt.setPronUK(Phonology.syncope(spel,tmpUK,dial));
            curablt.setPronUS(Phonology.syncope(spel,tmpUS,dial));
            curablt.setSpelling(spel);
            curablt.setAltr(16);
            if (curablt.structured()) {
                Lemma fil = curablt.getDghtr1();
                List<Lemma> abfils = internalInfl(Lemma.pack(fil),dial,trypfx,trysfx,record,bar);
                Lemma abfil = abfils.get(0);
                curablt.setDghtr1(abfil);
            }
            if (curablt.isFull()) {
                intInfl.add(curablt);
            }
        }
        return intInfl;
    }
    public static String restem = "";
    public static String varstem = "";
    public String attAdj(String mal, String acnt, String paff) {
        String rev = mal;
        restem = mal;
        try {
            char term = mal.charAt(mal.length()-1);
            char preterm = mal.charAt(mal.length()-2);
            if (Lingua.isConsonant(term) && preterm == term && !DictReader.isHead(mal) && !magicE) {
                rev = mal.substring(0,mal.length()-1);
                gem = true;
            }
            else if (mal.endsWith("ve") && !DictReader.isAvail(mal)) {
                int voc = mal.lastIndexOf("v");
                rev = Util.replaceAtIndex(mal,"f",voc,1);
                if (!DictReader.isRecog(rev,acnt,false) && rev.endsWith("e")) {
                    rev = rev.substring(0,rev.length()-1);
                }
                restem = rev;
            }
            else if ((term == 'r' && Lingua.isConsonant(preterm) || (mal.endsWith("ck")&& (paff.charAt(0)=='i' || paff.charAt(0)=='e' || paff.charAt(0) == 'y'))) && !DictReader.isAvail(mal)) {
                rev = mal.substring(0,mal.length()-1);
            }
            else if ((paff.equals("ably") || paff.equals("ibly")) && !DictReader.isAvail(mal)) {
                rev = mal+paff.substring(0,paff.length()-1)+"e";
            }
            else if ((mal.endsWith("ab") || mal.endsWith("ib")) && paff.equals("ly") && !DictReader.isAvail(mal)) {
                rev = mal+"le";
            }
            else if (mal.endsWith("al") && paff.equals("ly") && !DictReader.isAvail(mal)) {
                rev = mal.substring(0,mal.length()-2);
                varstem = rev;
            }
            else if (paff.equals("ist") && !DictReader.isAvail(mal)) {
                rev = mal+"y";
                delY = true;
            }
            else if (mal.endsWith("ie")) {
                rev = mal.substring(0,mal.length()-2)+"y";
            }
            else if (mal.endsWith("i") && endY) {
                rev = mal.substring(0,mal.length()-1)+"ie";
                endY = false;
            }
            else if (mal.endsWith("i")) {
                rev = mal.substring(0,mal.length()-1)+"y";
                if (!DictReader.isRecog(rev,acnt,false)) {                   //2/13/2017 for "tied"
                    rev = mal+"e";
                }
                endY = true;
            }
            else if (mal.endsWith("y")) {
                rev = mal.substring(0,mal.length()-1)+"ie";
            }
            else if (term == 'e' && !DictReader.isAvail(mal) && preterm != 'e') {
                rev = mal.substring(0,mal.length()-1);
                magicE = true;
            }
            else if ((term == 's' || Lingua.sonRank(term) > Lingua.sonRank('s')) && !DictReader.isAvail(mal) && (paff.equals("s~") || paff.equals("U"))) {
                rev = mal+"s";
            }
            else if ((Lingua.isConsonant(term) && Lingua.isVowel(preterm) || Lingua.isVowel(term)) && Lingua.isVowel(paff.charAt(0))) {     //final disjunct moved into initial conjunct on 6/9/2021 for "pledges"
                rev = mal+"e";
                magicE = true;
            }
            else if (Lingua.isConsonant(term) && !gem && Lingua.isVowel(paff.charAt(0))) {
                rev = mal+"e";
            }
            if (DictReader.isRecog(rev,acnt,false)) {// || dict.dictContains(rev)) {
                return rev;
            }
            else if (Lingua.isConsonant(term) && Lingua.isVowel(preterm)) {
                String dupl = Character.toString(term);
                rev = mal+dupl;
                if (DictReader.isRecog(rev,acnt,false) && paff.startsWith(dupl)) {
                    return rev;
                }
                else {
                    restem = mal;
                    return mal;
                }
            }
            else {
                restem = mal;
                return mal;
            }
        }
        catch (StringIndexOutOfBoundsException nihil) {
            //System.out.println(mal+"   "+rev);
            return mal;
        }
    }
    public static int sufLat (String test, char mode) {
        int wh = -1;
        for (int r = 0; r < (dict.latinate).size(); r++) {
            String latfix = "";
            if (mode == 'w') {
                latfix = ((dict.latinate).get(r)).getSpelling();
            }
            else {
                latfix = ((dict.latinate).get(r)).getPronUK();
            }
            if (test.endsWith(latfix)) {
                wh = r;
                break;
            }
        }
        return wh;
    }
    private static String latbase = "";
    public static String adjLat (String deriv) {
        String stem = deriv;
        int lt = sufLat(deriv, 'w');
        String latfix = "";
        if (lt >= 0) {
            latfix =((dict.latinate).get(lt)).getSpelling();
            String stmp = deriv.substring(0,deriv.length()-latfix.length());
            if (stmp.endsWith("at") && latfix.equals("ic")) {
                stmp = stmp.substring(0,stmp.length()-1)+"cy";
                if (!DictReader.isHead(stmp)) {
                    stmp = stmp.substring(0,stmp.length()-1)+"e";
                }
            }
            else if (stmp.endsWith("ac") && latfix.equals("y")) {
                stmp = stmp.substring(0,stmp.length()-1)+"te";
            }
            else if (stmp.endsWith("t") && latfix.equals("ic")) {
                if (!DictReader.isHead(stmp)) {
                    stmp = stmp.substring(0,stmp.length()-1)+"sis";
                }
            }
            else if (latfix.startsWith("i") && !latfix.equals("ion") && !latfix.equals("ity") || (stmp.endsWith("graph") || stmp.endsWith("log") || stmp.endsWith("nom")) && (latfix.equals("er") || latfix.equals("ous"))) {
                String back = stmp;
                stmp = stmp+"y";
                if (!DictReader.isHead(stmp) && latfix.equals("ist")) {
                    stmp = stmp.substring(0,stmp.length()-1)+"ism";
                }
                else if (!DictReader.isHead(stmp) && latfix.equals("ism")) {
                    stmp = stmp.substring(0,stmp.length()-1)+"ist";
                    if (!DictReader.isUnder(deriv,stmp)) {                                    //2/13/2017 for "excorcism"
                       stmp = stmp.substring(0,stmp.length()-3)+"ise";
                    }
                }
                if (!DictReader.isHead(stmp)) {
                    stmp = back;
                }
            }
            else if ((stmp.endsWith("ant") || stmp.endsWith("ent")) && (latfix.equals("ance") || latfix.equals("ence") || latfix.equals("ancy") || latfix.equals("ency"))) {
                stmp = stmp.substring(0,stmp.length()-1)+latfix.substring(latfix.length()-2);
            }
            else if ((stmp.endsWith("ance") || stmp.endsWith("ence") || stmp.endsWith("ancy") || stmp.endsWith("ency")) && (latfix.equals("ant") || latfix.equals("ent"))) {
                stmp = stmp.substring(0,stmp.length()-2)+"t";
            }
            else if ((stmp.endsWith("t") || stmp.endsWith("s") || stmp.endsWith("v") || stmp.endsWith("l") || stmp.endsWith("r")) && (latfix.equals("ory") || latfix.equals("ion") || latfix.equals("al") || latfix.equals("ity"))) {
                stmp += "e";
                if (stmp.endsWith("ate") && !DictReader.isHead(stmp)) {
                    stmp = stmp.substring(0,stmp.length()-3);
                    if (!DictReader.isHead(stmp)) {
                        stmp += "e";
                    }
                }    
            }
            else if (latfix.equals("y")) {
                stmp = stmp+"ist";
            }
            stem = stmp;
            if (!DictReader.isHead(stem)) {
                for (int w = 0; w < (dict.latinate).size(); w++) {
                    String stmp1 = stem+((dict.latinate).get(w)).getSpelling();
                    String stmp2 = deriv+((dict.latinate).get(w)).getSpelling();
                    if (DictReader.isHead(stmp1)) {
                        stem = stmp1;
                        break;
                    }
                    else if (DictReader.isHead(stmp2)) {
                        stem = stmp2;
                        break;
                    }
                }
            }
        }
        return stem;
    }
    public static String rootAdj (String wt, String rtspk, Lemma tag, int rtalt, Dialect dil) {
        String rt = rtspk;
        char bord1 = rt.charAt(rt.length()-1);
        char bord2 = (tag.getPronUK()).charAt(0);
        String adjrt = rt;
        if (rtalt == 3 && tag.getAlter() == 1) {
            adjrt = Lingua.swapVoiceEnd(rt);
        }
        else if (rtalt == 12 && tag.getAlter() == 12) {
            adjrt = rt.replace("t͡ʃaɪld","t͡ʃɪldɹ");
        }
        else if ((tag.getAlter() == 7 || tag.getAlter() == 5) && (rt.endsWith("ɪ.kəl") && wt.endsWith("ical") || rt.endsWith("əl") && wt.endsWith("le"))) {
            adjrt = rt.substring(0,rt.length()-2);
        }
        else if (tag.getAlter() == 5 && rtalt == 15 && rt.endsWith("iː") && (wt.endsWith("ary") || wt.endsWith("ory")) && dil.getClip()) {
            adjrt = rt.substring(0,rt.length()-2)+"ə";
        }
        else if (tag.getAlter() == 5 && rt.endsWith("iː")) {
            adjrt = rt.substring(0,rt.length()-2)+"ɪ";
        }
        else if (tag.getAlter() == 4 && rt.endsWith("ŋ")) {
            adjrt = rt+"ɡ";
        } 
        else if (Lingua.isConsonant(bord2) && bord2 == bord1) {
            adjrt = rt.substring(0,rt.length()-1);
        }
        else if (delY) {
            adjrt = rt.substring(0,rt.length()-2);
        }
        return adjrt;
    }
    public Lemma affAdj(String rt, char found, Lemma tag) {
        Lemma raff = new Lemma();
        String fix = tag.getPronUK();
        String ret;
        if (fix.length() > 0 && Lingua.select(found,tag)) {
            char fin = rt.charAt(rt.length()-1);
            char affirst = (Util.removeAll(fix,"~")).charAt(0);
            if (Lingua.isConsonant(rt.charAt(rt.length()-1)) && fix.equals("~ɹ")) {
                ret = "~ə˞";
            }
            else if ((Lingua.samePlaceMan(fin,affirst) || (tag.getAlter() == 2 && Lingua.sonRank(fin) < 3))) {
                if (tag.getAlter() == 1 && rt.endsWith("s") && fix.equals("z~") && !orgform.endsWith("es~")) {
                    ret = "~";
                } 
                else if (fix.endsWith("~")) {
                    ret = Util.insertAtIndex(fix,"ɪ",fix.length()-2);
                }
                else {
                    ret = Util.insertAtIndex(fix,"ɪ",fix.length()-1);
                }
                if (!Lingua.isVoiced(affirst)) {
                    ret = Lingua.swapVoiceEnd(fix);
                }
            }
            else if (tag.getAlter() == 11 && Lingua.sonRank(fin) == 1) {
                ret = Util.insertAtIndex(fix,"ə",1);
            }   
            else if (tag.getAlter() == 1 && Lingua.voiceClash(fin,affirst)) {
                ret = Lingua.swapVoiceEnd(fix);
            }
            else {
                ret = fix;
            }
        }
        else {
            ret = "";
        }
        raff.morphParms(tag.getSpelling(), ret, tag.getUsage(), tag.getSel(), tag.getAlter());
        return raff;
    }
    private static boolean rad = false;
    public String[] indivConcat (String orgwrt, String ecrire, Lemma add, String acrt, Dialect act, String cfix, char cat, char prsf, int altr, boolean syncopate) {
        String orth = ecrire;
        String root = acrt;
        String bfix = cfix;
        Lemma afix = add;
        char classif = '0';
        String phon = "|||";
        boolean change = false;
        int fixaltr = afix.getAlter();
        String cutroot = root;
        boolean cut = false;
        String fulform = "";
        String allort1 = "";
        String allort2 = "";
        if (!(afix.getPronUK()).equals("|||") && !rad && prsf == 's') {
            if (fixaltr >= 3 || altr >= 3 || delY) {
                String backroot = root;
                root = rootAdj(orth,acrt,add,altr,act);
                if (!root.equals(backroot)) {
                    change = true;
                }
                rad = true;
            }
            afix = affAdj(root,cat,afix);
            boolean restressed = false;
            bfix = afix.getPronUK();
            int trk = 3;
            String bfix2 = Util.removeAll(bfix,".");
            String nbroot = Util.removeAll(root,".");
            if (Lingua.isVowel(bfix2.charAt(0)) && root.endsWith("əl") && Lingua.isConsonant(root.charAt(root.lastIndexOf("ə")-1)) && (orth.endsWith("le") || orth.contains("le_"))) {// && !Lingua.isAlvStop(root.charAt(root.lastIndexOf("ə")-1))) {
                root = Util.removeAtIndex(root, root.lastIndexOf("ə"));
                change = true;
            }
            else if (root.endsWith("ə") && Lingua.isStop(root.charAt(root.length()-2)) && !Lingua.isAlvStop(root.charAt(root.length()-2)) && bfix.equals("liː")) {
                root = root.substring(0,root.length()-1);
                change = true;
            }
            else if (root.endsWith("ən") && orth.endsWith("en") && (Lingua.isCorSib(nbroot.charAt(nbroot.length()-3)) || Lingua.isAlvStop(nbroot.charAt(nbroot.length()-3))) && Lingua.isVowel(bfix2.charAt(0)) && cat == 'V') { 
                root = Util.removeAtIndex(root,root.lastIndexOf("ə"));
                nbroot = Util.removeAtIndex(nbroot,nbroot.length()-2);
                String ptons = nbroot.substring(nbroot.length()-2);
                if (!Phonology.validOnset(ptons)) {
                    root = Util.insertAtIndex(root,".",root.length()-1);
                    trk += 1;
                }
                if (root.length() >= trk) {
                    char back2 = root.charAt(root.length()-trk);
                    char back1 = root.charAt(root.length()-trk+1);
                    if (Lingua.sonRank(back1) < 3 && Lingua.isBound(back2)) {
                        root = Util.removeAtIndex(root,root.length()-trk);
                    }
                }
            }    
            else if ((root.endsWith("ɹ") || root.endsWith("r")) && act.equals("RP") && !Lingua.isVowel((Util.removeAll(bfix,".")).charAt(0))) { 
                root = root.substring(0,root.length()-1);
            }
            else if (root.endsWith("ə.ɹɪ") && orth.endsWith("ary") && (afix.getSpelling()).equals("ly") && bfix.equals(".liː")) {
                String testclip = Phonology.clip(root);
                boolean clippable = !testclip.equals(root);
                if (act.getClip() && !clippable) {
                    root = root.substring(0,root.length()-4)+"ɛ.ɹə";
                    root = root.replaceAll("ˈ","ˌ");
                    for (int sr = root.lastIndexOf("ɛ"); sr >= 0; sr--) {
                        char srch = root.charAt(sr);
                        if (srch == '.') {
                            root = Util.replaceAtIndex(root,"ˈ",sr,1);
                            break;
                        }
                    }
                    restressed = true;
                }
                else if (act.equals("RP")){
                    root = testclip.substring(0,testclip.length()-1)+"ə";
                }
            }
            else if (root.endsWith("ə.ɹɪ") && orth.endsWith("ory") && (afix.getSpelling()).equals("ly") && bfix.equals(".liː")) {
                String testclip = Phonology.clip(root);
                boolean clippable = !testclip.equals(root);
                if (act.getClip() && !clippable) {
                    root = root.substring(0,root.length()-4)+"ɔːɹ.ə";
                    root = root.replaceAll("ˈ","ˌ");
                    for (int sr = root.lastIndexOf("ɔː.ɹ"); sr >= 0; sr--) {
                        char srch = root.charAt(sr);
                        if (srch == '.') {
                            root = Util.replaceAtIndex(root,"ˈ",sr,1);
                            break;
                        }
                    }
                    restressed = true;
                }
                else if (act.equals("RP")){
                    root = testclip.substring(0,testclip.length()-1)+"ə";
                }
            }
            else if (root.endsWith("iː") && orth.endsWith("y") && (afix.getSpelling()).equals("ly") && bfix.equals(".liː")) {
                root = root.substring(0,root.length()-2)+"ɪ";
                change = true;
            }
            else if (root.endsWith("l") && bfix.startsWith(".l")) {
                root = root.substring(0,root.length()-1);
            }
            String tstfix = Util.removeAll(bfix,".");
            char testend = root.charAt(root.length()-1);
            String tstons = Character.toString(testend)+Character.toString(tstfix.charAt(0));
            if (testend != '˞' && Phonology.validOnset(tstons)) {
                bfix = tstfix;
            }
            classif = afix.getUsage();
            cutroot = Phonology.syncope(orth,root,act);
            //cut = syncopate && !Phonology.compareSync(root,cutroot);
            fulform = root+bfix;
            phon = cutroot+bfix;
            int ronum = Lingua.countSyl(root);
            int phonum = Lingua.countSyl(Phonology.maxOnset(phon));
            if (ronum == 1 && phonum >= 2 && !root.contains("ˈ")) {
                phon = "ˈ"+phon;
            }
            else if (ronum < phonum && phonum > 4 && !restressed) {
                phon = Phonology.reStress(phon);
            }
            phon = (phon.replaceAll("ʃtliː","ʃɪdliː")).replaceAll("d͡ʒdliː","d͡ʒɪdliː");
            phon = Phonology.antiClash(Phonology.maxOnset(phon));
            if (cut) {
                ronum = Lingua.countSyl(root);
                phonum = Lingua.countSyl(Phonology.maxOnset(fulform));
                if (ronum == 1 && phonum >= 2 && !root.contains("ˈ")) {
                    fulform = "ˈ"+fulform;
                }
                else if (ronum < phonum && phonum > 4 && !restressed) {
                    fulform = Phonology.reStress(fulform);
                }
                fulform = (fulform.replaceAll("ʃtliː","ʃɪdliː")).replaceAll("d͡ʒdliː","d͡ʒɪdliː");
                fulform = Phonology.antiClash(Phonology.maxOnset(fulform));
            }
        }
        else if (!rad && prsf == 's') {
            cutroot = Phonology.syncope(orth,root,act);
            //cut = syncopate && !Phonology.compareSync(root,cutroot);
            phon = Phonology.antiClash(Phonology.maxOnset(cutroot));
            fulform = Phonology.antiClash(Phonology.maxOnset(root));    //removed cut=true condition on 7/22/2019
            classif = cat;
        }
        else if (prsf == 'p') {
            classif = cat;
            if (!root.contains("ˈ")) {
                root = "ˈ"+root;
            }
            if (bfix.startsWith("ˌ") && (root.startsWith("ˈ") || root.startsWith("ˌ")) && Lingua.countSyl(bfix) <= 1) {
                bfix = Util.removeAll(bfix.substring(1),".");
            }
            else if (bfix.startsWith("ˈ") && root.contains("ˈ") && !root.startsWith("ˈ")) {
                bfix = "ˌ"+bfix.substring(1);
            }
            else if (bfix.startsWith("ˈ") && root.startsWith("ˈ") && Lingua.countSyl(root) <= 2) {
                root = root.substring(1);
            }
            cutroot = Phonology.syncope(orth,root,act);
            phon = Phonology.antiClash(Phonology.maxOnset(bfix+cutroot));
            //cut = syncopate && !Phonology.compareSync(root,cutroot);
            fulform = Phonology.antiClash(Phonology.maxOnset(bfix+root));   //removed cut=true condition on 7/22/2019
        }
        String sfx = bfix;
        if (!act.getRhotic()) {
            phon = Phonology.deRhot(phon,true);
            sfx = Phonology.deRhot(bfix,true);
            fulform = Phonology.deRhot(fulform,true);       //removed cut=true condition on 7/19/2019
        }
        if (prsf == 's') {
            allort1 = phon.substring(0,phon.lastIndexOf(Phonology.shave(sfx)));
            allort2 = fulform.substring(0,fulform.lastIndexOf(Phonology.shave(sfx)));
        }
        else if (prsf == 'p') {
            allort1 = phon.substring(bfix.length());
            allort2 = fulform.substring(bfix.length());
        }
        return new String[]{orth,phon,Character.toString(classif),fulform,allort1,allort2,bfix};
    }
    public Lemma concat (String orgwrt, Lemma princ, Lemma add, Dialect act, char prsf) {
        String orth = princ.getSpelling();
        String root = princ.getPronUK();
        String alrt = princ.getPronUS();
        //Lemma restore = princ;
        char classif;
        String[] collect1 = indivConcat(orgwrt,orth,add,root,ConvGUI.uk,add.getPronUK(),princ.getUsage(),prsf,princ.getAlter(),act.getSyncope());
        rad = false;
        String[] collect2 = indivConcat(orgwrt,orth,add,alrt,ConvGUI.us,add.getPronUK(),princ.getUsage(),prsf,princ.getAlter(),act.getSyncope());
        rad = false;
        String orthog = collect1[0];
        String phon1 = collect1[1];
        String phon2 = collect2[1];
        classif = collect2[2].charAt(0);
        Lemma ret = new Lemma();
        if (add.getUsage() == '̠') {
            classif = princ.getUsage();
        }
        ret.morphParms(orthog,phon1,phon2,classif,princ.getAlter());
        int reconf = findMorph(orth);
        if (dict.dictContains(orth) || Util.listContains(orth,ablauted) || Util.listContains(orth,TextParser.customs)) {//reconf < dictionary.size() || ((dict.homographs).containsKey(orth))) {
            dict.initLex(false);
            dictionary = dict.getLex();
        }
        if (act.getSyncope()) {   
            princ.setAllomorph1(collect1[4]);
            princ.setAllomorph2(collect2[4]);
        }
        else {
            princ.setAllomorph1(collect1[5]);
            princ.setAllomorph2(collect2[5]);
        }
        add.setAllomorph1(collect1[6]);
        add.setAllomorph2(collect1[6]);
        Lemma struct = Lemma.structure(ret,princ,add);
        /*if (princ.syncopated()) {
            String rtnsp = princ.getUnsyncUK();
            String alnsp = princ.getUnsyncUS();
            String[] collect3 = indivConcat(orgwrt,orth,add,rtnsp,"RP",add.getPronUK(),princ.getUsage(),prsf,princ.getAlter(),syncopate);
            rad = false;
            String[] collect4 = indivConcat(orgwrt,orth,add,alnsp,"GA",add.getPronUK(),princ.getUsage(),prsf,princ.getAlter(),syncopate);
            rad = false;
            String phon3 = collect3[1];
            String phon4 = collect4[1];
            struct.setUnsyncUK(phon3);
            struct.setUnsyncUS(phon4);
        }*/
        if (collect1[3].length() > 1) {
            struct.setUnsyncUK(collect1[3]);
        }
        if (collect2[3].length() > 1) {
            struct.setUnsyncUS(collect2[3]);
        }
        String infl = princ.getAllo();
        String fxsp = add.getSpelling();
        String spell;
        //System.out.println(restem);
        if (Lemma.isSuffix(add)) {
            if (!infl.equals("|||") && princ.getAlter() > 0  && infl.endsWith(fxsp)) {
                spell = infl;
            }
            else if (!restem.equals("")) {
                spell = restem+fxsp;
            }
            else {
                spell = orth+fxsp;
            }
        }
        else {
            spell = fxsp+orth;
        }
        struct.setSpelling(spell);
        return struct;
    }
    private static int limit = 0;
    private static int reclim = 0;
    private static int trials = 10;
    private static boolean lat = false;
    public static boolean dotted = false;
    private static boolean export = false;
    private static List<String> tried = new ArrayList<>();
    public static boolean isRecog (String prob) {
        return (Morphology.dict).dictContains(prob) || Util.listContains(prob,ablauted) || Util.listContains(prob,TextParser.customs) || DictReader.isWord(prob,"RP",false) || DictReader.isWord(prob,"GA",false);
    }
    public static List<Lemma> combList (List<Lemma> list1, List<Lemma> list2) {
        List<Lemma> comb = list1;
        for (int t = 0; t < list2.size(); t++) {
            comb.add(list2.get(t));
        }
        return comb;
    }
    public static List<Lemma> fromLexicon (List<Lemma> unref, Dialect dial) {
        List<Lemma> refined = new ArrayList<>();
        for (int qi = 0; qi < unref.size(); qi++) {
            Lemma base = unref.get(qi);
            boolean abbrev = (base.getPronUK()).contains("`") && (base.getPronUS()).contains("`");
            if ((base.getUnsyncUK()).equals("|||")) {
                base.setUnsyncUK(base.getPronUK());
            }
            if ((base.getUnsyncUS()).equals("|||")) {
                base.setUnsyncUS(base.getPronUS());
            }
            if (!abbrev) {
                base.setPronUK(Phonology.syncope(base.getSpelling(),base.getUnsyncUK(),ConvGUI.uk));
                base.setPronUS(Phonology.syncope(base.getSpelling(),base.getUnsyncUS(),ConvGUI.us));
            }
            if (base.getAlter() == 8 && ((base.getPronUS()).equals("|||") || (base.getPronUS()).equals(base.getPronUK()))) {
                base.setPronUS((base.getPronUK()).replaceAll("ɒ","ʌ"));
            }
            else if (base.getAlter() == 9 && ((base.getPronUS()).equals("|||") || (base.getPronUS()).equals(base.getPronUK()))) {
                base.setPronUS((base.getPronUK()).replaceAll("iː","ɪ"));
            }
            else if (base.getAlter() == 10 && ((base.getPronUS()).equals("|||") || (base.getPronUS()).equals(base.getPronUK()))) {
                base.setPronUS((base.getPronUK()).replaceAll("ɑː","æ"));
            }
            else if (base.getAlter() == 6 && ((base.getPronUS()).equals("|||") || (base.getPronUS()).equals(base.getPronUK()))) {       //6 became 12 on 7/14/2019
                base.setPronUS((base.getPronUK()).replaceAll("ɪə˞","ə˞"));
            }
            else if ((dial.getName()).equals("RP") && base.getAlter() == 13 && ((base.getPronUS()).equals("|||") || (base.getPronUS()).equals(base.getPronUK()))) {
                base.setPronUS(base.getPronUK());
                base.setPronUK((base.getPronUK()).replaceAll("jə","ə"));
            }
            else if (base.getAlter() == 14 && ((base.getPronUS()).equals("|||") || (base.getPronUS()).equals(base.getPronUK()))) {
                base.setPronUS((base.getPronUK()).replaceAll("ɛ","ɪ"));
            }
            else if ((base.getPronUS()).equals("|||")) {
                base.setPronUS(base.getPronUK());
            }
            if ((base.getPronUS()).equals(base.getPronUK()) || (base.getPronUS()).equals("|||")) {
                String ussnd = base.getPronUK();
                ussnd = (ussnd.replaceAll("ɛə˞ˈ(ɹ)","ɛˈɹ")).replaceAll("ɛə˞ˌ(ɹ)","ɛˌɹ");
                ussnd = (ussnd.replaceAll("ʌˈɹ","ɜ˞ˈ(ɹ)")).replaceAll("ʌˌɹ","ɜ˞ˌ(ɹ)");
                ussnd = (ussnd.replaceAll("æˈɹ","ɛˈɹ")).replaceAll("æˌɹ","ɛˌɹ");
                ussnd = ussnd.replaceAll("ɛə˞.(ɹ)","ɛ.ɹ");
                ussnd = ussnd.replaceAll("ʌ.ɹ","ɜ˞.(ɹ)");
                ussnd = ussnd.replaceAll("æ.ɹ","ɛ.ɹ");
                ussnd = ussnd.replaceAll("ɔː", "ɑː");
                ussnd = ussnd.replaceAll("ɛə˞","ɛɹ");
                ussnd = ussnd.replaceAll("ʌɹ","ɜ˞");
                ussnd = ussnd.replaceAll("æɹ","ɛɹ");
                ussnd = ussnd.replaceAll("ɒ", "ɑː");
                base.setPronUS(ussnd);
            }
            base.setUnsyncUS((base.getUnsyncUS()).replaceAll("əʊ","oʊ"));
            base.setUnsyncUK((base.getUnsyncUK()).replaceAll("oʊ","əʊ"));
            base.setUnsyncUS(Phonology.rhotVow(base.getUnsyncUS()));
            if (!abbrev) {
                base.setPronUK(Phonology.deRhot(Phonology.syncope(base.getSpelling(),base.getUnsyncUK(),ConvGUI.uk),true));
                base.setPronUS(Phonology.dblRhot(Phonology.syncope(base.getPronUS(),base.getUnsyncUS(),ConvGUI.us)));
            }
            else {
                base.setPronUK(Phonology.deRhot(base.getPronUK(),true));
                base.setPronUS(Phonology.dblRhot(base.getPronUS()));
            }
            refined.add(base);
        }
        return refined;
    }
    public static boolean treed = false;
    public static String classify (Dialect odd) {
        boolean[] parmsUK = (ConvGUI.uk).getParms();
        boolean[] parmsUS = (ConvGUI.us).getParms();
        boolean[] parmsHB = (ConvGUI.hb).getParms();
        boolean[] parmsOdd = odd.getParms();
        int scoreUS = 0;
        int scoreUK = 0;
        int scoreHB = 0;
        for (int q = 0; q < parmsOdd.length; q++) {
            if (parmsOdd[q] == parmsUK[q]) {
                scoreUK += 1;
            }
            if (parmsOdd[q] == parmsUS[q]) {
                scoreUS += 1;
            }
            if (parmsOdd[q] == parmsHB[q]) {
                scoreHB += 1;
            }
        }
        if (scoreUS > scoreUK) {
            if (scoreUS > scoreHB) {
                return "GA";
            }
            else {
                return "HB";
            }
        }
        else {
            if (scoreUK > scoreHB) {
                return "RP";
            }
            else {
                return "HB";
            }
        }
    }
    public String[] findLex (String query, String suff) {
        String stem0 = query;
        String dialect = "RP";
        String[] trov = new String[3];
        List<Lemma> reference = dictionary;
        boolean isBrit = DictReader.isWord(stem0,"RP",false);
        boolean isAmer = DictReader.isWord(stem0,"GA",false);
        boolean isLex = isBrit || isAmer || findMorph(stem0) < reference.size();
        if (!isLex && !suff.equals("---")) {
            String stem1 = attAdj(stem0,"RP",suff);
            String stem2 = attAdj(stem0,"GA",suff);
            isBrit = !stem1.equals(stem0) && DictReader.isWord(stem1,"RP",false);
            isAmer = !stem2.equals(stem0) && DictReader.isWord(stem2, "GA",false);
            isLex = isBrit || isAmer || findMorph(stem0) < reference.size();
            if (isAmer && !isBrit) {
                stem0 = stem2;
            }
            else if (isBrit) {
                stem0 = stem1;
            }
        }
        if (!isBrit && isAmer) {
            dialect = "GA";
        }
        if (isLex) {
            trov[0] = stem0;
            trov[1] = dialect;
            trov[2] = "TRUE";
        }
        else {
            trov[0] = "---";
            trov[1] = "HB";
            trov[2] = "FALSE";
        }
        return trov;
    }
    public static String addStress (String constr) {
        String nwstr = constr;
        if (constr.contains("ˈ")) {
            nwstr = Phonology.antiClash("ˌ"+nwstr);
        }
        else {
            nwstr = "ˈ"+nwstr;
        }
        return nwstr;
    }
    public Dialect findVar (String id) {
        Dialect variety = ConvGUI.hb;
        ListIterator<Dialect> varit = (ConvGUI.vars).listIterator();
        while (varit.hasNext()) {
            Dialect vrt = varit.next();
            if ((vrt.getName()).equals(id)) {
                variety = vrt;
                break;
            }
        }
        return variety;
    }
    //public static List<Lemma> attempts = new ArrayList<>();
    public List<Lemma> findHead (String tradit, Lemma cmpsn, Dialect acnt, boolean rej, boolean blank) {
        List<Lemma> compounds = new ArrayList<>();
        List<Lemma> spares = new ArrayList<>();
        String tail = tradit.toLowerCase();
        String restore = orgform;
        int bndry = tail.length()-2;
        boolean comp = false;
        boolean free = !cmpsn.isFull();
        while (bndry >= 2 && !comp) {
            String nonhead = tail.substring(0,bndry);
            String head = tail.substring(bndry);
            String[] finds1 = findLex(nonhead,"---");
            String[] finds2 = findLex(head,"---");
            String regvar1 = finds1[1];
            String regvar2 = finds2[1];
            if (regvar1.equals(regvar2) && finds1[2].equals(finds2[2]) && finds2[2].equals("TRUE")) {
                //System.out.println("Testing..."+cmpsn.printParms());
                List<Lemma> nonheads = Lemma.pack(nonhead);
                List<Lemma> heads = Lemma.pack(head);
                initialize();
                orgform = nonhead;
                nonheads = parseLemma(nonhead,nonheads,findVar(finds1[1]),true,true,false,rej);
                ListIterator<Lemma> nhdit = nonheads.listIterator();
                initialize();
                orgform = head;
                heads = parseLemma(head,heads,findVar(finds2[1]),true,true,false,rej);
                List<String> possibilities = new ArrayList<>();
                while (nhdit.hasNext()) {
                    Lemma noncap = nhdit.next();
                    String nonhd = Lemma.decReg(noncap,acnt);
                    ListIterator<Lemma> hdsitr = heads.listIterator();
                    boolean passed = false;
                    while (hdsitr.hasNext()) {
                        Lemma caput = hdsitr.next();
                        //System.out.println(caput.printParms());
                        String hd = Lemma.decReg(caput,acnt);
                        String combo = nonhd+hd;
                        combo = Util.removeAll(Util.removeAll(Util.removeAll(combo,"."),"ˈ"),"ˌ");
                        possibilities.add(combo);
                    }
                }
                String snds = Lemma.decReg(cmpsn,acnt);
                String pro = Util.removeAll(Util.removeAll(Util.removeAll(snds,"."),"ˈ"),"ˌ");
                pro = (pro.replaceAll("nɡ","ŋɡ")).replaceAll("nk","ŋk"); 
                for (int l = 0; l < possibilities.size(); l++) {
                    String cpr = possibilities.get(l);
                    cpr = (cpr.replaceAll("nɡ","ŋɡ")).replaceAll("nk","ŋk");
                    if (cpr.equals(pro) || blank) {
                        char klas = cmpsn.getUsage();
                        for (int e = 0; e < heads.size(); e++) {
                            char clas = (heads.get(e)).getUsage();
                            Lemma hdmr = heads.get(e);
                            int anch = snds.length()-1;
                            if (!blank) {
                                anch = snds.lastIndexOf(Lemma.decReg(hdmr,acnt));
                            }
                            boolean nom = klas == 'M' && clas == 'N';
                            try {
                                String extr = snds.substring(anch);
                                if ((klas == clas || nom || blank) && snds.charAt(anch-1) != 'ˈ' && !extr.contains("ˈ")) {
                                    /*double indx = l/heads.size();
                                    int flrd = (int)Math.floor(indx);*/
                                    List<Lemma> mods = new ArrayList<>();
                                    String candid = Phonology.shave(snds.substring(0,anch));
                                    List<Lemma> backnhd = nonheads;
                                    List<Lemma> backmod = new ArrayList<>();
                                    for (int f = 0; f < nonheads.size(); f++) {
                                        Lemma it = nonheads.get(f);
                                        String itpro = Phonology.shave(Lemma.decReg(it,acnt));
                                        if (itpro.equals(candid) && it.getUsage() == clas) {
                                            mods.add(it);
                                            nonheads.remove(f);
                                            f--;
                                        }
                                    }
                                    if (mods.isEmpty()) {
                                        for (int g = 0; g < nonheads.size(); g++) {
                                            Lemma it = nonheads.get(g);
                                            String itpro = Phonology.shave(Lemma.decReg(it,acnt));
                                            if (itpro.equals(candid)) {
                                                mods.add(it);
                                                nonheads.remove(g);
                                                g--;
                                            }
                                        }
                                    }
                                    for (int indx = 0; indx < mods.size(); indx++) {
                                        Lemma pair;
                                        Lemma mod = mods.get(indx);
                                        if (free) {
                                            String cmpUK = addStress(Phonology.maxOnset(mod.getPronUK()+hdmr.getPronUK()));
                                            String cmpUS = addStress(Phonology.maxOnset(mod.getPronUS()+hdmr.getPronUS()));
                                            Lemma gen = new Lemma(cmpsn.getSpelling(),cmpUK,cmpUS,hdmr.getUsage(),hdmr.getAlter());
                                            pair = Lemma.structure(gen,hdmr,mod);
                                        }
                                        else {
                                            pair = Lemma.structure(cmpsn,hdmr,mod);
                                        }
                                        String hdlo = hdmr.getAllo();
                                        if (!hdlo.equals("|||")) {
                                            String modallo = mod.getSpelling()+hdlo;
                                            pair.setAllo(modallo);
                                        }
                                        compounds.add(pair);
                                        comp = true;
                                    }
                                    heads.remove(e);
                                    e--;
                                }
                            }
                            catch (StringIndexOutOfBoundsException altern) {
                                break;
                            }
                        }
                    }
                }
                if (!compounds.isEmpty()) {
                    spares.addAll(heads);
                    spares.addAll(nonheads);
                    /*dict.store(heads);
                    dict.store(nonheads);
                    if (export && !natLex) {
                        exports.addAll(heads);
                        exports.addAll(nonheads);
                    }*/
                }
            }
            bndry--;
        }
        if (compounds.isEmpty()) {
            compounds.add(cmpsn);
        }
        else {
            dict.store(spares);
            if (export && !natLex) {
                //exports.addAll(spares);
            }
        }
        orgform = restore;
        return compounds;
    }
    public List<Lemma> parseStem (String orgwrt, Lemma cmpsn, String aff, List<Lemma> stems, Dialect acnt, char prsf, boolean rej, boolean syncopate) {
        List<Lemma> poly = new ArrayList<>();
        for (int mpt = 0; mpt < stems.size(); mpt++) {
            Lemma raiz = stems.get(mpt);
            Lemma add = findFix(raiz,aff,prsf);
            boolean compat = add.getUsage() == '̠' || add.getUsage() == cmpsn.getUsage() || add.getUsage() == 'S' && raiz.getUsage() == 'V' || add.getUsage() == 'S' && raiz.getUsage() == 'A' || add.getUsage() == 'S' && raiz.getUsage() == 'N';
            if (add.isFull() && compat) {
                Lemma combo = concat(orgwrt,raiz,add,acnt,prsf);
                String combpro = Lemma.decReg(combo,acnt);
                String orgcomb = Lemma.decReg(cmpsn,acnt);
                combpro = Util.removeAll(Util.removeAll(Util.removeAll(combpro,"ˈ"),"ˌ"),".");
                orgcomb = Util.removeAll(Util.removeAll(Util.removeAll(orgcomb,"ˈ"),"ˌ"),".");
                if (combpro.equals(orgcomb)) {
                    boolean tmpms = DictReader.missing;
                    List<Lemma> rtstr = findStem(raiz.getSpelling(),raiz,acnt,rej);
                    DictReader.missing = tmpms;
                    for (int dpt = 0; dpt < rtstr.size(); dpt++) {
                        Lemma struct = Lemma.structure(cmpsn,rtstr.get(dpt),add);
                        poly.add(struct);
                    }
                }
            }
        }
        return poly;
    }
    public List<Lemma> findStem (String tradit, Lemma cmpsn, Dialect acnt, boolean rej) {
        List<Lemma> poly = new ArrayList<>();
        String entire = tradit.toLowerCase();
        int bndry = findMB(entire,'s');
        int prebnd = findMB(entire,'p');
        if (prebnd < entire.length() && prebnd > 0) {
            String pref = entire.substring(0,prebnd);
            String stem = Util.removeAll(entire.substring(prebnd),"+");
            List<Lemma> trials = Lemma.pack(attAdj(stem,classify(acnt),pref));
            initialize();
            try {
                List<Lemma> stems = parseLemma(tradit,trials,acnt,true,true,false,rej);
                initialize();
                if (Lemma.isRoot(stems)) {
                    List<Lemma> trees = parseStem(tradit,cmpsn,pref,stems,acnt,'p',rej,acnt.getSyncope());
                    poly.addAll(trees);
                }
            }
            catch (IndexOutOfBoundsException non) {
                return Lemma.pack(cmpsn);
            }
        }
        if (poly.isEmpty() && bndry > 0) {
            String stem = Util.removeAll(entire.substring(0,bndry),"+");
            String suff = entire.substring(bndry);
            List<Lemma> trials = Lemma.pack(attAdj(stem,classify(acnt),suff));
            initialize();
            List<Lemma> stems = parseLemma(tradit,trials,acnt,true,true,false,rej);
            initialize();
            if (Lemma.isRoot(stems)) {
                List<Lemma> trees = parseStem(tradit,cmpsn,suff,stems,acnt,'s',rej,acnt.getSyncope());
                poly.addAll(trees);
            }
        }
        if (poly.isEmpty()){
            poly = findHead(tradit,cmpsn,acnt,rej,false);
        }
        return poly;
    }
    public static List<Lemma> extrHeads (List<Lemma> pairs) {
        List<Lemma> extractions = new ArrayList<>();
        for (int tr = 0; tr < pairs.size(); tr++) {
            Lemma thygat = (pairs.get(tr)).getDghtr1();
            if (thygat != null) {
                extractions.add((pairs.get(tr)).getDghtr1());
            }
        }
        return extractions;
    }
    public List<Lemma> findCompounds (String log, List<Lemma> wholes, Dialect dial, boolean rej, boolean blank) {
        List<Lemma> compounds = new ArrayList<>();
        ListIterator<Lemma> itrwhl = wholes.listIterator();
        while (itrwhl.hasNext()) {
            Lemma questo = itrwhl.next();
            if (questo.getDghtr1() == null) {
                List<Lemma> adds = findHead(log,questo,dial,rej,blank);
                ListIterator<Lemma> addit = adds.listIterator();
                while (addit.hasNext()) {
                    //System.out.println((addit.next()).printParms());
                    compounds.add(addit.next());
                }
            }
            else if (questo.isFull()) {
                compounds.add(questo);
            }
        }
        if (compounds.isEmpty()) {
            return wholes;
        }
        else {
            return compounds;
        }
    }
    public static List<Lemma> getProns (String log, String orig, Dialect dialect, boolean block, boolean syncopate) {
        List<Lemma> prons = Lemma.extract(log,DictReader.lookup(log,orig),dialect,block,syncopate);
        //System.out.println(Lemma.multiPrint(prons));
        return Lemma.trimBlank(prons);
    }
    public boolean hscp (String pscp) {
        if (pscp.endsWith("houses") && pscp.length() > 6 || pscp.endsWith("house") && pscp.length() > 5) {
            return true;
        }
        else {
            return false;
        }
    }
    public List<Lemma> parseLemma (String org, List<Lemma> input, Dialect dial, boolean trypfx, boolean trysfx, boolean record, boolean bar) {
        ListIterator<Lemma> through = input.listIterator();
        List<Lemma> bases = new ArrayList<>();
        while (through.hasNext()) {
            Lemma base = through.next();
            String ortho = base.getSpelling();
            if (ortho.equals("") || ortho.contains("+") || tried.contains(ortho)) {
                break;
            }
            List<Lemma> provis = dict.findWord(ortho);
            List<Lemma> added = Util.append(ablauted,TextParser.customs);
            List<Lemma> extra = Util.findInList(ortho,added);
            if (!extra.isEmpty() && !lat) {
                //System.out.println("Testing...2: "+ortho);
                Convert17.report = "Found "+(TextParser.cleanUp(ortho,false,true)).toUpperCase()+" among new dictionary entries.";
                bases = fromLexicon(extra,dial);
            }
            else if (!provis.isEmpty() && !lat) {
                //System.out.println("Testing...3: "+ortho);
                Convert17.report = "Found "+(TextParser.cleanUp(ortho,false,true)).toUpperCase()+" in native dictionary.";
                bases = provis;
                bases = fromLexicon(bases,dial);
                if (treed && !Lemma.structured(bases)) {
                    bases = findCompounds(ortho,bases,dial,bar,false);
                }
                natLex = true;
            }
            else if (limit < trials && !tried.contains(ortho)) {
                //System.out.println("Testing...4: "+ortho);
                Convert17.report = "Looking up "+(TextParser.cleanUp(ortho,false,true)).toUpperCase()+" in Cambridge Online Dictionary.";
                bases = getProns(ortho,org,dial,bar,dial.getSyncope());
                reclim = 0;
                if (treed && reclim < trials && !Lemma.structured(bases) || hscp(ortho)) {
                    reclim++;
                    bases = findCompounds(ortho,bases,dial,bar,false);
                }
                if (record && export) {
                    dict.store(bases);
                }
                if (export) {
                    exports = Util.append(exports,bases);
                }
                natLex = false;
            }
            if (!bases.isEmpty()) {
                tried.add(ortho);
                reclim = 0;
                limit = 0;
                return bases;
            }
            else if (limit < trials && !tried.contains(ortho)) {
                //System.out.println("Testing...5: "+ortho);
                Convert17.report = (TextParser.cleanUp(ortho,false,true)).toUpperCase()+" not found.  Attempting morphological analysis.";
                List<Lemma> tempres = parseMorph(org,input,dial,trypfx,trysfx,record,bar);
                boolean amspecentry = DictReader.isWord(org,classify(dial),false);// && !DictReader.isWord(org,classify(dial),true);
                if (amspecentry && !tempres.isEmpty()) {
                    List<Lemma> usres =  Lemma.trimBlank(getProns(ortho,org,dial,false,false));
                    if (!usres.isEmpty()) {
                        tempres = Phonology.compSync(tempres,usres);
                    }
                }
                else if (amspecentry) {
                    tempres = parseLemma(org,input,dial,trypfx,trysfx,record,false);
                }
                if (tempres.isEmpty() && Util.containsEnd(ortho,dict.ablaut)) {
                    String adjust = adjAblaut(ortho);
                    List<Lemma> mainforms = parseLemma(adjust,Lemma.pack(adjust),dial,trypfx,trysfx,record,bar);
                    List<Lemma> ablcopy = Util.copyList(mainforms);
                    tempres = internalInfl(ablcopy,dial,trypfx,trysfx,record,bar);
                    ablauted.addAll(mainforms);
                    ablauted.addAll(tempres);
                    if (record && !natLex) {
                        //dict.store(ablauted);
                    }
                    if (export && !natLex && !TextParser.cuscom) {
                        //exports.addAll(ablauted);
                    }
                }
                /*if (tempres.isEmpty() && trypfx && reclim < trials) {
                    latbase = adjLat(ortho);
                    tried.add(ortho);
                    lat = true;
                    limit = 0;
                    reclim++;
                    tempres = parseLemma(org,Lemma.pack(latbase),dial,true,true,true,bar);
                    tried.add(latbase);
                }*/
                if (tempres.isEmpty() && reclim < trials) {
                    reclim++;
                    tempres = findCompounds(ortho,Lemma.pack(ortho),dial,bar,true);
                
                }
                if (!tempres.isEmpty()) {
                    tried.add(ortho);
                    bases = Lemma.trimDup(Lemma.trimBlank(tempres));
                    return bases;
                }
                tried.add(ortho);
            }
        }
        return bases;
    }
    public List<Lemma> parseMorph (String org, List<Lemma> parse, Dialect dial, boolean trypfx, boolean trysfx, boolean record, boolean bar) {
        String comb = "";
        List<Lemma> results = new ArrayList<>();
        ListIterator<Lemma> iterate = parse.listIterator();
        while (iterate.hasNext()) {
            Lemma base = iterate.next();
            String ortho = base.getSpelling();
            int prefind = findMB(ortho,'p');
            if (trypfx && prefind > 0 && prefind < ortho.length() && !tried.contains(ortho) && limit < trials) {
                List<Lemma> faux = parseLemma(org,Lemma.pack(ortho),dial,false,true,true,bar);
                tried.clear();
                tried.add(ortho);
                limit = 0;
                if (faux.isEmpty()) {
                    String stem = ortho.substring(prefind);
                    String prefx = ortho.substring(0,prefind);
                    comb = prefx+"+"+stem;
                    //System.out.println(comb);
                    List<Lemma> bases = parseLemma(stem,Lemma.pack(stem),dial,true,true,true,bar);
                    List<Lemma> yields = matchMorph(org,bases,parse,dial,prefx,'p',bar);
                    if (yields.isEmpty() && DictReader.isWord(stem,classify(dial),bar)) {                                                             //for "unhelping"
                        tried.clear();
                        comb = prefx+"+"+stem;
                        //System.out.println(comb);
                        bases = parseMorph(stem,Lemma.pack(stem),dial,trypfx,true,true,bar);
                        yields = matchMorph(prefx+stem,bases,parse,dial,prefx,'p',bar);
                    }
                    tried.add(stem);
                    results = Util.append(results,yields);
                }
                else {
                    results = Util.append(results,faux);
                }
            }
            int sufind = findMB(ortho,'s');
            if (results.isEmpty() && trysfx && !tried.contains(ortho) && limit < trials) {
                if (sufind < ortho.length() && sufind > 0) {
                    String stub = ortho.substring(0,sufind);
                    String sufx = ortho.substring(sufind);
                    String stem = attAdj(stub,classify(dial),sufx);
                    comb = stem+"+"+sufx;
                    //System.out.println(comb);
                    List<Lemma> bases = parseLemma(stem,Lemma.pack(stem),dial,trypfx,true,true,bar);
                    //System.out.println(Lemma.multiPrint(bases));
                    ListIterator<Lemma> basitr = bases.listIterator();                                                      //for "mailmen's"
                    while (basitr.hasNext()) {
                        Lemma bslm = basitr.next();
                        String bsallo = bslm.getAllo();
                        if (stem.equals(bsallo)) {
                            restem = bsallo;
                        }
                    }
                    List<Lemma> yields = matchMorph(org,bases,parse,dial,sufx,'s',bar);
                    //System.out.println(Lemma.multiPrint(yields));
                    tried.add(stem);
                    if (yields.isEmpty() && !stem.equals(stub)) {                                                                          //for "severed"
                        comb = (stub+"+"+sufx);
                        //System.out.println(comb);
                        bases = parseLemma(stub,Lemma.pack(stub),dial,trypfx,true,true,bar);
                        yields = matchMorph(stub+sufx,bases,parse,dial,sufx,'s',bar);
                        tried.add(stub);
                        restem = stub;
                    }
                    if (yields.isEmpty() && DictReader.isWord(stem,classify(dial),bar)) {                                                             //for "helpingly"
                        tried.clear();
                        comb = stem+"+"+sufx;
                        //System.out.println(comb);
                        bases = parseMorph(stem,Lemma.pack(stem),dial,trypfx,true,true,bar);
                        yields = matchMorph(stem+sufx,bases,parse,dial,sufx,'s',bar);
                        restem = comb;
                    }
                    results = Util.append(results,yields);
                    /*if (!stem.equals(stub) && isWord(stub,dial,bar)) {                                                                 //for "singing"
                        comb = (stub+"+"+sufx);
                        //System.out.println(comb);
                        bases = parseLemma(stub,Lemma.pack(stub),dial,trypfx,true,true,bar);
                        yields = matchMorph(stub+sufx,bases,parse,dial,sufx,'s',bar);
                        if (!tried.contains(stub)) {
                            tried.add(stub);
                        }
                        results = Util.append(results,yields);
                    }*/
                    restem = org;
                }
            }
        }
        limit++;
        return results;
    }
    public static void initialize () {
        tried.clear();
        //exports.clear();
        //natLex = false;
        magicE = false;
        rad = false;
        gem = false;
        endY = false;
        delY = false;
        limit = 0;
        reclim = 0;
        restem = "";
        latbase = "";
        dotted = false;
        export = true;
        lat = false;
    }
}
