package convert17;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TranRLS {
    public static boolean isVowel (char letter) {
        if (letter == 'ʌ' || letter == 'ɛ' || letter == 'ɪ' || letter == 'ɒ' || letter == 'ʊ' || letter == 'ɑ' || letter == 'ɜ' || letter == 'i' || letter == 'ɔ' || letter == 'u' || letter == 'æ' || letter == 'e' || letter == 'ə' || letter == 'o' || letter == 'B' || letter == 'C' || letter == 'D' || letter == 'F' || letter == 'H' || letter == 'a' || letter == 'á' || letter == 'à' || letter == 'ä' || letter == 'é' || letter == 'è' || letter == 'í' || letter == 'ì' || letter == 'ï' || letter == 'ó' || letter == 'ò' || letter == 'ö' || letter == 'ø' || letter == 'u' || letter == 'ú' || letter == 'ù' || letter == 'ü' || letter == 'T' || letter == 'V' || letter == 'W') {
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isAcute (char letter) {
        if (letter == 'á' || letter == 'é' || letter == 'í' || letter == 'ó' || letter == 'ú') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isGrave (char letter) {
        if (letter == 'à' || letter == 'è' || letter == 'ì' || letter == 'ò' || letter == 'ù') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isDieresis (char letter) {
        if (letter == 'ä' || letter == 'ë' || letter == 'ï' || letter == 'ö' || letter == 'ü') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isShort (char one, char two) {
        if (isVowel(one) && !isAcute(one) && (isGrave(one) || isConsonant(two)) && two != 'y' && two != 'w') { 
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
    public static boolean formDiphthong (char one, char two) {
        String sequence = Character.toString(one)+Character.toString(two); 
        if (sequence.equals("ai") || sequence.equals("au") || sequence.equals("ei") || sequence.equals("oi") || sequence.equals("ou") || sequence.equals("eø") || sequence.equals("iø") || sequence.equals("uø")) {
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
    public static Map <Character, String> graves = new HashMap<>();
    public static Map <Character, String> graves2 = new HashMap<>();
    public static Map <Character, String> acutes = new HashMap<>();
    public static Map <Character, String> acutes2 = new HashMap<>();
    public static Map <Character, String> flexes = new HashMap<>();
    public static Map <Character, String> carons = new HashMap<>();
    public static Map <Character, String> diereses = new HashMap<>();
    public static Map <Character, Character> braces = new HashMap<>();
    public static Map <Character, String[]> diphthongs = new HashMap<>(); 
    public static void Diphthongs () {
        String[] diph1 = {"ai", "ay"};
        String[] diph2 = {"au", "aw"};
        String[] diph3 = {"ei", "ey"};
        String[] diph4 = {"oi", "oy"};
        String[] diph5 = {"ou", "ow"};
        String[] diph6 = {"eø", "eø"};
        String[] diph7 = {"iø", "iø"};
        String[] diph8 = {"uø", "uø"};
        diphthongs.put('B', diph1);
        diphthongs.put('C', diph2);
        diphthongs.put('D', diph3);
        diphthongs.put('F', diph4);
        diphthongs.put('H', diph5);
        diphthongs.put('T', diph6);
        diphthongs.put('V', diph7);
        diphthongs.put('W', diph8);
    }
    public static void Diacritics () {
        acutes.put('ɑ', "á");
        acutes.put('ɜ', "é");
        acutes.put('i', "í");
        acutes.put('ɔ', "ó");
        acutes.put('u', "ú");
        acutes2.put('ä',"â");
        acutes2.put('ö',"ô");
        acutes2.put('ï',"î");
        acutes2.put('ü',"û");
        acutes2.put('a', "á");
        acutes2.put('e', "é");
        acutes2.put('i', "í");
        acutes2.put('o', "ó");
        acutes2.put('u', "ú");
        graves.put('ʌ', "à");
        graves.put('ɛ', "è");
        graves.put('ɪ', "ì");
        graves.put('ɒ', "ò");
        graves.put('ʊ', "ù");
        graves2.put('æ', "æ̀");
        graves2.put('a', "à");
        graves2.put('e', "è");
        graves2.put('i', "ì");
        graves2.put('o', "ò");
        graves2.put('u', "ù");
        flexes.put('a', "â");
        flexes.put('á', "â");
        flexes.put('e', "ê");
        flexes.put('é', "ê");
        flexes.put('i', "î");
        flexes.put('í', "î");
        flexes.put('o', "ô");
        flexes.put('ó', "ô");
        flexes.put('u', "û");
        flexes.put('ú', "û");
        carons.put ('a', "ǎ");
        carons.put('e', "ě");
        carons.put('i', "ǐ");
        carons.put('o', "ǒ");
        carons.put ('u', "ǔ");
        carons.put('æ', "æ̌");
        carons.put('ä', "ǎ");
        carons.put('ï', "ǐ");
        carons.put('ö', "ǒ");
        carons.put('ü', "ǔ");
        diereses.put('a', "ä");
        diereses.put('i', "ï");
        diereses.put('o', "ö");
        diereses.put('u', "ü");
    }
    public static String Prepare (String logos) {
        String prep = prep = Util.removeAll(logos, "(ɹ)");
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
        prep = prep.replaceAll("ɑɹ", "ɑ˞");
        prep = prep.replaceAll("ɔɹ", "ɔ˞");
        prep = prep.replaceAll("ɚ", "ə˞");
        prep = prep.replaceAll("ɝ", "ɜ˞");
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
        prep = prep.replaceAll("ɛə", "T");
        prep = prep.replaceAll("ɪə", "V");
        prep = prep.replaceAll("ʊə", "W");
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
        char antes = '#';
        int schwa = 0;
        for (int c = 0; c < subj.length(); c++) {
            char ahora = subj.charAt(c);
            if ((ahora == 'ə' || ahora == 'ø') && antes != 'ɛ' && antes != 'ɪ' && antes != 'ʊ' && antes != 'e' && antes != 'i' && antes != 'u') {
                schwa += 1;
            }
            else {
                antes = ahora;
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
            boolean glide = j > 0 && formDiphthong(ready.charAt(j-1), ceci);
            if (isVowel(ceci) && !glide || Character.isDigit(ceci)) {
                co += 1;
            }
        }
        return co;
    }
    public static int stressRank (char rank) {
        if (isGrave(rank)) {
            return 3;
        }
        else if (isAcute(rank) || isDieresis(rank) || rank == 'æ') {
            return 2;
        }
        else if (rank == 'ø') {
            return 0;
        }
        else {
            return 1;
        }
    }
    public static String MarkStress (String unstr, int ubi) {
        String strest;
        char candid = unstr.charAt(ubi);
        boolean rhot = (ubi < unstr.length()-2 && unstr.charAt(ubi+1) == 'r' && isConsonant(unstr.charAt(ubi+2))) || ubi == unstr.length()-2 && unstr.charAt(ubi+1) == 'r';
        if (ubi < unstr.length()-1 && isShort(candid,unstr.charAt(ubi+1)) && !isGrave(candid) && !rhot) {
            strest = Util.replaceAtIndex(unstr,graves2.get(candid),ubi,1);
        }
        else {
            if (isDieresis(candid)) {
                strest = Util.replaceAtIndex(unstr,carons.get(candid),ubi,1);
            }
            else {
                strest = Util.replaceAtIndex(unstr,flexes.get(candid),ubi,1);
            }
        }
        return strest;
    }
    public static String MapStress (String stressed) {
        String marked = stressed;
        /*if (!stressed.contains("ˈ")) {
            marked = "ˈ"+stressed;
        }
        else {
            marked = stressed;
        }*/
        int highpre = 1;
        String partial = stressed.substring(stressed.indexOf('ˈ'));
        String prestr = stressed.substring(0,stressed.indexOf('ˈ'));
        int full = countSyl(stressed) - countSchwa(stressed);
        int elij = countSyl(partial) - countSchwa(partial);
        int cntsyl;
        if (full <= 3) {
            cntsyl = full - elij;
        }
        else {
            cntsyl = 3 - countSyl(partial);
        }
        for (int lr = prestr.length()-1; lr >= 0 && cntsyl > 0; lr--) {
            char atlr = prestr.charAt(lr);
            boolean glide = lr > 0 && formDiphthong(prestr.charAt(lr-1),atlr);
            if (isVowel(atlr) && !glide) {
                int ranking = stressRank(atlr);
                if (ranking > highpre) {
                    highpre = ranking;
                }
                else if (atlr != 'ø') {
                    cntsyl--;
                }
            }
        }
        if (elij <= 3) {
            int highest = highpre;
            int sylcnt = 0;
            for (int fr = partial.length()-1; fr >= 1; fr--) {
                char tst = partial.charAt(fr);
                boolean glide = fr > 0 && formDiphthong(partial.charAt(fr-1),tst);
                boolean vowel = isVowel(tst);
                int priority = 0;
                if (vowel && tst != 'ø' && !glide) {
                    priority = stressRank(tst);
                    sylcnt++;
                }
                int mark = fr;
                if (vowel && sylcnt == elij && (priority < highest || elij < full && (elij < 3 || full < 3) && priority <= highpre)) {
                    partial = MarkStress(partial,mark);
                    break;
                }
                else if (vowel && priority > highest) {
                    highest = priority;
                }
            }
        }
        else {
            int mark = toNextVowel(partial,0)+1;
            partial = MarkStress(partial,mark);
        }
        marked = stressed.substring(0,stressed.indexOf('ˈ'))+partial;
        marked = Util.removeAll(Util.removeAll(marked,"ˌ"),"ˈ");
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
            if (isVowel(cur) == true && cur != 'æ' && cur != 'ø') {
                if (toNextVowel(vowels, index) < 1 && graves.containsKey(cur)) {
                    vowels = Util.replaceAtIndex(vowels, graves.get(cur), index, 1);  
                }
                else if (toNextVowel(vowels, index) > 0 && acutes.containsKey(cur) && foll != '~') {
                    if (foll != 'ɹ' || foll == 'ɹ' && (cur == 'ɜ' || toNextVowel(vowels, index) < 2)) {
                        vowels = Util.replaceAtIndex(vowels, acutes.get(cur), index, 1);
                    }
                }
            }
        }
        for (index = 0; index < vowels.length(); index++) {
            cur = vowels.charAt(index);
            if (diphthongs.containsKey(cur)) { 
                String[] dptg = diphthongs.get(cur);
                if (toNextVowel(vowels,index) < 1 && index < vowels.length()-1) {
                    vowels = Util.replaceAtIndex(vowels, dptg[1], index, 1);
                }
                else {
                    vowels = Util.replaceAtIndex(vowels, dptg[0], index, 1);
                }
                index += 1;
            }
        }
        vowels = vowels.replaceAll("ɑɪ", "äɪ");
        vowels = vowels.replaceAll("ɑˈɪ", "äˈɪ");
        vowels = vowels.replaceAll("ɑˌɪ", "äˌɪ");
        vowels = vowels.replaceAll("ɑi", "äi");
        vowels = vowels.replaceAll("ɑˈi", "äˈi");
        vowels = vowels.replaceAll("ɑˌi", "äˌi");
        vowels = vowels.replaceAll("ɑʊ", "äʊ");
        vowels = vowels.replaceAll("ɑˈʊ", "äˈʊ");
        vowels = vowels.replaceAll("ɑˌʊ", "äˌʊ");
        vowels = vowels.replaceAll("ɑu", "äu");
        vowels = vowels.replaceAll("ɑˈu", "äˈu");
        vowels = vowels.replaceAll("ɑˌu", "äˌu");
        vowels = vowels.replaceAll("ɔɪ", "öɪ");
        vowels = vowels.replaceAll("ɔˈɪ", "öˈɪ");
        vowels = vowels.replaceAll("ɔˌɪ", "öˌɪ");
        vowels = vowels.replaceAll("ɔi", "öi");
        vowels = vowels.replaceAll("ɔˈi", "öˈi");
        vowels = vowels.replaceAll("ɔˌi", "öˌi");
        vowels = vowels.replaceAll("ɔʊ", "öʊ");
        vowels = vowels.replaceAll("ɔˈʊ", "öˈʊ");
        vowels = vowels.replaceAll("ɔˌʊ", "öˌʊ");
        vowels = vowels.replaceAll("ɔu", "öu");
        vowels = vowels.replaceAll("ɔˈu", "öˈu");
        vowels = vowels.replaceAll("ɔˌu", "öˌu");
        vowels = vowels.replaceAll("iə", "ïø");
        vowels = vowels.replaceAll("uə", "üø");
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
        String rls = Prepare(ipa);
        rls = Liquids(rls);
        rls = VowDiphth(rls);
        rls = rls.replaceAll("ɹ", "r");
        rls = rls.replaceAll("ə", "ø");
        rls = rls.replaceAll("j", "y");
        rls = rls.replaceAll("ʌ", "a");
        rls = rls.replaceAll("ɑ", "a");
        rls = rls.replaceAll("ɛ", "e");
        rls = rls.replaceAll("ɜ", "e");
        rls = rls.replaceAll("ɪ", "i");
        rls = rls.replaceAll("i", "i");
        rls = rls.replaceAll("ɒ", "o");
        rls = rls.replaceAll("ɔ", "o");
        rls = rls.replaceAll("ʊ", "u");
        rls = rls.replaceAll("u", "u");
        rls = rls.replaceAll("ŋk", "nk");
        rls = rls.replaceAll("ŋg", "ng");
        rls = rls.replaceAll("ŋˈk", "nˈk");
        rls = rls.replaceAll("ŋˈg", "nˈg");
        rls = rls.replaceAll("ŋˌk", "nˌk");
        rls = rls.replaceAll("ŋˌg", "nˌg");
        rls = rls.replaceAll("G", "j");
        rls = rls.replaceAll("Q", "c");
        rls = rls.replaceAll("ʒ", "x");
        rls = rls.replaceAll("θ", "þ");
        rls = rls.replaceAll("ŋ", "ñ");
        rls = rls.replaceAll("ʃ", "ç");
        rls = rls.replaceAll("ʔ", "q");
        int syllables = countSyl(rls);
        if (str && syllables-countSchwa(rls) > 1 && syllables > 1) {
            rls = MapStress(rls);
        }
        else {
            rls = Util.removeAll(Util.removeAll(rls,"ˈ"),"ˌ");
        }
        rls = rls.replaceAll("ː", "");
        rls = rls.replaceAll("~", "\'");
        rls = repExempt(ipa,rls,str);
        return rls;
    }
    public static String printRLS (List<String> info, boolean pros, boolean capit) {
        String list = "";
        List<String> data = info;
        if (!Util.isFilled(info)) {
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
        Diphthongs();
        Diacritics();
    }
}