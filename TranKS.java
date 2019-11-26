package convert17;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TranKS {
    public static boolean isVowel (char letter) {
        if (letter == 'ʌ' || letter == 'ɛ' || letter == 'ɪ' || letter == 'ɒ' || letter == 'ʊ' || letter == 'ɑ' || letter == 'ɜ' || letter == 'i' || letter == 'ɔ' || letter == 'u' || letter == 'æ' || letter == 'ə' || letter == 'a' || letter == 'e' || letter == 'o' || letter == 'ɩ' || letter == 'ʋ' || letter == 'ÿ' || letter == 'ä' || letter == 'ë' || letter == 'ï' || letter == 'ö' || letter == 'ü' || letter == 'ı') {
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
    public static Map <Character, String> acutes = new HashMap<>();
    public static Map <Character, String> macrons = new HashMap<>();
    public static Map <String, String> sight = new HashMap<>();
    public static void Diacritics () {
        acutes.put('a', "á");
        acutes.put('e', "é");
        acutes.put('ı', "í");
        acutes.put('o', "ó");
        acutes.put('u', "ú");
        acutes.put('ɑ', "ɑ́");
        acutes.put('ɛ', "ɛ́");
        acutes.put('ɩ', "ɩ́");
        acutes.put('ɔ', "ɔ́");
        acutes.put('ʋ', "ʋ́");
        acutes.put('ʊ', "ʊ́");
        acutes.put('æ', "ǽ");
        acutes.put('ə', "ə́");
        acutes.put('ÿ',"ý");
        acutes.put('ä',"ɑ̇́");
        acutes.put('ë',"ė́");
        acutes.put('ï',"ı̇́");
        acutes.put('ö',"ȯ́");
        acutes.put('ü',"u̇́");
        macrons.put('a', "ā");
        macrons.put('e', "ē");
        macrons.put('ı', "ī");
        macrons.put('o', "ō");
        macrons.put('u', "ū");
        macrons.put('ɑ', "ɑ̄");
        macrons.put('ɛ', "ɛ̄");
        macrons.put('ı', "ı̄");
        macrons.put('ɔ', "ɔ̄");
        macrons.put('ʋ', "ʋ̄");
        macrons.put('ʊ', "ʊ̄");
        macrons.put('æ', "ǣ");
        macrons.put('ə', "ə̄");
        macrons.put('ÿ', "ẏ̄");
        macrons.put('ä', "ɑ̇̄");
        macrons.put('ë', "ė̄");
        macrons.put('ï', "ı̇̄");
        macrons.put('ö', "ȱ");
        macrons.put('ü', "u̇̄");
    }
    public static void sightWords () {
        sight.put("the","the");
        sight.put("you","yu");
        sight.put("an","ɑn");
        sight.put("a","ɑ̇");
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
        prep = prep.replaceAll("aɪ", "1");
        prep = prep.replaceAll("aʊ", "2");
        prep = prep.replaceAll("eɪ", "3");
        prep = prep.replaceAll("ɔɪ", "4");
        prep = prep.replaceAll("oʊ", "5");
        prep = prep.replaceAll("ɑj", "1");
        prep = prep.replaceAll("ɑw", "2");
        prep = prep.replaceAll("ej", "3");
        prep = prep.replaceAll("eː", "3");
        prep = prep.replaceAll("ɛɪ", "3");
        prep = prep.replaceAll("ɔj", "4");
        prep = prep.replaceAll("ow", "5");
        prep = prep.replaceAll("oː", "5");
        prep = prep.replaceAll("əʊ", "5");
	prep = prep.replaceAll("ɡ","g");
        prep = prep.replaceAll ("t͡ʃ", "Q");
	prep = prep.replaceAll("d͡ʒ", "G");
        prep = prep.replaceAll ("ʧ", "Q");
	prep = prep.replaceAll("ʤ", "G");
	prep = Util.removeAll(prep, "ː");
        return prep;
    }
    public static String MapStress (String stressed) {
        String marked = stressed.replaceAll("ẏ","ÿ");
        marked = marked.replaceAll("ɑ̇","ä");
        marked = marked.replaceAll("ė","ë");
        marked = marked.replaceAll("ı̇","ï");
        marked = marked.replaceAll("ȯ","ö");
        marked = marked.replaceAll("u̇","ü");
        boolean primary = false;
        boolean secondary = false;
        for (int lr = 0; lr < marked.length(); lr++) {
            char atlr = marked.charAt(lr);
            //boolean glide = lr > 0 && formDiphthong(marked.charAt(lr-1),atlr);
            if (isVowel(atlr) && primary) {
               String diac = acutes.get(atlr);
               marked = Util.replaceAtIndex(marked,diac,lr,1);
               primary = false;
            }
            else if (isVowel(atlr) && secondary) {
               String diac = macrons.get(atlr);
               marked = Util.replaceAtIndex(marked,diac,lr,1);
               secondary = false;
            }
            else if (atlr == 'ˈ' && lr > 0) {
                primary = true;
            }
            else if (atlr == 'ˌ' && lr > 0) {
                secondary = true;
            }
        }
        marked = Util.removeAll(Util.removeAll(marked,"ˌ"),"ˈ");
        marked = marked.replaceAll("ÿ","ẏ");
        marked = marked.replaceAll("ä", "ɑ̇");
        marked = marked.replaceAll("ë", "ė");
        marked = marked.replaceAll("ï", "i̇");
        marked = marked.replaceAll("ü", "u̇");
        marked = marked.replaceAll("ö", "ȯ");
        return marked;
    }
    public static String VowDiphth (String raw, Lemma morph, Dialect acnt) {
        String vowels = "";
        int sils = Lingua.countSyl(raw);
        boolean stressed = false;
        boolean nclfnd = false;
        Lemma base = Lemma.findBase(morph);
        String parsed = raw;
        while (base.getMthr() != null) {
            Lemma test = base.getSis();
            if (Lemma.isSuffix(test)) {
                Morphology morphology = new Morphology();
                Lemma nwfix = morphology.affAdj(Lemma.decReg(base,acnt),base.getUsage(),test);
                String trimmed = Phonology.shave(parsed);
                String nwfixpro = Prepare(Lemma.decReg(nwfix,acnt));
                int split = trimmed.lastIndexOf(Phonology.shave(nwfixpro));
                boolean isAlv = (Util.removeAll((test.getSpelling()),"~")).equals("s") && (Util.removeAll((test.getPronUK()),"~")).equals("z") && (test.getUsage() == 'V' || test.getUsage() == 'D' || test.getUsage() == 'N' || test.getUsage() == 'X') || (test.getSpelling()).equals("ed") && (test.getPronUK()).equals("d") && test.getUsage() == 'S';
                boolean isLY = (test.getSpelling()).equals("ly") && (test.getPronUK()).equals(".liː") && test.getUsage() == 'B';
                boolean endL = (Lemma.decReg(base,acnt)).endsWith("l") && Lingua.isVowel(trimmed.charAt(split-1));
                if (isLY && endL) {
                    parsed = Util.insertAtIndex(parsed,"l+",parsed.lastIndexOf(nwfixpro));
                    /*int piv = parsed.lastIndexOf(nwfixpro);
                    String ptl = parsed.substring(0,piv);
                    if (ptl.endsWith(".") && ptl.length() > 2 && Lingua.isVowel(ptl.charAt(piv-1))) {
                        parsed = Util.insertAtIndex(parsed,"l+",piv-1);
                    }*/
                }
                else if (isAlv) {
                    String bspr = Lemma.decReg(base,acnt);
                    char fine = bspr.charAt(bspr.length()-1);
                    int breik = parsed.lastIndexOf(nwfixpro);
                    if (!Lingua.isVoiced(fine) && breik >= 0) {
                        String prt1 = parsed.substring(0,breik);
                        String prt2 = parsed.substring(breik);
                        if (prt2.contains("s") &&!Lingua.isCorSib(fine)) {
                            nwfixpro = nwfixpro.replace("s","z");
                            prt2 = prt2.replace("s","z");
                            parsed = prt1+prt2;
                        }
                        if (prt2.contains("t") &&!Lingua.isAlvStop(fine)) {
                            nwfixpro = nwfixpro.replace("t","d");
                            prt2 = prt2.replace("t","d");
                            parsed = prt1+prt2;
                        }
                    }
                }
                String rhfix = nwfixpro.replaceAll("˞", "ɹ");
                try {
                    parsed = Util.insertAtIndex(parsed,"+",parsed.lastIndexOf(rhfix));
                    if (isAlv) {
                        String sfxed = (parsed.substring(parsed.lastIndexOf("+"))).replaceAll("ɪ", "ə");
                        parsed = parsed.substring(0,parsed.lastIndexOf("+"))+sfxed;
                    }
                }
                catch (StringIndexOutOfBoundsException stup) {
                    // do nothing;
                }
                //parsed = parsed.substring(0,parsed.lastIndexOf(nwfixpro))+"+"+nwfixpro.replace("ɪ","ə");
            }
            /*else if (Lemma.isPrefix(test)) {
                Lemma nwfix = Pronounce2.affAdj(Lemma.decReg(base,acnt),base.getUsage(),test);
                String nwfixpro = Prepare(Lemma.decReg(nwfix,acnt));
                parsed = Util.insertAtIndex(parsed,"+",parsed.indexOf(nwfixpro)+nwfixpro.length());
            }*/
            else {
                String nwfixpro = Prepare(Lemma.decReg(test,acnt));
                String bndpro = Util.removeAll(Util.removeAll(Util.removeAll(nwfixpro,"ˈ"),"ˌ"),".");
                if (Lemma.isPrefix(test)) {
                    parsed = Util.insertAtIndex(parsed,"-",parsed.indexOf(bndpro)+bndpro.length());
                }
                else {
                    parsed = Util.insertAtIndex(parsed,"+",parsed.indexOf(bndpro)+bndpro.length());
                }
            
            }
            base = base.getMthr();
        }
        for (int it = 0; it < parsed.length(); it++) {
            char eso = parsed.charAt(it);
            boolean end = it == parsed.length()-1 || it == parsed.length()-2 && parsed.charAt(parsed.length()-1) == 'ː';
            boolean border = !end && parsed.charAt(it+1)=='+';
            if (Lingua.isVowel(eso) || Character.isDigit(eso)) {
                nclfnd = true;
            }
            else if (Lingua.isConsonant(eso) && nclfnd) {
                if (stressed && nclfnd) {
                    stressed = false;
                }
                nclfnd = false;
            }
            else if (Lingua.isBound(eso)) {
                if (eso == 'ˈ' || eso == 'ˌ') {
                    stressed = true;
                }
                nclfnd = false;
            }
            boolean beforeN = it < parsed.length()-1 && parsed.charAt(it+1) == 'n' && (it == parsed.length()-2 || parsed.charAt(it+2) == '+');
            boolean mono = !parsed.contains("+") && parsed.length() == 1 || parsed.contains("+") && (parsed.substring(0,parsed.lastIndexOf("+"))).length() == 1;
            if (eso == 'i') {
                int vwl = Phonology.toNextVowel(parsed, it+1);
                if (sils > 1 && (end || border) && !stressed) {
                    vowels += 'y';
                }
                else if (end && stressed || vwl < 1 && Lingua.nextVowel(parsed, it+1) != 'ɪ') {
                    vowels += 'e';
                }
                else {
                    vowels += "ė";
                }
            }
            else if (eso == 'ɔ') {
                if (beforeN || end || border || Phonology.toNextVowel(parsed, it+1) < 1) {
                    vowels += "ɑw";
                }
                else {
                    vowels += "ɑu";
                }
            }
            else if (eso == '1') {
                if ((end || border) && raw.length() > 1 && !mono && sils == 1) {
                    vowels += "y";
                }
                else if ((end || border) && raw.length() > 1 && !mono) {
                    vowels += "ẏ";
                }
                else {
                    vowels += "ı̇";
                }
            }
            else if (eso == '2') {
                if (beforeN || end || border || Phonology.toNextVowel(parsed,it+1) < 1) {
                    vowels += "ow";
                }
                else {
                    vowels += "ou";
                }
            }
            else if (eso == '3') {
                if (end || border) {
                    vowels += "ɑy";
                }
                else {
                    vowels += "ɑ̇";
                }
            }
            else if (eso == '4') {
                if (end || border || Phonology.toNextVowel(parsed, it+1) < 1) {
                    vowels += "oy";
                }
                else {
                    vowels += "oı";
                }
            }
            else if (eso == '5') {
                if (end) {
                    vowels += "o";
                }
                else {
                    vowels += "ȯ";
                }
            }
            else if (eso != '+' && eso != '-') {
                vowels += eso;
            }
        }
        return vowels;
    }
    public static List<Lemma> reconWH (List<Lemma> whats) {
        List<Lemma> whs = new ArrayList<>();
        for (int wt = 0; wt < whats.size(); wt++) {
            Lemma whadj = reconWH(whats.get(wt));
            whs.add(whadj);
        }
        return whs;
    }
    public static Lemma reconWH (Lemma what) {
        Lemma recon = what;
        if (what.structured()) {
            Lemma thyg1 = reconWH(what.getDghtr1());
            Lemma thyg2 = reconWH(what.getDghtr2());
            recon.setDghtr1(thyg1);
            recon.setDghtr2(thyg2);
            if (!Lemma.isSuffix(thyg2) && (thyg1.getPronUK()).startsWith("ʍ")) {
                String prob1;
                String prob2;
                if ((thyg1.getPronUK()).startsWith("ˈ") || (thyg1.getPronUK()).startsWith("ˌ")) {
                    prob1 = ((thyg1.getPronUK()).substring(1)).replaceAll("ʍ","w");
                    prob2 = ((thyg1.getPronUK()).substring(1)).replaceAll("ʍ","w");
                }
                else {
                    prob1 = (thyg1.getPronUK()).replaceAll("ʍ","w");
                    prob2 = (thyg1.getPronUS()).replaceAll("ʍ","w");
                }
                try {
                    String stub1 = (recon.getPronUK()).substring(0,(recon.getPronUK()).lastIndexOf(prob1));
                    String stub2 = (recon.getPronUK()).substring((recon.getPronUK()).lastIndexOf(prob1)+1);
                    recon.setPronUK(stub1+"ʍ"+stub2);
                    stub1 = (recon.getPronUS()).substring(0,(recon.getPronUS()).lastIndexOf(prob2));
                    stub2 = (recon.getPronUS()).substring((recon.getPronUS()).lastIndexOf(prob2)+1);
                    recon.setPronUS(stub1+"ʍ"+stub2);
                }
                catch (StringIndexOutOfBoundsException raro) {
                    //do nothing
                }
            }
        }
        else {
            String strip1 = Util.removeAll(Util.removeAll((what.getPronUK()),"ˈ"),"ˌ");
            String strip2 = Util.removeAll(Util.removeAll((what.getPronUS()),"ˈ"),"ˌ");
            if ((what.getSpelling()).startsWith("wh") && strip1.startsWith("w") && strip2.startsWith("w")) {
                recon.setPronUK((what.getPronUK()).replace("w", "ʍ"));
                recon.setPronUS((what.getPronUS()).replace("w", "ʍ"));
            }
        }
        return recon;
    }
    public static String repExempt (String ipa, String resp, boolean str, Lemma cmpsn, Dialect accent) {
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
                    target = Translit(preserve.substring(0,preserve.length()-1),str,cmpsn,accent);
                }
                else {
                    target = Translit(preserve,str,cmpsn,accent);
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
    public static String Translit (String ipa, boolean str, Lemma cmpsn, Dialect accent) {
        String ks = Prepare(ipa);
        //ks = Util.removeAll(ks,"~");
        ks = ks.replaceAll("ɛəɹ", "ɛɹ");
        ks = ks.replaceAll("ɪəɹ", "ɪɹ");
        ks = ks.replaceAll("ʊəɹ", "ʊɹ");
        ks = ks.replaceAll("ɛə˞", "ɛɹ");
        ks = ks.replaceAll("ɪə˞", "ɪɹ");
        ks = ks.replaceAll("ʊə˞", "ʊɹ");
        ks = ks.replaceAll("ə˞", "əɹ");
        ks = ks.replaceAll("ɜ˞", "ɜɹ");
        ks = ks.replaceAll("ɑ˞", "ɑɹ");
        ks = ks.replaceAll("ɔ˞", "oɹ");
        ks = ks.replaceAll("ɜː", "ɜɹ");
        ks = ks.replaceAll("ɛə", "ɛɹ");
        ks = ks.replaceAll("ɪə", "ɪɹ");
        ks = ks.replaceAll("ʊə", "ʊɹ");
        ks = ks.replaceAll("wɒ", "wa");
        ks = ks.replaceAll("ŋk", "nk");
        ks = ks.replaceAll("ŋg", "ng");
        ks = ks.replaceAll("ŋˈk", "nˈk");
        ks = ks.replaceAll("ŋˈg", "nˈg");
        ks = ks.replaceAll("ŋˌk", "nˌk");
        ks = ks.replaceAll("ŋˌg", "nˌg");
        ks = ks.replaceAll("ɑ", "a");
        ks = VowDiphth(ks,cmpsn,accent);
        ks = ks.replaceAll("jʊ","ʋ̇");
        ks = ks.replaceAll("ʊ", "ʋ");
        ks = ks.replaceAll("ju","u̇");
        ks = ks.replaceAll("ɜɹ", "ʊr");
        ks = ks.replaceAll("ʌ", "ʊ");
        ks = ks.replaceAll("ɛ", "e");
        ks = ks.replaceAll("ɪ", "ı");
        ks = ks.replaceAll("ɒ", "o");
        ks = ks.replaceAll("æ", "ɑ");
        ks = ks.replaceAll("Q", "ch");
        ks = ks.replaceAll("ð", "th");
        ks = ks.replaceAll("ʒ", "zh");
        ks = ks.replaceAll("θ", "ћ");
        ks = ks.replaceAll("ŋ", "ŋ");
        ks = ks.replaceAll("ʃ", "sh");
        ks = ks.replaceAll("x", "cɦ");
        ks = ks.replaceAll("ɹ", "r");
        ks = ks.replaceAll("j", "y");
        ks = ks.replaceAll("G", "j");
        ks = ks.replaceAll("ʔ", "'");
        ks = ks.replaceAll("ks", "x");
        ks = ks.replaceAll("kˈs", "xˈ");
        ks = ks.replaceAll("kˌs", "xˌ");
        ks = ks.replaceAll("ʍ","wh");
        int syllables = Lingua.countSyl(ks);
        if (str && syllables > 1) {
            ks = MapStress(ks);
        }
        else {
            ks = Util.removeAll(Util.removeAll(ks,"ˈ"),"ˌ");
        }
        ks = ks.replaceAll("ː", "");
        ks = repExempt(ipa,ks,str,cmpsn,accent);
        return ks;
    }
    public static boolean isFilled (List<String> data) {
        if (data.isEmpty() || data.size() == 1 && ((data.get(0)).equals("000") || (data.get(0)).equals(""))) {
            return false;
        }
        else {
            return true;
        }
    }
    public static String printKS (List<String> info, boolean pros, List<Lemma> entire, boolean capit, Dialect accent) {
        String list = "";
        List<String> data = info;
        String item = (entire.get(0)).getSpelling();
        if (!isFilled(info)) {
            return "[NOT FOUND]";
        }
        else if (sight.containsKey(item)) {
            if (capit) {
                list = Util.Capital(sight.get(item),0);
            }
            else {
                list = sight.get(item);
            }
            return list;
        }
        else if (!pros && info.size() > 1) {
            List<String> nostr = new ArrayList<>();
            for (int f = 0; f < info.size(); f++) {
                String dxtr = Phonology.prepIPA(info.get(f),pros);
                if (!nostr.contains(dxtr)) {
                    nostr.add(dxtr);
                }
            }
            data = nostr;
        }
        /*for (int dt = 0; dt < data.size(); dt++) {      //loop for inflectional constancy
            String dstr = Pronounce2.prepIPA(info.get(dt),pros);
            String suf = (Pronounce2.presinfl).get(dt);
            String apst = Util.removeAll(dstr, "~");
            Lemma compst = entire.get(dt);
            /*if (dstr.startsWith("w") && orgnl.startsWith("wh")) {
                dstr = "ʍ"+dstr.substring(1);
            }
            if (!suf.equals("0FLS0") && apst.length() > 1 && apst.charAt(apst.length()-2) == 'ɪ') { 
                dstr = Util.replaceAtIndex(dstr,"ə",dstr.lastIndexOf('ɪ'),1);
            }
            else if (!suf.equals("0FLS0") && (dstr.endsWith("s~") || dstr.endsWith("s"))) {
                dstr = Util.replaceAtIndex(dstr, "z", dstr.lastIndexOf('s'), 1);
            }
            else if (!suf.equals("0FLS0") && (dstr.endsWith("t"))) {
                dstr = Util.replaceAtIndex(dstr, "d", dstr.lastIndexOf('t'), 1);
            }
            data.set(dt,dstr);
        }*/
        if (data.size() == 1) {
            if (capit) {
                list += Util.Capital(Translit(data.get(0), pros, entire.get(0), accent),0);
            }
            else {
                String nov = Translit(data.get(0), pros, entire.get(0), accent);
                list += ((nov.replaceAll("ı̇́","i̇́")).replaceAll("ı̇̄","i̇̄")).replaceAll("ı̇", "i");
            }
        }
        else if (data.size() > 1) {
            list = "|";
            for (int f = 0; f < data.size(); f++) {
                if (capit) {
                    list += Util.Capital(Translit(data.get(f), pros, entire.get(0), accent),0)+"|";
                }
                else {
                    String nov = Translit(data.get(f), pros, entire.get(0), accent);
                    list += ((nov.replaceAll("ı̇́","i̇́")).replaceAll("ı̇̄","i̇̄")).replaceAll("ı̇", "i")+"|";
                }
            }
        }
        return list;
    }
    public static void initialize() {
        Diacritics();
        sightWords();
    }
}