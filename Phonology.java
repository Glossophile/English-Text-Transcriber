package convert17;
import java.util.*;
public class Phonology {
    private String org;
    public static boolean backA;
    private static boolean rhotac;
    public static Morphology morphology = new Morphology();
    private static Map<String,String> recon = new TreeMap<>();
    private static Map<String,String> reconlong = new TreeMap<>();
    public static List<String> presinfl = new ArrayList<>();
    public static Map<String,String> sampa = new TreeMap<>();
    public static Map<String,String> iast = new TreeMap<>();
    public static void initXSMap() {
        sampa.put("ə","@");
        sampa.put("ʌ","V");
        sampa.put("ɛ","E");
        sampa.put("ɪ","I");
        sampa.put("ɒ","Q");
        sampa.put("ʊ","U");
        sampa.put("æ","{");
        sampa.put("ɑ","A");
        sampa.put("ɜ","3");
        sampa.put("ɔ","O");
        sampa.put("ː",":");
        sampa.put("ʃ","S");
        sampa.put("ʒ","Z");
        sampa.put("ŋ","N");
        sampa.put("θ","T");
        sampa.put("ð","D");
        sampa.put("ɹ","r\\");
        sampa.put("˞","`");
        sampa.put("ˈ","\"");
        sampa.put("ˌ","'");
        sampa.put("ʔ","?");
        sampa.put("ɡ","g");
    }
    public static void initIASTMap() {
        iast.put("ʌ","ə");
        iast.put("ɜ","ə");
        iast.put("æ", "ă");
        iast.put("ɑ","aa");
        iast.put("ɛ","e");
        iast.put("ɪ","i");
        iast.put("i","ii");
        iast.put("ɒ","aa");
        iast.put("ɔ", "ŏ");
        iast.put("ʊ","u");
        iast.put("u","uu");
        iast.put("˞","r");
        iast.put("ɹ","r");
        iast.put("ɡ","g");
        iast.put("ʃ", "sh");
        iast.put("ʒ","zh");
        iast.put("C", "ch");
        iast.put("G", "j");
        iast.put("j","y");
        iast.put("θ","th");
        iast.put("ð","dh");
        iast.put("ŋ","ng");
    }
    public static void initRecMap() {
        recon.put("a","æ");
        recon.put("e","ɛ");
        recon.put("i","ɪ");
        recon.put("o","ɒ");
        recon.put("u","ʌ");
        recon.put("y","ɪ");
        recon.put("ai","eɪ");
        recon.put("au","ɔː");
        recon.put("eu","juː");
        recon.put("ou","aʊ");
        recon.put("ia","æ");
        recon.put("ie","ɛ");
        recon.put("io","ɒ");
        recon.put("]","ə");
        recon.put("é","eɪ");
        recon.put("â","æ");
        recon.put("ê","ɛ");
        recon.put("ô","oʊ");
    }
    public static void initRecLongMap() {
        reconlong.put("a","eɪ");
        reconlong.put("e","iː");
        reconlong.put("i","aɪ");
        reconlong.put("o","oʊ");
        reconlong.put("u","juː");
        reconlong.put("y","aɪ");
    }
    public static String rhotVow (String unrhot) {
        String rhov = unrhot;
        for (int iv = 1; iv < rhov.length(); iv++) {
            boolean wrdtrm = iv == rhov.length()-1;
            char prv = rhov.charAt(iv-1);
            char chv = rhov.charAt(iv);
            if (chv == 'ɹ' && (prv == 'ɪ' || prv == 'ʊ') && (toNextVowel(rhov,iv) > 1 || wrdtrm)) {
                if (!wrdtrm) {
                    rhov = rhov.substring(0,iv)+"ə˞"+rhov.substring(iv+1);
                }
                else {
                    rhov = rhov.substring(0,iv)+"ə˞";
                }
            }
        }
        return rhov;
    }
    public static boolean compareRhot(String ar, String nar) {
        boolean isEquiv = ar.equals(nar) || (deRhot(ar,false)).equals(nar);
        return isEquiv;
    }
    public static boolean compareSync (String uncut, String cut) {
        String schwa = uncut.replaceAll("]","ə");
        return schwa.equals(cut);
    }
    public static String dblRhot (String unrhot) {
        String rhot = unrhot;
        for (int h = 1; h < rhot.length()-2; h++) {
            char point0 = rhot.charAt(h-1);
            char point1 = rhot.charAt(h);
            char point2 = rhot.charAt(h+1);
            if (point0 == '˞' && (point1 == '.' || point1 == 'ˌ' || point1 == 'ˈ') && (point2 == 'ɹ' || point2 == 'r')) { 
                rhot = Util.replaceAtIndex(rhot,"(ɹ)",h+1,1);
            }
            else if ((point0 == '˞' || h > 1 && rhot.charAt(h-2) == '˞' && point0 == 'ː') && (point1 == '.' || point1 == 'ˌ' || point1 == 'ˈ') && Lingua.isVowel(point2)) { 
                rhot = Util.insertAtIndex(rhot,"(ɹ)",h+1);
            }
        }
        return rhot;
    }
    public static String deSchwi (String schwi) {
        String deschwid = schwi;
        if (deschwid.contains("i")) {
            int e = 0;
            int l = deschwid.length();
            while (e < l) {
                char v = deschwid.charAt(e);
                if (v == 'i' || v == 'u') {
                    if (v < l - 1) {
                        deschwid = deschwid.substring(0,v+1)+"ː"+deschwid.substring(v+1);
                    }
                    else {
                        deschwid = deschwid+"ː";
                    }
                }
                e++;
            }
        }
        return deschwid;
    }
    public static String deRhot (String intR, boolean intrude) {
        String derh = intR;
        for (int c = 1; c < intR.length(); c++) {
            char curr = intR.charAt(c);
            char prior = intR.charAt(c-1);
            char foll = ' ';
            if (c == intR.length()-1 && (curr == 'r' || curr == 'ɹ' || curr == '˞') && (prior == 'ə' || prior == 'ː' || prior == 'E' || prior == 'I' || prior == 'U')) {
                if (intrude) {
                    derh = derh.substring(0,derh.length()-1)+"r";
                }
                else {
                    derh = derh.substring(0,derh.length()-1);
                }
            }
            else if (c == intR.length()-1 && (curr == 'r' || curr == 'ɹ' || curr == '˞') && prior == '~') {
                String mein = derh.substring(0,derh.lastIndexOf("~"));
                if (intrude && mein.endsWith("iː")) {
                    derh = Util.replaceAtIndex(derh,"ɪə",derh.lastIndexOf("iː"),2);
                }
                else if (intrude && mein.endsWith("uː")) {
                    derh = Util.replaceAtIndex(derh,"ʊə",derh.lastIndexOf("uː"),2);
                }
                else if (intrude && mein.endsWith("eɪ")) {
                    derh = Util.replaceAtIndex(derh,"ɛə",derh.lastIndexOf("eɪ"),2);
                }
            }
            else if (c == intR.length()-1 && (curr == 'r' || curr == 'ɹ' || curr == '˞')) {
                if (intrude) {
                    derh = derh.substring(0,derh.length()-1)+"ːr";
                }
                else {
                    derh = derh.substring(0,derh.length()-1)+"ː";
                }
            }
            else if ((curr == 'r' || curr == 'ɹ' || curr == '˞') && Lingua.sonRank(prior) == 3) {
                foll = intR.charAt(c+1);
                char bfr = intR.charAt(c-1);
                if (c < derh.length()-3 && Lingua.isBound(foll) && Lingua.sonRank(derh.charAt(c+2)) == 3 && bfr != ']') {
                    derh = Util.insertAtIndex(derh,"ɹ",c+2);
                }
                if (Lingua.isFree(prior)) {
                    derh = Util.replaceAtIndex(derh, "ː", c, 1);
                }
                else if (bfr != ']') {
                    derh = Util.removeAtIndex(derh, c);
                }
            }
        }
        return derh;
    }
    public static boolean compareYod(String yod, String nyod) {
        boolean isEquiv = yod.contains("juː") && nyod.contains("uː") && nyod.equals(Util.removeAll(yod,"j"));
        return isEquiv;
    }
    public static boolean validOnset (String pons) {
        String ponset = shave(pons);
        boolean valid = false;
        if (ponset.length() > 1  && !ponset.contains("˞")) {
            char past = ponset.charAt(0);
            boolean plosive = Lingua.isStop(past);
            //boolean labobst = Lingua.isLabObstr(past);
            boolean labdent = Lingua.isLabDent(past);
            boolean alvstop = Lingua.isAlvStop(past);
            //boolean corsib = Lingua.isCorSib(past);
            boolean dental = Lingua.isDent(past);
            boolean voice = Lingua.isVoiced(past);
            int sonmax = Lingua.sonRank(past);
            for (int po = 1; po < ponset.length(); po++) {
                char cons = ponset.charAt(po);
                boolean nasal = Lingua.isNas(cons);
                boolean glide = Lingua.isGlide(cons);
                boolean isR = cons == 'r' || cons == 'ɹ';
                boolean isL = cons == 'l';
                int sonor = Lingua.sonRank(cons);
                if (sonor > sonmax) {
                    sonmax = sonor;
                    boolean licit = plosive && !alvstop && (isR || isL || glide) || (plosive || dental && !voice || labdent && !voice) && (isR) || plosive && glide || labdent && !voice && (isR || isL || glide) || past == 's' && (isL || nasal || glide);
                    plosive = Lingua.isStop(cons);
                    //labobst = Lingua.isLabObstr(cons);
                    labdent = Lingua.isLabDent(cons);
                    alvstop = Lingua.isAlvStop(cons);
                    //corsib = Lingua.isCorSib(cons);
                    dental = Lingua.isDent(cons);
                    voice = Lingua.isVoiced(cons);
                    past = cons;
                    if (licit && !Lingua.isNas(past)) { 
                        valid = true;
                    }
                    else {
                        valid = false;
                        break;
                    }
                    
                }
                else {
                    valid = false;
                    break;
                }
            }
        }
        else {
            valid = true;
        }
        return valid;
    }
    public static String maxOnset (String missyl) {
        if (Lingua.countSyl(missyl) < 1) {
            return missyl;
        }
        String cand = prepSyl(missyl);
        String cluster = "";
        boolean clust = false;
        char border = '.';
        int lstbrd = 0;
        int bckbrd = 0;
        for (int b = 0; b < cand.length(); b++) {
            if (Lingua.isBound(cand.charAt(b))) {
                lstbrd = b;
                bckbrd = b;
                break;
            }
        }
        char intl = cand.charAt(0);
        int lstvow = -1;
        int lstcons = -1;
        int bgcl = -1;
        if (Lingua.isVowel(intl) || Character.isDigit(intl)) {
            lstvow = 0;
        }
        else if (Lingua.isConsonant(intl)) {
            lstcons = 0;
            bgcl = 0;
        }
        boolean shift = true;
        for (int c = 1; c < cand.length(); c++) {
            char esto = cand.charAt(c);
            if ((Lingua.isConsonant(esto) || Lingua.isBound(esto)) && esto != '˞') {
                if (!clust) {
                    clust = true;
                    cluster = "";
                    bgcl = c;
                }
                if (Lingua.isBound(esto)) {
                    border = esto;
                    bckbrd = lstbrd;
                    lstbrd = c;
                }
                else {
                    lstcons = c;
                }
                if (cand.charAt(c-1) == ']') {
                    shift = false;
                }
                cluster += Character.toString(esto);
            }
            else if (clust && (Lingua.isVowel(esto) || Character.isDigit(esto) || esto == ']') && esto != '˞') {
                int tmplv = lstvow;
                lstvow = c;
                char clstend = cluster.charAt(cluster.length()-1);
                char backup = '.';
                if (Lingua.isBound(clstend) && cluster.length() > 1 && shift) {
                    cand = Util.removeAtIndex(cand,c-1);
                    backup = clstend;
                    c -= 1;
                    cluster = cluster.substring(0,cluster.length()-1);
                    clstend = cluster.charAt(cluster.length()-1);
                    border = cand.charAt(tmplv+1);
                    lstbrd = bckbrd;
                }
                if (cluster.length() == 1 && !cluster.equals(".")&& !cluster.equals("ˈ")&& !cluster.equals("ˌ") && lstvow > lstbrd && lstbrd != c-2 && shift) {
                    //System.out.println("Testing 1: "+cluster);
                    cand = Util.insertAtIndex(cand,Character.toString(backup), c-1);
                    c += 1;
                }
                else if (cluster.length() == 2 && Lingua.isBound(clstend) && !cluster.contains("˞") && shift) {
                    //System.out.println("Testing 2: "+cluster);
                    cand = Util.shiftSeg(cand,lstbrd,1,'l');
                    lstbrd -= 1;
                }
                else if (tmplv >= 0) {
                    //System.out.println("Testing 3: "+cluster);
                    boolean valons = false;
                    for (int on = cluster.length()-1; on >= 0; on--) {
                        String psclst = cluster.substring(on);
                        valons = validOnset(psclst);
                        if (!validOnset(psclst)) {
                            int ons = bgcl+on;
                            int offset = 0;
                            if (lstbrd != ons && lstvow < lstbrd) {
                                int dif = lstbrd-ons;
                                cand = Util.shiftSeg(cand,lstbrd,dif,'l');
                            }
                            else if (lstbrd != ons && lstvow > lstbrd && lstbrd < bgcl) {
                                cand = Util.insertAtIndex(cand,".", bgcl+1);
                                offset = 1;
                                lstbrd +=1;
                                c += 1;
                            }
                            lstbrd = bgcl+on+offset;
                            //change = true;
                        }
                        else if (on == 0 && lstvow > lstbrd && bgcl - lstbrd > 1) {
                            cand = Util.insertAtIndex(cand,".", bgcl);
                            lstbrd +=1;
                            c += 1;
                        }
                        else {
                            int ons = bgcl+on;
                            if (lstbrd > ons  && shift) {
                                int dif = lstbrd-ons;
                                cand = Util.shiftSeg(cand,lstbrd,dif,'l');
                                lstbrd = bgcl+on;
                            }
                        }
                    }
                }
                if (clust) {
                    clust = false;
                }
                cluster = "";
                if (!shift) {
                    shift = true;
                }
            }
            else if (Lingua.isVowel(esto) || Character.isDigit(esto) || esto == '˞' || esto == 'ː') {
                lstvow = c;
            }
        }
        cand = unPrepSyl(cand);
        return cand;
    }
    public static int toNextConsonant (String gp, int start) {
        String sbj = Util.removeAll(gp, "ǂ");
        int go = start;
        int dist = 0;
        while (go < sbj.length()) {
            char des = sbj.charAt(go);
            if (Lingua.isConsonant(des) == true) {
                break;
            }
            else if (des != 'ˌ' && des != 'ˈ' && des != 'ː' && des != '.') {
                dist++;
            }
            go++;
        }
        if (dist == 1 && go == sbj.length()) {
            dist = 2;
        }
        return dist;
    }
    public static int toNextVowel (String gp, int start) {
        String sbj = Util.removeAll(gp, "ǂ");
        int go = start;
        int dist = 0;
        while (go < sbj.length()) {
            char des = sbj.charAt(go);
            if (Lingua.isVowel(des) == true || Character.isDigit(des)) {
                break;
            }
            else if (des != 'ˌ' && des != 'ˈ' && des != 'ː' && des != '.' && des != '+' && des != '-' && des != '–') {
                dist++;
            }
            go++;
        }
        if (dist == 1 && go == sbj.length()) {
            dist = 2;
        }
        return dist;
    }
    public static List<String> syllabificate (String unsyl) {
        String targ = unsyl;
        int e = 0;
        int f = 0;
        List<String> syl = new ArrayList<>();
        while (f < targ.length() && Lingua.countSyl(unsyl) > 1) {
            String temp;
            if (Lingua.isBound(targ.charAt(f)) && f < targ.length()-1 && f > 0 && e != f) {
                temp = targ.substring(e, f);
                syl.add(temp);
                e = f;
            }
            else if (f == targ.length()-1) {
                temp = targ.substring(e);
                syl.add(temp);
                break;
            }
            else {
                f++;
            }
        }
        if (Lingua.countSyl(unsyl) == 1) {
            syl.add(unsyl); 
        }
        return syl;
    }
    public static String getSyls (String lon, int num, int skip) {
        String ready = prepSyl(lon);
        String part = "";
        int co = 0;
        int bgn = 0;
        for (int j = 0; j < ready.length() && co < num+skip; j++) {
            char ceci = ready.charAt(j);
            if (j == ready.length()-1) {
                String slbl = ready.substring(bgn);
                if (co >= skip) {
                    part += slbl;
                }
                co += 1;
                bgn = j;
            }
            else if (Lingua.isBound(ceci) && j > 0) {
                String slbl = ready.substring(bgn,j);
                if (co >= skip) {
                    part += slbl;
                }
                co += 1;
                bgn = j;
            }
        }
        return part;
    }
    public static String clip (String unclip) {
        String clpd = unclip;
        int lon = Lingua.countSyl(unclip);
        if (Lingua.countSyl(unclip) >= 4 && clpd.contains("]")) {
            String last = Lingua.getSyl(unclip, Lingua.countSyl(unclip)-1, true);
            String penult = Lingua.getSyl(unclip, Lingua.countSyl(unclip)-2, true);
            String antepen = Lingua.getSyl(unclip, Lingua.countSyl(unclip)-3, true);
            String ons = shave(Lingua.onset(penult));
            if ((Lingua.nucleus(penult)).equals("]") && ons.length() == 1 && (Lingua.isStop(ons.charAt(0)) || Lingua.isNas(ons.charAt(0))) && ((Lingua.nucleus(antepen)).equals("ə") || (Lingua.nucleus(antepen)).equals("ɪ"))) {
                String pren = ons+Lingua.coda(penult);
                char onsin = last.charAt(1);
                String comb = pren+Character.toString(onsin);
                boolean poss = Lingua.isVowel(onsin) || validOnset(comb);
                if (validOnset(pren) && poss) {
                    clpd = clpd.substring(0,clpd.lastIndexOf(penult))+"."+pren+last.substring(last.indexOf(Lingua.nucleus(last)));
                }
                else if (poss) {
                    pren = ons+"."+Lingua.coda(penult);
                    clpd = clpd.substring(0,clpd.lastIndexOf(penult))+pren+last.substring(last.indexOf(Lingua.nucleus(last)));
                }
            }
        }
        return clpd;
    }
    public static List<Lemma> compSync (List<Lemma> sncpt, List<Lemma> usdct) {
        List<Lemma> merg = new ArrayList<Lemma>();
        for (int mr = 0; mr < sncpt.size(); mr++) {
            Lemma mrlm = sncpt.get(mr);
            String mrus = mrlm.getUnsyncUS();
            for (int nr = 0; nr < usdct.size(); nr++) {
                Lemma nrlm = usdct.get(nr);
                String nrus = nrlm.getPronUS();
                if (nrus.equals(mrus)) {
                    merg.add(sncpt.get(mr));
                }
                else {
                    merg.add(mrlm);
                    merg.add(nrlm);
                }
            }
        }
        return merg;
    }
    public static String cleanRhot (String dirty) {
        String clean = dirty;
        char prdr = clean.charAt(0);
        for (int rc = 1; rc < clean.length(); rc++) {
            char chdr = clean.charAt(rc);
            if (chdr == '˞' && prdr == 'ː') {
                clean = Util.removeAtIndex(clean,rc-1);
                rc -= 1;
            }
            else if (chdr == '˞' && !Lingua.isVowel(prdr)) {
                clean = Util.replaceAtIndex(clean,"ɹ",rc,1);
            }
            prdr = chdr;
        }
        return clean;
    }
    public static String syncope (String spell, String noncl, Dialect diax) {
        //System.out.println(noncl);
        if (!diax.getSyncope() || !noncl.contains("]")) {
            return noncl;//.replaceAll("]","ə");
        }
        String unclip = noncl;
        int lon = Lingua.countSyl(noncl);
        int bnm = Lingua.countBound(noncl);
        if (lon > 1 && !(lon == bnm && Lingua.isBound(noncl.charAt(0))) && lon != bnm+1) {
            unclip = maxOnset(noncl);
        }
        String clpd = unclip;
        if (lon >= 3) {
            int host = lon - 3;
            for (int sr = 0; sr < lon; sr++) {
                String fet = Lingua.getSyl(unclip,sr,true);
                if (sr >= 1 && fet.contains("]")) {
                    host = sr-1;
                    break;
                }
            }
            if (host < lon - 2) {
                unclip = unclip.replaceAll("r","ɹ");//(unclip.replaceAll("˞","ɹ")).replaceAll("r","ɹ");
                String last = Lingua.getSyl(unclip, host+2, true);
                String penult = Lingua.getSyl(unclip, host+1, true);
                String antepen = Lingua.getSyl(unclip, host, true);
                String prec;
                String pen;
                if (Lingua.isOpen(antepen)) {
                    prec = Lingua.onset(penult);
                }
                else {
                    String prcd = Lingua.coda(antepen);
                    prec = prcd.substring(prcd.length()-1)+Lingua.onset(penult);
                }
                if (Lingua.isOpen(penult)) {
                    pen = Lingua.onset(last);
                }
                else {
                    String prcd = Lingua.coda(penult);
                    pen = prcd.substring(prcd.length()-1)+Lingua.onset(last);
                }
                prec = Util.removeAll(prec,".");
                String ons1 = Util.removeAll(Lingua.onset(penult),"(ɹ)");
                String nuc1 = Lingua.nucleus(penult);
                String cod1 = Lingua.coda(penult);
                String ons2 = Util.removeAll(Lingua.onset(last),"(ɹ)");
                String nuc2 = Lingua.nucleus(last);
                String cod2 = Lingua.coda(last);
                boolean rhotpen = penult.endsWith("˞") || cod1.equals("ɹ"); 
                boolean rhotlast = last.endsWith("˞") || cod2.equals("ɹ");
                boolean alvplos = prec.length() > 0 && Lingua.isAlvStop(prec.charAt(prec.length()-1));
                //System.out.println(antepen+"+"+penult+"+"+last);
                if (prec.length() >= 1 && last.startsWith(".")) {
                    char prcfin = prec.charAt(prec.length()-1);
                    char penfin = pen.charAt(pen.length()-1);
                    boolean repeat = nuc1.equals("]") && (nuc2.equals("]") || nuc2.equals("ə")) && (rhotpen && rhotlast || cod1.equals("l") && cod2.equals("l"));
                    boolean homorganic = ons1.length() > 1 && Lingua.place(antepen.charAt(antepen.length()-1)) == Lingua.place(ons1.charAt(1)) && repeat;
                    boolean sync1 = (!Lingua.isAlvStop(prcfin) || !diax.getFlap()) && !Lingua.isCorSib(prcfin) && (nuc2.equals("]") || nuc2.equals("ə") || nuc2.equals("ɪ") && cod2.length() > 0 && Lingua.sonRank(cod2.charAt(0)) == 1 || nuc2.equals("iː"));
                    boolean sync2 = (ons1.length() <= 2 && ons2.length() <= 2 && (rhotpen || cod1.endsWith("l") || Lingua.isOpen(penult)));
                    boolean syncopable = !repeat && !homorganic && sync1 && sync2;
                    boolean colpen = validOnset(prcfin+cod1);
                    boolean singintrv = (Lingua.isOpen(antepen) && ons1.length() == 2 || (Lingua.coda(antepen)).length() == 1 && ons1.length() == 1) && (cod1.length() == 1 && ons2.length()== 1 || Lingua.isOpen(penult) && ons2.length() == 2);
                    boolean extrasync = !diax.getFlap() || !alvplos;
                    //System.out.println(repeat+" "+homorganic+" "+sync1+" "+sync2+" "+singintrv+" "+diax.getFlap()+" "+alvplos);
                    clpd = clpd.replaceAll("r","ɹ");
                    if (syncopable && nuc1.equals("]") && (Lingua.isOpen(penult) || cod1.equals("ɹ") || cod1.equals("˞") || cod1.equals("l"))) {
                        String sync = prec+cod1;
                        sync = sync.replace("˞","ɹ");
                        clpd = prepSyl(unclip);
                        if (!validOnset(sync) && !repeat) {
                            String begin;
                            int anch = clpd.lastIndexOf(antepen)+antepen.length();
                            if (!Lingua.isOpen(antepen) && !(Lingua.coda(antepen)).endsWith(Character.toString(shave(sync).charAt(0)))) {
                                clpd = Util.shiftSeg(clpd,anch,1,'r');
                                begin = clpd.substring(0,anch+2);
                            }
                            else {
                                begin = clpd.substring(0,anch+1);
                            }
                            if (antepen.endsWith("˞")) {
                                ons1 = Util.removeAll(Lingua.onset(penult),"(ɹ)");   
                            }
                            else {
                                ons1 = (Lingua.onset(penult)).replaceAll("(ɹ)","ɹ");
                            }
                            ons2 = Lingua.nucleus(last);
                            if (sync.charAt(sync.length()-2) != 'n') {// && nuc2.equals("]")) {      //not-N condition for "general"; second condition removed 3/29/2018
                                int lim = clpd.lastIndexOf(ons2);
                                String end = clpd.substring(lim);
                                //System.out.println(begin+"+"+shave(ons1)+"+"+cod1+"+"+end);
                                clpd = begin+shave(ons1)+cod1+end;
                                clpd = cleanRhot(clpd);
                            }
                            else {// if (nuc2.equals("]")) {                                        condition removed 3/29/2018
                                clpd = (unclip.replace("]ɹ", "]˞")).replace("]r","]˞");
                            }
                        }
                        else {
                            int ant = clpd.indexOf(Lingua.nucleus(antepen),clpd.lastIndexOf(antepen));
                            char precinit = shave(sync).charAt(0);
                            int anch = clpd.indexOf(precinit,ant);
                            String begin = clpd.substring(0,anch);
                            if (begin.endsWith(".")) {
                                begin = clpd.substring(0,anch-1);
                            }
                            int lim = clpd.lastIndexOf(nuc2);
                            String end = clpd.substring(lim);
                            clpd = begin+"."+sync+end;
                        }
                    }
                    /*else if (syncopable && nuc1.equals("]")) {
                        clpd = unclip.replace(penult+last,ons1+cod1+nuc2+cod2);
                    }*/
                    else if (nuc2.equals("]") && !rhotpen && rhotlast) {
                        String sync = pen+cod2;
                        clpd = prepSyl(unclip);
                        if (!validOnset(sync)) {
                            String begin;
                            int anch = clpd.lastIndexOf(penult)+penult.length();
                            if (!cod1.endsWith(Character.toString(shave(sync).charAt(0)))) {
                                clpd = Util.shiftSeg(clpd,anch,1,'r');
                                begin = clpd.substring(0,anch+2);
                            }
                            else {
                                begin = clpd.substring(0,anch+1);
                            }
                            int lim = clpd.indexOf(".",clpd.lastIndexOf(cod2))+1;
                            String end = clpd.substring(lim);
                            clpd = begin+cod2+end;
                            if (clpd.endsWith("ɹ") && diax.getRhotic()) {
                                clpd = clpd.substring(0,clpd.length()-1)+"˞";
                            }
                            else if (clpd.endsWith("ɹ")){
                                clpd = clpd.substring(0,clpd.length()-1)+"r";
                            }
                            
                        }
                    }
                    else if (nuc1.equals("]") && (nuc2.equals("]") || nuc2.equals("ə") || (nuc2.startsWith("i") && spell.endsWith("ally"))) && singintrv && extrasync) {                       //option added 7/20/2019 and modified for "-ally" words 10/16/2019
                        String begin = clpd.substring(0,clpd.indexOf(unPrepSyl(penult)));
                        String end = clpd.substring(clpd.indexOf(unPrepSyl(last))+1);
                        if (Lingua.isOpen(antepen)) {
                            clpd = begin+ons1.substring(1)+"."+cod1.replaceAll("˞","ɹ")+end;
                        }
                        else {
                            clpd = begin+"."+cod1+end;
                        }
                    }
                }
            }
        }
        clpd = clpd.replaceAll("]","ə");
        /*for (int sch = 1; sch < clpd.length(); sch++) {
            char rt = clpd.charAt(sch);
            if (rt == '˞') {
                char pc = clpd.charAt(sch-1);
                if (!Lingua.isVowel(pc)) {
                    clpd = Util.replaceAtIndex(clpd,"ɹ",sch,1);
                }
            }
        }*/
        clpd = unPrepSyl(clpd);
        return clpd;
    }
    public static String reStress (String unstr) {
        String targ = unstr;
        String restr = "";
        int e = 0;
        int f = 0;
        int no = Lingua.countSyl(targ);
        int track = 0;
        boolean fourth = true;
        while (f < targ.length()) {
            String temp;
            if (Lingua.isBound(targ.charAt(f)) && f < targ.length()-1 && f > 0 && e != f) {
                temp = targ.substring(e, f);
                e = f;
                if (track == no-4 && !temp.startsWith("ˈ")) {
                    fourth = false;
                }
                else if (track == no-3 && !fourth) {
                    temp = "ˌ"+temp.substring(1);
                }
                restr += temp;
                track++;
            }
            else if (f == targ.length()-1) {
                temp = targ.substring(e);
                restr += temp;
                track++;
                break;
            }
            else {
                f++;
            }
        }
        int total = Lingua.countSyl(restr);
        if (total >= 4 && !Lingua.isStressed(Lingua.getSyl(restr, 0, true), 0) && (Lingua.getSyl(restr, 1, true)).startsWith("ˌ") && !Lingua.isStressed(Lingua.getSyl(restr, 2, true), 0)) {
            restr = "ˌ"+Util.replaceAtIndex(restr, ".", restr.indexOf("ˌ"), 1);
        }
        if (restr.indexOf("ˈ") < restr.lastIndexOf("ˌ")) {
            restr = restr.replaceAll("ˈ","ˌ");
            restr = Util.replaceAtIndex(restr,"ˈ",restr.lastIndexOf("ˌ"),1);
        }
        return restr;
    }
    public static String antiClash (String stress) {
        String unclash = stress;
        int mark = 0;
        for (int m = 0; m < unclash.length(); m++) {
            if (Lingua.isBound(unclash.charAt(m))) {
                mark = m;
                break;
            }
        }
        for (int a = mark+1; a < unclash.length(); a++) {
            char str  = unclash.charAt(a);
            char pre = unclash.charAt(mark);
            if (str == 'ˌ' && pre == 'ˈ') {
                unclash = Util.replaceAtIndex(unclash,".",a,1);
            }
            else if (str == 'ˈ' && pre == 'ˌ') {
                unclash = Util.replaceAtIndex(unclash,".",mark,1);
            }
            else if (str == 'ˌ' && pre == 'ˌ') {
                unclash = Util.replaceAtIndex(unclash,".",a,1);
            }
            else if (str == 'ˈ' && pre == 'ˈ') {
                unclash = Util.replaceAtIndex(unclash,".",mark,1);
            }
            else if (Lingua.isBound(str)){
                mark = a;
            }
        }
        if (unclash.startsWith(".")) {
            unclash = unclash.substring(1);
        }
        return unclash;
    }
    public static String reconstruct (String spelt, String pronounced, boolean unreduce, boolean lax, boolean adjbrit) {
        String spell = spelt;
        String phonol = lengthenA(pronounced);
        char folch = spelt.charAt(spelt.length()-1);
        if (Lingua.isConsonant(folch)) {
            for (int whr = spelt.length()-2; whr >= 0; whr--) {
                char ltr = spelt.charAt(whr);
                if (Lingua.isVowel(ltr)) {
                    break;
                }
                else if (Lingua.sonRank(folch) > Lingua.sonRank(ltr)) {
                    spell = Util.insertAtIndex(spell,"]",whr+1);
                }
                folch = ltr;
            }
        }
        String recov = "";
        List <String> orvow = new ArrayList<>();
        String phin = (phonol.replaceAll("aɪə","aɪ.ə")).replaceAll("aʊə","aʊ.ə");
        phin = (phin.replaceAll("eɪə","eɪ.ə")).replaceAll("ɔɪə","ɔɪ.ə");
        phin = (phin.replaceAll("əʊə","oʊə")).replaceAll("oʊ.ə","oʊ.ə");
        boolean cons = true;
        int track = Lingua.countSyl(phin);
        int vwl = 0;
        for (int ic = 0; ic < spell.length(); ic++) {
            char trig = spell.charAt(ic);
            if (Lingua.isVowel(trig) || ic < spell.length()-1 && trig == 'y' && !Lingua.isVowel(spell.charAt(ic+1)) || ic == spell.length()-1 && trig == 'y') {
                orvow.add(Character.toString(trig));
                cons = true;
                vwl++;
            }
            else if (vwl > 0 && ic > 0 && ic < spell.length()-1 && cons) {
                String termin = orvow.get(vwl-1)+"$";
                orvow.remove(vwl-1);
                orvow.add(vwl-1,termin);
                cons = false;
            }
        }
        if (orvow.size() > track) {
            int dscrp = orvow.size() - track;
            int tally = 0;
            for (int ir = 0; ir < orvow.size()-1 && tally < dscrp; ir++) {
                String test = orvow.get(ir)+Util.removeAll(orvow.get(ir+1),"$");
                String nuc = Lingua.nucleus(Lingua.getSyl(phin, ir, true));
                if (recon.containsKey(test) && ((recon.get(test)).contains(nuc) || nuc.endsWith("ə"))) {
                    orvow.remove(ir);
                    orvow.add(ir, test);
                    orvow.remove(ir+1);
                    tally++;
                }
            }
        }
        else if (orvow.size() < track) {
            int dscrp = track - orvow.size();
            int tally = 0;
            for (int ir = 0; ir < track-2 && tally < dscrp; ir++) {
                String test = unPrepSyl(Lingua.nucleus(Lingua.getSyl(phin, ir, true)))+unPrepSyl(Lingua.nucleus(Lingua.getSyl(phin, ir+1, true)));
                String orv = Util.removeAll(orvow.get(ir),"$");
                String corv = reconlong.get(orv);
                if (test.equals(corv+"ə")) {
                    if (ir < orvow.size()-1) {
                        orvow.add(ir+1,"a");
                    }
                    else {
                        orvow.add("a");
                    }
                }
                
            }
        }
        int sylcnt = Lingua.countSyl(phin);
        if (sylcnt > Lingua.countSeg(phin) && phin.contains("˞")) {
            phin = Lingua.rhoBound(phin);
        }
        if (sylcnt > Lingua.countSeg(phin) && phin.contains("]")) {
            phin = Lingua.syncBound(phin);
        }
        for (int inx = 0; inx < track; inx++) {
            String act = Lingua.getSyl(phin, inx, true);
            String actnuc = Lingua.nucleus(act);
            boolean reduced = actnuc.endsWith("ə");
            boolean schwi = !Lingua.isStressed(actnuc,0) && actnuc.endsWith("i");
            boolean notlast = inx < sylcnt-1;
            String nextsyl = "";
            String nxtshvd = "";
            if (notlast) {
                nextsyl = Lingua.getSyl(phin,inx+1,true);
                nxtshvd = shave(nextsyl);
            }
            boolean schwipret = notlast && Lingua.isStressed(nextsyl,0);
            boolean prevocalic = notlast && Lingua.isOpen(act) && (Lingua.isVowel(nxtshvd.charAt(0)) || Character.isDigit(nxtshvd.charAt(0)));
            String unred = Util.removeAll(orvow.get(inx),"$");
            if (reduced && unreduce) {
                recov += act.replace("ə", recon.get(unred));
            }
            else if (schwi && schwipret && lax && unred.equals("e") && !prevocalic) {
                recov += act.replace("i","ɪ");
            }
            else if (adjbrit && actnuc.endsWith("ɑː")) {
                boolean caught = unred.equals("au") || unred.equals("aw") || unred.equals("augh") || unred.equals("ough");
                if (caught) {
                    recov += act.replace("ɑː", "ɔː");
                }
                else if (unred.equals("o")) {
                    recov += act.replace("ɑː", "ɒ");
                }
                else {
                    recov += act;
                }
            }
            else {
                recov += act;
            }
        }
        if (unreduce) {
            recov = recov.replaceAll("ˈ","ˌ");
        }
        return recov;
    }
    public static String[] equalize (String us, String uk) {
        String amer = (us.replaceAll("ɪ.ə","ɪə")).replaceAll("ʊ.ə", "ʊə");
        String brit = uk.replaceAll("əʊ","oʊ");
        //brit = Util.removeAll(brit,"r");
        brit = (brit.replaceAll("ɪ.ə","ɪə")).replaceAll("ʊ.ə", "ʊə");
        for (int d = 0; d < amer.length()-1; d++) {
            if (amer.charAt(d) == 'j' && brit.charAt(d) == 'i') {
                amer = amer.substring(0,d)+Character.toString(brit.charAt(d))+"."+amer.substring(d+1);
            }
        }
        List<String> ussyl = syllabificate(dblRhot(amer));
        List<String> uksyl = syllabificate(brit);
        String past1 = uksyl.get(uksyl.size()-1);   //changed from ussyl 6/29/2016
        String past2 = ussyl.get(ussyl.size()-1);
        if (ussyl.size() < uksyl.size()) {
            int sz = ussyl.size()-1;
            boolean brk = false;
            int f = 0;
            for (int e = uksyl.size()-1; e >= 0; e--) {
                int usloc;
                /*int ukah;
                int usah;*/
                if (sz-f < 0) {
                    usloc = 0;
                }
                else {
                    usloc = sz - f;
                }
                /*if (e > 0) {
                    ukah = e-1;
                }
                else {
                    ukah = 0;
                }
                if (usloc > 0) {
                    usah = usloc-1;
                }
                else {
                    usah = 0;
                }*/
                String sbl1 = uksyl.get(e);
                String sbl2 = ussyl.get(usloc);
                /*String ahd1 = uksyl.get(ukah);
                String ahd2 = ussyl.get(usah);*/
                String tst1 = Lingua.nucleus(sbl1);
                String tst2 = Lingua.nucleus(sbl2);
                String onsbr = Lingua.onset(sbl1);
                String codbr = Lingua.coda(sbl1);
                String codam = Lingua.coda(sbl2);
                //String codam2 = Lingua.coda(past2);
                String onsbr2 = Lingua.onset(past1);
                String onsam2 = Lingua.onset(past2);
                //System.out.println(sbl1+"+"+sbl2);
                boolean schwasbl = tst1.equals("ə") || tst1.equals("]");
                if (schwasbl && e >= 1 && e < uksyl.size()-1) {
                    String surr = "";
                    String pair = "";
                    String bef = "";
                    if (onsbr.length() <= 1) {
                        String pstcd = Lingua.coda(uksyl.get(e-1));
                        bef = Character.toString(pstcd.charAt(pstcd.length()-1));
                    }
                    else {
                        bef = Character.toString(onsbr.charAt(onsbr.length()-1));
                    }
                    String aft = "";
                    if (e < uksyl.size()-1 && codbr.length() <= 0) {
                        aft = Character.toString((Lingua.onset(uksyl.get(e+1))).charAt(1));
                    }
                    else {
                        aft = Character.toString(codbr.charAt(0));
                    }
                    surr = bef+aft;
                    if (codam.length() >= 1) {
                        pair = Character.toString(codam.charAt(codam.length()-1))+shave(onsam2);
                    }
                    else {
                        pair = shave(onsam2);
                    }
                    if (pair.length() > 2) {
                        pair = pair.substring(0,2);
                    }
                    if (surr.equals(pair)) {
                        int ancr = brit.lastIndexOf(sbl1);
                        String piece1 = brit.substring(0,ancr);
                        String piece2 = brit.substring(ancr+sbl1.length()+1);
                        if (piece1.endsWith(surr.substring(0,1))) {
                            piece1 = piece1.substring(0,piece1.length()-1);
                        }
                        if (piece2.startsWith(surr.substring(1))) {
                            piece2 = piece2.substring(1);
                        }
                        String breik = past1.substring(0,1);
                        if (!validOnset(surr)) {
                            brit = piece1+surr.substring(0,1)+breik+surr.substring(1)+piece2;
                        }
                        else {
                            brit = piece1+breik+surr+piece2;
                        }
                        uksyl = syllabificate(brit);
                    }
                } 
                else if (tst1.equals("ɪ") || tst1.equals("ə") && compareRhot(past2,past1)) {
                    String onset1 = shave(Lingua.onset(sbl1));
                    String coda2 = Lingua.coda(sbl2);
                    char cod2 = coda2.charAt(coda2.length()-1);
                    if (onset1.startsWith(Character.toString(cod2))) {
                        int mrk = amer.lastIndexOf(cod2);
                        amer = Util.removeAtIndex(amer,mrk);
                        mrk = amer.lastIndexOf(past2);
                        amer = Util.insertAtIndex(amer,sbl1,mrk);
                        ussyl = syllabificate(dblRhot(amer));
                    }
                }
                else if ((tst1.equals("ɪ") || tst1.equals("ʊ")) && tst2.endsWith("ə")) {
                    brk = true;
                }
                else if (brk && tst1.endsWith("ɪ") && tst2.endsWith("ɪə") || tst1.endsWith("ʊ") && tst2.endsWith("ʊə")) {
                    int where = amer.lastIndexOf(tst2);
                    String nw = tst1+".ə";
                    amer = amer.substring(0,where)+nw+amer.substring(where+nw.length()-1);
                    if (!amer.contains("ˈ")) {
                        amer = "ˈ"+amer;
                    }
                    ussyl = syllabificate(dblRhot(amer));
                    brk = false;
                }
                else if (tst1.equals("iː") && !tst2.equals("iː") && onsam2.endsWith("j") && !onsbr2.endsWith("j")) {
                    String anchor;
                    if (onsam2.length() > 2) {
                        anchor = onsam2.substring(0,onsam2.indexOf("j"));
                    }
                    else {
                        anchor = Lingua.coda(sbl2);
                    }
                    if ((shave(Lingua.onset(sbl1))).endsWith(shave(anchor)) && (Lingua.nucleus(past2)).equals(Lingua.nucleus(past1))) {
                        String exp = sbl2+past2;
                        int start = amer.lastIndexOf(exp);
                        String fix = maxOnset(sbl2+anchor+"iː")+past1;
                        amer = Util.replaceAtIndex(amer,fix,start,exp.length());
                        ussyl = syllabificate(dblRhot(amer));
                    }
                }
                past2 = ussyl.get(usloc);
                past1 = sbl1;
                f++;
            }
        }
        else if (ussyl.size() > uksyl.size()) {
            int sz = uksyl.size()-1;
            String posclip = "";
            String nxt1 = "";
            String nxt2 = "";
            int f = 0;
            for (int e = ussyl.size()-1; e >= 0; e--) {
                int ukloc;
                if (sz-f < 0) {
                    ukloc = 0;
                }
                else {
                    ukloc = sz-f;
                }
                String actual1 = uksyl.get(ukloc);
                String actual2 = ussyl.get(e);
                String tst1 = Lingua.nucleus(actual1);
                String ons1 = Lingua.onset(actual1);
                String tst2 = Lingua.nucleus(actual2);
                String ons2 = Lingua.onset(actual2);
                //System.out.println(actual1+"  "+nxt1+"\n"+actual2+"   "+nxt2);
                if (((Lingua.nucleus(nxt2)).equals("ə") || (Lingua.nucleus(nxt2)).equals("]")) && (tst2.equals("iː") && (Lingua.nucleus(nxt1)).equals("ɪə") || tst2.equals("uː") && (Lingua.nucleus(nxt1)).equals("ʊə"))) {
                    int where = brit.lastIndexOf(Lingua.nucleus(nxt1));
                    String insrt = tst2+nxt2;
                    if (brit.length() > where+2 && !insrt.endsWith(Character.toString(brit.charAt(where+2)))) {
                        brit = Util.replaceAtIndex(brit,insrt,where,2);
                    }
                    else {
                        brit = Util.replaceAtIndex(brit,insrt,where,3);
                    }
                    uksyl = syllabificate(deRhot(brit,true));
                }
                //else if (ons1.length() > 1 && ons2.length() > 1 && ((Lingua.isStop(ons1.charAt(1)) || Lingua.isNas(ons1.charAt(1))) && ons1.endsWith("ɹ") || ons1.charAt(1) == 'ɹ') && (tst1.equals("ə") || tst1.equals("]")) && ons2.length() > 1 && ons2.charAt(1) == 'ɹ') {
                if (ons2.length() > 1 && (ons2.charAt(1) == 'ɹ' || ons2.equals(".(ɹ)"))) {
                    posclip = actual1;
                }
                //else if (posclip.length() > 1 && ons2.length() > 1 && (ons2.charAt(1) == posclip.charAt(1) || Lingua.isOpen(actual2) && (Lingua.coda(actual1)).equals(ons2.substring(1)) && (Lingua.onset(posclip)).equals(".ɹ")) && !actual2.endsWith("ɹə") && (ons2.charAt(0) == 'ˈ' || ons2.charAt(0) == 'ˌ')) {
                else if (posclip.length() > 1 && ons2.length() > 1 && ((ons2.charAt(1) == posclip.charAt(1) ||ons2.endsWith("(ɹ)") && posclip.charAt(1) == 'ɹ') || Lingua.isOpen(actual2) && (Lingua.coda(actual1)).equals(ons2.substring(1)) && (Lingua.onset(posclip)).equals(".ɹ")) && !actual2.endsWith("ɹə")) {
                    String mod = actual2+Lingua.onset(nxt2)+Lingua.nucleus(posclip)+Lingua.coda(posclip);
                    int where = brit.lastIndexOf(posclip);
                    int len = posclip.length();
                    if ((Lingua.coda(actual1)).equals(ons2.substring(1))) {
                        where -= 1;
                        len += 1;
                    }
                    String britemp = Util.replaceAtIndex(brit,mod,where,len);
                    String prestres = britemp.substring(0,brit.lastIndexOf("ˈ"));
                    String postres = britemp.substring(brit.lastIndexOf("ˈ"));
                    prestres = prestres.replaceAll("ˈ","ˌ");
                    brit = prestres+postres;
                    uksyl = syllabificate(brit);
                }
                nxt1 = actual1;
                nxt2 = actual2;
                f++;
            }
        }
        String[] equal = {brit,amer};
        return equal;
    }
    public static String matchStress (String amsyl, String britsyl, boolean usstr, boolean ukstr) {
        String hyb;
        if (ukstr && !usstr && (Lingua.nucleus(britsyl)).equals("ə")) {
            hyb = Character.toString(britsyl.charAt(0))+amsyl.substring(1);
        }
        else {
            hyb = amsyl;
        }
        return hyb;
    }
    public static String hybridize (Lemma ingred, Dialect dia) {
        if (Lingua.countSyl(ingred.getPronUK()) < 1 && Lingua.countSyl(ingred.getPronUS()) < 1) {
            return ingred.getPronUK();
        }
        rhotac = false;
        String brit = ingred.getPronUK();
        String amer = ingred.getPronUS();
        int ukcount = Lingua.countSyl(brit);
        int uscount = Lingua.countSyl(amer);
        if ((uscount > 1 || ukcount > 1) && uscount != ukcount) {
            String britlong = ingred.getUnsyncUK();
            String amlong = ingred.getUnsyncUS();
            int ukext = Lingua.countSyl(britlong);
            int usext = Lingua.countSyl(amlong);
            boolean britcut = !(britlong.replaceAll("]","ə")).equals(brit);
            boolean amcut = !(amlong.replaceAll("]","ə")).equals(amer);
            if ((amcut || britcut) && (usext > 1 || ukext > 1) && usext != ukext) {
                String[] equalized = equalize(ingred.getUnsyncUS(),ingred.getUnsyncUK());
                brit = equalized[0];
                amer = equalized[1];
            }
            else {
                brit = ingred.getUnsyncUK();
                amer = ingred.getUnsyncUS();
            }
            brit = brit.replaceAll("]","ə");
            amer = amer.replaceAll("]","ə");
            ukcount = Lingua.countSyl(brit);
            uscount = Lingua.countSyl(amer);
        }
        if ((uscount > 1 || ukcount > 1) && uscount != ukcount) {
            String[] equalized = equalize(ingred.getPronUS(),ingred.getPronUK());
            brit = equalized[0];
            amer = equalized[1];
        }
        List<String> ussyl = syllabificate(dblRhot(amer));
        List<String> uksyl = syllabificate(brit.replaceAll("r","ɹ"));
        String hybrid = "";
        if (uksyl.size() > 1 && ussyl.size() > 1 && uksyl.size() == ussyl.size()) {
            String brprev = uksyl.get(0);
            String amprev = ussyl.get(0);
            //System.out.println(brprev+"  "+amprev);
            for (int fk = 1; fk < ussyl.size(); fk++) {
                String brnow = uksyl.get(fk);
                String amnow = ussyl.get(fk);
                //System.out.println(brnow+"  "+amnow);
                String brcod = Lingua.coda(brprev);
                String brnuc = Lingua.nucleus(brprev);
                String amnuc = Lingua.nucleus(amprev);
                String amons = (Lingua.onset(amnow)).substring(1);
                String brons = (Lingua.onset(brnow)).substring(1);
                String prob = brcod+(Lingua.onset(brnow)).substring(1);
                boolean triph = brnuc.equals("aɪə") || brnuc.equals("aʊə") || brnuc.equals("eɪə") || brnuc.equals("ɔɪə") || brnuc.equals("oʊə");
                if (prob.equals(amons)) {
                    String fixed1 = amprev+brcod;
                    String fixed2 = amnow.substring(0,1)+amnow.substring(brcod.length()+1);
                    ussyl.set(fk-1,fixed1);
                    ussyl.set(fk,fixed2);
                }
                if (triph && Lingua.isOpen(amprev) && Lingua.isOpen(brprev) && amons.equals(brons) && amons.length() == 1 && Lingua.sonRank(brons.charAt(0)) == 2 && amnuc.equals(brnuc.substring(0,brnuc.length()-1))) {
                    uksyl.set(fk-1,amprev);
                }
                String shv1 = shave(brnow);
                String shv2 = shave(amnow);
                boolean affr1 = brprev.endsWith("t͡ʃ") || brprev.endsWith("d͡ʒ");
                boolean affr2 = amprev.endsWith("t͡ʃ") || amprev.endsWith("d͡ʒ");
                boolean affr3 = shv1.startsWith("t͡ʃ") || shv1.startsWith("d͡ʒ");
                boolean affr4 = shv2.startsWith("t͡ʃ") || shv2.startsWith("d͡ʒ");
                String trm1 = brprev.substring(brprev.length()-1);
                String trm2 = amprev.substring(amprev.length()-1);
                String bgn1 = brnow.substring(1,2);
                String bgn2 = amnow.substring(1,2);
                String stub1 = brprev.substring(0,brprev.length()-1);
                String stub2 = amprev.substring(0,amprev.length()-1);
                String stub3 = Util.removeAtIndex(brnow,1);
                String stub4 = Util.removeAtIndex(amnow,1);
                if (Lingua.sonRank(trm1.charAt(0)) == 1 && !affr1 && amprev.equals(stub1) && Lingua.sonRank(bgn2.charAt(0)) == 1 && !affr4 && brnow.equals(stub4) && trm1.equals((Lingua.voicepairs).get(bgn2))) {          //for "translation"
                    ussyl.set(fk-1,amprev+bgn2);
                    ussyl.set(fk,stub4);
                }
                else if (Lingua.sonRank(trm2.charAt(0)) == 1 && !affr2 && brprev.equals(stub2) && Lingua.sonRank(bgn1.charAt(0)) == 1 && !affr3 && amnow.equals(stub3) && trm2.equals((Lingua.voicepairs).get(bgn1))) {
                    uksyl.set(fk-1,brprev+bgn1);
                    uksyl.set(fk,stub3);
                }
                brprev = brnow;
                amprev = amnow;
            }
        }
        boolean prevstr = false;
        boolean usstr = false;
        boolean ukstr = false;
        String prvhyb = "";
        String hyb = "";
        for (int g = 0; g < uksyl.size() && g < ussyl.size(); g++) {
            String amsyl = ussyl.get(g);
            String britsyl = uksyl.get(g);
            String dual = amsyl+"   "+britsyl;
            //System.out.println(dual);
            usstr = amsyl.startsWith("ˈ") || amsyl.startsWith("ˌ");
            ukstr = britsyl.startsWith("ˈ") || britsyl.startsWith("ˌ");
            String coda1 = Lingua.coda(britsyl);
            String coda2 = Lingua.coda(amsyl);
            int codif = coda2.length() - coda1.length();
            if (codif > 0 && g < uksyl.size()-1 && g < ussyl.size()-1) {                                  //for "rhetoric"
                String prox1 = uksyl.get(g+1);
                String prox2 = ussyl.get(g+1);
                String fol1 = shave(Lingua.onset(prox1));
                String fol2 = shave(Lingua.onset(prox2));
                boolean routik = coda2.equals("˞") && fol2.equals("(ɹ)") && fol1.equals("ɹ");
                if ((shave(fol1)).startsWith(coda2) && !routik) {
                    try {
                        britsyl += fol1.substring(0,codif);
                    }
                    catch (StringIndexOutOfBoundsException lon) {
                        britsyl += fol1;
                    }
                    String calib = prox1.substring(0,1)+prox1.substring(codif+1);
                    uksyl.set(g+1,calib);
                }
            }
            else if (codif < 0 && g < uksyl.size()-1 && g < ussyl.size()) {
                codif = -codif;
                String prox1 = uksyl.get(g+1);
                String prox2 = ussyl.get(g+1);
                String fol1 = shave(Lingua.onset(prox1));
                String fol2 = shave(Lingua.onset(prox2));
                boolean routik = coda2.equals("˞") && fol2.equals("(ɹ)") && fol1.equals("ɹ");
                if ((shave(fol2)).startsWith(coda1) && !routik) {
                    try {
                        amsyl += fol2.substring(0,codif);
                    }
                    catch (StringIndexOutOfBoundsException lon) {
                        amsyl += fol2;
                    }
                    String calib = prox2.substring(0,1)+prox2.substring(codif+1);
                    ussyl.set(g+1,calib);
                }
            }
            if (britsyl.contains("ɜː") && amsyl.contains("ɛ")) {
                int place = britsyl.lastIndexOf("ɜː");
                britsyl = britsyl.substring(0,place)+"ɛə"+britsyl.substring(place+2);
            }
            else if (britsyl.endsWith("ɑː") && amsyl.endsWith("ɛ") && g < uksyl.size()-1 && (uksyl.get(g+1)).startsWith(".ɹ")) {
                amsyl = amsyl.replace("ɛ","ɑ˞");
            }
            else if (((Lingua.nucleus(britsyl)).equals("ɪə") || (Lingua.nucleus(britsyl)).equals("ʊə")) && (Lingua.nucleus(amsyl)).equals("ə") && (Lingua.coda(amsyl)).startsWith("˞")) {
                if (!Lingua.isOpen(britsyl)) {
                    int ins = britsyl.indexOf("ə")+1;
                    amsyl = Util.insertAtIndex(britsyl,"˞",ins);
                }
                else {   
                    amsyl = britsyl+"˞";
                }
            }
            else if (britsyl.endsWith("ʊə") && amsyl.endsWith("ə˞") && !amsyl.endsWith("ʊə˞")) {
                amsyl = britsyl+"˞";
            }
            else if (britsyl.contains("ʊə") && amsyl.contains("ɜ˞")) {
                amsyl = britsyl+"˞";
            }
            else if ((britsyl.startsWith(".ɹ") || britsyl.startsWith("ˌɹ") || britsyl.startsWith("ˈɹ")) && (amsyl.startsWith(".(ɹ)ə") || amsyl.startsWith("ˌ(ɹ)ʌ") || amsyl.startsWith("ˈ(ɹ)ʌ") || amsyl.startsWith(".(ɹ)ɑː") || amsyl.startsWith("ˌ(ɹ)ɑː") || amsyl.startsWith("ˈ(ɹ)ɑː"))) {
                amsyl = Util.replaceAtIndex(amsyl,Lingua.nucleus(britsyl),4,1);
            }
            else if (britsyl.contains("ɛəɹ") || britsyl.contains("ɪəɹ") || britsyl.contains("ʊəɹ")) {
                int place = britsyl.lastIndexOf("ɹ");
                amsyl = Util.replaceAtIndex(britsyl,"˞",place,1);
            }
            String uscod = Lingua.coda(amsyl);
            boolean routik = (g < ussyl.size()-1 && (shave(ussyl.get(g+1))).startsWith("ɹ") || uscod.equals("ɹ"))  && g < uksyl.size()-1 && !Lingua.isVowel((shave(uksyl.get(g+1))).charAt(0));
            if ((britsyl.contains("ɛə") || britsyl.contains("ɪə") || britsyl.contains("ʊə")) && britsyl.endsWith("~ɹ")) {
                hyb = amsyl;
            }
            else if (britsyl.contains("ɔː") && (Lingua.nucleus(amsyl)).equals("ʊ") && routik) {     //for "assurance"
                //hyb = amsyl.replace("ʊ","ʊə˞");
                hyb = amsyl;
            }
            else if (britsyl.contains("ɛə") || britsyl.contains("ɪə") || britsyl.contains("ʊə")) {// || ((britsyl.contains("jʊə") || britsyl.contains("ʊə")) && amsyl.contains("ʊə˞")) || (britsyl.contains("jɪə") && amsyl.contains("ɪə˞"))) {
                int place = britsyl.lastIndexOf("ə");
                String ukcod = Lingua.coda(britsyl);
                String nucl1 = Lingua.nucleus(britsyl);
                String nucl2 = Lingua.nucleus(amsyl);
                boolean matched = nucl2.equals(nucl1.substring(0,1));
                if (matched) {
                    if (g == uksyl.size()-1 || Lingua.isOpen(britsyl) && routik || !Lingua.isOpen(britsyl)) {
                        hyb = Util.replaceAtIndex(britsyl,"ə˞",place,1);
                        if (hyb.endsWith("˞ɹ") || hyb.endsWith("˞r")) {
                            hyb = hyb.substring(0,hyb.length()-1);
                        }
                    }
                }
                else if (Lingua.containsRhot(amsyl)) {
                    hyb = amsyl;
                }
                else {
                    hyb = britsyl;
                }
            }
            else if ((Lingua.nucleus(amsyl)).equals("ɑː") && (Lingua.nucleus(britsyl)).equals("ə")) {
                String etym = reconstruct(ingred.getSpelling(),brit,true,false,true);
                List<String> etymsyls = syllabificate(maxOnset(etym));
                String etymsyl = etymsyls.get(g);
                if (etymsyl.contains("ɒ")) {
                    hyb = britsyl.replaceAll("ə","ɒ");
                }
                else {
                    hyb = amsyl;
                }
            }
            else if ((Lingua.nucleus(amsyl)).equals("ɑː") && (Lingua.nucleus(britsyl)).equals("əʊ")) {
                hyb = britsyl.replace("əʊ","oʊ");
            }
            else if (usstr && britsyl.contains("ə") || ukstr && amsyl.contains("ə") || amsyl.contains("ɪə˞") || amsyl.contains("ʊə˞") || amsyl.contains("~ɹ") || (amsyl.contains("ɹə") && !britsyl.contains("ɹ")) || (!britsyl.contains("ɛə") && !britsyl.contains("ɪə") && !britsyl.contains("ʊə") && (Lingua.isRhotic(amsyl) && !britsyl.contains("ʌ") && !britsyl.contains("ɒ") || amsyl.contains("ɹ") && rhotac)) || amsyl.contains("æ") && !backA || (amsyl.contains("ɔ˞") && britsyl.contains("ɒ")) || !(Lingua.nucleus(amsyl)).equals("ə") && (Lingua.nucleus(britsyl)).equals("ə") && !((Lingua.nucleus(amsyl)).equals("ʌ") && prevstr && usstr)) {// || britsyl.contains("tj") || britsyl.contains("dj") || britsyl.contains("sj") || britsyl.contains("zj") || britsyl.contains("nj") || britsyl.contains("lj")) {
                hyb = matchStress(amsyl,britsyl,usstr,ukstr);
            }
            else {
                hyb = britsyl;
            }
            if (hyb.contains("j") && !amsyl.contains("j")) {
                String britons = Lingua.onset(britsyl);
                String amons = Lingua.onset(amsyl);
                boolean consyod = britons.endsWith("dj") || britons.endsWith("tj");
                boolean affric = amons.endsWith("d͡ʒ") || amons.endsWith("t͡ʃ");
                if (!(affric && consyod)) { 
                    hyb = Util.removeAll(hyb, "j");
                }
            }
            if (hyb.contains("t͡ʃu") && amsyl.contains("tu") || hyb.contains("t͡ʃʊ") && amsyl.contains("tʊ")) {
                hyb = hyb.replaceFirst("t͡ʃ","t");
            }
            else if (hyb.contains("d͡ʒu") && amsyl.contains("du") || hyb.contains("d͡ʒʊ") && amsyl.contains("dʊ")) {
                hyb = hyb.replaceFirst("d͡ʒ","d");
            }
            boolean amstres = Lingua.isStressed(amsyl,0);
            boolean britstres = Lingua.isStressed(britsyl,0);
            boolean difstres = amstres != britstres || amstres && britstres && amsyl.charAt(0) != britsyl.charAt(0);
            if (difstres) {
                hyb = hyb.replaceAll("ˈ","ˌ");
            }
            if (prvhyb.length() > 0 && Lingua.isDiphthong(Lingua.nucleus(prvhyb)) && !prvhyb.endsWith("ə")) {
                String brid = shave(hyb);
                if (brid.startsWith("j") && prvhyb.endsWith("ɪ")) {
                    hyb = Util.removeAtIndex(hyb,1);
                }
                if (brid.startsWith("w") && prvhyb.endsWith("ʊ")) {
                    hyb = Util.removeAtIndex(hyb,1);
                }
            }
            hybrid += hyb;
            prvhyb = hyb;
            if (Lingua.isRhotic(hyb)) {
                rhotac = true;
            }
            else {
                rhotac = false;
            }
            prevstr = hyb.startsWith("ˈ") || hyb.startsWith("ˌ");
        }
        for (int e = 1; e < hybrid.length()-2; e++) {
            char of0 = hybrid.charAt(e-1);
            char of1 = hybrid.charAt(e);
            char of2 = hybrid.charAt(e+1);
            char of3 = hybrid.charAt(e+2);
            if (of1 == 'ə' && (of0 == 'ɪ' || of0 == 'ʊ') && Lingua.isBound(of2) && of3 == 'ɹ') {
                hybrid = Util.insertAtIndex(hybrid,"˞",e+1);
            }
        }
        for (int f = 1; f < hybrid.length()-1; f++) {
            char off0 = hybrid.charAt(f-1);
            char off1 = hybrid.charAt(f);
            char off2 = hybrid.charAt(f+1);
            if (off1 == 'ə' && off0 == 'ɛ' && off2 != '˞') {
                hybrid = Util.insertAtIndex(hybrid,"˞",f+1);
            }
        }
        hybrid = hybrid.replaceAll("əʊ","oʊ");
        hybrid = hybrid.replaceAll("əɹ","ə˞");
        /*if (hybrid.endsWith("ɪə") && !amer.endsWith("ɪə")) {
            hybrid = hybrid.substring(0,hybrid.lastIndexOf("ɪə"))+"iːə";
        }
        else if (hybrid.endsWith("ʊə") && !amer.endsWith("ʊə")) {
            hybrid = hybrid.substring(0,hybrid.lastIndexOf("ɪə"))+"uːə";
        }*/
        hybrid = hybrid.replaceAll("iːə","iː.ə");
        hybrid = hybrid.replaceAll("uːə","uː.ə");
        try {
            if (!hybrid.contains("ˈ")) {
                hybrid = Util.replaceAtIndex(hybrid,"ˈ",hybrid.lastIndexOf('ˌ'),1);
            }
        }
        catch (IndexOutOfBoundsException non) {
            //do nothing
        }
        hybrid = antiClash(hybrid);
        return dblRhot(hybrid);
    }
    public static String lengthenA (String shortA) {
        String lengthnd = (shortA.replaceAll("ɑɪ","aɪ")).replaceAll("ɑʊ","aʊ");
        int length = lengthnd.length();
        char flw = lengthnd.charAt(length-1);
        for (int wa = 0; wa < length; wa++) {
            char vw = lengthnd.charAt(wa);
            if (vw == 'ɑ' && wa < length-1 && lengthnd.charAt(wa+1) != 'ː' && lengthnd.charAt(wa+1) != '˞') {
                lengthnd = Util.insertAtIndex(lengthnd,"ː",wa+1);
            }
            else if (vw == 'ɑ' && wa == length-1) {
                lengthnd = lengthnd+"ː";
            }
            flw = vw;
        }
        return lengthnd;
    } 
    public static String britishize (String spell, String pronounce) {
        String britform = "";
        String phonol = lengthenA(pronounce);
        String etymol = reconstruct(spell,phonol,false,false,true);
        List<String> phonsyl = syllabificate(phonol);
        List<String> etymsyl = syllabificate(etymol);
        for (int et = 0; et < phonsyl.size(); et++) {
            String nowsyl1 = phonsyl.get(et);
            String nowsyl2 = etymsyl.get(et);
            String nownuc1 = Lingua.nucleus(nowsyl1);
            String nownuc2 = Lingua.nucleus(nowsyl2);
            if (nownuc1.equals("ɑː") && nownuc2.equals("ɒ")) {
                britform += nowsyl2;
            }
            else {
                britform += nowsyl1;
            }
        }
        britform = britform.replaceAll("r","ɹ");
        return deRhot(britform,true);
    }
    public static String americanize (String unrhot, String rhal) {
        String nonrh;
        if (unrhot.endsWith("ə.təri") || unrhot.endsWith("ə.t]ri") || unrhot.endsWith("].təri") || unrhot.endsWith("].t]ri")) {
            nonrh = (unrhot.substring(0,unrhot.length()-2)+"ɹ.i").replaceAll("r","ɹ");
        }
        else {
            nonrh = (unrhot.replaceAll("r","ɹ")).replaceAll("əʊ","oʊ");
        }
        nonrh = nonrh.replaceAll("ɒɹ","ɔːɹ");
        String rhprep = rhal.replaceAll("t̬","t");
        /*rhprep = (rhprep.replaceAll("aɪɹ","aɪɚ")).replaceAll("aʊɹ","aʊɚ");
        rhprep = (rhprep.replaceAll("ɔɪɹ","ɔɪɚ")).replaceAll("oʊɹ","oʊɚ");
        rhprep = (rhprep.replaceAll("eɪɹ","eɪɚ"));*/
        rhprep = rhotVow(rhprep);
        rhprep = (rhprep.replaceAll("ɝː","ɜːɹ").replaceAll("ɚ", "əɹ")).replaceAll("r","ɹ");
        if (nonrh.endsWith("ən.əɹ.i") || nonrh.endsWith("ən.]ɹ.i")) {
            rhprep = rhprep.replace("ə.neɹ","ən.eɹ");
        }
        else if (nonrh.endsWith("]n.əɹ.i") || nonrh.endsWith("]n.]ɹ.i")) {
            rhprep = rhprep.replace("*.neɹ","*n.eɹ");
        }
        rhprep = Util.removeAll(rhprep,"-");
        if (Lexicon.lowback.contains(unrhot)) {
            rhprep = rhprep.replaceAll("ɔːɹ", "ɑːɹ");
        }
        else if (Lexicon.brityodrop.contains(unrhot)) {
            rhprep = (rhprep.replace("əˌɹ", "jəɹˌ")).replace("əˈɹ","jəɹˈ");
            rhprep = (rhprep.replace("əɹ", "jəɹ")).replace("ə.ɹ","jəɹ.");
        }
        else if (Lexicon.britrhdrop.contains(unrhot)) {
            rhprep = (rhprep.replace("ɔːˌɹ", "ɔːɹˌ")).replace("ɔːˈɹ","ɔːɹˈ");
            rhprep = (rhprep.replace("ɑːˌɹ", "ɑːɹˌ")).replace("ɑːˈɹ","ɑːɹˈ");
            rhprep = (rhprep.replace("ɑː.ɹ", "ɑːɹ.")).replace("ɔː.ɹ","ɔːɹ.");
            rhprep = (rhprep.replace("ɑː", "ɑːɹˌ")).replace("ɔː","ɔːɹ");
        }
        String rhot = nonrh.replaceAll("ɒ","ɑː");
        try {
            if (rhprep.startsWith("ˈ") && (rhal.startsWith("-") || rhal.endsWith("-"))) {
                String frag = rhot.substring(rhot.indexOf("ˈ"));
                String frag1 = maxOnset(frag);
                String rhsyl;
                if (frag1.contains(".")) {
                    rhsyl = frag1.substring(0,frag1.indexOf("."));
                }
                else {
                    rhsyl = frag1;
                }
                String frag2 = maxOnset(rhprep);
                String rhpr;
                try {
                    rhpr= frag2.substring(0,frag2.indexOf("."));
                }
                catch (StringIndexOutOfBoundsException shrt) {
                    rhpr = frag2;
                }
                String noR = deRhot(rhpr,false);
                //if ((Lingua.onset(rhsyl)).equals(Lingua.onset(rhpr)) && (Lingua.nucleus(rhsyl)).equals(Lingua.nucleus(noR))) {
                if (rhsyl.charAt(0) == rhpr.charAt(0)) {// && (rhsyl.charAt(rhsyl.length()-1) == rhpr.charAt(rhpr.length()-1) || rhsyl.charAt(rhsyl.length()-1) == noR.charAt(noR.length()-1) && (Lingua.nucleus(rhsyl)).equals(Lingua.nucleus(noR)))) {
                    List<String> syls1 = syllabificate(rhot.substring(rhot.indexOf("ˈ")));
                    String newrhot = rhot.substring(0,rhot.indexOf("ˈ"));
                    List<String> syls2 = syllabificate(rhprep);
                    int synchron = syls2.size();
                    for (int wh = 0; wh < syls2.size(); wh++) {
                        String silaba = syls2.get(wh);
                        if ((syls1.get(wh)).endsWith("i") && (syls2.get(wh)).startsWith(".j")) {
                            synchron += 1;
                        }
                        newrhot += silaba;
                    }
                    if (rhal.endsWith("-") && syls1.size() > syls2.size()) {
                        for (int hw = synchron; hw < syls1.size(); hw++ ) {
                            String silaba = syls1.get(hw);
                            newrhot += silaba;
                        }
                    }
                    //newrhot = (newrhot.replaceAll("ɪɹ", "ɪə˞")).replaceAll("ʊɹ", "ʊə˞");
                    newrhot = rhotVow(newrhot);
                    rhot = newrhot;
                }
            }
            else if (!rhal.startsWith("-") && !rhal.endsWith("-")) {
                //rhprep = (rhprep.replaceAll("ɪɹ","ɪə˞")).replaceAll("ʊɹ","ʊə˞");
                rhprep = rhotVow(rhprep);
                rhot = rhprep;
            }
            else if (rhal.startsWith("-") && !rhal.endsWith("-")) {
                if (!Lingua.isBound(rhprep.charAt(0))) {
                    rhprep = "."+rhprep;
                }
                if (rhprep.contains("jʊɹ") && !nonrh.contains("jʊɹ") && !Lingua.isStressed(rhprep,rhprep.lastIndexOf("jʊɹ"))) { 
                    rhprep = rhprep.replace("jʊɹ","jəɹ");
                }
                if (rhprep.contains("jʊɹ") && !nonrh.contains("jʊɹ") && !Lingua.isStressed(rhprep,rhprep.lastIndexOf("jʊɹ"))) { 
                    rhprep = rhprep.replace("jʊɹ","jəɹ");
                }
                //rhprep = (rhprep.replaceAll("ɪɹ","ɪə˞")).replaceAll("ʊɹ","ʊə˞");
                rhprep = rhotVow(rhprep);
                int dif = Lingua.countSyl(rhot) - Lingua.countSyl(rhprep);
                String syl = getSyls(rhot,dif,dif);
                if (syl.charAt(0) == 'ˈ' || syl.charAt(0) == 'ˌ') {
                    rhprep = syl.substring(0,1)+rhprep.substring(1);
                }
                if (Lingua.sonRank(syl.charAt(1)) != Lingua.sonRank(rhprep.charAt(1))) {
                    rhprep = Lingua.onset(syl)+rhprep.substring(1);
                }
                if (rhot.endsWith("i.ə") && rhprep.equals(".jə") || rhot.endsWith("i.əɹ") && rhprep.equals(".jəɹ")) {
                    rhot = rhot.substring(0,rhot.lastIndexOf("i"))+rhprep;
                }
                else {
                    rhot = getSyls(rhot,dif,0)+rhprep;
                }
            }
            else if (rhal.endsWith("-") && !rhal.startsWith("-")) {
                if (rhprep.contains("jʊɹ") && !nonrh.contains("jʊɹ") && !Lingua.isStressed(rhprep,rhprep.lastIndexOf("jʊɹ"))) { 
                    rhprep = rhprep.replace("jʊɹ","jəɹ");
                }
                //rhprep = (rhprep.replaceAll("ɪɹ","ɪə˞")).replaceAll("ʊɹ","ʊə˞");
                rhprep = rhotVow(rhprep);
                int disp = Lingua.countSyl(rhprep);
                int dif = Lingua.countSyl(rhot) - disp;
                rhot = rhprep+getSyls(rhot,dif,disp);
            }
            else {
                if (!Lingua.isBound(rhprep.charAt(0))) {
                    rhprep = "."+rhprep;
                }
                int s = 0;
                if (Lingua.countSyl(rhprep) > 1) {
                    for (int n = 1; n < rhprep.length(); n++) {
                        char tst = rhprep.charAt(n);
                        if ((Lingua.isBound(tst) || n == rhprep.length()-1)) {
                            String extr = rhprep.substring(s,n);
                            if (!rhot.contains(extr)) {
                                rhprep = extr;
                                break;
                            }
                            s = n;
                        }
                    }
                }
                String src = (rhprep.replaceAll("æ", "ɑː")).replaceAll("eɹ","eəɹ");
                //src = (src.replaceAll("ɪɹ", "ɪə˞")).replaceAll("ʊɹ", "ʊə˞");
                src = rhotVow(src);
                if (src.charAt(0) == '.') {
                    src = src.replaceAll("e","ə");
                }
                if (rhprep.contains("jʊɹ") && !rhot.contains("jʊɹ") && !Lingua.isStressed(rhprep,rhprep.lastIndexOf("jʊɹ"))) { 
                    rhprep = rhprep.replace("jʊɹ","jəɹ");
                    src = Util.removeAll(src,"j");
                }
                //rhprep = (rhprep.replaceAll("ɪɹ","ɪə˞")).replaceAll("ʊɹ","ʊə˞");
                rhprep = rhotVow(rhprep);
                if (!rhot.contains(src) && !rhot.contains(deRhot(src,false))) {
                    src = Util.replaceAtIndex(src,"əɹ",src.lastIndexOf("ɔː"),2);
                }
                if (!rhot.contains(src) && !rhot.contains(deRhot(src,false))) {
                    src = Util.replaceAtIndex(src,"əɹ",src.lastIndexOf("eɹ"),1);
                }
                if (!rhot.contains(src) && !rhot.contains(deRhot(src,false))) {
                    src = Util.replaceAtIndex(src,"ɑːɹ",src.lastIndexOf("ɔːɹ"),1);
                }
                if (!rhot.contains(src) && !rhot.contains(deRhot(src,false))) {
                    src = Lingua.onset(src)+"ə"+Lingua.coda(src);
                }
                /*if (!rhot.contains(src) && !rhot.contains(deRhot(src,false)) && rhprep.contains("eɹ") && rhot.contains("e") && !rhot.contains("eɹ")) {
                    System.out.println(src+"   "+rhprep+"   "+rhot);
                    src = Lingua.onset(src)+"ə"+Lingua.coda(src);
                }*/
                List<String> compr = syllabificate(rhot);
                String oldrhot = rhot;
                rhot = compr.get(0);
                boolean rhtc = false;
                int part = 0;
                for (int i = 1; i < compr.size()-1; i++) {
                    String hoc = compr.get(i);
                    if ((src.equals(hoc) || compareRhot(src,hoc) && !oldrhot.contains(src)) && !rhtc || compareYod(hoc,rhprep)) {
                        rhot += rhprep;
                        rhtc = true;
                    }
                    else {
                        rhot += hoc;
                    }
                    part++;
                }
                rhot += compr.get(compr.size()-1);
            }
        }
        catch (StringIndexOutOfBoundsException except) {
            rhot = unrhot;
        }
        if (unrhot.contains ("r.") || unrhot.contains("rˈ") || unrhot.contains("rˌ")) {
            String britrhot = "";
            boolean got = false;
            for (int r = 0; r < Lingua.countSyl(unrhot); r++) {
                String ora1 = getSyls(unrhot,1,r);
                String ora2 = getSyls(rhot,1,r);
                if ((Lingua.coda(ora1)).startsWith("r") && compareRhot(ora1.replaceAll("r","ɹ"),ora2)) {
                    britrhot += ora1;
                    got = true;
                }
                else if (shave((Lingua.onset(ora2))).startsWith("r") && got) {
                    int whr = ora2.indexOf("r");
                    britrhot += ora2.substring(0,whr)+ora2.substring(whr+1);
                    got = false;
                }
                else {
                    britrhot += ora2;
                }
            }
            rhot = britrhot.replaceAll("r","ɹ");
        }
        rhot = (rhot.replaceAll("ɜːɹ","ɜ˞ː")).replaceAll("əɹ","ə˞");
        rhot = (rhot.replaceAll("ɑːɹ","ɑ˞ː")).replaceAll("ɔːɹ","ɔ˞ː");
        rhot = (rhot.replaceAll("æˈɹ","eˈɹ")).replaceAll("æˌɹ","eˌɹ");
        rhot = rhot.replaceAll("æ.ɹ","e.ɹ");
        rhot = rhot.replaceAll("æɹ","eɹ");
        rhot = rhot.replaceAll("ʌɹ","ɜ˞ː");
        rhot = rhot.replaceAll("ɜːɹ","ɜ˞ː");
        /*rhot = (rhot.replaceAll("tjuː","tuː")).replaceAll("djuː","duː");
        rhot = (rhot.replaceAll("sjuː","suː")).replaceAll("zjuː","zuː");
        rhot = (rhot.replaceAll("ɹˈɹ","ˈɹ")).replaceAll("ɹˌɹ","ˌɹ");
        rhot = rhot.replaceAll("njuː","nuː");
        rhot = rhot.replaceAll("ɹ.ɹ",".ɹ");*/
        rhot = (rhot.replaceAll("ˌˌ","ˌ")).replaceAll("ˈˈ","ˈ");
        return rhot;
    }
    public static String shave (String id) {
        return id = Util.removeAll(Util.removeAll(Util.removeAll(id,"ˈ"),"ˌ"),".");
    }
    public static String prepSyl (String unsylb) {
        String pre = (unsylb.replaceAll("t͡ʃ","C")).replaceAll("d͡ʒ", "G");
        pre = pre.replaceAll("aɪə","0");
        pre = pre.replaceAll("aʊə","1");
        pre = pre.replaceAll("eɪə","2");
        pre = pre.replaceAll("ɔɪə","3");
        pre = pre.replaceAll("oʊə","4");
        pre = pre.replaceAll("aɪ","5");
        pre = pre.replaceAll("aʊ","6");
        pre = pre.replaceAll("eɪ","7");
        pre = pre.replaceAll("ɔɪ","8");
        pre = pre.replaceAll("oʊ","9");
        pre = pre.replaceAll("ɛə","E");
        pre = pre.replaceAll("ɪə","I");
        pre = pre.replaceAll("ʊə","U");
        pre = pre.replaceAll("əʊ","O");
        return pre;
    }
    public static String unPrepSyl (String pos) { 
        if (!pos.equals("|||")) {
            String post = (pos.replaceAll("C","t͡ʃ")).replaceAll("G","d͡ʒ");
            post = post.replaceAll("0","aɪə");
            post = post.replaceAll("1","aʊə");
            post = post.replaceAll("2","eɪə");
            post = post.replaceAll("3","ɔɪə");
            post = post.replaceAll("4","oʊə");
            post = post.replaceAll("5","aɪ");
            post = post.replaceAll("6","aʊ");
            post = post.replaceAll("7","eɪ");
            post = post.replaceAll("8","ɔɪ");
            post = post.replaceAll("9","oʊ");
            post = post.replaceAll("E","ɛə");
            post = post.replaceAll("I","ɪə");
            post = post.replaceAll("U","ʊə");
            post = post.replaceAll("O","əʊ");
            return post;
        }
        else {
            return pos;
        }
    }
    public static String parseY (String spell, String pronounce) {
        String disamb = spell;
        int sy = toNextVowel(pronounce,0);
        boolean interim = false;
        for (int c = 1; c < spell.length(); c++) {
            char ryt = spell.charAt(c);
            boolean notfin = c < spell.length()-1;
            if (ryt == 'y') {
                char prey = spell.charAt(c-1);
                boolean prec = Lingua.isConsonant(prey);
                if (c == spell.length()-1 && prec) {
                    disamb = disamb.substring(0,disamb.length()-1)+"[";
                }
                else if (notfin) {
                    char posty = spell.charAt(c+1);
                    boolean postc = Lingua.isConsonant(posty);
                    if (prec && postc) {
                        disamb = Util.replaceAtIndex(disamb,"[",c,1);
                    }
                    else {
                        int end = toNextConsonant(pronounce,sy);
                        String ncl = pronounce.substring(sy,end);
                        boolean yod = sy > 0 && pronounce.charAt(sy-1) == 'j';
                        if (yod) {
                            int move = sy+toNextConsonant(pronounce,sy);
                            sy = move+toNextVowel(pronounce,move);
                            interim = true;
                        }
                        else {
                            disamb = Util.replaceAtIndex(disamb,"[",c,1);
                        }
                    }
                }
            }
            else if (Lingua.isConsonant(ryt) && !interim) {
                if (notfin) {
                    int move = sy+toNextConsonant(pronounce,sy);
                    sy = move+toNextVowel(pronounce,move);
                }
                interim = true;
            }
            else if (Lingua.isVowel(ryt)) {
                interim = false;
            }
        }
        return disamb;
    }
    public static String collapse (String wrg, String pho1, String pho2) {
        String pho3 = pho1;
        String pho4 = Util.removeAll(pho2," ");
        int nmsl1 = Lingua.countSyl(Util.removeAll(pho3," "));
        int nmsl2 = Lingua.countSyl(Util.removeAll(pho4," "));                    
        char frst1 = pho1.charAt(0);
        char frst2 = pho2.charAt(0);
        if (!Lingua.isBound(frst1) && Lingua.isBound(frst2) && nmsl1 == 1) {
            pho3 = Character.toString(frst2)+pho3;
        }
        else if (!Lingua.isBound(frst2) && Lingua.isBound(frst1) && nmsl2 == 1) {
            pho4 = Character.toString(frst1)+pho4;
        }
        List<String> sils1 = syllabificate(pho3);
        List<String> sils2 = syllabificate(pho4);
        String parsedY = parseY(wrg,pho2);
        int nxt = toNextVowel(parsedY,0);
        String temprev = "";
        int ig;
        for (ig = 0; ig < sils2.size(); ig++) {
            try {
                String sy1 = sils1.get(ig);
                String sy2 = sils2.get(ig);
                String nuc1 = Lingua.nucleus(sy1);
                String nuc2 = Lingua.nucleus(sy2);
                String ons1 = Lingua.onset(sy1);
                String ons2 = Lingua.onset(sy2);
                String cod1 = Lingua.coda(sy1);
                String cod2 = Lingua.coda(sy2);
                char next;
                if (nxt < parsedY.length()) {
                    next = parsedY.charAt(nxt);
                }
                else {
                    next = '#';
                }
                String tempsyl = "";
                if (ons1.equals(ons2) && (nuc1.equals("aɪ") && nuc2.equals("ɪ") && next == 'i' || nuc1.equals("iː") && nuc2.equals("e") && next == 'e')) {
                    tempsyl = sy1.replace(nuc1,nuc2);
                }
                else if (nuc1.equals("ɪə") && nuc2.equals("eə") || nuc1.equals("ɒ") && nuc2.equals("əʊ")) {
                    tempsyl = sy1.replace(nuc1,nuc2);
                }
                else {
                    tempsyl = sy1;
                }
                if (cod2.contains("r") && !cod1.contains("r")) {
                    String tempnuc = Lingua.nucleus(tempsyl);
                    tempsyl = tempsyl.replace(tempnuc,tempnuc+"r");
                }
                temprev += tempsyl;
                int move = nxt+toNextConsonant(parsedY,nxt);
                nxt = move+toNextVowel(parsedY,move);
                pho3 = temprev;
            }
            catch (IndexOutOfBoundsException dumb) {
                break;
            }
        }
        if (sils1.size() > sils2.size()) {          //for British "perhaps" versus "praps"
            while (ig < sils1.size()) {
                pho3 += sils1.get(ig);
                ig++;
            }
        }
        return pho3;
    }
    public static String matchSylR (String pho1, String pho2) {
        String pho3 = pho1;
        String pho4 = pho2;
        int nmsl1 = Lingua.countSyl(Util.removeAll(pho3," "));
        int nmsl2 = Lingua.countSyl(Util.removeAll(pho4," "));
        if (pho4.startsWith("-") || pho4.endsWith("-")) {
            pho3 = americanize(pho3,(pho4.replaceAll("t̬","t")).replaceAll("r", "ɹ"));
        }
        else {
            pho3 = (pho4.replaceAll("t̬","t")).replaceAll("r", "ɹ");
        }
        if (pho1.contains("]r")) {
            String britmod = pho1.replaceAll("r","ɹ");
            pho4 = (pho4.replaceAll("ɚ","ə˞")).replaceAll("r","ɹ");
            String[] pare = {britmod,pho4};
            if (Lingua.countSyl(pho4) > 1 && Lingua.countSyl(britmod) > 1) { 
                pare = equalize(pho4,britmod);
            }
            List<String> brt = syllabificate(pare[0]);
            List<String> amr = syllabificate(pare[1]);
            boolean fixed = false;
            String norm = "";
            char amrnx;
            for (int nm = 0; nm < brt.size() && nm < amr.size(); nm++) {
                String brtsl = brt.get(nm);
                String amrsl = amr.get(nm);
                if (nm < amr.size()-1) {
                    amrnx = (amr.get(nm+1)).charAt(1);
                }
                else {
                    amrnx = '|';
                }
                String nucl = Lingua.nucleus(amrsl);
                if (brtsl.endsWith("]ɹ") && nucl.equals("ə") && amrnx == 'ɹ') {
                    norm += amrsl.replace("ə","]r");
                    fixed = true;
                }
                else if (brtsl.endsWith("]ɹ") && amrsl.endsWith("ə˞")) {
                    norm += amrsl.replace("ə˞","]˞");
                }
                else if (fixed) {
                    norm += Util.removeAtIndex(amrsl,1);
                    fixed = false;
                }
                else {
                    norm += amrsl;
                }
            }
            pho3 = norm;
        }
        return pho3;
    }
    public static String rhotacize (String norho) {
        String rho = norho.replaceAll("r", "ɹ");
        rho = rho.replaceAll("ɜːɹ","ɜ˞ː");
        rho = (rho.replaceAll("ɝː","ɜ˞ː")).replaceAll("ɚ","ə˞");
        rho = (rho.replaceAll("ɜː","ɜ˞ː")).replaceAll("əɹ","ə˞");
        rho = (rho.replaceAll("ɑɹ","ɑ˞ː")).replaceAll("ɔɹ","ɔ˞ː");
        rho = (rho.replaceAll("ɑːɹ","ɑ˞ː")).replaceAll("ɔːɹ","ɔ˞ː");
        rho = (rho.replaceAll("ɪəɹ","ɪə˞")).replaceAll("ʊəɹ","ʊə˞");
        rho = (rho.replaceAll("ɪə.ɹ","ɪə˞")).replaceAll("ʊə.ɹ","ʊə˞");
        /*rho = (rho.replaceAll("aɪə.ɹ","aɪə˞")).replaceAll("aʊə.ɹ", "aʊə˞");
        rho = (rho.replaceAll("ɔɪə.ɹ","ɔɪə˞")).replaceAll("oʊə.ɹ", "oʊə˞");
        rho = rho.replaceAll("eɪə.ɹ","eɪə˞");*/
        rho = rho.replaceAll("ʌɹ","ɜ˞");
        rho = rho.replaceAll("ɜɹ","ɜ˞ː");
        rho = rho.replaceAll("]ɹ","]˞");
        return rho;
    }
    public static String diChron (String short1) {
        String long1 = prepSyl(short1);
        for (int k = 0; k < long1.length(); k++) {
            char vow = long1.charAt(k);
            char len;
            if (k < long1.length()-1) {
                len = long1.charAt(k+1);
            }
            else {
                len = ' ';
            }
            if ((Lingua.isFree(vow) && len != '˞' && len != 'ː') || (Lingua.isFree(vow) && len == ' '))  {

                long1 = Util.insertAtIndex(long1,"ː",k+1);
            }
        }
        return unPrepSyl(long1);
    }
    public static String prepIPA (String ide, boolean pros) {
        String id = ide;
        for (int r = 1; r < id.length()-1; r++) {
            if (id.charAt(r) == '.' && (!Lingua.isVowel(id.charAt(r-1)) || !Lingua.isVowel(id.charAt(r+1)) || id.charAt(r-1) == 'ː')) {
                id = Util.removeAtIndex(id,r);
            }
        }
        if (!pros) {
            id = Util.removeAll(Util.removeAll(id,"ˈ"),"ˌ");
        }
        return id;
    }
    public static String prepIAST(String ipa) {
        String iast = ipa.replaceAll("oʊ","o");
        for (int h = 0; h < iast.length()-2; h++) {
            if (iast.charAt(h) == 'ɪ' && iast.charAt(h+1) == '.' && Lingua.isVowel(iast.charAt(h+2))) {
                iast = Util.replaceAtIndex(iast,"j",h,1);
            }
            else if (iast.charAt(h) == 'ʊ' && iast.charAt(h+1) == '.' && Lingua.isVowel(iast.charAt(h+2))) {
                iast = Util.replaceAtIndex(iast,"w",h,1);
            }
        }
        iast = iast.replaceAll("a", "ɑ");
        iast = (((iast.replaceAll("ɑɪə","ɑjɜ")).replaceAll("ɑʊə", "ɑwɜ")).replaceAll("ɔɪə","ɔjɜ")).replaceAll("eɪə", "ejɜ");
        //iast = ((iast.replaceAll("ɛə","ɛ")).replaceAll("ɪə","iː")).replaceAll("ʊə","uː");
        iast = (iast.replaceAll("d͡ʒ","G")).replaceAll("t͡ʃ", "C");
        iast = iast.replaceAll("ŋk", "nk");
        iast = iast.replaceAll("˞", "ɹ");
        iast = Util.removeAll(iast, "(ɹ)");
        iast = Util.removeAll(iast, "ː");
        iast = Util.removeAll(iast, ".");
        if (iast.endsWith("ɪ")) {
            iast = iast.substring(0,iast.length()-1)+"j";
        }
        else if (iast.endsWith("ʊ")) {
            iast = iast.substring(0,iast.length()-1)+"w";
        }
        return iast;
    }
    public static String trimBound (String raw) {
        String prep = raw;
        for (int in = 1; in < prep.length()-1; in++) {
            if (prep.charAt(in) == '.' && prep.charAt(in-1) != 'ː' && Lingua.isVowel(prep.charAt(in-1)) && !Lingua.isFree(prep.charAt(in-1)) && Lingua.isVowel(prep.charAt(in+1))) {
                prep = Util.removeAtIndex(prep, in);
            }
        }
        return prep;
    }
    public static String prepare (String raw) {
        String prep = raw;
        for (int in = 1; in < prep.length()-1; in++) {
            if ((prep.charAt(in) == '\'' || prep.charAt(in) == '’') && (Lingua.isVowel(prep.charAt(in-1)) || Lingua.isConsonant(prep.charAt(in-1))) && (Lingua.isVowel(prep.charAt(in+1)) || Lingua.isConsonant(prep.charAt(in+1)))) {
                prep = Util.replaceAtIndex(prep, "~", in, 1);
            }
            /*else if (prep.charAt(in) == '.' && prep.charAt(in-1) != 'ː' && Lingua.isVowel(prep.charAt(in-1)) && !Lingua.isFree(prep.charAt(in-1)) && Lingua.isVowel(prep.charAt(in+1))) {
                prep = Util.removeAtIndex(prep, in);
            }*/
        }
        prep = (prep.replaceAll("ɑr","ɑ˞")).replaceAll("ɔr","ɔ˞");
        prep = (prep.replaceAll("ɝ","ɜ˞")).replaceAll("ɚ","ə˞");
        prep = ((prep.replaceAll("l̩","əl")).replaceAll("m̩","əm")).replaceAll("n̩","ən");
        prep = (prep.replaceAll("dʒ","d͡ʒ")).replaceAll("tʃ","t͡ʃ");
        prep = (prep.replaceAll("·", ".")).replaceAll("̬", "");
        prep = (prep.replaceAll("ɑɪ","aɪ")).replaceAll("ɑʊ","aʊ");
        return prep;
    }
    public static String laxE (String tensE) {
        String lax = tensE;
        for (int i = 0; i < lax.length(); i++) {
            if (lax.charAt(i) == 'e' && (i == lax.length()-1 || lax.charAt(i+1) != 'ɪ')) {
                lax = Util.replaceAtIndex(lax, "ɛ", i, 1);
            }
        }
        return lax;
    }
    public static String procIndivRaw (String ort, String voc) {
        String results = voc;
        boolean tog = false;
        int nasal = 0;
        for (int res = 0; res < results.length(); res++) {
            char nas = results.charAt(res);
            if (nas == 'n') {
                nasal = res;
                tog = true;
            }
            else if (nas != 'ˈ' && nas != 'ˌ' && nas != '.' && nas != 'ɡ' && nas != 'k') { 
                tog = false;
            }
            else if (tog && (nas == 'ɡ' || nas == 'k')) {
                results = Util.replaceAtIndex(results, "ŋ", nasal, 1);
                tog = false;
            }
        }
        results = results.replaceAll("r","ɹ");
        results = antiClash(results);
        if (results.startsWith(".")) {
            results = results.substring(1);
        }
        String wrtn; 
        if (ort.contains("_")) {
            wrtn = ort.substring(0,ort.lastIndexOf("_"));
        }
        else {
            wrtn = ort;
        }
        String nbspoke = Util.removeAll(voc,".");
        int trk = 6;
        boolean conson = nbspoke.length() >= 5 && (Lingua.isCorSib(nbspoke.charAt(nbspoke.length()-5)) || Lingua.isAlvStop(nbspoke.charAt(nbspoke.length()-5)));
        if (wrtn.endsWith("ically") && nbspoke.endsWith("ɪkəliː") || conson && wrtn.endsWith("ening") && nbspoke.endsWith("ənɪŋ")) {
            voc = Util.removeAtIndex(voc,voc.lastIndexOf("ə"));
            nbspoke = Util.removeAtIndex(nbspoke,nbspoke.lastIndexOf("ə"));
            String ptons = nbspoke.substring(nbspoke.length()-4,nbspoke.length()-2);
            if (!validOnset(ptons) && voc.charAt(voc.length()-4) != '.') {
                voc = Util.insertAtIndex(voc,".",voc.length()-3);
                trk += 1;
            }
            if (voc.length() >= trk) {
                char back2 = voc.charAt(voc.length()-trk);
                char back1 = voc.charAt(voc.length()-trk+1);
                if (Lingua.sonRank(back1) < 3 && Lingua.isBound(back2)) {
                    voc = Util.removeAtIndex(voc,voc.length()-trk);
                }
            }
        }
        return results;
    }
    public static Lemma process (Lemma rough, Dialect accent, boolean syncopate) {
        String britpron = rough.getPronUK();
        String ampron = rough.getPronUS();
        char ctgry = rough.getUsage();
        int rtal = 0;
        britpron = (britpron.replaceAll("tʃ","t͡ʃ")).replaceAll("dʒ", "d͡ʒ");
        britpron = britpron.replaceAll("ɑɪ","aɪ");
        britpron = britpron.replaceAll("ɑʊ","aʊ");
        ampron = rhotVow(ampron);
        ampron = (ampron.replaceAll("tʃ","t͡ʃ")).replaceAll("dʒ", "d͡ʒ");
        ampron = (ampron.replaceAll("ɝː","ɜ˞ː")).replaceAll("ɚ","ə˞");
        ampron = ampron.replaceAll("ɑɪ","aɪ");
        ampron = ampron.replaceAll("ɑʊ","aʊ");
        if (britpron.endsWith("-")) {
            britpron = britpron.substring(0,britpron.length()-1);
            if (ampron.endsWith("-")) {
                ampron = ampron.substring(0,ampron.length()-1);
            }
        }
        int britnm = Lingua.countSyl(britpron);
        int reslnm = Lingua.countSyl(ampron);
        if (britpron.contains("]r") && britnm != reslnm && britnm > 1 && reslnm > 1) {
            String iguales[] = equalize(ampron,britpron);
            britpron = iguales[0];
            ampron = iguales[1];
        }
        if (britpron.contains("]r")) {
            for (int ir = 0; ir < Lingua.countSyl(britpron); ir++) {
                String slbl1 = Lingua.getSyl(britpron, ir, false);
                String slbl2 = Lingua.getSyl(ampron, ir, false);
                if (slbl1.endsWith("]r") && slbl2.endsWith("ə˞")) {
                    ampron = ampron.replace("ə˞","]˞");
                }
            }
        }
        ampron = ampron.replace("]r","]˞");
        String fullbrit = britpron;
        String fullamer = ampron;
        if ((britpron.contains("]") || ampron.contains("]")) && syncopate) {
            String testclip = clip(britpron);
            String britsyn;
            if (testclip.length() < britpron.length()) {
                fullbrit = testclip;
                britsyn = syncope(rough.getSpelling(),testclip,ConvGUI.uk);
            }
            else {
                fullbrit = britpron;
                britsyn = syncope(rough.getSpelling(),britpron,ConvGUI.uk);
            
            }
            fullamer = maxOnset(rhotacize(ampron));
            fullbrit = maxOnset(britpron);
            String amersyn = syncope(rough.getSpelling(),fullamer,ConvGUI.us);
            int redcnt1 = Lingua.countSyl(britsyn);
            int redcnt2 = Lingua.countSyl(amersyn);
            int orgcnt = Lingua.countSyl(britpron);
            if (redcnt1 < orgcnt && redcnt1 < redcnt2) {
                rtal = 15;
            }
            britpron = maxOnset(britsyn);
            ampron = ampron.replaceAll("æɹ","ɛɹ");
            ampron = maxOnset(rhotacize(amersyn));
        }
        else {
            ampron = ampron.replaceAll("]","ə");
            britpron = britpron.replaceAll("]","ə");
            ampron = ampron.replaceAll("æɹ","ɛɹ");
            ampron = maxOnset(rhotacize(ampron));
            britpron = maxOnset(britpron);
            fullamer = ampron;//maxOnset(rhotacize(ampron));
            fullbrit = britpron;//maxOnset(britpron);
        }
        String results1 = britpron;
        String results2 = ampron;
        String results3 = fullbrit;
        String results4 = fullamer;
        boolean cutbrit = !compareSync(results3,results1);
        boolean cutamer = !compareSync(results4,results2);
        char cd;
        //System.out.println(results1+" "+results2+"   "+results3+"  "+results4);
        try {
            results1 = laxE(prepare(britpron));
            results2 = laxE(prepare(ampron));
            results3 = laxE(prepare(fullbrit));
            results4 = laxE(prepare(fullamer));
            String orspl = rough.getSpelling();
            results1 = reconstruct(orspl,results1,false,true,false);
            results2 = reconstruct(orspl,results2,false,true,false);
            results3 = reconstruct(orspl,results3,false,true,false);
            results4 = reconstruct(orspl,results4,false,true,false);
            if (cutbrit) {
                results3 = reconstruct(orspl,results3,false,true,false);
            }
            if (cutamer) {
                results4 = reconstruct(orspl,results4,false,true,false);
            }
            results1 = trimBound(results1);
            results2 = trimBound(results2);
            results3 = trimBound(results3);
            results4 = trimBound(results4);
            //System.out.println("Testing: "+results3+" "+results4);
            if (!results2.equals("|||")) {
                results2 = results2.replaceAll("ɪəɛɹ", "ɪə˞");
                results2 = (results2.replaceAll("əʊ","oʊ")).replaceAll("ɒ","ɑː");
                results2 = dblRhot(diChron(results2));
                if (results2.endsWith("ɪə") && results2.length() > 3 && !Lingua.isVowel(results2.charAt(results2.length()-3))) {
                    results2 = results2.substring(0,results2.length()-2)+"iː.ə";
                }
                else if (results2.endsWith("ʊə") && results2.length() > 3 && !Lingua.isVowel(results2.charAt(results2.length()-3))) {
                    results2 = results2.substring(0,results1.length()-2)+"uː.ə";
                }
                results2 = (results2.replaceAll("ɛəˈɹ","ɛ.ɹ")).replaceAll("ɛəˈɹ", "ɛˈɹ");
                results2 = (results2.replaceAll("ɛə.ɹ","ɛ.ɹ")).replaceAll("ɛəɹ", "ɛɹ");
                if (cutamer) {
                    results4 = results4.replaceAll("ɪəɛɹ", "ɪə˞");
                    results4 = (results4.replaceAll("əʊ","oʊ")).replaceAll("ɒ","ɑː");
                    results4 = dblRhot(diChron(results4));
                    if (results4.endsWith("ɪə") && results4.length() > 3 && !Lingua.isVowel(results4.charAt(results4.length()-3))) {
                        results4 = results4.substring(0,results4.length()-2)+"iː.ə";
                    }
                    else if (results4.endsWith("ʊə") && results4.length() > 3 && !Lingua.isVowel(results4.charAt(results4.length()-3))) {
                        results4 = results4.substring(0,results1.length()-2)+"uː.ə";
                    }
                    results4 = (results4.replaceAll("ɛəˈɹ","ɛ.ɹ")).replaceAll("ɛəˈɹ", "ɛˈɹ");
                    results4 = (results4.replaceAll("ɛə.ɹ","ɛ.ɹ")).replaceAll("ɛəɹ", "ɛɹ");
                }
            }
            if (!results1.equals("|||")) {
                results1 = diChron(deRhot(results1,true));
                results3 = diChron(deRhot(results3,true));              //removed if=cutbrit condition on 7/19/2019
                results4 = unPrepSyl(results4);
            }
            results1 = procIndivRaw(rough.getSpelling(),results1);
            results3 = procIndivRaw(rough.getSpelling(),results3);      //removed if=cutbrit condition on 7/19/2019
            results2 = procIndivRaw(rough.getSpelling(),results2);
            results4 = procIndivRaw(rough.getSpelling(),results4);      //removed if=amer condition on 7/19/2019
        }
        catch (Exception whatever) {
            results1 = "|||";
            results2 = "|||";
            cd = Lemma.encode("00");
        }
        Lemma refined = new Lemma(rough.getSpelling(),results1, results2,ctgry,rtal);
        /*if (syncopate) {
            String back1 = rough.getPronUK();
            String back2 = rough.getPronUS();
            if (back1.contains("]") || back2.contains("]")) {
                Lemma nosync = Phonology.process(rough, accent, false);
                refined.setUnsyncUK(nosync.getPronUK());
                refined.setUnsyncUS(nosync.getPronUS());
            }
        }*/
        refined.setUnsyncUK(results3);      //removed if=cutbrit condition on 7/19/2019
        refined.setUnsyncUS(results4);      //removed if=cutamer condition on 7/19/2019
        return refined;
    }
    public List<Lemma> discrim (String wrd, List<Lemma> indiscr, Dialect dlct, int sys) {
        List<Lemma> discr = Lemma.pack(wrd);
        boolean exclude = false;
        if (DictReader.dotted) {
            List<Lemma> nwsrc = morphology.parseLemma(wrd,discr,dlct,true,true,true,true);
            if (!nwsrc.isEmpty()) {
                discr = nwsrc;
                exclude = true;
            }
            else {
                discr = indiscr;
            }
            DictReader.dotted = false;
            DictReader.export = true;
        }
        else {
            discr = indiscr;
        }
        if (sys == 5) {
            discr = TranKS.reconWH(morphology.adjMorph(wrd,discr,dlct,exclude));
        }
        return discr;
    }
    public static String adjUS(String mal) {
        String rev = mal;
        try {
            char anteterm = mal.charAt(mal.length()-3);
            char antepret = mal.charAt(mal.length()-4);
            if (mal.endsWith("isation")) {
                rev = mal.substring(0,mal.length()-6)+"zation";
            }
            else if (mal.endsWith("isate")) {
                rev = mal.substring(0,mal.length()-4)+"zate";
            }
            else if (mal.endsWith("ourite")) {
                rev = mal.substring(0,mal.length()-6)+"orite";
            }
            else if (mal.endsWith("ence")) {
                rev = mal.substring(0,mal.length()-2)+"se";
            }
            else if (mal.endsWith("amme")) {
                rev = mal.substring(0,mal.length()-2);
            }
            else if (Lingua.isConsonant(antepret) && mal.endsWith("our")) {
                rev = mal.substring(0,mal.length()-3)+"or";
            }
            else if (mal.endsWith("ise")) {
                rev = mal.substring(0,mal.length()-2)+"ze";
            }
            else if (mal.endsWith("gue")) {
                rev = mal.substring(0,mal.length()-2);
            }
            else if (mal.endsWith("que")) {
                rev = mal.substring(0,mal.length()-3)+"ck";
            }
            else if (Lingua.isConsonant(anteterm) && mal.endsWith("re")) {
                rev = mal.substring(0,mal.length()-2)+"er";
            }
            else {
                for (int k = 1; k < mal.length(); k++) {
                    char now = mal.charAt(k);
                    char bef = mal.charAt(k-1);
                    if (now == 'e' && (bef == 'a' || bef == 'o')) {
                        String tmp1 = Util.removeAtIndex(mal,k-1);
                        if (DictReader.isWord(tmp1,"GA",false)) {
                            rev = tmp1;
                        }
                    }
                }
            }
            if (!rev.equals(mal) && DictReader.isWord(rev,"GA",false)) {
                return rev;
            }
            else {
                return mal;
            }
        }
        catch (StringIndexOutOfBoundsException none) {
            return mal;
        }
    }    
    public static String adjUK(String mal) {
        String rev = mal;
        try {
            char anteterm = mal.charAt(mal.length()-3);
            char antepret = mal.charAt(mal.length()-4);
            if (mal.endsWith("ization")) {
                rev = mal.substring(0,mal.length()-6)+"sation";
            }
            else if (mal.endsWith("izate")) {
                rev = mal.substring(0,mal.length()-4)+"sate";
            }
            else if (mal.endsWith("orite")) {
                rev = mal.substring(0,mal.length()-4)+"urite";
            }
            else if (mal.endsWith("ense")) {
                rev = mal.substring(0,mal.length()-2)+"ce";
            }
            else if (mal.endsWith("am")) {
                rev = mal+"me";
            }
            else if (Lingua.isConsonant(anteterm) && mal.endsWith("or")) {
                rev = mal.substring(0,mal.length()-1)+"ur";
            }
            else if (mal.endsWith("ize")) {
                rev = mal.substring(0,mal.length()-2)+"se";
            }
            else if (mal.endsWith("g")) {
                rev = mal+"ue";
            }
            else if (mal.endsWith("ck")) {
                rev = mal.substring(0,mal.length()-2)+"que";
            }
            else if (Lingua.isConsonant(anteterm) && mal.endsWith("er")) {
                rev = mal.substring(0,mal.length()-2)+"re";
            }
            else {
                for (int k = 1; k < mal.length(); k++) {
                    char now = mal.charAt(k);
                    if (now == 'e') {
                        String tmp1 = Util.insertAtIndex(mal,"a",k);
                        String tmp2 = Util.insertAtIndex(mal,"o",k);
                        if (DictReader.isWord(tmp1,"RP",false)) {
                            rev = tmp1;
                        }
                        else if (DictReader.isWord(tmp2,"RP",false)) {
                            rev = tmp2;
                        }
                    }
                }
            }
            if (!rev.equals(mal) && DictReader.isWord(rev,"RP",false)) {
                return rev;
            }
            else {
                return mal;
            }
        }
        catch (StringIndexOutOfBoundsException none) {
            return mal;
        }
    }
    public List<Lemma> store = new ArrayList<>();
    public List<Lemma> resultList (String txt, Dialect dia, int syst) throws IndexOutOfBoundsException {
        String text = trimBound(prepare(txt));
        List<Lemma> search = Lemma.pack(text);
        org = text;
        morphology.orgform = text;
        search = discrim(text,morphology.parseLemma(text,search,dia,true,true,true,true),dia,syst);
        //System.out.println(Lemma.multiPrint(search));
        search = Lemma.match(org,search);
        boolean nosuch = search.isEmpty();
        if (nosuch) {
            String britadj = adjUS(text);
            search.clear();
            if (DictReader.isWord(britadj,dia.getName(),false)) {
                search = Lemma.pack(britadj);
                org = britadj;
                search = discrim(text,morphology.parseLemma(britadj,search,dia,true,true,true,false),dia,syst);
            }
            else {
                String amadj = adjUK(text);
                search.clear();
                if (DictReader.isWord(amadj,dia.getName(),false)) {
                    search = Lemma.pack(amadj);
                    org = amadj;
                    search = discrim(text,morphology.parseLemma(amadj,search,dia,true,true,true,false),dia,syst);
                }
            }
        }
        /*ListIterator<Lemma> prints = search.listIterator();
        while (prints.hasNext()) {
            Lemma print = prints.next();
            String parms = print.printParms();
            Util.updateProg(parms.substring(1),60);
        }*/
        return search;
    }
    public static Map<String,String> whlspts = new TreeMap<>();
    public static Map<String,String> baseX = new TreeMap<>();
    public String extrPron (Lemma source, Dialect dia, boolean whole, int sysm) {
        String pho;
        Lemma fount = Lemma.copyLemma(source);
        if ((fount.getPronUK()).contains("`") && (fount.getPronUS()).contains("`") && sysm == 0) {
            fount.setPronUK(fount.getUnsyncUK());
            fount.setPronUS(fount.getUnsyncUS());
        }
        if ((dia.getName()).equals("RP")) {
            if (whole) {
                pho = deRhot(fount.getPronUK(),true);
            }
            else {
                pho = deRhot(fount.getAllomorph1(),true);
            }
            /*if (pho.endsWith("ɹ")) {
                pho = pho.substring(0,pho.length()-1)+"r";
            }*/
        }
        else if ((dia.getName()).equals("GA")) {
            if (whole) {
                pho = fount.getPronUS();
            }
            else {
                pho = fount.getAllomorph2();
            }
        }
        else {
            if (whole) {
                pho = hybridize(fount,dia);
            }
            else {
                pho = hybridize(fount,dia);
            }
        }
        if (Lingua.countSyl(pho) == 1) {
            pho = Util.removeAll(pho,"ˈ");
        }
        if (dia.getClip()) {
            pho = clip(pho);
        }
        else if (dia.getRhotic()) {
            pho = dblRhot(pho);
        }
        if (Lingua.countSyl(pho) > 1 && !pho.contains("ˈ")) {
            pho = "ˈ"+pho;
        }
        pho = pho.replaceAll("]","ə");
        boolean intExempt = false;
        if (source.structured()) {
            Lemma bottom = fount.getDghtr1();
            Lemma top = fount.getDghtr2();
            String basepho = extrPron(bottom,dia,false,sysm);
            if (basepho.equals("|||")) {
                basepho = extrPron(bottom,dia,true,sysm);
            }
            String addpho = extrPron(top,dia,whole,sysm);
            if (addpho.equals("|||")) {
                addpho = extrPron(top,dia,true,sysm);
            }
            if (bottom.getExempt() && !top.getExempt()) {
                //pho = pho.replaceAll(basepho,"*"+basepho+"*");
                String partkey = basepho.substring(1);
                baseX.put(partkey,bottom.getSpelling());
                whlspts.put(pho,partkey);
                intExempt = true;
            }
            else if (!bottom.getExempt() && top.getExempt()) {
                //pho = pho.replaceAll(addpho,"*"+addpho+"*");
                String partkey = addpho.substring(1);
                baseX.put(partkey,top.getSpelling());
                whlspts.put(pho,partkey);
                intExempt = true;
            }
            else if (bottom.getExempt() && top.getExempt()) {
                //pho = "*"+pho+"*";
                baseX.put(pho,fount.getSpelling());
                whlspts.put(pho,pho);
                intExempt = true;
            }
        }
        if (source.getExempt() && !intExempt) {
            baseX.put(pho,fount.getSpelling());
            whlspts.put(pho,pho);
            pho = "*"+pho;
        }
        else if (intExempt) {
            pho = "*"+Util.removeAll(pho,"*");
        }
        return pho;
    }
    public List<String> extrPron (String txt, List<Lemma> look, Dialect dia, int sysm) throws IndexOutOfBoundsException {
        List<Lemma> storage = new ArrayList<>(); 
        List<String> prons = new ArrayList<>();
        presinfl.clear();
        //System.out.println(look.size());
        for (int v = 0; v < look.size(); v++) {
            Lemma source = look.get(v);
            String pho;
            if ((source.getPronUK()).equals("|||")) {
                continue;
            }
            else {
                pho = extrPron(source,dia,true,sysm);
            }
            //boolean irregpl = ((source.getSpelling()).endsWith("f+s") || (source.getSpelling()).endsWith("fe+s")) && (Convert17.org).endsWith("ves"); 
            if (!prons.contains(pho) && !pho.equals("|||")) {// && !irregpl && (source.getUsage() != 'F' || !contLevel(pho,prons))) {
                prons.add(pho);
                source.setSpelling(txt);
                storage.add(source);
            }
        }
        //dict.store(storage);
        Morphology.initialize();
        org = "";
        return prons;
    }
    public static String printProns (List<String> info, boolean pros) {
        String list = "";
        List<String> data = info;
        if (info.size() == 1) {
            list = data.get(0);
        }
        else if (!pros && info.size() > 1) {
            List<String> nostr = new ArrayList<>();
            for (int f = 0; f < info.size(); f++) {
                String dstr = Phonology.prepIPA(info.get(f),pros);
                if (!nostr.contains(dstr)) {
                    nostr.add(dstr);
                }
            }
            data = nostr;
        }
        if (data.size() > 1) {
            list = "|";
            for (int f = 0; f < data.size(); f++) {
                list += data.get(f)+"|";
            }
        }
        else {
            list = data.get(0);
        }
        return list;
    }
    /*public String finalPron (String beg, Dialect dia) {
        String phono = beg;
        if (dia.equals("RP")) {
            phono = clip(phono);
        }
        else {
            phono = dblRhot(phono);
        }
        if (Lingua.countSyl(phono) > 1 && !phono.contains("ˈ")) {
            if (phono.startsWith("*")) {
                phono = "*ˈ"+phono.substring(1);
            }
            else {
                phono = "ˈ"+phono;
            }
        }         
        return phono;  
    }*/
    public List<String> finalPron (String verbum, List<Lemma> args, Dialect dia, int sysm) {
        List<String> phons = new ArrayList<>();
        String txt;
        if (!DictReader.isWord(verbum,dia.getName(),false)) {
            txt = adjUS(verbum);
        }
        else {
            txt = verbum;
        }
        org = txt;
        phons = extrPron(verbum,args,dia,sysm);
        List<String> finpron = new ArrayList<>();
        for (int h = 0; h < phons.size(); h++) { 
            String phono = phons.get(h);
            if (!finpron.contains(phono)) {
                String backpho = phono;
                //phono = finalPron(phono,dia);
                //System.out.println(phono);
                finpron.add(phono);
            }
        }
        return finpron;
    }
}
