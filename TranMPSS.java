package convert17;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TranMPSS {
    public static boolean isVowel (char letter) {
        if (letter == 'ʌ' || letter == 'ɛ' || letter == 'ɪ' || letter == 'ɒ' || letter == 'ʊ' || letter == 'ɑ' || letter == 'ɜ' || letter == 'i' || letter == 'ɔ' || letter == 'u' || letter == 'æ' || letter == 'e' || letter == 'ə' || letter == 'o' || letter == 'B' || letter == 'C' || letter == 'D' || letter == 'F' || letter == 'H' ||  letter == 'W' || letter == 'Y'  || letter == 'Z' || letter == 'a' || letter == 'á' || letter == 'à' || letter == 'ä' || letter == 'é' || letter == 'è' || letter == 'í' || letter == 'ì' || letter == 'ó' || letter == 'ò' || letter == 'ö' || letter == 'ø' || letter == 'u' || letter == 'ú' || letter == 'ù' || letter == 'ü') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isConsonant (char letter) {
        if (isVowel(letter) == false && Character.isLetter(letter) || letter == 'ʃ' || letter == 'ʒ' || letter == 'θ' || letter == 'ð' || letter == 'ŋ' || letter == '˞' || letter == 'ɹ' || letter == 'ʔ' || letter == 'ç' || letter == 'ñ' || letter == 'þ') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static int toNextVowel (String gp, int start) {
        String sbj = Util.removeAll(gp, "^");
        int go = start+1;
        int dist = 0;
        while (go < sbj.length()) {
            if (isVowel(sbj.charAt(go)) == true) {
                break;
            }
            else if (sbj.charAt(go) != 'ˌ' && sbj.charAt(go) != 'ˈ' && sbj.charAt(go) != 'ː') {
                dist++;
            }
            go++;
        }
        if (dist == 1 && go == sbj.length()) {
            dist = 2;
        }
        return dist;
    }
    public static Map <Character, String> acutes = new HashMap<>();
    public static void Diacritics () {
        acutes.put('a', "á");
        acutes.put('e', "é");
        acutes.put('i', "í");
        acutes.put('o', "ó");
        acutes.put('u', "ú");
        acutes.put('ù', "û");
        acutes.put('ü',"ǘ");
    }
    public static Map <Character, String[]> diphthongs = new HashMap<>();
    public static Map <Character, String> digraphs = new HashMap<>(); 
    public static void Diphthongs () {
        String[] diph1 = {"ai", "ay"};
        String[] diph2 = {"au", "aw"};
        String[] diph3 = {"ei", "ey"};
        String[] diph4 = {"oi", "oy"};
        String[] diph5 = {"ou", "ow"};
        String[] diph6 = {"ea", "ea"};
        String[] diph7 = {"ia", "ia"};
        String[] diph8 = {"ua", "ua"};
        diphthongs.put('B', diph1);
        diphthongs.put('C', diph2);
        diphthongs.put('D', diph3);
        diphthongs.put('F', diph4);
        diphthongs.put('H', diph5);
        diphthongs.put('T', diph6);
        diphthongs.put('V', diph7);
        diphthongs.put('W', diph8);
    }
    public static void Doublings () {
        digraphs.put('ɑ', "aa");
        digraphs.put('ɜ', "oe");
        digraphs.put('i', "iy");
        digraphs.put('ɔ', "oo");
        digraphs.put('u', "uu");
        digraphs.put('Y', "üu");
    }
    public static String Prepare (String logos) {
        String prep = Util.removeAll(logos, "(ɹ)");
        /*for (int in = 1; in < prep.length()-1; in++) {
            if (prep.charAt(in) == '.' && (isVowel(prep.charAt(in-1)) || isConsonant(prep.charAt(in-1))) && (isVowel(prep.charAt(in+1)) || isConsonant(prep.charAt(in+1)))) {
                prep = removeAtIndex(prep, in);
            }
            else if (prep.charAt(in) == '\'' && (isVowel(prep.charAt(in-1)) || isConsonant(prep.charAt(in-1))) && (isVowel(prep.charAt(in+1)) || isConsonant(prep.charAt(in+1)))) {
                prep = replaceAtIndex(prep, "~", in);
            }
        }*/
        prep = prep.toLowerCase();
        prep = Util.removeAll(prep,".");
        prep = prep.replaceAll("ɑə˞", "ɑɹ");
        prep = prep.replaceAll("ɔə˞", "ɔɹ");
        prep = prep.replaceAll("ɑɹ", "ɑ˞ː");
        prep = prep.replaceAll("ɔɹ", "ɔ˞ː");
        prep = prep.replaceAll("ɚ", "ə˞");
        prep = prep.replaceAll("ɝ", "ɜ˞ː");
        prep = prep.replaceAll("ɨ", "ə");
        prep = prep.replaceAll("ɹ̩", "ə˞");
        prep = prep.replaceAll("l̩", "əl");
        prep = prep.replaceAll("m̩", "əm");
        prep = prep.replaceAll("n̩", "ən");
        prep = prep.replaceAll("ɐ", "ʌ");
        prep = prep.replaceAll("ɑj", "aɪ");
        prep = prep.replaceAll("ɑw", "aʊ");
        prep = prep.replaceAll("ej", "eɪ");
        prep = prep.replaceAll("eː", "eɪ");
        prep = prep.replaceAll("ɛɪ", "eɪ");
        prep = prep.replaceAll("ɔj", "ɔɪ");
        prep = prep.replaceAll("ow", "oʊ");
        prep = prep.replaceAll("oː", "oʊ");
        prep = prep.replaceAll("əʊ", "oʊ");
	prep = prep.replaceAll("aɪ", "B");
        prep = prep.replaceAll("aʊ", "C");
        prep = prep.replaceAll("eɪ", "D");
        prep = prep.replaceAll("ɔɪ", "F");
        prep = prep.replaceAll("oʊ", "H");
        prep = prep.replaceAll("ʊə","W");
        prep = prep.replaceAll("juː","Y");
        prep = prep.replaceAll("jʊ","Z");
        prep = prep.replaceAll("ɡ","g");
        prep = prep.replaceAll ("t͡ʃ", "Q");
	prep = prep.replaceAll("d͡ʒ", "G");
        prep = prep.replaceAll ("ʧ", "Q");
	prep = prep.replaceAll("ʤ", "G");
	prep = prep.replaceAll("ʍ", "hw");
        prep = Util.removeAll(prep, "ː");
        return prep;
    }
    public static int countSchwa (String su) {
        String subj = prepSyl(su);
        int schwa = 0;
        for (int c = 0; c < subj.length(); c++) {
            char ahora = subj.charAt(c);
            char antes = '#';
            if (c > 0) {
                antes = subj.charAt(c-1);
            }
            if ((ahora == 'ə' || ahora == 'ø') && antes != 'ɛ' && antes != 'ɪ' && antes != 'ʊ' && antes != 'è' && antes != 'ì' && antes != 'ù') {
                schwa += 1;
            }
        }
        return schwa;
    }
    public static String prepSyl (String unsylb) {
        String pre = (unsylb.replaceAll("t͡ʃ","C")).replaceAll("d͡ʒ", "G");
        pre = pre.replaceAll("ai","1");
        pre = pre.replaceAll("au","2");
        pre = pre.replaceAll("ei","3");
        pre = pre.replaceAll("oi","4");
        pre = pre.replaceAll("ou","5");
        return pre;
    }
    public static int countSyl (String count) {
        String ready = prepSyl(count);
        int co = 0;
        for (int j = 0; j < ready.length(); j++) {
            char ceci = ready.charAt(j); 
            if (isVowel(ceci) || Character.isDigit(ceci)) {
                co += 1;
            }
        }
        return co;
    }
    public static boolean isDigraph (String digr) {
        return digr.equals("ae") || digr.equals("aa") || digr.equals("ai") || digr.equals("ay") || digr.equals("au") || digr.equals("aw") || digr.equals("ee") || digr.equals("ei") || digr.equals("ey") || digr.equals("ea") || digr.equals("iy") || digr.equals("ia") || digr.equals("oe") || digr.equals("oo") || digr.equals("oi") || digr.equals("oy") || digr.equals("ou") || digr.equals("ow") || digr.equals("ua") || digr.equals("uu") || digr.equals("üu");
    }
    public static boolean isDiphthong (String digr) {
        return digr.equals("ai") || digr.equals("ay") || digr.equals("au") || digr.equals("aw") || digr.equals("ei") || digr.equals("ey") || digr.equals("ea") || digr.equals("ia") || digr.equals("oi") || digr.equals("oy") || digr.equals("ou") || digr.equals("ow") || digr.equals("ua");
    }
    public static String MapStress (String stressed) {
        String marked = stressed;
        if (!stressed.contains("ˈ")) {
            marked = "ˈ"+stressed;
        }
        else {
            marked = stressed;
        }
        boolean stress = false;
        String prim = "";
        int highest = 0;
        int highind = 0;
        int prank = 0;
        int pou = 0;
        char org;
        for (int flex = 0; flex < marked.length(); flex++) {
            org = marked.charAt(flex);
            int sync = flex;
            int rank = 0;
            if (org == 'ˈ') {
                stress = true;
            }
            else {
                boolean digraph = isVowel(org) && flex < marked.length()-1 && isDigraph(Character.toString(org)+Character.toString(marked.charAt(flex+1)));
                boolean diphthong = isVowel(org) && flex < marked.length()-1 && isDiphthong(Character.toString(org)+Character.toString(marked.charAt(flex+1)));
                boolean trmnl = flex == marked.length()-1;
                if (org == 'ü') {// && (trmnl || toNextVowel(marked,flex) == 0)) {
                    rank = 5;
                    /*if (!trmnl && marked.charAt(flex+1) == 'u') {
                        sync += 1;
                    }*/
                }
                else if (diphthong) {
                    rank = 4;
                    sync += 1;
                }
                else if (digraph) {
                    rank = 3;
                    sync += 1;
                }
                else if (isVowel(org) && org != 'a') {
                    rank = 2;
                }
                else if (org == 'a') {
                    rank = 1;
                }
                else {
                    rank = 0;
                }
                if (isVowel(org) && (rank > highest || rank < 5 && rank > 2 && rank == highest)) {
                    highest = rank;
                    highind = flex;
                }
                if (isVowel(org) && stress) {
                    pou = flex;
                    prank = rank;
                    stress = false;
                }
                flex = sync;
            }
        }
        char cand = marked.charAt(pou);
        boolean outord;
        if (highest < 5 && highest > 2) {
            outord = pou < highind;
        }
        else {
            outord = pou > highind;
        }
        if (isVowel(cand) && (prank < highest || prank == highest && outord)) {
            prim = acutes.get(cand);
            marked = Util.replaceAtIndex(marked,prim,pou,1);
        }
        marked = marked.replaceAll("ˌ", "");
        marked = marked.replaceAll("ˈ", "");
        return marked;
    }
    public static String Liquids (String liq) {
        String tempor = liq;
        int ind;
        char now;
        char next;
        char next2;
        for (ind = 0; ind < tempor.length(); ind++) {
            now = tempor.charAt(ind);
            try {
                next = tempor.charAt(ind+1);
            }
            catch (StringIndexOutOfBoundsException Error0) {
                next = 'X';
            }
            try {
                next2 = tempor.charAt(ind+2);
            }
            catch (StringIndexOutOfBoundsException Error0) {
                next2 = 'X';
            }
            /*if ((now == 'B' || now == 'C' || now == 'D' || now == 'F' || now == 'H') && next == 'ə' && next2 == '˞') {
                if (toNextVowel(tempor, ind+1) < 2) {
                    tempor = replaceSection(tempor, "ɹɹ", ind+1);
                }
                else {
                    tempor = replaceAtIndex(tempor, "ɹ", ind+2);
                    tempor = removeAtIndex(tempor, ind+1);
                }
            }*/
        }
        for (ind = 0; ind < tempor.length(); ind++) {
            now = tempor.charAt(ind);
            try {
                next = tempor.charAt(ind+1);
            }
            catch (StringIndexOutOfBoundsException Error0) {
                next = 'X';
            }
            if (now == '˞') {
                if (isVowel(next) == true || (Lingua.isBound(next) && isVowel(tempor.charAt(ind+2)))) {
                    tempor = Util.replaceAtIndex(tempor, "ɹɹ", ind, 1);
                }
                else {
                    tempor = Util.replaceAtIndex(tempor, "ɹ", ind, 1);
                }    
            }
        }
        return tempor;
    }
    public static String VowDiphth (String raw) {
        String vowels = raw;
        int index;
        char cur;
        for (index = 0; index < vowels.length(); index++) {
            cur = vowels.charAt(index);
            char foll;
            try {
                foll = vowels.charAt(index+1);
            }
            catch (StringIndexOutOfBoundsException Error0) {
                foll = 'X';
            }
            if (toNextVowel(vowels, index) > 0 && cur != 'ɑ' && cur != 'ɜ' && digraphs.containsKey(cur) && foll != '~') {
                vowels = Util.replaceAtIndex(vowels, digraphs.get(cur), index, 1);
                index += 1;
            }
            else if (cur == 'Y' && foll == 'ə') {
                vowels = Util.replaceAtIndex(vowels, "üw", index, 1);
            }
        }
       // char prvlt = vowels.charAt(0);
        for (index = 0; index < vowels.length(); index++) {
            cur = vowels.charAt(index);
            if (diphthongs.containsKey(cur)) {
                String[] dptg = diphthongs.get(cur);
                if (cur == 'W' && index > 0 && vowels.charAt(index-1) == 'j') {
                    vowels = Util.replaceAtIndex(vowels,"üa", index-1, 2);
                }
                else if (toNextVowel(vowels,index) < 1 && index < vowels.length()-1) {
                    vowels = Util.replaceAtIndex(vowels, dptg[1], index, 1);
                }
                else {
                    vowels = Util.replaceAtIndex(vowels, dptg[0], index, 1);
                }
                index += 1;
            }
            //prvlt = cur;
        }
        vowels = vowels.replaceAll("ɔɪ", "o-ɪ");
        vowels = vowels.replaceAll("ɔˈɪ", "o-ˈɪ");
        vowels = vowels.replaceAll("ɔˌɪ", "o-ˌɪ");
        vowels = vowels.replaceAll("ɔi", "o-i");
        vowels = vowels.replaceAll("ɔˈi", "o-ˈi");
        vowels = vowels.replaceAll("ɔˌi", "o-ˌi");
        vowels = vowels.replaceAll("ɔʊ", "o-ʊ");
        vowels = vowels.replaceAll("ɔˈʊ", "o-ˈʊ");
        vowels = vowels.replaceAll("ɔˌʊ", "o-ˌʊ");
        vowels = vowels.replaceAll("ɔu", "o-u");
        vowels = vowels.replaceAll("ɔˈu", "o-ˈu");
        vowels = vowels.replaceAll("ɔˌu", "o-ˌu");
        vowels = vowels.replaceAll("iə", "iya");
        vowels = vowels.replaceAll("uə", "uwa");
        return vowels;
    }
    public static String repExempt (String ipa, String resp, boolean str) {
        String repair = resp;
        if (ipa.startsWith("*")) {
            String key = ipa.substring(1);
            String preserve;
            if (TextParser.intrude) {
                preserve = (Phonology.whlspts).get(key+"r");
                
            }
            else {
                preserve = (Phonology.whlspts).get(key);
            }
            if (!preserve.equals(key)) {
                String xform = (Phonology.baseX).get(preserve);
                String target;
                if (TextParser.intrude) {
                    target = Translit(preserve.substring(0,preserve.length()-1),str);
                }
                else {
                    target = Translit(preserve,str);
                }
                repair = repair.replaceAll(target,xform);
            }
            else {
                repair = (Phonology.baseX).get(preserve);
            }
            repair = Util.removeAll(repair,"*");
        }
        return repair;
    }
    public static String Translit (String ipa, boolean str) {
        String mpss = Prepare(ipa);
        mpss = Liquids(mpss);
        mpss = VowDiphth(mpss);
        mpss = mpss.replaceAll("əj", "a-j");
        mpss = mpss.replaceAll("əˈj", "a-ˈj");
        mpss = mpss.replaceAll("əˌj", "a-ˌj");
        mpss = mpss.replaceAll("əw", "a-w");
        mpss = mpss.replaceAll("əˈw", "a-ˈw");
        mpss = mpss.replaceAll("əˌw", "a-ˌw");
        mpss = mpss.replaceAll("ɑːɪ", "a-ɪ");
        mpss = mpss.replaceAll("ɑːˈj", "a-ˈɪ");
        mpss = mpss.replaceAll("ɑːˌɪ", "a-ˌɪ");
        mpss = mpss.replaceAll("ɔːɪ", "o-ɪ");
        mpss = mpss.replaceAll("ɔːˈɪ", "o-ˈɪ");
        mpss = mpss.replaceAll("ɔːˌɪ", "o-ˌɪ");
        mpss = mpss.replaceAll("æ", "ae");
        mpss = mpss.replaceAll("ɹ", "r");
        mpss = mpss.replaceAll("ə", "a");
        mpss = mpss.replaceAll("j", "y");
        mpss = mpss.replaceAll("ɑ", "aa");
        mpss = mpss.replaceAll("ʌ", "u");
        mpss = mpss.replaceAll("ɜ", "oe");
        mpss = mpss.replaceAll("ɛ", "e");
        mpss = mpss.replaceAll("ɪ", "i");
        mpss = mpss.replaceAll("ɔ", "o");
        mpss = mpss.replaceAll("ɒ", "o");
        mpss = mpss.replaceAll("ŋk", "nk");
        mpss = mpss.replaceAll("ŋg", "ngg");
        mpss = mpss.replaceAll("ŋˈk", "nˈk");
        mpss = mpss.replaceAll("ŋˈg", "nˈgg");
        mpss = mpss.replaceAll("ŋˌk", "nˌk");
        mpss = mpss.replaceAll("ŋˌg", "nˌgg");
        mpss = mpss.replaceAll("G", "j");
        mpss = mpss.replaceAll("Q", "ch");
        mpss = mpss.replaceAll("W","ü");
        mpss = mpss.replaceAll("Y","ü");
        mpss = mpss.replaceAll("zh", "z-h");
        mpss = mpss.replaceAll("zˈh", "z-ˈh");
        mpss = mpss.replaceAll("zˌh", "z-ˌh");
        mpss = mpss.replaceAll("th", "t-h");
        mpss = mpss.replaceAll("tˈh", "t-ˈh");
        mpss = mpss.replaceAll("tˌh", "z-ˌh");
        mpss = mpss.replaceAll("dh", "d-h");
        mpss = mpss.replaceAll("dˈh", "d-ˈh");
        mpss = mpss.replaceAll("dˌh", "d-ˌh");
        mpss = mpss.replaceAll("sh", "s-h");
        mpss = mpss.replaceAll("sˈh", "s-ˈh");
        mpss = mpss.replaceAll("sˌh", "s-ˌh");
        mpss = mpss.replaceAll("ʒ", "zh");
        mpss = mpss.replaceAll("θ", "th");
        mpss = mpss.replaceAll("ð", "dh");
        mpss = mpss.replaceAll("ŋ", "ng");
        mpss = mpss.replaceAll("ʃ", "sh");
        mpss = mpss.replaceAll("ʔ", "q");
        mpss = mpss.replaceAll("ʊ", "ù");
        
        int syllables = countSyl(mpss);
        if (str && syllables-countSchwa(mpss) > 1 && syllables > 1) {
            mpss = MapStress(mpss);
        }
        else {
            mpss = Util.removeAll(Util.removeAll(mpss,"ˈ"),"ˌ");
        }
        mpss = mpss.replaceAll("ː", "");
        mpss = mpss.replaceAll("~", "\'");
        mpss = repExempt(ipa,mpss,str);
        return mpss;
    }
    public static boolean isFilled (List<String> data) {
        if (data.isEmpty() || data.size() == 1 && ((data.get(0)).equals("000") || (data.get(0)).equals(""))) {
            return false;
        }
        else {
            return true;
        }
    }
    public static String printMPSS (List<String> info, boolean pros, boolean capit) {
        String list = "";
        List<String> data = info;
        if (!isFilled(info)) {
            return "[NOT FOUND]";
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
        if (data.size() == 1) {
            if (capit) {
                list += Util.Capital(Translit(data.get(0), pros),0);
            }
            else {
                list += Translit(data.get(0), pros);
            }
        }
        else if (data.size() > 1) {
            list = "|";
            for (int f = 0; f < data.size(); f++) {
                if (capit) {
                    list += Util.Capital(Translit(data.get(f), pros),0)+"|";
                }
                else {
                    list += Translit(data.get(f), pros)+"|";
                }
            }
        }
        return list;
    }
    public static void initialize() {
        Diacritics();
        Diphthongs();
        Doublings();
    }
}
