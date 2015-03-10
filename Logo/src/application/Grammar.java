package application;

public class Grammar extends edu.hendrix.grambler.Grammar {
    public Grammar() {
        super();
        addProduction("lines", new String[]{"lines", "line"}, new String[]{"line"});
        addProduction("line", new String[]{"expr", "newline"}, new String[]{"expr"}, new String[]{"expr0", "newline"}, new String[]{"expr0"});
        addProduction("expr", new String[]{"abbr", "sp", "num", "sp"}, new String[]{"abbr1", "sp"});
        addProduction("expr0", new String[]{"'repeat'", "sp", "num", "sp", "expr2"}, new String[]{"'if'", "sp", "expr1", "sp", "expr2"}, new String[]{"'ifelse'", "sp", "expr1", "sp", "expr2", "sp", "expr2"});
        addProduction("expr1", new String[]{"num", "sp", "eq", "sp", "num"}, new String[]{"'('", "num", "sp", "eq", "sp", "num", "')'"}, new String[]{"expr1", "sp", "'or'", "sp", "expr1"}, new String[]{"expr1", "sp", "'and'", "sp", "expr1"});
        addProduction("expr3", new String[]{"expr3", "sp", "expr"}, new String[]{"expr"});
        addProduction("expr2", new String[]{"'['", "expr3", "']'"});
        addProduction("abbr", new String[]{"\"fd\""}, new String[]{"\"bk\""}, new String[]{"\"lt\""}, new String[]{"\"rt\""}, new String[]{"\"forward\""}, new String[]{"\"back\""}, new String[]{"\"right\""}, new String[]{"\"left\""});
        addProduction("abbr1", new String[]{"\"pd\""}, new String[]{"\"pu\""}, new String[]{"\"cs\""}, new String[]{"\"st\""}, new String[]{"\"ht\""}, new String[]{"\"pendown\""}, new String[]{"\"penup\""}, new String[]{"\"home\""}, new String[]{"\"showturtle\""}, new String[]{"\"hideturtle\""}, new String[]{"\"clearscreen\""});
        addProduction("num", new String[]{"\"\\d+\""});
        addProduction("newline", new String[]{"'\r\n'"}, new String[]{"'\n'"});
        addProduction("sp", new String[]{"\"\\s*\""});
        addProduction("eq", new String[]{"'<='"}, new String[]{"'<'"}, new String[]{"'>='"}, new String[]{"'>'"}, new String[]{"'='"});
    }
}

