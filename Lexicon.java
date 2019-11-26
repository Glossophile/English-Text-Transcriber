package convert17;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
public class Lexicon {
    public List<Lemma> lexicon = new ArrayList<>();
    public static List<Lemma> backsuff = new ArrayList<>();
    public static List<Lemma> suffixes = new ArrayList<>();
    public static List<Lemma> prefixes = new ArrayList<>();
    public static List<String> inflects = new ArrayList<>();
    public static List<Lemma> latinate = new ArrayList<>();
    /*public static List<Lemma> cans = new ArrayList<>();
    public static List<Lemma> wills = new ArrayList<>();
    public static List<Lemma> reads = new ArrayList<>();
    public static List<Lemma> winds = new ArrayList<>();
    public static List<Lemma> wounds = new ArrayList<>();
    public static List<Lemma> useds = new ArrayList<>();
    public static List<Lemma> lives3 = new ArrayList<>();
    public static List<Lemma> lowering3 = new ArrayList<>();
    public static List<Lemma> proofreads = new ArrayList<>();
    public static List<Lemma> bowed = new ArrayList<>();
    public static List<Lemma> perfect = new ArrayList<>();
    public static List<Lemma> blessed3 = new ArrayList<>();
    public static List<Lemma> cursed3 = new ArrayList<>();
    public static List<Lemma> learned3 = new ArrayList<>();
    public static List<Lemma> doles = new ArrayList<>();
    public static List<Lemma> deliberates = new ArrayList<>();
    public static List<Lemma> puttings = new ArrayList<>();
    public static List<Lemma> separates = new ArrayList<>();
    public static List<Lemma> mans = new ArrayList<>();
    public static List<Lemma> knifes = new ArrayList<>();
    public static List<Lemma> wolfs = new ArrayList<>();
    public static List<Lemma> houses = new ArrayList<>();
    public static List<Lemma> dwarfs = new ArrayList<>();
    public static TreeMap<String,List<Lemma>> homographs = new TreeMap<>();*/
    public static List<String> lowback = new ArrayList<>();
    public static List<String> brityodrop = new ArrayList<>();
    public static List<String> britrhdrop = new ArrayList<>();
    public static List<String> voicealt = new ArrayList<>();
    public static List<String> ablaut = new ArrayList<>();
    public static List<String> rStem = new ArrayList<>();
    public static TreeMap<String,String> ablautforms = new TreeMap<>();
    public Lexicon (boolean update) {
        initLex(update);
    }
    public void initLex (boolean update) {
        suffixes.clear();
        backsuff.clear();
        /*reads.clear();
        proofreads.clear();
        lives3.clear();
        bowed.clear();*/
        lowback.clear();
        brityodrop.clear();
        britrhdrop.clear();
        //homographs.clear();
        lexicon.clear();
        voicealt.clear();
        ablaut.clear();
        rStem.clear();
        Lemma contrL = new Lemma("~ll","~l",'X',"NRSDB",11,'s');
        Lemma contrM = new Lemma("~m","~m",'X',"NRS",1,'s');
        Lemma contrR = new Lemma("~re","~ɹ",'X',"NRSH",1,'s');
        Lemma contrS = new Lemma("~s","~z",'X',"NRSDBC",1,'s');
        Lemma contrD = new Lemma("~d","~d",'X',"NRSDB", 11,'s');
        Lemma contrT = new Lemma("~t","~t",'X',"H",'s');
        Lemma contrNT = new Lemma("n~t","~nt",'X',"HV", 11,'s');
        Lemma contrV = new Lemma("~ve","~v",'X',"NRSH", 11,'s');
        Lemma poss = new Lemma("~s","~z",'D',"NMSD",1,'s');
        Lemma thirdsg = new Lemma("s","z",'V',"V",1,'s');
        Lemma past = new Lemma("ed","d",'S', "V",1,'s');
        Lemma plur = new Lemma("s","z",'N',"NSD",1,'s');
        Lemma plps = new Lemma("s~","z~",'N',"NMSD",1,'s');
        Lemma ger = new Lemma("ing",".ɪŋ",'S',"V",'s');
        Lemma infg = new Lemma("in~",".ɪn~",'S',"V",'s');
        Lemma paspart = new Lemma("en",".ən",'S',"V",'s');
        Lemma irrpl = new Lemma("en",".ən",'N',"N",12,'s');
        Lemma adverb = new Lemma("ly", ".liː",'B',"AS",5,'s');
        Lemma adverbIC = new Lemma("ally", ".liː",'B',"MASN",7,'s');
        Lemma nomin = new Lemma("ness", ".nəs",'N',"AB",'s');
        Lemma full = new Lemma("ful", ".fəl",'A',"NM",'s');
        Lemma nomd = new Lemma("ed", "d",'A',"NM",1,'s');
        Lemma less = new Lemma("less", "ləs",'A',"NM",'s');
        Lemma ish = new Lemma("ish",".ɪʃ",'A',"NM",'s');
        Lemma compar = new Lemma("er", ".ə˞",'̠',"AB",4,'s');
        Lemma superl = new Lemma("est", ".ɪst",'̠',"AB",4,'s');
        Lemma agent = new Lemma("er", ".ə˞",'N',"V",4,'s');
        Lemma no = new Lemma("no", "nəʊ", 'B','s');
        Lemma able = new Lemma("able", ".ə.bəl",'S',"V",4,'s');
        Lemma ably = new Lemma("ably", ".əb.liː",'B',"V",4,'s');
        Lemma ible = new Lemma("ible", ".ɪ.bəl",'S',"V",4,'s');
        Lemma ibly = new Lemma("ibly", ".ɪb.liː",'B',"V",4,'s');
        Lemma ment = new Lemma("ment",".mənt",'N',"AV",'s');
        Lemma re = new Lemma("re","ˌɹiː.",'̠',"VASBM",'p');
        Lemma un = new Lemma("un","ˌʌn.",'̠',"VASBM",'p');
        Lemma in = new Lemma("in","ˌɪn.",'̠',"VASBM",'p');
        Lemma im = new Lemma("im","ˌɪm.",'̠',"VASBM",'p');
        Lemma ir = new Lemma("ir","ˌɪ.",'̠',"VASBM",'p');
        Lemma over = new Lemma("over","ˌoʊ.və˞.",'̠',"NVASBMH",'p');
        Lemma under = new Lemma("under","ˌʌn.də˞.",'̠',"NVASBMH",'p');
        Lemma sup = new Lemma("super","ˈsuːpə˞.",'̠',"NVASBM",'p');
        Lemma en = new Lemma("en","ˌɪn.",'V',"ASB",'p');
        Lemma pre = new Lemma("pre","ˌpɹiː.",'̠',"NVASBM",'p');
        Lemma post = new Lemma("post","ˌpoʊst.",'̠',"NVASBM",'p');
        Lemma meta = new Lemma("meta","ˈmɛ.tə.",'̠',"NVASBM",'p');
        Lemma firsig = new Lemma("i", "aɪ",'R');
        Lemma frsgx = new Lemma("ih", "aɪ",'R');
        Lemma secper = new Lemma("you", "juː",'R');
        Lemma macsig = new Lemma("he", "hiː",'R');
        Lemma femsig = new Lemma("she", "ʃiː",'R');
        Lemma neuter = new Lemma("it", "ɪt",'R');
        Lemma firplur = new Lemma("we", "wiː",'R');
        Lemma thirplur = new Lemma("they", "ðeɪ",'R');
        Lemma thplinf = new Lemma("em", "əm",'R');
        Lemma macc = new Lemma("him", "hɪm",'R');
        Lemma macinf = new Lemma("im", "ɪm",'R');
        Lemma placc = new Lemma("them", "ðɛm",'R');
        Lemma macgen = new Lemma("his", "hɪz",'R');
        Lemma femgen = new Lemma("her", "hɜ˞",'R');
        Lemma femgenemph = new Lemma("hers", "hɜ˞z",'R');
        Lemma fginf = new Lemma("er", "ɜ˞",'R');
        Lemma plurgen = new Lemma("their", "ðɛə˞",'R');
        Lemma frsgacc = new Lemma("me", "miː", 'R');
        Lemma frplacc = new Lemma("us", "ʌs", 'R');
        Lemma firsgen = new Lemma("my", "maɪ",'R');
        Lemma secgen = new Lemma("your", "jɔ˞",'R');
        Lemma secgenemph = new Lemma("yours", "jɔ˞z",'R');
        Lemma firplgen = new Lemma("our", "aʊə˞",'R');
        Lemma firplgenemph = new Lemma("ours", "aʊə˞z",'R');
        Lemma andc = new Lemma("and", "ænd",'C');
        Lemma butc = new Lemma("but", "bʌt",'C');
        Lemma that = new Lemma("that", "ðæt",'R');
        Lemma few = new Lemma("few", "fjuː",'A');
        Lemma not = new Lemma("not", "nɒt",'B');
        Lemma as = new Lemma("as", "æz",'P');
        Lemma atp = new Lemma("at", "æt",'P');
        Lemma iff = new Lemma("if", "ɪf",'C');
        Lemma ofp = new Lemma("of", "ɒv",'P', 8);
        Lemma inp = new Lemma("in", "ɪn",'P');
        Lemma onp = new Lemma("on", "ɒn",'P');
        Lemma throf = new Lemma("thereof", "ðɛə˞ˈɒv",'B', 8);
        Lemma forp = new Lemma("for", "fɔ˞", 'P');
        Lemma fromp = new Lemma("from", "fɹɒm", 'P', 8);
        Lemma orc = new Lemma("or", "ɔ˞", 'C');
        Lemma eith = new Lemma("either", "ˈaɪ.ðə˞", 'C');
        Lemma neith = new Lemma("neither", "ˈnaɪ.ðə˞", 'C');
        Lemma nor = new Lemma("nor", "nɔ˞", 'C');
        Lemma top = new Lemma("to", "tuː", 'P');
        Lemma orct = new Lemma("o", "ə", 'C');
        Lemma andct = new Lemma("n", "ən", 'C');
        Lemma was = new Lemma("was", "wɒz",'V', 8);
        Lemma what = new Lemma("what", "wɒt",'R', 8);
        Lemma art = new Lemma("a", "ə",'R');
        Lemma nart = new Lemma("an", "ən",'R');
        Lemma dart = new Lemma("the", "ðə",'R');
        Lemma some = new Lemma("some","sʌm",'R');
        Lemma am = new Lemma("am", "æm",'H');
        Lemma are = new Lemma("are", "ɑ˞",'H');
        Lemma is = new Lemma("is", "ɪz",'H');
        Lemma were = new Lemma("were", "wɜ˞",'H');
        Lemma be = new Lemma("be", "biː",'H');
        Lemma being = new Lemma("being", "ˈbiː.ɪŋ",'S');
        Lemma been = new Lemma("been", "bɪn",'S');
        Lemma doo = new Lemma("do", "duː",'H');
        Lemma does = new Lemma("does", "dʌz",'H');
        Lemma did = new Lemma("did", "dɪd",'H');
        Lemma doing = new Lemma("doing", "ˈduːɪŋ",'S');
        Lemma done = new Lemma("done", "dʌn",'S');
        Lemma have = new Lemma("have", "hæv",'H');
        Lemma has = new Lemma("has", "hæz",'H');
        Lemma had = new Lemma("had", "hæd",'S');
        Lemma having = new Lemma("having", "ˈhæ.vɪŋ",'S');
        Lemma can1 = new Lemma("can", "kæn", 'H');
        Lemma can2 = new Lemma("can", "kæn", 'V');
        Lemma can3 = new Lemma("can", "kæn", 'N');
        Lemma will1 = new Lemma("will", "wɪl", 'H');
        Lemma will2 = new Lemma("will", "wɪl", 'V');
        Lemma will3 = new Lemma("will", "wɪl", 'N');
        Lemma cant = new Lemma("can~t", "kɑːn~t", "kæn~t", 'X', 10);
        Lemma wont = new Lemma("won~t", "wəʊn~t", 'X');
        Lemma dont = new Lemma("don~t", "dəʊn~t", 'X');
        Lemma would = new Lemma("would", "wʊd", 'H');
        Lemma could = new Lemma("could", "kʊd", 'H');
        Lemma should = new Lemma("should", "ʃʊd", 'H');
        Lemma ought = new Lemma("ought", "ɔːt", 'H');
        Lemma shall = new Lemma("shall", "ʃæl", 'H');
        Lemma shant = new Lemma("shan~t", "ʃɑːn~t", "ʃæn~t", 'X', 10);
        Lemma mustnt = new Lemma("mustn~t", "mʌs~ənt", 'X', 10);
        Lemma cause = new Lemma("~cause","~kɒz",'C',8);
        Lemma bout = new Lemma("~bout","~baʊt",'P');
        Lemma round = new Lemma("~round","~round",'P');
        Lemma twas = new Lemma("~twas","~twɒz",'V',8);
        Lemma tis = new Lemma("~tis","~tɪz",'V');
        Lemma til = new Lemma("~til", "~tɪl", 'C');
        Lemma em = new Lemma("~em","~əm",'R');
        Lemma nd = new Lemma("~n~","ən~",'R');
        Lemma gonna = new Lemma("gonna", "ˈgʌnə", 'X');
        Lemma aint = new Lemma("ain~t", "eɪn~t", 'X');
        Lemma myself = new Lemma("myself", "maɪˈsɛlf",'R');
        Lemma yourself = new Lemma("yourself", "jɔ˞ˈsɛlf",'R');
        Lemma himself = new Lemma("himself", "hɪmˈsɛlf",'R');
        Lemma herself = new Lemma("herself", "hɜ˞ˈsɛlf",'R');
        Lemma ourselves = new Lemma("ourselves", "aʊə˞ˈsɛlvz",'R');
        Lemma yourselves = new Lemma("yourselves", "jɔ˞ˈsɛlvz",'R');
        Lemma themselves = new Lemma("themselves", "ðɛmˈsɛlvz",'R');
        Lemma somebody = new Lemma("somebody", "ˈsʌm.bɒ.diː",'R',8);
        Lemma anybody = new Lemma("anybody", "ˈɛ.niːˌbɒdiː",'R',8);
        Lemma nobody = new Lemma("nobody", "ˈnoʊ.bɒ.diː",'R',8);
        Lemma lives1 = new Lemma("lives", "lɪvz", 'V');
        Lemma lives2 = new Lemma("lives", "laɪvz", 'N');
        Lemma read1 = new Lemma("read", "ɹiːd", 'V');
        Lemma read2 = new Lemma("read", "ɹɛd", 'A');
        Lemma wound1 = new Lemma("wound", "wuːnd", 'V');
        Lemma wound2 = new Lemma("wound", "wuːnd", 'N');
        Lemma wound3 = new Lemma("wound", "waʊnd", 'A');
        Lemma wind1 = new Lemma("wind", "wɪnd", 'V');
        Lemma wind2 = new Lemma("wind", "wɪnd", 'N');
        Lemma wind3 = new Lemma("wind", "waɪnd", 'V');
        Lemma used1 = new Lemma("used", "juːst", 'A');
        Lemma used2 = new Lemma("used", "juːzd", 'S');
        Lemma proofread1 = new Lemma("proofread", "ˈpɹuː.fɹiːd", 'V');
        Lemma proofread2 = new Lemma("proofread", "ˈpɹuː.fɹɛd", 'A');
        Lemma bowed1 = new Lemma("bowed", "baʊd", 'A');
        Lemma bowed2 = new Lemma("bowed", "bəʊd", 'A');
        Lemma lowering1 = new Lemma("lowering", "ˈləʊə˞.ɪŋ", 'S');
        Lemma lowering2 = new Lemma("lowering", "ˈlaʊə˞.ɪŋ", 'A');
        Lemma perfect1 = new Lemma("perfect","ˈpɜ˞.fɛkt",'A');
        Lemma perfect2 = new Lemma("perfect","pə˞ˈfɛkt",'V');
        Lemma perfect3 = new Lemma("perfect","ˈpɜ˞.fɛkt",'N');
        Lemma blessed1 = new Lemma("blessed","ˈblɛ.sɪd",'A');
        Lemma blessed2 = new Lemma("blessed","blɛst",'S');
        Lemma cursed1 = new Lemma("cursed","ˈkɜ˞.sɪd",'A');
        Lemma cursed2 = new Lemma("cursed","kɜ˞st",'S');
        Lemma learned1 = new Lemma("learned","ˈlɜ˞.nɪd",'A');
        Lemma learned2 = new Lemma("learned","lɜ˞nd",'S');
        Lemma curse = new Lemma("curse","kɜ˞s",'V');
        Lemma bless = new Lemma("bless","blɛs",'V');
        Lemma learn = new Lemma("learn","lɜ˞n",'V');
        blessed1 = Lemma.structure(blessed1,bless,past);
        blessed2 = Lemma.structure(blessed2,bless,past);
        cursed1 = Lemma.structure(cursed1,curse,past);
        cursed2 = Lemma.structure(cursed2,curse,past);
        learned1 = Lemma.structure(learned1,learn,past);
        learned2 = Lemma.structure(learned2,learn,past);
        Lemma dole1 = new Lemma("dole","doʊl",'V');
        Lemma dole2 = new Lemma("dole","doʊl",'N');
        Lemma putting1 = new Lemma("putting","ˈpʊ.tɪŋ",'S');
        Lemma putting2 = new Lemma("putting","ˈpʌ.tɪŋ",'S');
        Lemma put = new Lemma("put","pʊt",'V',0);
        Lemma putt = new Lemma("putt","pʌt",'V',0);
        Lemma use1 = new Lemma("use","juːs",'N',0);
        Lemma use2 = new Lemma("use","juːz",'V',0);
        putting1 = Lemma.structure(putting1,put,ger);
        putting2 = Lemma.structure(putting2,putt,ger);
        used2 = Lemma.structure(used2,use2,past);
        Lemma hood = new Lemma("hood", "hʊd",'N',"NMA");
        Lemma ical = new Lemma("ical","ɪ.kəl",'A',"NMA",'s','l');
        Lemma ist = new Lemma("ist","ɪst",'N',"NMA",'s','l');
        Lemma ism = new Lemma("ism","ɪ.zəm",'N',"NMA",'s','l');
        Lemma ity = new Lemma("ity","ɪ.tiː",'N',"A",'s','l');
        Lemma ous = new Lemma("ous","əs",'A',"NM",'s','l');
        Lemma ic = new Lemma("ic","ɪk",'A',"NM",'s','l');
        Lemma ive = new Lemma("ive","ɪv",'A',"NMV",'s','l');
        Lemma ory = new Lemma("ory","ə.ɹiː",'A',"V",'s','l');
        Lemma ion = new Lemma("ion","ən",'N',"VA",'s','l');
        Lemma y = new Lemma("y","iː",'N',"NM",'s','l');
        Lemma al = new Lemma("al","əl",'S',"NM",'s','l');
        Lemma ant = new Lemma("ant","ənt",'S',"NM",'s','l');
        Lemma ent = new Lemma("ent","ənt",'S',"NM",'s','l');
        Lemma ance = new Lemma("ance","əns",'N',"NMA",'s','l');
        Lemma ence = new Lemma("ence","əns",'N',"NMA",'s','l');
        Lemma ancy = new Lemma("ancy","ən.siː",'N',"NMA",'s','l');
        Lemma ency = new Lemma("ency","ən.siː",'N',"NMA",'s','l');
        Lemma whatever = new Lemma("whatever", "wɒˈtɛ.və˞",'R', 8);
        Lemma whatsoever = new Lemma("whatsoever", "ˌwɒt.səʊˈɛ.və˞",'R', 8);
        Lemma using = new Lemma("using","ˈjuːzɪŋ",'S');
        Lemma exclam = new Lemma("o","oʊ",'E');
        Lemma man1 = new Lemma("man","mæn",'N',16);
        man1.setAllo("men");
        man1.setAllomorph1("mɛn");
        //man1.setAllomorph2("mɛn");
        Lemma man2 = new Lemma("man","mæn",'V');
        Lemma men = new Lemma("men","mɛn",'N',16);
        Lemma woman = new Lemma("woman", "ˈwʊ.mən",'N',16);
        woman.setAllo("women");
        woman.setAllomorph1("ˈwɪ.mɪn");
        //woman.setAllomorph2("ˈwɪ.mɪn");
        Lemma women = new Lemma("women", "ˈwɪ.mɪn",'N',16);
        Lemma life = new Lemma("life", "laɪf",'N',3);
        life.setAllo("lives");
        life.setAllomorph1("laɪv");
        //life.setAllomorph2("laɪv");
        Lemma knife1 = new Lemma("knife", "naɪf",'N',3);
        knife1.setAllo("knives");
        knife1.setAllomorph1("naɪv");
        //knife1.setAllomorph2("naɪv");
        Lemma knife2 = new Lemma("knife", "knife",'V');
        Lemma knives = new Lemma("knives","naɪvz",'N',3);
        Lemma wolf1 = new Lemma("wolf", "wʊlf",'N',3);
        wolf1.setAllo("wolves");
        wolf1.setAllomorph1("wʊlv");
        //wolf1.setAllomorph2("wʊlv");
        Lemma wolf2 = new Lemma("wolf", "wʊlf",'V',0);
        Lemma wolves = new Lemma("wolves","wʊlvz",'N',3);
        Lemma wife = new Lemma("wife","waɪf",'N',3);
        wife.setAllo("wives");
        wife.setAllomorph1("waɪv");
        //wife.setAllomorph2("waɪv");
        Lemma wives = new Lemma("wives","waɪvz",'N',3);
        Lemma half = new Lemma("half","hɑːf","hæf",'N',3);
        half.setAllo("halves");
        half.setAllomorph1("hɑːv");
        //half.setAllomorph2("hæv");
        Lemma halves = new Lemma("halves","hɑːvz","hævz",'N',3);
        Lemma halve = new Lemma("halve","hɑːvz","hæv",'V',0);
        Lemma truth = new Lemma("truth","tɹuːθ",'N',3);
        truth.setAllomorph1("tɹuːð");
        //dwarf1.setAllomorph2("tɹuːð");
        Lemma truths = new Lemma("truths","tɹuːðz",'N',3);
        Lemma dwarf1 = new Lemma("dwarf","dwɔ˞f",'N',3);
        dwarf1.setAllo("dwarves");
        dwarf1.setAllomorph1("dwɔ˞v");
        //dwarf1.setAllomorph2("dwɔ˞v");
        Lemma dwarf2 = new Lemma("dwarf","dwɔ˞f",'V');
        Lemma dwarf3 = new Lemma("dwarf","dwɔ˞f",'A');
        Lemma dwarves = new Lemma("dwarves","dwɔ˞vz",'N',3);
        Lemma house1 = new Lemma("house","haʊs",'N',3);
        Lemma house2 = new Lemma("house","haʊz",'V');
        house1.setAllomorph1("haʊz");
        //house1.setAllomorph2("haʊz");
        Lemma houses = new Lemma("houses","ˈhaʊ.zɪz",'N',3);
        Lemma scarf1 = new Lemma("scarf","skɑ˞f",'N',3);
        Lemma scarf2 = new Lemma("scarf","skɑ˞f",'V',0);
        scarf1.setAllo("scarves");
        scarf1.setAllomorph1("skɑ˞v");
        //scarf1.setAllomorph2("skɑ˞v");
        Lemma scarves = new Lemma ("scarves","skɑ˞vz",'N',3);
        Lemma wreath = new Lemma("wreath","ɹiːθ",'N',3);
        wreath.setAllo("scarves");
        wreath.setAllomorph1("ɹiːð");
        //wreath.setAllomorph2("ɹiːð");
        Lemma child = new Lemma("child","t͡ʃaɪld",'N',12); 
        child.setAllo("children");
        child.setAllomorph1("ˈt͡ʃɪldɹ");
        //child.setAllomorph2("ˈt͡ʃɪldɹ");
        Lemma children = new Lemma("children","t͡ʃɪl.dɹən",'N',12); 
        Lemma father = new Lemma("father","ˈfɑː.ðə˞",'N',0);
        Lemma singing = new Lemma("singing","ˈsɪ.ŋɪŋ",'S',0);
        Lemma meant = new Lemma("meant","mɛnt",'S',0);
        Lemma learnt = new Lemma("learnt","lɜ˞nt",'S',0);
        Lemma dreamt = new Lemma("dreamt","dɹɛmt",'S',0);
        Lemma mr = new Lemma("mr`","mɹ`","mɹ`",'N',0);
        mr.setUnsyncUK("ˈmɪs.təɹ");
        mr.setUnsyncUS("ˈmɪs.tə˞");
        Lemma mrs = new Lemma("mrs`","msz`","msz`",'N',0);
        mrs.setUnsyncUK("ˈmɪ.sɪz");
        mrs.setUnsyncUS("ˈmɪ.sɪz");
        Lemma ms = new Lemma("ms`","ms`","ms`",'N',0);
        ms.setUnsyncUK("mɪs");
        ms.setUnsyncUS("mɪs");
        Lemma dr = new Lemma("dr`","dɹ`","dɹ`",'N',0);
        dr.setUnsyncUK("ˈdɒk.təɹ");
        dr.setUnsyncUS("ˈdɑːk.tə˞");
        Lemma jr = new Lemma("jr`","d͡ʒɹ`","d͡ʒɹ`",'N',0);
        jr.setUnsyncUK("ˈd͡ʒuːn.jəɹ");
        jr.setUnsyncUS("ˈd͡ʒuːn.jə˞");
        Lemma sr = new Lemma("sr`","sɹ`","sɹ`",'N',0);
        sr.setUnsyncUK("ˈsiːn.jəɹ");
        sr.setUnsyncUS("ˈsiːn.jə˞");
        Lemma drs = new Lemma("drs`","dɹz`","dɹz`",'N',0);
        drs.setUnsyncUK("ˈdɒk.təɹz");
        drs.setUnsyncUS("ˈdɑːk.tə˞z");
        Lemma mrz = new Lemma("mrs`","mɹz`","mɹz`",'N',0);
        mrz.setUnsyncUK("ˈmɪs.təɹz");
        mrz.setUnsyncUS("ˈmɪs.tə˞z");
        Lemma eg = new Lemma("e`g`","ɛ`g`","ɛ`g`",'N',0);
        eg.setUnsyncUK("iːˈd͡ʒiː");
        eg.setUnsyncUS("iːˈd͡ʒiː");
        Lemma ie = new Lemma("i`e`","ɪ`ɛ`","ɪ`ɛ`",'N',0);
        ie.setUnsyncUK("aɪˈiː");
        ie.setUnsyncUS("aɪˈiː");
        Lemma etc = new Lemma("etc`","ɛts`","ɛts`",'N',0);
        etc.setUnsyncUK("ɛtˈsɛ.t]ɹ.ə");
        etc.setUnsyncUS("ɛtˈsɛ.t]˞.ə");
        Lemma dna = new Lemma("dna","dnæ",'N',0);
        /*cans.add(can1);
        cans.add(can2);
        cans.add(can3);
        wills.add(will1);
        wills.add(will2);
        wills.add(will3);
        reads.add(read1);
        reads.add(read2);
        lives3.add(lives1);
        lives3.add(lives2);
        winds.add(wind1);
        winds.add(wind2);
        winds.add(wind3);
        wounds.add(wound1);
        wounds.add(wound2);
        wounds.add(wound3);
        useds.add(used1);
        useds.add(used2);
        proofreads.add(proofread1);
        proofreads.add(proofread2);
        bowed.add(bowed1);
        bowed.add(bowed2);
        lowering3.add(lowering1);
        lowering3.add(lowering2);
        perfect.add(perfect1);
        perfect.add(perfect2);
        perfect.add(perfect3);
        blessed3.add(blessed1);
        blessed3.add(blessed2);
        cursed3.add(cursed1);
        cursed3.add(cursed2);
        learned3.add(learned1);
        learned3.add(learned2);
        doles.add(dole1);
        doles.add(dole2);
        puttings.add(putting1);
        puttings.add(putting2);
        mans.add(man1);
        mans.add(man2);
        knifes.add(knife1);
        knifes.add(knife2);
        wolfs.add(wolf1);
        wolfs.add(wolf2);
        dwarfs.add(dwarf1);
        dwarfs.add(dwarf2);
        dwarfs.add(dwarf3);
        houses.add(house1);
        houses.add(house2);*/
        suffixes.add(nomin);
        suffixes.add(less);
        suffixes.add(ish);
        suffixes.add(contrL);
        suffixes.add(contrM);
        suffixes.add(contrR);
        suffixes.add(contrS);
        suffixes.add(contrD);
        suffixes.add(contrNT);
        suffixes.add(contrT);
        suffixes.add(contrV);
        suffixes.add(poss);
        suffixes.add(thirdsg);
        suffixes.add(past);
        suffixes.add(plur);
        suffixes.add(plps);
        suffixes.add(ger);
        suffixes.add(infg);
        suffixes.add(paspart);
        suffixes.add(irrpl);
        suffixes.add(full);
        suffixes.add(compar);
        suffixes.add(superl);
        suffixes.add(agent);
        suffixes.add(able);
        suffixes.add(ably);
        suffixes.add(ible);
        suffixes.add(ibly);
        suffixes.add(adverb);
        suffixes.add(adverbIC);
        suffixes.add(ment);
        backsuff.add(nomd);
        //suffixes.add(hood);
        prefixes.add(re);
        prefixes.add(under);
        prefixes.add(un);
        prefixes.add(in);
        prefixes.add(im);
        prefixes.add(ir);
        prefixes.add(over);
        prefixes.add(sup);
        prefixes.add(en);
        prefixes.add(pre);
        prefixes.add(post);
        prefixes.add(meta);
        inflects.add(thirdsg.getSpelling());
        inflects.add(past.getSpelling());
        inflects.add(plur.getSpelling());
        inflects.add(poss.getSpelling());
        inflects.add(plps.getSpelling());
        inflects.add(contrS.getSpelling());
        latinate.add(ical);
        latinate.add(ist);
        latinate.add(ism);
        latinate.add(ity);
        latinate.add(ous);
        latinate.add(ic);
        latinate.add(ive);
        latinate.add(ory);
        latinate.add(ion);
        latinate.add(y);
        latinate.add(al);
        latinate.add(ant);
        latinate.add(ent);
        latinate.add(ance);
        latinate.add(ence);
        latinate.add(ancy);
        latinate.add(ency);
        lexicon.add(firsig);
        lexicon.add(frsgx);
        lexicon.add(secper);
        lexicon.add(macsig);
        lexicon.add(femsig);
        lexicon.add(neuter);
        lexicon.add(firplur);
        lexicon.add(thirplur);
        lexicon.add(thplinf);
        lexicon.add(macc);
        lexicon.add(macinf);
        lexicon.add(placc);
        lexicon.add(macgen);
        lexicon.add(femgen);
        lexicon.add(femgenemph);
        lexicon.add(fginf);
        lexicon.add(plurgen);
        lexicon.add(frsgacc);
        lexicon.add(frplacc);
        lexicon.add(firsgen);
        lexicon.add(secgen);
        lexicon.add(secgenemph);
        lexicon.add(firplgen);
        lexicon.add(firplgenemph);
        lexicon.add(myself);
        lexicon.add(yourself);
        lexicon.add(himself);
        lexicon.add(herself);
        lexicon.add(ourselves);
        lexicon.add(yourselves);
        lexicon.add(themselves);
        lexicon.add(somebody);
        lexicon.add(anybody);
        lexicon.add(nobody);
        lexicon.add(andc);
        lexicon.add(butc);
        lexicon.add(that);
        lexicon.add(few);
        lexicon.add(not);
        lexicon.add(as);
        lexicon.add(atp);
        lexicon.add(iff);
        lexicon.add(ofp);
        lexicon.add(inp);
        lexicon.add(onp);
        lexicon.add(throf);
        lexicon.add(forp);
        lexicon.add(fromp);
        lexicon.add(orc);
        lexicon.add(top);
        //lexicon.add(orct);
        lexicon.add(eith);
        lexicon.add(neith);
        lexicon.add(nor);
        lexicon.add(andct);
        lexicon.add(was);
        lexicon.add(what);
        lexicon.add(whatever);
        lexicon.add(whatsoever);
        lexicon.add(art);
        lexicon.add(nart);
        lexicon.add(dart);
        lexicon.add(some);
        lexicon.add(be);
        lexicon.add(am);
        lexicon.add(are);
        lexicon.add(is);
        lexicon.add(were);
        lexicon.add(being);
        lexicon.add(been);
        lexicon.add(doo);
        lexicon.add(does);
        lexicon.add(did);
        lexicon.add(doing);
        lexicon.add(done);
        lexicon.add(have);
        lexicon.add(has);
        lexicon.add(had);
        lexicon.add(having);
        lexicon.add(cant);
        lexicon.add(wont);
        lexicon.add(dont);
        lexicon.add(could);
        lexicon.add(should);
        lexicon.add(ought);
        lexicon.add(would);
        lexicon.add(shall);
        lexicon.add(shant);
        lexicon.add(mustnt);
        lexicon.add(cause);
        lexicon.add(bout);
        lexicon.add(round);
        lexicon.add(twas);
        lexicon.add(tis);
        lexicon.add(til);
        lexicon.add(em);
        lexicon.add(nd);
        lexicon.add(gonna);
        lexicon.add(aint);
        lexicon.add(no);
        lexicon.add(using);
        lexicon.add(exclam);
        lexicon.add(man1);
        lexicon.add(man2);
        lexicon.add(woman);
        lexicon.add(child);
        lexicon.add(women);
        lexicon.add(men);
        lexicon.add(children);
        /*lexicon.add(wives);
        lexicon.add(wolves);
        lexicon.add(halves);
        lexicon.add(knives)
        lexicon.add(dwarves);
        lexicon.add(truth);
        lexicon.add(truths);
        lexicon.add(father);
        lexicon.add(bless);
        lexicon.add(learn);
        lexicon.add(singing);*/
        lexicon.add(meant);
        lexicon.add(learnt);
        lexicon.add(dreamt);
        lexicon.add(can1);
        lexicon.add(can2);
        lexicon.add(can3);
        lexicon.add(will1);
        lexicon.add(will2);
        lexicon.add(will3);
        lexicon.add(read1);
        lexicon.add(read2);
        lexicon.add(proofread1);
        lexicon.add(proofread2);
        lexicon.add(used1);
        lexicon.add(used2);
        lexicon.add(use1);
        lexicon.add(use2);
        lexicon.add(blessed1);
        lexicon.add(blessed2);
        lexicon.add(cursed1);
        lexicon.add(cursed2);
        lexicon.add(dole1);
        lexicon.add(dole2);
        lexicon.add(putting1);
        lexicon.add(putting2);
        lexicon.add(put);
        lexicon.add(putt);
        lexicon.add(perfect1);
        lexicon.add(perfect2);
        lexicon.add(lives1);
        lexicon.add(lives2);
        lexicon.add(bowed1);
        lexicon.add(bowed2);
        lexicon.add(mr);
        lexicon.add(mrs);
        //lexicon.add(mrz);
        lexicon.add(ms);
        lexicon.add(dr);
        lexicon.add(drs);
        lexicon.add(jr);
        lexicon.add(sr);
        lexicon.add(eg);
        lexicon.add(ie);
        lexicon.add(etc);
        lexicon.add(dna);
        /*lexicon.add(wound1);
        lexicon.add(wound2);
        lexicon.add(wind1);
        lexicon.add(wind2);
        homographs.put("can",cans);
        homographs.put("will",wills);
        homographs.put("lives",lives3);
        homographs.put("wound",wounds);
        homographs.put("read",reads);
        homographs.put("wind",winds);
        homographs.put("used",useds);
        homographs.put("proofread",proofreads);
        homographs.put("bowed",bowed);
        homographs.put("lowering",lowering3);
        homographs.put("perfect",perfect);
        homographs.put("blessed",blessed3);
        homographs.put("cursed",cursed3);
        homographs.put("dole",doles);
        homographs.put("putting",puttings);
        homographs.put("man",mans);
        homographs.put("learned",learned3);
        homographs.put("knife",knifes);
        homographs.put("wolf",wolfs);
        homographs.put("house",houses);
        homographs.put("dwarf",dwarfs);*/
        lowback.add("ˈsɒr.i");
        lowback.add("təˈmɒr.əʊ");
        lowback.add("ˈsɒr.əʊ");
        brityodrop.add("kənˈfɪɡ.ər");
        brityodrop.add("kənˌfɪɡ.əˈreɪ.ʃən");
        brityodrop.add("kənˈfɪɡ.ər");
        britrhdrop.add("kənˈsɜː.və.tər.i");
        voicealt.add("house");
        voicealt.add("knife");
        voicealt.add("wolf");
        voicealt.add("wife");
        voicealt.add("half");
        voicealt.add("dwarf");
        voicealt.add("truth");
        voicealt.add("scarf");
        voicealt.add("wreath");
        voicealt.add("life");
        voicealt.add("houses");
        voicealt.add("knives");
        voicealt.add("wolves");
        voicealt.add("wives");
        voicealt.add("halves");
        voicealt.add("dwarves");
        voicealt.add("truths");
        voicealt.add("scarves");
        voicealt.add("wreathes");
        voicealt.add("lives");
        ablaut.add("man");
        ablaut.add("woman");
        ablaut.add("men");
        ablaut.add("women");
        ablaut.add("tooth");
        ablaut.add("teeth");
        ablaut.add("foot");
        ablaut.add("feet");
        ablautforms.put("man","men");
        ablautforms.put("woman","women");
        ablautforms.put("tooth","teeth");
        ablautforms.put("foot","feet");
        ablautforms.put("men","man");
        ablautforms.put("women","woman");
        ablautforms.put("teeth","tooth");
        ablautforms.put("feet","foot");
        /*ablaut.add("child");
        ablaut.add("children");*/
        rStem.add("child");
        rStem.add("children");
        initImport.addAll(lexicon);
        importLex(update);
    }
    /*public void addHomograph (List<Lemma> homogs) {
        String keyword = (homogs).get(0).getSpelling();
        if (homographs.containsKey(keyword) && ((homographs.get(keyword)).get(0)).getDghtr1() == null && (homogs.get(0).getDghtr1()) != null) {
            homographs.remove(keyword);
        }
        if (!homographs.containsKey(keyword)) {
            List<Lemma> poss = new ArrayList<>();
            for (int nw = 0; nw < homogs.size(); nw++) {
                Lemma arv = homogs.get(nw);
                poss.add(arv);
            }
            homographs.put(keyword,poss);
        }
    }*/
    public void store (List<Lemma> additions) {
        List<Lemma> arrivals = new ArrayList<Lemma>();
        try {
            //System.out.println("Storing...");
            Lemma back = new Lemma();
            for (int it = 0; it < additions.size(); it++) {
                Lemma arriv = additions.get(it);
                arriv.setPronUK((arriv.getPronUK()).replaceAll("ʍ","w"));
                arriv.setPronUS((arriv.getPronUS()).replaceAll("ʍ","w"));
                if (!arriv.compare(back) && !containsItem(arriv) && !(arriv.getSpelling()).equals("|||")) {
                    //System.out.println(arriv.printParms());
                    arrivals.add(arriv);
                }
                back = arriv;
            }
            String keyword = (arrivals.get(0)).getSpelling();
            int locate = Morphology.findMorph(keyword);
            if (arrivals.size() > 1) {
                for (int nw = 0; nw < arrivals.size(); nw++) {
                    Lemma keylem = arrivals.get(nw);
                    /*String keyform = keylem.getSpelling();
                    List<Lemma> homs = new ArrayList<>();
                    homs.add(keylem);
                    for (int hw = nw+1; hw < arrivals.size(); hw++) {
                        Lemma testlem = arrivals.get(hw);
                        String testform = testlem.getSpelling();
                        if (testform.equals(keyform)) {
                            homs.add(testlem);
                            arrivals.remove(hw);
                        }
                    }
                    if (homs.size() > 1) {
                        addHomograph(homs);
                    }
                    else {
                        Lemma single = homs.get(0);
                        lexicon.add(single);
                    }*/
                    lexicon.add(keylem);
                }
            }
            else if (arrivals.size() == 1 && locate >= lexicon.size()) {
                Lemma arrival = arrivals.get(0);
                //System.out.println(arrival.printParms());
                lexicon.add(arrival);
            }
            else if (arrivals.size() == 1) {
                Lemma arrival = arrivals.get(0);
                if (arrival.getDghtr1() != null) {
                    int lct = findItem(arrival);
                    Lemma cmprsn = lexicon.get(lct);
                    if (cmprsn.getDghtr1() == null) {
                        lexicon.remove(lct);
                    }
                }
                locate = findItem(arrival);
                if (locate >= lexicon.size()) {         //changed from 0 on 7/15/2019
                    lexicon.add(arrival);
                }
            }
        }
        catch (IndexOutOfBoundsException exception) {
            //JOptionPane.showMessageDialog(new JFrame(), "Input/output error.  Please try again.");
        }
    }
    public List<String> exported = new ArrayList<>();
    public boolean containsItem (Lemma prob) {
        boolean incl = false;
        for (int io = 0; io < lexicon.size(); io++) {
            Lemma cmpr = lexicon.get(io);
            if (cmpr.compare(prob)) {
                incl = true;
            }
        }
        return incl;
    }
    public boolean dictContains (String prob) {
        //boolean homog = homographs.containsKey(prob);
        boolean entered = !(findWord(prob)).isEmpty();
        return entered;// || homog;
    }
    public List<Lemma> findWord (String prob) {
        List<Lemma> matches = Util.findInList(prob,lexicon);
        return matches;
    }
    public int findItem (Lemma prob) {
        int locat = lexicon.size();
        for (int io = 0; io < lexicon.size(); io++) {
            Lemma cmpr = lexicon.get(io);
            if (cmpr.compare(prob)) {
                locat = io;
                break;
            }
        }
        return locat;
    }
    public String writeMorph(Lemma inform) {
        String novel;
        if (inform.getSyncope() && !(inform.getUnsyncUK()).equals(inform.getUnsyncUS()) && !(inform.getUnsyncUS()).equals("|||")) {
            novel = inform.getSpelling()+"\t"+inform.getUnsyncUK()+" "+inform.getUnsyncUS()+"\t"+Lemma.decode(inform.getUsage())+"\t"+inform.getAlter();
        }
        else if (inform.getSyncope()) {
            novel = inform.getSpelling()+"\t"+inform.getUnsyncUK()+"\t"+Lemma.decode(inform.getUsage())+"\t"+inform.getAlter();
        }
        else if (!(inform.getPronUK()).equals(inform.getPronUS()) && !(inform.getPronUS()).equals("|||")) {
            novel = inform.getSpelling()+"\t"+inform.getPronUK()+" "+inform.getPronUS()+"\t"+Lemma.decode(inform.getUsage())+"\t"+inform.getAlter();
        }
        else {
            novel = inform.getSpelling()+"\t"+inform.getPronUK()+"\t"+Lemma.decode(inform.getUsage())+"\t"+inform.getAlter();
        }
        if (!(inform.getSel()).equals("")) {
            novel += "\t"+inform.getSel();
        }
        String allo = inform.getAllo();
        String altspl = inform.getAltSpl();
        if (inform.structured()) {
            String dotar1 = writeMorph(inform.getDghtr1());
            String dotar2 = writeMorph(inform.getDghtr2());
            novel += "\t["+dotar1+"\t+\t"+dotar2+"]";
            if (!allo.equals("|||")) {
                novel += "\t("+allo+")";
            }
            if (!altspl.equals("|||")) {
                novel += "\t-"+allo+"-";
            }
            /*novel += System.getProperty("line.separator")+dotar1;
            if (Lemma.isRoot(inform.getDghtr2())) {
                novel += System.getProperty("line.separator")+dotar2;
            }*/
        }
        else if (!allo.equals("|||")) {
            novel += "\t("+allo+")";
        }
        if (!altspl.equals("|||")) {
            novel += "\t-"+altspl+"-";
        }
        if (inform.getExempt()) {
            novel += "\tEXEMPT";
        }
        novel = Util.removeAll(novel,"(ɹ)");
        return novel;
    }
    public void exportEntries(List<Lemma> data) {
        String dir = System.getProperty("user.dir");
        String address = dir+"\\lexicon.dic";
        Lemma back = new Lemma();
        List<Lemma> arrivals = new ArrayList<Lemma>();
        for (int it = 0; it < data.size(); it++) {
            Lemma arriv = data.get(it);
            //System.out.println(arriv.printParms());
            arriv.setPronUK((arriv.getPronUK()).replaceAll("r","ɹ"));
            arriv.setPronUK((arriv.getPronUK()).replaceAll("ʍ","w"));
            arriv.setPronUS((arriv.getPronUS()).replaceAll("ʍ","w"));
            String longform1 = arriv.getUnsyncUK();
            String longform2 = arriv.getUnsyncUS();
            if (!longform1.equals("|||")) {
                arriv.setUnsyncUK((arriv.getUnsyncUK()).replaceAll("r","ɹ"));
                arriv.setUnsyncUK((arriv.getUnsyncUK()).replaceAll("ʍ","w"));
            }
            if (!longform2.equals("|||")) {
                arriv.setUnsyncUS((arriv.getUnsyncUS()).replaceAll("ʍ","w"));
            }
            if (!arriv.compare(back)) {// && !containsItem(arriv)) {    //altered 7/18/2019
                arrivals.add(arriv);
            }
            back = arriv;
        }
        try {
            File infile = new File(address);
            File outfile = new File(dir+"\\TEMP.txt");
            String lineSep = System.getProperty("line.separator");
            for (int ih = 0; ih < arrivals.size(); ih++) {
                Lemma arriv = arrivals.get(ih);
                String search = arriv.getSpelling();
                String nuentri = writeMorph(arriv);
                //System.out.println(nuentri);
                boolean inlex = dictContains(search);
                boolean eligible = !exported.contains(nuentri) && !imported.contains(lineSep+nuentri) && !Util.listContains(search,initImport) && !search.equals("|||");
                if (nuentri.contains("[") && eligible) {
                    String trunc = nuentri.substring(0,nuentri.indexOf("\t["));
                    int truncimp = imported.indexOf(trunc);
                    if (imported.contains(lineSep+trunc)) {
                        imported = imported.substring(0,truncimp-1)+imported.substring(truncimp+trunc.length());
                    }
                }
                if (eligible) {
                    exported.add(nuentri);
                    String heading = lineSep+arriv.getSpelling()+"\t";
                    if (imported.contains(heading)) {
                        int prm = imported.indexOf(lineSep,imported.indexOf(heading));
                        imported = imported.substring(0,prm)+lineSep+nuentri+lineSep+imported.substring(prm);
                    }
                    else {
                        imported += nuentri+lineSep;
                    }
                }
            }
            PrintWriter output = new PrintWriter(dir+"\\TEMP.txt","UTF-8");
            if (imported.length() > 1) {
                imported = imported.replaceAll(lineSep+lineSep,lineSep);
                imported = imported.replaceAll("\n\n","\n");
                /*if (imported.endsWith(lineSep)) {
                    imported = imported.substring(0,imported.lastIndexOf(lineSep));
                }*/
                output.write(imported);
                output.flush();
                output.close();
                infile.delete();
                outfile.renameTo(infile);
            }
        }
        catch (IOException exception) {
            JOptionPane.showMessageDialog(new JFrame(), "Input/output error.  Please try again.");
        }
    }
    public Lemma readMorph (String inform) {
        String infprep = inform;
        String treetag = "";
        if (inform.contains("\t[")) {
            try {
                infprep = inform.substring(0,inform.indexOf("\t["))+inform.substring(inform.lastIndexOf("]")+1);
            }
            catch (StringIndexOutOfBoundsException toolong) {
                infprep = inform.substring(0,inform.indexOf("\t["));
            }
            try {
                treetag = inform.substring(inform.indexOf("["),inform.lastIndexOf("]")+1);
            }
            catch (StringIndexOutOfBoundsException toolong) {
                treetag = inform.substring(inform.indexOf("["));
            }
            
        }
        String[] parms = infprep.split("\t");
        String writt = parms[0];
        String snds = parms[1];
        String amsnd;
        String brsnd;
        if (parms[1].contains(" ")) { 
            brsnd = snds.substring(0,snds.indexOf(" "));
            amsnd = snds.substring(snds.indexOf(" ")+1);
        }
        else {
            brsnd = parms[1];
            amsnd = brsnd;
        }
        String slct = "";
        String allo = "|||";
        String altspl = "|||";
        try {
            String extra = parms[4];
            if (extra.startsWith("(") && extra.endsWith(")")) {
                allo = parms[4].substring(1,parms[4].length()-1);
            }
            else if (extra.startsWith("-") && extra.endsWith("-")) {
                altspl = parms[4].substring(1,parms[4].length()-1);
            }
            else {
                slct = parms[4];
            }
        }
        catch (Exception nal) {
            //do nothing;
        }
        try {
            String extra = parms[5];
            if (extra.startsWith("(") && extra.endsWith(")")) {
                allo = parms[5].substring(1,parms[5].length()-1);
            }
            else if (extra.startsWith("-") && extra.endsWith("-")) {
                altspl = parms[5].substring(1,parms[5].length()-1);
            }
        }
        catch (ArrayIndexOutOfBoundsException nal) {
            //do nothing;
        }
        try {
            String extra = parms[6];
            if (extra.startsWith("(") && extra.endsWith(")")) {
                allo = parms[6].substring(1,parms[6].length()-1);
            }
            else if (extra.startsWith("-") && extra.endsWith("-")) {
                altspl = parms[6].substring(1,parms[6].length()-1);
            }
        }
        catch (ArrayIndexOutOfBoundsException nal) {
            //do nothing;
        }
        int spec = Integer.parseInt(parms[3]);//Util.removeAll(parms[3],"]"));
        Lemma novel = new Lemma(writt,brsnd,amsnd,Lemma.encode(parms[2]),slct,spec);
        if (!treetag.equals("")) {
            String daughters = treetag.substring(1,treetag.length()-1);
            String prima = daughters.substring(0,daughters.indexOf("\t+\t"));
            String secunda = daughters.substring(daughters.indexOf("\t+\t")+3);
            Lemma filia1 = readMorph(prima.trim());
            Lemma filia2 = readMorph(secunda.trim());
            filia1.setSis(filia2);
            filia1.setMthr(novel);
            filia2.setSis(filia1);
            filia2.setMthr(novel);
            novel.setDghtr1(filia1);
            novel.setDghtr2(filia2);
        }
        novel.setAllo(allo);
        novel.setAltSpl(altspl);
        novel.setExempt(inform.endsWith("\tEXEMPT"));
        return novel;      
    }
    public static String imported = "";
    public static List<Lemma> initImport = new ArrayList<>();
    public void importLex (boolean update) {
        String dir = System.getProperty("user.dir");
        String address = dir+"\\lexicon.dic";
        //JOptionPane.showMessageDialog(new JFrame(), address);
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(address),"UTF-8"));
            Lemma prmrf = new Lemma();
            String back = "|||";
            String line;
            while ((line = input.readLine()) != null) {
                if (line.startsWith("//") || !line.contains("\t")) {
                    if (!back.equals("|||")) {
                        lexicon.add(prmrf);
                        initImport.add(prmrf);
                    }
                    if (update) {
                        imported += line+System.getProperty("line.separator");
                    }
                    continue;
                }
                Lemma add = readMorph(line);
                String writt = add.getSpelling();
                lexicon.add(add);  
                initImport.add(add);
                if (update) {
                    imported += line+System.getProperty("line.separator");
                }
                back = writt;
                prmrf = add;
            }
            input.close();
            lexicon.add(prmrf);
            initImport.add(prmrf);
        }
	catch (IOException exception) {
		//JOptionPane.showMessageDialog(new JFrame(), "Input/output error.  Please try again.");
	}
    }
    public List<Lemma> getLex() {
        return lexicon;
    }
}