package convert17;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TranSF {
    public static boolean isVowel (char letter) {
        if (letter == 'ʌ' || letter == 'ɛ' || letter == 'ɪ' || letter == 'ɒ' || letter == 'ʊ' || letter == 'ɑ' || letter == 'ɜ' || letter == 'i' || letter == 'ɔ' || letter == 'u' || letter == 'æ' || letter == 'e' || letter == 'ə' || letter == 'o' || letter == 'B' || letter == 'C' || letter == 'D' || letter == 'F' || letter == 'H' || letter == 'a' || letter == 'á' || letter == 'à' || letter == 'ä' || letter == 'é' || letter == 'è' || letter == 'í' || letter == 'ì' || letter == 'ó' || letter == 'ò' || letter == 'ö' || letter == 'ø' || letter == 'u' || letter == 'ú' || letter == 'ù') { 
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
    public static Map <Character, String> length = new HashMap<>();
    public static void Doublings () {
        length.put('ɑ', "aa");
        length.put('ɜ', "öö");
        length.put('i', "ii");
        length.put('ɔ', "oo");
        length.put('u', "uu");
        length.put('T', "ee");
        /*length.put('V', "ii");
        length.put('W', "uu");*/
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
    public static String VowDiphth (String raw) {
        String vowels = raw.replaceAll("ɑ˞", "ɑːɹ");
        vowels = vowels.replaceAll("ɔ˞", "ɔːɹ");
        vowels = vowels.replaceAll("ɜ˞", "ɜːɹ");
        vowels = vowels.replaceAll("˞", "ɹ");
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
            if ((isVowel(cur) || cur == 'T') == true && cur != 'æ' && toNextVowel(vowels, index) > 0 && length.containsKey(cur) && foll != '~') {
                vowels = Util.replaceAtIndex(vowels, length.get(cur), index, 1);
                index += 1;
            }
        }
        return vowels;
    }
    public static String repExempt (String ipa, String resp) {
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
                    target = Translit(preserve.substring(0,preserve.length()-1));
                }
                else {
                    target = Translit(preserve);
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
    public static String Translit (String ipa) {
        String sf = Prepare(ipa);
        sf = VowDiphth(sf);
        sf = sf.replaceAll("˞", "r");
        sf = sf.replaceAll("ɹ", "r");
        sf = sf.replaceAll("ə", "ö");
        sf = sf.replaceAll("ɜ", "ö");
        sf = sf.replaceAll("æ", "ä");
        sf = sf.replaceAll("ʌ", "a");
        sf = sf.replaceAll("ɑ", "a");
        sf = sf.replaceAll("ɛ", "e");
        sf = sf.replaceAll("ɜ", "e");
        sf = sf.replaceAll("ɪ", "i");
        sf = sf.replaceAll("i", "i");
        sf = sf.replaceAll("ɒ", "o");
        sf = sf.replaceAll("ɔ", "o");
        sf = sf.replaceAll("ʊ", "u");
        sf = sf.replaceAll("u", "u");
        sf = sf.replaceAll("ŋk", "nk");
        sf = sf.replaceAll("ŋg", "ng");
        sf = sf.replaceAll("ŋˈk", "nˈk");
        sf = sf.replaceAll("ŋˈg", "nˈg");
        sf = sf.replaceAll("ŋˌk", "nˌk");
        sf = sf.replaceAll("ŋˌg", "nˌg");
        sf = sf.replaceAll("B", "ai");
        sf = sf.replaceAll("C", "au");
        sf = sf.replaceAll("D", "ei");
        sf = sf.replaceAll("F", "oi");
        sf = sf.replaceAll("H", "ou");
        //sf = sf.replaceAll("Tr", "eer");
        sf = sf.replaceAll("Vr", "iir");
        sf = sf.replaceAll("Wr", "uur");
        sf = sf.replaceAll("T", "e");
        sf = sf.replaceAll("V", "iö");
        sf = sf.replaceAll("W", "uö");
        sf = sf.replaceAll("G", "dzh");
        sf = sf.replaceAll("Q", "tsh");
        sf = sf.replaceAll("ʒ", "zh");
        sf = sf.replaceAll("θ", "th");
        sf = sf.replaceAll("ð", "th");
        sf = sf.replaceAll("ŋ", "ng");
        sf = sf.replaceAll("ʃ", "sh");
        sf = sf.replaceAll("ʔ", "q");
        sf = Util.removeAll(Util.removeAll(sf,"ˈ"),"ˌ");
        sf = sf.replaceAll("ː", "");
        sf = sf.replaceAll("~", "\'");
        sf = repExempt(ipa,sf);
        return sf;
    }
    public static boolean isFilled (List<String> data) {
        if (data.isEmpty() || data.size() == 1 && ((data.get(0)).equals("000") || (data.get(0)).equals(""))) {
            return false;
        }
        else {
            return true;
        }
    }
    public static String printSF (List<String> info, boolean pros, boolean capit) {
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
                list += Util.Capital(Translit(data.get(0)),0);
            }
            else {
                list += Translit(data.get(0));
            }
        }
        else if (data.size() > 1) {
            list = "|";
            for (int f = 0; f < data.size(); f++) {
                if (capit) {
                    list += Util.Capital(Translit(data.get(f)),0)+"|";
                }
                else {
                    list += Translit(data.get(f))+"|";
                }
            }
        }
        return list;
    }
    public static void initialize() {
        Doublings();
    }
}
