package convert17;
import java.util.*;
import javax.swing.JFrame;
public class TextParser extends Thread {
    private int tick = 0;
    private String org = "";
    private boolean thi = false;
    private boolean glot = false;
    public static boolean intrude = false;
    private boolean change = false;
    public String transcrip1 = "";
    public String transcrip2 = "";
    private String cor = "";
    private Dialect acnt;
    private boolean stmk = false;
    private int systm = 0;
    private boolean capitI = true;
    private boolean backAsh = false;
    private boolean syncopate = true;
    public boolean paused = false;
    public boolean askforhelp = false;
    public static List<Lemma> expCustoms = new ArrayList<>();
    public TextParser (String text, Dialect dialect, boolean markstress, int system, boolean capitalI, boolean ask) {
        this.cor = text;
        this.acnt = dialect;
        this.stmk = markstress;
        this.systm = system;
        this.capitI = capitalI;
        this.backAsh = dialect.getBackA();
        this.askforhelp = ask;
        //morphDec(system);
    }
    public Lemma matchComp (String cmp, Lemma add, Dialect ant, int syst, boolean main) {
        Lemma matched = new Lemma();
        Phonology phonparse = new Phonology();
        String cuspro = phonparse.shave(add.getPronUK());
        if (main) {
            List<Lemma> fille1 = phonparse.resultList(cmp,ant,syst);
            char grcat = add.getUsage();
            int irr = add.getAlter();
            for (int q = 0; q < fille1.size(); q++) {
                Lemma fil = fille1.get(q);
                String filpro = phonparse.shave(fil.getPronUK());
                char filgrm = fil.getUsage();
                int odd = fil.getAlter();
                if (cuspro.endsWith(filpro) && filgrm == grcat && odd == irr) {
                    matched = fil;
                }
            }
        }
        else {
            List<Lemma> fille2 = phonparse.resultList(cmp,ant,syst);
            for (int r = 0; r < fille2.size(); r++) {
                Lemma fil = fille2.get(r);
                String filpro = phonparse.shave(fil.getPronUK());
                if (cuspro.startsWith(filpro)) {
                    matched = fil;
                }
            }
        }
        return matched;
    }
    public static boolean cuscom = false;
    public static List<Lemma> customs = new ArrayList<>();
    public Lemma buildComp (Lemma novel, String ing1, String ing2, Dialect ant, int syst) throws NullPointerException {
        Lemma ingred1;
        List<Lemma> antec1 = Lemma.findWord(ing1,customs);
        if (antec1.size() == 1) {
            ingred1 = antec1.get(0);
        }
        else if (Morphology.isRecog(ing1)) {
            ingred1 = matchComp(ing1,novel,ant,syst,true);
        }
        else {
            ingred1 = inputLemma(ing1,30,ant,syst);
        }
        Lemma ingred2;
        List<Lemma> antec2 = Lemma.findWord(ing2,customs);
        if (antec2.size() == 1) {
            ingred2 = antec2.get(0);
        }
        else if (Morphology.isRecog(ing2)) {
            ingred2 = matchComp(ing2,novel,ant,syst,false);
        }
        else {
            ingred2 = inputLemma(ing2,30,ant,syst);
        }
        Lemma cust;
        if (ingred1.isFull() && ingred2.isFull()) {
            cust = Lemma.structure(novel,ingred1,ingred2);
        }
        else {
            cust = novel;
        }
        return cust;
    }
    public Lemma inputLemma (String non, int res, Dialect ant, int syst) {
        LemmaCreator create = new LemmaCreator(non.replaceAll("~","\'"));
        create.setVisible(true);
        JFrame creator = new JFrame("Unknown word detected!");
        creator.setSize(360,450);
        creator.setContentPane(create);
        creator.setVisible(true);
        //creator.setResizable(false);
        String repback = Convert17.report;
        Convert17.report = create.help;
        try {
            while (!create.done) {
                this.sleep(res);
            }
        }
        catch (InterruptedException interrupt) {
            //do nothing
        }
        Convert17.report = repback;
        Lemma built = create.addition;
        String head = (create.thyg1).getText();
        String nohd = (create.thyg2).getText();
        boolean intStruc = !head.equals("") && !nohd.equals("");
        boolean already;
        //System.out.println("Initial: "+built.printParms());
        try {
            already = (built.getDghtr1()).isFull() && (built.getDghtr2()).isFull();
        }
        catch (NullPointerException nul) {
            already = false;
        }
        if (intStruc && !already) {
            built = buildComp(built,head,nohd,ant,syst);
            try {
                Lemma hdlem = built.getDghtr1();
                Lemma othlem = built.getDghtr2();
                if (hdlem.hasAllo() && !Lemma.isSuffix(othlem)) {
                    built.setAllo(othlem.getSpelling()+hdlem.getAllo());
                }
                else if (hdlem.hasAllo()) {
                    built.setAllo(hdlem.getAllo()+othlem.getSpelling());
                }
            }
            catch (NullPointerException dumb) {
                //do nothing
            }
        }
        try {
            built.setExempt(create.exempt);
        }
        catch (NullPointerException nul) {
            //do nothing
        }
        //System.out.println("Composite: "+built.printParms());
        try {
            if (built.isFull()) {
                customs.add(built);
                //(Morphology.dict).store(Lemma.pack(built));
                cuscom = true;
                if (create.save) {
                    expCustoms.add(built);
                    //(Morphology.exports).add(built);
                }
            }
            return built;
        }
        catch (NullPointerException blank) {
            return new Lemma(non);
        }
    }
    public String incorp (String mod, Dialect ac, int scm, boolean trans) {
        String wurd = mod;
        char schwa;
        char schwi;
        switch (scm) {
            case 1:   schwa = 'ø';
                      break;
            case 4:   schwa = 'ö';
                      break;
            case 5:   schwa = 'ə';
                      break;
            default:  schwa = 'a';
                      break;         
        }
        switch (scm) {
            case 5:   schwi = 'e';
                      break;
            default:  schwi = 'i';
                      break;
        }
        String werd = Util.removeAll(Util.removeAll(wurd,"ˌ"), "ˈ");
        boolean initVowel;
        try {
            initVowel = TranRLS.isVowel(werd.charAt(0)) || werd.charAt(0) == '*' && TranRLS.isVowel(werd.charAt(1));
        }
        catch (StringIndexOutOfBoundsException no) {
            initVowel = false;
        }
        if (!ac.getRhotic() && intrude && initVowel && tick <= 1) {
            wurd = "ɹ~"+wurd;
            change = true;
        }
        else if (initVowel && thi && tick == 1) {
            transcrip1 = Util.replaceAtIndex(transcrip1,"ðiː",transcrip1.lastIndexOf("ðə"),2);
            if (scm > 0 && scm != 5) {
                transcrip2 = Util.replaceAtIndex(transcrip2,Character.toString(schwi),transcrip2.lastIndexOf(schwa),1);
            }
        }
        else if (initVowel && glot && tick == 1) {
            transcrip1 = Util.insertAtIndex(transcrip1,"ʔ",transcrip1.lastIndexOf("ə")+1);
            if (scm == 1) {
                transcrip2 = Util.insertAtIndex(transcrip2,"q",transcrip2.lastIndexOf(" "));
            }
        }
        if ((wurd.endsWith("r") || wurd.endsWith("ɹ")) && !ac.getRhotic()) {
            wurd = wurd.substring(0,wurd.length()-1);
            intrude = true;
            change = true;
            tick = 1;
        
        }
        else if ((wurd.endsWith("r`") || wurd.endsWith("ɹ`")) && !ac.getRhotic()) {
            wurd = wurd.substring(0,wurd.length()-2)+"`";
            intrude = true;
            change = true;
            //tick = 1;
        
        }
        else if (wurd.equals("ðə")) {
            intrude = false;
            thi = true;
            tick = 1;
        }
        else if (wurd.equals("ə")) {
            intrude = false;
            glot = true;
            tick = 1;
        }
        else {
            intrude = false;
            thi = false;
            glot = false;
            tick = 0;
        }
        return wurd;
    }
    public void toggle (int res) throws InterruptedException {
        while (paused) {
            this.sleep(res);
        }
    }
    public List<String> multIncorp(List<String> mult, Dialect ac, int cd, boolean trans) {
        List<String> mincorp = new ArrayList<>();
        for (int e = 0; e < mult.size(); e++) {
            String enow = incorp(mult.get(e),ac,cd, trans);
            mincorp.add(enow);
        }
        return mincorp;
    }
    public String chooseHomograph (List<String> hmgrphs, String ambig, int res) {
        HomographHandler handle = new HomographHandler(ambig,hmgrphs);
        handle.setVisible(true);
        JFrame homslct = new JFrame("Homograph "+ambig.toUpperCase()+" detected!");
        homslct.setSize(428,100);
        homslct.setContentPane(handle);
        homslct.setVisible(true);
        //homslct.setResizable(false);
        try {
            while (!handle.selected) {
                this.sleep(res);
            }
        }
        catch (InterruptedException interrupt) {
            //do nothing
        }
        return handle.selection;
    }
    public void nullOutput (String word) {
        transcrip1 += "}"+word+"{";
        transcrip2 += "}"+word+"{";
    }
    public void outputWord (String ref, List<String> yields, List<Lemma> finds, boolean stmk, boolean cap) {
        if (!Util.isFilled(yields)) {
            nullOutput(ref);
        }
        else {
            transcrip1 += Phonology.printProns(yields,stmk);
            String conv = transcribe(ref,acnt,finds,yields,stmk,cap,systm);
            conv = conv.replaceAll("~", "'");
            conv = conv.replaceAll("`", ".");
            transcrip2 += conv;
        }
    }
    public List<Lemma> NotInDict (String ref, Dialect ant, int syst) {
        String reflow = ref.toLowerCase();
        Lemma custom = inputLemma(ref,30,ant,syst);
        /*(Morphology.dict).store(customs); 
        (Morphology.exports).addAll(expCustoms);*/
        String modif = custom.getSpelling();
        List<Lemma> input= Lemma.pack(custom);
        Phonology morphcon = new Phonology();
        (morphcon.morphology).orgform = reflow;
        if (!modif.equals(reflow)) {
            input = (morphcon.morphology).parseMorph(reflow,Lemma.pack(reflow),ant,true,true,false,false);
           //System.out.println(Lemma.multiPrint(input));
            
        }
        //Morphology.exports = Util.append(Morphology.exports,expCustoms);
        return input;
    }
    public static List<String> trimStrDup (List<String> poly) {
        List<String> trimmed = new ArrayList<>();
        ListIterator trmtr = poly.listIterator();
        while (trmtr.hasNext()) {
            String illud = Phonology.shave((String)trmtr.next());
            if (!trimmed.contains(illud)) {
                trimmed.add(illud);
            }
        }
        return trimmed;
    }
    public void convertWord (String word, Dialect ant, int syst, boolean stmk, boolean again) {
        Phonology phonology = new Phonology();
        String ref = word;
        boolean cap;
        org = ref;
        if (ref.startsWith("~") && ref.length() >= 2) {
            cap = Character.isUpperCase(ref.charAt(1));
        }
        else {
            cap = Character.isUpperCase(ref.charAt(0));
        }
        String reflow = ref.toLowerCase();
        org = reflow;
        List<Lemma> finds = phonology.resultList(reflow,ant,syst);
        List<String> yields = phonology.finalPron(reflow,finds,ant,syst);
        if (askforhelp && yields.size() > 1) {
            if (stmk || !stmk && trimStrDup(yields).size() > 1) {
                String disamb = chooseHomograph(yields,word,30);
                if (!disamb.equals("ALL") && !disamb.equals("BOTH")) {
                    yields = new ArrayList<String>();
                    yields.add(disamb);
                }
            }
        }
        if (!Util.isFilled(yields)) {
            if (askforhelp) {
                finds = NotInDict(ref,ant,syst);
            }
            if (cuscom) {
                yields = phonology.finalPron(reflow,finds,ant,syst);
                yields = multIncorp(yields,ant,syst,true);
                outputWord(ref,yields,finds,stmk,cap);
                cuscom = false;
            }
            else {
                nullOutput(ref);
            }
            if (again) {
                String wrdlow = word.toLowerCase();
                org = wrdlow;
                finds = phonology.resultList(wrdlow,ant,syst);            
                yields = phonology.finalPron(wrdlow,finds,ant,syst);
                yields = multIncorp(yields,ant,syst,true);
            }
            else {
                yields = phonology.finalPron(reflow,finds,ant,syst);
                yields = multIncorp(yields,ant,syst,true);
            }
        }
        else {
            yields = multIncorp(yields,ant,syst,true);
            outputWord(ref,yields,finds,stmk,cap);
            /*transcrip1 += Phonology.printProns(yields,stmk);
            String conv = transcribe(ref,ant,finds,yields,stmk,cap,syst);
            conv = conv.replaceAll("~", "'");
            transcrip2 += conv;*/
        }
        (Morphology.dict).store(Morphology.ablauted);
        (Morphology.dict).store(customs);
        (Morphology.exports).addAll(expCustoms);
        /*(Morphology.ablauted).clear();
        expCustoms.clear();
        customs.clear();*/
        Convert17.converted = transcrip2;
    }
    public static String normalize (String abnorm) {
        String normal = (abnorm.replaceAll("“","\"")).replaceAll("”","\"");
        normal = (normal.replaceAll("‘","\'")).replaceAll("’","\'");
        return normal;
    }
    public void check (int res) {
        try {
            while (paused) {
                this.sleep(res);
            }
        }
        catch (InterruptedException interrupt) {
            //do nothing
        }
    }
    public boolean disambDot (String context, int res) {
        boolean period = false;
        AskPeriod handle = new AskPeriod(context);
        handle.setVisible(true);
        JFrame disambig = new JFrame("Ambiguous dot detected!");
        disambig.setSize(600,120);
        disambig.setContentPane(handle);
        disambig.setVisible(true);
        //homslct.setResizable(false);
        try {
            while (!handle.ready) {
                this.sleep(res);
            }
        }
        catch (InterruptedException interrupt) {
            //do nothing
        }
        return handle.apost;
    }
    public boolean disambTick (String context, int res) {
        boolean apost = false;
        AskApostr handle = new AskApostr(context);
        handle.setVisible(true);
        JFrame disambig = new JFrame("Ambiguous tick mark detected!");
        disambig.setSize(600,120);
        disambig.setContentPane(handle);
        disambig.setVisible(true);
        //homslct.setResizable(false);
        try {
            while (!handle.ready) {
                this.sleep(res);
            }
        }
        catch (InterruptedException interrupt) {
            //do nothing
        }
        return handle.apost;
    }
    public boolean senSplit (String context, int res, boolean semi) {
        boolean sepsen = false;
        SenSplit handle = new SenSplit(context,semi);
        handle.setVisible(true);
        JFrame splitter = new JFrame("Ambiguous punctuation detected!");
        String backup = Convert17.report;
        Convert17.report = "Proper de-capitalization of \"I\" requires discerning when it occurs in positions where any word would be capitalized\n(e.g. at the start of a sentence), and such discernemnt relies on certain syntactic information, some of which defies\n automatic and unambiguous detection by computer.";
        splitter.setSize(693,120);
        splitter.setContentPane(handle);
        splitter.setVisible(true);
        //homslct.setResizable(false);
        try {
            while (!handle.ready) {
                this.sleep(res);
            }
        }
        catch (InterruptedException interrupt) {
            //do nothing
        }
        Convert17.report = backup;
        return handle.split;
    }
    public boolean capLine (int res) {
        boolean sepsen = false;
        CapLine handle = new CapLine();
        handle.setVisible(true);
        JFrame splitter = new JFrame("Capitalize every line?");
        String backup = Convert17.report;
        Convert17.report = "This is useful for knowing when to capitalize or de-capitalize \"I\" in poetry.\nSome poems capitalize every line, while others follow the capitalization conventions of prose.";
        splitter.setSize(400,120);
        splitter.setContentPane(handle);
        splitter.setVisible(true);
        //homslct.setResizable(false);
        try {
            while (!handle.ready) {
                this.sleep(res);
            }
        }
        catch (InterruptedException interrupt) {
            //do nothing
        }
        Convert17.report = backup;
        return handle.capt;
    }
    public static String extrCon (String decaptl, int m) {
        String cntxt;
        try {
            int left = Util.lastSpace(decaptl,m)+1;
            int right = Util.firstSpace(decaptl,Util.firstNonSpace(decaptl,m+1));
            cntxt = decaptl.substring(left,right);
            cntxt = (cntxt.replaceAll("\t"," ")).replaceAll("\n"," ");
        }
        catch (StringIndexOutOfBoundsException out) {
            cntxt = decaptl.substring(m);
        }
        return cntxt;
    }
    public String colSpaces (String coll, int start) {
        String spaces = "";
        int lin = start;
        for (int ln = start; ln < coll.length(); ln++) {
            char lnhc = coll.charAt(ln);
            if (!Util.isSpace(lnhc)) {
                break;
            }
            else {
                spaces += Character.toString(lnhc);
            }
        }
        return spaces;
    }
    public String lowerI (String upperI) {
        String unit = upperI;
        for (int lo = 0; lo < unit.length(); lo++) {
            boolean alone = lo == unit.length()-1 || (lo <= unit.length()-2 && (Util.isSpace(unit.charAt(lo+1)) || unit.charAt(lo+1) == '~' || Util.isMedPunct(unit.charAt(lo+1))));
            char letrI = unit.charAt(lo);
            if (letrI == 'I' && alone) {
                unit = Util.replaceAtIndex(unit,"i",lo,1);
            }
        }
        return unit;
    }
    public String parsePunct (String unparsed, boolean capline) {
        String parst = "";
        String anparst = unparsed;
        boolean firstquote1 = false;
        boolean firstquote2 = false;
        boolean quote1 = false;
        boolean quote2 = false;
        boolean sent1 = false;
        boolean sent2 = false;
        boolean sent = false;
        boolean isol = false;
        boolean par = false;
        char closepunct = '.';
        char lastclose = '.';
        char lastpunct = '.';
        char prior1 = '|';
        char prior2 = '|';
        String unit = "";
        int princ = 0;
        String parenth = "";
        boolean separate = true;
        boolean lastsep = true;
        boolean reject = false;
        int lastunit = 0;
        boolean enter = false;
        boolean lastent = false;
        for (int m = 0; m < anparst.length(); m++) {
            boolean last = m == anparst.length()-1;
            boolean seclast = m >= anparst.length()-2;
            char place = anparst.charAt(m);
            boolean upper = Character.isUpperCase(place);
            boolean termpunct = Util.isTermPunct(place);
            boolean medpunct = Util.isMedPunct(place);
            boolean phrase = false;
            char next1;
            if (last) {
                next1 = '|';
            }
            else {
                next1 = anparst.charAt(m+1);
            }
            char next2;
            if (seclast) {
                next2 = '|';
            }
            else {
                next2 = anparst.charAt(m+2);
            }
            if (capline && place == '\n' && m > princ && !last) {
                unit = anparst.substring(princ,m+1);
                if (!last) {
                    princ = m+1;
                }
                phrase = (unit.trim()).length() > 1;
                enter = true;
            }
            else if (place == '\'') {
                quote2 = !quote2;
                if (quote2) {
                    //isol = Util.isSpace(prior1) && !Util.isPunct(prior2);
                    if (last) {
                        unit = anparst.substring(princ);
                    }
                    else if (prior1 != '"' && m > princ+1) {
                        unit = anparst.substring(princ,m);
                        princ = m;
                    }
                    phrase = unit.length() > 1;
                    /*if (phrase) {
                        System.out.println("Testing 1: "+unit);
                    }*/
                    if (Character.isUpperCase(next1) && Util.isTermPunct(lastpunct)) {
                        sent1 = true;
                    }
                }
                else if (!quote2) {
                    boolean ambigSpace = (Util.isTermPunct(prior1) && prior1 != '.' || (prior1 == '-' || prior1 == '–' || prior1 == 'ǂ') && firstquote2) && Util.isSpace(next1) && Util.isNotLower(next2);
                    if (ambigSpace) {
                        String env = extrCon(anparst,m);
                        separate = senSplit(cleanUp(env,false,true),30,false);
                    }
                    else if (prior1 == '.' || next1 == '\n' && next2 == '\n' || next1 == '\n' && next2 == '\t') {
                        separate = true;
                    }
                    else {
                        separate = !lastsep;
                    }
                    if (Util.isPunct(prior1)) {
                        //lastclose = closepunct;
                        closepunct = prior1;
                    }
                    if (last) {// || seclast && next1 == '"') {
                        unit = anparst.substring(princ);
                    }
                    /*else if (!seclast && next1 == '"' && m > princ+1) {
                        String spaces = colSpaces(anparst,m+2);
                        unit = anparst.substring(princ,m+2)+spaces;
                        princ = m+2+spaces.length();
                        m += 1+spaces.length();
                        quote1 = false;
                    }*/
                    else if (m > princ+1 && next1 != '"') {
                        unit = anparst.substring(princ,m+1);
                        princ = m+1;
                    }
                    phrase = (unit.trim()).length() > 1;
                    /*if (phrase) {
                        System.out.println("Testing 2: "+unit);
                    }*/
                }
            }
            else if (place == '"') {
                quote1 = !quote1;
                if (quote1) {
                    //isol = Util.isSpace(prior1) && !Util.isPunct(prior2);
                    if (last) {
                        unit = anparst.substring(princ);
                    }
                    else if (next1 != '\'' && m > princ+1) {
                        unit = anparst.substring(princ,m);
                        princ = m;
                    }
                    phrase = (unit.trim()).length() > 1;
                    /*if (phrase) {
                        System.out.println("Testing 3: "+unit);
                    }*/
                    if (Character.isUpperCase(next1) && Util.isTermPunct(lastpunct) || m == 0) {
                        sent = true;
                    }
                }
                else if (!quote1) {
                    boolean ambigSpace = (Util.isTermPunct(prior1) && prior1 != '.' || prior1 == '\'' && Util.isTermPunct(prior2) && prior2 != '.' || (prior1 == '-' || prior1 == '–' || prior1 == 'ǂ') && firstquote1) && Util.isSpace(next1) && Util.isNotLower(next2);
                    if (ambigSpace) {
                        String env = extrCon(anparst,m);
                        separate = senSplit(cleanUp(env,false,true),30,false);
                    }
                    else if (prior1 == '.' || prior1 == '\'' && prior2 == '.' || next1 == '\n' && next2 == '\n' || next1 == '\n' && next2 == '\t') {
                        separate = true;
                    }
                    else {
                        separate = !lastsep;
                    }
                    if (prior1 == '\'' && Util.isPunct(prior2)) {
                        closepunct = prior2;
                    }
                    else if (Util.isPunct(prior1)) {
                        closepunct = prior1;
                    }
                    if (last) {
                        unit = anparst.substring(princ);
                    }
                    else if (m > princ+1) {
                        String spaces = colSpaces(anparst,m+1);
                        unit = anparst.substring(princ,m+1)+spaces;
                        princ = m+1+spaces.length();
                        m += spaces.length();
                    }
                    phrase = (unit.trim()).length() > 1;
                    /*if (phrase) {
                        System.out.println("Testing 4: "+unit);
                    }*/
                }
            }
            else if (termpunct) {
                if (quote2) {
                    sent2 = false;
                }
                else if (quote1) {
                    sent1 = false;
                    firstquote2 = false;
                }
                else {
                    sent = false;
                    firstquote1 = false;
                    firstquote2 = false;
                }
                if (last && !quote1 && !quote2) {
                    unit = anparst.substring(princ);
                }
                else if (!Util.isQuote(next1) && m > princ+1) {
                    String spaces = colSpaces(anparst,m+1);
                    unit = anparst.substring(princ,m+1)+spaces;
                    princ = m+1+spaces.length();
                    m += spaces.length();
                }
                else if (!Util.isQuote(next1) && m > princ+1) {
                    unit = anparst.substring(princ,m+1);
                    princ = m+1;
                }
                lastclose = closepunct;
                closepunct = place;
                phrase = (unit.trim()).length() > 1;
                separate = true;
                /*if (phrase) {
                    System.out.println("Testing 5: "+unit);
                }*/   
            }
            else if (place == '(' && reject) {
                int close = anparst.indexOf(")",m);
                String paren = "";
                String spaces = "";
                if (close == anparst.length()-1 || close < 0) {
                    paren = anparst.substring(m);
                }
                else {
                    spaces = colSpaces(anparst,close+1);
                    paren = anparst.substring(m,close+1);
                }
                /*boolean sentence = Character.isUpperCase(paren.charAt(Util.opening(paren))) && Util.isTermPunct(paren.charAt(Util.closing(paren)));
                if (sentence) {
                    unit = "("+parsePunct(paren.substring(1,paren.length()-1),capline)+")"+spaces;
                }
                else {
                    unit = lowerI(paren);
                }*/
                princ = m+paren.length();
                paren = paren+spaces;
                m = princ;
                prior2 = paren.charAt(paren.length()-1);
                prior1 = anparst.charAt(princ);
                parst = parst+unit;
                reject = false;
                unit = "";
            }
            else if (place == '(') {
                int close = anparst.indexOf(")",m);
                String paren = "";
                String tail = "";
                if (close == anparst.length()-1 || close < 0) {
                    paren = anparst.substring(m);
                }
                else {
                    paren = anparst.substring(m,close+1);
                    int space1 = Util.firstSpace(anparst,close);
                    int space2 = Util.firstNonSpace(anparst,space1);
                    tail = anparst.substring(close+1,space2);
                }
                String edit = "("+parsePunct(paren.substring(1,paren.length()-1),capline)+")";
                /*boolean sentence = Character.isUpperCase(paren.charAt(Util.opening(paren))) && Util.isTermPunct(paren.charAt(Util.closing(paren)));
                if (sentence) {
                    edit = "("+parsePunct(paren.substring(1,paren.length()-1),capline)+")";
                }
                else {
                    edit = lowerI(paren);
                }*/
                int offset = edit.length() - paren.length();
                int beg = m+paren.length()+tail.length();
                if (!phrase) {
                    unit = anparst.substring(princ,m)+edit+tail;
                    phrase = (unit.trim()).length() > 1;
                }
                else {
                    parst = parst+edit+tail;
                }
                princ = beg;
                m = beg-1;
            }
            else if (medpunct) {
                if (place == ':' && (!last && (next1 == '-' || next1 == '–') || !seclast && next1 == ' ' && (next2 == '–' || next2 == '-'))) {
                    continue;
                }
                else if (last) {
                    unit = anparst.substring(princ);
                }
                else if (!Util.isQuote(next1) && m > princ+1) {
                    String spaces = colSpaces(anparst,m+1);
                    unit = anparst.substring(princ,m+1)+spaces;
                    princ = m+1+spaces.length();
                }
                lastclose = closepunct;
                closepunct = place;
                phrase = (unit.trim()).length() > 1;
                /*if (phrase) {
                    System.out.println("Testing 6: "+unit);
                }*/   
            }
            else if (!sent && upper) {
                sent = true;
            }
            else if (last && !phrase) {
                unit = anparst.substring(princ);
                phrase = (unit.trim()).length() > 1;
            }
            if (askforhelp && phrase && (place == ';' || place == ':') && !seclast) {
                if (quote1 && !quote2) {
                    String env = extrCon(anparst,m);
                    firstquote2 = !senSplit(cleanUp(env,false,true),30,true);
                    separate = !firstquote2;
                }
                else {
                    String env = extrCon(anparst,m);
                    firstquote1 = !senSplit(cleanUp(env,false,true),30,true);
                    separate = !firstquote1;
                }
            }
            else if (askforhelp && phrase && place == '–' && (!seclast && next1 == ' ' && Util.isQuote(next2) || !last && Util.isQuote(next1))) {
                if (quote1 && !quote2  && !(unit.trim()).endsWith("'")) {
                    String env = extrCon(anparst,m);
                    firstquote2 = !senSplit(cleanUp(env,false,true),30,true);
                    separate = !firstquote2;
                }
                else if (!(unit.trim()).endsWith("\"")) {
                    String env = extrCon(anparst,m);
                    firstquote1 = !senSplit(cleanUp(env,false,true),30,true);
                    separate = !firstquote1;
                }
            }
            if (capline && next1 == '\n' && phrase) {
                enter = true;
            }
            boolean allCaps = Util.isAllCaps(unit);
            boolean endquote1 = (unit.trim()).endsWith("\"");
            boolean endquote2 = (unit.trim()).endsWith("'") || (unit.trim()).endsWith("'\"");
            //System.out.println("Test: "+unit.trim()+"\n"+endquote1+"\n"+endquote2);
            boolean cont = lastclose == ',' || lastclose == ':' || lastclose == ';' && (firstquote1 || firstquote2);
            if (unit.startsWith("(") && phrase) {
                reject = true;
                m -= unit.length();
                princ = m;
                m -= 1;
                continue;
            }
            if (phrase && !allCaps) {
                //System.out.println(unit);
                int look = Util.firstAlphaNum(unit,0);
                String tester = (Util.removeAll(Util.removeAll(unit,"'"),"\"")).trim();
                boolean isolate = false;
                if (endquote1 || endquote2) {
                    char finl = tester.charAt(tester.length()-1);
                    isolate = Character.isLetterOrDigit(finl) && finl != 'ǂ';
                }
                int frst = 0;
                for (int nq = 0; nq < unit.length(); nq++) {
                    char nqch = unit.charAt(nq);
                    if (Character.isLetterOrDigit(nqch)) {
                        break;
                    }
                    else {
                        frst += 1;
                    }
                }
                char prev = '|';
                //System.out.println(unit+"   "+Character.toString(prev)+" "+firstquote1+" "+firstquote2+" "+cont+" "+isolate+" "+isol+" "+lastsep);
                int cap;
                if (tester.contains("(") && !tester.startsWith(")")) {
                    cap = unit.indexOf("(");
                }
                else {
                    cap = unit.length();
                }
                for (int lo = look; lo < cap; lo++) {
                    boolean alone = lo == unit.length()-1 || (lo <= unit.length()-2 && (Util.isSpace(unit.charAt(lo+1)) || unit.charAt(lo+1) == '~' || Util.isMedPunct(unit.charAt(lo+1))));
                    char letrI = unit.charAt(lo);
                    if (lo >= 1) {
                        prev = unit.charAt(lo-1);
                    }
                    if (letrI == 'I' && alone) {
                        boolean capitalize = lo == frst && ((prev == '"' && !firstquote1 || prev == '\'' && !firstquote2 || !cont) && !isolate && !isol && lastsep || enter || lastent);
                        if (capitalize) {
                            unit = Util.replaceAtIndex(unit,"Ih",lo,1);
                            lo += 1;
                        }
                        else {
                            unit = Util.replaceAtIndex(unit,"i",lo,1);
                        }
                    }
                }
                //boolean med = Util.isMedPunct(closepunct);
                if (!firstquote1 && endquote1) {
                    firstquote1 = true;
                }
                if (!firstquote2 && endquote2) {
                    firstquote2 = true;
                }
                lastsep = separate;
                lastent = enter;
                isol = isolate;
                parst += unit;
                phrase = false;
                enter = false;
                lastunit = m;
                //back = unit;
                unit = "";
            }
            else if (phrase) {
                String tester = Util.removeAll(Util.removeAll(unit,"'"),"\"");
                boolean med = Util.isMedPunct(closepunct);
                boolean isolate = false;
                if (endquote1 || endquote2) {
                    isolate = Character.isLetterOrDigit(tester.charAt(tester.length()-1));
                }
                if (!firstquote1 && endquote1) {
                    firstquote1 = true;
                }
                if (!firstquote2 && endquote2) {
                    firstquote2 = true;
                }
                lastsep = separate;
                isol = isolate;
                parst += unit;
                phrase = false;
                lastunit = m;
                //back = unit;
                unit = "";
            }
            /*else if (last && unit.length()== 1) {
                parst = parst+unit;
            }*/
            if (Util.isPunct(place)) {
                lastpunct = place;
            }
            prior2 = prior1;
            prior1 = place;
        }
        parst = parst.replaceAll("ǂ","...");
        //parst = parst.replaceAll("`",".");
        return parst;
    }
    public static String cleanUp (String dirty, boolean delete, boolean switchI) {
        if (switchI && dirty.equals("Ih")) {
            return "I";
        }
        String clean = dirty;
        for (int cl = 0; cl < clean.length()-1; cl++) {
            char thisch = clean.charAt(cl);
            char nextch = clean.charAt(cl+1);
            if ((thisch == '.' || thisch == '`') && nextch == '.' && (cl >= clean.length()-2 || clean.charAt(cl+2) != '.')) {
                clean = Util.removeAtIndex(clean,cl);
            }
            else if ((thisch == '.' || thisch == '`') && nextch == '.') {
                cl += 3;
            }
        }
        if (delete) {
            clean = Util.removeAll(clean,"`");
            clean = Util.removeAll(clean,"~");
        }
        else {
            clean = clean.replaceAll("`",".");
            clean = clean.replaceAll("~","'");
        }
        return clean;
    }
    @Override
    public void run () {
        String corpus = normalize(prepTS(cor));
        Phonology.backA = backAsh;
        if (capitI) {
            corpus = parsePunct(corpus,capLine(30));
        }
        boolean lex = false;
        String word = "";
        char slot;
        tick = 0;
        int w;
        for (w = 0; w < corpus.length(); w++) {
            check(30);
            slot = corpus.charAt(w);
            if ((Character.isLetter(slot) && slot != 'ǂ' || slot == '~' || slot == '`') && w == corpus.length()-1) {
                lex = false;
                word += Character.toString(slot);
                convertWord(word,acnt,systm,stmk,true);
            }
            else if ((!Character.isLetter(slot) || slot == 'ǂ') && slot != '~' && slot != '`') {
                if (lex) {
                    convertWord(word,acnt,systm,stmk,true);
                }
                else {
                    tick++;
                }
                if (Character.isDigit(slot) || slot == ' ' || slot == '\n' || slot == '\t' || slot == '-' || slot == '–' || slot == 'ǂ') {
                    transcrip1 += Character.toString(slot);
                    word = "";
                }
                else {
                    word = "";
                }
                transcrip2 += Character.toString(slot);
                lex = false;
            }
            else {
                lex = true;
                word += Character.toString(slot);
            }
        }
        if (lex) {
            convertWord(word,acnt,systm,stmk,false);
            tick = 0;
        }
        intrude = false;
        thi = false;
        glot = false;
        tick = 0;
        transcrip1 = cleanUp(transcrip1,true,false);
        transcrip2 = cleanUp(transcrip2,false,false);
        Convert17.finish(transcrip1, transcrip2);
        if (!(Morphology.exports).isEmpty()) {
            Morphology.exports = Util.expand(Morphology.exports);
            (Morphology.dict).exportEntries(Morphology.exports);
        }
    }
    public static String transcribe(String refr, Dialect accent, List<Lemma> full, List<String> ylds, boolean strmrk, boolean capit, int scheme) {
        String transcription;
        switch (scheme) {
            case 1:   transcription = TranRLS.printRLS(ylds,strmrk,capit);
                      break;
            case 2:   transcription = TranPSS.printPSS(ylds,strmrk,capit);
                      break;
            case 3:   transcription = TranMPSS.printMPSS(ylds,strmrk,capit);
                      break;
            case 4:   transcription = TranSF.printSF(ylds,strmrk,capit);
                      break;
            case 5:   Morphology.treed = true;
                      transcription = TranKS.printKS(ylds,strmrk,full,capit,accent);
                      Morphology.treed = false;
                      break;
            default:  transcription =  Phonology.printProns(ylds,strmrk);
                      break;  
        }
        if (scheme <= 4 && capit && Util.isAllCaps(refr)) {
            transcription = transcription.toUpperCase();
        }
        return transcription;
    }
    public String prepTS (String raw) {
        String prep = raw;
        char presym = prep.charAt(0);
        char prechr = prep.charAt(0);
        for (int in = 0; in < prep.length(); in++) {
            char sym = prep.charAt(in);
            if ((sym == '\'' || sym == '‘' || sym == '’') && in > 0 && in < prep.length()-1 && !Util.isPunct(presym) && !Character.isLetter(prep.charAt(in+1))) {
                char follw = prep.charAt(in+1);
                if (Character.isLetter(presym) && Util.isSpace(follw)) {
                    int delin = Util.lastSpace(prep,in)+1;
                    String test = prep.substring(delin,in)+"~";
                    boolean apostrophe;
                    if (capitI) {
                        String env = extrCon(prep,in);
                        apostrophe = disambTick(env,30);
                    }
                    else {
                        apostrophe = Util.containsEnding(test.toLowerCase(),(Morphology.dict).suffixes);
                    
                    }
                    if (apostrophe) {
                        prep = Util.replaceAtIndex(prep,"~",in,1);
                    }
                }
                else if (presym != '-' && presym != '–' && Character.isLetter(follw)) {
                    int delin1 = Util.lastSpaceOrPunct(prep,in)+1;
                    int delin2 = Util.firstSpaceOrPunct(prep,in);
                    String test;
                    if (delin2 == prep.length()) {
                        test = prep.substring(delin1);
                    }
                    else {
                        test = prep.substring(delin1,delin2);
                    }
                    test = (test.replace(Character.toString(sym),"~")).toLowerCase();
                    boolean apostrophe = Util.isSpace(presym) && (Morphology.dict).dictContains(test) || Character.isLetterOrDigit(presym) && Util.containsEnding(test,(Morphology.dict).suffixes);
                    if (apostrophe) {
                        prep = Util.replaceAtIndex(prep,"~",in,1);
                    }
                }
            }
            else if (sym == '\'' || sym == '‘' || sym == '’') {// && (in == 0 || in == prep.length()-1 || !Util.isPunct(presym) && (!Util.isPunct(prechr) || presym != ' '))) {
                boolean repl = true;
                if ((Util.isSpace(presym) || presym == '\"' || Util.isPunct(presym)) && in < prep.length()-1) {
                    String tester = "~";
                    int pot = Util.firstSpaceOrPunct(prep,in);
                    if (pot < prep.length()-1) {
                        tester += prep.substring(in+1,pot);
                    }
                    else {
                        tester += prep.substring(in+1);
                    }
                    repl = (Morphology.dict).dictContains(tester.toLowerCase());//Morphology.findMorph(tester) < (Morphology.dict.lexicon).size();
                }
                if (repl) {
                    prep = Util.replaceAtIndex(prep,"~", in, 1);
                }
            }
            else if (sym == '.' && in < prep.length()-1 && prep.charAt(in+1) == '.') {
                prep = Util.replaceAtIndex(prep,"ǂ",in,2);
                sym = 'ǂ';
            }
            else if (sym == '.' && presym == 'ǂ') {
                prep = Util.removeAtIndex(prep,in);
                sym = 'ǂ';
                in -= 1;
            }
            else if (sym == '.' && in >= 1 && in < prep.length()-1) {
                boolean abbrev = false;
                char follw = prep.charAt(in+1);
                if (Character.isLetterOrDigit(follw)) {
                    prep = Util.replaceAtIndex(prep,"`",in,1);
                    abbrev = true;
                }
                else {
                    int delin1 = Util.lastSpace(prep,in)+1;
                    int delin2 = Util.firstSpace(prep,in);
                    int delin3 = Util.firstNonSpace(prep,delin2);
                    String test;
                    if (delin2 >= prep.length()) {
                        test = prep.substring(delin1);
                    }
                    else {
                        test = prep.substring(delin1,delin2);
                    }
                    if (test.charAt(0) == '(') {
                        test = test.substring(1);
                    }
                    test = (test.trim()).toLowerCase();
                    if (delin3 < prep.length()) {
                        char nextchar = prep.charAt(delin3);
                        boolean valform = (Morphology.dict).dictContains(test) || DictReader.isAvail(test);
                        if ((Util.isPunct(follw) || Character.isLowerCase(nextchar) || Character.isUpperCase(nextchar) && Util.isTitle(test) || (Character.isDigit(nextchar) || Util.isQuote(follw)) && valform && !disambDot(test,30)) && follw != ')') {
                            prep = Util.replaceAtIndex(prep,"`",in,1);
                            abbrev = true;
                        }
                        else if (Util.isSpace(follw) && (Character.isUpperCase(nextchar) || in == prep.length()-2) || follw == ')') {
                            String testend = test.substring(0,test.length()-1)+"`";
                            testend = (testend.trim()).toLowerCase();
                            valform = (Morphology.dict).dictContains(testend) || DictReader.isAvail(testend);
                            if (valform) {
                                prep = Util.insertAtIndex(prep,"`",in);
                                in += 1;
                            }
                        }
                    }
                }
            }
            else if (sym == '.' && in == prep.length()-1) {
                int delin1 = Util.lastSpace(prep,in)+1;
                String testend = prep.substring(delin1);
                if (testend.charAt(0) == '(') {
                    testend = testend.substring(1);
                }
                testend = (testend.trim()).toLowerCase();
                testend = testend.substring(0,testend.length()-1)+"`";
                boolean valform = (Morphology.dict).dictContains(testend) || DictReader.isAvail(testend);
                if (valform) {
                    prep = Util.insertAtIndex(prep,"`",in);
                    in += 1;
                }
            }
            presym = sym;
            if (!Util.isSpace(presym)) {
                prechr = presym;
            }
        }
        //System.out.println(prep);
        return prep;
    }
}