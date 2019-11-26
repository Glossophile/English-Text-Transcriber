package convert17;
import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
public class DictReader {
    public static boolean dotted = false;
    public static boolean missing = true;
    public static boolean export = true;
    private static int reclim = 0;
    private static String query;
    public static String addr = "http://dictionary.cambridge.org/dictionary/english/";
    public static boolean isAvail (String prob) {
        return (Morphology.dict).dictContains(prob) || Util.listContains(prob,Morphology.ablauted) || Util.listContains(prob,TextParser.customs) || isHead(prob);
    }
    public static boolean isRecog (String prob, String acnt, boolean ignore) {
        return (Morphology.dict).dictContains(prob) || Util.listContains(prob,Morphology.ablauted) || Util.listContains(prob,TextParser.customs) || isWord(prob,acnt,ignore);
    }
    public static boolean isWord (String prop, String ac, boolean ignore) {
        boolean stat = false;
        try {
            String propcap = Character.toUpperCase(prop.charAt(0))+prop.substring(1);
            String propup = prop.toUpperCase();
            Document testing = Jsoup.connect(addr+prop).get();
            if (addr.equals(testing.location())) {          //added 1/25/2017
                IOException notfound = new IOException();
                throw notfound;
            }
            String title = testing.title();
            if (title.startsWith(prop) || title.startsWith(propcap) || title.startsWith(propup) || Util.removeMarks(title).startsWith(prop) || Util.removeMarks(title).startsWith(propcap)) {
                if ((testing.html()).contains("ipa dipa")) {
                    stat = true;
                    return stat;    //added 7/18/2019 for "hasty" -> "hastily"
                }
            }
            /*else {
                Elements lemmas = testing.select("span[class]");
                for (Element lemma: lemmas) {
                    String lmcls = lemma.className();
                    if (lmcls.equals("w") && (lemma.text()).equals(prop)) {
                        stat = true;
                    }
                }
            }*/
            if (stat) {
                Elements divs = testing.select("div[data-id]");
                String label = prop;
                String prevlb = "";
                for (Element div: divs) {
                    String dvid = div.id();
                    if (dvid.equals("cald4")) {
                        Elements lemmas = (div.children()).select("span[class]");
                        for (Element lemma: lemmas) {
                            String lmcls = lemma.className();
                            if (lmcls.equals("ipa dipa")) {
                                Elements ancs = lemma.parents();
                                for (Element anc:ancs) {
                                    String ancl = anc.className();
                                    if (ancl.equals("pos-header dpos-h")) {
                                        String  actlb = getLabel(anc);
                                        if (actlb.equals("")) {
                                            label = prevlb;
                                        }
                                        else {
                                            label = actlb;
                                            prevlb = label;
                                        }
                                    }
                                }
                                String lem = lemma.text();
                                if (lem.contains("·")) {
                                    dotted = true;
                                    export = false;
                                    if (ignore) {
                                        stat = false;
                                    }
                                }
                                else if (!label.equals(prop)) {
                                    stat = false;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        catch (IOException notfound) {
            return false;
            /*int possuf = Morphology.findMB(prop,'s');
            int pospre = Morphology.findMB(prop,'p');
            String decomp = prop;
            while (reclim < 5) {
                if (possuf <= 0 && pospre <= 0 && Morphology.sufLat(decomp,'w') >= 0) {
                    decomp = Morphology.adjLat(decomp);
                    if (isHead(decomp)) {
                        break;
                    }
                }
                try {
                    Document testing = Jsoup.connect(addr+decomp).get();
                    Elements lemmas1 = testing.select("h3[class]");
                    if (lemmas1.hasClass("runon-title")) {
                        for (Element lemma1: lemmas1) {
                            String lmcl1 = lemma1.className();
                            if (lmcl1.equals("runon-title")) {
                               Elements lemmas2 = (lemma1.children()).select("b[class]");
                               for (Element lemma2: lemmas2) {
                                   String lmcl2 = lemma2.className();
                                   if (lmcl2.equals("w") && (lemma2.text()).equals(prop)) {
                                       stat = true;
                                   }
                               }
                            }
                        }
                    }
                    reclim++;
                }
                catch (IOException noencontrado) {
                    stat = false;
                    reclim++;
                }
            }*/
        }
        catch (StringIndexOutOfBoundsException no) {
            return false;
        }
        return stat;
    }
    public static boolean isHead (String prop) {
        String addr = "http://dictionary.cambridge.org/dictionary/english/";
        String site = addr+Util.removeMarks(prop);
        try {
            Document doc = Jsoup.connect(site).get();
            String dblchk = (doc.location()).replace("https:","http:");
            if (!dblchk.equals(site) || (doc.getElementsByClass("pron")).size() == 0) {
                return false;
            }
            else {
                return true;
            }
        }
        catch (IOException notfound) {
            return false;
        }
    }
    public static boolean isUnder (String sub, String prop) {
        String addr = "http://dictionary.cambridge.org/dictionary/english/";
        String site = addr+Util.removeMarks(prop);
        try {
            Document doc = Jsoup.connect(site).get();
            String dblchk = (doc.location()).replace("https:","http:");
            if (!dblchk.equals(site)) {
                return false;
            }
            else {
                boolean answer = false;
                Elements tabs = doc.select("div[id]");
                for (Element tab: tabs) {
                    String tbid = tab.id();
                    if (tbid.startsWith("dataset-") && !tbid.endsWith("-business")) {
                        Elements elems = (tab.children()).select("div[class]");
                        for (Element elem1: elems) {
                            String h2cl = elem1.className();
                            Elements heads = (elem1.siblingElements()).select("h3[class]");
                            if (heads.hasClass("runon-title")) {
                                for (Element elem2: heads) {
                                    String spcl1 = elem2.className();
                                    if (spcl1.equals("runon-title")) {
                                        Elements subelms = (elem2.children()).select("b[class]");
                                        for (Element subelm: subelms) {
                                            String sbcl = subelm.className();
                                            if (sbcl.equals("w")) {
                                                String get = elem2.text();
                                                if (get.equals(sub)) {
                                                    answer = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
                return answer;
            }
        }
        catch (IOException notfound) {
            return false;
        }
    }
    public static Element omit (Element over, String label, String tag) {
        Element stream = over;
        String code = stream.html();
        while (code.contains(tag)) {
            int hwaer = code.indexOf(tag);
            int pou = (code.substring(0,hwaer)).lastIndexOf("<"+label);
            int thr = hwaer+tag.length();
            int expn = 1;
            while (expn > 0 && thr < code.length()-1) {
                if (code.charAt(thr) == '<') {
                    if (thr < code.length()-2 && code.charAt(thr+1) == '/') {
                        expn--;
                    }
                    else if (thr < code.length()-1) {
                        expn++;
                    }
                    int bknd = code.indexOf(">",thr);
                    thr = bknd+1;
                }
                else {
                    thr++;
                }
            }
            String gunk = code.substring(pou,thr);
            //String gunk = code.substring(pou,code.indexOf(close,hwaer)+7);
            //System.out.println(gunk);
            code = Util.removeAll(code,gunk);
        }
        stream.html(code);
        return stream;
    }
    public static Element realign (Element over, String label) {
        Element stream = over;
        String code = stream.html();
        String opn = "<"+label+">";
        String cls = "</"+label+">";
        while (code.contains(opn)) {
            int pret = code.indexOf(opn);
            int thr = (code.substring(0,pret)).lastIndexOf(cls);
            String gunk = code.substring(thr,pret+opn.length());
            code = Util.removeAll(code,gunk);
        }
        stream.html(code);
        return stream;
    }
    /*public static Element strip (Element over, String label, String name) {
        Element stripped = over;
        Elements sublms = (stripped.children()).select(label);
        for (Element sblm: sublms) {
            String slcl = sblm.className();
            if (slcl.equals(name)) {
                sblm.empty();
                sblm.text("useless gobbledygook");
            }
        }
        return stripped;
    }*/
    public static boolean equal (String label1, String label2) {
        if (label1.equalsIgnoreCase(label2)) {
            return true;
        }
        else{
            String edlab1 = Util.removeMarks(label1);
            String edlab2 = Util.removeMarks(label2);
            if (edlab1.equals(edlab2)) {
                return true;
            }
            else {
                return false;
            }
        }
    }
    public static boolean equalIgnoreCase (String label1, String label2) {
        if (label1.equals(label2)) {
            return true;
        }
        else{
            String edlab1 = Util.removeMarks(label1);
            String edlab2 = Util.removeMarks(label2);
            if (edlab1.equalsIgnoreCase(edlab2)) {
                return true;
            }
            else {
                return false;
            }
        }
    }
    public static String getLabel (Element poshead) {
        Elements kids = (poshead.children()).select("div[class]");
        boolean found = false;
        String sbpr = "";
        String key = "";
        String tmp = "";
        for (Element kid: kids) {
            String kidcl = kid.className();
            if (kidcl.equals("di-title")) {
                Elements heads = (kid.children()).select("span[class]");
                for (Element head: heads) {
                    String hdcls = head.className();
                    if (hdcls.startsWith("headword hdb")) {// && hdcls.endsWith("dpos-h_hw")) {
                        Elements hdwrds = (head.children()).select("span[class]");
                        for (Element hdwrd: hdwrds) {
                            String hwcls = hdwrd.className();
                            if (hwcls.equals("hw dhw")) {
                                key = hdwrd.text();
                                found = true;
                            }
                        }
                    }
                }
            }
        }
        /*if (!found) {
            Elements sibs = (poshead.siblingElements()).select("h3[class]");
            for (Element sib: sibs) {
                String sbcl = sib.className();
                if (sbcl.equals("runon-title")) {
                    Elements sbwds = (sib.children()).select("b[class]");
                    for (Element sbwd: sbwds) {
                        String swcls = sbwd.className();
                        if (swcls.equals("w")) {
                            key = sbwd.text();
                            found = true;
                        }
                    }
                }
            }
        }*/
        kids = (poshead.children()).select("span[class]");
        for (Element kid: kids) {
            String cls = kid.className();
            if ((cls.equals("uk dpron") || cls.equals("us dpron")) && !found) {
                Elements pros = (kid.children()).select("span[class]");
                for (Element pro: pros) {
                    String prcl = pro.className();
                    if (prcl.equals("pron dpron")) {
                        Elements phons = (pro.children()).select("span[class]");
                        for (Element phon: phons) {
                            String phcl = phon.className();
                            if (phcl.equals("ipa dipa")) {
                                sbpr = phon.text();
                            }
                        }
                    }
                }
            }
            else if (cls.equals("var") && (poshead.className()).equals("pron-info")) {
                Elements vars = (kid.children()).select("span[class]");
                for (Element var: vars) {
                    String vcl = var.className();
                    if (vcl.equals("v")) {
                        key = var.text();
                        found = true;
                    }
                    else if (vcl.equals("lab")) {
                        String lbtx = var.text();
                    }
                    else if (vcl.equals("pron-info")) {
                        Elements sbpns = (kid.children()).select("span[class]");
                        for (Element sbpn: sbpns) {
                            String spcl = sbpn.className();
                            if (spcl.equals("uk") || spcl.equals("us")) {
                                Elements prns = (sbpn.children()).select("span[class]");
                                for (Element prn: prns) {
                                    String pncl = prn.className();
                                    if (pncl.equals("pron")) {
                                        Elements spns = (prn.children()).select("span[class]");
                                        for (Element spn: spns) {
                                            String sncl = spn.className();
                                            if (sncl.equals("ipa")) {
                                                String ph = spn.text();
                                                //System.out.println("Variant: "+key+" "+ph);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }     
            }
        }
        if (!found && ((poshead.parent()).className()).equals("pos-header")) {
            key = getLabel(poshead.parent());
        }
        return key;
    }
    public static String getAccent (Element phon) {
        Element acc = (phon.parent()).parent();
        Elements acnts = (acc.siblingElements()).select("span[class]");
        String accent = "";
        for (Element acnt: acnts) {
            String acls = acnt.className();
            if (acls.equals("uk dpron") || acls.equals("us dpron")) {
                accent = acls.substring(0,acls.indexOf(" "));
            }
        }
        if (accent.equals("")) {
            Element altacnt = (((phon.parent()).parent()).parent()).previousElementSibling();
            Elements altacs = (altacnt.children()).select("span[class]");
            for (Element altac: altacs) {
                String alcls = altac.className();
                if (alcls.equals("uk dpron") || alcls.equals("us dpron")) {
                    Elements pros = (altac.children()).select("span[class]");
                    for (Element pro: pros) {
                        String prcls = pro.className();
                        if (prcls.equals("pron dpron")) {
                            Elements iphs = (pro.children()).select("span[class]");
                            for (Element iph: iphs) {
                                String phcls = iph.className();
                                if (phcls.equals("ipa dipa")) {
                                    accent = getAccent(iph);
                                }
                            }
                        }
                    }
                }
            }
        }
        return accent;
    }
    private static String dial = "";
    public static String parsePron (Element info) {
        String sound = "";
        Elements pronins = (info.children()).select("span[class]");
        for (Element pronin: pronins) {                                         //1-1-2019 for (Element reg: regs) {
            String plcls = pronin.className();                                  //1-1-2019 String plcls = reg.className();
            if (plcls.equals("pron dpron")) {
                Elements prons = (pronin.children()).select("span[class]");     //1-1-2019 Element prons = (pronin.children()).select("span[class");
                for (Element pron: prons) {
                    String prcls = pron.className();
                    if (prcls.startsWith("ipa dipa")) {
                        String curac = info.className();                         //1-1-2019 String curac = getAccent(pron)
                        curac = curac.substring(0,curac.indexOf(" "));
                        if (curac.equals("")) {
                            curac = "cmn";
                        }    
                        String temp = ((pron.html()).replaceAll("<span class=\"sp dsp\">r</span>","r")).replaceAll("<span class=\"sp dsp\">ə</span>","]");
                        String ipa = "<ipa>"+temp+"</ipa>";
                        if (dial.equals("") && temp.contains("·")) {
                            dial = "us";
                            curac = "us";
                            sound = "<us>"+ipa;
                        }
                        else if (dial.equals("")) {                                  //removed 1-1-2019
                            sound = "<"+curac+">"+ipa;
                        }
                        else if (!curac.equals(dial)) {
                            sound += "<"+curac+">"+ipa;
                        }
                        else {
                            sound += ipa;
                        }
                        sound += "</"+curac+">";
                        dial = curac;
                        //System.out.println("Testing..."+ipa);
                    }
                }
            }
        }
        return sound;
    }
    private static String grmhd = "";
    private static String varac = "";
    private static boolean variant = false;
    private static Map<String,String> cache = new TreeMap<>();
    public static String parsePosHdr (Element poshdr) {
        String pro = "";
        String gram = "";
        String altr = "";
        String info = "";
        boolean take = true;
        boolean abrv = false;
        String varspell = "";
        String varpron = "";
        String bacnt = dial;
        String bvcnt = varac;
        String bam = grmhd;
        variant = false;
        dial = "";
        grmhd = "";
        Element pronc = omit(omit(poshdr,"span","class=\"circle circle-btn sound audio_play_button\">"),"div","class=\"share rounded js-share\"");
        pronc = realign(pronc,"span");
        String code = pronc.toString();
        Elements pgrams = (pronc.children()).select("div[class]");
        for (Element pgram: pgrams) {
            String pgmcls = pgram.className();
            if (pgmcls.startsWith("posgram dpos-g")) {
                Elements subs1 = (pgram.children()).select("span[class]");
                for (Element sub1: subs1) {
                    String sb1cls = sub1.className();
                    if (sb1cls.equals("pos dpos")) {
                        //System.out.println("Testing..."+sub1.text());
                        if (!gram.equals("")) {
                            gram += "</pos><pos>"+sub1.text();
                        }
                        else {
                            gram += "<pos>"+sub1.text();
                        }
                        grmhd = sub1.text();
                    }
                    else if (sb1cls.equals("gram dgram")) {
                        Elements ahrefs = (sub1.children()).select("a[href*=/help/codes.html]");
                        for (Element ahref: ahrefs) {
                            Elements subs2 = (ahref.children()).select("span[class]");
                            for (Element sub2: subs2) {
                                String sb2cls = sub2.className();
                                if (sb2cls.equals("gc dgc") && !gram.contains("-")) {
                                    String mu = (Util.removeAll(Util.removeAll(sub2.text(),"]"),"[")).trim();
                                    gram += "-"+mu+"</pos>";
                                }
                            }
                        }
                    }
                }
            }
            if (!gram.equals("") && !gram.endsWith("</pos>")) {
                gram += "</pos>";
            }
        }
        Elements spans = (poshdr.children()).select("span[class]");
        for (Element span: spans) {
            String spcls = span.className();
            if (spcls.equals("irreg-infls dinfls")) {
                String inflpro = "";
                Elements irifs = (span.children()).select("span[class]");
                for (Element irif: irifs) {
                    String ifcls = irif.className();
                    if (ifcls.equals("inf-group dinfg")) {
                        Elements groups = (irif.children()).select("span[class]");
                        for (Element group: groups) {
                            String grpcls = group.className();
                            if (grpcls.equals("lab dlab")) {
                                altr += "<lab>"+group.text()+"</lab>";
                            }
                            else if (grpcls.startsWith("uk dpron") || grpcls.startsWith("us dpron")) {
                                String bdl = dial;
                                dial = "";
                                String alpr = parsePron(group);
                                if (alpr.endsWith("</ipa>")) {
                                    inflpro += alpr+"</"+dial+">";
                                }
                                else if (!alpr.equals("")) {
                                    inflpro += alpr;
                                }
                                dial = bdl;
                            }
                        }
                        Elements bclss = (irif.children()).select("b[class]");
                        for (Element bcls: bclss) {
                            String bcln = bcls.className();
                            if (bcln.equals("inf dinf")) {
                                altr += "<spell>"+bcls.text()+"</spell>";
                            }
                        }
                    }
                }
                if (!inflpro.equals("")) {
                    inflpro = "<pron>"+inflpro+"</pron>";
                }
                altr = "<altr>"+altr+inflpro+"</altr>";
                take = false;
            }
            else if (spcls.equals("pos dsense_pos") && gram.equals("")) {
                //System.out.println("Testing..."+sub1.text());
                if (!gram.equals("") && !gram.endsWith("</pos>")) {
                    gram += "</pos><pos>"+span.text();
                }
                else {
                    gram += "<pos>"+span.text();
                }
            }
            else if (spcls.equals("dgram") && !gram.contains("-")) {
                String gramc = span.text();
                String mu = (Util.removeAll(Util.removeAll(gramc,"]"),"[")).trim();
                gram += "-"+mu;
            }
            else if ((spcls.startsWith("uk dpron") || spcls.startsWith("us dpron")) && take) {
                //System.out.println("Testing...");
                pro += parsePron(span);
            }
            else if (spcls.equals("lab dlab") && !equal(getLabel(poshdr),query)) {
                String lbtxt = span.text();
                if (lbtxt.equals("also") && !pro.equals("")) {
                    take = false;
                }
            }
            else if (spcls.equals("spellvar dspellvar") && !equal(getLabel(poshdr),query)) {
                if (!pro.equals("")) {
                    take = false;
                }
                Elements vars = (span.children()).select("span[class]");
                for (Element var: vars) {
                    String vcl = var.className();
                    if (vcl.startsWith("v dv") && !abrv) {
                        varspell = var.text();
                        /*Element sib = var.nextElementSibling();
                        if ((sib.className()).startsWith("uk dpron") || (sib.className()).startsWith("us dpron")) {
                            varpron = parsePron(sib);
                            Elements vchs =(sib.children()).select("span[class]");
                            for (Element vch: vchs) {
                                String psac = vch.className();
                                if (psac.startsWith("uk dpron") || psac.startsWith("us dpron")) {
                                    varac = psac.substring(0,psac.indexOf(" "));
                                }
                            }
                            varpron += "</"+varac+">";
                        }*/
                        variant = true;                                         //moved from above if-clause 10/6/2019
                    }
                    else if (vcl.equals("lab dlab")) {
                        Elements labs = (var.children()).select("span[class]");
                        for (Element lab: labs) {
                            String lbcl = lab.className();
                            if (lbcl.equals("usage dusage") && (lab.text()).equals("written abbreviation")) {
                                abrv = true;
                            }
                        }
                    }
                }
            }
            else if (spcls.equals("spellvar dspellvar")) {
                Elements vars = (span.children()).select("span[class]");
                for (Element var: vars) {
                    String vcl = var.className();
                    if (vcl.equals("lab dlab")) {
                        Elements labs = (var.children()).select("span[class]");
                        for (Element lab: labs) {
                            String lbcl = lab.className();
                            if (lbcl.equals("usage dusage") && (lab.text()).equals("short form") && !pro.equals("")) {
                                take = false;
                            }
                        }
                    }
                }
            }
        }
        if (!gram.equals("") && !gram.endsWith("</pos>")) {
            gram += "</pos>";
        }
        if (pro.equals("")) {
            try {
                pro = cache.get(getLabel(poshdr));
            }
            catch (Exception empt) {
                //do nothing
            }
        }
        if (!pro.endsWith("</pron>\n")) {
            pro = "<pron>"+pro+"</pron>\n";
        }
        pro = (pro.replaceAll("</us><ipa>","<ipa>")).replaceAll("</uk><ipa>","<ipa>");
        gram = gram.replaceAll("</pos></pos>","</pos>");
        altr = altr.replaceAll("<altr><altr>","<altr>");
        gram = "<gram>"+gram+"</gram>\n";
        String label = getLabel(poshdr);
        cache.put(label,pro);
        if (variant) {
            String exspel = "<spell>"+varspell+"</spell>";
            /*String expro = "<pron>"+varpron+"</pron>\n";
            String extra = exspel+"\n"+expro+gram+altr;*/
            String extra = exspel+"\n"+pro+gram+altr;
            if (!equalIgnoreCase(label,query) && (equalIgnoreCase(varspell,Morphology.varstem) || equalIgnoreCase(varspell,query))) {                              //for "atheistically"
                info = extra;
            }
            else {
                info = "<spell>"+label+"</spell>\n"+pro+gram+altr+"</subentry>\n<subentry>\n"+extra;
            }
        }
        else {
            info = "<spell>"+query+"</spell>\n"+pro+gram+altr;
        }
        dial = bacnt;
        varac = bvcnt;
        grmhd = bam;
        info = info.trim();
        //System.out.println(info);
        return info;
    }
    public static String parseEntry (Element holder) {
        String pos = "";
        String ref = "";
        //System.out.println(holder.html());
        Elements phdrs = (holder.children()).select("div[class]");
        for (Element phdr: phdrs) {
            String phdrcls = phdr.className();
            String label = getLabel(phdr);
            if (phdrcls.equals("pos-header dpos-h")) {
                String subentry = "<subentry>\n"+parsePosHdr(phdr)+"\n</subentry>\n";
                if (equalIgnoreCase(label,query) || variant) {
                    pos += subentry;
                }
            }
            else if (phdrcls.equals("pos-body")) {
                Elements bods = (phdr.children()).select("div[class]");
                for (Element bod: bods) {
                    String bdcls = bod.className();
                    if (bdcls.startsWith("pr dsense")) {     //formerly "sense-block"
                        Elements subpos1 = (phdr.children()).select("div[class]");
                        for (Element sbps1: subpos1) {
                            String sbcl1 = sbps1.className();
                            if (sbcl1.equals("sense-body dsense_b")) {
                                Elements subpos2 = (sbps1.children()).select("div[class]");
                                for (Element sbps2: subpos2) {
                                    String sbcl2 = sbps2.className();
                                    if (sbcl2.equals("def-block ddef_block")) {
                                        Elements subpos3 = (sbps2.children()).select("div[class]");
                                        for (Element sbps3: subpos3) {
                                            String sbcl3 = sbps3.className();
                                            if (sbcl3.equals("ddef_h")) {
                                                Elements subpos4 = (sbps3.children()).select("div[class]");
                                                for (Element sbps4: subpos4) {
                                                    String sbcl4 = sbps4.className();
                                                    if (sbcl4.equals("def ddef_d")) {
                                                        String reftex = "";
                                                        Elements subpos5 = (sbps4.children()).select("span[class]");
                                                        for (Element sbps5: subpos5) {
                                                            String sbcl5 = sbps5.className();
                                                            if (sbcl5.equals("lab dlab")) {
                                                                reftex = sbps5.ownText();
                                                                //System.out.println(reftex);
                                                                Elements labs = (sbps5.children()).select("span[class]");
                                                                for (Element lab: labs) {
                                                                    String lbcl = lab.className();
                                                                    if (lbcl.equals("region dregion")) {
                                                                        reftex = lab.ownText()+" "+reftex;
                                                                        //System.out.println("Testing..."+reftex);
                                                                    }
                                                                    else if (lbcl.equals("usage dusage")) {
                                                                        reftex = lab.ownText()+" "+reftex;
                                                                    }
                                                                }
                                                            }
                                                            /*else if (sbcl5.equals("x")) {
                                                                Elements subpos6 = (sbps5.children()).select("a[class]");
                                                                for (Element sbps6: subpos6) {
                                                                    String sbcl6 = sbps6.className();
                                                                    if (sbcl6.equals("Ref")) {
                                                                        Elements subpos7 = (sbps6.children()).select("span[class]");
                                                                        for (Element sbps7: subpos7) {
                                                                            String sbcl7 = sbps7.className();
                                                                            if (sbcl7.equals("x-h")) {
                                                                                String altspell = sbps7.text();
                                                                                reftex += "<vdt>"+altspell+"</vdt>";
                                                                                if (reftex.contains("spelling") || reftex.contains("plural") || reftex.contains("past")) {
                                                                                    ref = "<note>"+reftex+"</note>";
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                Elements subpos7 = (sbps5.children()).select("a[class]");
                                                                for (Element sbps7: subpos7) {
                                                                    String sbcl7 = sbps7.className();
                                                                    if (sbcl7.equals("Ref")) {
                                                                        Elements subpos8 = (sbps7.children()).select("span[class]");
                                                                        for (Element sbps8: subpos8) {
                                                                            String sbcl8 = sbps8.className();
                                                                            if (sbcl8.equals("x-h")) {
                                                                                String altspell = sbps8.text();
                                                                                reftex += " <vdt>"+altspell+"</vdt>";
                                                                                //System.out.println("Testing..."+reftex);
                                                                                if (reftex.contains("plural")) {
                                                                                    ref = "<note>"+reftex+"</note>";
                                                                                    //System.out.println("Testing..."+ref);
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }*/
                                                        }
                                                    }
                                                }
                                                subpos4 = (sbps3.children()).select("span[class]");
                                                for (Element sbps4: subpos4) {
                                                    String sbcl4 = sbps4.className();
                                                    if (sbcl4.equals("def-info ddef-info")) {
                                                        Elements subpos5 = (sbps4.children()).select("span[class]");
                                                        for (Element sbps5: subpos5) {   
                                                            String sbcls1 = sbps5.className();
                                                            if (sbcls1.equals("gram dgram")) {
                                                                Elements subpos6 = (sbps5.children()).select("a[href*=/help/codes.html]");
                                                                for (Element sbps6: subpos6) {
                                                                    Elements subpos7 = (sbps6.children()).select("span[class]");
                                                                    for (Element sbps7: subpos7) {
                                                                        String sbcls2 = sbps7.className();
                                                                        if (sbcls2.equals("gc dgc")) {
                                                                            String gramcat = pos.substring(pos.lastIndexOf("<pos>"),pos.lastIndexOf("</pos>")+6);
                                                                            if (!gramcat.contains("-")) {
                                                                                String gramc = sbps6.text();
                                                                                int mc = pos.lastIndexOf("</pos>");
                                                                                pos = pos.substring(0,mc)+"-"+(Util.removeAll(Util.removeAll(gramc,"]"),"[")).trim()+pos.substring(mc);
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        dial = "";
        if (!ref.equals("")) {
            pos = "<entry>\n"+pos.trim()+"\n"+ref+"\n</entry>";
        }
        else {
            pos = "<entry>\n"+pos.trim()+"\n</entry>";
        }
        return pos;
    }
    public static String lookup (String graph, String orig) {
        String direct = addr+Util.removeAll(Util.removeAll(Util.removeMarks(graph),"`"),".");
        boolean accept = true;
        String phon = "";
        query = orig;
        try {
            Document doc = (Jsoup.connect(direct)).get();
            Elements bodies = doc.select("div[class]");
            for (Element body: bodies) {
                String bodcls = body.className();
                if (!accept) {
                    break;
                }
                else if (bodcls.equals("di-body")) {
                    Elements entries = (body.children()).select("div[class]");
                    for (Element entry: entries) {
                        String entcls = entry.className();
                        if (entcls.equals("entry")) {
                            Elements enbds = (entry.children()).select("div[class]");
                            for (Element enbd: enbds) {
                                String ebcls = enbd.className();
                                if (ebcls.equals("entry-body")) {
                                    Elements holds = (enbd.children()).select("div[class]");
                                    for (Element hold: holds) {
                                        String hldcls = hold.className();
                                        if (hldcls.equals("pr entry-body__el")) {
                                            /*Element infor = strip(strip(strip(hold,"div[class]","am-default contentslot"),"div[class]","smartt"),"div[class]","share rounded js-share");
                                            phon += parseEntry(infor)+"\n";*/
                                            String parsed = parseEntry(hold)+"\n";
                                            if (parsed.contains("ipa")) {
                                                phon += parsed;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else if (bodcls.startsWith("di-head")) {
                    Elements titles = (body.children()).select("div[class]");
                    for (Element title: titles) {
                        String tcls = title.className();
                        if (tcls.startsWith("di-title")) {
                            Elements head2s = (title.children()).select("h2[class]");
                            for (Element head2: head2s) {
                                String hd2cls = head2.className();
                                if (hd2cls.startsWith("h2")) {
                                    String base = head2.text();
                                    if (base.endsWith(" | American Dictionary") && !phon.equals("") || base.contains(" | Business English")) {
                                        accept = false;
                                        break;
                                    }
                                }
                                if (!accept) {
                                    break;
                                }
                            }
                        }
                        if (!accept) {
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception oops) {
            //System.out.println(oops.toString());
        }
        return phon;
    }
}