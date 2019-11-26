package convert17;
import java.util.*;
import java.util.TreeMap;
public class Util {
    /*public static void updateProg (String prog, int wait) {
        ((Monitor.newwin).log).setText(prog);
        (Monitor.newwin).paint((Monitor.newwin).getGraphics());
        try {
            Thread.sleep(wait);
        }
        catch (InterruptedException interrupt) {
            //do nothing
        }
    }*/
    public static List<Lemma> append (List<Lemma> list1, List<Lemma> list2) {
        List<Lemma> cmblst = list1;
        ListIterator<Lemma> itrlst = list2.listIterator();
        while (itrlst.hasNext()) {
            cmblst.add(itrlst.next());
        }
        return cmblst;
    }
    public static List<Lemma> copyList (List<Lemma> targ) {
        List<Lemma> copy = new ArrayList<>();
        for (int i = 0; i < targ.size(); i++) {
            Lemma cop = targ.get(i);
            copy.add(Lemma.copyLemma(cop));
        }
        return copy;
    }
    /*public static List<Lemma> orgHoms (List<Lemma> disorg) {
        List<Lemma> organ = new ArrayList<>();
        List<Lemma> disorgan = disorg;
        for (int k = 0; k < disorgan.size(); k++) {
            Lemma hold = disorgan.get(k);
            organ.add(hold);
            String holdspell = hold.getSpelling();
            for (int l = k+1; l < disorgan.size(); l++) {
                Lemma pmatch = disorg.get(l);
                String pmspell = pmatch.getSpelling();
                if (pmspell.equals(holdspell)) {
                    organ.add(pmatch);
                    disorg.remove(l);
                    l--;
                }
            }
        }
        return organ;
    }*/
    public static List<Lemma> findInList (String prob, List<Lemma> pool) {
        List<Lemma> rez = new ArrayList<>();
        ListIterator poolit = pool.listIterator();
        while (poolit.hasNext()) {
            Lemma drop = (Lemma)poolit.next();
            String surf1 = drop.getSpelling();
            String surf2 = drop.getAltSpl();
            if (surf1.equals(prob) || surf2.equals(prob)) {
                rez.add(drop);
            }
        }
        return rez;
    }
    public static boolean listContains (String prob, List<Lemma> pool) {
        return !(findInList(prob,pool)).isEmpty();
    }
    public static TreeMap<String,Lemma> encapsulate (List<Lemma> pool) {
        TreeMap<String,Lemma> encap = new TreeMap<>();
        ListIterator poolit = pool.listIterator();
        while (poolit.hasNext()) {
            Lemma drop = (Lemma)poolit.next();
            encap.put(drop.getSpelling(),drop);
        }
        return encap;
    }
    public static List<Lemma> expand (List<Lemma> cond) {
        List<Lemma> xpnd = cond;
        for (int c = 0; c < cond.size(); c++) {
            Lemma struc = cond.get(c);
            if (struc.structured()) {
                Lemma dohtor1 = struc.getDghtr1();
                List<Lemma> subits = expand(Lemma.pack(dohtor1));
                subits.add(struc.getDghtr2());
                xpnd = append(xpnd,subits);
            }
        }
        return xpnd;
    }
    public static boolean isFilled (List<String> data) {
        if (data.isEmpty() || data.size() == 1 && ((data.get(0)).equals("|||") || (data.get(0)).equals(""))) {
            return false;
        }
        else {
            return true;
        }
    }
    public static List<String> combine (List<String> list1, List<String> list2) {
        List<String> cmblst = list1;
        ListIterator<String> itrlst = list2.listIterator();
        while (itrlst.hasNext()) {
            cmblst.add(itrlst.next());
        }
        return cmblst;
    }
    public static boolean containsEnding (String termin, List<Lemma> dtbs) {
        boolean hasIt = false;
        ListIterator<Lemma> trouv = dtbs.listIterator();
        while (trouv.hasNext()) {
            Lemma cmpr = trouv.next();
            if (termin.endsWith(cmpr.getSpelling())) {
                hasIt = true;
                break;
            }
        }
        return hasIt;
    }
    public static boolean containsEnd (String termin, List<String> dtbs) {
        boolean hasIt = false;
        ListIterator<String> trouv = dtbs.listIterator();
        while (trouv.hasNext()) {
            String cmpr = trouv.next();
            if (termin.endsWith(cmpr)) {
                hasIt = true;
                break;
            }
        }
        return hasIt;
    }
    public static String swapSeg (String swap, int src, int dest) {
        String swapped;
        if (dest > src) {
            try {
                String begin = swap.substring(0,src);
                String mid = swap.substring(src+1,dest);
                String end = swap.substring(dest+1);
                String source = Character.toString(swap.charAt(src));
                String destin = Character.toString(swap.charAt(dest));
                swapped = begin+destin+mid+source+end;
            }
            catch (Exception toobig) {
                swapped = swap;
            }
        }
        else {
            swapped = swap;
        }
        return swapped;
    }
    public static String shiftSeg (String shift, int from, int steps, char dir) {
        String shifted;
        try {
            if (dir == 'r') {
                shifted = swapSeg(shift,from,from+steps);
            }
            else if (dir == 'l') {
                shifted = swapSeg(shift,from-steps,from);
            }
            else {
                shifted = shift;
            }
        }
        catch (Exception indexprob) {
            shifted = "";
        }
        return shifted;
    }
    public static String insertAtIndex (String old, String ins, int wh) { 
        String new1 = old.substring(0,wh)+ins+old.substring(wh);
        return new1;
    }
    public static String removeAll (String suj, String rem) {
        int ix = 0;
        String cut = suj;
        while (ix < cut.length()) { 
            String sec = cut.substring(ix);
            if (sec.startsWith(rem)) {
                cut = cut.substring(0,ix)+cut.substring(ix+rem.length());
                ix += rem.length();
            }
            else {
                ix++;
            }
        }
        return cut;
    }
    public static String removeAtIndex (String del, int w) {
        String red;
        if (w < del.length()-1) {
            red = del.substring(0,w)+del.substring(w+1);
        }
        else {
            red = del.substring(0, del.length()-1);
        }
        return red;
    }
    public static String replaceAtIndex (String subj, String subst, int loc, int dist) {
        String nov;
        try {
            nov = subj.substring(0,loc)+subst+subj.substring(loc+dist);
        }
        catch (StringIndexOutOfBoundsException blip) {
            nov = subj.substring(0,loc)+subst;
        }
        return nov;
    }
    public static boolean isAllCaps(String cap) {
        boolean allcaps = true;
        for (int ij = 0; ij < cap.length(); ij++) {
            char ji = cap.charAt(ij);
            if (Character.isLetter(ji) && Character.isLowerCase(ji)) {
                allcaps = false;
            }
        }
        return allcaps;
    }
    public static boolean isAbbrev(String cap) {
        int capcnt = 0;
        int ncpcnt = cap.length();
        for (int ip = 0; ip < cap.length(); ip++) {
            if (Character.isUpperCase(cap.charAt(ip))) {
                capcnt ++;
                ncpcnt --;
            }
        }
        return capcnt > ncpcnt || cap.charAt(cap.length()-1) == '.';
    }
    public static String Capital (String txt, int pl) throws StringIndexOutOfBoundsException {
        String capt = txt;
        int set;
        if (capt.startsWith("r'")) {
            set = Math.max(txt.indexOf("'"),pl);
        }
        else {
            set = pl;
        }
        for (int pi = set; pi < txt.length(); pi++) {
            char pit = txt.charAt(pi);
            if (Character.isLetterOrDigit(pit)) {
                capt = Util.replaceAtIndex(txt,Character.toString(Character.toUpperCase(pit)),pi,1);
                break;
            }
        }
        return capt;
    }
    public static String convert(String in, Map<String,String> corr) {
        String conv = "";
        for (int s = 0; s < in.length(); s++) {
            String probe = Character.toString(in.charAt(s));
            if (corr.containsKey(probe)) {
                String tar = Character.toString(in.charAt(s));
                conv += corr.get(tar);
            }
            else {
                conv += Character.toString(in.charAt(s));
            }
        }
        return conv;
    }
    public static boolean isQuote (char mark) {
        return mark == '"' || mark == '“' || mark == '”' || mark == '‘' || mark == '’' || mark == '\'';
    }
    public static boolean isNotLower (char mark) {
        return !isSpace(mark) && !isPunct(mark) && (Character.isUpperCase(mark) || Character.isDigit(mark));
    }
    public static boolean isTermPunct (char mark) {
        if (mark == '.' || mark == '?' || mark == '!') {
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean isMedPunct (char mark) {
        if (mark == ',' || mark == ':' || mark == ';' || mark == '-' || mark == '–' || mark == 'ǂ') {
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean isPunct (char mark) {
        return isTermPunct(mark) || isMedPunct(mark);
    }
    public static boolean isSpace (char mark) {
        return mark == ' ' || mark == '\n' || mark == '\t';
    }
    public static boolean isTitle (String mark) {
        return mark.equals("Mr.") || mark.equals("Mrs.") || mark.equals("Ms.") || mark.equals("Dr.");
    }
    /*public static boolean isHonor (String mark) {
        return mark.equals("Jr.") ||  mark.equals("Sr.");
    }*/
    public static int lastAlphaNum (String mat, int limit) {
        int loc = limit;
        for (int w = limit; w >= 0; w--) {
            char wt = mat.charAt(w);
            if (isSpace(wt) || isPunct(wt) || isQuote(wt) || wt == '~' || wt == '`') {
                loc--;
            }
            else {
                break;
            }
        }
        return loc;
    }
    public static int firstAlphaNum (String mat, int limit) {
        int loc = limit;
        for (int w = limit; w < mat.length(); w++) {
            char wt = mat.charAt(w);
            if (isSpace(wt) || isPunct(wt) || isQuote(wt) || wt == '~' || wt == '`') {
                loc++;
            }
            else {
                break;
            }
        }
        return loc;
    }
    public static int opening (String mat) {
        int loc = 0;
        for (int w = 0; w < mat.length(); w++) {
            char wt = mat.charAt(w);
            if (Util.isQuote(wt) || wt == '(' || wt == '~') {
                loc++;
            }
            else {
                break;
            }
        }
        return loc;
    }
    public static int closing (String mat) {
        int loc = mat.length()-1;
        for (int w = loc; w >= 0; w--) {
            char wt = mat.charAt(w);
            if (Util.isQuote(wt) || wt == ')' || wt == '~') {
                loc--;
            }
            else {
                break;
            }
        }
        return loc;
    }
    public static int lastNonSpace (String mat, int limit) {
        int loc = limit;
        for (int w = limit; w >= 0; w--) {
            char wt = mat.charAt(w);
            if (isSpace(wt)) {
                loc--;
            }
            else {
                break;
            }
        }
        return loc;
    }
    public static int firstNonSpace (String mat, int limit) {
        int loc = limit;
        for (int w = limit; w < mat.length(); w++) {
            char wt = mat.charAt(w);
            if (Util.isSpace(wt)) {
                loc++;
            }
            else {
                break;
            }
        }
        return loc;
    }
    public static int lastSpaceOrPunct (String mat, int limit) {
        int loc = limit;
        for (int w = limit; w >= 0; w--) {
            char wt = mat.charAt(w);
            if (!isSpace(wt) && !isPunct(wt)) {
                loc--;
            }
            else {
                break;
            }
        }
        return loc;
    }
    public static int firstSpaceOrPunct (String mat, int limit) {
        int loc = limit;
        for (int w = limit; w < mat.length(); w++) {
            char wt = mat.charAt(w);
            if (!isSpace(wt) && ! isPunct(wt)) {
                loc++;
            }
            else {
                break;
            }
        }
        return loc;
    }
    public static int lastSpace (String mat, int limit) {
        int loc = limit;
        for (int w = limit; w >= 0; w--) {
            char wt = mat.charAt(w);
            if (!isSpace(wt)) {
                loc--;
            }
            else {
                break;
            }
        }
        return loc;
    }
    public static int firstSpace (String mat, int limit) {
        int loc = limit;
        for (int w = limit; w < mat.length(); w++) {
            char wt = mat.charAt(w);
            if (!isSpace(wt)) {
                loc++;
            }
            else {
                break;
            }
        }
        return loc;
    }
    public static List<String> pullText (String code, String tag) {
        List<String> inner = new ArrayList<>();
        for (int i = 0; i < code.length(); i++) {
            char crch = code.charAt(i);
            boolean ntnd = i < code.length()-1;
            if (ntnd && crch == '<') {
                int open = code.indexOf(">",i);
                String name = code.substring(i+1,open);
                int close = code.indexOf("</"+name,i);
                if (name.equals(tag)) {
                    String ore = (code.substring(open+1,close)).trim();
                    inner.add(ore);
                }
                i = close+name.length()+2;
            }
        }
        return inner;
    }
    public static String removeMarks(String diac) {
        String edlab = ((diac.replaceAll("é","e")).replaceAll("ç","c")).replaceAll("ñ","n");
        edlab = ((edlab.replaceAll("â","a")).replaceAll("ê","e")).replaceAll("ô","o");
        return edlab;
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
    public static List<String> pack (String incept) {
        List<String> pckg = new ArrayList<>();
        pckg.add(incept);
        return pckg;
    }
    public static List<String> trimDup (List<String> dup) {
        List<String> trim = new ArrayList<>();
        ListIterator<String> dups = dup.listIterator();
        while (dups.hasNext()) {
            String dpst = dups.next();
            boolean duplicate = false;
            ListIterator<String> iter = trim.listIterator();
            while (iter.hasNext()) {
                String nwst = iter.next();
                if (nwst.equals(dpst)) {
                    duplicate = true;
                }
            }
            if (!duplicate) {
                trim.add(dpst);
            }
        }
        return trim;
    }
}
