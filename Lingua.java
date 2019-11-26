package convert17;
import java.util.*;
public class Lingua {
    public static Map<String,String> voicepairs = new TreeMap<>();
    public static String coda (String syl) {
        String coda = "";
        for (int o = syl.length()-1; o > 0; o--) {
            if (sonRank(syl.charAt(o)) < 3) {
                coda = Character.toString(syl.charAt(o))+coda;
            }
            else {
                break;
            }
        }
        return coda;
    }
    public static boolean isDiphthong (String psdg) {
        String digr = psdg.replaceAll("eə","ɛə");
        return digr.equals("aɪ") || digr.equals("aʊ") || digr.equals("eɪ") || digr.equals("ɔɪ") || digr.equals("oʊ") || digr.equals("ɛə") || digr.equals("ɪə") || digr.equals("ʊə") || digr.equals("E") || digr.equals("I") || digr.equals("U");
    }
    public static int countSyl (String count) {
        String ready = Phonology.prepSyl(count.replaceAll("ẏ","ÿ"));
        char avant = '#';
        int co = 0;
        for (int j = 0; j < ready.length(); j++) {
            char ceci = ready.charAt(j);
            boolean diphthong = j < ready.length()-1 && isDiphthong(Character.toString(avant)+Character.toString(ceci));
            boolean syllabic = ceci == '̩';
            if (ceci != 'ː' && (Lingua.isVowel(ceci) || Character.isDigit(ceci) || syllabic) && !diphthong) {
                co += 1;
            }
            avant = ceci;
        }
        return co;
    }
    public static int countBound (String count) {
        int bndnm = 0;
        for (int j = 0; j < count.length(); j++) {
            char dis = count.charAt(j);
            if (dis == '.' || dis == 'ˈ' || dis == 'ˌ') {
                bndnm++;
            }
        }
        return bndnm;
    }
    public static int countSeg (String count) {
        if (isBound(count.charAt(0))) {
            return countBound(count);
        }
        else {
            return countBound(count)+1;
        }
    }
    public static String rhoBound (String dumb) {
        String smart = "";
        for (int j = 0; j < dumb.length()-1; j++) {
            char ceci = dumb.charAt(j);
            smart += Character.toString(ceci);
            char proch = dumb.charAt(j+1);
            if (ceci == '˞' && isVowel(proch)) {
                smart += ".";
            }
        }
        smart += dumb.charAt(dumb.length()-1);
        return smart;
    }
    public static String syncBound (String dumb) {
        String smart = Character.toString(dumb.charAt(0));
        for (int j = 1; j < dumb.length(); j++) {
            char ceci = dumb.charAt(j);
            char dern = dumb.charAt(j-1);
            if (ceci == ']' && isVowel(dern)) {
                smart += ".";
            }
            smart += Character.toString(ceci);
        }
        return smart;
    }
    public static String getSyl (String lon, int num, boolean prep) {
        String ready = Phonology.prepSyl(lon);
        String part = "";
        int co = 0;
        int bgn = 0;
        for (int j = 0; j < ready.length() && co <= num; j++) {
            char ceci = ready.charAt(j);
            if (j == ready.length()-1) {
                String slbl = ready.substring(bgn);
                if (co == num) {
                    part = slbl;
                }
                co += 1;
                bgn = j;
            }
            else if (isBound(ceci) && j > 0) {
                String slbl = ready.substring(bgn,j);
                if (co == num) {
                    part = slbl;
                }
                co += 1;
                bgn = j;
            }
        }
        if (prep) {
            return part;
        }
        else {
            return Phonology.unPrepSyl(part);
        }
    }
    public static void initialize () {
        voicepairs.put("d","t");
        voicepairs.put("t","d");
        voicepairs.put("z","s");
        voicepairs.put("s","z");
        voicepairs.put("v","f");
        voicepairs.put("f","v");
        voicepairs.put("ð","θ");
        voicepairs.put("θ","ð");
    }
    public static int place (char letter) {
        if (letter == 'p' || letter == 'b' || letter == 'm' || letter == 'w') {
            return 1;
        }
        else if (letter == 'f' || letter == 'v') {
            return 2;
        }
        else if (letter == 'θ' || letter == 'ð') {
            return 3;
        }
        else if (letter == 't' || letter == 'd' || letter == 's' || letter == 'z' || letter == 'n' || letter == 'l' || letter == 'ɹ' || letter == 'r') {
            return 4;
        }
        else if (letter == 'ʃ' || letter == 'ʒ' || letter == 'C' || letter == 'G') {
            return 5;
        }
        else if (letter == 'j') {
            return 6;
        }
        else if (letter == 'k' || letter == 'g' || letter == 'ŋ') {
            return 7;
        }
        else if (letter == 'ʔ' || letter == 'h') {
            return 8;
        }
        else {
            return 0;
        }
    }
    public static boolean isAlvStop (char letter) {
        if (letter == 't' || letter == 'd') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isBound (char letter) {
        if (letter == '.' || letter == 'ˈ' || letter == 'ˌ') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isCorSib (char letter) {
        if (letter == 's' || letter == 'z' || letter == 'ʃ' || letter == 'ʒ' || letter == 'C' || letter == 'G') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isDent (char letter) {
        if (letter == 'θ' || letter == 'ð') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isFree (char letter) {
        if (letter == 'ɑ' || letter == 'ɜ' || letter == 'i' || letter == 'ɔ' || letter == 'u') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isHeavy (String sylb) {
        char termin = sylb.charAt(sylb.length()-1);
        if (sylb.endsWith("ː") || Character.isDigit(termin) || isConsonant(termin) || isDiphthong(nucleus(sylb))) { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isLabObstr (char letter) {
        if (letter == 'p' || letter == 'b' || letter == 'f' || letter == 'v') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isLabDent (char letter) {
        if (letter == 'f' || letter == 'v') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isGlide (char letter) {
        if (letter == 'j' || letter == 'w') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isLiq (char letter) {
        if (letter == 'l' || letter == 'r' || letter == 'ɹ') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isNas (char letter) {
        if (letter == 'm' || letter == 'n' || letter == 'ŋ') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isOpen (String sylb) {
        char termin = sylb.charAt(sylb.length()-1);
        if (!isConsonant(termin)) { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isPostAlvSib (char letter) {
        if (letter == 'ʃ' || letter == 'ʒ' || letter == 'C' || letter == 'G') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isStop (char letter) {
        if (letter == 't' || letter == 'd' || letter == 'p' || letter == 'b' || letter == 'k' || letter == 'g') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isVoiced (char letter) {
        if (letter == 'b' || letter == 'm' || letter == 'v' || letter == 'ð' || letter == 'd' || letter == 'z' || letter == 'n' || letter == 'ʒ' || letter == 'ɡ' || letter == 'ŋ' || isVowel(letter) || letter == '˞'  || letter == 'ɹ' || letter == 'l' || letter == 'r'){ 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isVowel (char letter) {
        if (letter == 'ʌ' || letter == 'ɛ' || letter == 'ɪ' || letter == 'ɒ' || letter == 'ʊ' || letter == 'ɑ' || letter == 'ɜ' || letter == 'i' || letter == 'ɔ' || letter == 'u' || letter == 'æ' || letter == 'e' || letter == 'ə' || letter == 'o' || letter == 'a' || letter == 'ː' || letter == 'E' || letter == 'I' || letter == 'U'  || letter == 'O' || letter == 'ɩ' || letter == 'ʋ' || letter == ']' || letter == '[' || letter == 'ÿ' || letter == 'ï' || letter == 'ü' || letter == 'ö' || letter == 'ı' || letter == 'é' || letter == 'â' || letter == 'ê' || letter == 'ô') { 
            return true;
        }                        
        else {
            return false;
        }
    }
    public static boolean isConsonant (char letter) {
        if (!isVowel(letter) && !isBound(letter) && Character.isLetter(letter) || letter == 'ʃ' || letter == 'ʒ' || letter == 'θ' || letter == 'ð' || letter == 'ŋ' || letter == '˞' || letter == 'ɹ' || letter == 'ʔ' || letter == 'ʍ' || letter == 'ç' || letter == 'ñ' || letter == 'þ') { 
            return true;
        }                        
        else {
            return false;
        }
    
    }
    public static int lastCons (String seq, int dep) {
        int q = seq.length();
        int dpt = 0;
        while (q <= 0 && dpt < dep) {
            char cons = seq.charAt(q);
            if (isConsonant(cons)) {
                dpt++;
            }
            else {
                q--;
            }
        }
        return q;
    }
    public static int lastVowel (String seq, int dep) {
        int q = seq.length();
        int dpt = 0;
        while (q <= 0 && dpt < dep) {
            char vow = seq.charAt(q);
            if (sonRank(vow) == 4) {
                dpt++;
            }
            else {
                q--;
            }
        }
        return q;
    }
    public static char nextVowel (String seq, int dep) {
        int dpt = dep;
        char vow = '|';
        while (dpt < seq.length()) {
            vow = seq.charAt(dpt);
            if (sonRank(vow) == 3) {
                break;
            }
            else {
                dpt++;
            }
        }
        return vow;
    }
    public static boolean isRhotic (String syl) {
        return syl.contains("˞");
    }
    public static boolean isSyllabic (String sylb) {
        return sylb.equals("l̩") || sylb.equals("ɹ̩") || sylb.equals("r̩") || sylb.equals("m̩") || sylb.equals("n̩"); 
    }
    public static boolean containsRhot (String rhotac) {
        boolean cntrhot = false;
        char rht1 = rhotac.charAt(0);
        if (rhotac.contains("ɚ") || rhotac.contains("ɝ")) {
            cntrhot = true;
        }
        else {
            for (int rh = 1; rh < rhotac.length(); rh++) {
                char rht2 = rhotac.charAt(rh);
                if ((rht2 == 'r' || rht2 == 'ɹ' || rht2 == '˞') && isVowel(rht1) && (rh == rhotac.length()-1 || rh < rhotac.length()-1 && !isVowel(rhotac.charAt(rh+1)) || isBound(rhotac.charAt(rh+1)))) {
                    cntrhot = true;
                    break;
                }
                rht1 = rht2;
            }
        }
        return cntrhot;
    }
    public static boolean isRhotacized (String syl) {
        if (syl.lastIndexOf("r") == syl.length()-1 || !isVowel(syl.charAt(syl.lastIndexOf("r")+1))) {
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean isStressed (String syl, int begin) {
        boolean tonic = false;
        for (int q = begin; q >= 0; q--) {
            char is = syl.charAt(q);
            if (is == 'ˈ' || is == 'ˌ') {
                tonic = true;
                break;
            }
        }
        return tonic;
    }
    public static String nucleus (String syl) {
        String nucl = "";
        for (int o = 0; o < syl.length(); o++) {
            if (sonRank(syl.charAt(o)) == 3) {
                nucl += Character.toString(syl.charAt(o));
            }
        }
        return nucl;
    }
    public static String onset (String syl) {
        String nucl = "";
        for (int o = 0; o < syl.length(); o++) {
            if (sonRank(syl.charAt(o)) < 3) {
                nucl += Character.toString(syl.charAt(o));
            }
            else {
                break;
            }
        }
        return nucl;
    }
    public static boolean samePlaceMan (char let1, char let2) {
        if ((isCorSib(let1) && isCorSib(let2)) || (isAlvStop(let1) && isAlvStop(let2))) {
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean select (Lemma base, Lemma aff) { 
        boolean crit;
        char cat1 = base.getUsage();
        String cats2 = aff.getSel();
        if (cats2.indexOf(cat1) >= 0) {
            crit = true;
        }
        else {
            crit = false;
        }
        return crit;
    }
    public static boolean select (char cat1, Lemma aff) { 
        boolean crit;
        String cats2 = aff.getSel();
        if (cats2.indexOf(cat1) >= 0) {
            crit = true;
        }
        else {
            crit = false;
        }
        return crit;
    }
    public static int sonRank (char letter) {
        if (letter == 'p' || letter == 'b' || letter == 'f' || letter == 'v' || letter == 'θ' || letter == 'ð' || letter == 't' || letter == 'd' || letter == 's' || letter == 'z' || letter == 'ʃ' || letter == 'ʒ' || letter == 'C' || letter == 'G' || letter == 'k' || letter == 'ɡ' || letter == 'h' || letter == 'ʔ') { 
            return 1;
        }                        
        else if (letter == 'l' || letter == 'r' || letter == 'ɹ' || letter == 'j' || letter == 'w' || letter == '˞' || letter == 'm' || letter == 'n' || letter == 'ŋ'){
            return 2;
        }
        else if (isVowel(letter) || Character.isDigit(letter) || letter == ']') { 
            return 3;
        }
        else {
            return 0;
        }
    }
    public static boolean stress (String syl) {
        if (syl.startsWith("ˈ") || syl.startsWith("ˌ")) {
            return true;
        }
        else {
            return false;
        }
    }
    public static String swapVoiceEnd (String voz) {
        String voice;
        if (voz.endsWith("~")) {
            voice = voz.substring(0,voz.length()-1);
        }
        else {
            voice = voz;
        }
        String trgt = voice.substring(voice.length()-1);
        String prepart = voice.substring(0,voice.length()-1);
        String devoiced;
        if (voz.endsWith("~")) {
            devoiced = prepart+voicepairs.get(trgt)+"~";
        }
        else {
            devoiced = prepart+voicepairs.get(trgt);
        }
        return devoiced;
    }
    public static String swapVoiceStart (String voice) {
        String invoiced;
        if (voice.length() > 1) {
            String trgt = voice.substring(0,1);
            String pospart = voice.substring(1);
            invoiced = voicepairs.get(trgt)+pospart;
        }
        else {
            invoiced = voicepairs.get(voice);
        }
        return invoiced;
    }
    public static boolean voiceClash (char let1, char let2) {
        if ((!isVoiced(let1) && isVoiced(let2)) || (isVoiced(let1) && !isVoiced(let2))) {
            return true;
        }
        else {
            return false;
        }
    }
}